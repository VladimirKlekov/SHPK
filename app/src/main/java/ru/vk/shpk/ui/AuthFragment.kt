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
import ru.vk.shpk.databinding.FragmentAuthBinding
import ru.vk.shpk.dto.SingInUser
import ru.vk.shpk.feedmodel.FeedModelState
import ru.vk.shpk.interfaces.CheckConnection
import ru.vk.shpk.viewmodels.AuthViewModel


class AuthFragment : Fragment(), CheckConnection {
    private var _binding: FragmentAuthBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for FragmentLoginBinding must not be null")
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAuthBinding.inflate(inflater, container, false)
        checkConnectionInternet()
        stateSingInServer()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.logInButton.setOnClickListener {
            val login = binding.loginTextView.text.toString()
            val password = binding.passwordTextView.text.toString()
            val user = SingInUser(
                login = login,
                password = password
            )
            viewModel.singIn(user)
        }

    }

    private fun stateSingInServer() {
        viewModel.state.observe(viewLifecycleOwner) { state ->

            if (state is FeedModelState.Loading) {
                binding.authGroup.visibility = View.GONE
                binding.progressBar.visibility = View.VISIBLE
                binding.progressBar.hideKeyboard()
            }

            if (state is FeedModelState.Idle) {
                findNavController().navigate(R.id.action_authFragment_to_mainFragment)
            }

            if (state is FeedModelState.Error) {
                binding.authGroup.visibility = View.VISIBLE
                binding.progressBar.visibility = View.GONE
                Toast.makeText(requireContext(), "Отказано в доступе", Toast.LENGTH_SHORT).show()
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
                binding.authGroup.visibility = View.VISIBLE
            } else {
                binding.authGroup.visibility = View.GONE
                binding.containerErrorInternet.errorInternetLayout.visibility = View.VISIBLE

            }
        })
    }
}