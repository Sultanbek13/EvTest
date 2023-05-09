package com.sultandev.evtest.presentation.ui.webview

import android.graphics.Bitmap
import android.os.Bundle
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.webkit.CookieManager
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.sultandev.evtest.R
import com.sultandev.evtest.databinding.ScreenWebViewBinding

class WebViewScreen : Fragment(R.layout.screen_web_view) {

    private val binding: ScreenWebViewBinding by viewBinding()
    private val saveArgs: WebViewScreenArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cookieManager = CookieManager.getInstance()
        cookieManager.setAcceptCookie(true)

        webViewKeyListener()

        setOnWebUrl()

        setOnWebSetting()

        setOnWebClient()

    }

    private fun setOnWebUrl() {
        val url = saveArgs.url
        binding.apply {
            if(url != "") {
                llEmptyUrl.isVisible = false
                webView.isVisible = true
                webView.loadUrl(url)
            } else {
                llEmptyUrl.isVisible = true
                webView.isVisible = false
            }
        }
    }

    private fun webViewKeyListener() {
        binding.webView.setOnKeyListener(object : View.OnKeyListener {
            override fun onKey(v: View?, keyCode: Int, keyEvent: KeyEvent?): Boolean {
                if (keyCode == KeyEvent.KEYCODE_BACK && keyEvent?.action == MotionEvent.ACTION_UP && binding.webView.canGoBack()) {
                    binding.webView.goBack()
                    return true;
                }
                return false;
            }
        })
    }

    private fun setOnWebSetting() {
        binding.apply {
            webView.settings.javaScriptEnabled = true
            webView.settings.domStorageEnabled = true
            webView.settings.javaScriptCanOpenWindowsAutomatically = true
            webView.settings.loadWithOverviewMode = true
            webView.settings.useWideViewPort = true
            webView.settings.databaseEnabled = true
            webView.settings.setSupportZoom(false)
            webView.settings.allowFileAccess = true
            webView.settings.allowContentAccess = true
        }
    }

    private fun setOnWebClient() {
        binding.webView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                if (view?.contentHeight == 0) {
                    view.reload()
                } else {
                    super.onPageFinished(view, url)
                }
            }
        }
    }
}
