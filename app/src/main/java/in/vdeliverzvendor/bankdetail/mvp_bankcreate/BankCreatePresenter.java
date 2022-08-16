package in.vdeliverzvendor.bankdetail.mvp_bankcreate;


import in.vdeliverzvendor.utils.GeneralResponse;

public class BankCreatePresenter implements  BankCreateContract.GetcratebankIntractor.OnFinishedListener,BankCreateIntract.OnbankCreateListener {

    BankCreateContract bankCreateContract;
    BankCreateIntract bankCreateIntract;
    String TAG = BankCreatePresenter.class.getSimpleName();


    public BankCreatePresenter(BankCreateContract bankCreateContract, BankCreateIntract bankCreateIntract){
        this.bankCreateContract = bankCreateContract;
        this.bankCreateIntract = bankCreateIntract;
    }

    public void validateDetails(String strShopId,String strHolderName,String strAccountNum,String strCity,String strifsc,String strBranch,String strBankName){
        bankCreateIntract.directValidation(strShopId,strHolderName,strAccountNum,strCity,strifsc,strBranch,strBankName,this);
    }


    @Override
    public void onFinished(GeneralResponse generalResponse) {
        bankCreateContract.cratebank_success(generalResponse);

    }

    @Override
    public void onFailure(String error_msg) {
        bankCreateContract.cratebank_failure(error_msg);
    }

    @Override
    public void do_logout() {
        bankCreateContract.dashboard_logout();
    }

    @Override
    public void onSuccess() {
        if (bankCreateContract!=null)
            bankCreateIntract.cratebankAPICall(this);

    }

    @Override
    public void onError(String msg) {
        bankCreateContract.cratebank_failure(msg);
    }
}
