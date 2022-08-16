package in.vdeliverzvendor.topping.mvp;


import in.vdeliverzvendor.utils.GeneralResponse;

public interface CreateToppingContract {

    void createtopping_success(GeneralResponse generalResponse);

    void createtopping_failure(String msg);

    void dashboard_logout();

    interface Getcreatetopping_Intractor {

        interface OnFinishedListener {
            void onFinished(GeneralResponse generalResponse);
            void onFailure(String error_msg);
            void do_logout();
        }
        void createtoppingAPICall(Getcreatetopping_Intractor.OnFinishedListener onFinishedListener);
    }
}
