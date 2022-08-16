
package in.vdeliverzvendor.myearnings.model_withdrawdet;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WithDrawDetailResponse {

    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("withdrawal_detail")
    @Expose
    private WithdrawalDetail withdrawalDetail;

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

    public WithdrawalDetail getWithdrawalDetail() {
        return withdrawalDetail;
    }

    public void setWithdrawalDetail(WithdrawalDetail withdrawalDetail) {
        this.withdrawalDetail = withdrawalDetail;
    }

}
