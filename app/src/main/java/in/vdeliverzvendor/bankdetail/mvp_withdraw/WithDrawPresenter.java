package in.vdeliverzvendor.bankdetail.mvp_withdraw;


import in.vdeliverzvendor.bankdetail.model_withdraw.WithDrawResponse;

public class WithDrawPresenter implements  WithdrawContract.GetwithdrawIntractor.OnFinishedListener,WithDrawIntract.OnbanklistListener {

    WithdrawContract withdrawContract;
    WithDrawIntract withDrawIntract;
    String TAG = WithDrawPresenter.class.getSimpleName();


    public WithDrawPresenter(WithdrawContract withdrawContract, WithDrawIntract withDrawIntract){
        this.withdrawContract = withdrawContract;
        this.withDrawIntract = withDrawIntract;
    }

    public void validateDetails(String strShopid,String strbankid){
        withDrawIntract.directValidation(strShopid,strbankid,this);

    }


    @Override
    public void onSuccess() {
        if (withdrawContract!=null)
            withDrawIntract.withdrawAPICall(this);

    }

    @Override
    public void onError(String msg) {
        withdrawContract.withdraw_failure(msg);

    }

    @Override
    public void onFinished(WithDrawResponse withDrawResponse) {
        withdrawContract.withdraw_success(withDrawResponse);
    }

    @Override
    public void onFailure(String error_msg) {
        withdrawContract.withdraw_failure(error_msg);
    }

    @Override
    public void do_logout() {
        withdrawContract.dashboard_logout();
    }
}
