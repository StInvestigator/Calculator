package com.example.calculator;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private String currentInput = "";
    private String operator = "";
    private double firstNumber = Double.NaN;
    private boolean isNewInput = true;
    private double memory = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        if(item.getItemId()==R.id.about_us){
            Intent intent = new Intent(getBaseContext(), AboutUsActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
    public void onNumberClick(View view) {
        if (isNewInput) {
            currentInput = "";
            isNewInput = false;
        }
        currentInput += ((Button) view).getText().toString();
        textView.setText(currentInput);
    }

    public void onOperatorClick(View view) {
        if(Objects.equals(currentInput, "")) return;
        if (!Double.isNaN(firstNumber) && !currentInput.isEmpty()) {
            calculate();
        } else {
            firstNumber = Double.parseDouble(currentInput);
        }
        operator = ((Button) view).getText().toString();
        isNewInput = true;
    }

    public void onEqualsClick(View view) {
        calculate();
        operator = "";
        isNewInput = true;
    }

    private void calculate() {
        if(Objects.equals(currentInput, "")) return;
        double secondNumber = Double.parseDouble(currentInput);
        switch (operator) {
            case "+": firstNumber += secondNumber; break;
            case "-": firstNumber -= secondNumber; break;
            case "*": firstNumber *= secondNumber; break;
            case "/": firstNumber /= secondNumber; break;
            case "%": firstNumber %= secondNumber; break;
            case "": return;
        }
        textView.setText(String.valueOf(firstNumber));
        currentInput = String.valueOf(firstNumber);
    }

    public void onClear(View view) {
        currentInput = "";
        firstNumber = Double.NaN;
        operator = "";
        textView.setText("0");
        isNewInput = true;
    }

    public void onClearE(View view) {
        currentInput = "";
        textView.setText("0");
        isNewInput = true;
    }

    public void onMemoryClick(View view) {
        switch (((Button) view).getText().toString()) {
            case "MC": memory = 0; break;
            case "MR": textView.setText(String.valueOf(memory)); currentInput = String.valueOf(memory); break;
            case "MS": memory = Double.parseDouble(currentInput); break;
            case "M+": memory += Double.parseDouble(currentInput); break;
            case "M-": memory -= Double.parseDouble(currentInput); break;
        }
    }

    public void onSpecialFunctionClick(View view) {
        if(Objects.equals(currentInput, "")) return;
        double value = Double.parseDouble(currentInput);
        switch (((Button) view).getText().toString()) {
            case "sqrt": value = Math.sqrt(value); break;
            case "1/x": value = 1 / value; break;
            case "+-": value = -value; break;
        }
        currentInput = String.valueOf(value);
        textView.setText(currentInput);
    }

    public void onBackspace(View view) {
        if (!currentInput.isEmpty()) {
            currentInput = currentInput.substring(0, currentInput.length() - 1);
            textView.setText(currentInput.isEmpty() ? "0" : currentInput);
        }
    }
}
