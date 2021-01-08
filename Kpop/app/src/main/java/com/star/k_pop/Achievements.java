package com.star.k_pop;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Achievements extends AppCompatActivity {


    /**
     *
     * @param text текст на ачивке
     * @param drawable id картинки из drawable для ачивки
     */
    public void showToast(String text, int drawable) {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast,
                (ViewGroup) findViewById(R.id.custom_toast_container));

        ImageView image = layout.findViewById(R.id.custom_toast_image);
        image.setImageResource(drawable);

        TextView textView = layout.findViewById(R.id.custom_toast_text);
        textView.setText(text);

        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievements);


        final ImageView ach1 = findViewById(R.id.ach1);
        final ImageView ach2 = findViewById(R.id.ach2);
        final ImageView ach3 = findViewById(R.id.ach3);
        final ImageView ach4 = findViewById(R.id.ach4);

        showToast("Достижение открыто!", R.drawable.achievement);

        Button tempButton = findViewById(R.id.button2); //временная штука, потом будем считывать из Хранилища состояния ачивок
        tempButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showToast("Достижение открыто!", R.drawable.achievement);
                //потом надо будет вместо кнопки сделать функцию обновления ачивок. Если в хранилище будет ачивка True -> открыть картинку. КОнечно же надо будет сначала ачивки сохранить в Хранилище....
                ach1.setImageResource(R.drawable.heart);
                ach2.setImageResource(R.drawable.energy);
                ach3.setImageResource(R.drawable.cubes);
                ach4.setImageResource(R.drawable.star);
            }
        });
    }
}