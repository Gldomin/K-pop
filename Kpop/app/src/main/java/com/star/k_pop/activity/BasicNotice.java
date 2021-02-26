package com.star.k_pop.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.star.k_pop.R;
import com.star.k_pop.helper.Theme;

public class BasicNotice extends AppCompatActivity {

    Theme theme;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        theme = new Theme(this);
        setTheme(theme.getAlertDialogStyle());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_notice);

        int idStringSmall = getIntent().getIntExtra("text", 0);
        TextView textView1 = findViewById(R.id.abautSmallText);
        textView1.setText(idStringSmall);

        int idStringBig = getIntent().getIntExtra("title", 0);
        TextView textView2 = findViewById(R.id.abautTitleText);
        textView2.setText(idStringBig);
    }
}