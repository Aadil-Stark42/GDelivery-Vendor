package in.vdeliverzvendor.orders.mvp;


import in.vdeliverzvendor.orders.model.OrdersListResponse;

public class OrderListPresenter  implements  OrderListContract.GetOrderListIntractor.OnFinishedListener, OrderListIntract.OnorderlistListener{

    OrderListContract orderListContract;
    OrderListIntract orderListIntract;
    String TAG = OrderListPresenter.class.getSimpleName();


    public OrderListPresenter(OrderListContract orderListContract, OrderListIntract orderListIntract){
        this.orderListContract = orderListContract;
        this.orderListIntract = orderListIntract;
    }

    public void validateDetails(String strpage){
        orderListIntract.directValidation(strpage,this);

    }

    @Override
    public void onFinished(OrdersListResponse ordersListResponse) {
        orderListContract.orderList_success(ordersListResponse);

    }

    @Override
    public void onFailure(String error_msg) {
        orderListContract.orderList_failure(error_msg);

    }

    @Override
    public void do_logout() {
        orderListContract.dashboard_logout();
    }

    @Override
    public void onSuccess() {
        if (orderListContract!=null)
            orderListIntract.orderListAPICall(this);

    }

    @Override
    public void onError(String msg) {
        orderListContract.orderList_failure(msg);

    }
}
