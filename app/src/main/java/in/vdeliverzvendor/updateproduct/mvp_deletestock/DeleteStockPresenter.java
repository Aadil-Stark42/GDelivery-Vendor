package in.vdeliverzvendor.updateproduct.mvp_deletestock;


import in.vdeliverzvendor.utils.GeneralResponse;

public class DeleteStockPresenter implements  DeleteStockIntract.OnDeleteStockListener,DeleteStockContract.GetDeleteStockIntractor.OnFinishedListener {

    DeleteStockContract deleteStockContract;
    DeleteStockIntract deleteStockIntract;
    String TAG = DeleteStockPresenter.class.getSimpleName();


    public DeleteStockPresenter(DeleteStockContract deleteStockContract, DeleteStockIntract deleteStockIntract){
        this.deleteStockContract = deleteStockContract;
        this.deleteStockIntract = deleteStockIntract;
    }

    public void validateDetails(String strstockid){
        deleteStockIntract.directValidation(strstockid,this);

    }


    @Override
    public void onFinished(GeneralResponse generalResponse) {
        deleteStockContract.deletestock_success(generalResponse);

    }

    @Override
    public void onFailure(String error_msg) {
        deleteStockContract.deletestock_failure(error_msg);

    }

    @Override
    public void do_logout() {
        deleteStockContract.dashboard_logout();
    }

    @Override
    public void onSuccess() {
        if (deleteStockContract!=null)
            deleteStockIntract.deletestockAPICall(this);
    }

    @Override
    public void onError(String msg) {
        deleteStockContract.deletestock_failure(msg);
    }
}
