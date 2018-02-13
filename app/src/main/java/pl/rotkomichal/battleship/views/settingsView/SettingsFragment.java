package pl.rotkomichal.battleship.views.settingsView;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import pl.rotkomichal.battleship.R;

public class SettingsFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.fragment_settings);
    }
}