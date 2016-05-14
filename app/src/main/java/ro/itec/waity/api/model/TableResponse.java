package ro.itec.waity.api.model;

import com.google.gson.annotations.SerializedName;

public class TableResponse {
    @SerializedName("status")
    private String status;
    @SerializedName("tableId")
    private Integer tableId;
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
     * The tableId
     */
    public Integer getTableId() {
        return tableId;
    }

    /**
     *
     * @param tableId
     * The tableId
     */
    public void setTableId(Integer tableId) {
        this.tableId = tableId;
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
        return status + " " + tableId + " " + message;
    }
}
