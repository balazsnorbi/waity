package ro.itec.waity.order.view;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ro.itec.waity.R;
import ro.itec.waity.api.model.Category;
import ro.itec.waity.order.OrderMVP;
import ro.itec.waity.order.model.OrderModel;
import ro.itec.waity.order.presenters.OrderCategoryPresenter;

public class ProductsFragment extends Fragment implements OrderMVP.RequiredViewOps {

    @BindView(R.id.pb_products_progressBar)
    ProgressBar progressBar;

    private OrderMVP.ProvidedPresenterOps presenter;

    private List<Category> categories;
    private ProductsRecyclerViewAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_products, container, false);
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
        RecyclerView productsRecyclerView = (RecyclerView) view.findViewById(R.id.rv_products_list);

        int columnsPerLine = 2;
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            columnsPerLine = 2;
        } else if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            columnsPerLine = 3;
        }

        productsRecyclerView.setLayoutManager(
                new StaggeredGridLayoutManager(columnsPerLine, LinearLayoutManager.VERTICAL));
        productsRecyclerView.addItemDecoration(
                new ListSpacingDecoration(getContext(), R.dimen.item_offset));

        categories = new LinkedList<>();
        adapter = new ProductsRecyclerViewAdapter(categories, getContext());
        productsRecyclerView.setAdapter(adapter);

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
        adapter.notifyItemInserted(categories.size());
    }
}
