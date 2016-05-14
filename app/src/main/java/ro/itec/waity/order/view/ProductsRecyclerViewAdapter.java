package ro.itec.waity.order.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.List;
import java.util.Random;

import ro.itec.waity.R;
import ro.itec.waity.api.model.Category;

public class ProductsRecyclerViewAdapter
        extends RecyclerView.Adapter<ProductsRecyclerViewAdapter.ProductViewHolder> {

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
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ProductViewHolder holder, int position) {
        Glide.with(context)
                //.load(categories.get(position).getImageSrcId())
                .load(getRandomDrawable())
                .asBitmap()
                .listener(new RequestListener<Integer, Bitmap>() {
                    @Override
                    public boolean onException(Exception e, Integer model, Target<Bitmap> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, Integer model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        Palette palette = Palette.from(resource).generate();
                        int default_value = 0xFFFFFF;
                        int vibrant = palette.getVibrantColor(default_value);
                        int muted = palette.getMutedColor(default_value);

                        if (vibrant != default_value) {
                            holder.viewDescriptionBackground.setBackgroundColor(vibrant);
                        } else if (muted != default_value){
                            holder.viewDescriptionBackground.setBackgroundColor(muted);
                        }
                        return false;
                    }
                })
                .into(holder.ivLogoImage);
        holder.tvDescription.setText(categories.get(position).getDescription().trim());
    }

    private int getRandomDrawable() {
        int minimum = 0;
        int maximum = 7;
        Random rand = new Random();
        int randomNum = minimum + rand.nextInt((maximum - minimum) + 1);
        switch (randomNum) {
            case 0:
                return R.drawable.banana;
            case 1:
                return R.drawable.food;
            case 2:
                return R.drawable.pasta;
            case 3:
                return R.drawable.pizza;
            case 4:
                return R.drawable.burger;
            case 5:
                return R.drawable.vegetables;
            case 6:
                return R.drawable.pasta2;
            case 7:
                return R.drawable.takito;
        }
        return R.drawable.logo_red;
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {

        private final ImageView ivLogoImage;
        private final TextView tvDescription;
        private final View viewDescriptionBackground;

        public ProductViewHolder(View itemView) {
            super(itemView);

            this.ivLogoImage = (ImageView) itemView.findViewById(R.id.iv_category_logo);
            this.tvDescription = (TextView) itemView.findViewById(R.id.tv_category_description);
            this.viewDescriptionBackground = itemView.findViewById(R.id.view_description_background);
        }
    }

}
