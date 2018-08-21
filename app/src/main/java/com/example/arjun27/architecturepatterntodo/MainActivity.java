package com.example.arjun27.architecturepatterntodo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    public static interface TaskListManager {
        //Through this interface the event logic is
        //passed off to the ViewModel.
        void registerTaskList(ListView list);

        void registerTaskAdder(View button, EditText input);
    }

    public static final String APP_TAG = "com.example.mvvm";

    private ListView taskView;
    private Button btNewTask;
    private EditText etNewTask;
    private TaskListManager delegate;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        delegate = new TodoViewModel(this);
        taskView = (ListView) findViewById(R.id.tasklist);
        btNewTask = (Button) findViewById(R.id.btNewTask);
        etNewTask = (EditText) findViewById(R.id.etNewTask);
        delegate.registerTaskList(taskView);
        delegate.registerTaskAdder(btNewTask, etNewTask);

    }

}
