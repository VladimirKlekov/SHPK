package ru.vk.shpk.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.vk.shpk.databinding.FragmentMainBinding

class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for FragmentMainBinding must not be null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}