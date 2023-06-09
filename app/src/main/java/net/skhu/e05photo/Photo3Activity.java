package net.skhu.e05photo;

import android.net.Uri;
import android.os.Bundle;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import net.skhu.e05photo.databinding.ActivityPhoto3Binding;
import java.io.File;

public class Photo3Activity extends AppCompatActivity {

    private ActivityPhoto3Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityPhoto3Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolBarLayout = binding.toolbarLayout;
        toolBarLayout.setTitle(getTitle());

        FloatingActionButton button = binding.btnTakePhoto;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        ImageView imageView = findViewById(R.id.imageView1);
        imageView.setVisibility(View.GONE);
        imageView.setOnClickListener((view) -> {
            button.setVisibility(View.VISIBLE);
            view.setVisibility(View.GONE);
        });

        File directory = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File[] files = directory.listFiles();

        FileRecyclerView2Adapter fileRecyclerView1Adapter = new FileRecyclerView2Adapter(this, files);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(fileRecyclerView1Adapter);

        OnBackPressedCallback backPressedCallback = new OnBackPressedCallback(false) {
            @Override
            public void handleOnBackPressed() {
                button.setVisibility(View.VISIBLE);
                imageView.setVisibility(View.GONE);
                this.setEnabled(false);
            }
        };
        getOnBackPressedDispatcher().addCallback(backPressedCallback);

        fileRecyclerView1Adapter.setOnFileClickListener((index, file) -> {
            button.setVisibility(View.INVISIBLE);
            imageView.setImageURI(Uri.fromFile(file));
            imageView.setVisibility(View.VISIBLE);
            backPressedCallback.setEnabled(true);
        });
    }
}

