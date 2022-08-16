package in.vdeliverzvendor.productsearch.mvp;


import in.vdeliverzvendor.productlist.model.ProductListResponse;

public class ProductSearchPresenter implements  ProductSearchContract.GetproductsearchIntractor.OnFinishedListener,ProductSearchIntract.onProductsearchlistener{

    ProductSearchContract productSearchContract;
    ProductSearchIntract productSearchIntract;
    String TAG = ProductSearchPresenter.class.getSimpleName();


    public ProductSearchPresenter(ProductSearchContract productSearchContract, ProductSearchIntract productSearchIntract){
        this.productSearchContract = productSearchContract;
        this.productSearchIntract = productSearchIntract;
    }

    public void validateDetails(String strShopId,String strpage){
        productSearchIntract.directValidation(strShopId,strpage,this);

    }


    @Override
    public void onFinished(ProductListResponse productListResponse) {
        productSearchContract.productsearch_success(productListResponse);
    }

    @Override
    public void onFailure(String error_msg) {
        productSearchContract.productsearch_failure(error_msg);
    }

    @Override
    public void do_logout() {
        productSearchContract.dashboard_logout();
    }

    @Override
    public void onSuccess() {
        if (productSearchContract!=null)
            productSearchIntract.productsearchAPICall(this);
    }

    @Override
    public void onError(String msg) {
        productSearchContract.productsearch_failure(msg);
    }
}
