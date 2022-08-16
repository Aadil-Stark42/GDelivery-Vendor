package in.vdeliverzvendor.addvariants.mvp;

import in.vdeliverzvendor.utils.GeneralResponse;

public interface AddStockContract {

    void addstock_failure(String msg);
    void addstock_success(GeneralResponse generalResponse);
    void dashboard_logout();

    interface GetaddstockIntractor {

        interface OnFinishedListener {
            void onFinished(GeneralResponse generalResponse);
            void onFailure(String error_msg);
            void do_logout();
        }
        void addstockAPICall(GetaddstockIntractor.OnFinishedListener onFinishedListener);
    }
}
