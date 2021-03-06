package com.nakusambabible.digitalbibleapp.BiblesDb;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import static com.nakusambabible.digitalbibleapp.Constants.NOVA_DATABASE_NAME;

@Database(version = 1, entities = {BiblesEntities.class})
public abstract class DatabaseNOVA extends RoomDatabase {

    public abstract BiblesDoa biblesDoa();

    private static DatabaseNOVA INSTANCE;

    public static DatabaseNOVA getInstance(Context context) {

        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    DatabaseNOVA.class, NOVA_DATABASE_NAME)
                    .allowMainThreadQueries()
                    .createFromAsset("dba_nvul.db")
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return INSTANCE;
    }


}
