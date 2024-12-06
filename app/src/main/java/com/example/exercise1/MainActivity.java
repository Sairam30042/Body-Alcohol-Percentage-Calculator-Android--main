package com.example.exercise1;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public Drinks drink;
    public ArrayList<Drinks> Ds= new ArrayList<>();
    public Profile profile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText weight_edit;
        TextView view_weight_text,default_value, no_drinks,BAC_Level, your_limit;
        RadioGroup gender_group,alcohol_size;
        SeekBar alcohol_percentage;

        weight_edit=findViewById(R.id.weight_edit);
        view_weight_text=findViewById(R.id.view_weight_text);
        default_value=findViewById(R.id.default_level);
        no_drinks=findViewById(R.id.no_drinks);
        BAC_Level=findViewById(R.id.BAC_level);
        your_limit=findViewById(R.id.your_limit);
        gender_group=findViewById(R.id.radioGroup);
        alcohol_size=findViewById(R.id.radioGroup2);
        alcohol_percentage=findViewById(R.id.seekBar_alcohol_level);
        View your_view=findViewById(R.id.your_view);



        findViewById(R.id.set_weight).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String gender;
                    double weight=Double.valueOf(weight_edit.getText().toString());
                    if(gender_group.getCheckedRadioButtonId()==R.id.radioButton_male){
                        gender="Male";
                    }
                    else{
                        gender="Female";
                    }
                    profile=new Profile(weight,gender);
                    view_weight_text.setText(String.valueOf(profile.weight +"("+profile.gender+")"));
                    weight_edit.setText("");
                }
                catch(Exception e){
                    Toast.makeText(MainActivity.this, "Enter valid weight", Toast.LENGTH_SHORT).show();
                }


            }
        });
        alcohol_percentage.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                default_value.setText(String.valueOf(progress)+"%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
if(profile !=null){
                try {
                    int drink_id=alcohol_size.getCheckedRadioButtonId();
                    double drink_size;
                    if(drink_id==R.id.alcohol1oz){
                        drink_size=1;
                    }
                    else if(drink_id==R.id.alcohol5oz){
                        drink_size=5;
                    }
                    else{
                        drink_size=12;
                    }
                    double drink_percent=Double.valueOf(alcohol_percentage.getProgress());
                    drink=new Drinks(drink_percent,drink_size);
                    Ds.add(drink);
                    no_drinks.setText("# Drinks : " +String.valueOf(Ds.size()));
                    double bac_level=0.0;
                    for(Drinks d:Ds){
                    bac_level+=(d.size*d.percentage/100);
                    }
                    if(profile.gender=="Male"){
                        bac_level=bac_level*5.14/(0.73*profile.weight);
                    }
                    else{
                        bac_level=bac_level*5.14/(0.66*profile.weight);

                    }
                    BAC_Level.setText("BAC Level : "+String.format("%.4f",bac_level));
                    if((0.0 < bac_level) && (bac_level <= 0.08 )){
                        your_view.setBackgroundColor(Color.GREEN);
                        your_limit.setText("You're Safe");
                    } else if ((0.08 < bac_level) && (bac_level <= 0.2 )) {
                        your_view.setBackgroundColor(Color.YELLOW);
                        your_limit.setText("Be careful");
                    }
                    else{your_view.setBackgroundColor(Color.RED);
                        your_limit.setText("Over the limit!");}
                }
                catch(Exception e){
                    Toast.makeText(MainActivity.this, "Check Values", Toast.LENGTH_SHORT).show();
                }
            }else{
    Toast.makeText(MainActivity.this, "Enter weight first", Toast.LENGTH_SHORT).show();}
            }
        });
        findViewById(R.id.reset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profile=null;
                drink=null;
                view_weight_text.setText("");
                BAC_Level.setText("BAC Level : 0.0000");
                no_drinks.setText("#Drink : 00");
                your_view.setBackgroundColor(Color.GREEN);
                your_limit.setText("You're Safe");
                alcohol_percentage.setProgress(12);
                gender_group.set
                Ds.clear();
            }
        });
    }
}