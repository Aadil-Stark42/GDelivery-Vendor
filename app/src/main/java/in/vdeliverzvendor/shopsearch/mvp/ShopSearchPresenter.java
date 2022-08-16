package in.vdeliverzvendor.shopsearch.mvp;


import in.vdeliverzvendor.shoplist.model.ShopListResponse;

public class ShopSearchPresenter implements  ShopSearchContract.GetshopsearchIntractor.OnFinishedListener,ShopSearchIntract.onShopsearchlistener {

    ShopSearchContract shopSearchContract;
    ShopSearchIntract shopSearchIntract;
    String TAG = ShopSearchPresenter.class.getSimpleName();


    public ShopSearchPresenter(ShopSearchContract shopSearchContract, ShopSearchIntract shopSearchIntract){
        this.shopSearchContract = shopSearchContract;
        this.shopSearchIntract = shopSearchIntract;
    }

    public void validateDetails(String strShopId,String strpage){
        shopSearchIntract.directValidation(strShopId,strpage,this);

    }


    @Override
    public void onFinished(ShopListResponse shopSearchRespone) {
        shopSearchContract.shopsearch_success(shopSearchRespone);

    }

    @Override
    public void onFailure(String error_msg) {
        shopSearchContract.shopsearch_failure(error_msg);
    }

    @Override
    public void do_logout() {
        shopSearchContract.dashboard_logout();
    }

    @Override
    public void onSuccess() {
        if (shopSearchContract!=null)
            shopSearchIntract.shopsearchAPICall(this);

    }

    @Override
    public void onError(String msg) {
        shopSearchContract.shopsearch_failure(msg);
    }
}
