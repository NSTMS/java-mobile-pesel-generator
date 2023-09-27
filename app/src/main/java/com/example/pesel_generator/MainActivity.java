package com.example.pesel_generator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private EditText dayInput;
    private EditText monthInput;
    private EditText yearInput;
    private Spinner spinner;
    private CheckBox checkBox;
    private Button button;
    private TextView generatedPesel;
    private TextView test;
    private TextView test1;
    private TextView test2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dayInput = findViewById(R.id.dayInput);
        monthInput = findViewById(R.id.monthInput);
        yearInput = findViewById(R.id.yearInput);
        spinner = findViewById(R.id.spinner);
        spinner.setSelection(0);
        checkBox = findViewById(R.id.checkBox);
        button = findViewById(R.id.button);
        generatedPesel = findViewById(R.id.generatedPesel);
        button.setOnClickListener(v -> click());
        generatedPesel.setOnLongClickListener(v -> copyText());

    }
    private void click(){
        int day;
        int month;
        int year;
        String plec = spinner.getSelectedItem().toString();
        if(checkBox.isChecked())
        {
            day = 11;
            month = 9;
            year = 2001;
            generatePesel(day,month,year,plec);
        }
        else{
            if(dayInput.getText().toString().isEmpty() || monthInput.getText().toString().isEmpty() || yearInput.getText().toString().isEmpty()){
                Toast.makeText(this , "uzupełnij wszystkie pola!", Toast.LENGTH_SHORT).show();
                return;
            }
            day = Integer.parseInt(dayInput.getText().toString());
            month = Integer.parseInt(monthInput.getText().toString());
            year = Integer.parseInt(yearInput.getText().toString());
            if(day < 0 || day > 31 || month < 0 || month > 12 || year < 1800 || year >= 2300){
                Toast.makeText(this , "uzupełnij poprawnie!", Toast.LENGTH_SHORT).show();
                return;
            }
            generatePesel(day,month,year,plec);
        }


    }

    private void generatePesel(int day, int month, int year, String plec){
        String result = "";
        char[] yearString = Integer.toString(year).toCharArray();

        result += String.valueOf(yearString[2]) + String.valueOf(yearString[3]);

        String monthString = String.valueOf(yearString[0]) + String.valueOf(yearString[1]);

        switch (Integer.parseInt(monthString)){
            case 18:
                result += String.valueOf(month + 80);
                break;
            case 19:
                if(month < 10){
                    result += "0"+ String.valueOf(month);
                }
                else{
                    result += String.valueOf(month);
                }
                break;
            case 20:
                result += String.valueOf(month + 20);
                break;
            case 21:
                result += String.valueOf(month + 40);
                break;
            case 22:
                result += String.valueOf(month + 60);
                break;
            default:
                break;
        }
        if(day < 10){
            result += "0";
        }
        result += String.valueOf(day);
        Random random = new Random();
        String t = "";
        for (int i = 0;i < 4; i++) {
            if(i == 3 && plec.equals("mężczyzna"))
            {
                int s = 0;
                do{
                    s = random.nextInt(10);
                }
                while(s %2 == 0);
                t += s;

            }
            else if(i ==3 && plec.equals("kobieta"))
            {
                int s = 0;
                do{
                    s = random.nextInt(10);
                }
                while(s %2 != 0);
                t += s;
            }
            else{
                t += random.nextInt(10);
            }

        }
        result += t;

        int[] numbers = {1,3,7,9,1,3,7,9,1,3};
        int sum = 0;
        for (int j=0;j<numbers.length;j++)
        {
            int r = numbers[j]*Integer.parseInt(String.valueOf(result.toCharArray()[j]));
            if(r >=10){
                int q =  Integer.parseInt(String.valueOf(Integer.toString(r).toCharArray()[1]));
                sum += q;
            }
            else{
                sum += r;
            }
        }
        if(sum >= 10)
        {
            sum = 10 - Integer.parseInt(String.valueOf(Integer.toString(sum).toCharArray()[1]));
        }
        else{
            sum = 10 - sum;
        }
        if(sum == 10){
            sum = 0;
        }
        result += sum;

        generatedPesel.setText(result);
    }

    private boolean copyText(){
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("pesel", generatedPesel.getText().toString());
        clipboard.setPrimaryClip(clip);
        return false;
    }

};