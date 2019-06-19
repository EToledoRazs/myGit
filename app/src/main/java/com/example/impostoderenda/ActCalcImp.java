package com.example.impostoderenda;

import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.impostoderenda.database.DadosOpenHelper;
import com.example.impostoderenda.dominio.entidade.Cliente;
import com.example.impostoderenda.dominio.repositorio.ClienteRepositorio;

import java.text.DateFormat;
import java.util.Calendar;

public class ActCalcImp extends AppCompatActivity {
    private EditText edtSalario;
    private TextView txtResult;
    private ClienteRepositorio clienteRepositorio;
    private SQLiteDatabase conexao;
    private DadosOpenHelper dadosOpenHelper;
    private ConstraintLayout layoutContentActCalc;
    private Cliente cliente;
    private Button btnCalcular;
    private Button btnGravar;
    private int numValor;
    private float result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_calc_imp);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        edtSalario       = (EditText) findViewById(R.id.edtSalario);
        txtResult = (TextView) findViewById(R.id.txtResultSalario);
        layoutContentActCalc = (ConstraintLayout) findViewById(R.id.layoutContentActCalc);
        btnCalcular = (Button) findViewById(R.id.btnCalcular);
        btnGravar = (Button) findViewById(R.id.btnGravarSalario);
        criarConexao();

        btnGravar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                confirmar(numValor, result);
            }
        });

        btnCalcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtSalario.getText().toString().isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "Informe o sálario", Toast.LENGTH_LONG).show();
                    edtSalario.requestFocus();
                }else
                {
                    Toast.makeText(getApplicationContext(), "...", Toast.LENGTH_SHORT).show();
                    numValor = Integer.parseInt(edtSalario.getText().toString());
                    result = calcImposto(numValor);
                    if(result == 0) txtResult.setText("Isento");
                    else if (result == - 1) txtResult.setText("Salário invalido");
                    else txtResult.setText("R$ " + String.valueOf(result));

                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){

            case R.id.action_cancelar:
                Toast.makeText(this, "Voltando...", Toast.LENGTH_SHORT).show();
                finish();
                break;
            case R.id.action_info:
                AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            {
                  dlg.setTitle("Calculadora de imposto");
                  dlg.setMessage("Calcule o valor do seu imposto com base no seu salário \nVersão 1.2");
                  dlg.setNeutralButton("Voltar", null);
                  dlg.show();
                }
        }
        return super.onOptionsItemSelected(item);
    }
    private void criarConexao(){
        try{
            dadosOpenHelper = new DadosOpenHelper(this);
            conexao = dadosOpenHelper.getWritableDatabase();
            Snackbar.make(layoutContentActCalc, R.string.conexao_estabelecida, Snackbar.LENGTH_LONG ).setAction("OK", null).show();

            clienteRepositorio = new ClienteRepositorio(conexao);
        }catch (SQLException ex){
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle("Erro");
            dlg.setMessage(ex.getMessage());
            dlg.setNeutralButton("OK", null).show();
        }
    }
    private void confirmar(int salario, float result){
        cliente = new Cliente();
        cliente.salario = salario +"";
        cliente.resultSalario = result + "";
        Calendar calendar = Calendar.getInstance();
        String data = DateFormat.getDateInstance().format(calendar.getTime());
        cliente.datas = data;

        try{
            clienteRepositorio.inserir(cliente);
            finish();
            Toast.makeText(this, "Salvando", Toast.LENGTH_SHORT).show();
        }catch (SQLException ex){
            AlertDialog.Builder dlg = new AlertDialog.Builder(this);
            dlg.setTitle("Erro");
            dlg.setMessage(ex.getMessage());
            dlg.setNeutralButton("OK", null).show();
        }

    }

    public float calcImposto(float value) {
        if (value <= 1903.98)
        {
            return 0;
        }else if(value >= 1903.98 && value <= 2826.65)
        {
            return (float) ((value*0.075)-142.80);
        }
        else if(value >= 2826.66 && value <= 3751.05)
        {
            return (float) ((value*0.15)-354.80);
        }
        else if(value >= 3751.06 && value <= 4664.68)
        {
            return (float) ((value*0.225)-636.13);
        }
        else if(value > 4664.68)
        {
            return (float) ((value*0.275)-869.36);
        }
        else
        {
            return -1;
        }
    }
}
