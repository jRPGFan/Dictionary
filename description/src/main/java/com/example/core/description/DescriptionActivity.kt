package com.example.core.description

import android.content.Context
import android.content.Intent
import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import coil.ImageLoader
import coil.request.LoadRequestBuilder
import com.example.core.R
import com.example.core.databinding.ActivityDescriptionBinding
import com.example.utils.AlertDialogFragment
import com.example.utils.OnlineLiveData

class DescriptionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDescriptionBinding

    @RequiresApi(31)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDescriptionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setActionbarHomeButtonAsUp()
        binding.descriptionScreenSwipeRefreshLayout.setOnRefreshListener { startLoadingOrShowError() }
        setData()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setActionbarHomeButtonAsUp() {
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    @RequiresApi(31)
    private fun setData() {
        val bundle = intent.extras
        binding.descriptionHeader.text = bundle?.getString(WORD_EXTRA)
        binding.descriptionTextview.text = bundle?.getString(DESCRIPTION_EXTRA)
        val imageLink = bundle?.getString(URL_EXTRA)
        if (imageLink.isNullOrBlank()) stopRefreshAnimationIfNeeded()
        else useCoilToLoadPhoto(binding.descriptionImageview, imageLink)
    }

    @RequiresApi(31)
    private fun startLoadingOrShowError() {
        OnlineLiveData(this).observe(
            this@DescriptionActivity,
            Observer<Boolean> {
                if (it) setData()
                else {
                    AlertDialogFragment.Companion.newInstance(
                        getString(R.string.dialog_title_device_is_offline),
                        getString(R.string.dialog_message_device_is_offline)
                    ).show(supportFragmentManager, DIALOG_FRAGMENT_TAG)
                    stopRefreshAnimationIfNeeded()
                }
            }
        )
    }

    private fun stopRefreshAnimationIfNeeded() {
        if (binding.descriptionScreenSwipeRefreshLayout.isRefreshing)
            binding.descriptionScreenSwipeRefreshLayout.isRefreshing = false
    }

    @RequiresApi(31)
    private fun useCoilToLoadPhoto(imageView: ImageView, imageLink: String) {
        val request = LoadRequestBuilder(this).data("https:$imageLink")
            .target(
                onStart = {},
                onSuccess = { result ->
                    imageView.setImageDrawable(result)
                    val blurEffect = RenderEffect.createBlurEffect(15f, 0f, Shader.TileMode.MIRROR)
                    imageView.setRenderEffect(blurEffect)
                            },
                onError = { imageView.setImageResource(R.drawable.ic_load_error_vector) }
            ).build()
        ImageLoader(this).execute(request)
    }

    companion object {
        private const val DIALOG_FRAGMENT_TAG = "DIALOG_FRAGMENT_TAG"
        private const val WORD_EXTRA = "WORD_EXTRA"
        private const val DESCRIPTION_EXTRA = "DESCRIPTION_EXTRA"
        private const val URL_EXTRA = "URL_EXTRA"
        fun getIntent(
            context: Context,
            word: String,
            description: String,
            url: String?
        ): Intent = Intent(context, DescriptionActivity::class.java).apply {
            putExtra(WORD_EXTRA, word)
            putExtra(DESCRIPTION_EXTRA, description)
            putExtra(URL_EXTRA, url)
        }
    }
}