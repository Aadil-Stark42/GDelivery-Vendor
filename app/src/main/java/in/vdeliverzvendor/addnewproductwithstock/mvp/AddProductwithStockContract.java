package in.vdeliverzvendor.addnewproductwithstock.mvp;


import in.vdeliverzvendor.utils.GeneralResponse;

public interface AddProductwithStockContract {

    void addstockproduct_success(GeneralResponse generalResponse);

    void addstockproduct__failure(String msg);

    void dashboard_logout();

    interface Getaddstockproduct_Intractor {

        interface OnFinishedListener {
            void onFinished(GeneralResponse generalResponse);
            void onFailure(String error_msg);
            void do_logout();
        }
        void addstockproduct_APICall(Getaddstockproduct_Intractor.OnFinishedListener onFinishedListener);
    }

}
