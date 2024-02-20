package com.example.findbook.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.findbook.R
import com.example.findbook.activity.DetailsActivity
import com.example.findbook.model.Book
import com.squareup.picasso.Picasso

class dashboardadeptor(val context: Context, val itemList: ArrayList<Book> ):
    RecyclerView.Adapter<dashboardadeptor.dashbordViewHolder> (){

    class  dashbordViewHolder(view:View): RecyclerView.ViewHolder(view){

        val textBookName: TextView = view.findViewById(R.id.txtBookName)
        val textBookAuthor: TextView = view.findViewById(R.id.txtBookAuthor)
        val textBookPrice: TextView = view.findViewById(R.id.txtBookPrice)
        val imgBookImage: ImageView = view.findViewById(R.id.imgBookImage)
        val contentClick: LinearLayout = view.findViewById(R.id.content)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): dashbordViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.single_row_data_view, parent, false)

        return  dashbordViewHolder(view)
    }

    override fun getItemCount(): Int {

        return itemList.size
    }

    override fun onBindViewHolder(holder: dashbordViewHolder, position: Int) {

        val book = itemList[position]
        holder.textBookName.text = book.bookName
        holder.textBookAuthor.text = book.bookAothor
        holder.textBookPrice.text = book.bookPrice
        //holder.imgBookImage.setBackgroundResource(book.bookImage)
        Picasso.get().load(book.bookImage).into(holder.imgBookImage)
        holder.contentClick.setOnClickListener{
            val intent = Intent(context,DetailsActivity::class.java)
            intent.putExtra("book_id", book.bookId)
            context.startActivity(intent)
        }

    }
}