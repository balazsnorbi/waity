package ro.itec.waity.bl.persistence.order;

import java.util.List;

/**
 * Manager for product Orders
 */
public enum OrderMgr {
    INSTANCE;

    public final void addOrder(Integer orderId, Integer productId, Integer quantity, String extra, String description, String price, OrderState orderState) {
        Order2 order = new Order2(orderId, productId, quantity, extra, description, price, orderState);
        order.save();
    }

    /**
     * Allows you to modify the state of the selected order
     *
     * @param orderID  OrderID
     * @param newState New OrderState
     * @return boolean true - success false - failed
     */
    public final boolean modifyOrderState(Integer orderID, OrderState newState) {
        Order2 order = Order2.find(Order2.class, "orderId = ?", "" + orderID).get(0);
        boolean success = false;

        if (order != null) {
            order.orderState = newState;
            order.save();
            success = true;
        }

        return success;
    }

    /**
     * Returns all the active orders
     *
     * @return OrderList
     */
    public final List<Order2> getOrdersList() {
        return Order2.listAll(Order2.class);
    }

    public final void clear() {
        Order2.deleteAll(Order2.class);
    }
}
