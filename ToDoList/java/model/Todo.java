package model;

import java.io.Serializable;

/**
 * Created by anilosman on 12.05.2017.
 */

public class Todo implements Serializable{
    private static final long serialVersionUID = 10L;
    private String todoTitle;
    private String todoDetail;
    private String todoDate;
    private int itemID;

    public Todo(String todoTitle, String todoDetail, String todoDate, int itemID) {
        this.todoTitle = todoTitle;
        this.todoDetail = todoDetail;
        this.todoDate = todoDate;
        this.itemID = itemID;
    }

    public Todo () {

    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getTodoTitle() {
        return todoTitle;
    }

    public void setTodoTitle(String todoTitle) {
        this.todoTitle = todoTitle;
    }

    public String getTodoDetail() {
        return todoDetail;
    }

    public void setTodoDetail(String todoDetail) {
        this.todoDetail = todoDetail;
    }

    public String getTodoDate() {
        return todoDate;
    }

    public void setTodoDate(String todoDate) {
        this.todoDate = todoDate;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }
}
