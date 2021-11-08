package com.example.dictionary.view.main

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dictionary.databinding.SearchHistoryDialogFragmentBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlin.coroutines.CoroutineContext

class SearchHistoryDialogFragment : BottomSheetDialogFragment() {
    private var _binding: SearchHistoryDialogFragmentBinding? = null
    private val binding get() = _binding!!
    private var onSearchHistoryClickListener: OnSearchHistoryClickListener? = null
    private val textWatcher = object : TextWatcher {
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            if (binding.searchHistoryEditText.text != null && binding.searchHistoryEditText.text.toString()
                    .isNotEmpty()
            ) {
                binding.searchHistoryButtonText.isEnabled = true
                binding.historyClearTextImage.visibility = View.VISIBLE
            } else {
                binding.searchHistoryButtonText.isEnabled = false
                binding.historyClearTextImage.visibility = View.GONE
            }
        }
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun afterTextChanged(p0: Editable?) {}
    }

    private val onSearchHistoryButtonClickListener = View.OnClickListener {
        onSearchHistoryClickListener?.onClick(binding.searchHistoryEditText.text.toString())
        dismiss()
    }

    internal fun setOnSearchHistoryClickListener(listener: OnSearchHistoryClickListener) {
        onSearchHistoryClickListener = listener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = SearchHistoryDialogFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.searchHistoryButtonText.setOnClickListener(onSearchHistoryButtonClickListener)
        binding.searchHistoryEditText.addTextChangedListener(textWatcher)
        addOnClearClickListener()
    }

    private fun addOnClearClickListener() = binding.historyClearTextImage.setOnClickListener {
        binding.searchHistoryEditText.setText("")
        binding.searchHistoryButtonText.isEnabled = false
    }

    override fun onDestroy() {
        onSearchHistoryClickListener = null
        super.onDestroy()
    }

    interface OnSearchHistoryClickListener {
        fun onClick(searchWord: String)
    }

    companion object {
        fun newInstance(): SearchHistoryDialogFragment = SearchHistoryDialogFragment()
    }
}