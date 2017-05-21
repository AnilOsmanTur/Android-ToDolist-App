package app.anilosman.com.todolist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import data.Constants;
import data.DataBaseHandler;
import model.Todo;

public class TodoDetailsActivity extends AppCompatActivity {

    private TextView title, date, detail;
    private Button deleteButton;
    private int todoID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_details);

        title = (TextView) findViewById(R.id.todoTitleDetail);
        detail = (TextView) findViewById(R.id.todoDetail_Detail);
        date = (TextView) findViewById(R.id.alarmDateDetail);
        deleteButton = (Button) findViewById(R.id.deleteButtonDetail);

        Todo todo = (Todo) getIntent().getSerializableExtra(Constants.CHOSEN_OBJ);

        title.setText(todo.getTodoTitle());
        detail.setText(todo.getTodoDetail());
        date.setText("Created at: " + todo.getTodoDate());

        todoID = todo.getItemID();

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteTodo();
            }
        });

    }

    private void deleteTodo() {
        DataBaseHandler dba = new DataBaseHandler(getApplicationContext());
        dba.deleteTodo(todoID);

        Toast.makeText(getApplicationContext(), "To do item Deleted!", Toast.LENGTH_SHORT).show();

        dba.close();
        TodoDetailsActivity.this.finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_todo_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_delete) {
            deleteTodo();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
