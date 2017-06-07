package com.example.rka.bluesscale;

import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    String d = "[\n" +
            "    {id: 1, name: \"John Smith\", phone:+380952122015},\n" +
            "    {id: 2, name: \"John Smith\", phone:+380952122015},\n" +
            "    {id: 3, name: \"Adam Smith\", phone:+380951652015},\n" +
            "    {id: 4, name: \"Bred Smith\", phone:+380952869015}\n" +
            "\n" +
            "]";

    String[] scales = new String[]{"Major", "Minor", "Diatonic"};

    String notes[] = new String[]{"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};
    int[] grades = new int[]{0, 2, 4, 5, 7, 9, 11};

    String[] majorScaleGrade = new String[]{"", "m", "m", "", "", "m", "7"};
    String[] minorScaleGrade = new String[]{"m", "", "m", "m", "", "", "7"};
    String[] seventhChordsOnMajorScale = new String[]{"maj7", "m7", "m7", "maj7", "7", "m7", "7b5"};

    String[] colors = new String[]{"#BEF684", "#5DD6EE", "#5DD6EE", "#BEF684", "#BEF684", "#5DD6EE", "#BEF684"};
    String[] tomatoColors = new String[]{"#CA241C", "#E33E35", "#E7534B", "#EA6962", "#EE8B86", "#F3A9A5", "#F8D0CE"};
    String[] pinkColors = new String[]{"#AD1457", "#C2185B", "#D81B60", "#E91E63", "#EC407A", "#F06292", "#F48FB1"};

    int tonic = 0;
    String[] currentGrade = majorScaleGrade;

    GridView gvMain;
    GridView gvScale;
    Spinner spinner;
    Spinner spScale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.getBackground().setAlpha(100);
        setSupportActionBar(myToolbar);

        gvMain = (GridView) findViewById(R.id.gvMain);
        gvScale = (GridView) findViewById(R.id.gvScale);

        spinner = (Spinner) findViewById(R.id.spinner2);
        spScale = (Spinner) findViewById(R.id.spScale);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, notes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        spinner.setSelection(tonic);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tonic = position;
                addChordsToLayout(getScale(position));
                Snackbar.make(findViewById(R.id.base), "Tonic: " + notes[position], Snackbar.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        ArrayAdapter<String> scaleAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, scales);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spScale.setAdapter(scaleAdapter);
        spScale.setSelection(tonic);

        spScale.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (position) {
                    case 0:
                        currentGrade = majorScaleGrade;
                        break;
                    case 1:
                        currentGrade = minorScaleGrade;
                        break;
                    case 2:
                        currentGrade = seventhChordsOnMajorScale;
                        break;
                    default:
                        currentGrade = seventhChordsOnMajorScale;
                }

                addChordsToLayout(getScale(tonic));

                Snackbar.make(findViewById(R.id.base), "Scale: " + scales[position], Snackbar.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }

    public void addChordsToLayout(ScaleAndChords scaleAndChords) {

        gvMain.setAdapter(new ChordAdapter(this, scaleAndChords, colors));
        gvMain.setNumColumns(3);
        gvMain.setVerticalSpacing(5);
        gvMain.setHorizontalSpacing(5);
        addScaleNotes(scaleAndChords);
    }

    public void addScaleNotes(ScaleAndChords scaleAndChords) {

        gvScale.setAdapter(new NoteAdapter(this, scaleAndChords.getScale(), pinkColors));
        gvScale.setNumColumns(7);
        gvScale.setVerticalSpacing(5);
        gvScale.setHorizontalSpacing(5);
    }

    public ScaleAndChords getScale(int tonic) {
        List<String> scale = new ArrayList<>();
        List<String> scaleChords = new ArrayList<>();

        for (int i = 0; i < grades.length; i++) {
            scale.add(
                    notes[step(tonic, grades[i])]
            );
            scaleChords.add(
                    notes[step(tonic, grades[i])] + currentGrade[i]
            );
        }

        return new ScaleAndChords(scale, scaleChords, currentGrade);
    }

    private int step(int tonic, int step) {
        return (tonic + step) > 11 ? (tonic + step - 12) : (tonic + step);
    }

}
