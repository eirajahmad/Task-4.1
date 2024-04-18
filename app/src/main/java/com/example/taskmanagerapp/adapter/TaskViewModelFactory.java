package com.example.taskmanagerapp.adapter;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import android.app.Application;

public class TaskViewModelFactory implements ViewModelProvider.Factory {
    private Application application;

    public TaskViewModelFactory(Application application) {
        this.application = application;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(TaskViewModel.class)) {
            return (T) new TaskViewModel(new TaskRepository(application));
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
