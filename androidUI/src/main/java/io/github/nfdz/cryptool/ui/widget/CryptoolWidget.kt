package io.github.nfdz.cryptool.ui.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.RemoteViews
import android.widget.Toast
import io.github.nfdz.cryptool.ui.R

/**
 * Implementation of App Widget functionality.
 */
class CryptoolWidget : AppWidgetProvider() {
    var widgetId = 0
    lateinit var mAppWidgetManager: AppWidgetManager

    override fun onAppWidgetOptionsChanged(
        context: Context?,
        appWidgetManager: AppWidgetManager?,
        appWidgetId: Int,
        newOptions: Bundle?
    ) {
        super.onAppWidgetOptionsChanged(context, appWidgetManager, appWidgetId, newOptions)
    }
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            widgetId = appWidgetId
            mAppWidgetManager = appWidgetManager
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)

//        val sharedPreferences = context?.getSharedPreferences("WidgetPrefs", Context.MODE_PRIVATE)
//        val updatedValue = sharedPreferences?.getString("broadcastPasswordValue", "")
//        val views = RemoteViews(context?.packageName, R.layout.cryptool_widget)
//        views.setTextViewText(R.id.passwordInput, updatedValue)



        val action = intent?.action
        if (action == "com.widget.refresh.password" || action == AppWidgetManager.ACTION_APPWIDGET_UPDATE) {
            updateAppWidget(context!!, mAppWidgetManager, widgetId)
//                val updatedValue = sharedPreferences?.getString("broadcastPasswordValue", "")
//                Toast.makeText(context, updatedValue, Toast.LENGTH_LONG).show()
//                val views = RemoteViews(context?.packageName, R.layout.cryptool_widget)
//                views.setTextViewText(R.id.passwordInput, updatedValue)
        }
        if (action == "com.widget.refresh.message" || action == AppWidgetManager.ACTION_APPWIDGET_UPDATE){
            updateAppWidget(context!!, mAppWidgetManager, widgetId)
//                val updatedValue = sharedPreferences?.getString("broadcastMessageValue", "")
//                Toast.makeText(context, updatedValue, Toast.LENGTH_LONG).show()
//                val views = RemoteViews(context?.packageName, R.layout.cryptool_widget)
//                views.setTextViewText(R.id.textInput, updatedValue)
        }
        //views.setViewVisibility(R.id.errorBackground, 0)

        /*
        if (context != null) {
            updateAppWidget(context, mAppWidgetManager, widgetId)
        }*/
    }
}

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {
    // Construct the RemoteViews object
    val views = RemoteViews(context.packageName, R.layout.cryptool_widget)

    val sharedPreferences = context.getSharedPreferences("WidgetPrefs", Context.MODE_PRIVATE)
    val updatedValueP = sharedPreferences?.getString("broadcastPasswordValue", "")
    val updatedValueM = sharedPreferences?.getString("broadcastMessageValue", "")
    views.setTextViewText(R.id.passwordInput, updatedValueP)
    views.setTextViewText(R.id.textInput, updatedValueM)
    Toast.makeText(context, updatedValueP, Toast.LENGTH_LONG).show()
    Toast.makeText(context, updatedValueM, Toast.LENGTH_LONG).show()



    val passwordClickIntent = Intent(context, PasswordClickReceiver::class.java)
    passwordClickIntent.action = "com.widget.receiver.password"
    val pendingIntentPassword = PendingIntent.getBroadcast(context, 0, passwordClickIntent, PendingIntent.FLAG_UPDATE_CURRENT)


    val messageClickIntent = Intent(context, MessageClickReceiver::class.java)
    messageClickIntent.action = "com.widget.receiver.message"
    val pendingIntentMessage = PendingIntent.getBroadcast(context, 0, messageClickIntent, PendingIntent.FLAG_UPDATE_CURRENT)


    views.setOnClickPendingIntent(R.id.passwordLayout, pendingIntentPassword)
    views.setOnClickPendingIntent(R.id.messageLayout, pendingIntentMessage)

    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views)
}