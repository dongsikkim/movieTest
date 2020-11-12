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
import kotlinx.android.synthetic.main.fragment_people.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class PeopleFragment : Fragment() {
    private val peopleViewModel: PeopleViewModel by viewModel()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        FragmentPeopleBinding.inflate(inflater).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = peopleViewModel
        }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(PeopleAdapter(onClick)) {
            people_recycler.adapter = this
            peopleViewModel.list.observe(viewLifecycleOwner, Observer(this::submitList))
        }
    }

    private val onClick: ((Pair<AppCompatImageView, People>) -> Unit)? = { pair ->
        val extras = androidx.navigation.fragment.FragmentNavigatorExtras(pair.first to pair.first.transitionName)
        val action = PeopleFragmentDirections.actionPeopleFragmentToPeopleDetailFragment(pair.second.toPeopleDetail())
        findNavController().navigate(action, extras)
    }
}

