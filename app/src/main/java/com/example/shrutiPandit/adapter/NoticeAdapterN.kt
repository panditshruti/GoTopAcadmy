import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.shrutiPandit.PdfViewActivity
import com.example.shrutiPandit.R
import com.example.shrutiPandit.db.NoticeItem
import com.ortiz.touchview.TouchImageView
import com.squareup.picasso.Picasso

class NoticeAdapterN(
    private var noticeList: ArrayList<NoticeItem>,
    private val context: Context
) : RecyclerView.Adapter<NoticeAdapterN.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.title)
        val link: TextView = itemView.findViewById(R.id.link)
        val images: TouchImageView = itemView.findViewById(R.id.imageuser)
        val date: TextView = itemView.findViewById(R.id.date)
        val openPdf: ImageView = itemView.findViewById(R.id.pdfOpen)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.sample_notice, parent, false)
        return ViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val notice = noticeList[position]

        if (notice.title != "") {
            holder.title.text = notice.title
        } else {
            holder.title.visibility = View.GONE
        }

        if (notice.link != "") {
            holder.link.text = notice.link

            // Remove the Intent creation here

            holder.link.setOnClickListener {
                // Open link in a browser
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(notice.link))
                context.startActivity(intent)

            }
        } else {
            holder.link.visibility = View.GONE
        }


        if (notice.imageUrl != "") {
            Picasso.get().load(notice.imageUrl).into(holder.images)
        } else {
            holder.images.visibility = View.GONE
        }

        if (notice.date != "") {
            holder.date.text = notice.date
        } else {
            holder.date.visibility = View.GONE
        }

        if (notice.pdf != "") {
            holder.openPdf.setOnClickListener {
                val intent = Intent(context, PdfViewActivity::class.java)
                intent.putExtra("PDF", notice.pdf)
                context.startActivity(intent)
                 updateData(noticeList)
            }
        } else {
            holder.openPdf.visibility = View.GONE
        }


    }

    override fun getItemCount(): Int {
        return noticeList.size
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun updateData(newNoticeList: ArrayList<NoticeItem>) {
        // Update your data and notify the adapter
        noticeList.clear()
        noticeList.addAll(newNoticeList)
        notifyDataSetChanged()

        // Display a notification for the newly fetched data
        if (newNoticeList.isNotEmpty()) {
            val latestNotice = newNoticeList[0] // Assuming the latest notice is at index 0
            latestNotice.title?.let {
                sendNotification("New Notice", it, "your_channel_id", Intent())
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun sendNotification(title: String, message: String, channelId: String, intent: Intent) {
        val pendingIntent = PendingIntent.getActivity(
            context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT
        )

        val channel = NotificationChannel(
            channelId,
            "Channel Name",
            NotificationManager.IMPORTANCE_DEFAULT
        ).apply {
            description = "Channel Description"
        }

        val notificationManager: NotificationManager =
            context.getSystemService(AppCompatActivity.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)

        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_launcher_foreground) // Use your own icon
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(context)) {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            notify(1, builder.build())
        }
    }
}
