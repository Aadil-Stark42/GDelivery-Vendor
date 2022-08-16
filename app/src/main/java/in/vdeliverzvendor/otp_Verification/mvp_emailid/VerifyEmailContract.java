package in.vdeliverzvendor.otp_Verification.mvp_emailid;


import in.vdeliverzvendor.utils.GeneralResponse;

public interface VerifyEmailContract {
    void verifyemailotp_success(GeneralResponse generalResponse);

    void verifyemailotp_failure(String msg);

    void dashboard_logout();

    interface GetVerifyOtpIntractor {
        interface OnFinishedListener {
            void onFinished(GeneralResponse generalResponse);
            void onFailure(String error_msg);
            void do_logout();
        }
        void verifyemailotpAPICall(GetVerifyOtpIntractor.OnFinishedListener onFinishedListener);
    }

}
