package com.example.taskmanagerapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.taskmanagerapp.adapter.Task;
import com.example.taskmanagerapp.adapter.TaskViewModel;
import com.example.taskmanagerapp.adapter.TaskViewModelFactory;

import java.util.Calendar;

public class AddTaskActivity extends AppCompatActivity {
    private EditText editTaskTitle;
    private EditText editTaskDescription;
    private DatePicker pickerTaskDueDate;
    private TaskViewModel taskViewModel;
    private Task currentTask;  // Hold the current task if editing

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_task);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Enable the Up button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        editTaskTitle = findViewById(R.id.edit_task_title);
        editTaskDescription = findViewById(R.id.edit_task_description);
        pickerTaskDueDate = findViewById(R.id.picker_task_due_date);
        Button saveButton = findViewById(R.id.button_save);
        TaskViewModelFactory factory = new TaskViewModelFactory(getApplication());
        taskViewModel = new ViewModelProvider(this, factory).get(TaskViewModel.class);
        final int taskId = getIntent().getIntExtra("TASK_ID", -1);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = editTaskTitle.getText().toString().trim();
                String description = editTaskDescription.getText().toString().trim();
                long dueDateMillis = getDueDateFromDatePicker();

                if (!isValidTitle(title)) {
                    Toast.makeText(getApplicationContext(), "Title cannot be empty and must be less than 50 characters.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!isValidDueDate(dueDateMillis)) {
                    Toast.makeText(getApplicationContext(), "Due date cannot be in the past.", Toast.LENGTH_SHORT).show();
                    return;
                }

                saveTask(taskId);
            }
        });
       if (taskId != -1) {
            // Load the task details if editing
            taskViewModel.getTaskById(taskId).observe(this, task -> {
                if (task != null) {
                    currentTask = task;
                    editTaskTitle.setText(task.getTitle());
                    editTaskDescription.setText(task.getDescription());
                    setDatePicker(task.getDueDate());
                }
            });
        }
    }

    private void saveTask(int taskId) {
        if (taskId !=-1) {
            String title = editTaskTitle.getText().toString();
            String description = editTaskDescription.getText().toString();
            long dueDate = getDueDateFromDatePicker();

            Task currentTask = new Task(taskId, title, description, dueDate);

            taskViewModel.update(currentTask);
            finish();
        }else{
            String title = editTaskTitle.getText().toString();
            String description = editTaskDescription.getText().toString();
            long dueDate = getDueDateFromDatePicker();

            Task task = new Task(0, title, description, dueDate);
            taskViewModel.insert(task);
            finish();
        }
       // Close this activity and return to the previous one
    }

    private long getDueDateFromDatePicker() {
        int day = pickerTaskDueDate.getDayOfMonth();
        int month = pickerTaskDueDate.getMonth();
        int year =  pickerTaskDueDate.getYear();
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        return calendar.getTimeInMillis();
    }
    private void setDatePicker(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        pickerTaskDueDate.updateDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();  // This method is called when the up button is pressed. Just finish the activity or handle fragment back stack.
        return true;
    }
    private boolean isValidTitle(String title) {
        return title != null && !title.isEmpty() && title.length() <= 50;
    }

    private boolean isValidDueDate(long dueDateMillis) {
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        today.set(Calendar.MILLISECOND, 0);
        return dueDateMillis >= today.getTimeInMillis();
    }


}