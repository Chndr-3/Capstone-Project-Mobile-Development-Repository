package com.bangkit.skutapplication.view.login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.bangkit.skutapplication.R
import com.bangkit.skutapplication.databinding.ActivityLoginBinding
import com.bangkit.skutapplication.datastore.UserPreference
import com.bangkit.skutapplication.datastore.ViewModelFactory
import com.bangkit.skutapplication.model.user.LoginModel
import com.bangkit.skutapplication.view.customview.MyButton
import com.bangkit.skutapplication.view.customview.MyEditText
import com.bangkit.skutapplication.view.main.MainActivity
import com.bangkit.skutapplication.view.register.RegisterActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var myButton: MyButton
    private lateinit var myEditText: MyEditText
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        myEditText = binding.passwordEditText
        myButton = binding.loginButton

        setupViewModel()

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

        loginViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        loginViewModel.isSuccess.observe(this) {
            if (!it) {
                Toast.makeText(this, getString(R.string.wrong_email), Toast.LENGTH_SHORT).show()
            }
        }

        loginViewModel.loginResult.observe(this) { result ->
            loginViewModel.login(result.token.toString())

            Log.d("token", result.token.toString())

            if (result.message == "Password incorrect") {
                Toast.makeText(this, result.message, Toast.LENGTH_SHORT).show()
            } else {
                AlertDialog.Builder(this).apply {
                    setTitle("Hore!")
                    setMessage(getString(R.string.login_succes))
                    setPositiveButton(getString(R.string.next)) { _, _ ->
                        val intent = Intent(context, MainActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                        finish()
                    }
                    create()
                    show()
                }
            }
        }
    }

    private fun setupViewModel() {
        loginViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[LoginViewModel::class.java]
    }

    private fun setupAction() {
        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = myEditText.text.toString()
            when {
                email.isEmpty() -> {
                    binding.emailEditTextLayout.error = getString(R.string.enter_email)

                }
                password.isEmpty() -> {
                    binding.passwordEditTextLayout.error = getString(R.string.enter_password)
                }
                else -> {
                    loginViewModel.loginUser(LoginModel(email, password))
                }
            }
        }
        binding.registerButton.setOnClickListener {
            val registerIntent = Intent(this, RegisterActivity::class.java)
            startActivity(registerIntent)
        }
    }

    private fun setMyButtonEnable() {
        val email = binding.emailEditText.text
        val password = myEditText.text
        myButton.isEnabled = password != null && "$password".isNotEmpty() && email != null && "$email".isNotEmpty()
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}