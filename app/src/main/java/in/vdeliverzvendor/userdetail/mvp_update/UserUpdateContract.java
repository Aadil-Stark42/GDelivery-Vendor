package in.vdeliverzvendor.userdetail.mvp_update;


import in.vdeliverzvendor.utils.GeneralResponse;

public interface UserUpdateContract {

    void userupdate_success(GeneralResponse generalResponse);

    void userupdate_failure(String msg);

    void dashboard_logout();

    interface GetuserupdateIntractor {

        interface OnFinishedListener {
            void onFinished(GeneralResponse generalResponse);
            void onFailure(String error_msg);
            void do_logout();
        }
        void userupdateAPICall(GetuserupdateIntractor.OnFinishedListener onFinishedListener);
    }
}
