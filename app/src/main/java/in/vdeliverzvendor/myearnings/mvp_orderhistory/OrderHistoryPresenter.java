package in.vdeliverzvendor.myearnings.mvp_orderhistory;


import in.vdeliverzvendor.myearnings.model_orderhistory.OrderHistroyResponse;

public class OrderHistoryPresenter implements  OrderHistoryContract.GetorderhistoryIntractor.OnFinishedListener,OrderHistoryIntract.OnOrderHistoryListener {

    OrderHistoryContract orderHistoryContract;
    OrderHistoryIntract orderHistoryIntract;
    String TAG = OrderHistoryPresenter.class.getSimpleName();


    public OrderHistoryPresenter(OrderHistoryContract orderHistoryContract, OrderHistoryIntract orderHistoryIntract){
        this.orderHistoryContract = orderHistoryContract;
        this.orderHistoryIntract = orderHistoryIntract;
    }

    public void validateDetails(String strShopId){
        orderHistoryIntract.directValidation(strShopId,this);

    }


    @Override
    public void onFinished(OrderHistroyResponse orderHistroyResponse) {
        orderHistoryContract.orderhistory_success(orderHistroyResponse);

    }

    @Override
    public void onFailure(String error_msg) {
        orderHistoryContract.orderhistory_failure(error_msg);

    }

    @Override
    public void do_logout() {
        orderHistoryContract.dashboard_logout();
    }

    @Override
    public void onSuccess() {
        if (orderHistoryContract!=null)
            orderHistoryIntract.orderhistoryAPICall(this);
    }

    @Override
    public void onError(String msg) {
        orderHistoryContract.orderhistory_failure(msg);

    }
}
