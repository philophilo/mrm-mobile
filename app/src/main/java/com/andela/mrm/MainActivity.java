package com.andela.mrm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * MainActivity Class.
 */
public class MainActivity extends AppCompatActivity {

    /**
     * onCreate Method.
     *
     * @param savedInstanceState - Used to SAve/Restore Application State.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
