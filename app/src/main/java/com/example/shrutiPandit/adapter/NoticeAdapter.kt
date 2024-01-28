import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shrutiPandit.NoticeItem
import com.example.shrutiPandit.R
import com.squareup.picasso.Picasso

class NoticeAdapter(private val noticelist: MutableList<NoticeItem>) : RecyclerView.Adapter<NoticeAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.title)
        val link: TextView = itemView.findViewById(R.id.link)
        val image: ImageView = itemView.findViewById(R.id.imagechoose)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.sample_notice, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val notice = noticelist[position]

        holder.title.text = notice.title
        holder.link.text = notice.link
        Picasso.get().load(notice.uri).into(holder.image)
    }

    override fun getItemCount(): Int {
        return noticelist.size
    }
}
