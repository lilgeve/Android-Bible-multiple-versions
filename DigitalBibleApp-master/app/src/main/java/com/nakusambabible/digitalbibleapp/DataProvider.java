package com.nakusambabible.digitalbibleapp;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.nakusambabible.digitalbibleapp.BiblesDb.BiblesRepository;
import com.nakusambabible.digitalbibleapp.Versions.DB.VersionsRepository;

public class DataProvider extends ContentProvider {

    static final String PROVIDER_NAME = "com.nakusambabible.digitalbibleapp.DataProvider";
    static final String URL = "content://" + PROVIDER_NAME;
    static final Uri CONTENT_URI = Uri.parse(URL);

    protected VersionsRepository versionRepository;

    protected BiblesRepository biblesRepository;

    @Override
    public boolean onCreate() {

        versionRepository = new VersionsRepository(getContext());

        biblesRepository = new BiblesRepository(getContext());

        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri,
                        @Nullable String[] projection,
                        @Nullable String selection,
                        @Nullable String[] selectionArgs,
                        @Nullable String sortOrder) {

        int book = Integer.parseInt(selectionArgs[0]);
        int chapter = Integer.parseInt(selectionArgs[1]);

        int number = 1; // KJV

        switch (selection) { // languageCode

            case "en":
                number = 7; //UKJV
                break;
            case "af":
                number = 5; //AF53
                break;
            case "da":
                number = 6; //DN1933
                break;

        }

        // set version active
        versionRepository.setVersionActive(1, number);// active, version

        biblesRepository.initialize(number);

        // Cursor cursor;
        MatrixCursor matrixCursor = new MatrixCursor(new String[]{"txt"});

        // verse can be a range of verses
        if (selectionArgs[2].contains("|")) {

            String stringToSplit = selectionArgs[2];

            String delimiter = "\\|";

            String[] verses = stringToSplit.split(delimiter);

            for (int i = 0; i < verses.length; i++) {

                Cursor c = biblesRepository.getDataProviderValues(book, chapter, Integer.parseInt(verses[i]));

                if (c.moveToFirst()) {
                    matrixCursor.newRow().add(c.getString(c.getColumnIndex("t")));
                }

            }


        } else {

            Cursor d = biblesRepository.getDataProviderValues(book, chapter, Integer.parseInt(selectionArgs[2]));

            if (d.moveToFirst()) {
                matrixCursor.newRow().add(d.getString(d.getColumnIndex("t")));
            }

        }

        return matrixCursor;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
