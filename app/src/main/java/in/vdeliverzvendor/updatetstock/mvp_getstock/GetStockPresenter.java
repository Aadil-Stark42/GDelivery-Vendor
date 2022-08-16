package in.vdeliverzvendor.updatetstock.mvp_getstock;


import in.vdeliverzvendor.updatetstock.model_getstock.StockDetailResponse;

public class GetStockPresenter implements  GetStockContract.GetstockIntractor.OnFinishedListener,GetStockIntract.OnStockListener {

    GetStockContract getStockContract;
    GetStockIntract getStockIntract;
    String TAG = GetStockPresenter.class.getSimpleName();


    public GetStockPresenter(GetStockContract getStockContract, GetStockIntract getStockIntract){
        this.getStockContract = getStockContract;
        this.getStockIntract = getStockIntract;
    }

    public void validateDetails(String strstockid){
        getStockIntract.directValidation(strstockid,this);

    }


    @Override
    public void onFinished(StockDetailResponse StockDetailResponse) {
        getStockContract.getstock_success(StockDetailResponse);

    }

    @Override
    public void onFailure(String error_msg) {
        getStockContract.getstock_failure(error_msg);

    }

    @Override
    public void do_logout() {
        getStockContract.dashboard_logout();
    }

    @Override
    public void onSuccess() {
        if (getStockContract!=null)
            getStockIntract.getstockAPICall(this);

    }

    @Override
    public void onError(String msg) {
        getStockContract.getstock_failure(msg);

    }
}
