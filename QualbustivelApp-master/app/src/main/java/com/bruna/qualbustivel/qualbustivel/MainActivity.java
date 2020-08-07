package com.bruna.qualbustivel.qualbustivel;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    private SeekBar gasPriceSeekBar;
    private SeekBar ethanolPriceSeekbar;
    private TextView gasValueTextView;
    private TextView ethValueTextView;
    private TextInputEditText resultTextInput;
    private ImageView bestFuelImageView;

    private double gasPrice = 0.1;
    private double ethPrice = 0.1;

    // Inicializa number format
    private static final NumberFormat currencyFormat =
            NumberFormat.getCurrencyInstance();
    private static final NumberFormat percentFormat =
            NumberFormat.getPercentInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gasPriceSeekBar     = (SeekBar) findViewById(R.id.gasPriceSeekBar);
        ethanolPriceSeekbar = (SeekBar) findViewById(R.id.ethanolPriceSeekbar);
        gasValueTextView    = (TextView) findViewById(R.id.gasValueTextView);
        ethValueTextView    = (TextView) findViewById(R.id.ethValueTextView);
        resultTextInput     = (TextInputEditText) findViewById(R.id.resultTextInput);
        bestFuelImageView   = (ImageView) findViewById(R.id.bestFuelImageView);

        // inicializa valores
        gasValueTextView.setText( currencyFormat.format(gasPrice) );
        ethValueTextView.setText( currencyFormat.format(ethPrice) );

        gasPriceSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                gasPrice = (i + 10) / 100.0f;
                gasValueTextView.setText( currencyFormat.format(gasPrice) );

                compare();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                compare();
            }
        });

        ethanolPriceSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                ethPrice = (i + 10) / 100.0f;
                ethValueTextView.setText( currencyFormat.format(ethPrice) );

                compare();

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                compare();
            }
        });


    }

    public void compare() {

        if (ethPrice / gasPrice >= 0.7) {
            resultTextInput.setText(getResources().getString(R.string.gas));
            bestFuelImageView.setImageDrawable(getResources().getDrawable(R.drawable.gas, null));
        } else {
            resultTextInput.setText(getResources().getString(R.string.eth));
            bestFuelImageView.setImageDrawable(getResources().getDrawable(R.drawable.ethanol, null));
        }

    }


}
