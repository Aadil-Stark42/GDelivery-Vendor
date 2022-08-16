package in.vdeliverzvendor.productview.mvp;


import in.vdeliverzvendor.productview.model.ProductDetailResponse;

public interface ProductViewContract {

    void productview_success(ProductDetailResponse productDetailResponse);

    void productview_failure(String msg);

    void dashboard_logout();

    interface GetProductviewIntractor {

        interface OnFinishedListener {
            void onFinished(ProductDetailResponse productDetailResponse);
            void onFailure(String error_msg);
            void do_logout();
        }
        void productviewAPICall(GetProductviewIntractor.OnFinishedListener onFinishedListener);
    }
}
