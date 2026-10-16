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
import androidx.navigation.fragment.findNavController
import ru.vk.shpk.R
import ru.vk.shpk.databinding.FragmentAuthBinding
import ru.vk.shpk.dto.User
import ru.vk.shpk.feedmodel.FeedModelState
import ru.vk.shpk.viewmodels.AuthViewModel


class AuthFragment : Fragment() {
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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

        binding.logInButton.setOnClickListener {
            val login = binding.loginTextView.text.toString()
            val password = binding.passwordTextView.text.toString()
            val user = User(
                login = login,
                password = password
            )
            viewModel.singIn(user)
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
}