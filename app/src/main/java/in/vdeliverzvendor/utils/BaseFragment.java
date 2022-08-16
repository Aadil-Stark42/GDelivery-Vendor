package in.vdeliverzvendor.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class BaseFragment extends Fragment {

    private Dialog mProgressDialog;
    String TAG = BaseFragment.class.getSimpleName();
    static AlertDialog builderObj;
    Context context;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context=context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void showLoading() {
        Log.d(TAG, "showLoading: ");
        if ((mProgressDialog == null || !mProgressDialog.isShowing()))
            mProgressDialog = PackZoProgressDialog.showLoader(getActivity(), false);
    }

    public boolean isNetworkConnected()
    {
        return NetworkConnectivity.checkNow(getActivity());
    }


    public void hideLoading() {
        PackZoProgressDialog.dismissLoader(mProgressDialog);
    }


    @Override
    public void onDestroy() {
        if(mProgressDialog!=null)
            PackZoProgressDialog.dismissLoader(mProgressDialog);
        super.onDestroy();
    }


    public  void callNativeAlert(Context activity, View view, String title, String message, boolean cancelableFlag, String positiveButtonText, String negativeButtonText, String neutralButtonText, final NativeDialogClickListener nativeDialogClickListener)
    {
        try
        {
            if (builderObj != null)
                builderObj.dismiss();

            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            if (view != null)
            {
                builder.setView(view);
            } else
            {
                builder.setTitle(title).setMessage(Html.fromHtml(message));
            }

            builder.setCancelable(cancelableFlag).setPositiveButton(positiveButtonText, new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface dialog, int whichButton)
                {
                    nativeDialogClickListener.onPositive(dialog);
                }
            });
            if (!negativeButtonText.equals(""))
            {
                builder.setNegativeButton(negativeButtonText, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        nativeDialogClickListener.onNegative(dialog);
                    }
                });
            }

            if (!neutralButtonText.equals(""))
            {
                builder.setNeutralButton(neutralButtonText, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        nativeDialogClickListener.onNeutral(dialog);
                    }
                });
            }
            builderObj = builder.create();
            builderObj.show();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
