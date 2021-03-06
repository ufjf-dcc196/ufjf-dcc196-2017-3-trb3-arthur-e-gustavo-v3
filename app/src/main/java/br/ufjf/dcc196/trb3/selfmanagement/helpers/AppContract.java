package br.ufjf.dcc196.trb3.selfmanagement.helpers;

import android.provider.BaseColumns;

/**
 * Created by arthurlorenzi on 03/12/17.
 */

public class AppContract {

    public static final String TEXT_TYPE = " TEXT";
    public static final String INT_TYPE = " INTEGER";
    public static final String SEP = ",";

    public AppContract() {
    }

    public static final class Task implements BaseColumns {
        public static final String TABLE_NAME = "task";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_DIFFICULTY = "difficulty";
        public static final String COLUMN_NAME_STATE = "state";
        public static final String COLUMN_NAME_TAGS = "taglist";

        public static final String SQL_CREATE_TASK = "CREATE TABLE " + TABLE_NAME + " (" +
                _ID + INT_TYPE + " PRIMARY KEY AUTOINCREMENT" + SEP +
                COLUMN_NAME_TITLE + TEXT_TYPE + SEP +
                COLUMN_NAME_DESCRIPTION + TEXT_TYPE + SEP +
                COLUMN_NAME_DIFFICULTY + INT_TYPE + SEP +
                COLUMN_NAME_STATE + TEXT_TYPE + ")";

        public static final String SQL_DROP_TASK = "DROP TABLE IF EXISTS " + TABLE_NAME;

        public static final String SQL_SELECT_TASK = "SELECT " +
                _ID + SEP +
                COLUMN_NAME_TITLE + SEP +
                COLUMN_NAME_DESCRIPTION + SEP +
                COLUMN_NAME_DIFFICULTY +
                COLUMN_NAME_STATE +
                " FROM " + TABLE_NAME +
                " WHERE " + _ID + " = ?";

        public static final String SQL_SELECT_TASKS = "SELECT " +
                TABLE_NAME + "." + _ID + SEP +
                COLUMN_NAME_TITLE + SEP +
                COLUMN_NAME_DESCRIPTION + SEP +
                COLUMN_NAME_DIFFICULTY + SEP +
                COLUMN_NAME_STATE + SEP +
                " group_concat(" + Tag.COLUMN_NAME_NAME + ", \", \") "
                    + "AS " + COLUMN_NAME_TAGS +
                " FROM " + TABLE_NAME +
                " LEFT JOIN " + TaskTag.TABLE_NAME + " ON " + TaskTag.COLUMN_NAME_TASK + "=" + TABLE_NAME + "." + _ID +
                " LEFT JOIN " + Tag.TABLE_NAME + " ON " + Tag.TABLE_NAME + "." + Tag._ID + "=" + TaskTag.COLUMN_NAME_TAG +
                " GROUP BY " + TABLE_NAME + "." + _ID +
                " ORDER BY " + COLUMN_NAME_TITLE;

        public static final String SQL_DELETE_TASK = "DELETE FROM "
                + TABLE_NAME + " WHERE " + _ID + " = ?";
    }

    public static final class Tag implements BaseColumns {
        public static final String TABLE_NAME = "tag";
        public static final String COLUMN_NAME_NAME = "name";

        public static final String SQL_CREATE_TAG = "CREATE TABLE " + TABLE_NAME + " (" +
                _ID + INT_TYPE + " PRIMARY KEY AUTOINCREMENT" + SEP +
                COLUMN_NAME_NAME + TEXT_TYPE + ")";

        public static final String SQL_DROP_TAG = "DROP TABLE IF EXISTS " + TABLE_NAME;

        public static final String SQL_SELECT_TAG = "SELECT " +
                _ID + SEP + COLUMN_NAME_NAME +
                " FROM " + TABLE_NAME +
                " WHERE " + _ID + " = ?";

        public static final String SQL_SELECT_TAGS = "SELECT " +
                _ID + SEP + COLUMN_NAME_NAME +
                " FROM " + TABLE_NAME;

        public static final String SQL_DELETE_TAG = "DELETE FROM "
                + TABLE_NAME + " WHERE " + _ID + " = ?";
    }

    public static final class TaskTag implements BaseColumns {
        public static final String TABLE_NAME = "tasktag";
        public static final String COLUMN_NAME_TASK = "task";
        public static final String COLUMN_NAME_TAG = "tag";

        public static final String SQL_CREATE_TASKTAG = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_NAME_TASK + INT_TYPE + " NOT NULL REFERENCES " + Task.TABLE_NAME + "(" + Task._ID + ")" + SEP +
                COLUMN_NAME_TAG + INT_TYPE + " NOT NULL REFERENCES " + Tag.TABLE_NAME + "(" + Tag._ID + ")" + SEP +
                " PRIMARY KEY (" + COLUMN_NAME_TASK + SEP + COLUMN_NAME_TAG + "))";

        public static final String SQL_DROP_TASKTAG = "DROP TABLE IF EXISTS " + TABLE_NAME;

        public static final String SQL_SELECT_TASKS = Task.SQL_SELECT_TASKS +
                " JOIN " + TABLE_NAME + " ON " + Task._ID + " = " + COLUMN_NAME_TASK;

        public static final String SQL_SELECT_TAGS = Tag.SQL_SELECT_TAGS +
                " JOIN " + TABLE_NAME + " ON " + Tag._ID + " = " + COLUMN_NAME_TAG;

        public static final String SQL_SELECT_TAGS_BY_TASK = "SELECT " +
                Tag.TABLE_NAME + "." + Tag._ID + SEP +
                Tag.COLUMN_NAME_NAME + SEP +
                TABLE_NAME + "." + COLUMN_NAME_TASK +
                " FROM " + Tag.TABLE_NAME +
                " LEFT JOIN " + TABLE_NAME + " ON " + Tag.TABLE_NAME + "." + Tag._ID + "=" + COLUMN_NAME_TAG +
                " AND " + TABLE_NAME + "." + COLUMN_NAME_TASK + " =  ?";

        public static final String SQL_SELECT_TASKS_BY_TAG = "SELECT " +
                Task.TABLE_NAME + "." + Task._ID + SEP +
                Task.COLUMN_NAME_TITLE + SEP +
                Task.COLUMN_NAME_DESCRIPTION + SEP +
                Task.COLUMN_NAME_DIFFICULTY + SEP +
                Task.COLUMN_NAME_STATE + SEP +
                " group_concat(" + Tag.COLUMN_NAME_NAME + ", \", \") "
                + "AS " + Task.COLUMN_NAME_TAGS +
                " FROM " + Task.TABLE_NAME +
                " JOIN " + TABLE_NAME + " ON " + TABLE_NAME + "." + COLUMN_NAME_TASK + "=" + Task.TABLE_NAME + "." + Task._ID +
                " JOIN " + Tag.TABLE_NAME + " ON " + Tag.TABLE_NAME + "." + Tag._ID + "=" + TABLE_NAME + "." + COLUMN_NAME_TAG +
                " WHERE EXISTS (SELECT 1 FROM " + TABLE_NAME +
                    " WHERE " + TABLE_NAME + "." + COLUMN_NAME_TAG + "= ?" +
                        " AND " + TABLE_NAME + "." + COLUMN_NAME_TASK + "=" + Task.TABLE_NAME + "." + Task._ID + ")" +
                " GROUP BY " + Task.TABLE_NAME + "." + _ID;

        public static final String SQL_DELETE_WHERE = COLUMN_NAME_TASK + " = ? AND " + COLUMN_NAME_TAG + " = ?";
    }
}
