package ru.vk.shpk.ui

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import ru.vk.shpk.R
import ru.vk.shpk.adapter.ListUsersAdapter
import ru.vk.shpk.checkinternet.NetworkConnectionLiveData
import ru.vk.shpk.databinding.FragmentListUsersBinding
import ru.vk.shpk.feedmodel.FeedModelState
import ru.vk.shpk.interfaces.CheckConnection
import ru.vk.shpk.viewmodels.ListUsersViewModel
import kotlin.getValue

class ListUsersFragment : Fragment(), CheckConnection {
    private var _binding: FragmentListUsersBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for FragmentListUsersBinding must not be null")
    private val viewModel: ListUsersViewModel by viewModels()
    private lateinit var adapter: ListUsersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListUsersBinding.inflate(inflater, container, false)
        stateOnServer()

        binding.backButton.setOnClickListener {
            findNavController().navigate(R.id.action_listUsersFragment_to_mainFragment)
        }

        return binding.root
    }

    private fun deleteUserOnServer(userId: Int) {
        val dialog = AlertDialog.Builder(requireContext())
        dialog.setTitle("Подтверждение")
        dialog.setMessage("Вы уверены, что хотите удалить пользователя?")
        dialog.setPositiveButton("Да") { _, _ ->
            viewModel.deleteUserOnServer(userId)
            viewModel.refreshListUsers()

        }
        dialog.setNegativeButton("Нет", null).show()
    }

    private fun stateOnServer() {
        viewModel.state.observe(viewLifecycleOwner) { state ->

            if (state is FeedModelState.Loading) {
                binding.listUsersRecyclerView.visibility = View.GONE
                binding.progressBar.visibility = View.VISIBLE
            }

            if (state is FeedModelState.Idle) {
                binding.listUsersRecyclerView.visibility = View.VISIBLE
                binding.progressBar.visibility = View.GONE

                adapter = ListUsersAdapter(onItemClick = ::deleteUserOnServer)
                binding.listUsersRecyclerView.adapter = adapter
                viewModel.listUsersBd.observe(viewLifecycleOwner) {
                    adapter.submitList(it)
                }
            }

            if(state is FeedModelState.Refresh){
                viewModel.listUsersBd.observe(viewLifecycleOwner) {
                    adapter.submitList(it)
                }
            }

            if (state is FeedModelState.Error) {
                binding.listUsersRecyclerView.visibility = View.VISIBLE
                binding.progressBar.visibility = View.GONE
                Toast.makeText(requireContext(), R.string.list_no_load, Toast.LENGTH_SHORT).show()
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun checkConnectionInternet() {
        val connectionLiveData = NetworkConnectionLiveData(requireContext())
        connectionLiveData.observe(requireContext() as LifecycleOwner, Observer { isConnected ->
            if (isConnected == true) {
                binding.containerErrorInternet.errorInternetLayout.visibility = View.GONE
            } else {
                binding.containerErrorInternet.errorInternetLayout.visibility = View.VISIBLE
            }
        })
    }
}