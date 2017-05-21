package app.anilosman.com.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import java.util.ArrayList;

import data.CustomListViewAdapter;
import data.DataBaseHandler;
import model.Todo;

public class MainActivity extends AppCompatActivity {

    private DataBaseHandler dba;
    private ArrayList<Todo> dbTodos = new ArrayList<>();
    private CustomListViewAdapter todoAdapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(MainActivity.this, AddTodoActivity.class);
                startActivity(i);

                Snackbar.make(view, "Moving to adding layout", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        listView = (ListView) findViewById(R.id.toDoList);

        refreshData();

    }

    // data refresher helper function
    private void refreshData() {
        dbTodos.clear();
        dba = new DataBaseHandler(getApplicationContext());
        ArrayList<Todo> todosFromDB = dba.getTodos();

        for (int i = 0; i < todosFromDB.size(); i++){
            String title = todosFromDB.get(i).getTodoTitle();
            String detail = todosFromDB.get(i).getTodoDetail();
            String date = todosFromDB.get(i).getTodoDate();
            int mID = todosFromDB.get(i).getItemID();

            Todo todo = new Todo();
            todo.setTodoTitle(title);
            todo.setTodoDetail(detail);
            todo.setTodoDate(date);
            todo.setItemID(mID);

            dbTodos.add(todo);
        }

        dba.close();

        //setup adapter
        todoAdapter = new CustomListViewAdapter(MainActivity.this, R.layout.todo_row, dbTodos);
        listView.setAdapter(todoAdapter);
        todoAdapter.notifyDataSetChanged();

    }


    @Override
    protected void onResume() {
        super.onResume();
        refreshData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add) {
            Intent i = new Intent(MainActivity.this, AddTodoActivity.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
