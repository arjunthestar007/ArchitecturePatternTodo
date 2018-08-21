package com.example.arjun27.architecturepatterntodo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String APP_TAG = "com.example.mvc";

    private ListView taskView;
    private Button btNewTask;
    private EditText etNewTask;

    /*Controller changes are transparent to the View. UI changes won't
     *affect logic, and vice-versa. See below: the TodoModel has
     * been replaced with the TodoController, and the View persists
     * without knowledge that the implementation has changed.
     */
    private TodoController provider;

    private final View.OnClickListener handleNewTaskEvent = new View.OnClickListener()
    {
        @Override
        public void onClick(final View view)
        {
            Log.d(APP_TAG, "add task click received");

            MainActivity.this.provider.addTask(MainActivity.this
                    .etNewTask
                    .getText()
                    .toString());

            MainActivity.this.renderTodos();
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.provider = new TodoController(this);
        this.taskView = (ListView) this.findViewById(R.id.tasklist);
        this.btNewTask = (Button) this.findViewById(R.id.btNewTask);
        this.etNewTask = (EditText) this.findViewById(R.id.etNewTask);
        this.btNewTask.setOnClickListener(this.handleNewTaskEvent);

        this.renderTodos();
    }

    private void renderTodos()
    {
        final List<String> beans = this.provider.getTasks();

        Log.d(MainActivity.APP_TAG, String.format("%d beans found", beans.size()));

        this.taskView.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                beans.toArray(new String[]
                        {})));

        this.taskView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(final AdapterView<?> parent, final View view, final int position, final long id)
            {
                Log.d(MainActivity.APP_TAG, String.format("item with id: %d and position: %d", id, position));

                final TextView v = (TextView) view;
                MainActivity.this.provider.deleteTask(v.getText().toString());
                MainActivity.this.renderTodos();
            }
        });
    }
}
