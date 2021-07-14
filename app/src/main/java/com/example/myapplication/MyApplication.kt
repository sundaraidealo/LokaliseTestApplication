package com.example.myapplication

import android.app.Application
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import com.lokalise.sdk.Lokalise
import com.lokalise.sdk.LokaliseCallback
import com.lokalise.sdk.LokaliseUpdateError

/**
 * @author sundara.santhanam
 * @since 09.07.2021
 */

class MyApplication :Application(){
	override fun onCreate() {
		super.onCreate()
		AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
		// Initialise Lokalise SDK with projects SDK token and project id
		// It is important init right after the "super.onCreate()"
		Lokalise.init(this,  "9f45cea43afd721d751ca0fad8689fb59efbb3d4", "5588096260e6beffb43ad9.55374331")

		// Add this only if you want to use pre-release bundles
		Lokalise.isPreRelease = true

		// Fetch the latest translations from Lokalise (can be called anywhere)
		Lokalise.updateTranslations();
		val myCallback =  object: LokaliseCallback{

			override fun onUpdateFailed(error: LokaliseUpdateError) {
				Log.d("Lokalise Update","Update failed")
			}

			override fun onUpdateNotNeeded() {
				Log.d("Lokalise Update","Update not necessary")
			}

			override fun onUpdated(oldBundleId: Long, newBundleId: Long) {
				Log.d("Lokalise Update","OldVersion:$oldBundleId NewVersion:$newBundleId")
			}
		}

		Lokalise.addCallback(myCallback);
	}
}