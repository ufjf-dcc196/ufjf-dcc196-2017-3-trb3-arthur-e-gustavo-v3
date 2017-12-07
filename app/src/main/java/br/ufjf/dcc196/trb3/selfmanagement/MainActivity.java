package br.ufjf.dcc196.trb3.selfmanagement;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;

import br.ufjf.dcc196.trb3.selfmanagement.adapters.TaskAdapter;
import br.ufjf.dcc196.trb3.selfmanagement.helpers.TagHelper;
import br.ufjf.dcc196.trb3.selfmanagement.helpers.TaskHelper;
import br.ufjf.dcc196.trb3.selfmanagement.helpers.TaskTagHelper;
import br.ufjf.dcc196.trb3.selfmanagement.models.Tag;
import br.ufjf.dcc196.trb3.selfmanagement.models.Task;

public class MainActivity extends AppCompatActivity {

    private Tag filterTag;
    private Task selectedTask;
    private View selectedView;
    private ListView lstTasks;
    private TaskAdapter taskAdapter;
    private TaskHelper taskHelper;
    private TaskTagHelper taskTagHelper;
    private Toolbar toolbar;

    private void updateList() {
        Cursor cursor = filterTag != null ?  taskTagHelper.getAllTasks(filterTag) : taskHelper.getAll();

        if (taskAdapter != null) {
            taskAdapter.swapCursor(cursor);
        } else {
            taskAdapter = new TaskAdapter(getBaseContext(), cursor, 0);
            lstTasks.setAdapter(taskAdapter);
        }
    }

    private void clearListSelection() {
        if (selectedTask != null) {
            selectedView.setSelected(false);

            selectedTask = null;
            selectedView = null;

            MenuItem remove = toolbar.getMenu().findItem(R.id.action_remove);
            remove.setVisible(false);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        lstTasks = (ListView) findViewById(R.id.lstTags);
        lstTasks.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        taskHelper = new TaskHelper(getApplicationContext());
        taskTagHelper = new TaskTagHelper(getApplicationContext());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearListSelection();
                Intent intent = new Intent(MainActivity.this, AddEditTask.class);
                startActivity(intent);
            }
        });

        lstTasks.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                selectedView = view;
                selectedTask = (Task) taskAdapter.getItem(position);

                view.setSelected(true);
                MenuItem remove = toolbar.getMenu().findItem(R.id.action_remove);
                remove.setVisible(true);

                return false;
            }
        });

        lstTasks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (selectedTask != null) {
                    selectedView.setSelected(false);

                    selectedTask = (Task) taskAdapter.getItem(position);
                    selectedView = view;

                    selectedView.setSelected(true);
                } else {
                    Intent intent = new Intent(MainActivity.this, AddEditTask.class);
                    intent.putExtra("taskId", (int) ((Task) taskAdapter.getItem(position)).getId());
                    clearListSelection();
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Integer id = getIntent().getIntExtra("tagId", -1);

        filterTag = id != -1 ? new Tag(id): null;

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
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent;

        switch (id) {
            case R.id.action_tags:
                intent = new Intent(MainActivity.this, TagActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_remove:
                taskHelper.remove(selectedTask);
                clearListSelection();
                updateList();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
