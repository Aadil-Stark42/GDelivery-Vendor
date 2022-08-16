package in.vdeliverzvendor.orders.mvp_status;


import in.vdeliverzvendor.utils.GeneralResponse;

public class OrderStatusPresenter implements  OrderStatusIntract.OnOrderStatusListener,OrderStatucContract.GetorderstatusIntractor.OnFinishedListener {

    OrderStatucContract orderStatucContract;
    OrderStatusIntract orderStatusIntract;
    String TAG = OrderStatusPresenter.class.getSimpleName();


    public OrderStatusPresenter(OrderStatucContract orderStatucContract, OrderStatusIntract orderStatusIntract){
        this.orderStatucContract = orderStatucContract;
        this.orderStatusIntract = orderStatusIntract;
    }

    public void validateDetails(String strOrderId,String strAction){
        orderStatusIntract.directValidation(strOrderId,strAction,this);

    }


    @Override
    public void onFinished(GeneralResponse generalResponse) {
        orderStatucContract.orderstatus_success(generalResponse);

    }

    @Override
    public void onFailure(String error_msg) {
        orderStatucContract.orderstatus_failure(error_msg);

    }

    @Override
    public void do_logout() {
        orderStatucContract.dashboard_logout();
    }

    @Override
    public void onSuccess() {
        if (orderStatucContract!=null)
            orderStatusIntract.orderstatusAPICall(this);

    }

    @Override
    public void onError(String msg) {
        orderStatucContract.orderstatus_failure(msg);

    }
}
