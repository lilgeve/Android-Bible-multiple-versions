package com.nakusambabible.digitalbibleapp.Versions;

import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.nakusambabible.digitalbibleapp.R;
import com.nakusambabible.digitalbibleapp.Versions.DB.VersionsEntities;

import java.util.Collections;
import java.util.List;

public class VersionsFragAdapter extends RecyclerView.Adapter<VersionsFragAdapter.CustomViewHolder> {

    private List<VersionsEntities> values = Collections.emptyList(); //cached copy

    private int version;
    private int color;
    private int TextSize;

    public VersionsFragAdapter(int version, int color, int textsize) {

        this.version = version;
        this.color = color;
        this.TextSize = textsize;

    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.versions_item, viewGroup, false);

        return new CustomViewHolder((view));
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int position) {

        final VersionsEntities versionModel = values.get(position);

        boolean status;
        int a = versionModel.getActive();
        status = a == 1;

        customViewHolder.versionName.setText(versionModel.getVerName());
        customViewHolder.versionName.setTextSize(TextSize);
        customViewHolder.versionSwitch.setChecked(status);

        if(versionModel.getNumber() == version) {
            customViewHolder.versionName.setTypeface(null, Typeface.BOLD_ITALIC);
            customViewHolder.versionName.setTextColor(color);
        }

    }

    public static class CustomViewHolder extends RecyclerView.ViewHolder {

        protected TextView versionName;
        protected SwitchCompat versionSwitch;

        public CustomViewHolder(final View view) {
            super(view);

            this.versionName = view.findViewById(R.id.versionName);
            this.versionSwitch = view.findViewById(R.id.versionSwitch);

        }

    }

    public void setValues(List<VersionsEntities> values) {
        this.values = values;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return values.size();
    }


}
