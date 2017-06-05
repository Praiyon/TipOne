package com.example.praiyon.tipone;

/**
 * Created by praiyon on 25/05/17.
 */

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.widget.SeekBar.*;
import static com.example.praiyon.tipone.R.id.billEditText;
import static com.example.praiyon.tipone.R.id.button;
import static com.example.praiyon.tipone.R.id.peopleEditText;

public class Home extends Fragment implements View.OnClickListener {
    Spinner cSpinner;
    EditText bill, people , editTip;
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    ArrayAdapter<CharSequence> cAdapter;
    String billText,peopleText,tipText;
    SeekBar seekBar;
    boolean flag = false;
    float tipAmount ;
    int spinnerNumber = 10;

    private  static final Fragment  INSTANCE = new Home();
    public static Home newInstance() {
        //Home fragment = new Home();
        return (Home) INSTANCE;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        flag = true;
        View view = inflater.inflate(R.layout.home, container, false);
        String[] c = getResources().getStringArray(R.array.currency);
        //ArrayList<String> list = Arrays.asList(c);
        cSpinner = (Spinner)view.findViewById(R.id.cSpinner);
        cAdapter = (ArrayAdapter.createFromResource(getActivity(),R.array.currency,android.R.layout.simple_spinner_item));

        cAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cSpinner.setAdapter(cAdapter);

        //String defTip = "";

        //String defTip = getArguments().getString("def");
        //Toast.makeText(getActivity(), defTip,
        //        Toast.LENGTH_SHORT).show();

        Button calculate = (Button) view.findViewById(button);
        editTip = (EditText) view.findViewById(R.id.tipS);
        editTip.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(10,1)});
        sharedPref = this.getActivity().getSharedPreferences("pref",Context.MODE_PRIVATE);

        String x = sharedPref.getString("default","10");
        String z = x;



        tipAmount = Float.parseFloat(x);

        //editTip.setText(suggest);
        editTip.setText("" + tipAmount);

        seekBar = (SeekBar) view.findViewById(R.id.seekBar);

        seekBar.setMax(30);
        seekBar.setProgress(spinnerNumber);


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                tipAmount = progress;
               //tipAmount = Math.round(progress);
                editTip.setText("" + tipAmount);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        //if((suggest.equals("fail"))) editTip.setText(suggest);
        bill = (EditText)  view.findViewById(R.id.billEditText);
        bill.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(10,2)});
        people = (EditText) view.findViewById(R.id.peopleEditText);
        Log.d("STATE",z);
        editTip.setText(z);
        calculate.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {

        billText = bill.getText().toString();
        peopleText = people.getText().toString();
        tipText = editTip.getText().toString();
            sharedPref = this.getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);

            editor = sharedPref.edit();
            editor.putString("bill", billText);
            editor.putString("people",peopleText);
            editor.putString("tip",tipText);
            editor.commit();

            //Log.d("STATE", billText);
            Fragment fragment = null;

            switch (view.getId()) {
                case button:
                    boolean b = !(billText.equals(""));
                    boolean p = !(peopleText.equals(""));
                    boolean t = !(tipText.equals(""));
                    if (b && p && t){
                        fragment = Display.newInstance();
                        replaceFragment(fragment);
                        break;
                    }
                    else if(!b && !p && !t){
                        Toast.makeText(getActivity(), "Enter Bill and Number of People and Tip",
                                Toast.LENGTH_SHORT).show();
                    }
                    else if(!b && !p ){
                        Toast.makeText(getActivity(), "Enter Bill and Number of People",
                                Toast.LENGTH_SHORT).show();
                    }
                    else if(!b){
                        Toast.makeText(getActivity(), "Enter Bill",
                                Toast.LENGTH_SHORT).show();
                    }
                    else if(!p){
                        Toast.makeText(getActivity(), "Enter Number of People",
                                Toast.LENGTH_SHORT).show();
                    }
                    else if(!t){
                        Toast.makeText(getActivity(), "Enter Tip",
                                Toast.LENGTH_SHORT).show();
                    }




            }


    }
    public void replaceFragment(Fragment someFragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.content, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(flag && isVisibleToUser){



            String x = sharedPref.getString("default","10");
            editTip.setText(x);
            Toast.makeText(getActivity(), String.valueOf(x),
                    Toast.LENGTH_SHORT).show();




        }


    }
    public void changeText(String str){
        editTip.setText(str);
    }

}



