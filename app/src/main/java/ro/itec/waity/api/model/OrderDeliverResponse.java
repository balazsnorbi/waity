package ro.itec.waity.api.model;

import com.google.gson.annotations.SerializedName;

public class OrderDeliverResponse {
    @SerializedName("status")
    private String status;
    @SerializedName("delivered_order_id")
    private Integer deliveredOrderId;
    @SerializedName("message")
    private String message;

    /**
     *
     * @return
     * The status
     */
    public String getStatus() {
        return status;
    }

    /**
     *
     * @param status
     * The status
     */
    public void setStatus(String status) {
        this.status = status;
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
     * The message
     */
    public String getMessage() {
        return message;
    }

    /**
     *
     * @param message
     * The message
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
