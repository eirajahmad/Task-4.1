package com.example.taskmanagerapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskmanagerapp.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TaskAdapter extends ListAdapter<Task, TaskAdapter.TaskViewHolder> {

    private OnItemClickListener listener;

    // Change the constructor to only take the listener
    // Updated constructor to take only the OnItemClickListener
    public TaskAdapter(OnItemClickListener listener) {
        super(new TaskDiff());  // TaskDiff initialized right here
        this.listener = listener;
    }



    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item, parent, false);
        return new TaskViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task currentTask = getItem(position);
        holder.bind(currentTask, listener);
    }

    static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle, textView_description, textViewDate;
        ImageView editImage, deleteImage;

        TaskViewHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textView2);
            textView_description = itemView.findViewById(R.id.textView_description);
            editImage = itemView.findViewById(R.id.image_edit);

            deleteImage = itemView.findViewById(R.id.image_delete);
        }

        void bind(final Task task, final OnItemClickListener listener) {
            textViewTitle.setText(task.getTitle());
            textView_description.setText("more details");
            textView_description.setOnClickListener(v -> listener.onDetails(task));
            editImage.setOnClickListener(v -> listener.onEditClick(task));
            deleteImage.setOnClickListener(v -> listener.onDeleteClick(task));
        }

        private String formatDate(long dateInMillis) {
            SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
            return sdf.format(new Date(dateInMillis));
        }
    }

    public interface OnItemClickListener {
        void onEditClick(Task task);
        void onDeleteClick(Task task);
        void onDetails(Task task);
    }

    // TaskDiff is defined as a static nested class
    public static class TaskDiff extends DiffUtil.ItemCallback<Task> {
        @Override
        public boolean areItemsTheSame(@NonNull Task oldItem, @NonNull Task newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Task oldItem, @NonNull Task newItem) {
            return oldItem.equals(newItem);  // Make sure Task has a properly overridden equals method
        }
    }
}
