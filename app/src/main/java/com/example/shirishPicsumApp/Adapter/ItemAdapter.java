package com.example.shirishPicsumApp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shirishPicsumApp.Models.Items;
import com.example.shirishPicsumApp.R;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemHplder> {
    Context context;
    List<Items> itemsList;

    public ItemAdapter(Context context, List<Items> itemsList) {
        this.context = context;
        this.itemsList = itemsList;
    }

    @NonNull
    @Override
    public ItemHplder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_list,parent,false);
        return  new ItemHplder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHplder holder, int position) {

        Items items=itemsList.get(position);

        holder.textView.setText(items.getAuthor());
        Glide.with(context)
                .load("https://picsum.photos/300/300?image="+items.getId())
        .placeholder(R.drawable.loading1)
        .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    public class ItemHplder extends  RecyclerView.ViewHolder{

        ImageView imageView;
        TextView textView;

        public ItemHplder(@NonNull View itemView) {
            super(itemView);

           imageView=itemView.findViewById(R.id.img);
           textView=itemView.findViewById(R.id.img_title);

        }
    }
}
