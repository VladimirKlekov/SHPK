package ru.vk.shpk.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import ru.vk.shpk.databinding.FragmentAuthBinding
import ru.vk.shpk.dto.User
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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}