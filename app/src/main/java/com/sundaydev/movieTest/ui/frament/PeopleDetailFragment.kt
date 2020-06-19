package com.sundaydev.movieTest.ui.frament

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.transition.ArcMotion
import com.google.android.material.transition.MaterialContainerTransform
import com.google.android.material.transition.MaterialContainerTransform.FIT_MODE_HEIGHT
import com.sundaydev.movieTest.R
import com.sundaydev.movieTest.databinding.FragmentPeopleDetailBinding
import com.sundaydev.movieTest.viewmodel.PeopleDetailViewModel
import kotlinx.android.synthetic.main.fragment_people_detail.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class PeopleDetailFragment : Fragment() {
    private val args: PeopleDetailFragmentArgs by navArgs()
    val viewModel: PeopleDetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val receiveData = args.peopleData

        receiveData?.run {
            viewModel.detailData.value = this
            viewModel.loadPeopleDetail(id)
        }
        val transform = MaterialContainerTransform()
        transform.setPathMotion(ArcMotion())
        transform.fitMode = FIT_MODE_HEIGHT
        sharedElementEnterTransition = transform
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentPeopleDetailBinding = FragmentPeopleDetailBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.detailData.value?.let { detail ->
            ViewCompat.setTransitionName(people_image, getString(R.string.transition_image, detail.id))
        }
    }
}