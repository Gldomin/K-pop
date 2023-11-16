package com.star.k_pop.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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
        textView1.setTextColor(theme.getTextColor());

        int idStringBig = getIntent().getIntExtra("title", 0);
        TextView textView2 = findViewById(R.id.abautTitleText);
        textView2.setText(idStringBig);
        textView2.setTextColor(theme.getTextColor());
        View.OnClickListener listener = view -> finish();
        textView2.setOnClickListener(listener);
        textView1.setOnClickListener(listener);

    }
}