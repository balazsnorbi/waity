package ro.itec.waity.order.view;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ro.itec.waity.R;
import ro.itec.waity.bl.persistence.order.Order2;
import ro.itec.waity.order.OrdersMVP;
import ro.itec.waity.order.model.OrderModel;
import ro.itec.waity.order.presenters.OrdersPresenter;
import ro.itec.waity.order.view.adapters.OrdersRecyclerViewAdapter;
import ro.itec.waity.order.view.listeners.OnOrderClickListener;

public class OrdersFragment extends Fragment implements OrdersMVP.RequiredViewOps, OnOrderClickListener {

    @BindView(R.id.pb_orders_progressBar)
    ProgressBar progressBar;
    @BindView(R.id.tv_orders_no_orders)
    TextView tvNoOrders;

    private RecyclerView itemsRecyclerView;
    private OrdersMVP.ProvidedPresenterOps presenter;
    private List<Order2> orders;
    private OrdersRecyclerViewAdapter ordersAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_orders, container, false);
        ButterKnife.bind(this, view);
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
                new ListSpacingDecoration(getContext(), R.dimen.item_offset_2));
        itemsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        orders = new LinkedList<>();
        ordersAdapter = new OrdersRecyclerViewAdapter(orders, this);
        itemsRecyclerView.setAdapter(ordersAdapter);
    }

    @OnClick(R.id.fb_orders_bill)
    void onBillClick() {
        presenter.makeBill();
    }

    @Override
    public void addOrders(List<Order2> orders) {
        this.orders.addAll(orders);
        if (orders.size() == 0) {
            tvNoOrders.setVisibility(View.VISIBLE);
        } else {
            tvNoOrders.setVisibility(View.INVISIBLE);
        }
        ordersAdapter.notifyDataSetChanged();
    }

    @Override
    public void showLoader() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoader() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showBillDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setPositiveButton("PAY", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                orders.clear();
                ordersAdapter.notifyDataSetChanged();
            }
        });
        builder.setTitle("Bill");
        builder.setMessage("Bill will arrive soon.");
        // Create the AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void updateOrder(int position) {
        ordersAdapter.notifyItemChanged(position);
    }

    @Override
    public void onOrderClick(Order2 order, int position) {
        presenter.onOrderClick(order, position);
    }
}
