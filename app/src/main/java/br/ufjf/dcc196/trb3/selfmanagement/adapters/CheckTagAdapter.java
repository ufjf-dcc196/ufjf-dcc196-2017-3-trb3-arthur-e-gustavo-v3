package br.ufjf.dcc196.trb3.selfmanagement.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.CursorAdapter;

import br.ufjf.dcc196.trb3.selfmanagement.R;
import br.ufjf.dcc196.trb3.selfmanagement.helpers.AppContract;
import br.ufjf.dcc196.trb3.selfmanagement.models.Tag;

/**
 * Created by arthurlorenzi on 06/12/17.
 */

public class CheckTagAdapter extends CursorAdapter {

    public CheckTagAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.check_tag_list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        CheckedTextView check = view.findViewById(R.id.chkTagListItem);

        check.setText(cursor.getString(cursor.getColumnIndexOrThrow(AppContract.Tag.COLUMN_NAME_NAME)));
        int task = cursor.getInt(cursor.getColumnIndexOrThrow(AppContract.TaskTag.COLUMN_NAME_TASK));

        check.setChecked(task != 0);
    }

    @Override
    public Object getItem(int position) {
        Cursor cursor = (Cursor) super.getItem(position);

        Tag tag = new Tag(cursor.getString(cursor.getColumnIndexOrThrow(AppContract.Tag.COLUMN_NAME_NAME)));
        tag.setId(cursor.getLong(cursor.getColumnIndexOrThrow(AppContract.Tag._ID)));

        return tag;
    }
}
