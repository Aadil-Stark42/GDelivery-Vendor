package in.vdeliverzvendor.shopoverview.mvp_product;


import in.vdeliverzvendor.shopoverview.model_product.ShopProductListResponse;

public interface ShopProductContract {

    void shopproductlist_success(ShopProductListResponse shopProductListResponse);

    void shopproductlist_failure(String msg);

    void dashboard_logout();

    interface GetshopproductlistIntractor {

        interface OnFinishedListener {
            void onFinished(ShopProductListResponse shopProductListResponse);
            void onFailure(String error_msg);
            void do_logout();
        }
        void shopproductlistAPICall(GetshopproductlistIntractor.OnFinishedListener onFinishedListener);
    }
}
