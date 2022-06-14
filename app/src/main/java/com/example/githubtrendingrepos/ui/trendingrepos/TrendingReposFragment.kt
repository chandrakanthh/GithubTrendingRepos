package com.example.githubtrendingrepos.ui.trendingrepos

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubtrendingrepos.R
import com.example.githubtrendingrepos.data.models.TrendingRepoModel
import com.example.githubtrendingrepos.data.services.BaseResponse
import com.example.githubtrendingrepos.databinding.FragmentTrendingReposBinding
import com.example.githubtrendingrepos.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TrendingReposFragment : BaseFragment(R.layout.fragment_trending_repos) {

    private lateinit var fragmentTrendingReposBinding : FragmentTrendingReposBinding
    private val trendingReposViewModel by viewModels<TrendingReposViewModel>()
    private var mrepolist : ArrayList<TrendingRepoModel> = arrayListOf()
    private val trendingReposAdapter : TrendingReposAdapter by lazy {
        TrendingReposAdapter(
            requireContext(),
            arrayListOf()
        )
    }

    private var selectedListItemPos : Int = 0

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList("repolist",mrepolist)
        outState.putInt("repoSelectedItemPos",selectedListItemPos)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentTrendingReposBinding = FragmentTrendingReposBinding.bind(view)
        initializeViews()
        setObservers()
        onClickListeners()
        if(savedInstanceState!=null){
            mrepolist = savedInstanceState.getParcelableArrayList<TrendingRepoModel>("repolist") as ArrayList<TrendingRepoModel>
            selectedListItemPos = savedInstanceState.getInt("repoSelectedItemPos")
            fragmentTrendingReposBinding.reposDataRv.scrollToPosition(selectedListItemPos)
            trendingReposAdapter.updateList(mrepolist,selectedListItemPos)
        }
    }

    private fun onClickListeners() {
        trendingReposAdapter.selectedItemListenerOnClick {
            selectedListItemPos = it
        }
    }

    private fun initializeViews() {
        val llm = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        fragmentTrendingReposBinding.reposDataRv.layoutManager = llm
        fragmentTrendingReposBinding.reposDataRv.adapter = trendingReposAdapter

        fragmentTrendingReposBinding.searchViewEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //beforeTextChanged
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //onTextChanged
            }

            override fun afterTextChanged(p0: Editable?) {
                trendingReposAdapter.filter.filter(p0.toString())
            }

        })

    }

    private fun setObservers() {
        trendingReposViewModel.trendingReposData.observe(viewLifecycleOwner){
            when(it){
                is BaseResponse.Loading -> {
                    Toast.makeText(context,"Loading",Toast.LENGTH_SHORT).show()
                    fragmentTrendingReposBinding.progressBar.show()
                }
                is BaseResponse.Success -> {
                   // Toast.makeText(context,"Success",Toast.LENGTH_SHORT).show()
                    fragmentTrendingReposBinding.progressBar.hide()
                    it.data?.let { res->
                        mrepolist = res
                        trendingReposAdapter.updateList(res, selectedListItemPos)
                    }
                }
                is BaseResponse.Error -> {
                    fragmentTrendingReposBinding.progressBar.hide()
                    Toast.makeText(context,it.message,Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}