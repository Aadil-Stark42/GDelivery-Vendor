package in.vdeliverzvendor.myearnings.mvp_orderhistory;


import in.vdeliverzvendor.myearnings.model_orderhistory.OrderHistroyResponse;

public interface OrderHistoryContract {

    void orderhistory_success(OrderHistroyResponse orderHistroyResponse);

    void orderhistory_failure(String msg);

    void dashboard_logout();

    interface GetorderhistoryIntractor {

        interface OnFinishedListener {
            void onFinished(OrderHistroyResponse orderHistroyResponse);
            void onFailure(String error_msg);
            void do_logout();
        }
        void orderhistoryAPICall(GetorderhistoryIntractor.OnFinishedListener onFinishedListener);
    }
}
