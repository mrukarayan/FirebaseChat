package co.syntax.firebasedemo;

import android.content.Context;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by rukarayan on 05-Jan-17.
 */

public class HelperMethods {

    public static void showToast(Context context, String msg) {
        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
        toast.show();
    }

    public static String getFormatedDate(String strDate, String sourceFormate,
                                         String destinyFormate) {
        SimpleDateFormat df;
        df = new SimpleDateFormat(sourceFormate);
        Date date = null;
        try {
            date = df.parse(strDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        df = new SimpleDateFormat(destinyFormate);
        return df.format(date);

    }
}
