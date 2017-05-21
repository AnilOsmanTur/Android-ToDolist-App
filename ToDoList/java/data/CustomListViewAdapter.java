package data;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import app.anilosman.com.todolist.R;
import app.anilosman.com.todolist.TodoDetailsActivity;
import model.Todo;

/**
 * Created by anilosman on 13.05.2017.
 */

public class CustomListViewAdapter extends ArrayAdapter<Todo> {

    private int layoutResource;
    private Activity activity;
    private ArrayList<Todo> todoList = new ArrayList<>();

    public CustomListViewAdapter(Activity act, int resource, ArrayList<Todo> data) {
        super(act, resource, data);
        this.layoutResource = resource;
        this.activity = act;
        this.todoList = data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return todoList.size();
    }

    @Nullable
    @Override
    public Todo getItem(int position) {
        return todoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View row = convertView;
        ViewHolder holder = null;

        if ( row == null || (row.getTag() == null) ) {

            LayoutInflater inflater = LayoutInflater.from(activity);
            row = inflater.inflate(layoutResource, null);

            holder = new ViewHolder();

            holder.todoTitle = (TextView) row.findViewById(R.id.todoTitle);
            holder.todoDetail = (TextView) row.findViewById(R.id.todoDetail);
            holder.todoDate = (TextView) row.findViewById(R.id.todoDate);

            row.setTag(holder);

        } else {
            holder = (ViewHolder) row.getTag();
        }

        holder.todo = getItem(position);

        holder.todoTitle.setText(holder.todo.getTodoTitle());
        holder.todoDetail.setText(holder.todo.getTodoDetail());
        holder.todoDate.setText("Created at: " + holder.todo.getTodoDate());

        final ViewHolder finalHolder = holder;
        row.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(activity, TodoDetailsActivity.class);

                Bundle mBundle = new Bundle();
                mBundle.putSerializable(Constants.CHOSEN_OBJ, finalHolder.todo);
                i.putExtras(mBundle);

                activity.startActivity(i);

            }
        });

        return row;
    }

    public class ViewHolder {
        Todo todo;
        TextView todoTitle;
        TextView todoDetail;
        TextView todoDate;
    }

}
