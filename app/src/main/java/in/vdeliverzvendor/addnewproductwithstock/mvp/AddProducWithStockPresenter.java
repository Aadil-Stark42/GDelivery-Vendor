package in.vdeliverzvendor.addnewproductwithstock.mvp;


import java.io.File;

import in.vdeliverzvendor.utils.GeneralResponse;

public class AddProducWithStockPresenter  implements  AddProductwithStockContract.Getaddstockproduct_Intractor.OnFinishedListener,AddProductWithStockIntract.OnAddProductWithStockListener{

    AddProductwithStockContract addProductwithStockContract;
    AddProductWithStockIntract addProductWithStockIntract;
    String TAG = AddProducWithStockPresenter.class.getSimpleName();


    public AddProducWithStockPresenter(AddProductwithStockContract addProductwithStockContract, AddProductWithStockIntract addProductWithStockIntract){
        this.addProductwithStockContract = addProductwithStockContract;
        this.addProductWithStockIntract = addProductWithStockIntract;
    }

    public void directValidation(String strShopId, String strCatId, String strPdCatId, String strCuisineId, String strVarId, String strname, String strDesc, File file,
                                 String strActualPrice, String strSellingPrice, String strAvailable, String strSize, String strUnit, String strVariant,
                                 String strActualPricetop, String stravailabletop, String strtitleid, String strnametop, String strVarianttop){

        addProductWithStockIntract.directValidation(strShopId,strCatId,strPdCatId,strCuisineId,strVarId,strname,strDesc,file,
                strActualPrice,strSellingPrice,strAvailable,strSize,strUnit,strVariant,
                strActualPricetop,stravailabletop,strtitleid,strnametop,strVarianttop,this);

    }


    @Override
    public void onSuccess() {
        if (addProductwithStockContract!=null)
            addProductWithStockIntract.addstockproduct_APICall(this);

    }

    @Override
    public void onError(String msg) {
        addProductwithStockContract.addstockproduct__failure(msg);

    }

    @Override
    public void onFinished(GeneralResponse generalResponse) {
        addProductwithStockContract.addstockproduct_success(generalResponse);

    }

    @Override
    public void onFailure(String error_msg) {
        addProductwithStockContract.addstockproduct__failure(error_msg);
    }

    @Override
    public void do_logout() {
        addProductwithStockContract.dashboard_logout();
    }
}
