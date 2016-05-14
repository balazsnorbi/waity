package ro.itec.waity.api;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ProductsResponse {

    @SerializedName("status")
    private String status;
    @SerializedName("produse")
    private List<Produse> produse = new ArrayList<Produse>();

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
     * The produse
     */
    public List<Produse> getProduse() {
        return produse;
    }

    /**
     *
     * @param produse
     * The produse
     */
    public void setProduse(List<Produse> produse) {
        this.produse = produse;
    }

}
