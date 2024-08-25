package br.rs.edu.controlecontas

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import br.rs.edu.controlecontas.databinding.ActivityMainBinding
import br.rs.edu.controlecontas.db.DataBase
import br.rs.edu.controlecontas.entity.Lancamento


class MainActivity : AppCompatActivity() {


    lateinit var binding: ActivityMainBinding
    lateinit var itemSelected: String
    lateinit var itemSelectedDetalhe : String
    private lateinit var banco : DataBase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        banco = DataBase(this)

        setButtonListener()

        val opcoesTipo = arrayOf(getString(R.string.credito), getString(R.string.debito))
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, opcoesTipo)
        var opcoesTipoSpinner = binding.spTipo
        opcoesTipoSpinner.adapter = adapter
        var opcoesDetalheSpinner = binding.spDetalhe

        opcoesTipoSpinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                itemSelected = p0?.getItemAtPosition(p2).toString()


                if (itemSelected == resources.getString(R.string.credito)) {
                    val opcoesDetalhe = resources.getStringArray(R.array.opcoesSpinnerDetalhe)
                    var adapterDetalhe =
                        ArrayAdapter(this@MainActivity, android.R.layout.simple_list_item_1, opcoesDetalhe)
                    opcoesDetalheSpinner.adapter = adapterDetalhe
                } else {
                    val opcoesDetalhe = resources.getStringArray(R.array.opcoesSpinnerDetalhe2)
                    var adapterDetalhe =
                        ArrayAdapter(this@MainActivity, android.R.layout.simple_list_item_1, opcoesDetalhe)
                    opcoesDetalheSpinner.adapter = adapterDetalhe
                }


                opcoesDetalheSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                    override fun onItemSelected(p0 : AdapterView<*>?, p1: View?, p2: Int, p3: Long){
                        itemSelectedDetalhe = p0?.getItemAtPosition(p2).toString()
                        System.out.println(itemSelected + "   " + itemSelectedDetalhe)
                    }

                    override fun onNothingSelected(p0: AdapterView<*>?) {
                    }

                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
    }

    private fun setButtonListener(){

        binding.btLanAr.setOnClickListener{
            btLancarOnClick()
        }


    }

    private fun btLancarOnClick(){
        banco.insert(Lancamento(0,itemSelected, binding.etData.text.toString(), itemSelectedDetalhe, binding.etValor.text.toString() ) )
        binding.etData.setText("")
        binding.etValor.setText("")
        binding.etValor.setOnFocusChangeListener()
    }

    override fun onStart(){
        super.onStart()
        val registro = banco.cursorList()
    }
}
