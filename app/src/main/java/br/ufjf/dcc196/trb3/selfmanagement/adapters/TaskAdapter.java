package br.ufjf.dcc196.trb3.selfmanagement.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import br.ufjf.dcc196.trb3.selfmanagement.R;
import br.ufjf.dcc196.trb3.selfmanagement.helpers.AppContract;
import br.ufjf.dcc196.trb3.selfmanagement.models.Tag;
import br.ufjf.dcc196.trb3.selfmanagement.models.Task;

/**
 * Created by arthurlorenzi on 03/12/17.
 */

public class TaskAdapter extends CursorAdapter {

    public TaskAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.task_list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView textTitle = view.findViewById(R.id.txtTaskTitle);
        TextView textState = view.findViewById(R.id.txtTaskState);
        TextView textTags = view.findViewById(R.id.txtTaskTags);

        String title = cursor.getString(cursor.getColumnIndexOrThrow(AppContract.Task.COLUMN_NAME_TITLE));
        String tags = cursor.getString(cursor.getColumnIndexOrThrow(AppContract.Task.COLUMN_NAME_TAGS));
        Task.TaskState state =
                Task.TaskState.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(AppContract.Task.COLUMN_NAME_STATE)));

        textTitle.setText(title);
        textState.setText(state.toString());
        textTags.setText(context.getResources().getString(R.string.txt_task_tags) + ": " + (tags != null ? tags: "-"));
    }

    @Override
    public Object getItem(int position) {
        Cursor cursor = (Cursor) super.getItem(position);

        Task task = new Task(
                cursor.getString(cursor.getColumnIndexOrThrow(AppContract.Task.COLUMN_NAME_TITLE)),
                cursor.getString(cursor.getColumnIndexOrThrow(AppContract.Task.COLUMN_NAME_DESCRIPTION)),
                cursor.getInt(cursor.getColumnIndexOrThrow(AppContract.Task.COLUMN_NAME_DIFFICULTY)),
                Task.TaskState.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(AppContract.Task.COLUMN_NAME_STATE))),
                new ArrayList<Tag>()
        );
        task.setId(cursor.getLong(cursor.getColumnIndexOrThrow(AppContract.Tag._ID)));

        return task;
    }
}
