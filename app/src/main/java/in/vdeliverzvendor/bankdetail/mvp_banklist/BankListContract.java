package in.vdeliverzvendor.bankdetail.mvp_banklist;


import in.vdeliverzvendor.bankdetail.model_banklist.BankListResponse;

public interface BankListContract {

    void banklist_success(BankListResponse bankListResponse);

    void banklist_failure(String msg);

    void dashboard_logout();

    interface GetbankListIntractor {

        interface OnFinishedListener {
            void onFinished(BankListResponse bankListResponse);
            void onFailure(String error_msg);
            void do_logout();
        }
        void banklistAPICall(GetbankListIntractor.OnFinishedListener onFinishedListener);
    }
}
