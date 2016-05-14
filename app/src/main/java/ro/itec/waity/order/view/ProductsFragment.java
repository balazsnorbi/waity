package ro.itec.waity.order.view;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ro.itec.waity.R;
import ro.itec.waity.api.Produse;
import ro.itec.waity.api.model.Category;
import ro.itec.waity.order.OrderMVP;
import ro.itec.waity.order.model.OrderModel;
import ro.itec.waity.order.presenters.OrderCategoryPresenter;

public class ProductsFragment extends Fragment implements OrderMVP.RequiredViewOps, OnProductClickListener {
    private static final String TAG = ProductsFragment.class.getSimpleName();

    @BindView(R.id.pb_products_progressBar)
    ProgressBar progressBar;

    private OrderMVP.ProvidedPresenterOps presenter;

    private RecyclerView itemsRecyclerView;

    private List<Category> categories;
    private CategoriesRecyclerViewAdapter categoriesAdapter;

    private LinkedList<Produse> products;
    private ProductsRecyclerViewAdapter productsAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_products, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new OrderCategoryPresenter(this, new OrderModel());

        initProducts(view);
    }

    private void initProducts(View view) {
         itemsRecyclerView = (RecyclerView) view.findViewById(R.id.rv_products_list);

        int columnsPerLine = 2;
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            columnsPerLine = 2;
        } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            columnsPerLine = 3;
        }

        itemsRecyclerView.setLayoutManager(
                new StaggeredGridLayoutManager(columnsPerLine, LinearLayoutManager.VERTICAL));
        itemsRecyclerView.addItemDecoration(
                new ListSpacingDecoration(getContext(), R.dimen.item_offset));

        categories = new LinkedList<>();
        categoriesAdapter = new CategoriesRecyclerViewAdapter(categories, this, getContext());
        itemsRecyclerView.setAdapter(categoriesAdapter);

        presenter.fetchCategories();
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
        }, 3000);

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

    @Override
    public void switchToProductsPerspective() {
        Log.i(TAG, "switchToProductsPerspective: ");
        products = new LinkedList<>();
        productsAdapter = new ProductsRecyclerViewAdapter(products, getContext());
        itemsRecyclerView.setAdapter(productsAdapter);
        itemsRecyclerView.requestLayout();
    }

    @Override
    public void onClick(Category category) {
        Log.i(TAG, "onClick: " + category.getDescription());
        presenter.fetchProductsForCategory(category);
    }
}
