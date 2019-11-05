package upile.todoproject.todo.model;

public class TodoItem {

    private int Id;
    private String todoItemTitle;
    private String todoItemDescription;
    private boolean isChecked;

    public TodoItem() {
    }

    public TodoItem(TodoItem todoItem) {
        this.Id = todoItem.getId();
        this.todoItemDescription = todoItem.getTodoItemDescription();
        this.todoItemTitle = todoItem.getTodoItemTitle();
        this.isChecked = todoItem.isChecked();
    }

    public String getTodoItemTitle() {
        return todoItemTitle;
    }

    public void setTodoItemTitle(String todoItemTitle) {
        this.todoItemTitle = todoItemTitle;
    }

    public String getTodoItemDescription() {
        return todoItemDescription;
    }

    public void setTodoItemDescription(String todoItemDescription) {
        this.todoItemDescription = todoItemDescription;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
