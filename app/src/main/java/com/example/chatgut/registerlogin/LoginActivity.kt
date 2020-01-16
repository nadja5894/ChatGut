package com.example.chatgut.registerlogin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.chatgut.R
import com.example.chatgut.messages.LatestMessagesActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

// Compat = compatibility - Vereinbarkeit.
class LoginActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        register_button_login.setOnClickListener {
            performLogin()
        }

        //Zurück zur Registrierung (Beendet Activity, zurück zum Main)
        back_register_textView.setOnClickListener {
            finish()
        }
    }

        private fun performLogin() {
            val email = email_editText_login.text.toString()
            val password = password_editText_login.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Bitte E-Mail und Passwort eingeben.", Toast.LENGTH_SHORT)
                    .show()
                return
            }

            Log.d("LoginActivity", "Versuchter Login mit: $email")

            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {
            //wenn NICHT successful
            if (!it.isSuccessful) return@addOnCompleteListener

            //else if successful
            Log.d("Login", "Erfolgreich eingeloggt mit User UID: ${it.result?.user?.uid}")

             val intent = Intent(this, LatestMessagesActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)

               }
               .addOnFailureListener() {
                   Log.d("Login", "Anmelden fehlgeschlagen: ${it.message}")
                   Toast.makeText(this, "Anmelden fehlgeschlagen: ${it.message}", Toast.LENGTH_SHORT).show()
               }


        }



    }


