package com.sample.vkoelassign.ui.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.sample.vkoelassign.databinding.FragmentXListBinding
import com.sample.vkoelassign.network.StatusCode
import com.sample.vkoelassign.ui.model.MovieResponseModel
import com.sample.vkoelassign.ui.view.adapter.MovieAdapter
import com.sample.vkoelassign.ui.viewmodel.XViewModel
import com.sample.vkoelassign.utility.Utils
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.sample.vkoelassign.ui.model.Result

class XListFragment : Fragment() {
    private lateinit var binding: FragmentXListBinding
    private lateinit var viewModel: XViewModel
    private var movieList = ArrayList<Result>()
    private lateinit var movieAdapter: MovieAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = ViewModelProvider(this).get(XViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentXListBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun init() {
        initializeAdapter()
        viewModel.getMovieList()
        observeMovieDataResponse()
    }

    private fun initializeAdapter() {
        with(binding.recyclerView) {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            movieAdapter = MovieAdapter(
                movieList
            )
            adapter = movieAdapter
        }
    }

    private fun observeMovieDataResponse() {
        viewModel.movieResponse.removeObservers(this)
        viewModel.movieResponse.observe(this, Observer { success ->
            when (success?.statusCode) {
                StatusCode.NETWORK_ERROR -> {
                    //Utils.showToast(this, getString(R.string.text_no_internet_available))
                }
                StatusCode.START -> {

                    binding.progressBar.visibility = View.VISIBLE
                    Utils.hideKeyPad(requireActivity())

                }
                StatusCode.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    Utils.hideKeyPad(requireActivity())
                    setUpRecyclerViewData(success.results!!)

                }
                StatusCode.ERROR -> {
                    //toastShort(success.msg)

                }
            }
            if (viewModel.movieResponse.value != null)
                viewModel.movieResponse.value = null
        })
    }

    /**
     * Method to setUp Recyclerview to fill data into it
     */
    private fun setUpRecyclerViewData(data: ArrayList<Result>) {
        if (data.size > 0) {
            movieList.clear()
            movieList.addAll(data)
            movieAdapter.notifyDataSetChanged()
        } else {
        }
    }

}