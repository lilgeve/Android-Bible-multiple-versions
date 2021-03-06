package com.nakusambabible.digitalbibleapp.Compare;

import android.annotation.SuppressLint;
import android.app.Application;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import com.nakusambabible.digitalbibleapp.BiblesDb.BiblesEntities;
import com.nakusambabible.digitalbibleapp.BiblesDb.BiblesRepository;
import com.nakusambabible.digitalbibleapp.Common.DividerLineDecoration;
import com.nakusambabible.digitalbibleapp.LangKeyDb.LangRepository;
import com.nakusambabible.digitalbibleapp.PreferenceProvider;
import com.nakusambabible.digitalbibleapp.R;
import com.nakusambabible.digitalbibleapp.Versions.DB.VersionsEntities;
import com.nakusambabible.digitalbibleapp.Versions.DB.VersionsRepository;

import java.util.ArrayList;
import java.util.List;

public class CompareSheet extends BottomSheetDialogFragment {

    PreferenceProvider preferenceProvider;

    protected VersionsRepository versionRepository;
    protected LangRepository langRepository;
    protected BiblesRepository biblesRepository;

    private RecyclerView recyclerView;
    private CompareAdapter compareAdapter;

    CompareModel compareModel;

    List<VersionsEntities> allCompareVersions;

    final List<CompareModel> compareModels = new ArrayList<>();

    int[] versionVars;
    String[] compareVars;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preferenceProvider = new PreferenceProvider(getContext());
        versionVars = preferenceProvider.getVersionVars();
        compareVars = preferenceProvider.getCompareVars();

        langRepository = new LangRepository(new Application());

        versionRepository = new VersionsRepository(getContext());

        biblesRepository = new BiblesRepository(getContext());
        biblesRepository.initialize(versionVars[0]);

    }

    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(Dialog dialog, int style) {
        super.setupDialog(dialog, style);

        @SuppressLint("InflateParams")
        View view = LayoutInflater.from(getContext()).inflate(R.layout.compare_fragment, null);
        TextView compare_list_header = view.findViewById(R.id.compare_list_header);
        TextView compare_list_header_text = view.findViewById(R.id.compare_list_header_text);

        recyclerView = view.findViewById(R.id.CompareFragmentRecyclerView);
        compareAdapter = new CompareAdapter(preferenceProvider.gettextSizeVar());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerLineDecoration(getActivity(), LinearLayoutManager.VERTICAL, 16));
        recyclerView.setAdapter(compareAdapter);

        StringBuilder sb = new StringBuilder();

        sb.append(compareVars[0]);
        sb.append(" ");
        sb.append(compareVars[1]);
        sb.append(" ");
        sb.append(versionVars[2]);
        sb.append(":");
        sb.append(versionVars[3]);

        // compare text header
        compare_list_header.setText(sb.toString());

        compare_list_header.setTextSize(preferenceProvider.gettextSizeVar());

        // compare text
        compare_list_header_text.setText(compareVars[2]);
        compare_list_header_text.setTextSize(preferenceProvider.gettextSizeVar());

        dialog.setContentView(view);

        ((View) view.getParent()).setBackgroundColor(Color.TRANSPARENT);

        GetAllCompareVersions getAllCompareVersions
                = new GetAllCompareVersions(versionVars[0]);

        // loop
        for (VersionsEntities versionsEntities : allCompareVersions = getAllCompareVersions.getVersions()) {

            int version = versionsEntities.getNumber();

            biblesRepository.initialize(version);

            final List<BiblesEntities> results = biblesRepository.getCompareValues(versionVars[1],
                    versionVars[2], versionVars[3]); // book, chapter, verse

            String compBookName = langRepository.getBookName(versionVars[1], biblesRepository.lang); // book, lang

            String compAbbrev = versionRepository.getAbbreviation(version);

            for (BiblesEntities result : results) {
                compareModel = new CompareModel();
                compareModel.setCompareabbreviation(compAbbrev);
                compareModel.setComparebook(compBookName);
                compareModel.setComparechapter(Integer.toString(result.getC()));
                compareModel.setCompareverse(Integer.toString(result.getV()));
                compareModel.setComparetext(result.getT());
                compareModels.add(compareModel);
            }

            compareAdapter.setValues(compareModels);

        }


    }

    private class GetAllCompareVersions {

        int num;

        GetAllCompareVersions(int num) {
            this.num = num;
        }

        public List<VersionsEntities> getVersions() {
            return versionRepository.getCompareVersions(num);
        }

    }


}
