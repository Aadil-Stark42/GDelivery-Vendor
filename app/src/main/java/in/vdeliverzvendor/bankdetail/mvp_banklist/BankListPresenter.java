package in.vdeliverzvendor.bankdetail.mvp_banklist;


import in.vdeliverzvendor.bankdetail.model_banklist.BankListResponse;

public class BankListPresenter implements  BankListContract.GetbankListIntractor.OnFinishedListener,BankListIntract.OnbanklistListener {

    BankListContract bankListContract;
    BankListIntract bankListIntract;
    String TAG = BankListPresenter.class.getSimpleName();


    public BankListPresenter(BankListContract bankListContract, BankListIntract bankListIntract){
        this.bankListContract = bankListContract;
        this.bankListIntract = bankListIntract;
    }

    public void validateDetails(String shop_id){
        bankListIntract.directValidation(shop_id,this);

    }

    @Override
    public void onFinished(BankListResponse bankListResponse) {
        bankListContract.banklist_success(bankListResponse);

    }

    @Override
    public void onFailure(String error_msg) {
        bankListContract.banklist_failure(error_msg);
    }

    @Override
    public void do_logout() {
        bankListContract.dashboard_logout();
    }

    @Override
    public void onSuccess() {
        if (bankListContract!=null)
            bankListIntract.banklistAPICall(this);
    }

    @Override
    public void onError(String msg) {
        bankListContract.banklist_failure(msg);
    }
}
