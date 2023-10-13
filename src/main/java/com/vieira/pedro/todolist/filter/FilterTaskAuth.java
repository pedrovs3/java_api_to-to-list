package com.vieira.pedro.todolist.filter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.vieira.pedro.todolist.repository.IUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;


@Component
public class FilterTaskAuth extends OncePerRequestFilter {

    private final IUserRepository iUserRepository;

    public FilterTaskAuth(IUserRepository iUserRepository) {
        this.iUserRepository = iUserRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // this works like a middleware
        // here we can check if the user is authenticated

        var servletPath = request.getServletPath();
        if (servletPath.contains("/api/task/")) {
            var authorization = request.getHeader("Authorization").substring("Basic".length()).trim();
            var decodedAuth = new String(Base64.getDecoder().decode(authorization));

            String[] credentials = decodedAuth.split(":");
            String username = credentials[0];
            String password = credentials[1];

            var user = this.iUserRepository.findByUsername(username);

            if (user == null) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            } else {
                if (!(BCrypt.verifyer().verify(password.toCharArray(), user.getPassword()).verified)) {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }
            }
            request.setAttribute("userId", user.getId());
            filterChain.doFilter(request, response);
        } else {
            filterChain.doFilter(request, response);
        }
    }
}
