package ro.itec.waity.api;

import com.google.gson.annotations.SerializedName;

public class Produse {

    @SerializedName("id")
    private Integer id;
    @SerializedName("description")
    private String description;
    @SerializedName("image_src_id")
    private Object imageSrcId;
    @SerializedName("category_id")
    private Integer categoryId;
    @SerializedName("price")
    private String price;

    /**
     *
     * @return
     * The id
     */
    public Integer getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The description
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     * The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     * @return
     * The imageSrcId
     */
    public Object getImageSrcId() {
        return imageSrcId;
    }

    /**
     *
     * @param imageSrcId
     * The image_src_id
     */
    public void setImageSrcId(Object imageSrcId) {
        this.imageSrcId = imageSrcId;
    }

    /**
     *
     * @return
     * The categoryId
     */
    public Integer getCategoryId() {
        return categoryId;
    }

    /**
     *
     * @param categoryId
     * The category_id
     */
    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
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

    @Override
    public String toString() {
        return description + " " + price;
    }
}
