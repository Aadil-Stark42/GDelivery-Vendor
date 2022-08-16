
package in.vdeliverzvendor.dashboard.model_dashboard;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DashBoardResponse {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("users")
    @Expose
    private String users;
    @SerializedName("tot_deliveries")
    @Expose
    private String totDeliveries;
    @SerializedName("unassigned")
    @Expose
    private String unassigned;
    @SerializedName("tot_earning")
    @Expose
    private String totEarning= "";
    @SerializedName("shop_id")
    @Expose
    private String shop_id;
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("orders")
    @Expose
    private List<Order> orders = null;
    @SerializedName("pagination")
    @Expose
    private Pagination pagination;


    @SerializedName("app_status")
    @Expose
    private String app_status;

    public String getApp_status() {
        return app_status;
    }

    public String getTermsAndConditions() {
        return termsAndConditions;
    }

    public void setTermsAndConditions(String termsAndConditions) {
        this.termsAndConditions = termsAndConditions;
    }

    public String getPrivacyPolicy() {
        return privacyPolicy;
    }

    public void setPrivacyPolicy(String privacyPolicy) {
        this.privacyPolicy = privacyPolicy;
    }

    public String getShop_id() {
        return shop_id;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @SerializedName("terms_and_conditions")
    @Expose
    private String termsAndConditions;
    @SerializedName("privacy_policy")
    @Expose
    private String privacyPolicy;

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

    public String getUsers() {
        return users;
    }

    public void setUsers(String users) {
        this.users = users;
    }

    public String getTotDeliveries() {
        return totDeliveries;
    }

    public void setTotDeliveries(String totDeliveries) {
        this.totDeliveries = totDeliveries;
    }

    public String getUnassigned() {
        return unassigned;
    }

    public void setUnassigned(String unassigned) {
        this.unassigned = unassigned;
    }

    public String getTotEarning() {
        return totEarning;
    }

    public void setTotEarning(String totEarning) {
        this.totEarning = totEarning;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

}
