package in.vdeliverzvendor.bankdetail.mvp_withdraw;


import in.vdeliverzvendor.bankdetail.model_withdraw.WithDrawResponse;

public interface WithdrawContract {

    void withdraw_success(WithDrawResponse withDrawResponse);

    void withdraw_failure(String msg);

    void dashboard_logout();

    interface GetwithdrawIntractor {

        interface OnFinishedListener {
            void onFinished(WithDrawResponse withDrawResponse);
            void onFailure(String error_msg);
            void do_logout();
        }
        void withdrawAPICall(GetwithdrawIntractor.OnFinishedListener onFinishedListener);
    }
}
