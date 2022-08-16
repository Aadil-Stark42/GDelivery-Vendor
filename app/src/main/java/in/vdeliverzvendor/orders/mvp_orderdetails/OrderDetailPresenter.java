package in.vdeliverzvendor.orders.mvp_orderdetails;


import in.vdeliverzvendor.orders.model_ordredetails.OrderDetailResponse;

public class OrderDetailPresenter implements  OrderDetailIntract.OnOrderDetailListener,OrderDetailContract.GetorderdetailIntractor.OnFinishedListener {

    OrderDetailContract orderDetailContract;
    OrderDetailIntract orderDetailIntract;
    String TAG = OrderDetailPresenter.class.getSimpleName();


    public OrderDetailPresenter(OrderDetailContract orderDetailContract, OrderDetailIntract orderDetailIntract){
        this.orderDetailContract = orderDetailContract;
        this.orderDetailIntract = orderDetailIntract;
    }

    public void validateDetails(String stremail){
        orderDetailIntract.directValidation(stremail,this);

    }


    @Override
    public void onFinished(OrderDetailResponse orderDetailResponse) {
        orderDetailContract.orderdetail_success(orderDetailResponse);

    }

    @Override
    public void onFailure(String error_msg) {
        orderDetailContract.orderdetail_failure(error_msg);

    }

    @Override
    public void do_logout() {
        orderDetailContract.dashboard_logout();
    }

    @Override
    public void onSuccess() {
        if (orderDetailContract!=null)
            orderDetailIntract.orderdetailAPICall(this);

    }

    @Override
    public void onError(String msg) {
        orderDetailContract.orderdetail_failure(msg);

    }
}
