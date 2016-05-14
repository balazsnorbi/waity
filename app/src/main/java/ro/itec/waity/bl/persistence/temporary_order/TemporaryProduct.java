package ro.itec.waity.bl.persistence.temporary_order;

import com.orm.SugarRecord;

public class TemporaryProduct extends SugarRecord{
   public Integer productId;
   public Integer quantity;
   public String extra;

   public TemporaryProduct() {}

   public TemporaryProduct(Integer product_id, Integer quantity, String extra) {
      this.productId = product_id;
      this.quantity = quantity;
      this.extra = extra;
   }
}
