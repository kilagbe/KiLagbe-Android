package com.kichai.kichai.ui.profile

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.google.firebase.auth.EmailAuthProvider
import com.kichai.kichai.R
import com.kichai.kichai.data.User
import com.kichai.kichai.databasing.ProfileHelper
import com.kichai.kichai.ui.auth.LoginActivity

class ProfileFragment : Fragment(), ProfileHelper.getCustomerSuccessListener, ProfileHelper.getCustomerFailureListener, ProfileHelper.getDeliverymanSuccessListener, ProfileHelper.getDeliverymanFailureListener, ProfileHelper.changeProfileSuccessListener, ProfileHelper.changeProfileFailureListener {

    lateinit var username_text: TextView
    lateinit var useremail_text: TextView
    lateinit var userphone_text: TextView

    lateinit var mContext: Context

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mContext = this.context!!

        val ph = ProfileHelper()
        ph.setGetCustomerSuccessListener(this)
        ph.setGetCustomerFailureListener(this)
        ph.setGetDeliverymanSuccessListener(this)
        ph.setGetDeliverymanFailureListener(this)
        ph.setChangeProfileSuccessListener(this)
        ph.setChangeProfileFailureListener(this)

        val root = inflater.inflate(R.layout.fragment_profile, container, false)

        username_text = root.findViewById<TextView>(R.id.username)
        useremail_text = root.findViewById<TextView>(R.id.useremail)
        userphone_text = root.findViewById<TextView>(R.id.userphone)

        val logout_button = root.findViewById<Button>(R.id.logout_button)
        val username_edit_button = root.findViewById<Button>(R.id.username_edit)
        val useremail_edit_button = root.findViewById<Button>(R.id.useremail_edit)
        val userpass_edit_button = root.findViewById<Button>(R.id.userpass_edit)
        val userphone_edit_button = root.findViewById<Button>(R.id.userphone_edit)

        val user = ph.getUser()

        logout_button.setOnClickListener {
            ph.logout()
            val intent = Intent(activity, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK.and(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
        }

        val uid = ph.getUid()

        ph.getCustomer(uid!!)
        ph.getDeliveryman(uid!!)


//        username_edit_button.setOnClickListener {
//            val dialog = AlertDialog.Builder(mContext).create()
//            val dialogview = inflater.inflate(R.layout.edit_dialog, null)
//            dialogview.findViewById<Button>(R.id.confirm_button).setOnClickListener {
//                if ( dialogview.findViewById<EditText>(R.id.edit).text.toString() == dialogview.findViewById<EditText>(R.id.edit_confirm).text.toString() && !dialogview.findViewById<EditText>(R.id.edit).text.toString().isEmpty())
//                {
//                    val type = when (dialogview.findViewById<RadioGroup>(R.id.usertype_selector).checkedRadioButtonId)
//                    {
//                        R.id.deliveryman_radio -> "deliveryman"
//                        R.id.customer_radio -> "customer"
//                        else -> "error"
//                    }
//                    user.reauthenticate(EmailAuthProvider.getCredential(dialogview.findViewById<EditText>(R.id.edit_email).text.toString(), dialogview.findViewById<EditText>(R.id.edit_password).text.toString()))
//                        .addOnSuccessListener {
//                            ph.changeProfileName(dialogview.findViewById<EditText>(R.id.edit).text.toString(), type)
//                            dialog.dismiss()
//                        }
//                        .addOnFailureListener {
//                            Toast.makeText(mContext, "${it.message}", Toast.LENGTH_SHORT).show()
//                        }
//                }
//                else
//                {
//                    Toast.makeText(mContext, "Not matching", Toast.LENGTH_SHORT).show()
//                }
//            }
//            dialogview.findViewById<Button>(R.id.cancel_button).setOnClickListener {
//                dialog.dismiss()
//            }
//            dialog.setView(dialogview)
//            dialog.setCancelable(true)
//            dialog.show()
//        }
//
//        userpass_edit_button.setOnClickListener {
//            val dialog = AlertDialog.Builder(mContext).create()
//            val dialogview = inflater.inflate(R.layout.edit_dialog, null)
//            dialogview.findViewById<Button>(R.id.confirm_button).setOnClickListener {
//                if ( dialogview.findViewById<EditText>(R.id.edit).text.toString() == dialogview.findViewById<EditText>(R.id.edit_confirm).text.toString() && !dialogview.findViewById<EditText>(R.id.edit).text.toString().isEmpty() )
//                {
//                    val type = when (dialogview.findViewById<RadioGroup>(R.id.usertype_selector).checkedRadioButtonId)
//                    {
//                        R.id.deliveryman_radio -> "deliveryman"
//                        R.id.customer_radio -> "customer"
//                        else -> "error"
//                    }
//                    user.reauthenticate(EmailAuthProvider.getCredential(dialogview.findViewById<EditText>(R.id.edit_email).text.toString(), dialogview.findViewById<EditText>(R.id.edit_password).text.toString()))
//                        .addOnSuccessListener {
//                            ph.changeProfilePassword(dialogview.findViewById<EditText>(R.id.edit).text.toString())
//                            dialog.dismiss()
//                        }
//                        .addOnFailureListener {
//                            Toast.makeText(mContext, "${it.message}", Toast.LENGTH_SHORT).show()
//                        }
//                }
//                else
//                {
//                    Toast.makeText(mContext, "Not matching", Toast.LENGTH_SHORT).show()
//                }
//            }
//            dialogview.findViewById<Button>(R.id.cancel_button).setOnClickListener {
//                dialog.dismiss()
//            }
//            dialog.setView(dialogview)
//            dialog.setCancelable(true)
//            dialog.show()
//        }
//
//        useremail_edit_button.setOnClickListener {
//            val dialog = AlertDialog.Builder(mContext).create()
//            val dialogview = inflater.inflate(R.layout.edit_dialog, null)
//            dialogview.findViewById<Button>(R.id.confirm_button).setOnClickListener {
//                if ( dialogview.findViewById<EditText>(R.id.edit).text.toString() == dialogview.findViewById<EditText>(R.id.edit_confirm).text.toString() && !dialogview.findViewById<EditText>(R.id.edit).text.toString().isEmpty() )
//                {
//                    val type = when (dialogview.findViewById<RadioGroup>(R.id.usertype_selector).checkedRadioButtonId)
//                    {
//                        R.id.deliveryman_radio -> "deliveryman"
//                        R.id.customer_radio -> "customer"
//                        else -> "error"
//                    }
//                    user.reauthenticate(EmailAuthProvider.getCredential(dialogview.findViewById<EditText>(R.id.edit_email).text.toString(), dialogview.findViewById<EditText>(R.id.edit_password).text.toString()))
//                        .addOnSuccessListener {
//                            ph.changeProfileEmail(dialogview.findViewById<EditText>(R.id.edit).text.toString(), type)
//                            dialog.dismiss()
//                        }
//                        .addOnFailureListener {
//                            Toast.makeText(mContext, "${it.message}", Toast.LENGTH_SHORT).show()
//                        }
//                }
//                else
//                {
//                    Toast.makeText(mContext, "Not matching", Toast.LENGTH_SHORT).show()
//                }
//            }
//            dialogview.findViewById<Button>(R.id.cancel_button).setOnClickListener {
//                dialog.dismiss()
//            }
//            dialog.setView(dialogview)
//            dialog.setCancelable(true)
//            dialog.show()
//        }
//
//        userphone_edit_button.setOnClickListener {
//            val dialog = AlertDialog.Builder(mContext).create()
//            val dialogview = inflater.inflate(R.layout.edit_dialog, null)
//            dialogview.findViewById<Button>(R.id.confirm_button).setOnClickListener {
//                if ( dialogview.findViewById<EditText>(R.id.edit).text.toString() == dialogview.findViewById<EditText>(R.id.edit_confirm).text.toString() && !dialogview.findViewById<EditText>(R.id.edit).text.toString().isEmpty() )
//                {
//                    val type = when (dialogview.findViewById<RadioGroup>(R.id.usertype_selector).checkedRadioButtonId)
//                    {
//                        R.id.deliveryman_radio -> "deliveryman"
//                        R.id.customer_radio -> "customer"
//                        else -> "error"
//                    }
//                    user.reauthenticate(EmailAuthProvider.getCredential(dialogview.findViewById<EditText>(R.id.edit_email).text.toString(), dialogview.findViewById<EditText>(R.id.edit_password).text.toString()))
//                        .addOnSuccessListener {
//                            ph.changeProfilePhone(dialogview.findViewById<EditText>(R.id.edit).text.toString(), type)
//                            dialog.dismiss()
//                        }
//                        .addOnFailureListener {
//                            Toast.makeText(mContext, "${it.message}", Toast.LENGTH_SHORT).show()
//                        }
//                }
//                else
//                {
//                    Toast.makeText(mContext, "Not matching", Toast.LENGTH_SHORT).show()
//                }
//            }
//            dialogview.findViewById<Button>(R.id.cancel_button).setOnClickListener {
//                dialog.dismiss()
//            }
//            dialog.setView(dialogview)
//            dialog.setCancelable(true)
//            dialog.show()
//        }
        return root
    }

    override fun getCustomerSuccess(customer: User) {
        username_text.text = customer.name
        userphone_text.text = customer.phone
    }

    override fun getCustomerFailure() {
        Toast.makeText(mContext, "Failed to get customer", Toast.LENGTH_SHORT).show()
    }

    override fun getDeliverymanFailure() {
        Toast.makeText(mContext, "Failed to get deliveryman", Toast.LENGTH_SHORT).show()
    }

    override fun getDeliverymanSuccess(deliveryman: User) {
        username_text.text = deliveryman.name
        userphone_text.text = deliveryman.phone

    }

    override fun changeProfileSuccess() {
        Toast.makeText(mContext, "Successfully edited profile", Toast.LENGTH_SHORT).show()
    }

    override fun changeProfileFailure() {
        Toast.makeText(mContext, "Failed to edit profile", Toast.LENGTH_SHORT).show()
    }
}
