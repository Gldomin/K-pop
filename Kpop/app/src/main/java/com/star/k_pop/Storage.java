package com.star.k_pop;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.Nullable;

public class Storage {
    Context context;

    public Storage(Context context) {
        this.context = context;
    }

    //+++++++++++++++++++++++++++++++++VALUE+SAVE+++++++++++++++++++++++++++++++++++++++++
    //куча перегрузок для разных типов
    //Overload!
    public void saveValue(String nameOfVault, String nameOfValue, String value) {
//сохранение значений в файле-хранилище
        SharedPreferences sp = context.getSharedPreferences(nameOfVault, Context.MODE_PRIVATE);
        SharedPreferences.Editor e = sp.edit();
        e.putString(nameOfValue, value);
        e.apply();
    }

    public void saveValue(String nameOfVault, String nameOfValue, Integer value) {
//сохранение значений в файле-хранилище
        SharedPreferences sp = context.getSharedPreferences(nameOfVault, Context.MODE_PRIVATE);
        SharedPreferences.Editor e = sp.edit();
        e.putInt(nameOfValue, value);
        e.apply();
    }


    public void saveValue(String nameOfVault, String nameOfValue, Boolean value) {
//сохранение значений в файле-хранилище
        SharedPreferences sp = context.getSharedPreferences(nameOfVault, Context.MODE_PRIVATE);
        SharedPreferences.Editor e = sp.edit();
        e.putBoolean(nameOfValue, value);
        e.apply();
    }

    public void saveValue(String nameOfVault, String nameOfValue, Float value) {
//сохранение значений в файле-хранилище
        SharedPreferences sp = context.getSharedPreferences(nameOfVault, Context.MODE_PRIVATE);
        SharedPreferences.Editor e = sp.edit();
        e.putFloat(nameOfValue, value);
        e.apply();
    }

    //+++++++++++++++++++++++++++++++++VALUE+RETURN+++++++++++++++++++++++++++++++++++++++++
    //context важен, так как без него не заработает SharedPreferences, так как последний хочет рабить после Create

    public String getString(String nameOfStorage, String nameOfValue) {
        //nameOfStorage - это название файла-хранилища, а nameOfValue - название значения, что хранится в файле-хранилище
        String value = "";
        SharedPreferences sp = context.getSharedPreferences(nameOfStorage, Context.MODE_PRIVATE);
        if (sp.contains(nameOfValue)) {
            value = sp.getString(nameOfValue, value); //взятие settings из Хранилища

        } else {

            // saveValue(nameOfStorage, nameOfValue);

            throw new MissedValueException();
        }


        return value;
    }

    public Boolean getBoolean(String nameOfStorage, String nameOfValue) {
        //nameOfStorage - это название файла-хранилища, а nameOfValue - название значения, что хранится в файле-хранилище
        Boolean value = false;
        SharedPreferences sp = context.getSharedPreferences(nameOfStorage, Context.MODE_PRIVATE);
        if (sp.contains(nameOfValue)) {
            value = sp.getBoolean(nameOfValue, value); //взятие settings из Хранилища

        } else {

            // saveValue(nameOfStorage, nameOfValue);

            throw new MissedValueException();
        }


        return value;
    }


    public Integer getInt(String nameOfStorage, String nameOfValue) {
        //nameOfStorage - это название файла-хранилища, а nameOfValue - название значения, что хранится в файле-хранилище
        Integer value = 0;
        SharedPreferences sp = context.getSharedPreferences(nameOfStorage, Context.MODE_PRIVATE);
        if (sp.contains(nameOfValue)) {
            value = sp.getInt(nameOfValue, value); //взятие settings из Хранилища

        } else {

            // saveValue(nameOfStorage, nameOfValue);

            throw new MissedValueException();
        }


        return value;
    }

    public Float getFloat(String nameOfStorage, String nameOfValue) {
        //nameOfStorage - это название файла-хранилища, а nameOfValue - название значения, что хранится в файле-хранилище
        Float value = 0f;
        SharedPreferences sp = context.getSharedPreferences(nameOfStorage, Context.MODE_PRIVATE);
        if (sp.contains(nameOfValue)) {
            value = sp.getFloat(nameOfValue, value); //взятие settings из Хранилища

        } else {

            // saveValue(nameOfStorage, nameOfValue);

            throw new MissedValueException();
        }


        return value;
    }


    static class MissedValueException extends NullPointerException {

        @Nullable
        @Override
        public String getMessage() {
            return "Chosen Value not found in Vault";
        }
    }

}
