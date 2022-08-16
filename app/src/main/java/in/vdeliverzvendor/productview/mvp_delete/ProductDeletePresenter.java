package in.vdeliverzvendor.productview.mvp_delete;


import in.vdeliverzvendor.utils.GeneralResponse;

public class ProductDeletePresenter implements ProductDeleteContract.GetProductdeleteIntractor.OnFinishedListener,ProductDeleteIntract.OnProductDeleteListener {

    ProductDeleteContract productDeleteContract;
    ProductDeleteIntract productDeleteIntract;
    String TAG = ProductDeletePresenter.class.getSimpleName();


    public ProductDeletePresenter(ProductDeleteContract productDeleteContract, ProductDeleteIntract productDeleteIntract){
        this.productDeleteContract = productDeleteContract;
        this.productDeleteIntract = productDeleteIntract;
    }

    public void validateDetails(String strproductid){
        productDeleteIntract.directValidation(strproductid,this);

    }

    @Override
    public void onFinished(GeneralResponse generalResponse) {
        productDeleteContract.productdelete_success(generalResponse);

    }

    @Override
    public void onFailure(String error_msg) {
        productDeleteContract.productdelete_failure(error_msg);

    }

    @Override
    public void do_logout() {
        productDeleteContract.dashboard_logout();
    }

    @Override
    public void onSuccess() {
        if (productDeleteContract!=null)
            productDeleteIntract.productdeleteAPICall(this);

    }

    @Override
    public void onError(String msg) {
        productDeleteContract.productdelete_failure(msg);

    }
}
