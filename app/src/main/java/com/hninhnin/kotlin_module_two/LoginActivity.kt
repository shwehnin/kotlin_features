package com.hninhnin.kotlin_module_two

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class LoginActivity : AppCompatActivity() {
    private lateinit var txtLoginEmail: EditText
    private lateinit var txtLoginPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var chkRemember: CheckBox

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        txtLoginEmail = findViewById(R.id.txtLoginEmail)
        txtLoginPassword = findViewById(R.id.txtLoginPassword)
        btnLogin = findViewById(R.id.btnLogin)
        chkRemember = findViewById(R.id.chkRemember)

        val sharePreference = getSharedPreferences("LocalData", Context.MODE_PRIVATE)
        val saveEmail = sharePreference.getString("Email", "")
        val savePassword = sharePreference.getString("Password", "")
        val isRemember = sharePreference.getBoolean("Remember Me", false)

        // check remember me checked
        if(isRemember) {
            navigateToHome()
            finish()
        }

        // create login
        btnLogin.setOnClickListener {
            val enterEmail = txtLoginEmail.text.toString()
            val enterPassword = txtLoginPassword.text.toString()
            if(enterEmail == saveEmail && enterPassword == savePassword) {
                Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                val editor = sharePreference.edit()
                if(chkRemember.isChecked) {
                    editor.putBoolean("Remember Me", true)
                }else {
                    editor.putBoolean("Remember Me", false)
                }
                editor.apply()
                navigateToHome()
            }else {
                Toast.makeText(this, "Invalid Email or Password", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // change screen
    private fun navigateToHome() {
        val intent = Intent(this, WebViewActivity::class.java)
        startActivity(intent)
        finish()
    }
}