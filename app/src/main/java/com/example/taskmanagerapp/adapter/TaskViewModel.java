package com.example.taskmanagerapp.adapter;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import java.util.List;

public class TaskViewModel extends ViewModel {
    private LiveData<List<Task>> allTasks;
    private TaskRepository repository;

    public TaskViewModel(TaskRepository repository) {
        this.repository = repository;
        allTasks = repository.getAllTasks();
    }

    public LiveData<List<Task>> getAllTasks() {
        return allTasks;
    }

    public void insert(Task task) {
        repository.insert(task);
    }

    public void update(Task task) {
        repository.update(task);
    }

    public void delete(Task task) {
        repository.delete(task);
    }
    public LiveData<Task> getTaskById(int taskId) {
        return repository.getTaskById(taskId);
    }
}
