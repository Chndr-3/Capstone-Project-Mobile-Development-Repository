package com.bangkit.skutapplication.view.register

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.bangkit.skutapplication.R
import com.bangkit.skutapplication.databinding.ActivityRegisterBinding
import com.bangkit.skutapplication.model.user.RegisterModel
import com.bangkit.skutapplication.view.customview.MyButton
import com.bangkit.skutapplication.view.customview.MyEditText
import java.util.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var myButton: MyButton
    private lateinit var myEditText: MyEditText
    private val registerViewModel: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backButton.setOnClickListener {
            onBackPressed()
        }

        myEditText = binding.passwordEditText
        myButton = binding.registerButton

        setupAction()

        setMyButtonEnable()

        myEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                setMyButtonEnable()
            }

            override fun afterTextChanged(s: Editable) {

            }
        })

        registerViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        registerViewModel.isSuccess.observe(this) {
            showResult(it)
        }

        when (resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)) {
            Configuration.UI_MODE_NIGHT_YES -> {
                binding.backButton.setImageResource(R.drawable.ic_baseline_arrow_back_24)
            }
            Configuration.UI_MODE_NIGHT_NO -> {
                binding.backButton.setImageResource(R.drawable.ic_baseline_arrow_back_black_24)
            }
            Configuration.UI_MODE_NIGHT_UNDEFINED -> {
                binding.backButton.setImageResource(R.drawable.ic_baseline_arrow_back_black_24)
            }
        }
    }

    private fun setupAction() {
        binding.registerButton.setOnClickListener {
            val name = binding.nameEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val password = myEditText.text.toString()
            val password2 = binding.passwordEditText2.text.toString()
            when {
                name.isEmpty() -> {
                    binding.nameEditTextLayout.error = getString(R.string.enter_name)
                }
                email.isEmpty() -> {
                    binding.emailEditTextLayout.error = getString(R.string.enter_email)
                }
                password.isEmpty() -> {
                    binding.passwordEditTextLayout.error = getString(R.string.enter_password)
                }
                password2.isEmpty() -> {
                    binding.passwordEditTextLayout.error = getString(R.string.enter_password)
                }
                password2 != password -> {
                    binding.passwordEditTextLayout2.error = getString(R.string.password_does_not_match)
                }
                else -> {
                    registerViewModel.registerUser(RegisterModel(name, email, password, password2))
                }
            }
        }
    }

    private fun setMyButtonEnable() {
        val email = binding.emailEditText.text
        val password = myEditText.text
        myButton.isEnabled = password != null && "$password".isNotEmpty() && email != null && "$email".isNotEmpty()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun showResult(isSuccess: Boolean) {
        if (isSuccess) {
            Log.d("kok gak masuk sini", "yey123")
            AlertDialog.Builder(this).apply {
                setTitle("Hore!")
                setMessage(getString(R.string.account_created))
                setPositiveButton(getString(R.string.next)) { _, _ ->
                    finish()
                }
                create()
                show()
            }
        } else {
            Log.d("kok gak masuk sini", "yey321")
            Toast.makeText(
                this@RegisterActivity,
                getString(R.string.email_already_registered),
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}