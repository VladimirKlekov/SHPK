package ru.vk.shpk.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import ru.vk.shpk.R
import ru.vk.shpk.databinding.FragmentListUsersBinding
import ru.vk.shpk.databinding.FragmentMainBinding




class ListUsersFragment : Fragment() {
    private var _binding: FragmentListUsersBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for FragmentListUsersBinding must not be null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
    _binding = FragmentListUsersBinding.inflate(inflater, container, false)
        binding.button.setOnClickListener {
            findNavController().navigate(R.id.action_listUsersFragment_to_mainFragment)
        }

        return  binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}