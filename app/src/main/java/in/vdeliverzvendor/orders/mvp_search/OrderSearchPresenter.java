package in.vdeliverzvendor.orders.mvp_search;


import in.vdeliverzvendor.orders.model.OrdersListResponse;

public class OrderSearchPresenter implements  OrderSearchContract.GetOrderListsearchIntractor.OnFinishedListener,OrderSearchIntract.OnorderlistsearchListener {

    OrderSearchContract orderSearchContract;
    OrderSearchIntract orderSearchIntract;
    String TAG = OrderSearchPresenter.class.getSimpleName();


    public OrderSearchPresenter(OrderSearchContract orderSearchContract, OrderSearchIntract orderSearchIntract){
        this.orderSearchContract = orderSearchContract;
        this.orderSearchIntract = orderSearchIntract;
    }

    public void validateDetails(String strpage,String strsearch){
        orderSearchIntract.directValidation(strpage,strsearch,this);

    }


    @Override
    public void onFinished(OrdersListResponse ordersListResponse) {
        orderSearchContract.orderListsearch_success(ordersListResponse);
    }

    @Override
    public void onFailure(String error_msg) {
        orderSearchContract.orderListsearch_failure(error_msg);
    }

    @Override
    public void do_logout() {
        orderSearchContract.dashboard_logout();
    }

    @Override
    public void onSuccess() {
        if (orderSearchContract!=null)
            orderSearchIntract.orderListsearchAPICall(this);

    }

    @Override
    public void onError(String msg) {
        orderSearchContract.orderListsearch_failure(msg);
    }
}
