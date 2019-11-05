package upile.todoproject.todo.viewmodel;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import upile.todoproject.todo.model.TodoItem;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class TodoViewModelTest {

    private TodoViewModel todoViewModel;
    private TodoItem item;

    @Before
    public void setUp() {
        todoViewModel = new TodoViewModel();
        item = new TodoItem();
        item.setChecked(true);
        item.setTodoItemTitle("Title");
        item.setTodoItemDescription("Description");
    }


    @Test
    public void addTaskItem() {
        todoViewModel = new TodoViewModel();
        todoViewModel.AddTaskItem(item);
        assertEquals("Title", todoViewModel.getTodoItemList().get(0).getTodoItemTitle());
    }

    @Test
    public void getTodoItemList() {
        todoViewModel = new TodoViewModel();
        todoViewModel.AddTaskItem(item);
        assertEquals(1, todoViewModel.getTodoItemList().size());
    }

    @Test
    public void updateItem() {
        todoViewModel = new TodoViewModel();
        todoViewModel.AddTaskItem(item);

        TodoItem newItem = new TodoItem(item);
        newItem.setChecked(false);

        todoViewModel.updateItem(item, newItem);
        assertFalse(todoViewModel.getTodoItemList().get(0).isChecked());
    }

    @Test
    public void deleteItem() {
        todoViewModel = new TodoViewModel();
        todoViewModel.AddTaskItem(item);
        todoViewModel.deleteItem(item);
        assertEquals(0, todoViewModel.getTodoItemList().size());
    }

    @Test
    public void setTodoItemArrayList() {
        todoViewModel = new TodoViewModel();
        ArrayList<TodoItem> items = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            TodoItem todoItem = new TodoItem(item);
            items.add(todoItem);
        }
        todoViewModel.setTodoItemArrayList(items);
        assertEquals(3, todoViewModel.getTodoItemList().size());
    }

    @Test
    public void getProgress() {
        todoViewModel = new TodoViewModel();
        item.setChecked(true);
        todoViewModel.AddTaskItem(item);
        assertEquals(100, todoViewModel.getProgress());
    }
}