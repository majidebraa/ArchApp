package com.majidebrahimi.detail

import android.os.Bundle
import android.transition.ChangeBounds
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.majidebrahimi.common.base.BaseFragment
import com.majidebrahimi.common.base.BaseViewModel
import com.majidebrahimi.detail.databinding.FragmentDetailImageBinding
import com.majidebrahimi.model.User
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


/**
 * A simple [BaseFragment] subclass
 * that will print the [User] profile picture in full screen
 */
class DetailImageFragment : BaseFragment() {

    // FOR DATA
    private lateinit var binding: FragmentDetailImageBinding
    private val args: DetailImageFragmentArgs by navArgs()
    private val viewModel: DetailImageViewModel by viewModel{ parametersOf(args.imageUrl)}

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        configureTransition()
        binding = FragmentDetailImageBinding.inflate(inflater, container, false)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun getViewModel(): BaseViewModel = viewModel

    // ---

    private fun configureTransition() {
        val transition = ChangeBounds().apply { duration = 300 }
        sharedElementEnterTransition = transition
        sharedElementReturnTransition = transition
    }
}
