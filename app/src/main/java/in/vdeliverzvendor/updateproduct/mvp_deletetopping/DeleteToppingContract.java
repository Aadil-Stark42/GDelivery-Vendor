package in.vdeliverzvendor.updateproduct.mvp_deletetopping;

import in.vdeliverzvendor.utils.GeneralResponse;

public interface DeleteToppingContract {

    void deletetopping_success(GeneralResponse generalResponse);

    void deletetopping_failure(String msg);

    void dashboard_logout();

    interface GetDeleteStockIntractor {

        interface OnFinishedListener {
            void onFinished(GeneralResponse generalResponse);
            void onFailure(String error_msg);
            void do_logout();
        }
        void deletetoppingAPICall(GetDeleteStockIntractor.OnFinishedListener onFinishedListener);
    }
}
