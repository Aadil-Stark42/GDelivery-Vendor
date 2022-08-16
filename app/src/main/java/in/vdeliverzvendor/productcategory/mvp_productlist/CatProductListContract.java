package in.vdeliverzvendor.productcategory.mvp_productlist;


import in.vdeliverzvendor.productlist.model.ProductListResponse;

public interface CatProductListContract {

    void catproductlist_success(ProductListResponse catProductListResponse);

    void catproductlist_failure(String msg);

    void dashboard_logout();

    interface GetcatproductlistIntractor {

        interface OnFinishedListener {
            void onFinished(ProductListResponse catProductListResponse);
            void onFailure(String error_msg);
            void do_logout();
        }
        void catproductlistAPICall(GetcatproductlistIntractor.OnFinishedListener onFinishedListener);
    }
}
