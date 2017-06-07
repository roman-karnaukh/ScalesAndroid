package com.example.rka.bluesscale;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by RKA on 6/7/2017.
 */

public class NoteAdapter extends BaseAdapter {
    private Context mContext;
    private String[] scale;
    private String[] colors;
    String[] scaleSteps = new String[]{"I", "II", "III", "IV", "V", "VI", "VII"};

    public NoteAdapter(Context context, List<String> scale, String[] colors){
        this.mContext = context;
        this.scale = scale.toArray(new String[0]);
        this.colors = colors;
    }

    @Override
    public int getCount() {
        return scale.length;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater mInflater;
        mInflater = LayoutInflater.from(mContext);
        View view = mInflater.inflate(R.layout.llitem, null);
        TextView tvName = (TextView) view.findViewById(R.id.tvText);
        tvName.setBackgroundColor(Color.parseColor(colors[position]));
        tvName.getBackground().setAlpha(100);

        tvName.setText(scale[position]);
        tvName.setId(position);
        tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, ((TextView) v).getText(), Toast.LENGTH_SHORT).show();
            }
        });

        LinearLayout ll = new LinearLayout(mContext);
        ll.setOrientation(LinearLayout.VERTICAL);
        TextView label = new TextView(mContext);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT
        );

        label.setText("("+scaleSteps[position]+")");
        label.setGravity(Gravity.CENTER);

        ll.addView(label, layoutParams);
        ll.addView(tvName);

        return ll;
    }

}
