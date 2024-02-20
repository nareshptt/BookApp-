package com.example.findbook.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.findbook.R

class loginActivity : AppCompatActivity() {

    lateinit var  usermobile : EditText
    lateinit var userpass : EditText
    lateinit var loginbutton : Button
    lateinit var shardpreference : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        shardpreference = getSharedPreferences(getString(R.string.preference_file_name),Context.MODE_PRIVATE)

        val isloggedin = shardpreference.getBoolean("isloggedin", false)

        setContentView(R.layout.activity_login)

        if (isloggedin){
            val  intent = Intent(this@loginActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        usermobile = findViewById(R.id.usermo)
        userpass = findViewById(R.id.upass)
        loginbutton = findViewById(R.id.loginbtn)

        val validMo = "6353635764"
        val validpass = arrayOf("nareshptt","tony","hulk")

        loginbutton.setOnClickListener {

            val userMo = usermobile.text.toString()
            val userPass = userpass.text.toString()

            var nameofuser = "Aengers"
            val intent = Intent(this@loginActivity, MainActivity::class.java)


        if ((userMo == validMo) ){


            if (userPass == validpass[0]){
                nameofuser = "Naresh kumar"
                savepreference(nameofuser)
                intent.putExtra("Name", nameofuser)
                startActivity(intent)

            } else if(userPass == validpass[1]){
                nameofuser = "Tony"
                savepreference(nameofuser)
                intent.putExtra("Name", nameofuser)
                startActivity(intent)
            } else if(userPass == validpass[2]){
                nameofuser = "Hulk"
                savepreference(nameofuser)
                intent.putExtra("Name", nameofuser)
                startActivity(intent)
            }
            else{
                Toast.makeText(this@loginActivity,"invalid user",Toast.LENGTH_SHORT).show()
            }

        }
        else{
            Toast.makeText(this@loginActivity,"invalid user",Toast.LENGTH_SHORT).show()
        }

        }



    }
     fun savepreference (title: String){

         shardpreference.edit().putBoolean("isloggedin", true).apply()
         shardpreference.edit().putString("Title", title).apply()

     }

  }
