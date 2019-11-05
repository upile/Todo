package upile.todoproject.todo.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import upile.todoproject.R;
import upile.todoproject.todo.model.TodoItem;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.TodoItemHolder> {
    private final LayoutInflater layoutInflater;
    private List<TodoItem> todoItems;
    private CompoundButton.OnCheckedChangeListener itemCheckedListener;
    private View.OnClickListener deleteItemOnClickListener;

    public TodoAdapter(LayoutInflater layoutInflater, List<TodoItem> todoItems,
                       CompoundButton.OnCheckedChangeListener itemCheckedListener, View.OnClickListener deleteItemOnClickListener) {
        this.layoutInflater = layoutInflater;
        this.todoItems = todoItems;
        this.itemCheckedListener = itemCheckedListener;
        this.deleteItemOnClickListener = deleteItemOnClickListener;
    }

    public void updateItemsList(List<TodoItem> todoItems) {
        this.todoItems = todoItems;
    }

    @NonNull
    @Override
    public TodoItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TodoItemHolder(layoutInflater.inflate(R.layout.todo_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TodoItemHolder holder, int position) {
        todoItems.get(position).setId(position);
        holder.title.setText(this.todoItems.get(position).getTodoItemTitle());
        holder.description.setText(this.todoItems.get(position).getTodoItemDescription());
        holder.checkBox.setChecked(this.todoItems.get(position).isChecked());
        holder.checkBox.setTag(this.todoItems.get(position));
        holder.deleteIcon.setTag(this.todoItems.get(position));
        holder.checkBox.setOnCheckedChangeListener(itemCheckedListener);
        holder.deleteIcon.setOnClickListener(deleteItemOnClickListener);
    }


    @Override
    public int getItemCount() {
        return this.todoItems.size();
    }

    class TodoItemHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView description;
        private CheckBox checkBox;
        private ImageView deleteIcon;

        TodoItemHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.todoDisplayTitle);
            description = itemView.findViewById(R.id.todoDisplayDescription);
            checkBox = itemView.findViewById(R.id.checkBox);
            deleteIcon = itemView.findViewById(R.id.iconDelete);
        }
    }
}
