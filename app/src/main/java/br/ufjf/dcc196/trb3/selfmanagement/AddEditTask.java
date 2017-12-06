package br.ufjf.dcc196.trb3.selfmanagement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.HashSet;
import java.util.Set;

import br.ufjf.dcc196.trb3.selfmanagement.adapters.CheckTagAdapter;
import br.ufjf.dcc196.trb3.selfmanagement.adapters.TagAdapter;
import br.ufjf.dcc196.trb3.selfmanagement.helpers.TagHelper;
import br.ufjf.dcc196.trb3.selfmanagement.helpers.TaskHelper;
import br.ufjf.dcc196.trb3.selfmanagement.helpers.TaskTagHelper;
import br.ufjf.dcc196.trb3.selfmanagement.models.Tag;
import br.ufjf.dcc196.trb3.selfmanagement.models.Task;

public class AddEditTask extends AppCompatActivity {

    private boolean editing;
    private Task task;
    private EditText edtTaskTitle;
    private EditText edtTaskDescription;
    private SeekBar barTaskDifficulty;
    private Spinner spnTaskState;
    private ArrayAdapter<Task.TaskState> spnAdapter;
    private ListView lstCheckTags;
    private CheckTagAdapter tagAdapter;
    private TaskHelper taskHelper;
    private TaskTagHelper taskTagHelper;
    private Button btnTaskConfirm;
    private Button btnTaskCancel;
    private Set<Integer> checked = new HashSet<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_task);

        taskHelper = new TaskHelper(getApplicationContext());
        taskTagHelper = new TaskTagHelper(getApplicationContext());

        edtTaskTitle = (EditText) findViewById(R.id.edtTaskText);
        edtTaskDescription = (EditText) findViewById(R.id.edtTaskDescription);
        barTaskDifficulty = (SeekBar) findViewById(R.id.barTaskDifficulty);
        spnTaskState = (Spinner) findViewById(R.id.spnTaskState);
        spnAdapter = new ArrayAdapter<Task.TaskState>(this, android.R.layout.simple_spinner_item, Task.TaskState.values());
        spnTaskState.setAdapter(spnAdapter);
        btnTaskConfirm = (Button) findViewById(R.id.btnTaskConfirm);
        btnTaskCancel = (Button) findViewById(R.id.btnTaskCancel);
        lstCheckTags = (ListView) findViewById(R.id.lstCheckTags);
        tagAdapter = new CheckTagAdapter(getBaseContext(), taskTagHelper.getAllTags(null), 0);
        lstCheckTags.setAdapter(tagAdapter);

        spnTaskState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                task.setState((Task.TaskState) parent.getSelectedItem());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                task.setState(null);
            }
        });

        lstCheckTags.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CheckedTextView check = view.findViewById(R.id.chkTagListItem);
                boolean select = !check.isChecked();

                view.setSelected(select);
                check.setChecked(select);

                if(select)
                    checked.add((Integer) position);
                else
                    checked.remove((Integer) position);
            }
        });

        btnTaskCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddEditTask.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnTaskConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = edtTaskTitle.getText().toString();
                String description = edtTaskDescription.getText().toString();
                Integer difficulty = barTaskDifficulty.getProgress();

                if (title != null && !title.isEmpty() && description != null
                        && !description.isEmpty() && difficulty != null && task.getState() != null) {
                    task.setTitle(title);
                    task.setDescription(description);
                    task.setDifficulty(difficulty);

                    // TODO: Could be inside a transaction
                    if (!editing) {
                        taskHelper.add(task);
                    } else {
                        taskHelper.update(task);
                        taskTagHelper.removeAllTags(task);
                    }

                    for (Integer position: checked) {
                        Tag tag = (Tag) tagAdapter.getItem(position);
                        taskTagHelper.add(task, tag);
                    }

                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.ask_fill), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        checked.clear();
        Integer id = getIntent().getIntExtra("taskId", -1);

        if (id != -1) {
            editing = true;
            task = taskHelper.get(id);

            edtTaskTitle.setText(task.getTitle());
            edtTaskDescription.setText(task.getDescription());
            barTaskDifficulty.setProgress(task.getDifficulty());
            spnTaskState.setSelection(spnAdapter.getPosition(task.getState()));

            tagAdapter.swapCursor(taskTagHelper.getAllTags(task));
        } else {
            task = new Task();
        }
        super.onResume();
    }
}
