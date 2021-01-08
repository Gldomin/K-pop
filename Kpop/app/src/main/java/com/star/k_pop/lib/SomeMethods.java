package com.star.k_pop.lib;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.star.k_pop.R;

import static android.app.PendingIntent.getActivity;

public class SomeMethods {
    /**
     * @param text     текст на ачивке
     * @param drawable id картинки из drawable для ачивки
     */
    static public void showToast(Activity act, String text, int drawable) {

        LayoutInflater inflater = act.getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast,
                (ViewGroup) act.findViewById(R.id.custom_toast_container));

        ImageView image = layout.findViewById(R.id.custom_toast_image);
        image.setImageResource(drawable);

        TextView textView = layout.findViewById(R.id.custom_toast_text);
        textView.setText(text);

        Toast toast = new Toast(act.getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }


    public static void showAlertDeialog(final Activity act, String title, String question, final String first, final String second) {
        AlertDialog.Builder builder = new AlertDialog.Builder(act);
        builder.setTitle(title)
                .setMessage(question)
                .setCancelable(false)
                .setNegativeButton(first, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(act, first,
                                Toast.LENGTH_LONG).show();
                    }
                })
                .setPositiveButton(second, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Toast.makeText(act, second,
                                Toast.LENGTH_LONG).show();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}

