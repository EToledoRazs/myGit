package com.example.impostoderenda;

import android.support.annotation.NonNull;
import android.support.v4.app.NavUtils;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.impostoderenda.dominio.entidade.Cliente;

import java.util.List;

public class ClienteAdapter extends RecyclerView.Adapter<ClienteAdapter.ViewHolderCliente> {

    private List<Cliente> dados;

    public ClienteAdapter(List<Cliente> dados){
        this.dados = dados;
    }



    @NonNull
    @Override
    public ClienteAdapter.ViewHolderCliente onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.linha_clientes, viewGroup, false);

        ViewHolderCliente holderCliente = new ViewHolderCliente(view);

        return holderCliente;
    }

    @Override
    public void onBindViewHolder(@NonNull ClienteAdapter.ViewHolderCliente viewHolder, int i) {

        if ((dados != null) && (dados.size() > 0)){
            Cliente cliente = dados.get(i);
            viewHolder.txtSalarioCli.setText("R$ "+cliente.salario) ;
            viewHolder.txtSalarioResult.setText("R$ "+(cliente.resultSalario));
            viewHolder.txtDataCli.setText(cliente.datas);

        }

    }

    @Override
    public int getItemCount() {
        return dados.size();
    }

    public class ViewHolderCliente extends RecyclerView.ViewHolder{

        public TextView txtSalarioCli;
        public TextView txtSalarioResult;
        public TextView txtDataCli;
        public ViewHolderCliente(@NonNull View itemView) {
            super(itemView);

            txtSalarioCli    = (TextView) itemView.findViewById(R.id.txtSalarioCli);
            txtSalarioResult = (TextView) itemView.findViewById(R.id.txtSalarioResult);
            txtDataCli       = (TextView) itemView.findViewById(R.id.txtDataCli);
        }

    }
}
