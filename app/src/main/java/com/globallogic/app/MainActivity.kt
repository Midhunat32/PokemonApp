package com.globallogic.app

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.globallogic.app.Constants.EMPTY_STRING
import com.globallogic.app.adapter.PokemonViewpagerAdapter
import com.globallogic.app.adapter.RvMoveListAdapter
import com.globallogic.app.adapter.RvStatisticsAdapter
import com.globallogic.app.databinding.ActivityMainBinding
import com.globallogic.app.network.RetrofitClient


class MainActivity : AppCompatActivity(), View.OnClickListener {

    private val TAG = javaClass.name
    private lateinit var mBinding: ActivityMainBinding
    private lateinit var mViewModel: PokemonViewModel
    private val pagerAdapter: PokemonViewpagerAdapter by lazy {
        PokemonViewpagerAdapter()
    }
    private val mMovesAdapter: RvMoveListAdapter by lazy {
        RvMoveListAdapter()
    }
    private val mStatsAdapter: RvStatisticsAdapter by lazy {
        RvStatisticsAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBinding()
        addPokemonDataObserver()
        fetchRandomPokemonData()
        addPokemonImageObserver()
        setViewPagerAdapter()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.home_menu, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_refresh -> {
                fetchRandomPokemonData()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * Method to initialize binding
     */
    private fun initBinding() {
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        val apiService = RetrofitClient.apiService
        mViewModel = ViewModelProvider(
            this,
            ViewModelFactory(PokemonRepository(apiService))
        )[PokemonViewModel::class.java]
        mBinding.apply {
            mBinding.lifecycleOwner = this@MainActivity
            rvMoves.adapter = mMovesAdapter
            rvStatistics.adapter = mStatsAdapter
            rvMoves.layoutManager = LinearLayoutManager(
                this@MainActivity,
                LinearLayoutManager.HORIZONTAL, false
            )
            rvStatistics.layoutManager = LinearLayoutManager(
                this@MainActivity,
                LinearLayoutManager.HORIZONTAL, false
            )
            ivArrowLeft.setOnClickListener(this@MainActivity)
            ivArrowRight.setOnClickListener(this@MainActivity)
            setVariable(BR.model, mViewModel)
            executePendingBindings()

        }
    }

    /**
     * Method to set viewpager adapter
     */
    private fun setViewPagerAdapter() {
        mBinding.viewpager.adapter = pagerAdapter
    }

    /**
     * Method to observe images of pokemon and set it to viewpager adapter
     */
    private fun addPokemonImageObserver() {
        mViewModel.getPokemonImages().observe(this, {
            pagerAdapter.setImageData(it)
        })
    }


    /**
     * Method to fetch random pokemon data
     */
    private fun fetchRandomPokemonData() {
        if (Utilities.checkForInternet(this)) {
            mViewModel.getGetRandomData()
        } else {
            clearData()
            Toast.makeText(this, resources.getString(R.string.msg_no_network), Toast.LENGTH_LONG)
                .show()
        }
    }

    /**
     * Method to clear pokemon name textview
     */
    private fun clearData() {
        mBinding.tvPokemonName.text = EMPTY_STRING
    }

    /**
     * Method to observe pokemon data from server
     */
    private fun addPokemonDataObserver() {
        mViewModel.pokemonLiveData.observe(this, {
            mBinding.rvMoves
            mBinding.apply {
                mMovesAdapter.setMoves(it.moves)
                mStatsAdapter.setStatisticsData(it.stats)
                rvMoves.scrollToPosition(0)
                rvStatistics.scrollToPosition(0)
                setVariable(BR.pokemonData, it)
                executePendingBindings()
                viewpager.setCurrentItem(0, true)
            }
        })
    }

    /**
     * Called when a view has been clicked.
     * @param view View of clicked widget
     */
    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.iv_arrow_left -> moveLeft()
            R.id.iv_arrow_right -> moveRight()
        }
    }

    /**
     * Method to swipe right in viewpager.
     */
    private fun moveRight() {
        mBinding.viewpager.currentItem = 1
    }

    /**
     * Method to swipe left in viewpager.
     */
    private fun moveLeft() {
        mBinding.viewpager.currentItem = 0
    }
}
