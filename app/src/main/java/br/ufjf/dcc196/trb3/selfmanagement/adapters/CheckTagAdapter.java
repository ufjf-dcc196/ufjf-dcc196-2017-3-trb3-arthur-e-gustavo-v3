package br.ufjf.dcc196.trb3.selfmanagement.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.CursorAdapter;

import java.util.ArrayList;
import java.util.List;

import br.ufjf.dcc196.trb3.selfmanagement.R;
import br.ufjf.dcc196.trb3.selfmanagement.helpers.AppContract;
import br.ufjf.dcc196.trb3.selfmanagement.models.Tag;

/**
 * Created by arthurlorenzi on 06/12/17.
 */

public class CheckTagAdapter extends CursorAdapter {

    List<Integer> selectedItemsPositions;

    public CheckTagAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        selectedItemsPositions = new ArrayList<>();
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.check_tag_list_item, parent, false);
        final CheckedTextView box = view.findViewById(R.id.chkTagListItem);
        box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer position = (Integer) v.getTag();
                box.setChecked(!box.isChecked());
                if(box.isChecked()){
                    if (!selectedItemsPositions.contains(position))
                        selectedItemsPositions.add(position);
                } else {
                    selectedItemsPositions.remove(position);
                }
            }
        });
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        CheckedTextView check = view.findViewById(R.id.chkTagListItem);
        check.setTag(cursor.getPosition());

        check.setText(cursor.getString(cursor.getColumnIndexOrThrow(AppContract.Tag.COLUMN_NAME_NAME)));
        int task = cursor.getInt(cursor.getColumnIndexOrThrow(AppContract.TaskTag.COLUMN_NAME_TASK));
        check.setChecked(task != 0);

        if(task != 0){
            if (!selectedItemsPositions.contains(cursor.getPosition()))
                selectedItemsPositions.add(cursor.getPosition());
        }
    }

    @Override
    public Object getItem(int position) {
        Cursor cursor = (Cursor) super.getItem(position);

        Tag tag = new Tag(cursor.getString(cursor.getColumnIndexOrThrow(AppContract.Tag.COLUMN_NAME_NAME)));
        tag.setId(cursor.getLong(cursor.getColumnIndexOrThrow(AppContract.Tag._ID)));

        return tag;
    }

    public boolean isSelected(Integer position) {
        return selectedItemsPositions.contains(position);
    }
}
