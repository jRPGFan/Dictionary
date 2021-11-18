package com.example.history.ui

import android.os.Bundle
import com.example.base.BaseActivity
import com.example.history.databinding.ActivityHistoryBinding
import com.example.history.interactor.HistoryInteractor
import com.example.model.AppState
import com.example.model.userdata.DataModel
import org.koin.core.qualifier.named
import org.koin.java.KoinJavaComponent.getKoin

class HistoryActivity : BaseActivity<AppState, HistoryInteractor>() {
    private lateinit var binding: ActivityHistoryBinding
    override lateinit var model: HistoryViewModel
    private val scope = getKoin().createScope("historyScope", named<HistoryActivity>())
    private val adapter: HistoryAdapter by lazy { HistoryAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViewModel()
        initViews()
    }

    override fun onResume() {
        super.onResume()
        model.getData("", false)
    }

    override fun setDataToAdapter(data: List<DataModel>) {
        adapter.setData(data)
    }

    private fun initViewModel() {
        if (binding.historyRecyclerview.adapter != null) {
            throw IllegalStateException("The ViewModel should be initialized first")
        }
        val viewModel: HistoryViewModel by scope.inject()
        model = viewModel
        model.subscribe().observe(this@HistoryActivity, { renderData(it) })
    }

    private fun initViews() {
        binding.historyRecyclerview.adapter = adapter
    }
}