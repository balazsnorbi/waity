package ro.itec.waity.bl.persistence.temporary_order;

import java.util.List;

/**
 * Created by Norbert on 5/14/2016.
 *
 * Manages operations related to the Temporary Order List
 * Adding items to the order list will be persisted on the SDCard
 */
public enum TemporaryOrderMgr {
   INSTANCE;

   /**
    * Call this method to add a new product to the temporary order list
    * @param productID Product ID
    * @param quantity Quantity
    * @param extra Extra
    * @param description Description
    */
   public final void addProductToOrder(int productID, String description, int quantity, String extra, String price) {
      TemporaryProduct temporaryProduct = new TemporaryProduct(productID, quantity, extra, description, price);
      temporaryProduct.save();
   }

   /**
    * Use this method to obtain the Temporary Order List content
    * @return List of TemporaryProducts
    */
   public final List<TemporaryProduct> getTemporaryProductsList(){
      return TemporaryProduct.listAll(TemporaryProduct.class);
   }

   /**
    * Clears the Temporary Order List
    */
   public final void clearTemporaryOrder() {
      TemporaryProduct.deleteAll(TemporaryProduct.class);
   }
}
