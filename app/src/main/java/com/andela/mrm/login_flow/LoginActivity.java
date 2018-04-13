package com.andela.mrm.login_flow;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.andela.mrm.R;

/**
 * LoginActivity class.
 */
public class LoginActivity extends AppCompatActivity {
    FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inflateLoginButton();
        inflateOnBoarding();
    }

    /**
     * Inflates the login button fragment.
     */
    private void inflateLoginButton() {
        LoginButtonFragment buttonFragment = new LoginButtonFragment();
        manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.frame_first, buttonFragment, "button_frag");
        transaction.commit();
    }

    /**
     * Inflates the onBoarding fragment.
     */
    private void inflateOnBoarding() {
       OnBoardingFragment onBoardingFragment = new OnBoardingFragment();
        manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.frame_second, onBoardingFragment, "on_board_frag");
        transaction.commit();
    }
}
