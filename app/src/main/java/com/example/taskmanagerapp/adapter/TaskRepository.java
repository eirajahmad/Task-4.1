package com.example.taskmanagerapp.adapter;

import android.app.Application;
import androidx.lifecycle.LiveData;

import com.example.taskmanagerapp.adapter.Task;
import com.example.taskmanagerapp.adapter.TaskDao;

import java.util.List;

public class TaskRepository {
    private TaskDao taskDao;
    private LiveData<List<Task>> allTasks;

    // Constructor that gets a handle to the database and initializes the member variables.
    public TaskRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        taskDao = db.taskDao();
        allTasks = taskDao.getAllTasks();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    LiveData<List<Task>> getAllTasks() {
        return allTasks;
    }

    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long-running operations on the main thread, blocking the UI.
    void insert(final Task task) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            taskDao.insertTask(task);
        });
    }

    void update(Task task) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            taskDao.updateTask(task);
        });
    }

    void delete(Task task) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            taskDao.deleteTask(task);
        });
    }
    public LiveData<Task> getTaskById(int taskId) {
        return taskDao.getTaskById(taskId);
    }
}
