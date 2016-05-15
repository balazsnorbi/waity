package ro.itec.waity.bl.persistence.order;

import com.orm.SugarRecord;

public class Order extends SugarRecord {
    public Integer orderId;
    public Integer productId;
    public Integer quantity;
    public String extra;
    public String description;
    public String price;
    public OrderState orderState;

    public Order() {
    }

    public Order(Integer orderId, Integer productId, Integer quantity, String extra,
                 String description, String price, OrderState orderState) {
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.extra = extra;
        this.description = description;
        this.price = price;
        this.orderState = orderState;
    }
}
