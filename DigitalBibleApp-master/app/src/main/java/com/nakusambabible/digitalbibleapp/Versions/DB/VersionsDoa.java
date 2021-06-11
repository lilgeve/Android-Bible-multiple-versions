package com.nakusambabible.digitalbibleapp.Versions.DB;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface VersionsDoa {

    @Query("SELECT * FROM version_key WHERE active=1")
    List<VersionsEntities> listActiveVersions();

    @Query("SELECT count(*) FROM version_key WHERE active=1 AND copyRight=1")
    int countActiveVersions();

    @Query("UPDATE version_key SET active=:act WHERE number=:num")
    void setVersionActive(int act, int num);

    @Query("SELECT transLang FROM version_key WHERE number=:number AND copyRight=1")
    String getLangFromNumber(int number);

    @Query("SELECT * FROM version_key WHERE copyRight=1 ORDER BY number ASC")
    List<VersionsEntities> getAllVersions();

    @Query("SELECT * FROM version_key WHERE active=1 AND number!=:number AND copyRight=1 ORDER BY number ASC")
    LiveData<List<VersionsEntities>> getActiveVersions(int number);

    @Query("SELECT * FROM version_key WHERE active=1 AND number!=:number AND copyRight=1 ORDER BY number ASC")
    List<VersionsEntities> getCompareVersions(int number);

    @Query("UPDATE version_key SET copyRight=1")
    void unlockVersions();

    @Query("SELECT verAbbr FROM version_key WHERE number=:number")
    String getAbbrFromNumber(int number);


}