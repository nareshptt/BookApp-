package com.example.findbook.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.findbook.R
import com.squareup.picasso.Picasso
import org.json.JSONObject

class DetailsActivity : AppCompatActivity() {

    lateinit var txtbookname: TextView
    lateinit var txtbookAuther: TextView
    lateinit var txtbookprice: TextView
    lateinit var bookimaeg: ImageView
    lateinit var txtbookdec: TextView
    lateinit var txtbookaddtofac: Button
    lateinit var progressbar: ProgressBar
    lateinit var progressbarlayout: RelativeLayout

    var bookId: String? = "100"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        txtbookname = findViewById(R.id.txtbookname)
        txtbookAuther = findViewById(R.id.Autharname)
        txtbookprice = findViewById(R.id.txtbookprice)
        bookimaeg = findViewById(R.id.iiimage)
        txtbookdec = findViewById(R.id.txtbookdec)
        txtbookaddtofac = findViewById(R.id.addtofavbtn)
        progressbar = findViewById(R.id.progressbar)
        progressbarlayout = findViewById(R.id.progressbarlayout)
        progressbar.visibility = View.VISIBLE
        progressbarlayout.visibility = View.VISIBLE


        if (intent != null){
            bookId = intent.getStringExtra("book_id")

        }else{
            finish()
            Toast.makeText(this@DetailsActivity,"Some error occured",Toast.LENGTH_SHORT).show()

        }
        if (bookId == "100"){
            finish()
            Toast.makeText(this@DetailsActivity,"Some error occured",Toast.LENGTH_SHORT).show()

        }

        val queue = Volley.newRequestQueue(this@DetailsActivity)
        val url = "http://13.235.250.119/v1/book/fetch_books/"

        val jsonParams = JSONObject()
        jsonParams.put("book_id", bookId)

        val jsonRequest = object : JsonObjectRequest(Request.Method.POST,url, jsonParams, Response.Listener {

          try {

              val success = it.getBoolean("success")
              if (success){
                  val bookJsonObject = it.getJSONObject("book_data")
                  progressbarlayout.visibility = View.GONE

                  Picasso.get().load(bookJsonObject.getString("image"))
                  txtbookname.text = bookJsonObject.getString("name")
                  txtbookAuther.text = bookJsonObject.getString("author")
                  txtbookprice.text = bookJsonObject.getString("price")

              } else{
                  Toast.makeText(this@DetailsActivity,"Some error occured",Toast.LENGTH_SHORT).show()

              }

          } catch (e: Exception){
              Toast.makeText(this@DetailsActivity,"Some error occured",Toast.LENGTH_SHORT).show()


          }
        },Response.ErrorListener {

            Toast.makeText(this@DetailsActivity,"Volly Error $it",Toast.LENGTH_SHORT).show()


        }){

            override  fun getHeaders() : MutableMap<String, String>{
                val headers = HashMap<String, String> ()
                headers["Content-type"] = "application/json"
                headers["token"] = "0d093966eee269"
                return headers
            }
        }


    }
}