package com.ahmadfzlbs.lp3icheckin.activity

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import androidx.appcompat.app.AlertDialog
import com.ahmadfzlbs.lp3icheckin.custom.CustomBottomSheetFragment
import com.ahmadfzlbs.lp3icheckin.databinding.ActivityForgotPasswordBinding
import com.ahmadfzlbs.lp3icheckin.service.NetworkManager
import com.google.firebase.auth.FirebaseAuth
import com.thekhaeng.pushdownanim.PushDownAnim
import kotlinx.android.synthetic.main.activity_forgot_password.btnReset

class ForgotPasswordActivity : AppCompatActivity() {

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private lateinit var binding: ActivityForgotPasswordBinding
    private lateinit var connectivityManager: NetworkManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnReset.isEnabled = false

        if(!checkJaringan()){
            val bottomSheetFragment = CustomBottomSheetFragment()
            bottomSheetFragment.isCancelable = false
            bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
        }

        val bottomSheetFragment = CustomBottomSheetFragment()
        bottomSheetFragment.isCancelable = false

        connectivityManager = NetworkManager(this)
        connectivityManager.observe(this){ isConnected ->
            if(!isConnected){
                if(!bottomSheetFragment.isVisible)
                    bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
            } else {
                if(bottomSheetFragment.isVisible)
                    bottomSheetFragment.dismiss()
            }
        }

        PushDownAnim.setPushDownAnimTo(binding.btnReset).setOnClickListener {
            val email = binding.emailResetPassword.text.toString()

            if(checkJaringan()){
                if(email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    binding.btnReset.isEnabled = false

                    val progressDialog = ProgressDialog(this)
                    progressDialog.setTitle("Tunggu")
                    progressDialog.setMessage("Mohon tunggu...")
                    progressDialog.setCancelable(false)
                    progressDialog.show()

                    auth.sendPasswordResetEmail(email).addOnCompleteListener { task ->
                        binding.btnReset.isEnabled = true
                        progressDialog.dismiss()
                        if(task.isSuccessful){
                            showSuccessMessage("Email untuk reset kata sandi telah dikirim, silahkan periksa kotak masuk gmail anda !!")
                        } else {
                            showErrorMessage("Pastikan anda memasukkan email yang terdaftar !!")
                        }
                    }
                } else {
                    showErrorMessage("Ada kesalahan dalam autentikasi email")
                }
            } else {
                bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
            }
        }

        val textWatcher = object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val email = binding.emailResetPassword.text.toString()
                binding.btnReset.isEnabled = email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
            }
        }
        binding.emailResetPassword.addTextChangedListener(textWatcher)
    }

    private fun checkJaringan() : Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCapabilities = connectivityManager.activeNetwork ?: return false
        val actNw = connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false

        return when {
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }

    private fun showSuccessMessage(message: String){
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("Berhasil")
        alertDialog.setMessage(message)
        alertDialog.setPositiveButton("OK"){dialogInterface, which ->
            dialogInterface.dismiss()
            val i = Intent(this, LoginActivity::class.java)
            startActivity(i)
            finish()
        }
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    private fun showErrorMessage(message: String){
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("Ups sepertinya ada kesalahan")
        alertDialog.setMessage(message)
        alertDialog.setPositiveButton("OK"){dialogInterface, which ->
            dialogInterface.dismiss()
        }
        alertDialog.setCancelable(false)
        alertDialog.show()
    }
}