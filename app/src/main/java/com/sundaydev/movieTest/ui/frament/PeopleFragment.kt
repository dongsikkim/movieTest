package com.sundaydev.movieTest.ui.frament

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.sundaydev.movieTest.data.People
import com.sundaydev.movieTest.databinding.FragmentPeopleBinding
import com.sundaydev.movieTest.ui.adapters.PeopleAdapter
import com.sundaydev.movieTest.viewmodel.PeopleViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class PeopleFragment : Fragment() {
    private val peopleViewModel: PeopleViewModel by viewModel()
    private val peopleAdapter: PeopleAdapter by lazy { PeopleAdapter(onClick) }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        FragmentPeopleBinding.inflate(inflater).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = peopleViewModel
            peopleRecycler.adapter = peopleAdapter
        }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        peopleViewModel.list.observe(viewLifecycleOwner, Observer { peopleAdapter.submitList(it) })
    }

    private val onClick: ((Pair<AppCompatImageView, People>) -> Unit)? = { pair ->
        val extras = androidx.navigation.fragment.FragmentNavigatorExtras(pair.first to pair.first.transitionName)
        val action = PeopleFragmentDirections.actionPeopleFragmentToPeopleDetailFragment(pair.second.toPeopleDetail())
        findNavController().navigate(action, extras)
    }
}

