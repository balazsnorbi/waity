package ro.itec.waity.bl.persistence.order;

import com.orm.SugarRecord;

/**
 * Created by Norbert on 5/14/2016.
 */
public class Order extends SugarRecord{
   public Integer orderId;
   public OrderState orderState;

   public Order() {}

   public Order(Integer orderId, OrderState orderState) {
      this.orderId = orderId;
      this.orderState = orderState;
   }
}
