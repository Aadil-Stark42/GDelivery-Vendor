package in.vdeliverzvendor.addtoppings.mvp_getcat;

import in.vdeliverzvendor.addtoppings.model_getcat.ToppingCatResponse;

public interface GetToppingCatContract {

    void toppingcat_failure(String msg);
    void toppingcat_success(ToppingCatResponse toppingCatResponse);
    void dashboard_logout();

    interface GettoppingCatIntractor {

        interface OnFinishedListener {
            void onFinished(ToppingCatResponse toppingCatResponse);
            void onFailure(String error_msg);
            void do_logout();
        }
        void toppingcatAPICall(GettoppingCatIntractor.OnFinishedListener onFinishedListener);
    }
}
