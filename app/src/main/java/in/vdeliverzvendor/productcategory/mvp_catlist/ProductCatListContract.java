package in.vdeliverzvendor.productcategory.mvp_catlist;


import in.vdeliverzvendor.productcategory.model_catlist.ProductCatListResponse;

public interface ProductCatListContract {

    void productcatlist_success(ProductCatListResponse productCatListResponse);

    void productcatlist_failure(String msg);

    void dashboard_logout();

    interface GetproductcatlistIntractor {

        interface OnFinishedListener {
            void onFinished(ProductCatListResponse productCatListResponse);
            void onFailure(String error_msg);
            void do_logout();
        }
        void productcatlistAPICall(GetproductcatlistIntractor.OnFinishedListener onFinishedListener);
    }
}
