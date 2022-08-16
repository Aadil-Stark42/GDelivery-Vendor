
package in.vdeliverzvendor.shopoverview.model_product;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ShopProductListResponse {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("referral_prefix")
    @Expose
    private String referralPrefix;
    @SerializedName("shop")
    @Expose
    private List<Shop> shop = null;

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

    public String getReferralPrefix() {
        return referralPrefix;
    }

    public void setReferralPrefix(String referralPrefix) {
        this.referralPrefix = referralPrefix;
    }

    public List<Shop> getShop() {
        return shop;
    }

    public void setShop(List<Shop> shop) {
        this.shop = shop;
    }

}
