package in.vdeliverzvendor.resetpassword.mvp;


import in.vdeliverzvendor.utils.GeneralResponse;

public interface ResetPasswordContract {

    void resetpassword_success(GeneralResponse generalResponse);

    void resetpassword_failure(String msg);

    void dashboard_logout();

    interface GetresetpasswordIntractor {

        interface OnFinishedListener {
            void onFinished(GeneralResponse generalResponse);
            void onFailure(String error_msg);
            void do_logout();
        }
        void resetpasswordAPICall(GetresetpasswordIntractor.OnFinishedListener onFinishedListener);
    }
}
