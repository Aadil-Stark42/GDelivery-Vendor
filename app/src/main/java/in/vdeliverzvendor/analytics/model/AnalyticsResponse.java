
package in.vdeliverzvendor.analytics.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AnalyticsResponse {

    @SerializedName("monthly_data")
    @Expose
    private MonthlyData monthlyData;
    @SerializedName("weekly_data")
    @Expose
    private WeeklyData weeklyData;
    @SerializedName("total_delivered")
    @Expose
    private String totalDelivered;
    @SerializedName("total_earnings")
    @Expose
    private String totalEarnings;
    @SerializedName("unassigned")
    @Expose
    private String unassigned;
    @SerializedName("users")
    @Expose
    private String users;
    @SerializedName("shop_id")
    @Expose
    private String shopId;
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;

    public MonthlyData getMonthlyData() {
        return monthlyData;
    }

    public void setMonthlyData(MonthlyData monthlyData) {
        this.monthlyData = monthlyData;
    }

    public WeeklyData getWeeklyData() {
        return weeklyData;
    }

    public void setWeeklyData(WeeklyData weeklyData) {
        this.weeklyData = weeklyData;
    }

    public String getTotalDelivered() {
        return totalDelivered;
    }

    public void setTotalDelivered(String totalDelivered) {
        this.totalDelivered = totalDelivered;
    }

    public String getTotalEarnings() {
        return totalEarnings;
    }

    public void setTotalEarnings(String totalEarnings) {
        this.totalEarnings = totalEarnings;
    }

    public String getUnassigned() {
        return unassigned;
    }

    public void setUnassigned(String unassigned) {
        this.unassigned = unassigned;
    }

    public String getUsers() {
        return users;
    }

    public void setUsers(String users) {
        this.users = users;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

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

}
