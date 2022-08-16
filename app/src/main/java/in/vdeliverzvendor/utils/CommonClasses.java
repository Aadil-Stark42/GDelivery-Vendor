package in.vdeliverzvendor.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Html;
import android.view.View;

public class CommonClasses {
    static AlertDialog builderObj;

    public    static void callNativeAlert(Context activity, View view, String title, String message, boolean cancelableFlag, String positiveButtonText, String negativeButtonText, String neutralButtonText, final NativeDialogClickListener nativeDialogClickListener)
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
