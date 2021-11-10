package com.globallogic.app

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.globallogic.app.Constants.EMPTY_STRING
import com.globallogic.app.adapter.PokemonViewpagerAdapter
import com.globallogic.app.data.PokemonImage
import com.globallogic.app.databinding.ActivityMainBinding
import com.globallogic.app.network.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    private val TAG = javaClass.name
    private lateinit var mBinding:ActivityMainBinding
    private lateinit var mViewModel:PokemonViewModel
    private val pagerAdapter: PokemonViewpagerAdapter by lazy {
        PokemonViewpagerAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBinding()
        addPokemonDataObserver()
        fetchRandomPokemonData()
        addSwipeListener()
        addPokemonImageObserver()
        setViewPagerAdapter()

    }


    private fun initBinding() {
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        val apiService = RetrofitClient.apiService
        mViewModel = ViewModelProvider(this,
            ViewModelFactory(PokemonRepository(apiService))).get(PokemonViewModel::class.java)
        mBinding.lifecycleOwner = this
        mBinding.setVariable(BR.model, mViewModel)
        mBinding.executePendingBindings()
    }

    private fun setViewPagerAdapter() {
        mBinding.viewpager.adapter = pagerAdapter
    }

    private fun addPokemonImageObserver() {
        mViewModel.getPokemonImages().observe(this, {
            pagerAdapter.setImageData(it as ArrayList<PokemonImage>)
        })
    }

    private fun addSwipeListener() {
        mBinding.swipeLayout.setOnRefreshListener {
            fetchRandomPokemonData()
        }
    }

    private fun fetchRandomPokemonData() {
        if(Utilities.checkForInternet(this)) {
            GlobalScope.launch(Dispatchers.IO) {
                mViewModel.getGetRandomData()
            }
        } else {
            clearData()
            Toast.makeText(this,resources.getString(R.string.msg_no_network),Toast.LENGTH_LONG).show()
        }
    }

    private fun clearData() {
        mBinding.swipeLayout.isRefreshing = false
        pagerAdapter.clearData()
        mBinding.tvMoves.text = EMPTY_STRING
        mBinding.tvPokemonName.text = EMPTY_STRING
        mBinding.tvStatistics.text = EMPTY_STRING
    }

    private fun addPokemonDataObserver() {
        mViewModel.pokemonLiveData.observe(this, {
            mBinding.apply {
                setVariable(BR.pokemonData, it)
                executePendingBindings()
                viewpager.setCurrentItem(0,true)
                swipeLayout.isRefreshing = false
            }
        })
    }
}
