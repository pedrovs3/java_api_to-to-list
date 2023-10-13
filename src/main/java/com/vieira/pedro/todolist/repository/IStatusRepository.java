package com.vieira.pedro.todolist.repository;

import com.vieira.pedro.todolist.models.StatusModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IStatusRepository extends JpaRepository<StatusModel, UUID> {
}
