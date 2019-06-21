package com.akash.womensafetyapp.files;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.akash.womensafetyapp.R;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    private ArrayList<DetailsModel> ContactLists;
    private WeakReference<ClickListener> listener;

    public RecyclerAdapter(ArrayList<DetailsModel> ContactLists , ClickListener listener)
    {
        this.ContactLists = ContactLists;
        this.listener = new WeakReference<>(listener);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Name , PhoneNo;
        Button CallButton;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            Name = itemView.findViewById(R.id.name);
            PhoneNo = itemView.findViewById(R.id.userPhoneNo);
            CallButton = itemView.findViewById(R.id.call);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.contactview , viewGroup, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i) {
        myViewHolder.Name.setText(ContactLists.get(i).getName());
        myViewHolder.PhoneNo.setText(ContactLists.get(i).getPhoneNo());
        myViewHolder.CallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.get().onPositionClicked(myViewHolder.getAdapterPosition());
            }
        });
    }

    public void DeleteItem(int pos)
    {
        ContactLists.remove(pos);
        notifyItemRemoved(pos);
    }
    @Override
    public int getItemCount() {
        return ContactLists.size();
    }




}
