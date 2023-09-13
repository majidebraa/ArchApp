package com.majidebrahimi.home.views

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.majidebrahimi.home.HomeViewModel
import com.majidebrahimi.home.databinding.ItemHomeBinding
import com.majidebrahimi.model.User

class HomeViewHolder(parent: View): RecyclerView.ViewHolder(parent) {

    private val binding = ItemHomeBinding.bind(parent)

    fun bindTo(user: User, viewModel: HomeViewModel) {
        binding.user = user
        binding.viewmodel = viewModel
    }
}