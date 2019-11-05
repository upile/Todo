package upile.todoproject.todo.fragment;

import androidx.lifecycle.ViewModelProviders;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Locale;
import java.util.Objects;

import upile.todoproject.R;
import upile.todoproject.todo.adapter.TodoAdapter;
import upile.todoproject.todo.model.TodoItem;
import upile.todoproject.todo.viewmodel.TodoViewModel;
import upile.todoproject.util.Storage;

public class TodoFragment extends Fragment {

    private TodoViewModel todoViewModel;
    private View rootView;
    private TodoAdapter adapter;
    private FloatingActionButton fab;
    private View dialogView;
    private Dialog dialog;
    private Storage storage;
    private ProgressBar progressBar;
    private TextView progressPercentageTextView;
    private TextView progressItemsTextView;

    private CompoundButton.OnCheckedChangeListener itemCheckedListener = new CompoundButton.OnCheckedChangeListener() {
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            TodoItem oldItem = (TodoItem) buttonView.getTag();
            TodoItem newItem = new TodoItem(oldItem);
            newItem.setChecked(isChecked);
            todoViewModel.updateItem(oldItem, newItem);
            updateDataAndSave(true);

        }
    };
    private View.OnClickListener deleteItemOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            TodoItem itemToDelete = (TodoItem) view.getTag();
            todoViewModel.deleteItem(itemToDelete);
            updateDataAndSave(false);
        }
    };
    private View.OnClickListener closeClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            revealShow(dialogView, false, dialog);
        }
    };
    private DialogInterface.OnShowListener dialogOnShowListener = new DialogInterface.OnShowListener() {
        @Override
        public void onShow(DialogInterface dialogInterface) {
            revealShow(dialogView, true, null);
        }
    };
    private DialogInterface.OnKeyListener dialogOnKeyListener = new DialogInterface.OnKeyListener() {
        @Override
        public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
            if (i == KeyEvent.KEYCODE_BACK) {
                revealShow(dialogView, false, dialog);
                return true;
            }
            return false;
        }
    };
    private View.OnClickListener addTaskClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            TodoItem item = new TodoItem();
            String title, description;
            EditText titleEditText = dialogView.findViewById(R.id.todoTitle);
            EditText descriptionEditText = dialogView.findViewById(R.id.todoDescription);

            if (titleEditText != null && descriptionEditText != null) {
                title = titleEditText.getText().toString();
                description = descriptionEditText.getText().toString();
                if (!title.isEmpty() && !description.isEmpty()) {
                    item.setTodoItemTitle(title);
                    item.setTodoItemDescription(description);
                    todoViewModel.AddTaskItem(item);
                    revealShow(dialogView, false, dialog);
                    updateDataAndSave(false);
                }
            }
        }
    };

    public static TodoFragment newInstance() {
        return new TodoFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        this.rootView = inflater.inflate(R.layout.todo_fragment, container, false);

        fab = rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
        return this.rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        todoViewModel = ViewModelProviders.of(this).get(TodoViewModel.class);
        storage = new Storage(getContext());
        todoViewModel.setTodoItemArrayList(storage.getItemsFromFile());

        progressBar = rootView.findViewById(R.id.progressBar);
        progressBar.setMax(100);
        progressBar.setProgress(todoViewModel.getProgress());

        progressPercentageTextView = rootView.findViewById(R.id.progressPercentage);
        progressItemsTextView = rootView.findViewById(R.id.progressItems);

        RecyclerView recyclerView = this.rootView.findViewById(R.id.todoListRecyclerView);
        adapter = new TodoAdapter(this.getLayoutInflater(), storage.getItemsFromFile(), itemCheckedListener, deleteItemOnClickListener);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        updateProgressView();
    }

    private void showDialog() {
        dialogView = View.inflate(getContext(), R.layout.todo_add_item_dialog, null);
        dialog = new Dialog(Objects.requireNonNull(getContext()), R.style.MyAlertDialogStyle);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(dialogView);

        ImageView imageView = dialog.findViewById(R.id.closeDialogImg);
        Button addTaskButton = dialog.findViewById(R.id.addTaskButton);

        imageView.setOnClickListener(closeClickListener);
        addTaskButton.setOnClickListener(addTaskClickListener);
        dialog.setOnShowListener(dialogOnShowListener);
        dialog.setOnKeyListener(dialogOnKeyListener);

        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.show();
    }

    private void revealShow(View dialogView, boolean b, final Dialog dialog) {
        final View view = dialogView.findViewById(R.id.dialog);
        int w = view.getWidth();
        int h = view.getHeight();
        int endRadius = (int) Math.hypot(w, h);
        int cx = (int) (fab.getX() + (fab.getWidth() / 2));
        int cy = (int) (fab.getY()) + fab.getHeight() + 56;

        if (b) {
            Animator revealAnimator = ViewAnimationUtils.createCircularReveal(view, cx, cy, 0, endRadius);
            view.setVisibility(View.VISIBLE);
            revealAnimator.setDuration(700);
            revealAnimator.start();

        } else {
            Animator anim =
                    ViewAnimationUtils.createCircularReveal(view, cx, cy, endRadius, 0);
            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    dialog.dismiss();
                    view.setVisibility(View.INVISIBLE);
                }
            });
            anim.setDuration(700);
            anim.start();
        }
    }

    private void updateDataAndSave(boolean isCheckBoxComponent) {
        adapter.updateItemsList(todoViewModel.getTodoItemList());
        storage.saveItemsToFile(todoViewModel.getTodoItemList());
        progressBar.setProgress(todoViewModel.getProgress());
        updateProgressView();
        if (!isCheckBoxComponent) {
            adapter.notifyDataSetChanged();
        }
    }

    private void updateProgressView() {
        String percentage = String.format(Locale.ENGLISH, "%d", todoViewModel.getProgress()) + "%";
        String progressItems = String.format(Locale.ENGLISH, "%d of %d done",
                todoViewModel.getCountTickedItems(), todoViewModel.getTodoItemList().size());
        progressPercentageTextView.setText(percentage);
        progressItemsTextView.setText(progressItems);
    }


}
