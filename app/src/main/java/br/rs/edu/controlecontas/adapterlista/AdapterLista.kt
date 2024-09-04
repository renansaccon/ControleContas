package br.rs.edu.controlecontas.adapterlista

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.database.Cursor
import android.icu.text.DecimalFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat.startActivity
import br.rs.edu.controlecontas.ListagemActivity
import br.rs.edu.controlecontas.R
import br.rs.edu.controlecontas.db.DataBase
import br.rs.edu.controlecontas.db.DataBase.Companion.COD
import br.rs.edu.controlecontas.db.DataBase.Companion.DATA
import br.rs.edu.controlecontas.db.DataBase.Companion.DETALHE
import br.rs.edu.controlecontas.db.DataBase.Companion.TIPO
import br.rs.edu.controlecontas.db.DataBase.Companion.VALOR
import br.rs.edu.controlecontas.entity.Lancamento

class AdapterLista (val context : Context, private val cursor: Cursor) : BaseAdapter() {

    private var banco : DataBase = DataBase(context) //Desta forma inicializa o DataBase para execução do btDeleteOnClick

    override fun getCount(): Int {
        return cursor.count
    }

    override fun getItem(p0: Int): Any {
        cursor.moveToPosition(p0)
        val listaItem = Lancamento(
            cursor.getInt(COD),
            cursor.getString(TIPO),
            cursor.getString(DATA),
            cursor.getString(DETALHE),
            cursor.getDouble(VALOR)
        )

        return listaItem
    }


    override fun getItemId(p0: Int): Long {
        cursor.moveToPosition(p0)
        return cursor.getLong(COD)
    }

    @SuppressLint("ViewHolder", "InflateParams")
    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val retorno = inflater.inflate(R.layout.lista_elemento, null)
        val format = DecimalFormat("#.00")



        val tvTipoLista = retorno.findViewById<TextView>(R.id.tvTipoElemento)
        val tvDetalheLista = retorno.findViewById<TextView>(R.id.tvDetalheElemento)
        val tvValorLista = retorno.findViewById<TextView>(R.id.tvValorElemento)
        val tvDataLista = retorno.findViewById<TextView>(R.id.tvDataElemento)
        val ibDelete = retorno.findViewById<ImageButton>(R.id.ibDelete)
        val intent = Intent (context, ListagemActivity::class.java)


        cursor.moveToPosition(p0)

        val position : Int = cursor.getInt(COD)
        tvTipoLista.text = cursor.getString(TIPO)
        val tipo = cursor.getString(TIPO)
        tvDetalheLista.text = cursor.getString(DETALHE)
        tvValorLista.text = format.format(cursor.getDouble(VALOR))
        val valor = format.format(cursor.getDouble(VALOR))
        tvDataLista.text = cursor.getString(DATA)
        ibDelete.setOnClickListener {

            val dialog = AlertDialog.Builder(context)

            dialog.setTitle("Deseja confirmar a exclusão?")
            dialog.setMessage(tipo+" R$ "+valor)
            dialog.setNegativeButton("Fechar",null)
            dialog.setPositiveButton("Excluir", DialogInterface.OnClickListener{ dialogInterface, i ->
                banco.btDeleteOnClick(position)
                startActivity(context,intent,null)
            }
            )


            dialog.show()
        }

        return retorno
    }

}
