package com.shrutipandit.gotopacademy

import android.annotation.SuppressLint
import android.os.AsyncTask
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.shrutipandit.gotopacademy.databinding.ActivityPdfViewBinding
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle
import com.github.barteksc.pdfviewer.util.FitPolicy
import java.io.BufferedInputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

class PdfViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPdfViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPdfViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intentPdfUrl = intent.getStringExtra("PDF")

        if (!intentPdfUrl.isNullOrBlank()) {
            LoadPdfAsyncTask().execute(intentPdfUrl)
        }
    }

    @SuppressLint("StaticFieldLeak")
    private inner class LoadPdfAsyncTask : AsyncTask<String, Void, BufferedInputStream?>() {

        @Deprecated("Deprecated in Java")
        override fun doInBackground(vararg params: String?): BufferedInputStream? {
            try {
                val url = URL(params[0])
                val connection = url.openConnection() as HttpURLConnection
                connection.connect()
                return BufferedInputStream(connection.inputStream)
            } catch (e: IOException) {
                e.printStackTrace()
                return null
            }
        }

        @Deprecated("Deprecated in Java")
        override fun onPostExecute(result: BufferedInputStream?) {
            if (result != null) {
                loadPdf(result)
            } else {
                // Handle the error, show a message, etc.
            }
        }
    }

    private fun loadPdf(inputStream: BufferedInputStream) {
        binding.pdfView.fromStream(inputStream)
            .scrollHandle(DefaultScrollHandle(this))
            .spacing(10)
            .pageFitPolicy(FitPolicy.BOTH)
            .onLoad {
                // Handle the load complete event if needed
            }
            .load()
    }
}
