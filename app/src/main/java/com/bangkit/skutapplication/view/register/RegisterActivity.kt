package com.bangkit.skutapplication.view.register

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
import com.google.android.material.appbar.MaterialToolbar
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

        val toolbar: MaterialToolbar = binding.toolbar
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

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

        registerViewModel.registerResult.observe(this) { result ->
            registerViewModel.registerError.observe(this) { error ->
                if (result.message?.isNotEmpty() == true) {
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
                    Toast.makeText(this, result.errors.toString(), Toast.LENGTH_SHORT).show()
                    if (error != null) {
                        for (i in 0 until error.count()) {
                            // Message
                            val message = error[i].message ?: "N/A"
                            Log.d("error2: ", message)
                        }
//                        Log.d("errorr", message)
                    }

                }
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
                    binding.nameEditTextLayout.error = "Masukkan email"
                }
                email.isEmpty() -> {
                    binding.emailEditTextLayout.error = "Masukkan email"
                }
                password.isEmpty() -> {
                    binding.passwordEditTextLayout.error = "Masukkan password"
                }
                password2.isEmpty() -> {
                    binding.passwordEditTextLayout.error = "Masukkan password"
                }
                password2 != password -> {
                    binding.passwordEditTextLayout2.error = "Password tidak sesuai"
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
}