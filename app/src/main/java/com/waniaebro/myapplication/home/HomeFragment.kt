package com.waniaebro.myapplication.home

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.waniaebro.core.data.source.remote.retrofit.ResultResponse
import com.waniaebro.core.ui.FilmAdapter
import com.waniaebro.myapplication.databinding.FragmentHomeBinding
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding
    private val homeViewModel: HomeViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = FilmAdapter { view1, id ->
            val toDetailActivity =
                HomeFragmentDirections.actionHomeFragmentToDetailActivity()
            toDetailActivity.id = id
            view1.findNavController().navigate(toDetailActivity)
        }
        homeViewModel.searchResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                ResultResponse.Empty -> {
                    binding?.let {
                        it.notFoundTv.text = getString(com.waniaebro.core.R.string.search_not_found)
                        it.notFoundTv.visibility = View.VISIBLE
                        it.progressBar.visibility = View.GONE
                    }
                }

                is ResultResponse.Error -> {
                    binding?.let {
                        it.progressBar.visibility = View.GONE
                        it.notFoundTv.visibility = View.GONE
                    }
                    Toast.makeText(context, result.message, Toast.LENGTH_LONG).show()
                }

                ResultResponse.Loading -> {
                    binding?.let {
                        it.progressBar.visibility = View.VISIBLE
                        it.notFoundTv.visibility = View.GONE
                    }
                }

                is ResultResponse.Success -> {
                    binding?.let {
                        it.progressBar.visibility = View.GONE
                        it.notFoundTv.visibility = View.GONE
                        adapter.submitList(result.data)
                    }
                    Log.e("search", result.data.toString())
                }
            }

        }
        binding?.let {
            it.rvContainer.adapter = adapter
            it.favoriteList.setOnClickListener {
                val uri = Uri.parse("movielist://favorite")
                startActivity(Intent(Intent.ACTION_VIEW, uri))
            }
            it.searchEd.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    lifecycleScope.launch {
                        homeViewModel.titleSearch.value = s.toString()
                    }
                }

                override fun afterTextChanged(s: Editable?) {

                }

            })
            it.searchEd.setOnEditorActionListener { v, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    val inputMethodManager =
                        requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(v.windowToken, 0)
                    true
                } else {
                    false
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}