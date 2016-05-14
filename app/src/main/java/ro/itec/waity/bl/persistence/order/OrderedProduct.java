package ro.itec.waity.bl.persistence.order;

import com.orm.SugarRecord;

/**
 * Created by Norbert on 5/14/2016.
 */
public class OrderedProduct extends SugarRecord{
   public Integer productId;
   public Integer quantity;
   public String extra;

   public Order order;

   public OrderedProduct() {}

   public OrderedProduct(Integer productId, Integer quantity, String extra) {
      this.productId = productId;
      this.quantity = quantity;
      this.extra = extra;
   }
}
