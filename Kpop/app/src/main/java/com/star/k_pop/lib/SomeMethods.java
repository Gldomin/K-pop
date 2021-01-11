package com.star.k_pop.lib;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.star.k_pop.GuessStar;
import com.star.k_pop.R;
import com.star.k_pop.Storage;

import static android.app.PendingIntent.getActivity;

public class SomeMethods {
    /**
     * @param text     текст на ачивке
     * @param drawable id картинки из drawable для ачивки
     */
    static public void showToast(Activity act, String text, int drawable) { //метод для вывода не очень важных сообщений + ачивок

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


    static public void achievementGetted(Activity act, int achievementNameStringId, int drawableId, String nameOfAchievement) { //метод для выдачи ачивки. после вызова сохраняет ачивку в хранилище, после чего вызывает Toast с поздравлением и названием ачивки
        //achievementNameStringId это айдишка-указатель на название R.String которое хранит название ачивки
        //drawableId это картинка нужная для сообщения
        //nameOfAchievement это Строка-название по которому идет запись в Хранилище
        Storage storage = new Storage(act.getApplicationContext());

        String nameOfStorage = "appStatus";
        if (!storage.getBoolean(nameOfStorage, nameOfAchievement)) {
            SomeMethods.showAchievementToast(act, act.getResources().getString(R.string.achievementUnlocked), act.getResources().getString(achievementNameStringId), drawableId);
            storage.saveValue(nameOfStorage, nameOfAchievement, true);
        }
    }

    static public void showAchievementToast(Activity act, String graceText, String achievementName, int drawable) { //метод для вывода не очень важных сообщений + ачивок
        //специальный Тост для уведомления об ачивке
        LayoutInflater inflater = act.getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast,
                (ViewGroup) act.findViewById(R.id.custom_toast_container));

        ImageView image = layout.findViewById(R.id.custom_toast_image);
        image.setImageResource(drawable);

        TextView textView1 = layout.findViewById(R.id.custom_toast_text);
        TextView textView2 = layout.findViewById(R.id.custom_toast_text);
        textView1.setText(graceText);
        textView2.setText(achievementName);
        Toast toast = new Toast(act.getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }


    public static String result = "resultHasNotSet"; //для хранения результата
    public static Boolean boolResult = false; //для хранения результата
    static Boolean answerGetted = false; //для учета нажатия кнопки

    public static String getStringAnswerAlertDeialog(final Activity act, String title, String question, final String first, final String second) { //метод для отображения простых окон да/нет или типа того. вариация работает со стрингами, главное быть осторожным на счет локализации

        AlertDialog.Builder builder = new AlertDialog.Builder(act);
        builder.setTitle(title)
                .setMessage(question)
                .setCancelable(false)
                .setNegativeButton(first, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        result = first;
                        Toast.makeText(act, result,
                                Toast.LENGTH_LONG).show();
                    }
                })
                .setPositiveButton(second, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        result = second;
                        Toast.makeText(act, result,
                                Toast.LENGTH_LONG).show();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
        return result;
    }


    public static Boolean getBoolAnswerAlertDeialog(final Activity act, String title, String question, final String first, final String second) { //метод для отображения простых окон да/нет или типа того. вариация работает со стрингами, главное быть осторожным на счет локализации
        answerGetted = false;
        AlertDialog.Builder builder = new AlertDialog.Builder(act);
        builder.setTitle(title)
                .setMessage(question)
                .setCancelable(false)
                .setNegativeButton(first, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        boolResult = true;
                        Toast.makeText(act, "true",
                                Toast.LENGTH_LONG).show();
                        answerGetted = true;
                    }
                })
                .setPositiveButton(second, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        boolResult = false;
                        Toast.makeText(act, "false",
                                Toast.LENGTH_LONG).show();
                        answerGetted = true;

                    }
                });
        AlertDialog alert = builder.create();
        alert.show();

        return boolResult;
    }

    public static void showAlertDeialog(final Activity act, String title, String question, final String first, final String second) { //метод для отображения простых окон да/нет или типа того
        AlertDialog.Builder builder = new AlertDialog.Builder(act);
        builder.setTitle(title)
                .setMessage(question)
                .setCancelable(false)
                .setNegativeButton(first, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        result = first;
                        Toast.makeText(act, result,
                                Toast.LENGTH_LONG).show();
                    }
                })
                .setPositiveButton(second, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        result = second;
                        Toast.makeText(act, result,
                                Toast.LENGTH_LONG).show();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

}

