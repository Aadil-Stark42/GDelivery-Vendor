package in.vdeliverzvendor.productsearch.mvp;


import in.vdeliverzvendor.productlist.model.ProductListResponse;

public interface ProductSearchContract {

    void productsearch_success(ProductListResponse productListResponse);

    void productsearch_failure(String msg);

    void dashboard_logout();

    interface GetproductsearchIntractor {

        interface OnFinishedListener {
            void onFinished(ProductListResponse productListResponse);
            void onFailure(String error_msg);
            void do_logout();
        }
        void productsearchAPICall(GetproductsearchIntractor.OnFinishedListener onFinishedListener);
    }
}
