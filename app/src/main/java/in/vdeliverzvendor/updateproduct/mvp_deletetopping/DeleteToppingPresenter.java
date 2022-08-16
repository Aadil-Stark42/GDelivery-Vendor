package in.vdeliverzvendor.updateproduct.mvp_deletetopping;


import in.vdeliverzvendor.utils.GeneralResponse;

public class DeleteToppingPresenter implements  DeleteToppingContract.GetDeleteStockIntractor.OnFinishedListener,DeleteToppingIntract.OnDeleteToppingListener {

    DeleteToppingContract deleteToppingContract;
    DeleteToppingIntract deleteToppingIntract;
    String TAG = DeleteToppingPresenter.class.getSimpleName();


    public DeleteToppingPresenter(DeleteToppingContract deleteToppingContract, DeleteToppingIntract deleteToppingIntract){
        this.deleteToppingContract = deleteToppingContract;
        this.deleteToppingIntract = deleteToppingIntract;
    }

    public void validateDetails(String strstockid){
        deleteToppingIntract.directValidation(strstockid,this);

    }

    @Override
    public void onFinished(GeneralResponse generalResponse) {
        deleteToppingContract.deletetopping_success(generalResponse);

    }

    @Override
    public void onFailure(String error_msg) {
        deleteToppingContract.deletetopping_failure(error_msg);

    }

    @Override
    public void do_logout() {
        deleteToppingContract.dashboard_logout();
    }

    @Override
    public void onSuccess() {
        if (deleteToppingContract!=null)
            deleteToppingIntract.deletetoppingAPICall(this);

    }

    @Override
    public void onError(String msg) {
        deleteToppingContract.deletetopping_failure(msg);
    }
}
