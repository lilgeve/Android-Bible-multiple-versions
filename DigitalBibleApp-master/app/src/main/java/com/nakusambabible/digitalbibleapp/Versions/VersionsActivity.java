package com.nakusambabible.digitalbibleapp.Versions;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.nakusambabible.digitalbibleapp.PreferenceProvider;
import com.nakusambabible.digitalbibleapp.R;

public class VersionsActivity extends AppCompatActivity {

    protected PreferenceProvider preferenceProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.versions_activity);

        preferenceProvider = new PreferenceProvider(this);

        int color = preferenceProvider.getColorVar();
        int actionBarTextSize = preferenceProvider.getActionBarTextSizeVar();

        // Toolbar layout
        Toolbar toolbar = findViewById(R.id.versions_toolbar);
        setSupportActionBar(toolbar);

        // custom title bar
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.toolbar_activity);

        // action bar
        ActionBar ab = getSupportActionBar();
        // back arrow
        ab.setDisplayHomeAsUpEnabled(true);
        // change toolbar background colour
        ab.setBackgroundDrawable(new ColorDrawable(color));

        // Action Bar Text One
        TextView textOne = toolbar.findViewById(R.id.action_bar_title_one);
        textOne.setText(R.string.activate_versions);
        textOne.setTextColor(Color.parseColor("#FFFFFF"));
        textOne.setTextSize(actionBarTextSize);

        // Action Bar Text Two
        TextView textTwo = toolbar.findViewById(R.id.action_bar_title_two);
        textTwo.setText("");
        textTwo.setTextColor(Color.parseColor("#FFFFFF"));
        textTwo.setTextSize(actionBarTextSize);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.versionsFragment, VersionsFragment.newInstance())
                .commitNow();

    }

}
