package in.vdeliverzvendor.login.mvp;

import in.vdeliverzvendor.login.model.LoginResponse;

public interface LoginContractor {

    void login_success(LoginResponse loginResponse);

    void login_failure(String msg);

    void dashboard_logout();

    interface GetLoginIntractor {

        interface OnFinishedListener {
            void onFinished(LoginResponse loginResponse);
            void onFailure(String error_msg);
            void do_logout();
        }
        void loginAPICall(OnFinishedListener onFinishedListener);
    }
}
