package com.example.bookhotel

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.bookhotel.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

@Suppress("DEPRECATION")
class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)


        binding = ActivityRegisterBinding.inflate(layoutInflater)



        setContentView(binding.root)
        changeStatusBarColor()

        firebaseAuth = FirebaseAuth.getInstance()



        binding.cirRegisterButton.setOnClickListener {
          val name = binding.editTextName.text.toString()
           val email = binding.editTextEmail.text.toString()
           val phone = binding.editTextMobile.text.toString()
           val password = binding.editTextPassword.text.toString()
           val confirmpassword = binding.editTextConfirmPassword.text.toString()

//           val progressBar = binding.progressBar1

            if (name.isNotEmpty() && email.isNotEmpty() && phone.isNotEmpty() && password.isNotEmpty() && confirmpassword.isNotEmpty()){

                if (password == confirmpassword){

//                    progressBar.visibility = View.VISIBLE
                    FireBaseAuthentication()






                } else{
                    val mToast = Toast.makeText(this@RegisterActivity, "Passwords Do Not Match!!", Toast.LENGTH_LONG)
                    val myview = mToast.view

//                    myview!!.setBackgroundResource(R.drawable.toast_bg)
//
//                    val mToastText = mToast.view!!.findViewById<TextView>(android.R.id.message)
//
//                    mToastText.setTextColor(Color.parseColor("#FFFFFF"))
//
//                    mToast.show()

                }


            }else{
                val mToast =  Toast.makeText(this@RegisterActivity, "Empty Inputs are not Allowed!!", Toast.LENGTH_LONG)

                val myview = mToast.view

//                myview!!.setBackgroundResource(R.drawable.toast_bg)
//
//                val mToastText = mToast.view!!.findViewById<TextView>(android.R.id.message)
//
//                mToastText.setTextColor(Color.parseColor("#FFFFFF"))
//
//                mToast.show()
            }

        }


    }

    private fun FireBaseAuthentication() {
        val name = binding.editTextName.text.toString()
        val email = binding.editTextEmail.text.toString()
        val phone = binding.editTextMobile.text.toString()
        val password = binding.editTextPassword.text.toString()

//        val progressBar = binding.progressBar1


        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful){

//                progressBar.visibility = View.GONE

                //Code to write to the firebase database
                database = FirebaseDatabase.getInstance().getReference("Users")
                val User = User(name,email,phone,password)

                database.child(name).setValue(User).addOnSuccessListener {
                    binding.editTextName.text.clear()
                    binding.editTextEmail.text.clear()
                    binding.editTextMobile.text.clear()
                    binding.editTextPassword.text.clear()
                    binding.editTextConfirmPassword.text.clear()

                    val mToast = Toast.makeText(this@RegisterActivity, "Record Saved Successfully, LOGIN!!", Toast.LENGTH_LONG)
                    val myview = mToast.view

                    //myview!!.setBackgroundResource(R.drawable.toast_bg)

//                    val mToastText = mToast.view!!.findViewById<TextView>(android.R.id.message)

                //    mToastText.setTextColor(Color.parseColor("#FFFFFF"))

  //                  mToast.show()



                }.addOnFailureListener{
                   val mToast =  Toast.makeText(this@RegisterActivity, "Could Not Save The Records!", Toast.LENGTH_LONG)

                    val myview = mToast.view

                    //myview!!.setBackgroundResource(R.drawable.toast_bg)

                    val mToastText = mToast.view!!.findViewById<TextView>(android.R.id.message)

                    mToastText.setTextColor(Color.parseColor("#FFFFFF"))

                    mToast.show()

                }



//                            after all is done launch the login activity
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()

            } else{
//                progressBar.visibility = View.GONE
                Toast.makeText(this, it.exception.toString() , Toast.LENGTH_LONG).show()

            }
        }


    }



    private fun changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            //            window.setStatusBarColor(Color.TRANSPARENT);
            window.statusBarColor = resources.getColor(R.color.register_bk_color)
        }
    }

    fun onLoginClick(view: View?) {
        startActivity(Intent(this, LoginActivity::class.java))
        overridePendingTransition(R.anim.slide_in_left, android.R.anim.slide_out_right)
        finish()
    }
}