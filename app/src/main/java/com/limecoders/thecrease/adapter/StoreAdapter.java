package com.limecoders.thecrease.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.limecoders.thecrease.R;
import com.limecoders.thecrease.activities.ProductDetailActivity;
import com.limecoders.thecrease.models.ProductModel;

import java.util.List;

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.ViewHolder> {

    private Context context;
    private List<ProductModel> productModels;
    private int proQuantity=1;

    public StoreAdapter(Context context, List<ProductModel> productModels){
        this.context = context;
        this.productModels = productModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.product_itemview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        ProductModel model = productModels.get(position);

        holder.title.setText(model.getProName());
        holder.category.setText(model.getProCategory());
        holder.price.setText(model.getProPrice());
        holder.model.setText(model.getProModel());

        if(model.getProImage().equals("")){
            holder.proImage.setImageResource(R.drawable.dummy_image);
        }else {
            Glide.with(context).load(model.getProImage()).into(holder.proImage);
        }

        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                proQuantity = proQuantity +1;
                holder.quantitiy.setText(String.valueOf(proQuantity));
            }
        });

        holder.neg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(proQuantity>1){
                    proQuantity = proQuantity - 1;
                    holder.quantitiy.setText(String.valueOf(proQuantity));
                }
            }
        });

        holder.proMore.setVisibility(View.GONE);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.startActivity(new Intent(context, ProductDetailActivity.class).
                        putExtra("name",model.getProName()).putExtra("title",model.getProCategory())
                .putExtra("image",model.getProImage()).putExtra("price",model.getProPrice())
                .putExtra("model",model.getProModel()));
            }
        });

    }

    @Override
    public int getItemCount() {
        return productModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView proImage, proMore;
        private TextView category, title, quantitiy, add, neg, price, model;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            proMore = itemView.findViewById(R.id.proMore);
            proImage = itemView.findViewById(R.id.proImage);
            category = itemView.findViewById(R.id.proCategory);
            title = itemView.findViewById(R.id.proName);
            quantitiy = itemView.findViewById(R.id.proQuantity);
            add = itemView.findViewById(R.id.plusCounter);
            neg = itemView.findViewById(R.id.negCounter);
            price = itemView.findViewById(R.id.proPrice);
            model = itemView.findViewById(R.id.proModel);


        }
    }
}
