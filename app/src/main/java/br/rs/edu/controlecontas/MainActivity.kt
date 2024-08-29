package br.rs.edu.controlecontas

import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import br.rs.edu.controlecontas.adapterlista.AdapterLista
import br.rs.edu.controlecontas.databinding.ActivityMainBinding
import br.rs.edu.controlecontas.db.DataBase
import br.rs.edu.controlecontas.entity.Lancamento


class MainActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {


    lateinit var binding: ActivityMainBinding
    lateinit var itemSelected: String
    lateinit var itemSelectedDetalhe : String
    private lateinit var banco : DataBase
    lateinit var date : String


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
                    }

                    override fun onNothingSelected(p0: AdapterView<*>?) {
                    }

                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }
        binding.btData.setOnClickListener{
            DatePickerDialog(this, this, 2024, 8, 30).show()
        }

    }

    private fun setButtonListener(){

        binding.btLanAr.setOnClickListener{
            btLancarOnClick()
        }

        binding.btSaldo.setOnClickListener{
            btSaldoOnClick()
        }

        binding.btLancamento.setOnClickListener {
            btLancamentosOnClick()
        }

    }



    override fun onDateSet(p0: DatePicker?, p1: Int, p2: Int, p3: Int) {
        date = p3.toString()+"/"+p2.toString()+"/"+p1.toString()
        binding.tvDate.setText(date)
    }

    private fun btSaldoOnClick() {
        val dialog = AlertDialog.Builder(this)
        val saldo = banco.btSaldoOnClick()

        dialog.setTitle("Saldo disponível ")
        dialog.setMessage("R$ "+saldo.toString().toDouble())
        dialog.setNegativeButton("Fechar",null)
        dialog.show()
    }


    private fun btLancarOnClick(){
        if (binding.etValor.text.isEmpty() || binding.tvDate.text.toString() == resources.getString(R.string.selecione_a_data)){
            Toast.makeText(this,"Preencha os campos corretamente", Toast.LENGTH_LONG).show()
        }else {
            banco.insert(
                Lancamento(
                    0,
                    itemSelected,
                    date,
                    itemSelectedDetalhe,
                    binding.etValor.text.toString().toDouble()
                )
            )
            binding.tvDate.setText(R.string.selecione_a_data)
            binding.etValor.setText("")
            binding.etValor.requestFocus()
            Toast.makeText(this, "Dados incluídos com sucesso", Toast.LENGTH_SHORT).show()
        }
    }

    private fun btLancamentosOnClick() {
        val intent = Intent(this, ListagemActivity::class.java)
        startActivity(intent)
    }


    override fun onStart(){
        super.onStart()
        banco.cursorList()
    }


}
