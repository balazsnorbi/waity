package ro.itec.waity.order.view.listeners;

import ro.itec.waity.bl.persistence.order.Order2;

public interface OnOrderClickListener {
    void onOrderClick(Order2 order, int position);
}
