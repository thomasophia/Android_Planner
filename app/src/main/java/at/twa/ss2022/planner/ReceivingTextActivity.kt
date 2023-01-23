package at.twa.ss2022.planner

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class ReceivingTextActivity : AppCompatActivity() {

    private val channelID = "1111"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receiving_text)

        when (intent?.action) {
            Intent.ACTION_SEND -> {
                intent.getStringExtra(Intent.EXTRA_TEXT)?.let {
                    findViewById<TextView>(R.id.tvReceivingText).text = it
                    showNotification(applicationContext, "New Message from Yourself", "" + it)
                }
            }
        }
    }

    fun createNotificationChannel(requireContext: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelName = requireContext.getString(R.string.notification_name)
            val notificationChannel =
                NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_DEFAULT)
            val notificationManager =
                requireContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    fun showNotification(requireContext: Context, title: String, content: String) {
        createNotificationChannel(requireContext)
        val notificationBuilder = NotificationCompat.Builder(requireContext, channelID)
            .setContentTitle(title)
            .setContentText(content)
            .setSmallIcon(R.drawable.bulb_icon)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
        val largeIcon = BitmapFactory.decodeResource(requireContext.resources, R.drawable.bulb_icon)

        notificationBuilder.setLargeIcon(largeIcon)

        val notificationId = 1
        val notificationManager = NotificationManagerCompat.from(requireContext)
        notificationManager.notify(notificationId, notificationBuilder.build())
    }
}