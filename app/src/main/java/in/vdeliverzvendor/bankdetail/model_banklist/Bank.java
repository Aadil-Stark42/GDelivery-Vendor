
package in.vdeliverzvendor.bankdetail.model_banklist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Bank {

    @SerializedName("bank_id")
    @Expose
    private String bankId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("bank_name")
    @Expose
    private String bankName;
    @SerializedName("acc_no")
    @Expose
    private String accNo;
    @SerializedName("ifsc")
    @Expose
    private String ifsc;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("branch")
    @Expose
    private String branch;

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getIfsc() {
        return ifsc;
    }

    public void setIfsc(String ifsc) {
        this.ifsc = ifsc;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public boolean isIschecked() {
        return ischecked;
    }

    public void setIschecked(boolean ischecked) {
        this.ischecked = ischecked;
    }

    public  boolean ischecked;

}
