package ro.itec.waity.api.model;

import com.google.gson.annotations.SerializedName;

public class Category {

    @SerializedName("id")
    private Integer id;
    @SerializedName("description")
    private String description;
    @SerializedName("image_src_id")
    private Object imageSrcId;

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

}