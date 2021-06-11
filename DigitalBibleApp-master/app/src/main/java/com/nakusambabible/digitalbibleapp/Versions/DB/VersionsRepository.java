package com.nakusambabible.digitalbibleapp.Versions.DB;

import android.content.Context;
import androidx.lifecycle.LiveData;
import java.util.List;

public class VersionsRepository {

    protected VersionsDoa versionsDoa;

    private LiveData<List<VersionsEntities>> allActiveVersions;
    private List<VersionsEntities> allCompareVersions, allVersions;

    public VersionsRepository(Context context) {
        versionsDoa = VersionsDatabase.getInstance(context).versionsDoa();
    }

    public List<VersionsEntities> listActiveVersions() {
        return versionsDoa.listActiveVersions();
    }

    public int countActiveVersions() {
        return versionsDoa.countActiveVersions();
    }

    public void setVersionActive(final int act, final int num) {
        versionsDoa.setVersionActive(act, num);
    }

    public void unlockVersions() {
        versionsDoa.unlockVersions();
    }

    public String getAbbreviation(int num) {
        return versionsDoa.getAbbrFromNumber(num);
    }

    public String getLangFromNumber(int num) {
        return versionsDoa.getLangFromNumber(num);
    }

    public LiveData<List<VersionsEntities>> getActiveVersions(int number) {
        allActiveVersions = versionsDoa.getActiveVersions(number);
        return allActiveVersions;
    }

    public List<VersionsEntities> getCompareVersions(int number) {
        allCompareVersions = versionsDoa.getCompareVersions(number);
        return allCompareVersions;
    }

    public List<VersionsEntities> getAllVersions() {
        allVersions = versionsDoa.getAllVersions();
        return allVersions;
    }


}
