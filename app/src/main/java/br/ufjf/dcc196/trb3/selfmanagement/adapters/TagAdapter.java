package br.ufjf.dcc196.trb3.selfmanagement.adapters;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import br.ufjf.dcc196.trb3.selfmanagement.R;
import br.ufjf.dcc196.trb3.selfmanagement.helpers.AppContract;
import br.ufjf.dcc196.trb3.selfmanagement.models.Tag;

/**
 * Created by arthurlorenzi on 05/12/17.
 */

public class TagAdapter extends CursorAdapter {

    public TagAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.tag_list_item, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView content = view.findViewById(R.id.txtTagListItem);

        content.setTextColor(ContextCompat.getColor(context, R.color.colorBlack));
        content.setText(cursor.getString(cursor.getColumnIndexOrThrow(AppContract.Tag.COLUMN_NAME_NAME)));
    }

    @Override
    public Object getItem(int position) {
        Cursor cursor = (Cursor) super.getItem(position);

        Tag tag = new Tag(cursor.getString(cursor.getColumnIndexOrThrow(AppContract.Tag.COLUMN_NAME_NAME)));
        tag.setId(cursor.getLong(cursor.getColumnIndexOrThrow(AppContract.Tag._ID)));

        return tag;
    }
}
