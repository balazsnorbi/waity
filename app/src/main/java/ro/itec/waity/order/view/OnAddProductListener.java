package ro.itec.waity.order.view;

import android.text.Editable;

import ro.itec.waity.api.Produse;

public interface OnAddProductListener {

    void addProductOrder(Produse productId, Integer quantity, String extra);

}
