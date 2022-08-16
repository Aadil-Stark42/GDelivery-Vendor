package in.vdeliverzvendor.shopoverview.mvp_product;


import in.vdeliverzvendor.shopoverview.model_product.ShopProductListResponse;

public class ShopProductPresenter implements  ShopProductIntract.OnShopProdcutListener,ShopProductContract.GetshopproductlistIntractor.OnFinishedListener {

    ShopProductContract shopProductContract;
    ShopProductIntract shopProductIntract;
    String TAG = ShopProductPresenter.class.getSimpleName();


    public ShopProductPresenter(ShopProductContract shopProductContract, ShopProductIntract shopProductIntract){
        this.shopProductContract = shopProductContract;
        this.shopProductIntract = shopProductIntract;
    }

    public void validateDetails(String strshopId){
        shopProductIntract.directValidation(strshopId,this);

    }

    @Override
    public void onFinished(ShopProductListResponse shopProductListResponse) {
        shopProductContract.shopproductlist_success(shopProductListResponse);

    }

    @Override
    public void onFailure(String error_msg) {
        shopProductContract.shopproductlist_failure(error_msg);

    }

    @Override
    public void do_logout() {
        shopProductContract.dashboard_logout();
    }

    @Override
    public void onSuccess() {
        if (shopProductContract!=null)
            shopProductIntract.shopproductlistAPICall(this);

    }

    @Override
    public void onError(String msg) {
        shopProductContract.shopproductlist_failure(msg);

    }
}
