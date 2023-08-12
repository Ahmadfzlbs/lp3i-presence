package com.ahmadfzlbs.lp3icheckin.activity

import com.ahmadfzlbs.lp3icheckin.custom.CustomBottomSheetFragment
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
import com.ahmadfzlbs.lp3icheckin.databinding.ActivityLoginBinding
import com.ahmadfzlbs.lp3icheckin.service.NetworkManager
import com.google.firebase.auth.FirebaseAuth
import com.thekhaeng.pushdownanim.PushDownAnim

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private lateinit var networkManager: NetworkManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnMasuk.isEnabled = false

        val bottomSheetFragment = CustomBottomSheetFragment()
        bottomSheetFragment.isCancelable = false

        if(!checkJaringan()){
            bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
        }

        networkManager = NetworkManager(this)
        networkManager.observe(this) { isConnected ->
            if(!isConnected){
                if(!bottomSheetFragment.isVisible)
                    bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
            } else {
                if(bottomSheetFragment.isVisible)
                    bottomSheetFragment.dismiss()
            }
        }

        PushDownAnim.setPushDownAnimTo(binding.btnMasuk).setOnClickListener {
            val email = binding.email.text.toString().trim()
            val pass = binding.password.text.toString().trim()

            if(checkJaringan()){
                if(email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches() && pass.isNotEmpty()) {
                    if (pass.length >= 8) {
                        binding.btnMasuk.isEnabled = false

                        val progressDialog = ProgressDialog(this)
                        progressDialog.setTitle("Tunggu")
                        progressDialog.setMessage("Mohon tunggu...")
                        progressDialog.setCancelable(false)
                        progressDialog.show()

                        auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener { task ->
                            binding.btnMasuk.isEnabled = true
                            progressDialog.dismiss()
                            if (task.isSuccessful) {
                                val user = auth.currentUser
                                if(user != null && !user.isEmailVerified){
                                    user.sendEmailVerification().addOnCompleteListener { task ->
                                        if(task.isSuccessful){
                                            showSuccessMessage("Email verifikasi telah dikirim, silahkan cek kotak masuk pada gmail anda !!")
                                        } else {
                                            showErrorMessage("Gagal mengirim verifikasi email, silahkan coba kembali !!")
                                        }
                                    }
                                } else {
                                    val i = Intent(this, MenuActivity::class.java)
                                    startActivity(i)
                                    finish()
                                }
                            } else {
                                val exception = task.exception
                                if (exception != null) {
                                    when {
                                        exception.message?.contains("password") == true -> {
                                            showErrorMessage("Sepertinya kata sandi yang anda masukkan salah !!")
                                        }
                                        exception.message?.contains("no user record") == true -> {
                                            showErrorMessage("Sepertinya email yang anda masukkan tidak terdaftar !!")
                                        }
                                        else -> {
                                            showErrorMessage("Terjadi kesalahan dalam autentikasi")
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        binding.password.setError("Kata sandi minimal 8 karakter")
                    }
                } else {
                    binding.email.setError("Email yang anda masukkan tidak valid")
                }
            } else {
                bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
            }
        }

        PushDownAnim.setPushDownAnimTo(binding.lupaKataSandi).setOnClickListener {
            val i = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(i)
        }

        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                val email = binding.email.text.toString()
                val pass = binding.password.text.toString()

                binding.btnMasuk.isEnabled = email.isNotEmpty() && pass.isNotEmpty()
            }
        }
        binding.email.addTextChangedListener(textWatcher)
        binding.password.addTextChangedListener(textWatcher)
    }

    override fun onDestroy() {
        super.onDestroy()
        networkManager.removeObservers(this@LoginActivity)
    }

    //pesan ketika terjadi kesalahan
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

    //pesan ketika sukses
    private fun showSuccessMessage(message: String){
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("Berhasil")
        alertDialog.setMessage(message)
        alertDialog.setPositiveButton("OK"){dialogInterface, which ->
            dialogInterface.dismiss()
        }
        alertDialog.setCancelable(false)
        alertDialog.show()
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
}