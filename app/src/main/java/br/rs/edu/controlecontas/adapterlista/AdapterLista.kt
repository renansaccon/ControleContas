package br.rs.edu.controlecontas.adapterlista

import android.content.Context
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import br.rs.edu.controlecontas.R
import br.rs.edu.controlecontas.databinding.ListaElementoBinding
import br.rs.edu.controlecontas.db.DataBase.Companion.COD
import br.rs.edu.controlecontas.db.DataBase.Companion.DATA
import br.rs.edu.controlecontas.db.DataBase.Companion.DETALHE
import br.rs.edu.controlecontas.db.DataBase.Companion.TIPO
import br.rs.edu.controlecontas.db.DataBase.Companion.VALOR
import br.rs.edu.controlecontas.entity.Lancamento

class AdapterLista (val context : Context, val cursor: Cursor) : BaseAdapter(){

    private lateinit var binding : ListaElementoBinding

    override fun getCount(): Int {
        return cursor.count
    }

    override fun getItem(p0: Int): Any {
        cursor.moveToPosition( p0 )
        val listaItem = Lancamento(
            cursor.getInt(COD),
            cursor.getString(TIPO),
            cursor.getString(DATA),
            cursor.getString(DETALHE),
            cursor.getDouble(VALOR))

        return listaItem
    }


    override fun getItemId(p0: Int): Long {
        cursor.moveToPosition( p0 )
        return cursor.getLong(COD)
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val inflater = context.getSystemService( Context.LAYOUT_INFLATER_SERVICE ) as LayoutInflater
        val retorno = inflater.inflate(R.layout.lista_elemento, null)

        val tvTipoLista = retorno.findViewById<TextView>(R.id.tvTipoElemento)
        val tvDetalheLista = retorno.findViewById<TextView>(R.id.tvDetalheElemento)
        val tvValorLista = retorno.findViewById<TextView>(R.id.tvValorElemento)
        val tvDataLista = retorno.findViewById<TextView>(R.id.tvDataElemento)

        cursor.moveToPosition( p0 )

        tvTipoLista.setText(cursor.getString(TIPO))
        tvDetalheLista.setText(cursor.getString(DETALHE))
        tvValorLista.setText(cursor.getString(VALOR))
        tvDataLista.setText(cursor.getString(DATA))

        return retorno
    }
}