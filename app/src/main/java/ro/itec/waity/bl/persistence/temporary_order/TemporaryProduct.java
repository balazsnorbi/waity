package ro.itec.waity.bl.persistence.temporary_order;

import com.orm.SugarRecord;

public class TemporaryProduct extends SugarRecord{
   public Integer productId;
   public Integer quantity;
   public String extra;
   public String description;
   public String price;

   public TemporaryProduct() {}

   public TemporaryProduct(Integer product_id, Integer quantity, String extra, String description, String price) {
      this.productId = product_id;
      this.quantity = quantity;
      this.extra = extra;
      this.description = description;
      this.price = price;
   }
}
