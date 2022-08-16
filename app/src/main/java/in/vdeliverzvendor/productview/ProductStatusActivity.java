package in.vdeliverzvendor.productview;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.firebase.crashlytics.FirebaseCrashlytics;

import java.util.Objects;

import in.vdeliverzvendor.R;
public class ProductStatusActivity extends AppCompatActivity {

    ImageView iv_menu;
    RelativeLayout rl_edit,rl_edits,rl_delete;
    ConstraintLayout consmain;
    ImageView iv_back;
    String TAG= ProductStatusActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_status);

        try{

        iv_menu=findViewById(R.id.iv_menu);
        rl_edit=findViewById(R.id.rl_edit);
        consmain=findViewById(R.id.consmain);
        rl_edits=findViewById(R.id.rl_edits);
        rl_delete=findViewById(R.id.rl_delete);
        iv_back=findViewById(R.id.iv_back);

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



        iv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl_edit.setVisibility(View.VISIBLE);
            }
        });

        consmain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl_edit.setVisibility(View.GONE);
            }
        });

        rl_edits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent myintent=new Intent(ProductStatusActivity.this, ProductviewActivity.class );
                startActivity(myintent);
                rl_edit.setVisibility(View.GONE);

            }
        });
        rl_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rl_edit.setVisibility(View.GONE);
                showCustomDialog();
            }
        });

        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " + Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "careet e: errorr rr + "+e.getMessage());
        }


    }
    private void showCustomDialog() {

        try{

        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.popup_deleteproduct, viewGroup, false);
        TextView tv_deletes=dialogView.findViewById(R.id.tv_deletes);
        TextView tv_cancel=dialogView.findViewById(R.id.tv_cancel);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        tv_deletes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        }catch (Exception e){
            FirebaseCrashlytics.getInstance().log(TAG+ " " +Objects.requireNonNull(e.getMessage()));
            Log.d(TAG, "dsad asd d: errorr rr + "+e.getMessage());
        }
    }

}
