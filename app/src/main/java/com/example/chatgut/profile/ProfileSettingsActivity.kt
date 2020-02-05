package com.example.chatgut.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.chatgut.R
import com.example.chatgut.messages.LatestMessagesActivity
import com.example.chatgut.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthSettings
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_profile_settings.*
import kotlinx.android.synthetic.main.nav_header.*

class ProfileSettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_settings)

        button_profileSettings.setOnClickListener {
            //changeUserSettings()
        }
    }
}

//private fun changeUserSettings(){
  //  val user = FirebaseAuth.getInstance().currentUser
    //val txtNewPass = editTextPassword.text

    //user!!.updatePassword(txtNewPass).addOnCompleteListener { task ->
    //    if (task.isSuccessful) {
      //      println("Update Success")
        //} else {
          //  println("Error Update")
       // }
    //}


    //val userData = FirebaseAuth.getInstance().currentUser
    //val ref = FirebaseDatabase.getInstance().getReference("/users/$userData")


    //val userData = FirebaseAuth.getInstance().uid
    //val ref = FirebaseDatabase.getInstance().getReference("/users/$userData")
    //ref.addListenerForSingleValueEvent(object: ValueEventListener {
        //override fun onDataChange(p0: DataSnapshot) {
            //LatestMessagesActivity.currentUser = p0.getValue(User::class.java)



            //Picasso.get()
              //  .load(LatestMessagesActivity.currentUser?.profileImageURL)
                //.into(navheader_imageView)

            //navheader_TextView_username.text = LatestMessagesActivity.currentUser?.username

        //}

//        override fun onCancelled(p0: DatabaseError) {

  //      }

    //})
//}

