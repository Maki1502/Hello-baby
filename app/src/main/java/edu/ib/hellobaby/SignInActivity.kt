package edu.ib.hellobaby

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.text.TextUtils
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_in.*


class SignInActivity : AppCompatActivity() {

    private val mAuth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        signup_btn_link.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        login_btn.setOnClickListener {
            loginUser()
        }

        forget_btn.setOnClickListener {
            showRecoveryPassDialog()
        }

    }

    private fun loginUser() {
        val email = email_login.text.toString()
        val password = password_login.text.toString()

        when {
            TextUtils.isEmpty(email) -> Toast.makeText(this, "potrzebny e-mail", Toast.LENGTH_LONG)
                .show()
            TextUtils.isEmpty(password) -> Toast.makeText(
                this,
                "potrzebne hasło",
                Toast.LENGTH_LONG
            ).show()

            else -> {
                val progressDialog = ProgressDialog(this@SignInActivity)
                progressDialog.setTitle("Logowanie")
                progressDialog.setMessage("Proszę czekać, to może zająć chwilę...")
                progressDialog.setCanceledOnTouchOutside(false)
                progressDialog.show()

                val mAuth = FirebaseAuth.getInstance()
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        progressDialog.dismiss()

                        val intent = Intent(this@SignInActivity, MainActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                        finish()

                    } else {
                        val message = task.exception!!.toString()
                        Toast.makeText(this, "Error: $message", Toast.LENGTH_LONG).show()

                        FirebaseAuth.getInstance().signOut()
                        progressDialog.dismiss()
                    }
                }
            }
        }
    }

    var loadingBar: ProgressDialog? = null

    private fun showRecoveryPassDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("Odzyskaj hasło")
        val linearLayout = LinearLayout(this)
        val emailet = EditText(this)

        emailet.hint = "Email"
        emailet.minEms = 16
        emailet.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
        linearLayout.addView(emailet)
        linearLayout.setPadding(10, 10, 10, 10)
        builder.setView(linearLayout)

        builder.setPositiveButton("Wyślij"
        ) { _, _ ->
            val email = emailet.text.toString().trim { it <= ' ' }
            beginRecovery(email)
        }
        builder.setNegativeButton("Powrót"
        ) { dialog, _ -> dialog.dismiss() }
        builder.create().show()
    }

    private fun beginRecovery(email: String) {
        loadingBar = ProgressDialog(this)
        loadingBar!!.setMessage("Wysyłanie w toku...")
        loadingBar!!.setCanceledOnTouchOutside(false)
        loadingBar!!.show()

        mAuth?.sendPasswordResetEmail(email)
            ?.addOnCompleteListener { task ->
                loadingBar!!.dismiss()
                if (task.isSuccessful) {
                    Toast.makeText(this@SignInActivity, "Wysłano", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this@SignInActivity, "Pojawił się błąd", Toast.LENGTH_LONG)
                        .show()
                }
            }?.addOnFailureListener {
                loadingBar!!.dismiss()
                Toast.makeText(this@SignInActivity, "Próba nieudana", Toast.LENGTH_LONG).show()
            }
    }

    override fun onStart() {
        super.onStart()

        if (FirebaseAuth.getInstance().currentUser != null) {
            val intent = Intent(this@SignInActivity, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
    }
}