package com.shahryar.cryptoprice.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.DialogFragment

class AboutDialog: DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent { AboutDialogView() }
        }
    }

    @Composable
    fun AboutDialogView() {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AnnotatedClickableText(
                text = "Contact me at shahryar.kh78@gmail.com",
                uri = "mailto:shahryar.kh78@gmail.com",
                tag = "URL"
            )
            Spacer(modifier = Modifier.height(10.dp))
            AnnotatedClickableText(
                text = "CryptoPrice repository on Github",
                uri = "https://www.github.com/5hahryar/CryptoPrice",
                tag = "URL"
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "v1.2.0", color = Color.Gray)
        }
    }

    @Composable
    fun AnnotatedClickableText(text: String, uri: String, tag: String) {
        val uriHandler = LocalUriHandler.current
        val annotatedText = buildAnnotatedString {
//            append(text)
            pushStringAnnotation(tag, uri)
            withStyle(style = SpanStyle(color = Color.Black,
                fontWeight = FontWeight.Medium)
            ) {
                append(text)
            }
            pop()
        }
        ClickableText(text = annotatedText, onClick = { offset ->
            Log.d("compose", "clicked")
            val annotations = annotatedText.getStringAnnotations(tag, offset, offset)
                annotations.firstOrNull()?.let { annotation ->
                    uriHandler.openUri(annotation.item)
                }
        })
    }

    @Composable
    @Preview
    fun Preview() {
        AboutDialogView()
    }
}