package br.ufjf.dcc196.trb3.selfmanagement.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import br.ufjf.dcc196.trb3.selfmanagement.models.Tag;

/**
 * Created by arthurlorenzi on 05/12/17.
 */

public class TagHelper extends DatabaseHelper {

    public TagHelper(Context context) {
        super(context);
    }

    public Cursor getAll() {
        try {
            SQLiteDatabase db = getWritableDatabase();
            String[] vision = {
                    AppContract.Tag._ID,
                    AppContract.Tag.COLUMN_NAME_NAME
            };
            String sort = AppContract.Tag.COLUMN_NAME_NAME + " ASC";
            return db.query(AppContract.Tag.TABLE_NAME, vision, null, null, null, null, sort);
        } catch (Exception e) {
            Log.e("BOOK", e.getLocalizedMessage());
            Log.e("BOOK", e.getStackTrace().toString());
            return null;
        }
    }

    public void add(Tag tag) {
        try {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(AppContract.Tag.COLUMN_NAME_NAME, tag.getName());

            long id = db.insert(AppContract.Tag.TABLE_NAME, null, values);

            tag.setId(id);
        } catch (Exception e) {
            Log.e("TAG", e.getLocalizedMessage());
            Log.e("TAG", e.getStackTrace().toString());
        }
    }

    public void update(Tag tag) {
        try {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(AppContract.Tag.COLUMN_NAME_NAME, tag.getName());
            String where = AppContract.Tag._ID + " = ?";
            String[] args = {String.valueOf(tag.getId())};
            db.update(AppContract.Tag.TABLE_NAME, values, where, args);
        } catch (Exception e) {
            Log.e("TAG", e.getLocalizedMessage());
            Log.e("TAG", e.getStackTrace().toString());
        }
    }

    public void remove(Tag tag) {
        try {
            SQLiteDatabase db = getWritableDatabase();
            String where = AppContract.Tag._ID + " = ?";
            String[] args = {String.valueOf(tag.getId())};
            db.delete(AppContract.Tag.TABLE_NAME, where, args);
        } catch (Exception e) {
            Log.e("TAG", e.getLocalizedMessage());
            Log.e("TAG", e.getStackTrace().toString());
        }
    }

}
