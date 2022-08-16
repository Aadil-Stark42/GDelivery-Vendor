
package in.vdeliverzvendor.requestpayment.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Withdrawal {

    @SerializedName("withdrawal_id")
    @Expose
    private String withdrawalId;
    @SerializedName("withdrawal_referral")
    @Expose
    private String withdrawalReferral;
    @SerializedName("payment_state")
    @Expose
    private String paymentState;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("bank_name")
    @Expose
    private String bankName;
    @SerializedName("acc_no")
    @Expose
    private String accNo;
    @SerializedName("acc_name")
    @Expose
    private String accName;

    public String getWithdrawalId() {
        return withdrawalId;
    }

    public void setWithdrawalId(String withdrawalId) {
        this.withdrawalId = withdrawalId;
    }

    public String getWithdrawalReferral() {
        return withdrawalReferral;
    }

    public void setWithdrawalReferral(String withdrawalReferral) {
        this.withdrawalReferral = withdrawalReferral;
    }

    public String getPaymentState() {
        return paymentState;
    }

    public void setPaymentState(String paymentState) {
        this.paymentState = paymentState;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAccNo() {
        return accNo;
    }

    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }

    public String getAccName() {
        return accName;
    }

    public void setAccName(String accName) {
        this.accName = accName;
    }

}
