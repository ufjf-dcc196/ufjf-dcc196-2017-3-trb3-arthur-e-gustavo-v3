package br.ufjf.dcc196.trb3.selfmanagement;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import br.ufjf.dcc196.trb3.selfmanagement.adapters.TagAdapter;
import br.ufjf.dcc196.trb3.selfmanagement.helpers.TagHelper;
import br.ufjf.dcc196.trb3.selfmanagement.models.Tag;

public class TagActivity extends AppCompatActivity implements TagNameDialog.TagNameDialogListener {

    private boolean editing;
    private Tag selectedTag;
    private View selectedView;
    private ListView lstTags;
    private TagAdapter tagAdapter;
    private TagHelper tagHelper;
    private Toolbar toolbar;

    private void updateList() {
        if (tagAdapter != null) {
            tagAdapter.swapCursor(tagHelper.getAll());
        } else {
            tagAdapter = new TagAdapter(getBaseContext(), tagHelper.getAll(), 0);
            lstTags.setAdapter(tagAdapter);
        }
    }

    private void clearListSelection() {
        if (selectedTag != null) {
            selectedView.setSelected(false);

            selectedTag = null;
            selectedView = null;

            MenuItem edit = toolbar.getMenu().findItem(R.id.action_edit);
            MenuItem remove = toolbar.getMenu().findItem(R.id.action_remove);
            edit.setVisible(false);
            remove.setVisible(false);
        }
    }

    private TagNameDialog openDialog() {
        TagNameDialog dialog = new TagNameDialog();
        dialog.setListener(TagActivity.this);
        dialog.show(getFragmentManager(), "Dialog");

        return dialog;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        editing = false;

        lstTags = (ListView) findViewById(R.id.lstTags);
        lstTags.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        tagHelper = new TagHelper(getApplicationContext());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearListSelection();
                openDialog();
            }
        });

        lstTags.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                selectedView = view;
                selectedTag = (Tag) tagAdapter.getItem(position);

                view.setSelected(true);
                MenuItem edit = toolbar.getMenu().findItem(R.id.action_edit);
                MenuItem remove = toolbar.getMenu().findItem(R.id.action_remove);
                edit.setVisible(true);
                remove.setVisible(true);

                return false;
            }
        });

        lstTags.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (selectedTag != null) {
                    selectedView.setSelected(false);

                    selectedTag = (Tag) tagAdapter.getItem(position);
                    selectedView = view;

                    selectedView.setSelected(true);
                } else {
                    Intent intent = new Intent(TagActivity.this, MainActivity.class);
                    intent.putExtra("tagId", (int) ((Tag) tagAdapter.getItem(position)).getId());
                    startActivity(intent);
                }
            }
        });

        updateList();
    }

    @Override
    public void onBackPressed() {
        if (selectedView == null) {
            super.onBackPressed();
        } else {
            clearListSelection();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_tag, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_tags:
                Intent intent = new Intent(TagActivity.this, MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_edit:
                editing = true;
                TagNameDialog dialog = openDialog();
                dialog.setTagName(selectedTag.getName());
                return true;
            case R.id.action_remove:
                tagHelper.remove(selectedTag);
                clearListSelection();
                updateList();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog, String name) {
        if (editing) {
            selectedTag.setName(name);
            tagHelper.update(selectedTag);
            clearListSelection();
        } else {
            Tag newTag = new Tag(name);
            tagHelper.add(newTag);
        }

        updateList();
    }

    @Override
    public void onDialogCancelClick(DialogFragment dialog) {
        editing = false;
    }
}
