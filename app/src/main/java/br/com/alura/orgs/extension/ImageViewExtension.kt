package br.com.alura.orgs.extension

import android.content.Context
import android.widget.ImageView
import br.com.alura.orgs.R
import br.com.alura.orgs.util.GifLoaderUtil
import coil.load
import coil.request.ImageRequest

fun ImageView.tentaCarregarImagem(url: String? = null, context: Context) {
    load(url, GifLoaderUtil.geraImageLoader(context)) {
        trataCasosExcepcionais()
    }
}

fun ImageView.tentaCarregarImagem(url: String? = null) {
    load(url) {
        trataCasosExcepcionais()
    }
}

private fun ImageRequest.Builder.trataCasosExcepcionais() {
    // Enquanto a imagem carrega, essa aparece no lugar
    placeholder(R.drawable.placeholder)

    // Caso falhe (a imagem seja nula), ele adiciona uma imagem padrão
    fallback(R.drawable.erro)

    // Caso a imagem seja procurada e algo dê errado, ou não seja encontrada,
    // Ele adiciona essa padrão
    error(R.drawable.erro)
}