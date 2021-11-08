package com.example.dictionary.view.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dictionary.R
import com.example.dictionary.view.base.BaseActivity
import com.example.dictionary.databinding.ActivityMainBinding
import com.example.dictionary.view.main.adapter.MainAdapter
import com.example.dictionary.model.data.AppState
import com.example.dictionary.model.data.DataModel
import com.example.dictionary.utils.convertMeaningsToString
import com.example.dictionary.utils.isOnline
import com.example.dictionary.utils.*
import com.example.dictionary.view.description.DescriptionActivity
import com.example.dictionary.view.history.HistoryActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<AppState, MainInteractor>() {
    private lateinit var binding: ActivityMainBinding
    override lateinit var model: MainViewModel
    private val adapter: MainAdapter by lazy { MainAdapter(onListItemClickListener) }

    private val fabClickListener: View.OnClickListener = View.OnClickListener {
        val searchDialogFragment = SearchDialogFragment.newInstance()
        searchDialogFragment.setOnSearchClickListener(onSearchClickListener)
        searchDialogFragment.show(supportFragmentManager, BOTTOM_SHEET_FRAGMENT_DIALOG_TAG)
    }

    private val onListItemClickListener: MainAdapter.OnListItemClickListener by lazy {
        object : MainAdapter.OnListItemClickListener {
            override fun onItemClick(data: DataModel) = onItemClickListener(data)
        }
    }

    private val onSearchClickListener: SearchDialogFragment.OnSearchClickListener =
        object : SearchDialogFragment.OnSearchClickListener {
            override fun onClick(searchWord: String) {
                isNetworkAvailable = isOnline(applicationContext)
                if (isNetworkAvailable) model.getData(searchWord, isNetworkAvailable)
                else showNoInternetConnectionDialog()
            }
        }

    private val onSearchHistoryClickListener: SearchHistoryDialogFragment.OnSearchHistoryClickListener =
        object : SearchHistoryDialogFragment.OnSearchHistoryClickListener {
            override fun onClick(searchWord: String) {
                //model.getData(searchWord, false)
                //model.getWord(searchWord)
                CoroutineScope(Dispatchers.Main + SupervisorJob())
                    .launch { onItemClickListener(model.getWord(searchWord)) }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViewModel()
        initViews()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.history_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_history -> {
                startActivity(Intent(this, HistoryActivity::class.java))
                true
            }
            R.id.menu_search_history -> {
                val searchHistoryDialogFragment = SearchHistoryDialogFragment.newInstance()
                searchHistoryDialogFragment.setOnSearchHistoryClickListener(
                    onSearchHistoryClickListener
                )
                searchHistoryDialogFragment.show(
                    supportFragmentManager,
                    BOTTOM_SHEET_FRAGMENT_DIALOG_TAG
                )
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun onItemClickListener(data: DataModel) {
        startActivity(
            DescriptionActivity.getIntent(
                this@MainActivity,
                data.text!!,
                convertMeaningsToString(data.meanings!!),
                data.meanings[0].imageUrl
            )
        )
    }

    private fun initViewModel() {
        if (binding.mainActivityRecyclerview.adapter != null)
            throw IllegalStateException("Initialize ViewModel first")

        val viewModel: MainViewModel by viewModel()
        model = viewModel
        model.subscribe().observe(this@MainActivity,  { renderData(it) })
    }

    private fun initViews() {
        binding.searchFab.setOnClickListener(fabClickListener)
        binding.mainActivityRecyclerview.layoutManager = LinearLayoutManager(applicationContext)
        binding.mainActivityRecyclerview.adapter = adapter
    }

    override fun setDataToAdapter(data: List<DataModel>) {
        adapter.setData(data)
    }

    companion object {
        private const val BOTTOM_SHEET_FRAGMENT_DIALOG_TAG =
            "Bottom_sheet_fragment_dialog_tag"
    }
}