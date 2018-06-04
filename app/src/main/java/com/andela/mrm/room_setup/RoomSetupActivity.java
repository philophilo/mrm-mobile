package com.andela.mrm.room_setup;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.andela.mrm.R;
import com.andela.mrm.room_setup.util.IOnBackPressed;

/**
 * The type Room setup activity.
 */
public class RoomSetupActivity extends AppCompatActivity implements RoomSetupFragment.Callbacks {

    private FragmentManager mFragmentManager;

    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager()
                .findFragmentById(R.id.activity_room_setup_fragment_container);
        if (!(fragment instanceof IOnBackPressed) || !((IOnBackPressed) fragment).onBackPressed()) {
            super.onBackPressed();
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_setup);

        mFragmentManager = getSupportFragmentManager();
        Fragment fragment = mFragmentManager
                .findFragmentById(R.id.activity_room_setup_fragment_container);
        if (fragment == null) {
            fragment = RoomSetupFragment.newInstance();
            mFragmentManager
                    .beginTransaction()
                    .add(R.id.activity_room_setup_fragment_container, fragment)
                    .commit();
        }
    }

    @Override
    public void onClickSetupItem() {
        mFragmentManager
                .beginTransaction()
                .replace(R.id.activity_room_setup_fragment_container,
                        RoomSetupFragment.newInstance())
                .commit();
    }
}
