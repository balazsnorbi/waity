package ro.itec.waity.bl.persistence.order;

import com.orm.SugarRecord;

/**
 * Created by Norbert on 5/14/2016.
 */
public class Order extends SugarRecord{
   public Integer orderId;
   public Integer productId;
   public Integer quantity;
   public String extra;
   public String description;
   public int price;
   public OrderState orderState;

   public Order() {}

   public Order(Integer orderId, Integer productId, Integer quantity, String extra, String description, int price, OrderState orderState) {
      this.orderId = orderId;
      this.productId = productId;
      this.quantity = quantity;
      this.extra = extra;
      this.description = description;
      this.price = price;
      this.orderState = orderState;
   }
}
