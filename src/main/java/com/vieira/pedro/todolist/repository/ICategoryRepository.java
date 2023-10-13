package com.vieira.pedro.todolist.repository;

import com.vieira.pedro.todolist.models.CategoryModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ICategoryRepository extends JpaRepository<CategoryModel, UUID> {
}
