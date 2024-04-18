package com.example.taskmanagerapp;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TaskDetailActivity extends AppCompatActivity {
    private TextView textViewTitle, textViewDescription, textViewDueDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_task_detail);
        textViewTitle = findViewById(R.id.textViewTitle);
        textViewDescription = findViewById(R.id.textViewDescription);
        textViewDueDate = findViewById(R.id.textViewDueDate);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Enable the Up button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Get the data passed from the previous Activity
        String title = getIntent().getStringExtra("EXTRA_TASK_TITLE");
        String description = getIntent().getStringExtra("EXTRA_TASK_DESCRIPTION");
        long dueDate = getIntent().getLongExtra("EXTRA_TASK_DUE_DATE", 0);

        // Set the text on the TextViews
        textViewTitle.setText(title);
        textViewDescription.setText(description);
        textViewDueDate.setText(convertMillisToDate(dueDate));
    }
    private String convertMillisToDate(long millis) {
        SimpleDateFormat format = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
        return format.format(new Date(millis));
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();  // This method is called when the up button is pressed. Just finish the activity or handle fragment back stack.
        return true;
    }
}