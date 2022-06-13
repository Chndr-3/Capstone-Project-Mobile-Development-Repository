package com.bangkit.skutapplication.view.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.bangkit.skutapplication.databinding.FragmentProfileBinding
import com.bangkit.skutapplication.datastore.UserPreference
import com.bangkit.skutapplication.datastore.ViewModelFactory
import com.bangkit.skutapplication.view.main.MainActivity
import com.bangkit.skutapplication.view.profile.editprofile.AboutActivity
import java.util.*

class ProfileFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private lateinit var binding: FragmentProfileBinding
    private lateinit var viewModel: ProfileViewModel
    private val Context.dataStore by preferencesDataStore(name = "profile")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentProfileBinding.inflate(layoutInflater)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonLanguage.setOnClickListener {
            startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
        }
        binding.buttonEdit.setOnClickListener {
            val intent = Intent(activity, AboutActivity::class.java)
            startActivity(intent)
        }
        
        viewModel.getUser().observe(viewLifecycleOwner) {
            if (it.token.isNotEmpty()) {
                binding.userName.text = it.username
                binding.imageProfileText.text = it.username[0].toString()
                    .uppercase(Locale.getDefault())
            } else {
                startActivity(Intent(activity, MainActivity::class.java))
            }
        }
        binding.buttonLogout.setOnClickListener {
            viewModel.logout()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(context.dataStore))
        )[ProfileViewModel::class.java]

    }


}