package com.hninhnin.kotlin_module_two

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var imgCamera: ImageView
    private lateinit var btnCamera: Button
    private lateinit var btnShare: Button
    private lateinit var btnWebView: Button
    private lateinit var btnBackground: Button
    private lateinit var btnNetwork: Button
    private val reqCode: Int = 123

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        imgCamera = findViewById(R.id.imgCamera)
        btnCamera = findViewById(R.id.btnCamera)
        btnShare = findViewById(R.id.btnShare)
        btnWebView = findViewById(R.id.btnWebView)
        btnBackground = findViewById(R.id.btnBackground)
        btnNetwork = findViewById(R.id.btnNetwork)

        btnCamera.setOnClickListener {
            takeCameraPhoto()
        }

        btnShare.setOnClickListener {
            // change screen
            val intent = Intent(this, SharePreference::class.java)
            startActivity(intent)
            // save application local storage (share prefs)
            val sharePreference = getSharedPreferences("LocalData", Context.MODE_PRIVATE)

        }
        btnWebView.setOnClickListener {
            val intent = Intent(this, WebViewActivity::class.java)
            startActivity(intent)
        }

        btnBackground.setOnClickListener {
            val intent = Intent(this, BackgroundDesign::class.java)
            startActivity(intent)
        }

        btnNetwork.setOnClickListener {
            if(checkNetwork())
                Toast.makeText(this, "Internet Access", Toast.LENGTH_SHORT).show()
            else Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show()
        }
    }

    // take photo with camera
    private fun takeCameraPhoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if(intent.resolveActivity(packageManager) !=null){
            startActivityForResult(intent, reqCode)
        }
    }

    // get image set after take photo
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(reqCode == requestCode && resultCode == RESULT_OK) {
            val bitMap = data?.extras?.get("data") as Bitmap
            imgCamera.setImageBitmap(bitMap)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    // check connection
    private fun checkNetwork() : Boolean {
        val connectivityManager: ConnectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return true
        return when{
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                true
            }
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                true
            }else ->
             false
        }
    }
}