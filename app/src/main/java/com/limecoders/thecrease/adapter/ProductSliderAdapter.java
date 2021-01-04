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
import com.github.islamkhsh.CardSliderAdapter;
import com.github.islamkhsh.CardSliderViewPager;
import com.limecoders.thecrease.R;
import com.limecoders.thecrease.activities.ProductDetailActivity;
import com.limecoders.thecrease.models.NewsModel;
import com.limecoders.thecrease.models.ProductModel;

import java.util.List;

public class ProductSliderAdapter extends CardSliderAdapter<ProductSliderAdapter.ViewHolder> {

    private Context context;
    private List<ProductModel> models;

    public ProductSliderAdapter(Context context, List<ProductModel> models){
        this.context = context;
        this.models = models;
    }

    @Override
    public void bindVH(ProductSliderAdapter.ViewHolder holder, int i) {
        ProductModel model = models.get(i);

        holder.proName.setText(model.getProName());
        holder.proPrice.setText(model.getProPrice());
        holder.proCategory.setText(model.getProCategory());
        holder.proModel.setText(model.getProModel());

        if(model.getProImage().equals("")){
            holder.proImage.setImageResource(R.drawable.dummy_image);
        }else {
            Glide.with(context).load(model.getProImage()).into(holder.proImage);
        }

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

    @NonNull
    @Override
    public ProductSliderAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.store_recyclerview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView proName, proCategory, proModel, proQuantity, proPrice;
        private ImageView proImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            proName = itemView.findViewById(R.id.proName);
            proCategory = itemView.findViewById(R.id.proCategory);
            proModel = itemView.findViewById(R.id.proModel);
            proQuantity = itemView.findViewById(R.id.proQuantity);
            proPrice = itemView.findViewById(R.id.proPrice);
            proImage = itemView.findViewById(R.id.proImage);

        }
    }
}
