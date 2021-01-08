package com.star.k_pop.lib;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.star.k_pop.R;

public class SomeMethods {
    /**
     * @param text     текст на ачивке
     * @param drawable id картинки из drawable для ачивки
     */
    static public void showToast(Activity app, String text, int drawable) {

        LayoutInflater inflater = app.getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast,
                (ViewGroup) app.findViewById(R.id.custom_toast_container));

        ImageView image = layout.findViewById(R.id.custom_toast_image);
        image.setImageResource(drawable);

        TextView textView = layout.findViewById(R.id.custom_toast_text);
        textView.setText(text);

        Toast toast = new Toast(app.getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }
}
