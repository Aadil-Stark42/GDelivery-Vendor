package in.vdeliverzvendor.updatetstock.mvp_update;


import in.vdeliverzvendor.utils.GeneralResponse;

public interface UpdateStockContract {

    void updatestock_success(GeneralResponse loginResponse);

    void updatestock_failure(String msg);

    void dashboard_logout();

    interface updatestockProductIntractor {

        interface OnFinishedListener {
            void onFinished(GeneralResponse generalResponse);
            void onFailure(String error_msg);
            void do_logout();
        }
        void updatestockAPICall(updatestockProductIntractor.OnFinishedListener onFinishedListener);
    }
}
