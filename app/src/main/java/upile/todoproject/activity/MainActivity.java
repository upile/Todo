package upile.todoproject.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import upile.todoproject.R;
import upile.todoproject.todo.fragment.TodoFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, TodoFragment.newInstance())
                    .commitNow();
        }
    }
}
