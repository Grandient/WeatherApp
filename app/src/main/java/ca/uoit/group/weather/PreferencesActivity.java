package ca.uoit.group.weather;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.v7.app.AppCompatActivity;

public class PreferencesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager()
                .beginTransaction()
                .replace(android.R.id.content, new PreferencesFragment())
                .commit();

    }


    public void changeTheme(int styleId) {
        setTheme(styleId);
        recreate();
    }

    public static class PreferencesFragment extends PreferenceFragment {

        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);

            // Listen for changes on theme ListPreference
            Preference.OnPreferenceChangeListener themePrefChangeListener =
                    new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    if (newValue != null) {
                        // Set summary
                        preference.setSummary((String) newValue);

                        // Set theme
                        PreferencesActivity parentActivity = (PreferencesActivity) getActivity();
                        if (newValue.equals("Light")) {
                            parentActivity.changeTheme(R.style.PreferencesLightTheme);
                        } else if (newValue.equals("Dark")) {
                            parentActivity.changeTheme(R.style.PreferencesDarkTheme);
                        }

                        return true;
                    }

                    return false;
                }
            };
            ListPreference appThemePref = (ListPreference)findPreference(getString(R.string.app_theme_preference_key));
            appThemePref.setSummary(appThemePref.getEntry());
            appThemePref.setOnPreferenceChangeListener(themePrefChangeListener);

            // Update ListPreference summary on activity launch
            Preference.OnPreferenceChangeListener tempUnitsPrefChangeListener =
                    new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    if (newValue != null) {
                        preference.setSummary((String) newValue);
                        return true;
                    }
                    return false;
                }
            };
            ListPreference appTempUnitPref = (ListPreference)findPreference(getString(R.string.app_temp_units_preference_key));
            appTempUnitPref.setSummary(appTempUnitPref.getEntry());
            appTempUnitPref.setOnPreferenceChangeListener(tempUnitsPrefChangeListener);
        }
    }
}
