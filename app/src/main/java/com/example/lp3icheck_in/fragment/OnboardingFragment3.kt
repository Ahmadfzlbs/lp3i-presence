package com.example.lp3icheck_in.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.lp3icheck_in.activity.LoginActivity
import com.example.lp3icheck_in.databinding.FragmentOnboarding3Binding
import com.thekhaeng.pushdownanim.PushDownAnim

class OnboardingFragment3 : Fragment() {
    private var _binding: FragmentOnboarding3Binding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOnboarding3Binding.inflate(inflater, container, false)

        PushDownAnim.setPushDownAnimTo(binding.btnMasuk).setOnClickListener {
            val i = Intent(requireContext(), LoginActivity::class.java)
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(i)
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
