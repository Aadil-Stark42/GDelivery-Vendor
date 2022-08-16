package in.vdeliverzvendor.productview.mvp_delete;


import in.vdeliverzvendor.utils.GeneralResponse;

public interface ProductDeleteContract {

    void productdelete_success(GeneralResponse generalResponse);

    void productdelete_failure(String msg);

    void dashboard_logout();

    interface GetProductdeleteIntractor {

        interface OnFinishedListener {
            void onFinished(GeneralResponse generalResponse);
            void onFailure(String error_msg);
            void do_logout();
        }
        void productdeleteAPICall(GetProductdeleteIntractor.OnFinishedListener onFinishedListener);
    }
}
