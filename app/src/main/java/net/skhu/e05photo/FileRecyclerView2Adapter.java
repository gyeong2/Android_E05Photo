package net.skhu.e05photo;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileRecyclerView2Adapter extends RecyclerView.Adapter<FileRecyclerView2Adapter.ViewHolder> {
    static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textView1, textView2, textView3;
        ImageView imageView;

        public ViewHolder(View view) {
            super(view);
            textView1 = view.findViewById(R.id.textView1);
            textView2 = view.findViewById(R.id.textView2);
            textView3 = view.findViewById(R.id.textView3);
            imageView = view.findViewById(R.id.imageView);
            view.setOnClickListener(this);
        }

        public void setData(int index) {
            File file = files[index];
            textView1.setText(file.getName());
            textView2.setText(dateFormat.format(file.lastModified()));
            String s = String.format("%,d bytes", file.length());
            textView3.setText(s);
            imageView.setImageURI(Uri.fromFile(file));
        }

        @Override
        public void onClick(View v) {
            int index = getAdapterPosition();
            File file = files[index];
            if (clickListener != null) clickListener.onClick(index, file);
        }
    }

    LayoutInflater layoutInflater;
    File[] files;
    OnFileClickListener clickListener;

    public FileRecyclerView2Adapter(Context context, File[] files) {
        this.layoutInflater = LayoutInflater.from(context);
        this.files = files;
    }

    public void setOnFileClickListener(OnFileClickListener listener) {
        this.clickListener = listener;
    }

    @Override
    public int getItemCount() {
        return files.length;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = layoutInflater.inflate(R.layout.photo_file, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int index) {
        viewHolder.setData(index);
    }
}

