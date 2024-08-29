package br.rs.edu.controlecontas

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
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

        System.out.println("Entrou no bindingActivity")

        banco = DataBase(this)

        val lancamentos = banco.cursorList()
        val adapter = AdapterLista(this, lancamentos)
        System.out.println("Criou variáveis")

        binding.lvListagem.adapter = adapter

    }

    override fun onStart() {
        super.onStart()
        val lancamento = banco.cursorList()
        val adapter = AdapterLista (this, lancamento)
        System.out.println("Criou variaves")
        binding.lvListagem.adapter = adapter
    }
}
