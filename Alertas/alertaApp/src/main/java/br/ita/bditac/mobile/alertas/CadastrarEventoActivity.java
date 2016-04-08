package br.ita.bditac.mobile.alertas;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;


public class CadastrarEventoActivity extends AppCompatActivity {

    private EditText inputTitulo;
    private EditText inputMsg;
    private Spinner inputClassificacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_evento);

        inputTitulo = (EditText) findViewById(R.id.titulo);
        inputMsg  = (EditText) findViewById(R.id.mensagem);

        CarregarClassificacao();

    }

    public void Limpar_Click(View view) {

        LimparTela();

    }

    public void Salvar_Click(View view) {

        SalvarDados();

    }


    private void CarregarClassificacao(){

        // TODO: Criar essa lista em resources e alterar quando a API estiver pronta para cadastrar esse dominio dinamicamente

        inputClassificacao = (Spinner) findViewById(R.id.classificacao);

        if (inputClassificacao == null)
            return ;

        ArrayList<String> classificacaoArrayList = new ArrayList<>();
        classificacaoArrayList.add("");
        classificacaoArrayList.add("Alagamento");
        classificacaoArrayList.add("Incêndio");
        classificacaoArrayList.add("Acidente Veicular");
        classificacaoArrayList.add("Acidente Aéreo");
        classificacaoArrayList.add("Assassinato");
        classificacaoArrayList.add("Roubo");
        classificacaoArrayList.add("Terremoto");
        classificacaoArrayList.add("Desmoronamento");
        classificacaoArrayList.add("Tiroteio");

        ArrayAdapter<String> adapter = new ArrayAdapter <String>(this, android.R.layout.simple_dropdown_item_1line, classificacaoArrayList);
        inputClassificacao.setAdapter(adapter);

    }

    private void LimparTela(){

        inputTitulo.setText("");
        inputMsg.setText("");
        inputClassificacao.setSelection(0);

    }

    private void SalvarDados(){

    }

}
