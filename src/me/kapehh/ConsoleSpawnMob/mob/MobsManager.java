package me.kapehh.ConsoleSpawnMob.mob;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Karen on 08.05.2015.
 */
public class MobsManager {
    List<MobInfo> mobInfos = new ArrayList<MobInfo>();

    public MobsManager() {
    }

    public void addMob(MobInfo mobInfo) {
        mobInfos.add(mobInfo);
    }

    public MobInfo getFromName(String name) {
        for (MobInfo mobInfo : mobInfos) {
            if (mobInfo.getName().equalsIgnoreCase(name)) {
                return mobInfo;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "MobsManager{" +
                "mobInfos=" + mobInfos +
                '}';
    }
}
