package com.example.rka.bluesscale;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by RKA on 6/7/2017.
 */

public class ChordAdapter extends BaseAdapter {
    private Context mContext;
    private String[] mChords;
    private String[] mScale;
    private String[] mGrade;
    private String[] mColors;

    private HashMap<String, int[]> chordTypes = new HashMap<>();
    String[] blueGrey = new String[]{"#263238", "#37474F", "#455A64", "#546E7A", "#607D8B", "#78909C", "#90A4AE"};

    public ChordAdapter(Context context, ScaleAndChords scaleAndChords, String[] colors){
        this.mContext = context;
        this.mChords = scaleAndChords.getChords().toArray(new String[0]);
        this.mGrade = scaleAndChords.getGrade();
        this.mScale = scaleAndChords.getScale().toArray(new String[0]);
        this.mColors = colors;

        chordTypes.put("", new int[] {0, 3, 4});
        chordTypes.put("m", new int[] {0, 3, 4});
        chordTypes.put("7", new int[] {0, 3, 4, 6});
        chordTypes.put("m7", new int[] {0, 3, 4, 6});
        chordTypes.put("maj7", new int[] {0, 3, 4, 6});
        chordTypes.put("7b5", new int[] {0, 3, 4, 6});
    }

    @Override
    public int getCount() {
        return mChords.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater mInflater;
        mInflater = LayoutInflater.from(mContext);
        View view = mInflater.inflate(R.layout.llitem, null);
        TextView tvName = (TextView) view.findViewById(R.id.tvText);


        tvName.setText(mChords[position]);
        tvName.setId(position);
        tvName.setBackgroundColor(Color.parseColor(mColors[position]));
        tvName.getBackground().setAlpha(100);
        tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View rootView = ((Activity)mContext).getWindow().getDecorView().findViewById(android.R.id.content);
                TextView tvSelectedChord = (TextView) rootView.findViewById(R.id.tvSelectedChord);
                GridView gvChord = (GridView) rootView.findViewById(R.id.gvChord);

                tvSelectedChord.setText("Selected Chord: " + ((TextView) v).getText());

                gvChord.setAdapter(new NoteAdapter(mContext, getChord(position, mGrade[position]), blueGrey));
                gvChord.setNumColumns(4);
                gvChord.setVerticalSpacing(5);
                gvChord.setHorizontalSpacing(5);

//                Toast.makeText(mContext, ((TextView) v).getText(), Toast.LENGTH_SHORT).show();
            }
        });
        return tvName;
    }

    private int step(int position, int step) {
        return (position + step) > 6 ? (position + step - 7) : (position + step);
    }

    List<String> getChord(int current, String type){
        int[] chord = chordTypes.get(type);
        List<String> notes = new ArrayList<>();

        for (int i = 0; i < chord.length; i++) {
           notes.add(
                   mScale[step(current, chord[i])]
           );
        }
        return notes;
    }

}
