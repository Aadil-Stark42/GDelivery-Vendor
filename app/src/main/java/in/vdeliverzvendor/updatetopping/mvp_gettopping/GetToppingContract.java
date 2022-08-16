package in.vdeliverzvendor.updatetopping.mvp_gettopping;

import in.vdeliverzvendor.updatetopping.model_gettopping.ToppingDetailResponse;

public interface GetToppingContract {

    void gettopping_success(ToppingDetailResponse toppingDetailResponse);

    void gettopping_failure(String msg);

    void dashboard_logout();

    interface GettoppingIntractor {

        interface OnFinishedListener {
            void onFinished(ToppingDetailResponse toppingDetailResponse);
            void onFailure(String error_msg);
            void do_logout();
        }
        void getstockAPICall(GettoppingIntractor.OnFinishedListener onFinishedListener);
    }
}
