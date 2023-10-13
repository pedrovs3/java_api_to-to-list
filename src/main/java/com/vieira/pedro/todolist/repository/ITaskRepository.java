package com.vieira.pedro.todolist.repository;

import com.vieira.pedro.todolist.models.TaskModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ITaskRepository extends JpaRepository<TaskModel, UUID> {
    List<TaskModel> findAllByUserId(UUID userId);
    @Query("SELECT t FROM tbl_task t WHERE t.id = ?1")
    TaskModel findByStringId(String id);
}
