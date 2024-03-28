
package com.shrutipandit.gotopacademy.adapter
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.shrutipandit.gotopacademy.activity.PdfViewActivity
import com.shrutipandit.gotopacademy.R
import com.shrutipandit.gotopacademy.db.NoticeItem
import com.ortiz.touchview.TouchImageView
import com.shrutipandit.gotopacademy.db.Profile
import com.squareup.picasso.Picasso

class NoticeAdapterN(
    private var noticeList: ArrayList<NoticeItem>,
    private val context: Context
) : RecyclerView.Adapter<NoticeAdapterN.ViewHolder>() {

    private var onItemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onDeleteClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.onItemClickListener = listener
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.title)
        val link: TextView = itemView.findViewById(R.id.link)
        val images: ImageView = itemView.findViewById(R.id.imageuser)
        val date: TextView = itemView.findViewById(R.id.date)
        val prise: TextView = itemView.findViewById(R.id.prise)
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
        fun capitalizeFirstWord(input: String): String {
            return input.split(" ").joinToString(" ") { it.capitalize() }
        }
        // Set title
        if (notice.title != "") {
            holder.title.text = notice.title?.let { capitalizeFirstWord(it) }
        } else {
            holder.title.visibility = View.GONE
        }

        // Set link
        if (notice.link != "") {
            holder.link.text = notice.link

            // Open link in a browser
//            holder.link.setOnClickListener {
//                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(notice.link))
//                context.startActivity(intent)
//            }
        } else {
            holder.link.visibility = View.GONE
        }

        // Set image
        if (notice.imageUrl != "") {
            Picasso.get().load(notice.imageUrl).into(holder.images)
        } else {
            holder.images.visibility = View.GONE
        }

        // Set date
        if (notice.date != "") {
            holder.date.text = notice.date
        } else {
            holder.date.visibility = View.GONE
        }

        // Set prise
        if (notice.prise != "") {
            holder.prise.visibility = View.VISIBLE
            holder.prise.text = notice.prise
        } else {
            holder.prise.visibility = View.GONE
        }

        // Set PDF
        if (notice.pdf != "") {
            holder.openPdf.setOnClickListener {
                val intent = Intent(context, PdfViewActivity::class.java)
                intent.putExtra("PDF", notice.pdf)
                context.startActivity(intent)
            }
        } else {
            holder.openPdf.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int {
        return noticeList.size
    }
}
