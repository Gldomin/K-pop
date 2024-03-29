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
import com.star.k_pop.helper.Storage;

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

        TextView textViewHeader = layout.findViewById(R.id.custom_toast_text_header);
        textViewHeader.setText("");
        TextView textView = layout.findViewById(R.id.custom_toast_text_body);
        textView.setText(text);

        Toast toast = new Toast(act.getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }

    static public boolean achievementGetted(Activity act, int achievementNameStringId, int drawableId, String nameOfAchievement) { //метод для выдачи ачивки. после вызова сохраняет ачивку в хранилище, после чего вызывает Toast с поздравлением и названием ачивки
        //achievementNameStringId это айдишка-указатель на название R.String которое хранит название ачивки
        //drawableId это картинка нужная для сообщения
        //nameOfAchievement это Строка-название по которому идет запись в Хранилище
        Storage storage = new Storage(act.getApplicationContext(), "appStatus");
        if (!storage.getBoolean(nameOfAchievement)) {
            SomeMethods.showAchievementToast(act, act.getResources().getString(R.string.achievementUnlocked), act.getResources().getString(achievementNameStringId), drawableId);
            storage.saveValue(nameOfAchievement, true);

            autoAchieve(act, storage);
            return true;
        }
        return false;
    }

    static public void autoAchieve(Activity act, Storage storage) {
        boolean gsExpert = storage.getBoolean("achGuessStarExpert");
        boolean gbExpert = storage.getBoolean("achGuessBandsModeTwoExpert");
        boolean dbExpert = storage.getBoolean("achSwipeTwoBandsExpert");
        if (gsExpert && gbExpert && dbExpert) {
            achievementGetted(act, R.string.achTripleExpert, R.drawable.kpoplove, "achTripleExpert"); //ачивочка
        }
    }

    static public void showAchievementToast(Activity act, String graceText, String achievementName, int drawable) { //метод для вывода не очень важных сообщений + ачивок
        //специальный Тост для уведомления об ачивке
        LayoutInflater inflater = act.getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast,
                (ViewGroup) act.findViewById(R.id.custom_toast_container));

        ImageView image = layout.findViewById(R.id.custom_toast_image);
        image.setImageResource(drawable);

        TextView textView1 = layout.findViewById(R.id.custom_toast_text_header);
        TextView textView2 = layout.findViewById(R.id.custom_toast_text_body);
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

        AlertDialog.Builder builder = new AlertDialog.Builder(act, R.style.DarkAlertDialog);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(act, R.style.DarkAlertDialog);
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

    public static void showAlertDialog(Activity act, int style, String title, String question, String positive, String negative, DialogInterface.OnClickListener s, DialogInterface.OnClickListener d) {
        AlertDialog.Builder builder = new AlertDialog.Builder(act, style);
        builder.setTitle(title)
                .setMessage(question)
                .setCancelable(false)
                .setPositiveButton(positive, s)
                .setNegativeButton(negative, d);
        AlertDialog alert = builder.create();
        alert.show();
    }
}

