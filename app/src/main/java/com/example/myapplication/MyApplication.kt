package com.example.myapplication

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import co.lokalise.android.sdk.LokaliseSDK

/**
 * @author sundara.santhanam
 * @since 09.07.2021
 */

class MyApplication :Application(){
	override fun onCreate() {
		super.onCreate()
		AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
		// Initialise Lokalise SDK with projects SDK token and project id
		// It is important to call this right after the "super.onCreate()"
		// If you are using AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
		// make sure it is called before LokaliseSDK.init()
		LokaliseSDK.init( "9f45cea43afd721d751ca0fad8689fb59efbb3d4", "5588096260e6beffb43ad9.55374331",this)

		// Add this only if you want to use pre-release bundles
		LokaliseSDK.setPreRelease(true);

		// Fetch the latest translations from Lokalise (can be called anywhere)
		LokaliseSDK.updateTranslations();
	}
}