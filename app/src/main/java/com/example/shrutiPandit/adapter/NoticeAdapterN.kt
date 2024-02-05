package com.example.shrutiPandit.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shrutiPandit.db.NoticeItem
import com.example.shrutiPandit.R
import com.github.barteksc.pdfviewer.PDFView
import com.squareup.picasso.Picasso

class NoticeAdapterN(
    private  var  noticeList: ArrayList<NoticeItem>
) : RecyclerView.Adapter<NoticeAdapterN.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.title)
        val link: TextView = itemView.findViewById(R.id.link)
        val images: ImageView = itemView.findViewById(R.id.imageuser)
        val date: TextView = itemView.findViewById(R.id.date)
        val pdfview: PDFView = itemView.findViewById(R.id.idPDFView)
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
            // Load PDF using PDFView library
            holder.pdfview.fromUri(Uri.parse(notice.pdf)).load()
        } else {
            holder.pdfview.visibility = View.GONE
        }

    }

    override fun getItemCount(): Int {
        return noticeList.size
    }
}
