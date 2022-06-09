package com.bangkit.skutapplication.view.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.bangkit.skutapplication.databinding.FragmentProfileBinding
import com.bangkit.skutapplication.datastore.UserPreference
import com.bangkit.skutapplication.datastore.ViewModelFactory
import com.bangkit.skutapplication.view.profile.editprofile.AboutActivity

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


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
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.getRoot();

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
        viewModel.getUser().observe(getViewLifecycleOwner()) {
                binding.userName.text = it.username
            binding.imageProfileText.text = it.username[0].toString()
            Toast.makeText(context, it.username, Toast.LENGTH_SHORT).show()
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



    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}