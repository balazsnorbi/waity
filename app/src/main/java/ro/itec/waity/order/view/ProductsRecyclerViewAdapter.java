package ro.itec.waity.order.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import ro.itec.waity.R;
import ro.itec.waity.api.model.Category;

import static android.view.LayoutInflater.from;

public class ProductsRecyclerViewAdapter
        extends RecyclerView.Adapter<ProductsRecyclerViewAdapter.ProductViewHolder>{

    private final List<Category> categories;
    private final Context context;

    public ProductsRecyclerViewAdapter(List<Category> categories, Context context) {
        this.categories = categories;
        this.context = context;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_item_view, parent, false);
        ProductViewHolder viewHolder = new ProductViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        Glide.with(context)
                .load(categories.get(position).getImageSrcId())
                .placeholder(R.color.colorPrimary)
                .into(holder.ivLogoImage);
        holder.tvDescription.setText(categories.get(position).getDescription().trim());
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder{

        private final ImageView ivLogoImage;
        private final TextView tvDescription;

        public ProductViewHolder(View itemView) {
            super(itemView);

            this.ivLogoImage = (ImageView) itemView.findViewById(R.id.iv_category_logo);
            this.tvDescription = (TextView) itemView.findViewById(R.id.tv_category_description);
        }
    }

}
