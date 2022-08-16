package in.vdeliverzvendor.productview.mvp_staus;

import in.vdeliverzvendor.utils.GeneralResponse;

public class ProductStatusChangePresenter implements  ProductStausChangeContract.GetProductStatusIntractor.OnFinishedListener,ProductStatusChangeIntract.OnProductStatusListener {

    ProductStausChangeContract productStausChangeContract;
    ProductStatusChangeIntract productStatusChangeIntract;
    String TAG = ProductStatusChangePresenter.class.getSimpleName();


    public ProductStatusChangePresenter(ProductStausChangeContract productStausChangeContract, ProductStatusChangeIntract productStatusChangeIntract){
        this.productStausChangeContract = productStausChangeContract;
        this.productStatusChangeIntract = productStatusChangeIntract;
    }

    public void validateDetails(String strproductid,String strStatus,String strname){
        productStatusChangeIntract.directValidation(strproductid,strStatus,strname,this);

    }

    @Override
    public void onSuccess() {
        if (productStausChangeContract!=null)
            productStatusChangeIntract.productstatusAPICall(this);
    }

    @Override
    public void onError(String msg) {
        productStausChangeContract.productstatus_failure(msg);

    }

    @Override
    public void onFinished(GeneralResponse generalResponse) {
        productStausChangeContract.productstatus_success(generalResponse);

    }

    @Override
    public void onFailure(String error_msg) {
        productStausChangeContract.productstatus_failure(error_msg);

    }

    @Override
    public void do_logout() {
        productStausChangeContract.dashboard_logout();
    }
}
