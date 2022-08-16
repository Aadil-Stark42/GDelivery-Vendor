package in.vdeliverzvendor.productcategory.mvp_productlist;


import in.vdeliverzvendor.productlist.model.ProductListResponse;

public class CatProductListPresenter  implements  CatProductListContract.GetcatproductlistIntractor.OnFinishedListener,CatProductListIntract.OncatproductlistListener{

    CatProductListContract catProductListContract;
    CatProductListIntract catProductListIntract;
    String TAG = CatProductListPresenter.class.getSimpleName();


    public CatProductListPresenter(CatProductListContract catProductListContract, CatProductListIntract catProductListIntract){
        this.catProductListContract = catProductListContract;
        this.catProductListIntract = catProductListIntract;
    }

    public void validateDetails(String strId,String strShopId,String strpage){
        catProductListIntract.directValidation(strId,strShopId,strpage,this);

    }


    @Override
    public void onFinished(ProductListResponse productCatListResponse) {
        catProductListContract.catproductlist_success(productCatListResponse);

    }

    @Override
    public void onFailure(String error_msg) {
        catProductListContract.catproductlist_failure(error_msg);
    }

    @Override
    public void do_logout() {
        catProductListContract.dashboard_logout();
    }

    @Override
    public void onSuccess() {
        if (catProductListContract!=null)
            catProductListIntract.catproductlistAPICall(this);
    }

    @Override
    public void onError(String msg) {
        catProductListContract.catproductlist_failure(msg);
    }
}
