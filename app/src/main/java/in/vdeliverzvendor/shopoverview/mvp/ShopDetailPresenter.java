package in.vdeliverzvendor.shopoverview.mvp;


import in.vdeliverzvendor.shopoverview.model.ShopDetailResponse;

public class ShopDetailPresenter implements  ShopDetailIntract.onShopDetaillistener,ShopDetailContract.GetshopdetailIntractor.OnFinishedListener {

    ShopDetailContract shopDetailContract;
    ShopDetailIntract shopDetailIntract;
    String TAG = ShopDetailPresenter.class.getSimpleName();


    public ShopDetailPresenter(ShopDetailContract shopDetailContract, ShopDetailIntract shopDetailIntract){
        this.shopDetailContract = shopDetailContract;
        this.shopDetailIntract = shopDetailIntract;
    }

    public void validateDetails(String strShopId){
        shopDetailIntract.directValidation(strShopId,this);

    }

    @Override
    public void onFinished(ShopDetailResponse shopDetailResponse) {
        shopDetailContract.shopdetail_success(shopDetailResponse);

    }

    @Override
    public void onFailure(String error_msg) {
        shopDetailContract.shopdetail_failure(error_msg);

    }

    @Override
    public void do_logout() {
        shopDetailContract.dashboard_logout();
    }

    @Override
    public void onSuccess() {
        if (shopDetailContract!=null)
            shopDetailIntract.shopdetailPICall(this);

    }

    @Override
    public void onError(String msg) {
        shopDetailContract.shopdetail_failure(msg);

    }
}
