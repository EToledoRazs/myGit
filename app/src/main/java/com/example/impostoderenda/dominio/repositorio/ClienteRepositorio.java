package com.example.impostoderenda.dominio.repositorio;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.impostoderenda.dominio.entidade.Cliente;

import java.util.ArrayList;
import java.util.List;

public class ClienteRepositorio {
    private SQLiteDatabase conexao;

    public ClienteRepositorio(SQLiteDatabase conexao){
        this.conexao = conexao;
    }

    public void inserir(Cliente cliente){
        ContentValues contentValues = new ContentValues();
        contentValues.put("SALARIO", cliente.salario);
        contentValues.put("RESULTSALARIO", cliente.resultSalario);
        contentValues.put("DATAS", cliente.datas);

        conexao.insertOrThrow("CLIENTE", null, contentValues);

    }
    public void excluir(int codigo){

        String[] paramentros = new String[1];
        paramentros[0] = String.valueOf(codigo);
        conexao.delete("CLIENTE", "CODIGO = ?", paramentros);

    }
    public void alterar(Cliente cliente){
        ContentValues contentValues = new ContentValues();
        contentValues.put("SALARIO", cliente.salario);
        contentValues.put("RESULTSALARIO", cliente.resultSalario);
        contentValues.put("DATAS", cliente.datas);

        String[] paramentros = new String[1];
        paramentros[0] = String.valueOf(cliente.codigo);
        conexao.update("CLIENTE", contentValues, "CODIGO = ?", paramentros);


    }
    public List<Cliente> buscarTodos(){
        List<Cliente> clientes = new ArrayList<Cliente>();

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT CODIGO, SALARIO, RESULTSALARIO, DATAS");
        sql.append("    FROM CLIENTE" );

        Cursor resultado = conexao.rawQuery(sql.toString(), null);

        if(resultado.getCount()>0) {
            resultado.moveToFirst();
            do {
                Cliente cli = new Cliente();
                cli.codigo         = resultado.getInt(resultado.getColumnIndexOrThrow("CODIGO"));
                cli.salario        = resultado.getString(resultado.getColumnIndexOrThrow("SALARIO"));
                cli.resultSalario  = resultado.getString(resultado.getColumnIndexOrThrow("RESULTSALARIO"));
                cli.datas          = resultado.getString(resultado.getColumnIndexOrThrow("DATAS"));

                clientes.add(cli);
            } while (resultado.moveToNext());
        }


        return clientes;
    }
    public Cliente buscarCliente(int codigo){

        Cliente cliente = new Cliente();

        StringBuilder sql = new StringBuilder();
        sql.append("SELECT CODIGO, SALARIO, RESULTSALARIO, DATAS");
        sql.append("FROM CLIENTE" );
        sql.append("WHERE CODIGO = ?");

        String[] paramentros = new String[1];
        paramentros[0] = String.valueOf(codigo);

        Cursor resultado = conexao.rawQuery(sql.toString(), paramentros);

        if(resultado.getCount()>0) {
            resultado.moveToFirst();

                Cliente cli = new Cliente();
                cli.codigo         = resultado.getInt(resultado.getColumnIndexOrThrow("CODIGO"));
                cli.salario        = resultado.getString(resultado.getColumnIndexOrThrow("SALARIO"));
                cli.resultSalario  = resultado.getString(resultado.getColumnIndexOrThrow("RESULTSALARIO"));
                cli.datas          = resultado.getString(resultado.getColumnIndexOrThrow("DATAS"));

                return cliente;
        }
        return null;
    }
}
