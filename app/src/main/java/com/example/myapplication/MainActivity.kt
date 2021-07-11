package com.example.myapplication

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.os.LocaleList
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import com.example.myapplication.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import com.lokalise.sdk.Lokalise
import com.lokalise.sdk.LokaliseContextWrapper
import com.lokalise.sdk.LokaliseResources
import java.util.Locale


class MainActivity : AppCompatActivity() {

	private lateinit var appBarConfiguration: AppBarConfiguration
	private lateinit var binding: ActivityMainBinding
	private lateinit var menu: Menu
	var localeSeleted = 0
	override fun attachBaseContext(newBase: Context) {
		// Inject the Lokalise SDK into the activity context
		super.attachBaseContext(LokaliseContextWrapper.wrap(newBase))
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		binding = ActivityMainBinding.inflate(layoutInflater)
		setContentView(binding.root)
		setSupportActionBar(binding.toolbar)
		val adapter = ArrayAdapter.createFromResource(
			this,
			R.array.locales, R.layout.spinner_locale_item
		)
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
		binding.spinnerNav.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
			override fun onItemSelected(
				parent: AdapterView<*>?,
				view: View,
				position: Int,
				id: Long
			) {
				if (localeSeleted > 0) {
					val newConfig = Configuration()
					newConfig.setLocales(
						LocaleList(
							when (position) {
								0 -> Locale.ENGLISH
								1 -> Locale.GERMAN
								2 -> Locale("es", "ES")
								3 -> Locale.ITALIAN
								4 -> Locale.FRENCH
								else -> Locale.ENGLISH
							}
						)
					)
					onConfigurationChanged(newConfig)
				}
				localeSeleted++
			}

			override fun onNothingSelected(parent: AdapterView<*>?) {
				Lokalise.setLocale(Locale.ENGLISH.language, Locale.ENGLISH.country)
			}
		}

		binding.spinnerNav.adapter = adapter

		binding.toolbar.setTitle(R.string.first_fragment_label)

		/*val navController = findNavController(R.id.nav_host_fragment_content_main)
		appBarConfiguration = AppBarConfiguration(navController.graph)
		setupActionBarWithNavController(navController, appBarConfiguration)

		*/
		var dynamicString = "default dynamic value"
		binding.fab.setOnClickListener { view ->
			val resources = LokaliseResources(this)
			val newKey = resources.getString("dynamic_string")
			newKey?.let {
				// do something with the new value
				dynamicString = it
			}
			Snackbar.make(view, dynamicString, Snackbar.LENGTH_LONG)
				.setAction("Action", null).show()
		}
	}

	override fun onCreateOptionsMenu(menu: Menu): Boolean {
		// Inflate the menu; this adds items to the action bar if it is present.

		menuInflater.inflate(R.menu.menu_main, menu)
		this.menu = menu
		menu.findItem(R.id.action_settings).setTitle(R.string.action_settings)
		(resources as LokaliseResources).translateToolbarItems(binding.toolbar)
		return true
	}

	override fun onConfigurationChanged(newConfig: Configuration) {
		newConfig.locales.get(0).let {
			Lokalise.setLocale(it.language, it.country)
			Toast.makeText(this, it.language, Toast.LENGTH_SHORT).show()
		}
		setContentView(binding.root)
		menu.findItem(R.id.action_settings).setTitle(R.string.action_settings)
		super.onConfigurationChanged(newConfig)
	}

	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		return when (item.itemId) {
			R.id.action_settings -> true
			else -> super.onOptionsItemSelected(item)
		}
	}

	override fun onSupportNavigateUp(): Boolean {
		val navController = findNavController(R.id.nav_host_fragment_content_main)
		return navController.navigateUp(appBarConfiguration)
				|| super.onSupportNavigateUp()
	}
}