package net.skhu.e05photo;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Photo1Activity extends AppCompatActivity {

    ImageView imageView;
    ActivityResultLauncher<Intent> galleryLauncher;
    ActivityResultLauncher<Intent> cameraLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo1);
        imageView = findViewById(R.id.imageView);

        galleryLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Intent intent = result.getData();
                            Uri imageUri = intent.getData();
                            Log.e("내태그", imageUri.toString());
                            imageView.setImageURI(imageUri);
                        }
                    }
                }
        );
        cameraLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            Log.e("내태그", Uri.parse(cameraFilePath).toString());
                            imageView.setImageURI(Uri.parse(cameraFilePath));
                        }
                    }
                }
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_photo1, menu);
        return true;
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_pick_from_gallery) {
            startActivity_pickFromGallery();
            return true;
        } else if (id == R.id.action_take_photo) {
            startActivity_takePhoto();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void startActivity_pickFromGallery() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        galleryLauncher.launch(intent);
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss_SSS").format(new Date());
        String imageFileName = "PHOTO" + timeStamp + ".jpg";
        File directory = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return new File(directory, imageFileName);
    }

    private String cameraFilePath;

    private void startActivity_takePhoto() {
        try {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            PackageManager pm = this.getPackageManager();
            if (pm.hasSystemFeature(PackageManager.FEATURE_CAMERA) == false) return;
            File file = createImageFile();
            cameraFilePath = "file://" + file.getAbsolutePath();
            String authorities = "net.skhu.e05photo.fileProvider";
            Uri cameraFileUri = FileProvider.getUriForFile(this, authorities, file);
            Log.e("내태그", cameraFileUri.toString());
            intent.putExtra(MediaStore.EXTRA_OUTPUT,  cameraFileUri);
            cameraLauncher.launch(intent);
        } catch (IOException ex) {
            Log.e("내태그", "에러", ex);
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

}

