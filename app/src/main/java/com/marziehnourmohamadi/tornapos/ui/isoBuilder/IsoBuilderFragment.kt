package com.marziehnourmohamadi.tornapos.ui.isoBuilder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.marziehnourmohamadi.tornapos.databinding.FragmentIsoBuilderBinding


class IsoBuilderFragment : Fragment() {

    private var _binding: FragmentIsoBuilderBinding? = null
    private val binding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentIsoBuilderBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}