package com.example.dictionary.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.base.BaseActivity
import com.example.core.description.DescriptionActivity
import com.example.dictionary.R
import com.example.dictionary.databinding.ActivityMainBinding
import com.example.dictionary.main.adapter.MainAdapter
import com.example.history.ui.HistoryActivity
import com.example.history.ui.SearchHistoryDialogFragment
import com.example.model.AppState
import com.example.model.userdata.DataModel
import com.example.utils.viewById
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.koin.core.qualifier.named
import org.koin.java.KoinJavaComponent.getKoin

class MainActivity : BaseActivity<AppState, MainInteractor>() {
    private lateinit var binding: ActivityMainBinding
    override lateinit var model: MainViewModel
    private val mainActivityRecyclerView by viewById<RecyclerView>(R.id.main_activity_recyclerview)
    private val searchFAB by viewById<FloatingActionButton>(R.id.search_fab)
    private val scope = getKoin().createScope("activityScope", named<MainActivity>())
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
                com.example.utils.convertMeaningsToString(data.meanings!!),
                data.meanings!![0].imageUrl
            )
        )
    }

    private fun initViewModel() {
        if (binding.mainActivityRecyclerview.adapter != null)
            throw IllegalStateException("Initialize ViewModel first")

        val viewModel: MainViewModel by scope.inject()
        model = viewModel
        model.subscribe().observe(this@MainActivity,  { renderData(it) })
    }

    private fun initViews() {
        searchFAB.setOnClickListener(fabClickListener)
        mainActivityRecyclerView.layoutManager = LinearLayoutManager(applicationContext)
        mainActivityRecyclerView.adapter = adapter
    }

    override fun setDataToAdapter(data: List<DataModel>) {
        adapter.setData(data)
    }

    companion object {
        private const val BOTTOM_SHEET_FRAGMENT_DIALOG_TAG =
            "Bottom_sheet_fragment_dialog_tag"
    }
}