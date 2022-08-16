package in.vdeliverzvendor.productview.mvp;

import in.vdeliverzvendor.productview.model.ProductDetailResponse;

public class ProductViewPresenter implements  ProductViewContract.GetProductviewIntractor.OnFinishedListener,ProductViewIntractor.OnProductviewListener {

    ProductViewContract productViewContract;
    ProductViewIntractor productViewIntractor;
    String TAG = ProductViewPresenter.class.getSimpleName();


    public ProductViewPresenter(ProductViewContract productViewContract, ProductViewIntractor productViewIntractor){
        this.productViewContract = productViewContract;
        this.productViewIntractor = productViewIntractor;
    }

    public void validateDetails(String strproductid){
        productViewIntractor.directValidation(strproductid,this);

    }

    @Override
    public void onFinished(ProductDetailResponse productDetailResponse) {
        productViewContract.productview_success(productDetailResponse);

    }

    @Override
    public void onFailure(String error_msg) {
        productViewContract.productview_failure(error_msg);

    }

    @Override
    public void do_logout() {
        productViewContract.dashboard_logout();
    }

    @Override
    public void onSuccess() {
        if (productViewContract!=null)
            productViewIntractor.productviewAPICall(this);

    }

    @Override
    public void onError(String msg) {
        productViewContract.productview_failure(msg);

    }
}
