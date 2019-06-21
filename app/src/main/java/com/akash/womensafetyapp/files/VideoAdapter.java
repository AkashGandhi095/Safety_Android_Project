package com.akash.womensafetyapp.files;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.akash.womensafetyapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.MyViewHolder> {


    private ArrayList<VideoModel> videoList;
    private itemClickListener mlistener;

    public VideoAdapter(ArrayList<VideoModel> videoList , itemClickListener mlistener) {
        this.videoList = videoList;
        this.mlistener = mlistener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView video_image;
        TextView  video_name;
        itemClickListener listener;
        public MyViewHolder(@NonNull View itemView , itemClickListener listener) {
            super(itemView);
            video_image = itemView.findViewById(R.id.video_image);
            video_name = itemView.findViewById(R.id.video_name);
            this.listener = listener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            listener.onItemClick(getAdapterPosition());
        }
    }
    @NonNull
    @Override
    public VideoAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.video_view , viewGroup , false);

        return new MyViewHolder(view , mlistener);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoAdapter.MyViewHolder myViewHolder, int i) {
        myViewHolder.video_name.setText(videoList.get(i).getVideo_name());

        ConvertingImage(myViewHolder , videoList.get(i).getImage());

    }

    private void ConvertingImage(MyViewHolder myViewHolder, String image) {
        if(image == null)
        {

            return;
        }

        Picasso.get().load(image).into(myViewHolder.video_image);
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }


    public interface itemClickListener {
        void onItemClick(int position);
    }
}
