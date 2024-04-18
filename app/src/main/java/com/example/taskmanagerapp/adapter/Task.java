package com.example.taskmanagerapp.adapter;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(tableName = "tasks")
public class Task {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String description;
    private long dueDate;

    // Constructor, getters, and setters
    // Constructor
    public Task(int id, String title, String description, long dueDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public long getDueDate() {
        return dueDate;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDueDate(long dueDate) {
        this.dueDate = dueDate;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Task task = (Task) obj;
        return id == task.id &&
                dueDate == task.dueDate &&
                title.equals(task.title) &&
                description.equals(task.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, dueDate);
    }
}
