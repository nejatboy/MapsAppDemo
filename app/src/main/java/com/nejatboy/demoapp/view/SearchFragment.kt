package com.nejatboy.demoapp.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.nejatboy.demoapp.R
import com.nejatboy.demoapp.viewmodel.SearchViewModel


class SearchFragment : Fragment() {

    private lateinit var viewModel: SearchViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(SearchViewModel::class.java)

        observeLiveData()

        viewModel.runData()
    }


    private fun observeLiveData() {
        viewModel.comments.observe(viewLifecycleOwner, Observer {
            for (comment in it) {
                println(comment.body)
            }
        })
    }
}