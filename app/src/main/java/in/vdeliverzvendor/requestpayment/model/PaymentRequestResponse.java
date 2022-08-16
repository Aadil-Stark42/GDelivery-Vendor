
package in.vdeliverzvendor.requestpayment.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaymentRequestResponse {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("Withdrawals")
    @Expose
    private List<Withdrawal> withdrawals = null;
    @SerializedName("pagination")
    @Expose
    private Pagination pagination;
    @SerializedName("total_orders")
    @Expose
    private String totalOrders;
    @SerializedName("total_earning")
    @Expose
    private String totalEarning;
    @SerializedName("commission")
    @Expose
    private String commission;

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

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public List<Withdrawal> getWithdrawals() {
        return withdrawals;
    }

    public void setWithdrawals(List<Withdrawal> withdrawals) {
        this.withdrawals = withdrawals;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public String getTotalOrders() {
        return totalOrders;
    }

    public void setTotalOrders(String totalOrders) {
        this.totalOrders = totalOrders;
    }

    public String getTotalEarning() {
        return totalEarning;
    }

    public void setTotalEarning(String  totalEarning) {
        this.totalEarning = totalEarning;
    }

    public String getCommission() {
        return commission;
    }

    public void setCommission(String commission) {
        this.commission = commission;
    }

}
