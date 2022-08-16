package in.vdeliverzvendor.updatetstock.mvp_update;


import in.vdeliverzvendor.utils.GeneralResponse;

                    public class UpdateStockPresenter implements  UpdateStockContract.updatestockProductIntractor.OnFinishedListener,UpdateStockIntract.OnUpdateStockListener {

    UpdateStockContract updateStockContract;
    UpdateStockIntract updateStockIntract;
    String TAG = UpdateStockPresenter.class.getSimpleName();


    public UpdateStockPresenter(UpdateStockContract updateStockContract, UpdateStockIntract updateStockIntract){
        this.updateStockContract = updateStockContract;
        this.updateStockIntract = updateStockIntract;
    }


    public void validateDetails(String strPId,String strstockid,String strActualPrice,String strSellingPrice,String strAvailable,String strSize,String strUnit,String strVariant){
        updateStockIntract.directValidation(strPId,strstockid,strActualPrice,strSellingPrice,strAvailable,strSize,strUnit,strVariant,this);
    }



    @Override
    public void onFinished(GeneralResponse generalResponse) {
        updateStockContract.updatestock_success(generalResponse);

    }

    @Override
    public void onFailure(String error_msg) {
        updateStockContract.updatestock_failure(error_msg);
    }

    @Override
    public void do_logout() {
        updateStockContract.dashboard_logout();
    }

    @Override
    public void onSuccess() {
        if (updateStockContract!=null)
            updateStockIntract.updatestockAPICall(this);
    }

    @Override
    public void onError(String msg) {
        updateStockContract.updatestock_failure(msg);
    }
}
