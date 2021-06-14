package com.majidebrahimi.home.views

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import coil.load
import coil.transform.CircleCropTransformation
import com.majidebrahimi.domain.utils.Resource
import com.majidebrahimi.model.User


    @BindingAdapter("bind:showWhenLoading")
    fun <T> showWhenLoading(view: SwipeRefreshLayout, resource: Resource<T>?) {
        if (resource != null) view.isRefreshing = resource.status == Resource.Status.LOADING
    }

    @BindingAdapter("bind:items")
    fun setItems(recyclerView: RecyclerView, resource: Resource<List<User>>?) {
        with(recyclerView.adapter as HomeAdapter) {
            resource?.data?.let { updateData(it) }
        }
    }

    @BindingAdapter("bind:imageUrl")
    fun loadImage(view: ImageView, url: String?) {
        url?.let {
            view.load(it){
                transformations(CircleCropTransformation())
            }
        }
    }

    @BindingAdapter("bind:showWhenEmptyList")
    fun showMessageErrorWhenEmptyList(view: View, resource: Resource<List<User>>?) {
        if (resource != null) {
            view.visibility = if (resource.status == Resource.Status.ERROR
                && resource.data != null
                && resource.data!!.isEmpty()
            ) View.VISIBLE else View.GONE
        }
    }