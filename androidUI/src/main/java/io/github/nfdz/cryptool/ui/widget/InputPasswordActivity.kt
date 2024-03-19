package io.github.nfdz.cryptool.ui.widget

import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import io.github.nfdz.cryptool.ui.R

class InputPasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input_password)

        val editText = findViewById<EditText>(R.id.password)
        val button = findViewById<ImageView>(R.id.enterButton)

        button.setOnClickListener {
            val sharedPreferences = getSharedPreferences("WidgetPrefs", Context.MODE_PRIVATE)
            sharedPreferences.edit().putString("broadcastPasswordValue", editText.text.toString()).apply()
            sendBroadcast(Intent("com.widget.refresh.password"))
            finish()
        }
    }
}