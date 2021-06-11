package com.nakusambabible.digitalbibleapp.Searches.History;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.nakusambabible.digitalbibleapp.Common.DividerLineDecoration;

import com.nakusambabible.digitalbibleapp.PreferenceProvider;
import com.nakusambabible.digitalbibleapp.R;
import com.nakusambabible.digitalbibleapp.Common.RecyclerTouchListener;
import com.nakusambabible.digitalbibleapp.Searches.DB.SearchesEntities;
import com.nakusambabible.digitalbibleapp.Searches.SearchesActivity;
import com.nakusambabible.digitalbibleapp.Searches.DB.SearchesRepository;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SearchesHistoryFragment extends Fragment {

    protected SearchesRepository searchesRepository;

    private RecyclerView recyclerView;
    private SearchesHistoryAdapter searchesHistoryAdapter;

    private View view;

    private List<SearchesEntities> history;

    private PreferenceProvider preferenceProvider;
    private EditText searchText;

    private int version;
    private int textSize;

    public static SearchesHistoryFragment newInstance() {

        SearchesHistoryFragment searchesFragment = new SearchesHistoryFragment();

        return searchesFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        searchesRepository = new SearchesRepository(getActivity());

        preferenceProvider = new PreferenceProvider(getActivity());

        textSize = preferenceProvider.gettextSizeVar();
        searchText = SearchesActivity.getInstance().searchText;
        version = SearchesActivity.getInstance().version;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.searches_history_fragment, container, false);

        recyclerView = view.findViewById(R.id.SearchesHistoryFragRecyclerView);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        history = searchesRepository.getSearchHistory(version);

        searchesHistoryAdapter = new SearchesHistoryAdapter(history, textSize);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerLineDecoration(getActivity(), LinearLayoutManager.VERTICAL, 16));
        recyclerView.setAdapter(searchesHistoryAdapter);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(),
                recyclerView, new RecyclerTouchListener.ClickListener() {

            public void onClick(View view, int position) {

                // set iconfied = false to receive the history item
                SearchesActivity.getInstance().searchView.setIconified(false);

                SearchesEntities searchesEntities = history.get(position);

                searchText.setText(searchesEntities.getText());

            }

            public void onLongClick(View view, int position) {
                // do nothing
            }
        }));

    }



}
