package ro.itec.waity.order.view;

import ro.itec.waity.api.model.Produse;

public interface OnAddProductListener {

    void addProductOrder(Produse productId, Integer quantity, String extra);

}
