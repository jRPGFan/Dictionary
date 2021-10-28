package com.example.dictionary.view.base

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.example.dictionary.R
import com.example.dictionary.model.data.AppState
import com.example.dictionary.presenter.Interactor
import com.example.dictionary.utils.AlertDialogFragment
import com.example.dictionary.utils.isOnline
import com.example.dictionary.viewmodel.BaseViewModel

abstract class BaseActivity<T : AppState, I : Interactor<T>> : AppCompatActivity() {
    abstract val model: BaseViewModel<T>
    protected var isNetworkAvailable: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        isNetworkAvailable = isOnline(applicationContext)
    }

    override fun onResume() {
        super.onResume()
        isNetworkAvailable = isOnline(applicationContext)
        if (!isNetworkAvailable && isDialogNull()) showNoInternetConnectionDialog()
    }

    protected fun showNoInternetConnectionDialog() {
        showAlertDialog(
            getString(R.string.dialog_title_device_is_offline),
            getString(R.string.dialog_message_device_is_offline)
        )
    }

    protected fun showAlertDialog(title: String?, message: String?) {
        AlertDialogFragment.newInstance(title, message)
            .show(supportFragmentManager, DIALOG_FRAGMENT_TAG)
    }

    private fun isDialogNull(): Boolean {
        return supportFragmentManager.findFragmentByTag(DIALOG_FRAGMENT_TAG) == null
    }

    abstract fun renderData(appState: T)

    companion object {
        private const val DIALOG_FRAGMENT_TAG = "Dialog_fragment_tag"
    }
}