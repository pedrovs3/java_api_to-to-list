package com.vieira.pedro.todolist.repository;

import com.vieira.pedro.todolist.models.PriorityModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IPriorityRepository extends JpaRepository<PriorityModel, UUID>{}
