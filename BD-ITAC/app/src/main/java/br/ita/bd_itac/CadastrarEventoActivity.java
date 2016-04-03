package br.ita.bd_itac;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;


public class CadastrarEventoActivity extends AppCompatActivity {

    private EditText inputNome, inputEmail, inputFone, inputLocal, inputTitulo, inputMsg;
    private Spinner inputClassificacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_evento);

        inputNome = (EditText) findViewById(R.id.nome);
        inputEmail = (EditText) findViewById(R.id.email);
        inputFone = (EditText) findViewById(R.id.fone);
        inputLocal = (EditText) findViewById(R.id.local);
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

        inputNome.setText("");
        inputEmail.setText("");
        inputFone.setText("");
        inputLocal.setText("");
        inputTitulo.setText("");
        inputMsg.setText("");
        inputClassificacao.setSelection(0);

    }

    private void SalvarDados(){

        // TODO: Ajustar esse método para chmar a API ou o Mock para simular a integração

        StringBuilder builder = new StringBuilder();

        builder.append("{\n");
        builder.append(" \"doc\":{");

        builder.append("\"nome\": \"" + inputNome.getEditableText().toString() + "\",\n");
        builder.append("\"email\": \"" + inputEmail.getEditableText().toString() + "\",\n");
        builder.append("\"fone\": \"" + inputFone.getEditableText().toString() + "\",\n");
        builder.append("\"local: \"" + inputLocal.getEditableText().toString() + "\",\n");
        builder.append("\"titulo: \"" + inputTitulo.getEditableText().toString() + "\",\n");
        builder.append("\"classificacao\" : " + inputClassificacao.getSelectedItem().toString() + "\",\n");
        builder.append("\"msg\": \"" + inputMsg.getEditableText().toString() + "\"}\n}\n\n");

        builder.append("<<agora é só enviar essa mensagem para o Mock ou para a API de integração!! >>");

        Toast toast = Toast.makeText(CadastrarEventoActivity.this, builder.toString(), Toast.LENGTH_LONG);
        toast.show();
    }

}
