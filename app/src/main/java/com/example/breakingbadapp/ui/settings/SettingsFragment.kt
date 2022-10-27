package com.example.breakingbadapp.ui.settings

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.example.breakingbadapp.R


class SettingsFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preference, rootKey)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        findPreference<Preference>("send_feed_back")?.onPreferenceClickListener =
            Preference.OnPreferenceClickListener {
                Toast.makeText(requireContext(), "test", Toast.LENGTH_SHORT).show()
                true
            }
        findPreference<Preference>("developer")?.onPreferenceClickListener =
            Preference.OnPreferenceClickListener {
                openUrl("https://github.com/yunuserbek/BreakingBad")
                true
            }
        findPreference<Preference>("send_feed_back")?.onPreferenceClickListener =
            Preference.OnPreferenceClickListener {
                openUrl("https://github.com/yunuserbek")
                true
            }



    }
    private fun openUrl(url: String) {
        val viewIntent = Intent(
            "android.intent.action.VIEW",
            Uri.parse(url)
        )
        startActivity(viewIntent)
    }
}