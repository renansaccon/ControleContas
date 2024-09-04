package br.rs.edu.controlecontas

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.rs.edu.controlecontas.adapterlista.AdapterLista
import br.rs.edu.controlecontas.databinding.ActivityListagemBinding
import br.rs.edu.controlecontas.db.DataBase

class ListagemActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListagemBinding
    private lateinit var banco: DataBase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListagemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        carrega()

    }


    fun carrega() {
        banco = DataBase(this)

        val lancamentos = banco.cursorList()
        val adapter = AdapterLista(this, lancamentos)

        binding.lvListagem.adapter = adapter
    }


    override fun onStart() {
        super.onStart()
        carrega()
        /*val lancamento = banco.cursorList()
        val adapter = AdapterLista (this, lancamento)
        binding.lvListagem.adapter = adapter */
    }


    override fun onRestart() {
        super.onRestart()
        carrega()
        System.out.println("onRestart rodou")
    }

    override fun onResume() {
        super.onResume()
        carrega()
        System.out.println("onResume rodou")
    }

    override fun onPause() {
        super.onPause()
        finish()
    }
}
