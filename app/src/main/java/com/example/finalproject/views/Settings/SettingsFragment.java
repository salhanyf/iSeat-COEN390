package com.example.finalproject.views.Settings;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.EditTextPreference;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;

import com.example.finalproject.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);

        EditTextPreference settingsUsername = (EditTextPreference) findPreference("settingsUsername");
        SwitchPreferenceCompat settingsAppNotifications = (SwitchPreferenceCompat) findPreference("settingsAppNotifications");
        SwitchPreferenceCompat settingsTextNotifications = (SwitchPreferenceCompat) findPreference("settingsTextNotifications");
        SwitchPreferenceCompat settingsEmailNotifications = (SwitchPreferenceCompat) findPreference("settingsEmailNotifications");
        ListPreference settingsAppearance = (ListPreference) findPreference("settingsAppAppearance");
        SwitchPreferenceCompat settingsLocation = (SwitchPreferenceCompat) findPreference("SettingsLocation");

        //setting App Appearance (Light/Dark)
        settingsAppearance.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(@NonNull Preference preference, Object newValue) {
                int index = settingsAppearance.findIndexOfValue(newValue.toString());
                if (index != -1){
                    if (index == 0){
                        // Light Mode
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                        Toast.makeText(getContext(),"Light",Toast.LENGTH_LONG).show();
                    }
                    else if (index == 1){
                        // Night Mode
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                        Toast.makeText(getContext(),"Dark",Toast.LENGTH_LONG).show();
                    }
                }
                return true;
            }
        });

    }//end of onCreate
}