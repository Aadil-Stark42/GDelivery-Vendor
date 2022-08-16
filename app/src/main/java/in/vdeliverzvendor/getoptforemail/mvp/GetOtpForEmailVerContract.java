package in.vdeliverzvendor.getoptforemail.mvp;

import in.vdeliverzvendor.signup.model.SignupResponse;

public interface GetOtpForEmailVerContract {

    void getemailotp_success(SignupResponse signupResponse);

    void getemailotp_failure(String msg);

    void dashboard_logout();

    interface GetEmailotpIntractor {
        interface OnFinishedListener {
            void onFinished(SignupResponse signupResponse);
            void onFailure(String error_msg);
            void do_logout();
        }
        void getemailotpAPICall(GetEmailotpIntractor.OnFinishedListener onFinishedListener);
    }
}
