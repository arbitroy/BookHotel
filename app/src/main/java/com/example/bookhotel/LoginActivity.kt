package com.example.bookhotel

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.example.bookhotel.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {


    private lateinit var binding:ActivityLoginBinding
    private  lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //for changing status bar icon colors
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.cirLoginButton.setOnClickListener {


            val email = binding.editTextEmail.text.toString()
            val password = binding.editTextPassword.text.toString()


            if (email.isNotEmpty() && password.isNotEmpty()){


                    firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                        if (it.isSuccessful){


                            binding.editTextEmail.text.clear()
                            binding.editTextPassword.text.clear()

                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            finish()

                        } else{

                            val mToast =  Toast.makeText(this@LoginActivity, it.exception.toString() , Toast.LENGTH_LONG)


                            val myview = mToast.view

                            myview!!.setBackgroundResource(R.drawable.toast_bg)

                            val mToastText = mToast.view!!.findViewById<TextView>(android.R.id.message)

                            mToastText.setTextColor(Color.parseColor("#FFFFFF"))

                            mToast.show()

                        }
                    }


            }else{

                val mToast =  Toast.makeText(this@LoginActivity, "Empty Inputs are not Allowed!!", Toast.LENGTH_LONG)
                val myview = mToast.view

                myview!!.setBackgroundResource(R.drawable.toast_bg)

                val mToastText = mToast.view!!.findViewById<TextView>(android.R.id.message)

                mToastText.setTextColor(Color.parseColor("#FFFFFF"))

                mToast.show()
            }

        }


    }

    fun onLoginClick(View: View?) {
        startActivity(Intent(this, RegisterActivity::class.java))
        overridePendingTransition(R.anim.slide_in_left, R.anim.stay)
        finish()

    }

    override fun onStart() {
        super.onStart()


//        if(firebaseAuth.currentUser != null){
//            val intent = Intent(this, UserList::class.java)
//            startActivity(intent)
//        }

    }


}