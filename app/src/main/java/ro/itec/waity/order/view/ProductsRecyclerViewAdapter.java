package ro.itec.waity.order.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.LinkedList;

import ro.itec.waity.R;
import ro.itec.waity.api.Produse;

public class ProductsRecyclerViewAdapter
        extends RecyclerView.Adapter<ProductsRecyclerViewAdapter.ProductViewHolder>{

    private final LinkedList<Produse> products;
    private final Context context;

    public ProductsRecyclerViewAdapter(LinkedList<Produse> products, Context context) {
        this.products = products;
        this.context = context;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_item_view, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        Glide.with(context)
                //.load(products.get(position).getImageSrcId()
                .load(R.drawable.burger)
                .into(holder.ivPicture);

        holder.tvDescription.setText(products.get(position).getDescription().trim());
        holder.tvPrice.setText(products.get(position).getPrice().trim());
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {

        private final ImageView ivPicture;
        private final TextView tvDescription;
        private final TextView tvPrice;

        public ProductViewHolder(View itemView) {
            super(itemView);

            this.ivPicture = (ImageView) itemView.findViewById(R.id.iv_product_picture);
            this.tvDescription = (TextView) itemView.findViewById(R.id.tv_product_description);
            this.tvPrice = (TextView) itemView.findViewById(R.id.tv_product_price);
        }
    }

}
