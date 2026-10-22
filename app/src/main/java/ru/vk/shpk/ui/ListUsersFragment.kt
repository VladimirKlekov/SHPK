package ru.vk.shpk.ui

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.vk.shpk.R
import ru.vk.shpk.adapter.ListUsersAdapter
import ru.vk.shpk.databinding.FragmentListUsersBinding
import ru.vk.shpk.viewmodels.ListUsersViewModel
import kotlin.getValue


class ListUsersFragment : Fragment() {
    private var _binding: FragmentListUsersBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for FragmentListUsersBinding must not be null")
    private val viewModel: ListUsersViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListUsersBinding.inflate(inflater, container, false)
        binding.backButton.setOnClickListener {
            findNavController().navigate(R.id.action_listUsersFragment_to_mainFragment)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val adapter = ListUsersAdapter(onItemClick = ::deleteUserOnServer)
        binding.listUsersRecyclerView.adapter = adapter
        viewModel.getListUsersOnServer().observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    private fun deleteUserOnServer(userId: String) {
        val dialog = AlertDialog.Builder(requireContext())
        dialog.setTitle("Подтверждение")
        dialog.setMessage("Вы уверены, что удалить пользователя?")
        dialog.setPositiveButton("Да") { _, _ ->
//            viewModel.deleteUserFirebase(userId)
        }
        dialog.setNegativeButton("Нет", null).show()

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}