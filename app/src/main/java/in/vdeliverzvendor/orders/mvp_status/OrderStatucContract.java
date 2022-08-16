package in.vdeliverzvendor.orders.mvp_status;


import in.vdeliverzvendor.utils.GeneralResponse;

public interface OrderStatucContract {

    void orderstatus_success(GeneralResponse generalResponse);

    void orderstatus_failure(String msg);

    void dashboard_logout();

    interface GetorderstatusIntractor {

        interface OnFinishedListener {
            void onFinished(GeneralResponse generalResponse);
            void onFailure(String error_msg);
            void do_logout();
        }
        void orderstatusAPICall(GetorderstatusIntractor.OnFinishedListener onFinishedListener);
    }
}
