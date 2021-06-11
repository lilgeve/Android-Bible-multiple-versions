package com.nakusambabible.digitalbibleapp.BiblesDb;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import static com.nakusambabible.digitalbibleapp.Constants.ASV_DATABASE_NAME;

@Database(version = 1, entities = {BiblesEntities.class})
public abstract class DatabaseASV extends RoomDatabase {

    public abstract BiblesDoa biblesDoa();

    private static DatabaseASV INSTANCE;

    public static DatabaseASV getInstance(Context context) {

        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    DatabaseASV.class, ASV_DATABASE_NAME)
                    .allowMainThreadQueries()
                    .createFromAsset("dba_asv.db")
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return INSTANCE;
    }


}
