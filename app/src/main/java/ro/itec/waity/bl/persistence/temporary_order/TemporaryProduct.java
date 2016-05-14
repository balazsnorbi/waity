package ro.itec.waity.bl.persistence.temporary_order;

import com.orm.SugarRecord;

/**
 * Created by Norbert on 5/14/2016.
 */
public class TemporaryProduct extends SugarRecord{
   Integer product_id;
   Integer quantity;
   String extra;

   public TemporaryProduct() {}

   public TemporaryProduct(Integer product_id, Integer quantity, String extra) {
      this.product_id = product_id;
      this.quantity = quantity;
      this.extra = extra;
   }
}
