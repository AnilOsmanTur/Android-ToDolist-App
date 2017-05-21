package app.anilosman.com.todolist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import data.DataBaseHandler;
import model.Todo;

public class AddTodoActivity extends AppCompatActivity {

    private EditText editTitle, editDetail;
    private Button addButton, cancelButton;
    private DataBaseHandler dba;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_todo);

        dba = new DataBaseHandler(getApplicationContext());

        editTitle = (EditText) findViewById(R.id.todoTitleEdit);
        editDetail = (EditText) findViewById(R.id.todoDetailEdit);
        addButton = (Button) findViewById(R.id.okButton);
        cancelButton = (Button) findViewById(R.id.cancelButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // database adding helper
                addToDB();
                AddTodoActivity.this.finish();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dba.close();
                AddTodoActivity.this.finish();
            }
        });


    }

    private void addToDB() {
        Todo todo = new Todo();
        todo.setTodoTitle(editTitle.getText().toString().trim());
        todo.setTodoDetail(editDetail.getText().toString().trim());

        dba.addTodo(todo);
        dba.close();

    }

}
