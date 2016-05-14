package ro.itec.waity.bl.persistence.temporary_order;

import com.orm.SugarRecord;

public class TemporaryProduct extends SugarRecord{
   public Integer productId;
   public Integer quantity;
   public String extra;
   public String description;

   public TemporaryProduct() {}

   public TemporaryProduct(Integer product_id, Integer quantity, String extra) {
      this.productId = product_id;
      this.quantity = quantity;
      this.extra = extra;
   }

   public TemporaryProduct(Integer product_id, Integer quantity, String extra, String description) {
      this.productId = product_id;
      this.quantity = quantity;
      this.extra = extra;
      this.description = description;
   }
}
