package kr.ac.hongic.ce.b611065.ck_min.netsec_insec_memo.register

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import kr.ac.hongic.ce.b611065.ck_min.netsec_insec_memo.R
import kr.ac.hongic.ce.b611065.ck_min.netsec_insec_memo.databinding.ActivityRegisterBinding


class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val PASSWORD_KEY = "password"

    private val masterKeyAlias: String = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        sharedPreferences = EncryptedSharedPreferences.create(
            "secret_shared_prefs",
            masterKeyAlias,
            this,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
        val registerButton = binding.register
        val title = binding.registerTitle
        val desc = binding.registerDesc
        if (isPasswordExists()) {
            registerButton.text = getString(R.string.loginButtonText)
            title.text = getString(R.string.loginButtonText)
            desc.text = getString(R.string.loginDesc)
            title.text = getString(R.string.action_sign_in)
            desc.text = getString(R.string.registerDesc)
        } else {
            title.text = getString(R.string.action_sign_in)
            desc.text = getString(R.string.registerDesc)
        }




        passwordFocusListener()
        registerButton.setOnClickListener { submitForm() }
    }

    private fun isPasswordExists(): Boolean {
        return sharedPreferences.contains(PASSWORD_KEY)
    }

    private fun savePassword(rawPassword: String) {
        val editor = sharedPreferences.edit()
        editor.putString(PASSWORD_KEY, rawPassword)
        editor.apply()
    }

    private fun checkPassword(rawPassword: String): Boolean {
        val retrievedPassword = sharedPreferences.getString(PASSWORD_KEY, "")
        return retrievedPassword == rawPassword
    }

    private fun submitForm() {
        binding.passwordHelperText.text = validPassword()
        val validPassword = binding.passwordHelperText.text == ""

        if (validPassword) {
            val password = binding.password.text.toString()
            if (isPasswordExists()) {
                if (checkPassword(password)) {
                    moveToEdit()
                } else {
                    resetForm("Password incorrect")
                }
            } else {
                savePassword(password)
                moveToEdit()
            }


        } else
            invalidForm()
    }

    private fun moveToEdit() {
        Intent().also { intent ->
            intent.action = "actions.intent.show_edit"
//            intent.putExtra("data", "Notice me senpai!")
            startActivity(intent)
        }

    }

    private fun invalidForm() {
        var message = ""
        if (binding.passwordHelperText.text != null)
            message += "\n\nPassword: " + binding.passwordHelperText.text


        AlertDialog.Builder(this)
            .setTitle("Invalid Form")
            .setMessage(message)
            .setPositiveButton("Okay") { _, _ ->
                // do nothing
            }
            .show()
    }

    private fun resetForm(message: String) {
        AlertDialog.Builder(this)
            .setTitle("Form submitted")
            .setMessage(message)
            .setPositiveButton("Okay") { _, _ ->
                binding.password.text = null
                binding.passwordHelperText.text = getString(R.string.password_invalid_length)
            }

            .show()
    }


    private fun passwordFocusListener() {

        binding.password.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.passwordHelperText.text = validPassword()
            }
        })
    }

    private fun validPassword(): String? {
        val passwordText = binding.password.text.toString()
        if (passwordText.length < 8) {
            return getString(R.string.password_invalid_length)
        }
        if (!passwordText.matches(".*[A-Z].*".toRegex())) {
            return getString(R.string.password_invalid_upper)
        }
        if (!passwordText.matches(".*[a-z].*".toRegex())) {
            return getString(R.string.password_invalid_lower)
        }
        if (!passwordText.matches(".*[!@#\$%^&+=].*".toRegex())) {
            return getString(R.string.password_invalid_special)
        }

        return null
    }


}