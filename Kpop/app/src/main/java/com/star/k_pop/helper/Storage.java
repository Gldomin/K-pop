package com.star.k_pop.helper;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Класс для сохранения данных в файле типа ключ-значение
 */
public class Storage {

    Context context; // Context – это объект, который предоставляет доступ к базовым функциям приложения: доступ к ресурсам, к файловой системе, вызов активности
    String nameOfStorage; // Название файла-хранилища

    public Storage(Context context, String nameOfStorage) {
        this.context = context;
        this.nameOfStorage = nameOfStorage;
    }

    //+++++++++++++++++++++++++++++++++VALUE+SAVE+++++++++++++++++++++++++++++++++++++++++

    /**
     * Тип String
     *
     * @param nameOfValue название значения (ключ)
     * @param value       значение для сохраниения
     */
    public void saveValue(String nameOfValue, String value) {
        SharedPreferences sp = context.getSharedPreferences(nameOfStorage, Context.MODE_PRIVATE);
        SharedPreferences.Editor e = sp.edit();
        e.putString(nameOfValue, value);
        e.apply();
    }

    /**
     * Тип Int
     *
     * @param nameOfValue название значения (ключ)
     * @param value       значение для сохраниения
     */
    public void saveValue(String nameOfValue, int value) {
        SharedPreferences sp = context.getSharedPreferences(nameOfStorage, Context.MODE_PRIVATE);
        SharedPreferences.Editor e = sp.edit();
        e.putInt(nameOfValue, value);
        e.apply();
    }

    /**
     * Тип Boolean
     *
     * @param nameOfValue название значения (ключ)
     * @param value       значение для сохраниения
     */
    public void saveValue(String nameOfValue, boolean value) {
        SharedPreferences sp = context.getSharedPreferences(nameOfStorage, Context.MODE_PRIVATE);
        SharedPreferences.Editor e = sp.edit();
        e.putBoolean(nameOfValue, value);
        e.apply();
    }

    /**
     * Тип Float
     *
     * @param nameOfValue название значения (ключ)
     * @param value       значение для сохраниения
     */
    public void saveValue(String nameOfValue, float value) {
        SharedPreferences sp = context.getSharedPreferences(nameOfStorage, Context.MODE_PRIVATE);
        SharedPreferences.Editor e = sp.edit();
        e.putFloat(nameOfValue, value);
        e.apply();
    }

    //+++++++++++++++++++++++++++++++++VALUE+RETURN+++++++++++++++++++++++++++++++++++++++++

    /**
     * Тип String
     *
     * @param nameOfValue название значения (ключ)
     * @return значение
     */
    public String getString(String nameOfValue) {
        String value = "";
        SharedPreferences sp = context.getSharedPreferences(nameOfStorage, Context.MODE_PRIVATE);
        if (sp.contains(nameOfValue)) {
            value = sp.getString(nameOfValue, value);
        }
        return value;
    }

    /**
     * Тип Int
     *
     * @param nameOfValue название значения (ключ)
     * @return значение
     */
    public int getInt(String nameOfValue) {
        int value = 0;
        SharedPreferences sp = context.getSharedPreferences(nameOfStorage, Context.MODE_PRIVATE);
        if (sp.contains(nameOfValue)) {
            value = sp.getInt(nameOfValue, value);
        }
        return value;
    }

    /**
     * Тип Boolean
     *
     * @param nameOfValue название значения (ключ)
     * @return значение
     */
    public boolean getBoolean(String nameOfValue) {
        boolean value = false;
        SharedPreferences sp = context.getSharedPreferences(nameOfStorage, Context.MODE_PRIVATE);
        if (sp.contains(nameOfValue)) {
            value = sp.getBoolean(nameOfValue, false);
        }
        return value;
    }

    /**
     * Тип Float
     *
     * @param nameOfValue название значения (ключ)
     * @return значение
     */
    public float getFloat(String nameOfValue) {
        float value = 0f;
        SharedPreferences sp = context.getSharedPreferences(nameOfStorage, Context.MODE_PRIVATE);
        if (sp.contains(nameOfValue)) {
            value = sp.getFloat(nameOfValue, value);
        }
        return value;
    }
}
