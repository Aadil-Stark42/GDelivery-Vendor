package in.vdeliverzvendor.addtoppings.mvp;

import in.vdeliverzvendor.utils.GeneralResponse;

public interface AddToppingContract {

    void addtopping_failure(String msg);
    void addtopping_success(GeneralResponse generalResponse);
    void dashboard_logout();

    interface GetaddtoppingIntractor {

        interface OnFinishedListener {
            void onFinished(GeneralResponse generalResponse);
            void onFailure(String error_msg);
            void do_logout();
        }
        void addtoppingAPICall(GetaddtoppingIntractor.OnFinishedListener onFinishedListener);
    }
}
