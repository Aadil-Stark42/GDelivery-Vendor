package in.vdeliverzvendor.productlist.mvp;


import in.vdeliverzvendor.productlist.model.ProductListResponse;

public class ProductListPresenter implements  ProductListContract.GetProductlistIntractor.OnFinishedListener,ProductListIntract.OnLoginListener {

    ProductListContract productListContract;
    ProductListIntract productListIntract;
    String TAG = ProductListPresenter.class.getSimpleName();


    public ProductListPresenter(ProductListContract productListContract, ProductListIntract productListIntract){
        this.productListContract = productListContract;
        this.productListIntract = productListIntract;
    }

    public void validateDetails(String strpage){
        productListIntract.directValidation(strpage,this);

    }

    @Override
    public void onFinished(ProductListResponse productListResponse) {
        productListContract.productlist_success(productListResponse);

    }

    @Override
    public void onFailure(String error_msg) {
        productListContract.productlist_failure(error_msg);

    }

    @Override
    public void do_logout() {
        productListContract.dashboard_logout();
    }

    @Override
    public void onSuccess() {
        if (productListContract!=null)
            productListIntract.productlistAPICall(this);

    }

    @Override
    public void onError(String msg) {
        productListContract.productlist_failure(msg);

    }
}
