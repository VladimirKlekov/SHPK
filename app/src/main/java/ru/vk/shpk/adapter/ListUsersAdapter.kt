package ru.vk.shpk.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.vk.shpk.databinding.ItemUsersListBinding
import ru.vk.shpk.dto.UserBd

class ListUsersAdapter (private val onItemClick: (String) -> Unit) :
    ListAdapter<UserBd, ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemUsersListBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        return ViewHolder(
            binding = binding,
            onItemClick = onItemClick
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
    }
}

class ViewHolder(
    private val binding: ItemUsersListBinding,
    private val onItemClick: (String) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(user: UserBd) {
        binding.apply {
            loginTextView.text = user.login
            passwordTextView.text = user.password
            statusTextView.text = user.status
            userNameTextView.text = user.username
        }
    }
}

class DiffCallback : DiffUtil.ItemCallback<UserBd>() {
    override fun areItemsTheSame(oldItem: UserBd, newItem: UserBd): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: UserBd, newItem: UserBd): Boolean {
        return oldItem == newItem
    }
}