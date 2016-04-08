package br.ita.bditac.mobile.alertas;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;


public class CadastrarEventoActivity extends AppCompatActivity {

    private EditText inputDescricao;
    private Spinner inputCategoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_evento);

        inputDescricao = (EditText) findViewById(R.id.mensagem);

        CarregarCategoria();

    }

    public void Limpar_Click(View view) {

        LimparTela();

    }

    public void Salvar_Click(View view) {

        SalvarDados();

    }


    private void CarregarCategoria(){

        // TODO: Criar essa lista em resources e alterar quando a API estiver pronta para cadastrar esse dominio dinamicamente

        inputCategoria = (Spinner) findViewById(R.id.categoria);

        if (inputCategoria == null)
            return ;

        ArrayList<String> categoriaArrayList = new ArrayList<>();
        categoriaArrayList.add("");
        categoriaArrayList.add("Alagamento");
        categoriaArrayList.add("Incêndio");
        categoriaArrayList.add("Acidente Veicular");
        categoriaArrayList.add("Acidente Aéreo");
        categoriaArrayList.add("Assassinato");
        categoriaArrayList.add("Roubo");
        categoriaArrayList.add("Terremoto");
        categoriaArrayList.add("Desmoronamento");
        categoriaArrayList.add("Tiroteio");

        ArrayAdapter<String> adapter = new ArrayAdapter <String>(this, android.R.layout.simple_dropdown_item_1line, categoriaArrayList);
        inputCategoria.setAdapter(adapter);

    }

    private void LimparTela(){

        inputDescricao.setText("");
        inputCategoria.setSelection(0);

    }

    private void SalvarDados(){

    }

}
