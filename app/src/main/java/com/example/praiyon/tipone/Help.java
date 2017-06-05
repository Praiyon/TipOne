package com.example.praiyon.tipone;

/**
 * Created by praiyon on 25/05/17.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

public class Help extends Fragment {
    RatingBar ratingBar;
    float suggest;
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    String suggestFinal;
    private  static final Fragment  INSTANCE = new Help();
    public static Help newInstance() {

        return (Help) INSTANCE;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.help, container, false);
        final TextView tip = (TextView) view.findViewById(R.id.tips);

        ratingBar = (RatingBar) view.findViewById(R.id.ratingbar);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                 suggest = ratingBar.getRating()*2 + 10;
                 suggestFinal= String.valueOf(Math.round(suggest));
                tip.setText(String.valueOf(suggestFinal+"%"));


            }

        });
        Button apply = (Button) view.findViewById(R.id.apply);
        apply.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                sharedPref = getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
                editor = sharedPref.edit();
                editor.putString("suggest",suggestFinal);
                editor.apply();
            }
        });

        return view;
    }
}