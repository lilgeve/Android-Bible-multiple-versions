package com.nakusambabible.digitalbibleapp.BiblesDb;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class BiblesViewModel extends AndroidViewModel {

    protected BiblesRepository biblesRepository;
    protected int version;

    protected LiveData<List<BiblesEntities>> bookChapter;

    public BiblesViewModel(Application application, int version) {
        super(application);
        this.version = version;

        biblesRepository = new BiblesRepository(application);
        biblesRepository.initialize(version);

    }

    public LiveData<List<BiblesEntities>> getBookChapter(int book, int chapter) {
        return bookChapter = biblesRepository.getBookAndChapter(book, chapter);
    }

}
