package com.example.rka.bluesscale;

import java.util.List;

/**
 * Created by RKA on 6/7/2017.
 */

public class ScaleAndChords {
    List<String> scale;
    List<String> chords;
    String[] grade;

    public ScaleAndChords(List<String> scale, List<String> chords, String[] grade) {
        this.chords = chords;
        this.scale = scale;
        this.grade = grade;
    }

    public List<String> getScale(){
        return this.scale;
    }

    public List<String> getChords() {
        return chords;
    }

    public String[] getGrade() {
        return grade;
    }
}
