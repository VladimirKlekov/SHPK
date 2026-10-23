package ru.vk.shpk.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import ru.vk.shpk.R
import ru.vk.shpk.checkinternet.NetworkConnectionLiveData
import ru.vk.shpk.databinding.FragmentAddUserBinding
import ru.vk.shpk.dto.NewUser
import ru.vk.shpk.feedmodel.FeedModelState
import ru.vk.shpk.interfaces.CheckConnection
import ru.vk.shpk.viewmodels.AddUserViewModel
import java.util.UUID
import kotlin.getValue

class AddUserFragment : Fragment(), CheckConnection {
    private var _binding: FragmentAddUserBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for FragmentAddUserBinding must not be null")
    private val viewModel: AddUserViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddUserBinding.inflate(inflater, container, false)
        stateSingInServer()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addUserButton.setOnClickListener {
            if (binding.loginTextView.text.isNotEmpty() &&
                binding.passwordTextView.text.isNotEmpty() &&
                binding.userStatusTextView.text.isNotEmpty() &&
                binding.userNameTextView.text.isNotEmpty()
            ) {
                val id = UUID.randomUUID().hashCode()
                val login = binding.loginTextView.text.toString()
                val password = binding.passwordTextView.text.toString()
                val userStatus = binding.userStatusTextView.text.toString()
                val userName = binding.userNameTextView.text.toString()

                val newUser = NewUser(
                    id=id,
                    login = login,
                    password = password,
                    username = userName,
                    status = userStatus
                )

                viewModel.addUserOnServer(newUser)

            } else {
                Toast.makeText(requireContext(), R.string.fill_fields, Toast.LENGTH_SHORT).show()
            }
        }
        binding.cancelButton.setOnClickListener {
            findNavController().navigate(R.id.action_addUserFragment_to_mainFragment)
        }

    }

    private fun stateSingInServer() {
        viewModel.state.observe(viewLifecycleOwner) { state ->

            if (state is FeedModelState.Loading) {
                binding.bindGroup.visibility = View.GONE
                binding.progressBar.visibility = View.VISIBLE
                binding.progressBar.hideKeyboard()
            }

            if (state is FeedModelState.Idle) {
                findNavController().navigate(R.id.action_addUserFragment_to_mainFragment)
            }

            if (state is FeedModelState.Error) {
                binding.bindGroup.visibility = View.VISIBLE
                binding.progressBar.visibility = View.GONE
            }
        }
    }
    private fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
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
