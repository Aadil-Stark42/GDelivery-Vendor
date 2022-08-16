package in.vdeliverzvendor.productcategory.mvp_catlist;


import in.vdeliverzvendor.productcategory.model_catlist.ProductCatListResponse;

public class ProductCatListPresenter implements ProductCatListContract.GetproductcatlistIntractor.OnFinishedListener,ProductCatListIntract.OnproductcatlistListener {

    ProductCatListContract productCatListContract;
    ProductCatListIntract productCatListIntract;
    String TAG = ProductCatListPresenter.class.getSimpleName();


    public ProductCatListPresenter(ProductCatListContract productCatListContract, ProductCatListIntract productCatListIntract){
        this.productCatListContract = productCatListContract;
        this.productCatListIntract = productCatListIntract;
    }

    public void validateDetails(String strShopId,String strpage){
        productCatListIntract.directValidation(strShopId,strpage,this);

    }


    @Override
    public void onFinished(ProductCatListResponse productCatListResponse) {
        productCatListContract.productcatlist_success(productCatListResponse);

    }

    @Override
    public void onFailure(String error_msg) {
        productCatListContract.productcatlist_failure(error_msg);
    }

    @Override
    public void do_logout() {
        productCatListContract.dashboard_logout();
    }

    @Override
    public void onSuccess() {
        if (productCatListContract!=null)
            productCatListIntract.productcatlistAPICall(this);
    }

    @Override
    public void onError(String msg) {
        productCatListContract.productcatlist_failure(msg);
    }
}
