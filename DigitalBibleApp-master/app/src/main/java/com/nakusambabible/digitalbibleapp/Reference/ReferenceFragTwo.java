package com.nakusambabible.digitalbibleapp.Reference;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

import com.nakusambabible.digitalbibleapp.BiblesDb.BiblesRepository;
import com.nakusambabible.digitalbibleapp.Common.RecyclerTouchListener;

import com.nakusambabible.digitalbibleapp.PreferenceProvider;
import com.nakusambabible.digitalbibleapp.R;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ReferenceFragTwo extends Fragment {

    private static ReferenceFragTwo instance;

    PreferenceProvider preferenceProvider;

    private RecyclerView recyclerView;
    private ReferenceFragTwoAdapter referenceFragTwoAdapter;

    protected BiblesRepository biblesRepository;

    private int[] values;

    public int book;
    public int chapter;
    private int textSize;

    public static ReferenceFragTwo newInstance() {
        ReferenceFragTwo referenceFragOne = new ReferenceFragTwo();
        return referenceFragOne;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        instance = this;

        preferenceProvider = new PreferenceProvider(getContext());

        int [] versionVars = preferenceProvider.getVersionVars();

        biblesRepository = new BiblesRepository(getContext());
        biblesRepository.initialize(versionVars[0]);

        chapter = preferenceProvider.getChapter();
        textSize = preferenceProvider.gettextSizeVar();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.reference_fragtwo, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        int numberOfColumns = 6;

        recyclerView = view.findViewById(R.id.ReferenceFragRecyclerView);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumns));

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        updateChapterGrid(); // initial values

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(),
                recyclerView, new RecyclerTouchListener.ClickListener() {

            // short click
            public void onClick(View view, int position) {

                chapter = values[position];

                String title = ReferenceFragOne.getInstance().book_name + " " + chapter + ":1";

                ReferenceActivity.getInstance().textOne.setText(title);

                preferenceProvider.setChapter(chapter);

                // update verses
                ReferenceFragThree.getInstance().updateVerseGrid();

                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        // Move to the verse tab
                        TabLayout tabhost = getActivity().findViewById(R.id.ref_tabs);
                        tabhost.getTabAt(2).select();

                    }
                }, 200);

            }

            // long click
            public void onLongClick(View view, int position) {
                // do nothing
            }

        }));
    }

    public void updateChapterGrid() {

        book = preferenceProvider.getBook();

        int cnt = biblesRepository.getChapterCount(book);

        values = new int[cnt];
        for (int i = 0; i < cnt; ++i) {
            values[i] = i + 1;
        }

        referenceFragTwoAdapter = new ReferenceFragTwoAdapter(values, textSize);
        recyclerView.setAdapter(referenceFragTwoAdapter);

    }

    public static ReferenceFragTwo getInstance() {
        return instance;
    }


}
