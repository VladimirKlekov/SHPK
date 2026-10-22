package ru.vk.shpk.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import ru.vk.shpk.R
import ru.vk.shpk.checkinternet.NetworkConnectionLiveData
import ru.vk.shpk.databinding.FragmentMainBinding
import ru.vk.shpk.interfaces.CheckConnection
import ru.vk.shpk.viewmodels.MainViewModel
import kotlin.getValue

class MainFragment : Fragment(), CheckConnection {
    private var _binding: FragmentMainBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for FragmentMainBinding must not be null")
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        binding.containerTopMenu.adminMenuButton.visibility = View.GONE
        checkConnectionInternet()
        checkToken()
        checkStatus()
        adminMenuTopAppBar()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.containerTopMenu.exitAppButton.setOnClickListener {
            viewModel.exitApp()
            findNavController().navigate(R.id.action_mainFragment_to_authFragment)
        }

    }

    private fun adminMenuTopAppBar() {
        val adminMenu = PopupMenu(
            context,
            binding.containerTopMenu.adminMenuButton
        )
        adminMenu.inflate(R.menu.admin_menu)
        binding.containerTopMenu.adminMenuButton.setOnClickListener {
            adminMenu.show()
        }

        adminMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.addUser -> {
                    findNavController().navigate(R.id.action_mainFragment_to_addUserFragment)
                    true
                }
                R.id.listUsers -> {
                    findNavController().navigate(R.id.action_mainFragment_to_listUsersFragment)
                    true
                }
                R.id.addSupplier->{
//                    findNavController().navigate(R.id.action_mainFragment_to_addSupplierFragment)
                    true
                }

                R.id.addProduct->{
//                    findNavController().navigate(R.id.action_mainFragment_to_addProductFragment)
                    true
                }

                else -> false
            }
        }
    }
    private fun checkStatus(){
        viewModel.checkStatus().observe(viewLifecycleOwner) {
            when(it){
                "admin"->{binding.containerTopMenu.adminMenuButton.visibility = View.VISIBLE}
            }
        }
    }
    private fun checkToken() {
        viewModel.checkToken().observe(viewLifecycleOwner) {
            if (it == false)
                findNavController().navigate(R.id.action_mainFragment_to_authFragment)
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

    override fun serverResponse() {
        viewModel.serverResponse().observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), "${it}", Toast.LENGTH_SHORT).show()
        }
    }

}