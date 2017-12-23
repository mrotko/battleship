package com.example.michal.battleship.views.settingsView;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.example.michal.battleship.R;

public class SettingsFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.fragment_settings);
    }
}