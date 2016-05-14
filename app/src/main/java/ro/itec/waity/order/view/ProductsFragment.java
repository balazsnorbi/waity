package ro.itec.waity.order.view;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ro.itec.waity.R;
import ro.itec.waity.api.model.Category;
import ro.itec.waity.api.model.Produse;
import ro.itec.waity.order.OrderMVP;
import ro.itec.waity.order.model.OrderModel;
import ro.itec.waity.order.presenters.OrderCategoryPresenter;
import ro.itec.waity.order.view.adapters.CategoriesRecyclerViewAdapter;
import ro.itec.waity.order.view.adapters.ProductsRecyclerViewAdapter;
import ro.itec.waity.order.view.listeners.OnAddProductListener;
import ro.itec.waity.order.view.listeners.OnBackPressedListener;
import ro.itec.waity.order.view.listeners.OnProductAddListener;
import ro.itec.waity.order.view.listeners.OnProductClickListener;

public class ProductsFragment extends Fragment
        implements OrderMVP.RequiredViewOps, OnProductClickListener, OnProductAddListener,
        OnAddProductListener, OnBackPressedListener {
    private static final String TAG = ProductsFragment.class.getSimpleName();

    @BindView(R.id.pb_products_progressBar)
    ProgressBar progressBar;
    @BindView(R.id.fb_product_checkout)
    FloatingActionButton fbCheckout;

    private OrderMVP.ProvidedPresenterOps presenter;

    private RecyclerView itemsRecyclerView;

    private List<Category> categories;
    private CategoriesRecyclerViewAdapter categoriesAdapter;

    private LinkedList<Produse> products;
    private ProductsRecyclerViewAdapter productsAdapter;
    private boolean isProductsPerspective = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_products, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new OrderCategoryPresenter(this, new OrderModel());

        initCategories(view);
    }

    private void initCategories(View view) {
        itemsRecyclerView = (RecyclerView) view.findViewById(R.id.rv_products_list);

        itemsRecyclerView.addItemDecoration(
                new ListSpacingDecoration(getContext(), R.dimen.item_offset));

        switchToCategoryPerspective();
    }

    @OnClick(R.id.fb_product_checkout)
    void onCheckoutClick() {
        presenter.checkoutTempOrder();
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.postDelayed(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.INVISIBLE);
            }
        }, 1000);

    }

    @Override
    public void addCategory(Category category) {
        this.categories.add(category);
        categoriesAdapter.notifyItemInserted(categories.size());
    }

    @Override
    public void addProduct(Produse product) {
        this.products.add(product);
        productsAdapter.notifyItemInserted(products.size());
    }

    private void switchToCategoryPerspective() {
        int columnsPerLine = 2;
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            columnsPerLine = 2;
        } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            columnsPerLine = 3;
        }

        itemsRecyclerView.setLayoutManager(
                new StaggeredGridLayoutManager(columnsPerLine, LinearLayoutManager.VERTICAL));

        categories = new LinkedList<>();
        categoriesAdapter = new CategoriesRecyclerViewAdapter(categories, this, getContext());
        itemsRecyclerView.setAdapter(categoriesAdapter);

        presenter.fetchCategories();
    }

    @Override
    public void switchToProductsPerspective() {
        Log.i(TAG, "switchToProductsPerspective: ");

        isProductsPerspective = true;

        itemsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        products = new LinkedList<>();
        productsAdapter = new ProductsRecyclerViewAdapter(products, this, getContext());
        itemsRecyclerView.setAdapter(productsAdapter);
        itemsRecyclerView.requestLayout();
    }

    @Override
    public boolean doBack() {
        if (isProductsPerspective) {
            isProductsPerspective = false;
            switchToCategoryPerspective();
            return true;
        }
        return false;
    }


    @Override
    public void showFloatingCheckout() {
        fbCheckout.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(Category category) {
        Log.i(TAG, "onClick: " + category.getDescription());
        presenter.fetchProductsForCategory(category);
    }

    @Override
    public void onProductAdd(Produse product) {
        // Create the fragment and show it as a dialog.
        AddProductDialogFragment newFragment = AddProductDialogFragment.newInstance();
        newFragment.setOnAddProductListener(this);
        newFragment.setProduct(product);
        newFragment.show(getFragmentManager(), "dialog");
    }

    @Override
    public void addProductOrder(Produse product, Integer quantity, String extra) {
        presenter.addTempProductOrder(product, quantity, extra);
    }

    public static class AddProductDialogFragment extends DialogFragment {

        private OnAddProductListener listener;

        private Produse product;

        @BindView(R.id.iv_dialog_product_add_picture)
        ImageView ivPicture;
        @BindView(R.id.tv_dialog_product_add_quantity)
        TextView tvQuantity;
        @BindView(R.id.et_dialog_product_add_quantity)
        EditText etQuantity;
        @BindView(R.id.et_dialog_product_add_extra)
        EditText etExtra;
        @BindView(R.id.iv_dialog_product_add_proceed)
        ImageView ivProceed;
        @BindView(R.id.iv_dialog_product_add_cancel)
        ImageView ivCancel;

        static AddProductDialogFragment newInstance() {
            return new AddProductDialogFragment();
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.fragment_add_product_dialog, container, false);
            ButterKnife.bind(this, v);
            return v;
        }

        @Override
        public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            Glide.with(getContext()).load(R.drawable.burger).into(ivPicture);
            Glide.with(getContext()).load(R.drawable.ic_remove_shopping_cart).into(ivCancel);
            Glide.with(getContext()).load(R.drawable.ic_check).into(ivProceed);
        }

        @OnClick(R.id.iv_dialog_product_add_cancel)
        void onCancelClick() {
            dismiss();
        }

        @OnClick(R.id.iv_dialog_product_add_proceed)
        void onProceedClick() {
            listener.addProductOrder(product, Integer.valueOf(etQuantity.getText().toString()),
                    etExtra.getText().toString());
            dismiss();
        }

        public void setProduct(Produse product) {
            this.product = product;
        }

        public void setOnAddProductListener(OnAddProductListener onAddProductListener) {
            this.listener = onAddProductListener;
        }

    }

}
