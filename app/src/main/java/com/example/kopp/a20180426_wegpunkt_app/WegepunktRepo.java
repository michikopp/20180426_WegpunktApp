package com.example.kopp.a20180426_wegpunkt_app;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class WegepunktRepo implements Serializable{
    private List<WegePunkt> wegePunkte;

    public WegepunktRepo() {
        this.wegePunkte = new ArrayList<>();
    }

    public void add(WegePunkt wegepunkt) {
        if (wegepunkt != null) {
            wegePunkte.add(wegepunkt);
        }
    }

    public WegePunkt get(int index) {
        if (index < 0 || index >= wegePunkte.size()) {
            return null;
        }
        return wegePunkte.get(index);
    }

    public int size() {
        return wegePunkte.size();
    }

    public List<WegePunkt> getWegePunkte() {
        return wegePunkte;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (WegePunkt wegpunkt:wegePunkte) {
            stringBuilder.append(wegpunkt.toString());
            stringBuilder.append(String.format("%n"));
        }
        return stringBuilder.toString();
    }
}
