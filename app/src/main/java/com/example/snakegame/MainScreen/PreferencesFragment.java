package com.example.snakegame.MainScreen;

import android.os.Bundle;
import android.preference.Preference;

import androidx.preference.PreferenceFragmentCompat;

import com.example.snakegame.R;

public class PreferencesFragment extends PreferenceFragmentCompat implements Preference.OnPreferenceChangeListener{

    public PreferencesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        setPreferencesFromResource(R.xml.preferences, s);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        return false;
    }
}