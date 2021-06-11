package com.nakusambabible.digitalbibleapp.Searches;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import com.nakusambabible.digitalbibleapp.Active.ActiveFragmentAdapter;
import com.nakusambabible.digitalbibleapp.Common.DividerLineDecoration;
import com.nakusambabible.digitalbibleapp.Common.RecyclerTouchListener;
import com.nakusambabible.digitalbibleapp.PreferenceProvider;
import com.nakusambabible.digitalbibleapp.R;
import com.nakusambabible.digitalbibleapp.Versions.DB.VersionsEntities;
import com.nakusambabible.digitalbibleapp.Versions.VersionsViewModel;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SearchesActiveSheet extends BottomSheetDialogFragment {

    PreferenceProvider preferenceProvider;

    private VersionsViewModel versionsViewModel;

    private RecyclerView recyclerView;
    private ActiveFragmentAdapter activeFragmentAdapter;

    private List<VersionsEntities> values;
    private int textSize;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SearchesActivity.getInstance().searchView.clearFocus();

    }

    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);

        preferenceProvider = new PreferenceProvider(getContext());

        textSize = preferenceProvider.gettextSizeVar();

        @SuppressLint("InflateParams")
        View view = LayoutInflater.from(getContext()).inflate(R.layout.active_fragment, null);

        dialog.setContentView(view);

        ((View) view.getParent()).setBackgroundColor(Color.TRANSPARENT);

        int version = preferenceProvider.getVersion();

        recyclerView = view.findViewById(R.id.ActiveFragmentRecyclerView);

        activeFragmentAdapter = new ActiveFragmentAdapter(textSize);

        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerLineDecoration(getActivity(), LinearLayoutManager.VERTICAL, 16));
        recyclerView.setAdapter(activeFragmentAdapter);

        versionsViewModel = ViewModelProviders.of(getActivity()).get(VersionsViewModel.class);

        versionsViewModel.getActiveVersions(version).observe(getActivity(), new Observer<List<VersionsEntities>>() {
            @Override
            public void onChanged(List<VersionsEntities> versionsEntities) {
                activeFragmentAdapter.setValues(versionsEntities);
                values = versionsEntities;
            }
        });

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(),
                recyclerView, new RecyclerTouchListener.ClickListener() {

            // short click
            public void onClick(View view, int position) {

                dismiss();

                final VersionsEntities versionsEntities = values.get(position);

                preferenceProvider.setVersion(versionsEntities.getNumber());

                getActivity().finish();
                startActivity(getActivity().getIntent());

            }

            // long click
            public void onLongClick(View view, int position) {
                dismiss();
            }
                // do nothing
        }));

    }

}
