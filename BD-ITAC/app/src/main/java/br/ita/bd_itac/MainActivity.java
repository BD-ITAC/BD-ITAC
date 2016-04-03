package br.ita.bd_itac;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // TODO: Fazer uma função que generaliza as funções start

    public void startCadastrarEvento(View view) {
        Intent itentCadastrarEvento = new Intent(this, CadastrarEventoActivity.class);
        startActivity(itentCadastrarEvento);
    }

    public void startConsultarEvento(View view) {
        Intent itentConsultarEvento = new Intent(this, ConsultaEventoActivity.class);
        startActivity(itentConsultarEvento);
    }

    public void startConsultarIndicador(View view) {
        Intent itentConsultarIndicador = new Intent(this, ConsultarIndicadorActivity.class);
        startActivity(itentConsultarIndicador);
    }
}
