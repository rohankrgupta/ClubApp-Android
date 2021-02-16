package com.example.unidayz


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    companion object{
        private const val RC_SIGN_IN = 120
    }

    val db = Firebase.firestore
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = FirebaseAuth.getInstance()

        val signUp: TextView = findViewById(R.id.sign_inpg)
        val logIn: Button = findViewById(R.id.log_in)


        signUp.setOnClickListener{
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }

        logIn.setOnClickListener{
            doLogin()
        }

        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        // Build a GoogleSignInClient with the options specified by gso.
        googleSignInClient = GoogleSignIn.getClient(this, gso);

        google_login_btn.setOnClickListener{
            signIn()
        }


    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d("MainActivity", "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w("MainActivity", "Google sign in failed", e)
                // ...
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("MainActivity", "signInWithCredential:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("MainActivity", "signInWithCredential:failure", task.exception)
                    // ...
                    updateUI(null)
                }

                // ...
            }
    }

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)

    }

    private fun updateUI(currentUser: FirebaseUser?){

        if(currentUser != null){
            if(currentUser.isEmailVerified) {
                Toast.makeText(
                    baseContext, "Authentication Successful",
                    Toast.LENGTH_SHORT
                ).show()

                val uid  = FirebaseAuth.getInstance().uid ?:""
                val docRef = db.collection("users").document(uid)

                docRef.get()
                    .addOnSuccessListener { task ->
                        val document = task.data

                        if (document != null) {
                            if(document.getValue("useruniversity") != null){
                                startActivity(Intent(this, DashboardActivity::class.java))
                                finish()
                            }else{
                                startActivity(Intent(this, InfoActivity::class.java))
                                finish()
                            }
                        }else{
                            Log.d("InfoActivity", "Document is null")
                            startActivity(Intent(this, InfoActivity::class.java))
                            finish()
                        }
                    }


            }else{
                Toast.makeText(baseContext, "Please verify your Email",
                    Toast.LENGTH_SHORT).show()
            }
        }else{
            Toast.makeText(baseContext, "Please Sign-up",
                Toast.LENGTH_SHORT).show()
        }
    }

    private fun doLogin(){
        if(editText12.text.toString().isEmpty()){
            editText12.error = "Please enter Password"
            editText12.requestFocus()
            return
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(editText11.text.toString()).matches()){
            editText11.error = "Please enter valid Email"
            editText11.requestFocus()
            return
        }

        auth.signInWithEmailAndPassword(editText11.text.toString(), editText12.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    updateUI(null)
                }
            }
    }

}


