package in.vdeliverzvendor.shoplist.mvp;


import in.vdeliverzvendor.shoplist.model.ShopListResponse;

public class ShopLitsPresenter implements  ShopListContract.GetShopListIntractor.OnFinishedListener,ShopListIntract.OShopListener {

    ShopListContract shopListContract;
    ShopListIntract shopListIntract;
    String TAG = ShopLitsPresenter.class.getSimpleName();


    public ShopLitsPresenter(ShopListContract shopListContract, ShopListIntract shopListIntract){
        this.shopListContract = shopListContract;
        this.shopListIntract = shopListIntract;
    }

    public void validateDetails(String strpage){
        shopListIntract.directValidation(strpage,this);

    }

    @Override
    public void onFinished(ShopListResponse shopListResponse) {
        shopListContract.shoplist_success(shopListResponse);

    }

    @Override
    public void onFailure(String error_msg) {
        shopListContract.shoplist_failure(error_msg);

    }

    @Override
    public void do_logout() {
        shopListContract.dashboard_logout();
    }

    @Override
    public void onSuccess() {
        if (shopListContract!=null)
            shopListIntract.shoplistAPICall(this);

    }

    @Override
    public void onError(String msg) {
        shopListContract.shoplist_failure(msg);
    }
}
