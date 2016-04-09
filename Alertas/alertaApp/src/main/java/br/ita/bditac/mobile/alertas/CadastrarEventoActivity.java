package br.ita.bditac.mobile.alertas;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Debug;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

import br.ita.bditac.ws.client.EventoClient;
import br.ita.bditac.ws.model.Evento;
import br.ita.bditac.ws.model.Usuario;


public class CadastrarEventoActivity extends AppCompatActivity {

    // TODO Definir a localização padrão do serviço BD-ITAC em produção
    private static final String DEFAULT_URL = "http://10.0.2.2:8080";

    private static final String DEBUG_URL = "http://10.0.2.2:8080";

    private String alertasUrl;

    private EditText inputDescricao;

    private Spinner inputCategoria;

    private Context context;

    private class SalvarEventoTask extends AsyncTask<Evento, Void, Void> {

        private Exception exception;

        @Override
        protected Void doInBackground(Evento... eventos) {

            try {
                EventoClient eventoClient = new EventoClient(alertasUrl);

                for(Evento evento: eventos) {
                    eventoClient.addEvento(evento);
                }
            }
            catch(Exception ex) {
                this.exception = ex;
            }
            finally {
                return null;
            }

        }

        @Override
        protected void onPostExecute(Void aVoid) {

            super.onPostExecute(aVoid);

            finish();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_evento);

        inputDescricao = (EditText) findViewById(R.id.mensagem);

        SharedPreferences preferences = null;

        context = getApplicationContext();

        if (!Debug.isDebuggerConnected()) {
            preferences = PreferenceManager.getDefaultSharedPreferences(context);
        }

        alertasUrl = Debug.isDebuggerConnected() ? DEBUG_URL : preferences.getString("alerts.service.url", DEFAULT_URL);

        CarregarCategoria();

    }

    public void Limpar_Click(View view) {

        inputDescricao.setText("");
        inputCategoria.setSelection(0);

    }

    public void Salvar_Click(View view) {

        Evento evento = new Evento();

        evento.setDescricao(inputDescricao.getText().toString());
        evento.setCategoria(inputCategoria.getId());
        evento.setNome(Usuario.getNome());
        evento.setEmail(Usuario.getEmail());
        evento.setTelefone(Usuario.getTelefone());

        new SalvarEventoTask().execute(evento);

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

}
