package ro.itec.waity.bl.persistence.order;

import java.util.List;

/**
 * Created by Norbert on 5/14/2016.
 *
 * Manager for product Orders
 */
public enum OrderMgr {
   INSTANCE;

   /**
    * Adds a new order to the DB
    * @param orderID OrderID
    * @param orderState OrderState
    */
   public final void addOrder(Integer orderID, OrderState orderState) {
      Order order = new Order(orderID, orderState);
      order.save();
   }

   /**
    * Allows you to modify the state of the selected order
    * @param orderID OrderID
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
         // TODO: remove orders when newState is Payed
      }

      return success;
   }

   /**
    * Returns all the activer orders
    * @return OrderList
    */
   public final List<Order> getOrdersList() {
      return Order.listAll(Order.class);
   }

   /**
    * Returns all the OrderedProducts for the givem orderID
    * @param orderID ID Of the Order
    * @return ProductList ordered
    */
   public final List<OrderedProduct> getOrderedProducts(int orderID) {
      return OrderedProduct.find(OrderedProduct.class, "order = ?", "" + orderID);
   }
}
