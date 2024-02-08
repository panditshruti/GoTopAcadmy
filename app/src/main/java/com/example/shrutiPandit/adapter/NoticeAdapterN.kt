package com.example.shrutiPandit.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.shrutiPandit.PdfViewActivity
import com.example.shrutiPandit.R
import com.example.shrutiPandit.db.NoticeItem
import com.google.firebase.storage.storageMetadata
import com.squareup.picasso.Picasso

class NoticeAdapterN(
    private var noticeList: ArrayList<NoticeItem>,private var context: Context
) : RecyclerView.Adapter<NoticeAdapterN.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.title)
        val link: TextView = itemView.findViewById(R.id.link)
        val images: ImageView = itemView.findViewById(R.id.imageuser)
        val date: TextView = itemView.findViewById(R.id.date)
        val openPdf: ImageView = itemView.findViewById(R.id.pdfOpen)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.sample_notice, parent, false)
        return ViewHolder(view)
    }

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
            intent.putExtra("PDF",notice.pdf)
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
