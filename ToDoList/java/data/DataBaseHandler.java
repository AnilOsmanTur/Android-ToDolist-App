package data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import model.Todo;

/**
 * Created by anilosman on 12.05.2017.
 */

public class DataBaseHandler extends SQLiteOpenHelper {

    private final ArrayList<Todo> TodoList = new ArrayList<>();

    public DataBaseHandler(Context context) {
        super(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Creating tables
        String CREATE_TODO_TABLE = "CREATE TABLE " + Constants.TABLE_NAME + "("
                + Constants.KEY_ID + " INTEGER PRIMARY KEY, "
                + Constants.TODO_TITLE + " TEXT, "
                + Constants.TODO_DETAIL + " TEXT, "
                + Constants.TODO_DATE + " LONG);";

        db.execSQL(CREATE_TODO_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // drop old one
        db.execSQL("DROP TABLE IF EXITS" + Constants.TABLE_NAME);

        // create new
        onCreate(db);

    }

    // delete a to-do
    public void deleteTodo(int id) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Constants.TABLE_NAME, Constants.KEY_ID + " = ? ",
                new String[]{ String.valueOf(id) });
        db.close();

    }

    //adding content to table
    public void addTodo( Todo todo ) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Constants.TODO_TITLE, todo.getTodoTitle());
        values.put(Constants.TODO_DETAIL, todo.getTodoDetail());
        values.put(Constants.TODO_DATE, System.currentTimeMillis());

        db.insert(Constants.TABLE_NAME, null, values);
        db.close();

    }

    //get all Todos
    public ArrayList<Todo> getTodos(){

        TodoList.clear();

        //String selectQuery = "SELECT * FROM " + Constants.TABLE_NAME;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Constants.TABLE_NAME, new String[]{Constants.KEY_ID,
                        Constants.TODO_TITLE, Constants.TODO_DETAIL, Constants.TODO_DATE},
                null, null, null, null, Constants.TODO_DATE + " ASC ");

        // loop through cursor
        if (cursor.moveToFirst()) {
            do {
                Todo todo = new Todo();
                todo.setTodoTitle(cursor.getString(cursor.getColumnIndex(Constants.TODO_TITLE)));
                todo.setTodoDetail(cursor.getString(cursor.getColumnIndex(Constants.TODO_DETAIL)));
                todo.setItemID(cursor.getInt(cursor.getColumnIndex(Constants.KEY_ID)));

                DateFormat dateFormat = DateFormat.getDateInstance();
                String dateData = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.TODO_DATE))).getTime());

                todo.setTodoDate(dateData);

                TodoList.add(todo);

            } while(cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return TodoList;
    }

}