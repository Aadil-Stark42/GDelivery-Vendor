
package in.vdeliverzvendor.productview.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Topping {

    @SerializedName("topping_id")
    @Expose
    private String toppingId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("available")
    @Expose
    private String available;
    @SerializedName("title_id")
    @Expose
    private String titleId;
    @SerializedName("variety")
    @Expose
    private String variety;

    public String getToppingId() {
        return toppingId;
    }

    public void setToppingId(String toppingId) {
        this.toppingId = toppingId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public String getTitleId() {
        return titleId;
    }

    public void setTitleId(String titleId) {
        this.titleId = titleId;
    }

    public String getVariety() {
        return variety;
    }

    public void setVariety(String variety) {
        this.variety = variety;
    }

}
