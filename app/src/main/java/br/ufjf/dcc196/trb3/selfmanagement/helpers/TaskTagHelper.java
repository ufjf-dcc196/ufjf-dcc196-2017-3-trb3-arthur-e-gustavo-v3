package br.ufjf.dcc196.trb3.selfmanagement.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import br.ufjf.dcc196.trb3.selfmanagement.models.Tag;
import br.ufjf.dcc196.trb3.selfmanagement.models.Task;

/**
 * Created by arthurlorenzi on 06/12/17.
 */

public class TaskTagHelper extends DatabaseHelper {

    public TaskTagHelper(Context context) {
        super(context);
    }

    public Cursor getAllTags(Task selectionTask) {
        long id = selectionTask != null ? selectionTask.getId() : -1;

        try {
            SQLiteDatabase db = getWritableDatabase();
            return db.rawQuery(AppContract.TaskTag.SQL_SELECT_TAGS_BY_TASK, new String[]{String.valueOf(id)});
        } catch (Exception e) {
            Log.e("TAG", e.getLocalizedMessage());
            Log.e("TAG", e.getStackTrace().toString());
            return null;
        }
    }

    public Cursor getAllTasks(Tag tag) {
        long id = tag != null ? tag.getId() : -1;

        try {
            SQLiteDatabase db = getWritableDatabase();
            return db.rawQuery(AppContract.TaskTag.SQL_SELECT_TASKS_BY_TAG, new String[]{String.valueOf(id)});
        } catch (Exception e) {
            Log.e("TAG", e.getLocalizedMessage());
            Log.e("TAG", e.getStackTrace().toString());
            return null;
        }
    }

    public void removeAllTags(Task task) {
        try {
            SQLiteDatabase db = getWritableDatabase();

            String where = AppContract.TaskTag.COLUMN_NAME_TASK + " = ?";
            String[] args = {String.valueOf(task.getId())};

            db.delete(AppContract.TaskTag.TABLE_NAME, where, args);
        } catch (Exception e) {
            Log.e("TAGTASK", e.getLocalizedMessage());
            Log.e("TAGTASK", e.getStackTrace().toString());
        }
    }

    public void removeAllTasks(Tag tag) {
        try {
            SQLiteDatabase db = getWritableDatabase();

            String where = AppContract.TaskTag.COLUMN_NAME_TAG + " = ?";
            String[] args = {String.valueOf(tag.getId())};

            db.delete(AppContract.TaskTag.TABLE_NAME, where, args);
        } catch (Exception e) {
            Log.e("TAGTASK", e.getLocalizedMessage());
            Log.e("TAGTASK", e.getStackTrace().toString());
        }
    }

    public void add(Task task, Tag tag) {
        try {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put(AppContract.TaskTag.COLUMN_NAME_TASK, task.getId());
            values.put(AppContract.TaskTag.COLUMN_NAME_TAG, tag.getId());

            long id = db.insert(AppContract.TaskTag.TABLE_NAME, null, values);

            tag.setId(id);
        } catch (Exception e) {
            Log.e("TAGTASK", e.getLocalizedMessage());
            Log.e("TAGTASK", e.getStackTrace().toString());
        }
    }

    public void remove(Task task, Tag tag) {
        try {
            SQLiteDatabase db = getWritableDatabase();

            db.delete(AppContract.TaskTag.TABLE_NAME, AppContract.TaskTag.SQL_DELETE_WHERE, new String[]{String.valueOf(task.getId()), String.valueOf(tag.getId())});

        } catch (Exception e) {
            Log.e("TAGTASK", e.getLocalizedMessage());
            Log.e("TAGTASK", e.getStackTrace().toString());
        }
    }
}
