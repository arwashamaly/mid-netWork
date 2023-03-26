package com.example.arwa_shamaly_mid;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.arwa_shamaly_mid.databinding.OrderItemBinding;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.orderViewHolder> {
    ArrayList<Data> dataArrayList;
    Context context;

    public Adapter(ArrayList<Data> dataArrayList, Context context) {
        this.dataArrayList = dataArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public orderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        OrderItemBinding binding = OrderItemBinding.inflate(LayoutInflater.from(parent.getContext())
                ,parent,false);
        return new orderViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull orderViewHolder holder, int position) {
        Data data= dataArrayList.get(position);
        User user = data.getUser();
        ArrayList<PhotoOrder> photoOrder=data.getPhotoOrderArrayList();

        holder.tv_phone_num.setText(user.getPhone());
        holder.tv_details.setText(user.getDetails());
         String photo = photoOrder.get(position).getPhoto();
        Glide.with(context).load(photo).into(holder.img);
    }

    @Override
    public int getItemCount() {
        return dataArrayList.size();
    }

    class orderViewHolder extends RecyclerView.ViewHolder {
        TextView tv_details , tv_phone_num;
        ImageView img;
        public orderViewHolder(@NonNull OrderItemBinding binding) {
            super(binding.getRoot());
            tv_details = binding.tvDetails;
            tv_phone_num=binding.tvPhoneNum;
            img=binding.img;
        }
    }
}
