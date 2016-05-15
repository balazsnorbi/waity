package ro.itec.waity.bl.persistence.order;

import java.util.List;

/**
 * Manager for product Orders
 */
public enum OrderMgr {
    INSTANCE;

    public final void addOrder(Integer orderId, Integer productId, Integer quantity, String extra, String description, String price, OrderState orderState) {
        Order order = new Order(orderId, productId, quantity, extra, description, price, orderState);
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
        Order order = Order.find(Order.class, "orderID = ?", "" + orderID).get(0);
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
    public final List<Order> getOrdersList() {
        return Order.listAll(Order.class);
    }
}
