package com.example.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {

    private Context context;
    private List<Note> notesList;
    private DatabaseHelper db;

    public NotesAdapter(Context context, List<Note> notesList) {
        this.context = context;
        this.notesList = notesList;
        this.db = new DatabaseHelper(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Note note = notesList.get(position);
        holder.titleTextView.setText(note.getTitle());
        holder.contentTextView.setText(note.getContent());

        // Обработка клика для редактирования
        holder.itemView.setOnClickListener(v -> {
            showEditNoteDialog(note, position);
        });

        // Обработка долгого нажатия для удаления
        holder.itemView.setOnLongClickListener(v -> {
            showDeleteConfirmationDialog(note, position);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView titleTextView;
        public TextView contentTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            contentTextView = itemView.findViewById(R.id.contentTextView);
        }
    }

    private void showEditNoteDialog(Note note, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.dialog_edit_note, null);

        EditText editTitle = dialogView.findViewById(R.id.editTitle);
        EditText editContent = dialogView.findViewById(R.id.editContent);

        editTitle.setText(note.getTitle());
        editContent.setText(note.getContent());

        builder.setView(dialogView)
                .setTitle("Редактировать заметку")
                .setPositiveButton("Сохранить", (dialog, id) -> {
                    String newTitle = editTitle.getText().toString().trim();
                    String newContent = editContent.getText().toString().trim();

                    if (!newTitle.isEmpty()) {
                        note.setTitle(newTitle);
                        note.setContent(newContent);
                        db.updateNote(note);
                        notifyItemChanged(position);
                        Toast.makeText(context, "Заметка обновлена", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Заголовок не может быть пустым", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Отмена", (dialog, id) -> dialog.cancel())
                .create()
                .show();
    }

    private void showDeleteConfirmationDialog(Note note, int position) {
        new AlertDialog.Builder(context)
                .setTitle("Удаление заметки")
                .setMessage("Вы уверены, что хотите удалить эту заметку?")
                .setPositiveButton("Удалить", (dialog, which) -> {
                    db.deleteNote(note);
                    notesList.remove(position);
                    notifyItemRemoved(position);
                    Toast.makeText(context, "Заметка удалена", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Отмена", null)
                .show();
    }

    public void updateNotesList(List<Note> newNotes) {
        notesList.clear();
        notesList.addAll(newNotes);
        notifyDataSetChanged();
    }
}