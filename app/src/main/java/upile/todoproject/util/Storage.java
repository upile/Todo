package upile.todoproject.util;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

import upile.todoproject.todo.model.TodoItem;

public class Storage {

    private Context context;

    public Storage(Context context) {
        this.context = context;
    }

    public void saveItemsToFile(ArrayList<TodoItem> todoItems) {
        String path = context.getFilesDir().getAbsolutePath();
        Gson gson = new Gson();
        String jsonData = gson.toJson(todoItems);
        try {
            FileUtils.write(new File(path + "/todoJsonTasks.txt"), jsonData, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<TodoItem> getItemsFromFile() {
        String path = context.getFilesDir().getAbsolutePath();
        String data = "";
        ArrayList<TodoItem> items = new ArrayList<>();
        try {
            data = FileUtils.readFileToString(new File(path + "/todoJsonTasks.txt"), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (!data.isEmpty()) {
            Gson gson = new Gson();
            Type listType = new TypeToken<ArrayList<TodoItem>>() {
            }.getType();
            items = gson.fromJson(data, listType);
        }
        return items;
    }

}
