package com.vieira.pedro.todolist.repository;

import com.vieira.pedro.todolist.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface IUserRepository extends JpaRepository<UserModel, UUID>{
    @Query("SELECT u FROM tbl_user u WHERE u.username = ?1")
    UserModel findByUsername(String username);
}
