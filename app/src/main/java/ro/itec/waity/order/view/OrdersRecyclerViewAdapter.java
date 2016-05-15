package ro.itec.waity.order.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ro.itec.waity.R;
import ro.itec.waity.bl.persistence.order.Order;

public class OrdersRecyclerViewAdapter extends RecyclerView.Adapter<OrdersRecyclerViewAdapter.OrderViewHolder> {
    private final List<Order> orders;
    private final Context context;

    public OrdersRecyclerViewAdapter(List<Order> orders, Context context) {
        this.orders = orders;
        this.context = context;
    }

    @Override
    public OrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_item_view, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OrderViewHolder holder, int position) {
        holder.tvStatus.setText(orders.get(position).orderState.getLiteral());
        holder.tvOrderId.setText(orders.get(position).orderId);
        holder.tvDescription.setText(orders.get(position).description);
        holder.tvQuantity.setText(orders.get(position).quantity);
        holder.tvQuantity.setText(orders.get(position).price);
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public class OrderViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_order_status)
        TextView tvStatus;
        @BindView(R.id.tv_order_order_id)
        TextView tvOrderId;
        @BindView(R.id.tv_order_description)
        TextView tvDescription;
        @BindView(R.id.tv_order_quantity)
        TextView tvQuantity;
        @BindView(R.id.tv_order_price)
        TextView tvPrice;

        public OrderViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(itemView);
        }
    }
}
