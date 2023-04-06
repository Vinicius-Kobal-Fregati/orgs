package br.com.alura.orgs.extension

import android.content.Context
import android.widget.ImageView
import br.com.alura.orgs.R
import br.com.alura.orgs.util.GifLoaderUtil
import coil.load
import coil.request.ImageRequest

fun ImageView.tentaCarregarImagem(
    url: String? = null,
    context: Context,
    fallback: Int = R.drawable.placeholder
) {
    load(url, GifLoaderUtil.geraImageLoader(context)) {
        trataCasosExcepcionais(fallback)
    }
}

fun ImageView.tentaCarregarImagem(
    url: String? = null,
    fallback: Int = R.drawable.placeholder
) {
    load(url) {
        trataCasosExcepcionais(fallback)
    }
}

private fun ImageRequest.Builder.trataCasosExcepcionais(fallback: Int) {
    // Enquanto a imagem carrega, essa aparece no lugar
    placeholder(fallback)

    // Caso falhe (a imagem seja nula), ele adiciona uma imagem padrão
    fallback(R.drawable.erro)

    // Caso a imagem seja procurada e algo dê errado, ou não seja encontrada,
    // Ele adiciona essa padrão
    error(R.drawable.erro)
}