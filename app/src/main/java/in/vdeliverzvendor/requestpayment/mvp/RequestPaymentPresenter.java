package in.vdeliverzvendor.requestpayment.mvp;


import in.vdeliverzvendor.requestpayment.model.PaymentRequestResponse;

public class RequestPaymentPresenter implements  RequestPaymentContract.GetrequestpaymentIntractor.OnFinishedListener,RequestPaymentIntract.OnRequestpaymentListener {

    RequestPaymentContract requestPaymentContract;
    RequestPaymentIntract requestPaymentIntract;
    String TAG = RequestPaymentPresenter.class.getSimpleName();


    public RequestPaymentPresenter(RequestPaymentContract requestPaymentContract, RequestPaymentIntract requestPaymentIntract){
        this.requestPaymentContract = requestPaymentContract;
        this.requestPaymentIntract = requestPaymentIntract;
    }

    public void validateDetails(String strShopId,String strpage){
        requestPaymentIntract.directValidation(strShopId,strpage,this);

    }


    @Override
    public void onFinished(PaymentRequestResponse paymentRequestResponse) {
        requestPaymentContract.requestpayment_success(paymentRequestResponse);

    }

    @Override
    public void onFailure(String error_msg) {
        requestPaymentContract.requestpayment_failure(error_msg);
    }

    @Override
    public void do_logout() {
        requestPaymentContract.dashboard_logout();
    }

    @Override
    public void onSuccess() {
        if (requestPaymentContract!=null)
            requestPaymentIntract.requestpaymentAPICall(this);

    }

    @Override
    public void onError(String msg) {
        requestPaymentContract.requestpayment_failure(msg);
    }
}
