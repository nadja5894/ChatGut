package com.example.chatgut.registerlogin

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import com.example.chatgut.R
import com.example.chatgut.messages.LatestMessagesActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_register.*
import java.util.*

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)


        //Klick auf Registrieren-Button
        register_button_register.setOnClickListener {
            performRegister()
        }

        //Verlinkung Login-Seite
        already_account_textView.setOnClickListener {
         Log.d("RegisterActivity", "Try to show something"
         )

        // Tatsächliche Weiterleitung
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
     }

        //Button zur Auswahl eines Profilbildes
        picture_button_register.setOnClickListener {
            Log.d("RegisterActivity", "Versuch, Fotoauswahl anzuzeigen.")

            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)
        }
    }

    var selectPhotoURI: Uri? = null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null) {
            //weiteres Laden und schauen, was das gewählte Bild ist
            Log.d("RegisterActivity", "Foto ausgewählt.")

            // uri = Speicherort des Bildes auf dem Handy
            selectPhotoURI = data.data
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectPhotoURI)

            picture_imageView_register.setImageBitmap(bitmap)

            picture_button_register.alpha = 0f

            //val bitmapDrawable = BitmapDrawable (bitmap)
            //picture_button_register.setBackgroundDrawable(bitmapDrawable)
        }
    }

    private fun performRegister() {
        val email = email_editText_register.text.toString()
        val password = password_editText_register.text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Bitte E-Mail und Passwort eingeben.", Toast.LENGTH_SHORT).show()
            return
        }

        Log.d("RegisterActivity", "Email ist:" + email)
        Log.d("RegisterActivity", "Passwort: $password")

        //Firebase Authentifizierung zum Registirieren mit Email und PW
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener{
                //wenn NICHT successful
                if (!it.isSuccessful) return@addOnCompleteListener

                //else if successful
                Log.d("RegisterActivity", "Erfolgreich User erstellt mit UID: ${it.result?.user?.uid}")
                // Nutzer registrieren ermöglichen ohne Auswahl eines Profilbild - voreingestelltes Bild wird verwendet
                if(selectPhotoURI == null){
                    saveUserToFirebaseDatabase("null")
                    saveUserToFirebaseDatabase("https://firebasestorage.googleapis.com/v0/b/chatgut-fire.appspot.com/o/images%2Fdefault_avatar.png?alt=media&token=606c06f5-6aff-4ac6-99c1-9acefd677b1a")
                } else{
                    uploadImageToFirebaseStorage()
                }
            }


            .addOnFailureListener{
                Log.d("RegisterActivity", "Benutzer anlegen fehlgeschlagen: ${it.message}")
                Toast.makeText(this, "Benutzer anlegen fehlgeschlagen: ${it.message}", Toast.LENGTH_SHORT).show()
            }

    }

    private fun uploadImageToFirebaseStorage(){

        if (selectPhotoURI == null) return

        val filename = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/images/$filename")

        ref.putFile(selectPhotoURI!!)
            .addOnSuccessListener {
                Log.d("RegisterActivity", "Bild erfolgreich hochgeladen: ${it.metadata?.path}")

                ref.downloadUrl.addOnSuccessListener {
                    Log.d("RegisterActivity", "Speicherort: $it")

                    saveUserToFirebaseDatabase(it.toString())
                }
            }

            .addOnFailureListener{
                //do some loggin here
            }

    }

    private fun saveUserToFirebaseDatabase(profileImageURL: String) {
        val uid = FirebaseAuth.getInstance().uid ?:""
        val ref = FirebaseDatabase.getInstance().getReference("/users/$uid")

        val user = User(
            uid,
            username_editText_register.text.toString(),
            profileImageURL
        )

        ref.setValue(user)
            .addOnSuccessListener {
                Log.d("RegisterActivity", "Benutzer gespeichert in der Firebase Database.")

                val intent = Intent(this, LatestMessagesActivity::class.java)

                //Bringt einen zurück auf den Homebildschirm statt zurück zum Registrieren.
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
    }
}

class User(val uid: String, val username: String, val profileImageURL: String) {
    constructor() : this("","","")
}