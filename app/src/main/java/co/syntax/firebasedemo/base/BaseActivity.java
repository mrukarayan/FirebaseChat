package co.syntax.firebasedemo.base;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by rukarayan on 19-Jan-17.
 */

public class BaseActivity extends AppCompatActivity{

    private ProgressDialog pd;

    public void showProgressDialog(){
        pd = new ProgressDialog(this);
        if(pd == null){
            pd.setCancelable(true);
            pd.setMessage("Working...");

        }
        pd.show();
    }


    public void hideProgressDialog(){
        if(pd != null && pd.isShowing()){
            pd.dismiss();
        }
    }

}
