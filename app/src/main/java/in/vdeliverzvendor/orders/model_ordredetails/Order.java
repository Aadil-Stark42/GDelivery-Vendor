
package in.vdeliverzvendor.orders.model_ordredetails;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Order {

    @SerializedName("order_id")
    @Expose
    private String orderId;
    @SerializedName("paid")
    @Expose
    private String paid;
    @SerializedName("payment_type")
    @Expose
    private String paymentType;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("customer_address")
    @Expose
    private CustomerAddress customerAddress;
    @SerializedName("canceled_at")
    @Expose
    private String canceledAt;
    @SerializedName("cancel_reason")
    @Expose
    private Object cancelReason;
    @SerializedName("confirmed_at")
    @Expose
    private String confirmedAt;
    @SerializedName("delivered_at")
    @Expose
    private String deliveredAt;
    @SerializedName("referral")
    @Expose
    private String referral;
    @SerializedName("picked_at")
    @Expose
    private String pickedAt;
    @SerializedName("order_status")
    @Expose
    private String orderStatus;
    @SerializedName("order_state")
    @Expose
    private String orderState;
    @SerializedName("delivered_by")
    @Expose
    private String deliveredBy;
    @SerializedName("expected_time")
    @Expose
    private String expectedTime;
    @SerializedName("shop_address")
    @Expose
    private ShopAddress shopAddress;
    @SerializedName("comission")
    @Expose
    private String comission;
    @SerializedName("accepted_at")
    @Expose
    private String acceptedAt;
    @SerializedName("rejected_at")
    @Expose
    private String rejectedAt;
    @SerializedName("assigned_at")
    @Expose
    private String assignedAt;
    @SerializedName("instructions")
    @Expose
    private String instructions;
    @SerializedName("delivery_boy")
    @Expose
    private String deliveryBoy;
    @SerializedName("order_items")
    @Expose
    private List<OrderItem> orderItems = null;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPaid() {
        return paid;
    }

    public void setPaid(String paid) {
        this.paid = paid;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public CustomerAddress getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(CustomerAddress customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getCanceledAt() {
        return canceledAt;
    }

    public void setCanceledAt(String canceledAt) {
        this.canceledAt = canceledAt;
    }

    public Object getCancelReason() {
        return cancelReason;
    }

    public void setCancelReason(Object cancelReason) {
        this.cancelReason = cancelReason;
    }

    public String getConfirmedAt() {
        return confirmedAt;
    }

    public void setConfirmedAt(String confirmedAt) {
        this.confirmedAt = confirmedAt;
    }

    public String getDeliveredAt() {
        return deliveredAt;
    }

    public void setDeliveredAt(String deliveredAt) {
        this.deliveredAt = deliveredAt;
    }

    public String getReferral() {
        return referral;
    }

    public void setReferral(String referral) {
        this.referral = referral;
    }

    public String getPickedAt() {
        return pickedAt;
    }

    public void setPickedAt(String pickedAt) {
        this.pickedAt = pickedAt;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderState() {
        return orderState;
    }

    public void setOrderState(String orderState) {
        this.orderState = orderState;
    }

    public String getDeliveredBy() {
        return deliveredBy;
    }

    public void setDeliveredBy(String deliveredBy) {
        this.deliveredBy = deliveredBy;
    }

    public String getExpectedTime() {
        return expectedTime;
    }

    public void setExpectedTime(String expectedTime) {
        this.expectedTime = expectedTime;
    }

    public ShopAddress getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(ShopAddress shopAddress) {
        this.shopAddress = shopAddress;
    }

    public String getComission() {
        return comission;
    }

    public void setComission(String comission) {
        this.comission = comission;
    }

    public String getAcceptedAt() {
        return acceptedAt;
    }

    public void setAcceptedAt(String acceptedAt) {
        this.acceptedAt = acceptedAt;
    }

    public String getRejectedAt() {
        return rejectedAt;
    }

    public void setRejectedAt(String rejectedAt) {
        this.rejectedAt = rejectedAt;
    }

    public String getAssignedAt() {
        return assignedAt;
    }

    public void setAssignedAt(String assignedAt) {
        this.assignedAt = assignedAt;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getDeliveryBoy() {
        return deliveryBoy;
    }

    public void setDeliveryBoy(String deliveryBoy) {
        this.deliveryBoy = deliveryBoy;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

}
