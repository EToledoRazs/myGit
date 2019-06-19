package com.example.impostoderenda;

import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.example.impostoderenda.database.DadosOpenHelper;
import com.example.impostoderenda.dominio.entidade.Cliente;
import com.example.impostoderenda.dominio.repositorio.ClienteRepositorio;

import java.util.List;

public class ActMain extends AppCompatActivity {
    private RecyclerView lstDados;
    private SQLiteDatabase conexao;
    private DadosOpenHelper dadosOpenHelper;
    private ConstraintLayout layoutContentMain;
    private Button btncalc;
    private ClienteAdapter clienteAdapter;
    private ClienteRepositorio clienteRepositorio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        btncalc = findViewById(R.id.btnPagcalc);

        lstDados = (RecyclerView) findViewById(R.id.lstDados);
        layoutContentMain = (ConstraintLayout) findViewById(R.id.layoutContentMain);

        criarConexao();

        lstDados.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        lstDados.setLayoutManager(linearLayoutManager);

        clienteRepositorio = new ClienteRepositorio(conexao);
        List<Cliente> dados = clienteRepositorio.buscarTodos();
        clienteAdapter = new ClienteAdapter(dados);
        lstDados.setAdapter(clienteAdapter);

    }
    public void exibeCalc(View view){
        Intent it = new Intent(ActMain.this, ActCalcImp.class);
        startActivityForResult(it, 0);
    }

    private void criarConexao(){
        try{
            dadosOpenHelper = new DadosOpenHelper(this);
            conexao = dadosOpenHelper.getWritableDatabase();
            Snackbar.make(layoutContentMain, R.string.conexao_estabelecida, Snackbar.LENGTH_SHORT ).setAction("OK", null).show();
        }catch (SQLException ex){
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle("Erro");
            dlg.setMessage(ex.getMessage());
            dlg.setNeutralButton("OK", null).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == 0){
        List<Cliente> dados = clienteRepositorio.buscarTodos();
        clienteAdapter = new ClienteAdapter(dados);
        lstDados.setAdapter(clienteAdapter);
        }

    }
}
