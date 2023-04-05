package br.com.alura.orgs.util

import android.content.Context
import android.os.Build
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder

abstract class GifLoader {

    companion object {
        fun geraImageLoader(contexto: Context) = ImageLoader.Builder(contexto)
            .componentRegistry {
                if (Build.VERSION.SDK_INT >= 28) {
                    add(ImageDecoderDecoder(contexto))
                } else {
                    add(GifDecoder())
                }
            }.build()
    }
}