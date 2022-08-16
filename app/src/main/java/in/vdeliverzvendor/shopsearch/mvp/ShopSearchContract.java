package in.vdeliverzvendor.shopsearch.mvp;


import in.vdeliverzvendor.shoplist.model.ShopListResponse;

public interface ShopSearchContract {

    void shopsearch_success(ShopListResponse shopSearchRespone);

    void shopsearch_failure(String msg);

    void dashboard_logout();

    interface GetshopsearchIntractor {

        interface OnFinishedListener {
            void onFinished(ShopListResponse shopSearchRespone);
            void onFailure(String error_msg);
            void do_logout();
        }
        void shopsearchAPICall(GetshopsearchIntractor.OnFinishedListener onFinishedListener);
    }
}
