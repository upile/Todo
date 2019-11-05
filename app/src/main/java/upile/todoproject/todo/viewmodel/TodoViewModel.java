package upile.todoproject.todo.viewmodel;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import upile.todoproject.todo.model.TodoItem;

public class TodoViewModel extends ViewModel {

    private ArrayList<TodoItem> todoItemArrayList = new ArrayList<>();

    public ArrayList<TodoItem> getTodoItemList() {
        return this.todoItemArrayList;
    }

    public void AddTaskItem(TodoItem todoItem) {
        this.todoItemArrayList.add(todoItem);
    }

    public void updateItem(TodoItem oldItem, TodoItem newItem) {
        this.todoItemArrayList.set(oldItem.getId(), newItem);
    }

    public void deleteItem(TodoItem deleteItem) {
        this.todoItemArrayList.remove(deleteItem.getId());
    }

    public void setTodoItemArrayList(ArrayList<TodoItem> todoItemArrayList) {
        this.todoItemArrayList = todoItemArrayList;
    }

    public int getProgress() {
        int count = getCountTickedItems();
        return this.todoItemArrayList.size() > 0 ?
                count * 100 / this.todoItemArrayList.size() : 0;
    }

    public int getCountTickedItems() {
        int count = 0;
        int progress;
        for (TodoItem item :
                this.todoItemArrayList) {
            if (item.isChecked()) {
                count++;
            }
        }
        return count;
    }
}
