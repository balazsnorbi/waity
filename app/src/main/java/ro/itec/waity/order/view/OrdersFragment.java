package ro.itec.waity.order.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.LinkedList;
import java.util.List;

import ro.itec.waity.R;
import ro.itec.waity.bl.persistence.order.Order2;
import ro.itec.waity.order.OrdersMVP;
import ro.itec.waity.order.model.OrderModel;
import ro.itec.waity.order.presenters.OrdersPresenter;

public class OrdersFragment extends Fragment implements OrdersMVP.RequiredViewOps {

    private RecyclerView itemsRecyclerView;
    private OrdersMVP.ProvidedPresenterOps presenter;
    private List<Order2> orders;
    private OrdersRecyclerViewAdapter ordersAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_orders, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        presenter = new OrdersPresenter(this, new OrderModel());

        initOrders(view);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (orders != null) {
                orders.clear();
            }
            presenter.fetchOrders();
        }
    }

    private void initOrders(View view) {
        itemsRecyclerView = (RecyclerView) view.findViewById(R.id.rv_orders_list);

        itemsRecyclerView.addItemDecoration(
                new ListSpacingDecoration(getContext(), R.dimen.item_offset));
        itemsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        orders = new LinkedList<>();
        ordersAdapter = new OrdersRecyclerViewAdapter(orders, getContext());
        itemsRecyclerView.setAdapter(ordersAdapter);
    }


    @Override
    public void addOrders(List<Order2> orders) {
        this.orders.addAll(orders);
        ordersAdapter.notifyDataSetChanged();
    }

}
