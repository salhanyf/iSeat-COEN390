/*
    File:           SettingsActivity.java
    Authors:        Adnan Saab          #40075504
                    Samson Kaller       #40136815
                    Farah Salhany       #40074803
                    Shahin Khalkhali    #40057384
                    Shayan Khalkhali    #40059491
                    Marwan Al-Ghaziri   #40126554
    Description:    This class controls items on the Settings activity. This includes light/dark mode,
                    App permissions and notifications. These last two were in preparation for notifications,
                    which in the end were not implemented.
*/
package com.example.finalproject.views.Settings;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.EditTextPreference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import androidx.preference.SwitchPreferenceCompat;

import com.example.finalproject.R;

public class SettingsActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();

        PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(this);
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        public static SwitchPreferenceCompat bluetoothPermission, locationPermission;
        public static EditTextPreference feedback;

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);

            bluetoothPermission = (SwitchPreferenceCompat) findPreference("settingsBluetooth");
            locationPermission =  (SwitchPreferenceCompat) findPreference("settingsLocation");
            feedback = (EditTextPreference) findPreference("settingSendFeedback");
        }
    }

    //handles preferences change
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String rootKey) {
        if (rootKey != null && sharedPreferences != null){
            String appearanceRootKey = "settingsAppAppearance";
            if (rootKey.equals(appearanceRootKey)) {
                final String[] appearanceValues = getResources().getStringArray(R.array.appearance_values);
                String pref = PreferenceManager.getDefaultSharedPreferences(this)
                        .getString(appearanceRootKey, "MODE_NIGHT_FOLLOW_SYSTEM");
                // Checking which option is selected to apply the desired theme
                if (pref.equals(appearanceValues[0]))
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
                if (pref.equals(appearanceValues[1]))
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                if (pref.equals(appearanceValues[2]))
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }
            String appNotificationsRootKey = "settingsAppNotifications";
            if (rootKey.equals(appNotificationsRootKey)){
                boolean isChecked = PreferenceManager.getDefaultSharedPreferences(this)
                        .getBoolean(appNotificationsRootKey, false);
                if (isChecked){
                    Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                }
            }
            String textNotificationsRootKey = "settingsTextNotifications";
            if (rootKey.equals(textNotificationsRootKey)){
                boolean isChecked = PreferenceManager.getDefaultSharedPreferences(this)
                        .getBoolean(textNotificationsRootKey, false);
                if (isChecked){
                    Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                }
            }
            String emailNotificationsRootKey = "settingsEmailNotifications";
            if (rootKey.equals(emailNotificationsRootKey)){
                boolean isChecked = PreferenceManager.getDefaultSharedPreferences(this)
                        .getBoolean(emailNotificationsRootKey, false);
                if (isChecked){
                    Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                }
            }
            String bluetoothRootKey = "settingsBluetooth";
            if(rootKey.equals(bluetoothRootKey)){
                boolean enabled = PreferenceManager.getDefaultSharedPreferences(this)
                        .getBoolean(bluetoothRootKey, false);
                if(enabled){
                    new AlertDialog.Builder(this)
                            .setTitle("Bluetooth Permission")
                            .setMessage("Are you sure you want to allow iSeat to use bluetooth")
                            .setPositiveButton("Allow", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int i) {
                                    Toast.makeText(SettingsActivity.this, "Permission granted", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setNegativeButton("Deny", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    SettingsFragment.bluetoothPermission.setChecked(false);
                                    dialogInterface.cancel();
                                }
                            })
                            .show();
                }else{
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                }
            }
            String locationRootKey = "settingsLocation";
            if(rootKey.equals(locationRootKey)){
                boolean enabled = PreferenceManager.getDefaultSharedPreferences(this)
                        .getBoolean(locationRootKey, false);
                if(enabled){
                    new AlertDialog.Builder(this)
                            .setTitle("Location Permission")
                            .setMessage("Are you sure you want to allow iSeat to access your location")
                            .setPositiveButton("Allow", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int i) {
                                    Toast.makeText(SettingsActivity.this, "Permission granted", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setNegativeButton("Deny", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    SettingsFragment.locationPermission.setChecked(false);
                                    dialogInterface.cancel();
                                }
                            })
                            .show();
                }else{
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                }
            }
            String sendFeedback = "settingSendFeedback";
            if(rootKey.equals(sendFeedback)){
                Toast.makeText(this, "Feedback Received, Thank you!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this);
    }
}