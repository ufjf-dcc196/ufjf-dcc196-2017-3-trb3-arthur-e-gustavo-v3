package br.ufjf.dcc196.trb3.selfmanagement;

import android.app.Activity;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import br.ufjf.dcc196.trb3.selfmanagement.models.Tag;

/**
 * Created by arthurlorenzi on 05/12/17.
 */

public class TagNameDialog extends DialogFragment {

    public interface TagNameDialogListener {
        public void onDialogPositiveClick(DialogFragment dialog, String name);
        public void onDialogCancelClick(DialogFragment dialog);
    }

    private String tagName;
    private TagNameDialogListener Listener;
    private Dialog dialog;
    private EditText edtTagName;

    public TagNameDialogListener getListener() {
        return Listener;
    }

    public void setListener(TagNameDialogListener Listener) {
        this.Listener = Listener;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_tag, null);

        builder.setView(dialogView)
                .setPositiveButton(R.string.dialog_confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Listener.onDialogPositiveClick(TagNameDialog.this, edtTagName.getText().toString());
                    }
                })
                .setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        TagNameDialog.this.getDialog().cancel();
                        Listener.onDialogCancelClick(TagNameDialog.this);
                    }
                });

        dialog = builder.create();
        edtTagName = (EditText) dialogView.findViewById(R.id.edtTagName);

        if (tagName != null)
            edtTagName.setText(tagName);

        return dialog;
    }


}
