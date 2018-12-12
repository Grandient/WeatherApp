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

    public static class PreferencesFragment extends PreferenceFragment {

        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);

            // Listen for changes on (List)Preference
            Preference.OnPreferenceChangeListener listPrefChangeListener =
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

            // Update ListPreference summary on value change
            findPreference(getString(R.string.app_theme_preference_key))
                    .setOnPreferenceChangeListener(listPrefChangeListener);
            findPreference(getString(R.string.app_temp_units_preference_key))
                    .setOnPreferenceChangeListener(listPrefChangeListener);

            // Update ListPreference summary on activity launch
            ListPreference appThemePref = (ListPreference)findPreference(getString(R.string.app_theme_preference_key));
            appThemePref.setSummary(appThemePref.getEntry());
            ListPreference appTempUnitPref = (ListPreference)findPreference(getString(R.string.app_temp_units_preference_key));
            appTempUnitPref.setSummary(appTempUnitPref.getEntry());
        }
    }
}
