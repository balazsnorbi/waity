package ro.itec.waity.api.model;

import com.google.gson.annotations.SerializedName;

public class BillResponse {

    @SerializedName("product_id")
    private Integer productId;
    @SerializedName("delivered_order_id")
    private Integer deliveredOrderId;
    @SerializedName("product_name")
    private String productName;
    @SerializedName("unit_price")
    private String unitPrice;
    @SerializedName("quantity")
    private Integer quantity;
    @SerializedName("price")
    private String price;
    @SerializedName("name")
    private String name;
    @SerializedName("email")
    private String email;
    @SerializedName("client_id")
    private Integer clientId;

    /**
     *
     * @return
     * The productId
     */
    public Integer getProductId() {
        return productId;
    }

    /**
     *
     * @param productId
     * The product_id
     */
    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    /**
     *
     * @return
     * The deliveredOrderId
     */
    public Integer getDeliveredOrderId() {
        return deliveredOrderId;
    }

    /**
     *
     * @param deliveredOrderId
     * The delivered_order_id
     */
    public void setDeliveredOrderId(Integer deliveredOrderId) {
        this.deliveredOrderId = deliveredOrderId;
    }

    /**
     *
     * @return
     * The productName
     */
    public String getProductName() {
        return productName;
    }

    /**
     *
     * @param productName
     * The product_name
     */
    public void setProductName(String productName) {
        this.productName = productName;
    }

    /**
     *
     * @return
     * The unitPrice
     */
    public String getUnitPrice() {
        return unitPrice;
    }

    /**
     *
     * @param unitPrice
     * The unit_price
     */
    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    /**
     *
     * @return
     * The quantity
     */
    public Integer getQuantity() {
        return quantity;
    }

    /**
     *
     * @param quantity
     * The quantity
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    /**
     *
     * @return
     * The price
     */
    public String getPrice() {
        return price;
    }

    /**
     *
     * @param price
     * The price
     */
    public void setPrice(String price) {
        this.price = price;
    }

    /**
     *
     * @return
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     * The email
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email
     * The email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @return
     * The clientId
     */
    public Integer getClientId() {
        return clientId;
    }

    /**
     *
     * @param clientId
     * The client_id
     */
    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

}
