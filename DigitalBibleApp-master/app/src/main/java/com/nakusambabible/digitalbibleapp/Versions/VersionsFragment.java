package com.nakusambabible.digitalbibleapp.Versions;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nakusambabible.digitalbibleapp.Common.DividerLineDecoration;
import com.nakusambabible.digitalbibleapp.Common.RecyclerTouchListener;
import com.nakusambabible.digitalbibleapp.PreferenceProvider;
import com.nakusambabible.digitalbibleapp.R;
import com.nakusambabible.digitalbibleapp.Versions.DB.VersionsEntities;
import com.nakusambabible.digitalbibleapp.Versions.DB.VersionsRepository;

import java.util.List;

public class VersionsFragment extends Fragment {

    View view;

    PreferenceProvider preferenceProvider;

    protected VersionsRepository versionsRepository;

    List<VersionsEntities> values;

    private int version;
    private int color;
    private int textSize;
    private SwitchCompat aSwitch;

    public static VersionsFragment newInstance() {

        return new VersionsFragment();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preferenceProvider = new PreferenceProvider(getContext());
        versionsRepository = new VersionsRepository(getContext());
        values = versionsRepository.getAllVersions();

        version = preferenceProvider.getVersion();
        color = preferenceProvider.getColorVar();
        textSize = preferenceProvider.gettextSizeVar();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.versions_fragment, parent, false);

        return view;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.VersionsRecyclerView);
        VersionsFragAdapter versionsFragAdapter = new VersionsFragAdapter(version, color, textSize);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerLineDecoration(getActivity(), LinearLayoutManager.VERTICAL, 16));
        recyclerView.setAdapter(versionsFragAdapter);

        versionsFragAdapter.setValues(values);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(),
                recyclerView, new RecyclerTouchListener.ClickListener() {

            // short click
            public void onClick(View view, int position) {

                final VersionsEntities versionsEntities = values.get(position);

                final int number = versionsEntities.getNumber();  // version number

                aSwitch = view.findViewById(R.id.versionSwitch);

                aSwitch.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        int active = (aSwitch.isChecked()) ? 1 : 0;

                        if (number == version) {
                            active = 1;
                        }

                        // set version active
                        versionsRepository.setVersionActive(active, number);// active, version

                    }

                });


            }

            // long click
            public void onLongClick(View view, int position) {
                // do nothing

            }

        }));

    }


}
