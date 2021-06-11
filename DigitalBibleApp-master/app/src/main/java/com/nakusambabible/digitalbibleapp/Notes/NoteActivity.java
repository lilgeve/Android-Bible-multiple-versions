package com.nakusambabible.digitalbibleapp.Notes;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.nakusambabible.digitalbibleapp.MainActivity;
import com.nakusambabible.digitalbibleapp.PreferenceProvider;
import com.nakusambabible.digitalbibleapp.R;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class NoteActivity extends AppCompatActivity {

    protected PreferenceProvider preferenceProvider;

    private String returnTo;
    private String action;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_activity);

        preferenceProvider = new PreferenceProvider(this);

        Bundle extras = getIntent().getExtras();
        returnTo = extras.getString("returnTo");
        action = extras.getString("action");

        int color = preferenceProvider.getColorVar();
        int actionBarTextSize = preferenceProvider.getActionBarTextSizeVar();

        // Toolbar layout
        Toolbar toolbar = findViewById(R.id.main_toolbar);
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
        textOne.setText(R.string.add_note);
        textOne.setTextColor(Color.parseColor("#FFFFFF"));
        textOne.setTextSize(actionBarTextSize);

        // Action Bar Text Two
        TextView textTwo = toolbar.findViewById(R.id.action_bar_title_two);
        textTwo.setText("");
        textTwo.setTextColor(Color.parseColor("#FFFFFF"));
        textTwo.setTextSize(actionBarTextSize);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.noteFragment, NoteFragment.newInstance(action))
                .commitNow();

    }

    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                this.onBackPressed();
                break;

            default:
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {

        Intent returnIntent = new Intent(NoteActivity.this, MainActivity.class);
        startActivity(returnIntent);

//        switch (returnTo) {
//            case "MainActivity":
//                returnIntent = new Intent(NoteActivity.this, MainActivity.class);
//                break;
//            case "NotesActivity":
//                returnIntent = new Intent(NoteActivity.this, NotesActivity.class);
//                break;
//            case "SearchesActivity":
//                returnIntent = new Intent(NoteActivity.this, SearchesActivity.class);
//                break;
//        }
//
//        startActivity(returnIntent);
    }
}
