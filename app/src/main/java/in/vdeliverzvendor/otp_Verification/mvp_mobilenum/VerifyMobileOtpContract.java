package in.vdeliverzvendor.otp_Verification.mvp_mobilenum;

import in.vdeliverzvendor.utils.GeneralResponse;

public interface VerifyMobileOtpContract {

    void verifymobileotp_success(GeneralResponse generalResponse);

    void verifymobileotp_failure(String msg);

    void dashboard_logout();

    interface GetVerifyOtpIntractor {
        interface OnFinishedListener {
            void onFinished(GeneralResponse generalResponse);
            void onFailure(String error_msg);
            void do_logout();
        }
        void verifymobileotpAPICall(OnFinishedListener onFinishedListener);
    }

}
