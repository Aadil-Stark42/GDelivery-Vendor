package in.vdeliverzvendor.updatetopping.mvp_updatetopping;


import in.vdeliverzvendor.utils.GeneralResponse;

public class UpdateToppingPresenter implements  UpdateToppingContract.updatetoppingProductIntractor.OnFinishedListener,
        UpdateToppingIntract.OnupdatetoppingListener{

    UpdateToppingContract updateToppingContract;
    UpdateToppingIntract updateToppingIntract;
    String TAG = UpdateToppingPresenter.class.getSimpleName();


    public UpdateToppingPresenter(UpdateToppingContract updateToppingContract, UpdateToppingIntract updateToppingIntract){
        this.updateToppingContract = updateToppingContract;
        this.updateToppingIntract = updateToppingIntract;
    }


    public void validateDetails(String strPId,String strtoppingid,String strActualPrice,String stravailable,String strtitleid,String strname,String strVariant){
        updateToppingIntract.directValidation(strPId,strtoppingid,strActualPrice,stravailable,strtitleid,strname,strVariant,this);
    }


    @Override
    public void onFinished(GeneralResponse generalResponse) {
        updateToppingContract.updatetopping_success(generalResponse);

    }

    @Override
    public void onFailure(String error_msg) {
        updateToppingContract.updatetopping_failure(error_msg);

    }

    @Override
    public void do_logout() {
        updateToppingContract.dashboard_logout();
    }

    @Override
    public void onSuccess() {
        if (updateToppingContract!=null)
            updateToppingIntract.updatetoppingAPICall(this);

    }

    @Override
    public void onError(String msg) {
        updateToppingContract.updatetopping_failure(msg);

    }
}
