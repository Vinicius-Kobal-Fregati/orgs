package br.com.alura.orgs.ui.dialog

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import br.com.alura.orgs.databinding.FormularioImagemBinding
import br.com.alura.orgs.extension.tentaCarregarImagem

class FormularioImagemDialog(private val context: Context) {
    // Higher-order function, recebemos uma função no parâmetro e executamos ela quando quisermos
    // No java usáva-se interfaces para ter algo semelhante
    fun mostra(
        urlPadrao: String? = null,
        quandoImagemCarregada: (imagem: String) -> Unit
    ) {
        // Usamos o apply para utilizar os membros do binding sem ter que ficar chamando a variável
        FormularioImagemBinding
            .inflate(LayoutInflater.from(context)).apply {

                urlPadrao?.let {
                    formularioImagemImageview.tentaCarregarImagem(it)
                    formularioImagemUrl.setText(it)
                }

                formularioImagemBotaoCarregar.setOnClickListener {
                    val url = formularioImagemUrl.text.toString()
                    formularioImagemImageview.tentaCarregarImagem(url, context)
                }

                AlertDialog.Builder(context)
                    //.setTitle("Título de teste")
                    //.setMessage("Mensagem de teste")
                    // Dessa forma não temos acesso aos campos, vamos ter que inflar a view
                    //.setView(R.layout.formulario_imagem)
                    .setView(root)
                    .setPositiveButton("Confirmar") { _, _ ->
                        val url = formularioImagemUrl.text.toString()
                        // Aqui quem chamou a função poderá executar o código, recebendo a url
                        quandoImagemCarregada(url)
                    }
                    .setNegativeButton("Cancelar") { _, _ ->

                    }
                    .show()
            }
    }
}