package in.vdeliverzvendor.productview.mvp_staus;

import in.vdeliverzvendor.utils.GeneralResponse;

public interface ProductStausChangeContract {

    void productstatus_success(GeneralResponse generalResponse);

    void productstatus_failure(String msg);

    void dashboard_logout();

    interface GetProductStatusIntractor {

        interface OnFinishedListener {
            void onFinished(GeneralResponse generalResponse);
            void onFailure(String error_msg);
            void do_logout();
        }
        void productstatusAPICall(GetProductStatusIntractor.OnFinishedListener onFinishedListener);
    }
}
