package com.example.rka.bluesscale;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    String[] scales = new String[] {"Major", "Minor", "Diatonic"};

    String notes[] = new String[] {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};
    int[] grades = new int[] {0, 2, 4, 5, 7, 9, 11};

    String[] majorScaleGrade = new String[] {"", "m","m","","","m","7"};
    String[] minorScaleGrade = new String[] {"m", "","m","m","","","7"};
    String[] seventhChordsOnMajorScale = new String[] {"maj7", "m7","m7","maj7","7","m7","7b5"};

    String[] colors = new String[] {"#BEF684", "#5DD6EE","#5DD6EE","#BEF684","#BEF684","#5DD6EE","#BEF684"};

    int tonic = 0;
    String[] currentGrade = majorScaleGrade;

    LinearLayout llScale;
    GridLayout gScale;
    GridView gvMain;
    Spinner spinner;
    Spinner spScale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.getBackground().setAlpha(100);
        setSupportActionBar(myToolbar);

        llScale = (LinearLayout) findViewById(R.id.llScale);
        gScale = (GridLayout) findViewById(R.id.gScale);
        gvMain = (GridView) findViewById(R.id.gvMain);

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
                addNotesToLayout( getScale(position) );
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

                switch(position){
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

                addNotesToLayout( getScale(tonic) );

                Snackbar.make(findViewById(R.id.base), "Scale: " + scales[position], Snackbar.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }

    public void addNotesToLayout(List<String> scale){
        ArrayAdapter<String> adapter;

        adapter =  new ArrayAdapter<String>(this, R.layout.item, R.id.tvText, scale) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);

                view.setBackgroundColor(Color.parseColor(colors[position]));
                view.getBackground().setAlpha(100);
                return view;
            }
        };

        gvMain.setAdapter(adapter);
        gvMain.setNumColumns(3);
        gvMain.setVerticalSpacing(5);
        gvMain.setHorizontalSpacing(5);
    }

    public List<String> getScale(int tonic){
        List<String> newScale = new ArrayList<>();

        for(int i = 0; i < grades.length; i++ ){
            newScale.add(
                    notes[step(tonic, grades[i])] + currentGrade[i]
            );
        }

        return newScale;
    }

    private int step(int tonic, int step ){
         return(tonic + step) > 11? (tonic + step - 12) : (tonic + step);
    }

}
