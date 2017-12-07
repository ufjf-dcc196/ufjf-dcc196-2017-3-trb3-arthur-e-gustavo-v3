package br.ufjf.dcc196.trb3.selfmanagement.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import br.ufjf.dcc196.trb3.selfmanagement.models.Task;

/**
 * Created by arthurlorenzi on 05/12/17.
 */

public class TaskHelper extends DatabaseHelper {

    private Context context;

    public TaskHelper(Context context) {
        super(context);
        this.context = context;
    }

    public Cursor getAll() {
        try {
            SQLiteDatabase db = getWritableDatabase();
            return db.rawQuery(AppContract.Task.SQL_SELECT_TASKS, null);
        } catch (Exception e) {
            Log.e("TASK", e.getLocalizedMessage());
            Log.e("TASK", e.getStackTrace().toString());
            return null;
        }
    }

    public Task get(long id) {
        Cursor cursor = null;
        Task task = new Task();
        try {
            SQLiteDatabase db = getWritableDatabase();
            String[] vision = {
                    AppContract.Task._ID,
                    AppContract.Task.COLUMN_NAME_TITLE,
                    AppContract.Task.COLUMN_NAME_DESCRIPTION,
                    AppContract.Task.COLUMN_NAME_DIFFICULTY,
                    AppContract.Task.COLUMN_NAME_STATE
            };
            String where = AppContract.Task._ID + " = ?";
            String[] args = {String.valueOf(id)};
            String sort = AppContract.Task.COLUMN_NAME_TITLE + " ASC";
            cursor = db.query(AppContract.Task.TABLE_NAME, vision, where, args, null, null, sort);

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                task.setId(id);
                task.setTitle(cursor.getString(cursor.getColumnIndex(AppContract.Task.COLUMN_NAME_TITLE)));
                task.setDescription(cursor.getString(cursor.getColumnIndex(AppContract.Task.COLUMN_NAME_DESCRIPTION)));
                task.setDifficulty(cursor.getInt(cursor.getColumnIndex(AppContract.Task.COLUMN_NAME_DIFFICULTY)));
                task.setState(Task.TaskState.valueOf(cursor.getString(cursor.getColumnIndexOrThrow(AppContract.Task.COLUMN_NAME_STATE))));
            }

            return task;
        } finally {
            cursor.close();
        }
    }

    public void add(Task task) {
        try {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(AppContract.Task.COLUMN_NAME_TITLE, task.getTitle());
            values.put(AppContract.Task.COLUMN_NAME_DESCRIPTION, task.getDescription());
            values.put(AppContract.Task.COLUMN_NAME_DIFFICULTY, task.getDifficulty());
            values.put(AppContract.Task.COLUMN_NAME_STATE, task.getState().name());

            long id = db.insert(AppContract.Task.TABLE_NAME, null, values);

            task.setId(id);
        } catch (Exception e) {
            Log.e("TASK", e.getLocalizedMessage());
            Log.e("TASK", e.getStackTrace().toString());
        }
    }

    public void update(Task task) {
        try {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(AppContract.Task.COLUMN_NAME_TITLE, task.getTitle());
            values.put(AppContract.Task.COLUMN_NAME_DESCRIPTION, task.getDescription());
            values.put(AppContract.Task.COLUMN_NAME_DIFFICULTY, task.getDifficulty());
            values.put(AppContract.Task.COLUMN_NAME_STATE, task.getState().name());

            String where = AppContract.Task._ID + " = ?";
            String[] args = {String.valueOf(task.getId())};

            db.update(AppContract.Task.TABLE_NAME, values, where, args);
        } catch (Exception e) {
            Log.e("TASK", e.getLocalizedMessage());
            Log.e("TASK", e.getStackTrace().toString());
        }
    }

    public void remove(Task task) {
        try {
            (new TaskTagHelper(context)).removeAllTags(task);

            SQLiteDatabase db = getWritableDatabase();

            String where = AppContract.Task._ID + " = ?";
            String[] args = {String.valueOf(task.getId())};

            db.delete(AppContract.Task.TABLE_NAME, where, args);
        } catch (Exception e) {
            Log.e("TASK", e.getLocalizedMessage());
            Log.e("TASK", e.getStackTrace().toString());
        }
    }
}
