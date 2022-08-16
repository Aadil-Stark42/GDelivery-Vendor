
package in.vdeliverzvendor.editoutlet.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OutLetDetailResponse {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("shop")
    @Expose
    private Shop shop;
    @SerializedName("cuisines")
    @Expose
    private List<Cuisine__1> cuisines = null;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public List<Cuisine__1> getCuisines() {
        return cuisines;
    }

    public void setCuisines(List<Cuisine__1> cuisines) {
        this.cuisines = cuisines;
    }

}
