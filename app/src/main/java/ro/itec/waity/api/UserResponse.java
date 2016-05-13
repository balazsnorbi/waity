package ro.itec.waity.api;

import com.google.gson.annotations.SerializedName;

public class UserResponse {

    @SerializedName("status")
    private String status;
    @SerializedName("user_id")
    private Integer userId;

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
     * The userId
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     *
     * @param userId
     * The user_id
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return status + " " + userId;
    }
}
