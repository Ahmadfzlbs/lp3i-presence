package com.example.lp3icheck_in.custom

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.lp3icheck_in.R
import com.example.lp3icheck_in.databinding.BottomSheetLayoutBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CustomBottomSheetFragment : BottomSheetDialogFragment() {
    private var _binding: BottomSheetLayoutBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetLayoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set your lottie animation, message, and button click listener here
        binding.animationView.setAnimation(R.raw.no_internet)
        binding.animationView.playAnimation()
        binding.buttonOK.setBackgroundColor(Color.rgb(71, 98, 250))
        binding.textViewMessage.text = "Eh, Sinyal nya hilang ya?"
        binding.textViewMessage1.text = "Pastikan koneksi internet kamu aktif ya..."
        binding.buttonOK.setOnClickListener {
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
