package in.vdeliverzvendor.productlist.mvp;

import in.vdeliverzvendor.productlist.model.ProductListResponse;

public interface ProductListContract {

    void productlist_success(ProductListResponse productListResponse);

    void productlist_failure(String msg);

    void dashboard_logout();

    interface GetProductlistIntractor {

        interface OnFinishedListener {
            void onFinished(ProductListResponse productListResponse);
            void onFailure(String error_msg);
            void do_logout();
        }
        void productlistAPICall(GetProductlistIntractor.OnFinishedListener onFinishedListener);
    }
}
