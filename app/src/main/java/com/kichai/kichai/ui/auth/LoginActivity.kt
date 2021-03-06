package com.kichai.kichai.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.kichai.kichai.R
import com.kichai.kichai.ui.CustomerHome
import com.kichai.kichai.ui.DeliverymanHome
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        register_text.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.and(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

        login_button.setOnClickListener {
            if ( email_text.text.toString().isNotEmpty() && password_text.text.toString().isNotEmpty() )
            {
                FirebaseAuth.getInstance().signInWithEmailAndPassword(email_text.text.toString(), password_text.text.toString())
                    .addOnSuccessListener {
                        if ( usertype_selector.checkedRadioButtonId == R.id.customer_radio )
                        {
                            FirebaseFirestore.getInstance().collection("customer").whereEqualTo("email", email_text.text.toString()).get()
                                .addOnSuccessListener {
                                    if ( !it.documents.isEmpty() )
                                    {
                                        Toast.makeText(this, "Signed in successfully", Toast.LENGTH_SHORT).show()
                                        val intent = Intent(this, CustomerHome::class.java)
                                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.and(Intent.FLAG_ACTIVITY_NEW_TASK)
                                        startActivity(intent)
                                        finish()
                                    }
                                    else
                                    {
                                        Toast.makeText(this, "Customer not found", Toast.LENGTH_SHORT).show()
                                        FirebaseAuth.getInstance().signOut()
                                    }
                                }
                        }
                        else if ( usertype_selector.checkedRadioButtonId == R.id.deliveryman_radio )
                        {
                            FirebaseFirestore.getInstance().collection("deliveryman").whereEqualTo("email", email_text.text.toString()).get()
                                .addOnSuccessListener {
                                    if ( !it.documents.isEmpty() )
                                    {
                                        Toast.makeText(this, "Signed in successfully", Toast.LENGTH_SHORT).show()
                                        val intent = Intent(this, DeliverymanHome::class.java)
                                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.and(Intent.FLAG_ACTIVITY_NEW_TASK)
                                        startActivity(intent)
                                        finish()
                                    }
                                    else
                                    {
                                        Toast.makeText(this, "Deliveryman not found", Toast.LENGTH_SHORT).show()
                                        FirebaseAuth.getInstance().signOut()
                                    }
                                }
                        }
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "${it.message}", Toast.LENGTH_SHORT).show()
                    }
            }
        }
    }
}
