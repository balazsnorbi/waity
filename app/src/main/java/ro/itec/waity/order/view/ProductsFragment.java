package ro.itec.waity.order.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.LinkedList;
import java.util.List;

import ro.itec.waity.R;
import ro.itec.waity.api.model.Category;
import ro.itec.waity.order.OrderMVP;
import ro.itec.waity.order.model.OrderModel;
import ro.itec.waity.order.presenters.OrderCategoryPresenter;

public class ProductsFragment extends Fragment implements OrderMVP.RequiredViewOps {

    private RecyclerView productsRecyclerView;

    private OrderMVP.ProvidedPresenterOps presenter;

    private List<Category> categories;
    private ProductsRecyclerViewAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_products, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        presenter = new OrderCategoryPresenter(this, new OrderModel());

        initProducts(view);
    }

    private void initProducts(View view) {
        productsRecyclerView = (RecyclerView) view.findViewById(R.id.rv_products_list);
        productsRecyclerView.addItemDecoration(new VerticalSpaceItemDecoration(30));

        categories = new LinkedList<>();
        productsRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        adapter = new ProductsRecyclerViewAdapter(categories, getContext());
        productsRecyclerView.setAdapter(adapter);

        presenter.fetchCategories();
    }

    @Override
    public void addCategories(List<Category> categories) {
        this.categories.addAll(categories);
        adapter.notifyDataSetChanged();
    }
}
