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

import org.w3c.dom.Text;

import java.util.ArrayList;

public class PostRecyclerAdapter extends RecyclerView.Adapter<PostRecyclerAdapter.MyViewHolder> {

    private ArrayList<PostModel> postModelsList;
    private itemClickListener mlistener;

    public PostRecyclerAdapter(ArrayList<PostModel> postModelsList , itemClickListener mlistener) {
        this.postModelsList = postModelsList;
        this.mlistener = mlistener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView sourceName , postName;
        ImageView postImage;
        itemClickListener listener;
        public MyViewHolder(@NonNull View itemView , itemClickListener  listener) {
            super(itemView);

            sourceName = itemView.findViewById(R.id.webName);
            postName = itemView.findViewById(R.id.postText);
            postImage = itemView.findViewById(R.id.postImage);
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
    public PostRecyclerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.post_view , viewGroup , false);

        return new MyViewHolder(view , mlistener);
    }

    @Override
    public void onBindViewHolder(@NonNull PostRecyclerAdapter.MyViewHolder myViewHolder, int i) {
        myViewHolder.sourceName.setText(postModelsList.get(i).getWeb_name());
        myViewHolder.postName.setText(postModelsList.get(i).getPost_name());
        urlImage(myViewHolder , postModelsList.get(i).getImage());
    }

    private void urlImage(MyViewHolder myViewHolder, String image) {

        if(image == null)
        {

            return;
        }

        Picasso.get().load(image).placeholder(R.drawable.placeholder).error(R.drawable.error)
                .into(myViewHolder.postImage);
    }

    @Override
    public int getItemCount() {
        return postModelsList.size();
    }

    public interface itemClickListener {
        void onItemClick(int position);
    }


}
