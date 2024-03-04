package com.example.a707;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;

public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.MyViewHolder> {

    private Context mContext;
    static ArrayList<VideoFiles> videoFilesArrayList;

    View view;

    public VideoListAdapter(Context mContext, ArrayList<VideoFiles> videoFilesArrayList) {
        this.mContext = mContext;
        this.videoFilesArrayList = videoFilesArrayList;
        Log.i("INFO","Video list"+videoFilesArrayList);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(mContext).inflate(R.layout.video_list_card, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.fileName.setText(videoFilesArrayList.get(position).getTitle());

        Glide.with(mContext).load(new File(videoFilesArrayList.get(position).getPath())).into(holder.thumbnail);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, VideoPlayer.class);
                intent.putExtra("position", position);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return videoFilesArrayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView thumbnail, menuMore;
        TextView fileName, videoDuration;
        public MyViewHolder(@NonNull View itemView){
            super(itemView);
            thumbnail = itemView.findViewById(R.id.thumbnail);
            menuMore = itemView.findViewById(R.id.menu_more);
            fileName = itemView.findViewById(R.id.file_name);
            videoDuration = itemView.findViewById(R.id.video_duration);

        }

    }
}
