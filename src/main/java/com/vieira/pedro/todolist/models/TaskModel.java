package com.vieira.pedro.todolist.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity(name = "tbl_task")
public class TaskModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private String id;

    private String title;
    private String description;
    private LocalDateTime dueDate;
    @ManyToOne
    @JoinColumn(name = "status_id")
    private StatusModel status;
    @ManyToOne
    @JoinColumn(name = "priority_id")
    private PriorityModel priority;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryModel category;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column(name = "user_id")
    private UUID userId;

    public void setTitle(String title) throws Exception {
        if (title.length() > 50) {
            throw new Exception("Title must be less than 50 characters");
        }
        this.title = title;
    }
}
