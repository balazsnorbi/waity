package ro.itec.waity.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlaceOrderResponse {
    @SerializedName("status")
    private String status;
    @SerializedName("orders_ids")
    private List<Integer> ordersIds = new ArrayList<Integer>();
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
     * The ordersIds
     */
    public List<Integer> getOrdersIds() {
        return ordersIds;
    }

    /**
     *
     * @param ordersIds
     * The orders_ids
     */
    public void setOrdersIds(List<Integer> ordersIds) {
        this.ordersIds = ordersIds;
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

    @Override
    public String toString() {
        return status + " " + Arrays.toString(ordersIds.toArray()) + " " + message;
    }
}
