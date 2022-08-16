package in.vdeliverzvendor.updatetstock.mvp_getstock;


import in.vdeliverzvendor.updatetstock.model_getstock.StockDetailResponse;

public interface GetStockContract {

    void getstock_success(StockDetailResponse stcStockDetailResponse);

    void getstock_failure(String msg);

    void dashboard_logout();

    interface GetstockIntractor {

        interface OnFinishedListener {
            void onFinished(StockDetailResponse StockDetailResponse);
            void onFailure(String error_msg);
            void do_logout();
        }
        void getstockAPICall(GetstockIntractor.OnFinishedListener onFinishedListener);
    }
}
