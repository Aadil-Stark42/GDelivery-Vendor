package in.vdeliverzvendor.updatetopping.mvp_updatetopping;

import in.vdeliverzvendor.utils.GeneralResponse;

public interface UpdateToppingContract {

    void updatetopping_success(GeneralResponse loginResponse);

    void updatetopping_failure(String msg);

    void dashboard_logout();

    interface updatetoppingProductIntractor {

        interface OnFinishedListener {
            void onFinished(GeneralResponse generalResponse);
            void onFailure(String error_msg);
            void do_logout();

        }
        void updatetoppingAPICall(updatetoppingProductIntractor.OnFinishedListener onFinishedListener);
    }
}
