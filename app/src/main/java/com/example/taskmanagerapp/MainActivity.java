package com.example.taskmanagerapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.example.taskmanagerapp.adapter.Task;
import com.example.taskmanagerapp.adapter.TaskAdapter;
import com.example.taskmanagerapp.adapter.TaskRepository;
import com.example.taskmanagerapp.adapter.TaskViewModel;
import com.example.taskmanagerapp.adapter.TaskViewModelFactory;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.view.GravityCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmanagerapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private TaskViewModel viewModel;
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // Initialize ViewModel
        TaskViewModelFactory factory = new TaskViewModelFactory(getApplication());
         viewModel = new ViewModelProvider(this, factory).get(TaskViewModel.class);
        TaskAdapter.OnItemClickListener listener = new TaskAdapter.OnItemClickListener() {
            @Override
            public void onEditClick(Task task) {
                // Implement your logic to edit a task
                Intent intent = new Intent(getApplicationContext(), AddTaskActivity.class);
                intent.putExtra("TASK_ID", task.getId());
                startActivity(intent);
            }

            @Override
            public void onDeleteClick(Task task) {
                // Implement your logic to delete a task
                showDeleteConfirmationDialog(task);
            }

            @Override
            public void onDetails(Task task) {
                Intent intent = new Intent(getApplicationContext(), TaskDetailActivity.class);
                intent.putExtra("EXTRA_TASK_TITLE", task.getTitle());
                intent.putExtra("EXTRA_TASK_DESCRIPTION", task.getDescription());
                intent.putExtra("EXTRA_TASK_DUE_DATE", task.getDueDate());
                startActivity(intent);
            }
        };
        TaskAdapter adapter = new TaskAdapter(listener);  // Correct initialization

        setSupportActionBar(binding.appBarMain.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);

        viewModel.getAllTasks().observe(this, adapter::submitList);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // Handle navigation view item clicks here.
                int id = item.getItemId();

                if (id == R.id.nav_home) {
                    // Handle the home action
                } else if (id == R.id.nav_gallery) {
                    Intent intent = new Intent(getApplicationContext(), AddTaskActivity.class);
                    intent.putExtra("TASK_ID", "-1");  // Assuming `task` is your Task object
                    startActivity(intent);

                }

                DrawerLayout drawer = findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
      }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();

    }
    private void showDeleteConfirmationDialog(Task task) {
        new AlertDialog.Builder(this)
                .setTitle("Delete Task")
                .setMessage("Are you sure you want to delete this task?")
                .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                    viewModel.delete(task);
                    // Notification that the task was deleted
                    Toast.makeText(this, "Task deleted", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

}