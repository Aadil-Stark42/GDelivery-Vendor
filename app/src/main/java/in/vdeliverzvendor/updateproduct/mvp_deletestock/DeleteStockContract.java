package in.vdeliverzvendor.updateproduct.mvp_deletestock;


import in.vdeliverzvendor.utils.GeneralResponse;

public interface DeleteStockContract {

    void deletestock_success(GeneralResponse generalResponse);

    void deletestock_failure(String msg);
    void dashboard_logout();

    interface GetDeleteStockIntractor {

        interface OnFinishedListener {
            void onFinished(GeneralResponse generalResponse);
            void onFailure(String error_msg);
            void do_logout();
        }
        void deletestockAPICall(GetDeleteStockIntractor.OnFinishedListener onFinishedListener);
    }
}
