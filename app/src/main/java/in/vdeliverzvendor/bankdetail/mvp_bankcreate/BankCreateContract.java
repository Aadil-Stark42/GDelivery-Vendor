package in.vdeliverzvendor.bankdetail.mvp_bankcreate;


import in.vdeliverzvendor.utils.GeneralResponse;

public interface BankCreateContract {

    void cratebank_success(GeneralResponse generalResponse);

    void cratebank_failure(String msg);

    void dashboard_logout();

    interface GetcratebankIntractor {

        interface OnFinishedListener {
            void onFinished(GeneralResponse generalResponse);
            void onFailure(String error_msg);
            void do_logout();
        }
        void cratebankAPICall(GetcratebankIntractor.OnFinishedListener onFinishedListener);
    }
}
