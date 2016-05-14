package ro.itec.waity.api.model;

import com.google.gson.annotations.SerializedName;

public class OrderBody {

    @SerializedName("productId")
    private Integer productId;
    @SerializedName("quantity")
    private Integer quantity;

    public OrderBody(Integer productId, Integer quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }


    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
