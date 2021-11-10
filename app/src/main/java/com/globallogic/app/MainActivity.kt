package com.globallogic.app

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.globallogic.app.Constants.EMPTY_STRING
import com.globallogic.app.adapter.PokemonViewpagerAdapter
import com.globallogic.app.adapter.RvMoveListAdapter
import com.globallogic.app.databinding.ActivityMainBinding
import com.globallogic.app.network.RetrofitClient


class MainActivity : AppCompatActivity() {

    private val TAG = javaClass.name
    private lateinit var mBinding:ActivityMainBinding
    private lateinit var mViewModel:PokemonViewModel
    private val pagerAdapter: PokemonViewpagerAdapter by lazy {
        PokemonViewpagerAdapter()
    }

    private val adapter : RvMoveListAdapter by lazy {
        RvMoveListAdapter()
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.home_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_refresh->{
                mViewModel.getGetRandomData()
            }
        }
        return super.onOptionsItemSelected(item)
    }


    private fun initBinding() {
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        mBinding.rvMoves.adapter = adapter
        setContentView(mBinding.root)
        val apiService = RetrofitClient.apiService
        mViewModel = ViewModelProvider(this,
            ViewModelFactory(PokemonRepository(apiService)))[PokemonViewModel::class.java]
        mBinding.lifecycleOwner = this
        mBinding.setVariable(BR.model, mViewModel)
        mBinding.executePendingBindings()
    }

    private fun setViewPagerAdapter() {
        mBinding.viewpager.adapter = pagerAdapter
    }

    private fun addPokemonImageObserver() {
        mViewModel.getPokemonImages().observe(this, {
            pagerAdapter.setImageData(it)
        })
    }

    private fun addSwipeListener() {
      /*  mBinding.swipeLayout.setOnRefreshListener {
            fetchRandomPokemonData()
        }*/
    }

    private fun fetchRandomPokemonData() {
        if(Utilities.checkForInternet(this)) {
            mViewModel.getGetRandomData()
        } else {
            clearData()
            Toast.makeText(this,resources.getString(R.string.msg_no_network),Toast.LENGTH_LONG).show()
        }
    }

    private fun clearData() {
        //mBinding.swipeLayout.isRefreshing = false
        pagerAdapter.clearData()
        mBinding.tvPokemonName.text = EMPTY_STRING
    }

    private fun addPokemonDataObserver() {
        mViewModel.pokemonLiveData.observe(this, {
            mBinding.apply {
                adapter.setMoves(it.moves)
                setVariable(BR.pokemonData, it)
                executePendingBindings()
                viewpager.setCurrentItem(0,true)
                //swipeLayout.isRefreshing = false
            }
        })
    }
}
