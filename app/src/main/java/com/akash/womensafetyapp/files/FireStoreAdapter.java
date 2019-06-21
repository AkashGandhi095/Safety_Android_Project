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

public class FireStoreAdapter extends RecyclerView.Adapter<FireStoreAdapter.MyViewHolder> {

    private ArrayList<MainViewModel> dataModel;
    private itemClickListener mlistener;
    public FireStoreAdapter(ArrayList<MainViewModel> dataModel , itemClickListener mlistener) {
        this.dataModel = dataModel;
        this.mlistener = mlistener;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView myText;
        ImageView myImage;
        itemClickListener listener;
        public MyViewHolder(@NonNull View itemView , itemClickListener listener) {
            super(itemView);

            myText = itemView.findViewById(R.id.equipText);
            myImage = itemView.findViewById(R.id.EqipImage);
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
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.equipview , viewGroup , false);

        return new MyViewHolder(view , mlistener);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.myText.setText(dataModel.get(i).getName());
        convertImage(myViewHolder ,dataModel.get(i).getMainImage());
    }

    private void convertImage(MyViewHolder myViewHolder, String mainImage) {
        if(mainImage == null)
        {
            return;
        }

        Picasso.get().load(mainImage).placeholder(R.drawable.placeholder).error(R.drawable.error)
                .into(myViewHolder.myImage);
    }

    @Override
    public int getItemCount() {
        return dataModel.size();
    }


    public interface itemClickListener {
        void onItemClick(int position);
    }
}
