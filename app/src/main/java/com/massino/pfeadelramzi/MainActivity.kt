package com.massino.pfeadelramzi

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_menu_principal.*
import kotlinx.android.synthetic.main.app_bar_main.*
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity() {

    companion object {

        private const val RC_SIGN_IN = 123
        const val EXTRA_EMAIL = "user.email"
        const val EXTRA_NOM = "user.nom"
        const val EXTRA_PHOTO="user.photo"
        val db = Firebase.firestore
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_principal)
        setSupportActionBar(toolbar)



        signInButton.setOnClickListener {
            createSignInIntent()
        }

        signUp.setOnClickListener {
            createSignInIntent()
        }


    }

    private fun createSignInIntent() {
        // [START auth_fui_create_intent]
        // Choose authentication providers
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            //AuthUI.IdpConfig.PhoneBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build(),
            AuthUI.IdpConfig.FacebookBuilder().build(),
        AuthUI.IdpConfig.AnonymousBuilder().build())

        // Create and launch sign-in intent
        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build(),
            RC_SIGN_IN)
        // [END auth_fui_create_intent]
    }

    // [START auth_fui_result]
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {
                // Successfully signed in
                val user = FirebaseAuth.getInstance().currentUser

                if (user != null) {
                    if (user.uid !="Un0LPGIeAmcRO6yPTrj1c2A0RwH3"){
                        val intent2 = Intent(this, MenuPrincipal::class.java)
                        intent2.putExtra(EXTRA_EMAIL, user.email)
                        intent2.putExtra(EXTRA_NOM, user.displayName)
                        intent2.putExtra(EXTRA_PHOTO,user.photoUrl.toString())
                        startActivity(intent2)
                        finish()
                    }else{

                        val intent3 = Intent(this, MenuPrincipalGerant::class.java)
                        intent3.putExtra(EXTRA_EMAIL, user.email)
                        intent3.putExtra(EXTRA_NOM, user.displayName)
                        intent3.putExtra(EXTRA_PHOTO,user.photoUrl.toString())
                        startActivity(intent3)
                        finish()
                    }
                }else {
                    Toast.makeText(this,"Utilisateur introuvable",Toast.LENGTH_LONG).show()
                }

                }else{
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
                toast("opération annullée")
            }

        }
    }
    // [END auth_fui_result]

    private fun signOut() {
        // [START auth_fui_signout]
        AuthUI.getInstance()
            .signOut(this)
            .addOnCompleteListener {
                // ...
            }

    }

    private fun delete() {
        // [START auth_fui_delete]
        AuthUI.getInstance()
            .delete(this)
            .addOnCompleteListener {
                // ...
            }

    }



}