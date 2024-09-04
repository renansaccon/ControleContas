package br.rs.edu.controlecontas

import android.app.DatePickerDialog
import android.content.Intent
import android.icu.text.DecimalFormat
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.DatePicker
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import br.rs.edu.controlecontas.databinding.ActivityMainBinding
import br.rs.edu.controlecontas.db.DataBase
import br.rs.edu.controlecontas.entity.Lancamento
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {


    private lateinit var binding: ActivityMainBinding
    lateinit var itemSelected: String
    lateinit var itemSelectedDetalhe : String
    private lateinit var banco : DataBase
    private lateinit var date : String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        banco = DataBase(this)

        setButtonListener()

        val opcoesTipo = arrayOf(getString(R.string.credito), getString(R.string.debito))
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, opcoesTipo)
        val opcoesTipoSpinner = binding.spTipo
        opcoesTipoSpinner.adapter = adapter
        val opcoesDetalheSpinner = binding.spDetalhe

        opcoesTipoSpinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                itemSelected = p0?.getItemAtPosition(p2).toString()


                if (itemSelected == resources.getString(R.string.credito)) {
                    val opcoesDetalhe = resources.getStringArray(R.array.opcoesSpinnerDetalhe)
                    val adapterDetalhe =
                        ArrayAdapter(this@MainActivity, android.R.layout.simple_list_item_1, opcoesDetalhe)
                    opcoesDetalheSpinner.adapter = adapterDetalhe
                } else {
                    val opcoesDetalhe = resources.getStringArray(R.array.opcoesSpinnerDetalhe2)
                    val adapterDetalhe =
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
            DatePickerDialog(  this, this, 2024, 8, 30).show()
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
        binding.tvDate.text = date
    }

    private fun btSaldoOnClick() {
        val dialog = AlertDialog.Builder(this)
        val saldo = banco.btSaldoOnClick()
        val format = DecimalFormat("#.00")

        dialog.setTitle("Saldo disponível ")
        dialog.setMessage("R$ "+ format.format(saldo.toString().toDouble()))
        dialog.setNegativeButton("Fechar",null)
        dialog.show()
    }


    private fun btLancarOnClick(){
        if (binding.etValor.text.isEmpty() || binding.tvDate.text.toString() == resources.getString(R.string.selecione_a_data)){
            Snackbar.make(binding.btLanAr,"Preencha os campos corretamente", Snackbar.LENGTH_LONG).show()
        }else {
            if (itemSelected == resources.getString(R.string.credito)) {
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
                binding.etValor.setText(getString(R.string.espaco))
                binding.etValor.requestFocus()
                Snackbar.make(binding.btLanAr,"Dados incluídos com exito", Snackbar.LENGTH_LONG).show()

            } else {
                val etValorNegativo = -(binding.etValor.text.toString().toDouble())
                banco.insert(
                    Lancamento(
                        0,
                        itemSelected,
                        date,
                        itemSelectedDetalhe,
                        etValorNegativo
                    )
                )
                binding.tvDate.setText(R.string.selecione_a_data)
                binding.etValor.setText(getString(R.string.espaco))
                binding.etValor.requestFocus()
                Snackbar.make(binding.btLanAr,"Dados incluídos com exito", Snackbar.LENGTH_LONG).show()
            }
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

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
    }


}
