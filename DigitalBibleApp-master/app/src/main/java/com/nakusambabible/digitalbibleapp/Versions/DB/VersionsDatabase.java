package com.nakusambabible.digitalbibleapp.Versions.DB;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import static com.nakusambabible.digitalbibleapp.Constants.VERSION_KEY_DATABASE_NAME;

@Database(version = 2, entities = {VersionsEntities.class})
public abstract class VersionsDatabase extends RoomDatabase {

    public abstract VersionsDoa versionsDoa();

    private static VersionsDatabase INSTANCE;

    public static VersionsDatabase getInstance(final Context context) {

        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    VersionsDatabase.class, VERSION_KEY_DATABASE_NAME)
                    .allowMainThreadQueries()
                    .addCallback(RoomDatabaseCallback)
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return INSTANCE;
    }

    private static Callback RoomDatabaseCallback = new Callback() {

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            // Called when the database is created for the first time.
            // This is called after all the tables are created.
            db.execSQL("BEGIN TRANSACTION;");

            db.execSQL("INSERT INTO version_key (number,active,copyRight,verAbbr,transLang,verName) VALUES(1,1,1,'KJV','eng','King James')");
            db.execSQL("INSERT INTO version_key (number,active,copyRight,verAbbr,transLang,verName) VALUES(2,1,1,'CLVUL','lat','Vulgata Clementina')");
            db.execSQL("INSERT INTO version_key (number,active,copyRight,verAbbr,transLang,verName) VALUES(3,1,1,'CPDV','eng','Catholic PDV')");
            db.execSQL("INSERT INTO version_key (number,active,copyRight,verAbbr,transLang,verName) VALUES(4,1,1,'NVUL','lat','Nova Vulgata')");
            db.execSQL("INSERT INTO version_key (number,active,copyRight,verAbbr,transLang,verName) VALUES(5,0,0,'AFR53','afr','Afrikaans 1933/53')");
            db.execSQL("INSERT INTO version_key (number,active,copyRight,verAbbr,transLang,verName) VALUES(6,0,0,'DN1933','dan','Bibelen på Dansk 1933')");
            db.execSQL("INSERT INTO version_key (number,active,copyRight,verAbbr,transLang,verName) VALUES(7,1,1,'UKJV','eng','Updated King James')");
            db.execSQL("INSERT INTO version_key (number,active,copyRight,verAbbr,transLang,verName) VALUES(8,1,1,'WEBBE','eng','World English Bible')");
            db.execSQL("INSERT INTO version_key (number,active,copyRight,verAbbr,transLang,verName) VALUES(9,0,0,'AFR83','afr','Afrikaans 1983')");
            db.execSQL("INSERT INTO version_key (number,active,copyRight,verAbbr,transLang,verName) VALUES(10,1,1,'ASV','eng','American Standard Version')");

            db.execSQL("COMMIT;");
        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            //Called when the database has been opened.
            //Log.e("logg", "onOpen: " + KJV_DATABASE_NAME);
        }
    };


}
