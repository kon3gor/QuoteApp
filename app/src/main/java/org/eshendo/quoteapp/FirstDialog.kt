package org.eshendo.quoteapp

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import org.eshendo.quoteapp.databinding.FirstTimeDialogBinding

class FirstDialog(
    private val mode: Boolean,
) : DialogFragment() {

    private lateinit var binding: FirstTimeDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FirstTimeDialogBinding.inflate(inflater, container, false)

        initUI()
        attachListeners()

        return binding.root
    }

    private fun initUI(){
        val bgColor: Int?
        val textColor: Int?
        val btnColor: Int?

        if (mode){
            bgColor   = ContextCompat.getColor(context!!, R.color.bgLightColor)
            textColor = ContextCompat.getColor(context!!, R.color.textLightColor)
            btnColor  = ContextCompat.getColor(context!!, R.color.btnLightColor)
        }else{
            bgColor   = ContextCompat.getColor(context!!, R.color.bgDarkColor)
            textColor = ContextCompat.getColor(context!!, R.color.textDarkColor)
            btnColor  = ContextCompat.getColor(context!!, R.color.btnDarkColor)
        }

        binding.root.backgroundTintList = ColorStateList.valueOf(bgColor)
        binding.text.backgroundTintList = ColorStateList.valueOf(textColor)
        binding.btn.backgroundTintList = ColorStateList.valueOf(btnColor)
    }

    private fun attachListeners(){
        binding.btn.setOnClickListener {
            this.dismiss()
        }
    }


}