package com.example.rka.bluesscale;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.data;
import static android.R.attr.tension;

public class MainActivity extends AppCompatActivity {

    String notes[] = new String[] {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"};
    int[] scale = new int[] {0, 2, 4, 5, 7, 9, 11};
    String[] majorScaleGrade = new String[] {"", "m","m","","","m","7"};
    String[] minorScaleGrade = new String[] {"m", "","m","m","","","7"};

    String[] colors = new String[] {"#BEF684", "#5DD6EE","#5DD6EE","#BEF684","#BEF684","#5DD6EE","#BEF684"};

    int tonic = 0;

    public LinearLayout llScale;
    public GridLayout gScale;
    GridView gvMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        llScale = (LinearLayout) findViewById(R.id.llScale);
        gScale = (GridLayout) findViewById(R.id.gScale);
        gvMain = (GridView) findViewById(R.id.gvMain);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, notes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spinner = (Spinner) findViewById(R.id.spinner2);
        spinner.setAdapter(adapter);
        spinner.setPrompt("Title");
        spinner.setSelection(tonic);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(getBaseContext(), "Position = " + position, Toast.LENGTH_SHORT).show();

                addNotesToLayout( getScale(position) );
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
                return view;
            }
        };

        gvMain.setAdapter(adapter);
        gvMain.setNumColumns(7);
        gvMain.setVerticalSpacing(5);
        gvMain.setHorizontalSpacing(5);


    }

    public List<String> getScale(int tonic){
        List<String> newScale = new ArrayList<>();
        String[] scaleGrade;



        for(int i = 0; i < scale.length; i++ ){
            newScale.add(
                    notes[step(tonic, scale[i])] + majorScaleGrade[i]
            );
        }

        return newScale;
    }

    private int step(int tonic, int step ){
         return(tonic + step) > 11? (tonic + step - 12) : (tonic + step);
    }

}
