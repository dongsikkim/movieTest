package com.sundaydev.movieTest.ui.frament

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.paging.PagedListAdapter
import com.sundaydev.movieTest.BR
import com.sundaydev.movieTest.R
import com.sundaydev.movieTest.data.People
import com.sundaydev.movieTest.databinding.FragmentPeopleBinding
import com.sundaydev.movieTest.util.BindingViewHolder
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

class PeopleAdapter(private val onClick: ((Pair<AppCompatImageView, People>) -> Unit)?) :
    PagedListAdapter<People, BindingViewHolder>(People.diffPeopleUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder =
        BindingViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_people, parent, false))

    override fun onBindViewHolder(holder: BindingViewHolder, position: Int) {
        holder.binding.setVariable(BR.item, getItem(position))
        holder.binding.executePendingBindings()
        holder.binding.root.setOnClickListener {
            getItem(position)?.let {
                val imageView = holder.binding.root.findViewById<AppCompatImageView>(R.id.profile_image)
                onClick?.invoke(Pair(imageView, it))
            }
        }
    }
}