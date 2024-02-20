package com.example.findbook.fragment

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import com.example.findbook.R
import com.example.findbook.adapter.dashboardadeptor
import com.example.findbook.model.Book
import com.example.findbook.util.connectionManager
import org.json.JSONObject

import java.util.Objects
import kotlin.collections.ArrayList
import com.android.volley.toolbox.JsonObjectRequest as JsonObjectRequest


class DashbordFragment : Fragment() {

lateinit var  recycleDashbord : RecyclerView
lateinit var  layoutManager: RecyclerView.LayoutManager
lateinit var  recyclerAdapter : dashboardadeptor
lateinit var  progreslayout: RelativeLayout
lateinit var  progressbar: ProgressBar

val bookInfoList: ArrayList<Book> = ArrayList<Book>()

//    val bookInfoList: ArrayList<Book>
//        get() = arrayListOf<Book>(
//            Book("P.S. I love You", "Cecelia Ahern", "Rs. 299",  R.drawable.ps_ily),
//            Book("The Great Gatsby", "F. Scott Fitzgerald", "Rs. 399",  R.drawable.great_gatsby),
//            Book("Anna Karenina", "Leo Tolstoy", "Rs. 199",  R.drawable.anna_kare),
//            Book("Madame Bovary", "Gustave Flaubert", "Rs. 500",  R.drawable.madame),
//            Book("War and Peace", "Leo Tolstoy", "Rs. 249",  R.drawable.war_and_peace),
//            Book("Lolita", "Vladimir Nabokov", "Rs. 349",  R.drawable.lolita),
//            Book("Middlemarch", "George Eliot", "Rs. 599",  R.drawable.middlemarch),
//            Book("Adventures ", "Mark Twain", "Rs. 699", R.drawable.adventures_finn),
//            Book("Moby-Dick", "Herman Melville", "Rs. 499",  R.drawable.moby_dick),
//            Book("Lord of the Rings", "J.R.R Tolkien", "Rs. 749",  R.drawable.lord_of_rings)
//        )


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view =  inflater.inflate(R.layout.fragment_dashbord, container, false)

        recycleDashbord = view.findViewById(R.id.recyclerview)

        progreslayout = view.findViewById(R.id.progreshbarlayout)
        progressbar = view.findViewById(R.id.progresbar)
        progreslayout.visibility = View.VISIBLE


        layoutManager = LinearLayoutManager(activity)



        val  queue = Volley.newRequestQueue(activity as Context)
        val url = "http://13.235.250.119/v1/book/fetch_books/"

        if (connectionManager().checkConnectivity(activity as Context)) {
            val jsonObjectRequest =
                object : JsonObjectRequest(Method.GET, url, null, Response.Listener {

                    progreslayout.visibility = View.GONE
                    val success = it.getBoolean("success")

                    if (success) {

                        val data = it.getJSONArray("data")
                        for (i in 0 until data.length()) {
                            val bookJsonObject = data.getJSONObject(i)
                            val bookObject = Book(
                                bookJsonObject.getString("book_id"),
                                bookJsonObject.getString("name"),
                                bookJsonObject.getString("author"),
                                bookJsonObject.getString("price"),
                                bookJsonObject.getString("image"),
                            )

                            bookInfoList.add(bookObject)
                            recyclerAdapter = dashboardadeptor(activity as Context, bookInfoList)

                            recycleDashbord.adapter = recyclerAdapter
                            recycleDashbord.layoutManager = layoutManager

                            recycleDashbord.addItemDecoration(
                                DividerItemDecoration(
                                    recycleDashbord.context,
                                    (layoutManager as LinearLayoutManager).orientation
                                )
                            )
                        }

                    } else {
                        Toast.makeText(
                            activity as Context,
                            "Some Error Occurred!!!",
                            Toast.LENGTH_SHORT
                        ).show()

                    }

                }, Response.ErrorListener {

                    println("Error is $it")

                }) {
                    override fun getHeaders(): MutableMap<String, String> {
                        val headers = HashMap<String, String>()
                        headers["Content-type"] = "application/json"
                        headers["token"] = "0d093966eee269"
                        return headers

                    }
                }

            queue.add(jsonObjectRequest)


        } else{
            val dilog = AlertDialog.Builder(activity as Context)
            dilog.setTitle("Error")
            dilog.setMessage("Internet Connection Not Found")
            dilog.setPositiveButton("Open Settings"){text, listener ->
                val  settingsIntent = Intent(Settings.ACTION_WIRELESS_SETTINGS)
                startActivity(settingsIntent)
                activity?.finish()

            }
            dilog.setNegativeButton("Exit") {text, listener ->
                ActivityCompat.finishAffinity(activity as Activity)
            }
            dilog.create()
            dilog.show()

        }

        return  view
    }

}