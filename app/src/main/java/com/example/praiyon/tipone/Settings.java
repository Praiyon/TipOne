package com.example.praiyon.tipone;

/**
 * Created by praiyon on 25/05/17.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.InputFilter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static com.example.praiyon.tipone.R.id.apply;


public class Settings extends Fragment implements View.OnClickListener {

    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    EditText tip;
    Button set;

    boolean flag = false;
    private  static final Fragment  INSTANCE = new Settings();
    public static Settings newInstance() {

        return (Settings) INSTANCE;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.settings, container, false);
        tip = (EditText) view.findViewById(R.id.tipDef);
        tip.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(10,1)});


        set = (Button) view.findViewById(apply);
        sharedPref = this.getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        tip.setText(sharedPref.getString("default",""));
        set.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        sharedPref = this.getActivity().getSharedPreferences("pref", Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        String tipD = tip.getText().toString();
        tip.setText(tipD);




        editor.putString("default",tipD);
        editor.apply();

        Fragment fragment;
        /*
        switch (view.getId()) {
            case apply:
                boolean b = tipD.equals("");
                if(!b){
                    fragment = Home.newInstance();
                    replaceFragment(fragment);
                    break;
                }
                else{
                    Toast.makeText(getActivity(), "Enter Default Tip",
                            Toast.LENGTH_SHORT).show();
                }


        }
    */


    }
    public void replaceFragment(Fragment someFragment) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.content, someFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}