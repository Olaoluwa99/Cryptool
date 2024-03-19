package io.github.nfdz.cryptool.ui.widget

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import io.github.nfdz.cryptool.ui.R

class InputMessageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input_message)

        val editText = findViewById<EditText>(R.id.message)
        val button = findViewById<ImageView>(R.id.enterButton)

        button.setOnClickListener {
            val sharedPreferences = getSharedPreferences("WidgetPrefs", Context.MODE_PRIVATE)
            sharedPreferences.edit().putString("broadcastMessageValue", editText.text.toString()).apply()
            sendBroadcast(Intent("com.widget.refresh.message"))
            finish()
        }
    }
}