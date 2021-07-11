package com.example.myapplication

import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.example.myapplication.databinding.FragmentFirstBinding
import com.lokalise.sdk.Lokalise

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

	private var _binding: FragmentFirstBinding? = null

	// This property is only valid between onCreateView and
	// onDestroyView.
	private val binding get() = _binding!!

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {

		_binding = FragmentFirstBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		binding.buttonFirst.setOnClickListener {
			findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
		}
		activity?.let{it as AppCompatActivity }?.supportActionBar?.setTitle (R.string.first_fragment_label)
	}

	override fun onResume() {
		super.onResume()
		activity?.let{it as AppCompatActivity}?.supportActionBar?.setTitle (R.string.first_fragment_label)
	}

	override fun onConfigurationChanged(newConfig: Configuration) {
		super.onConfigurationChanged(newConfig)
		binding.buttonFirst.setText(R.string.next)
		binding.textviewFirst.setText(R.string.hello_first_fragment)
		activity?.let{it as AppCompatActivity }?.supportActionBar?.setTitle (R.string.first_fragment_label)
	}

	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}
}