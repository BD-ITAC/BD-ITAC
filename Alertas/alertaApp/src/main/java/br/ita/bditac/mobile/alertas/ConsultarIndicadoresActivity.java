package br.ita.bditac.mobile.alertas;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Debug;
import android.preference.PreferenceManager;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

import br.ita.bditac.ws.client.IndicadoresClient;
import br.ita.bditac.ws.model.Indicadores;


public class ConsultarIndicadoresActivity extends ChartBase implements OnChartValueSelectedListener {

    private Context context;

    private String alertasUrl;

    private PieChart mChart;

    private Typeface tf;

    private Indicadores indicadores = null;

    private class ConsultarIndicadoresTask extends AsyncTask<Void, Void, Indicadores> {

        private Exception exception;

        protected Indicadores doInBackground(Void... params) {

            try {
                IndicadoresClient indicadoresClient = new IndicadoresClient(alertasUrl);

                indicadores = indicadoresClient.getIndicadores();
            }
            catch(Exception ex) {
                CharSequence mensagem = getText(R.string.msg_alerts_service_unaivalable);
                Toast.makeText(context, mensagem, Toast.LENGTH_LONG).show();

                Log.e(this.getClass().getSimpleName(), ex.getMessage(), ex);
            }
            finally {
                return indicadores;
            }

        }

        @Override
        protected void onPostExecute(Indicadores indicadores) {

            super.onPostExecute(indicadores);

            if(indicadores != null) {
                mChart = (PieChart)findViewById(R.id.indicadores_chart);
                mChart.setUsePercentValues(true);
                mChart.setDescription("");
                mChart.setExtraOffsets(5, 10, 5, 5);

                mChart.setDragDecelerationFrictionCoef(0.95f);

                tf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");

                mChart.setCenterTextTypeface(Typeface.createFromAsset(getAssets(), "OpenSans-Light.ttf"));
                mChart.setCenterText(generateCenterSpannableText());

                mChart.setExtraOffsets(0.f, 50.f, 0.f, 50.f);

                mChart.setDrawHoleEnabled(true);
                mChart.setHoleColor(Color.WHITE);

                mChart.setTransparentCircleColor(Color.WHITE);
                mChart.setTransparentCircleAlpha(110);

                mChart.setHoleRadius(58f);
                mChart.setTransparentCircleRadius(61f);

                mChart.setDrawCenterText(true);

                mChart.setRotationAngle(0);
                // enable rotation of the chart by touch
                mChart.setRotationEnabled(true);
                mChart.setHighlightPerTapEnabled(true);

                // mChart.setUnit(" €");
                // mChart.setDrawUnitsInChart(true);

                // add a selection listener
                //mChart.setOnChartValueSelectedListener(this);

                setData(2, 100);

                mChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
                // mChart.spin(2000, 0, 360);

                Legend l = mChart.getLegend();
                l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
                l.setEnabled(false);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_consultar_indicadores);

        SharedPreferences preferences = null;

        this.context = getApplicationContext();

        if (!Debug.isDebuggerConnected()) {
            preferences = PreferenceManager.getDefaultSharedPreferences(context);
        }

        alertasUrl = Debug.isDebuggerConnected() ? Constants.DEBUG_URL : preferences.getString("alerts.service.url", Constants.DEFAULT_URL);

        new ConsultarIndicadoresTask().execute();

        try {
            if(indicadores == null || indicadores.getIndicadores().size() == 0 ) {
                CharSequence mensagem = getText(R.string.msg_statistics_service_unaivalable);

                Log.i(this.getClass().getSimpleName(), "Statistics unaivalable.");
            }
        }
        catch(Exception ex) {
            CharSequence mensagem = getText(R.string.msg_alerts_service_unaivalable);
            Toast.makeText(context, mensagem, Toast.LENGTH_LONG).show();

            Log.e(this.getClass().getSimpleName(), ex.getMessage(), ex);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.pie, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.actionToggleValues: {
                for (IDataSet<?> set : mChart.getData().getDataSets())
                    set.setDrawValues(!set.isDrawValuesEnabled());

                mChart.invalidate();
                break;
            }
            case R.id.actionToggleHole: {
                if (mChart.isDrawHoleEnabled())
                    mChart.setDrawHoleEnabled(false);
                else
                    mChart.setDrawHoleEnabled(true);
                mChart.invalidate();
                break;
            }
            case R.id.actionDrawCenter: {
                if (mChart.isDrawCenterTextEnabled())
                    mChart.setDrawCenterText(false);
                else
                    mChart.setDrawCenterText(true);
                mChart.invalidate();
                break;
            }
            case R.id.actionToggleXVals: {

                mChart.setDrawSliceText(!mChart.isDrawSliceTextEnabled());
                mChart.invalidate();
                break;
            }
            case R.id.actionSave: {
                // mChart.saveToGallery("title"+System.currentTimeMillis());
                mChart.saveToPath("title" + System.currentTimeMillis(), "");
                break;
            }
            case R.id.actionTogglePercent:
                mChart.setUsePercentValues(!mChart.isUsePercentValuesEnabled());
                mChart.invalidate();
                break;
            case R.id.animateX: {
                mChart.animateX(1400);
                break;
            }
            case R.id.animateY: {
                mChart.animateY(1400);
                break;
            }
            case R.id.animateXY: {
                mChart.animateXY(1400, 1400);
                break;
            }
        }
        return true;
    }

    private void setData(int count, float range) {

        float mult = range;

        ArrayList<Entry> yVals1 = new ArrayList<Entry>();
        ArrayList<Integer> yValues = new ArrayList<>(indicadores.getIndicadores().values());

        // IMPORTANT: In a PieChart, no values (Entry) should have the same
        // xIndex (even if from different DataSets), since no values can be
        // drawn above each other.

        for (int i = 0; i < indicadores.getIndicadores().size(); i++) {
            yVals1.add(new Entry((float) yValues.get(i), i));
        }

        ArrayList<String> xVals = new ArrayList<String>(indicadores.getIndicadores().keySet());

        PieDataSet dataSet = new PieDataSet(yVals1, "Estatísticas");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);


        dataSet.setValueLinePart1OffsetPercentage(80.f);
        dataSet.setValueLinePart1Length(0.3f);
        dataSet.setValueLinePart2Length(0.4f);
        // dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);

        PieData data = new PieData(xVals, dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.BLACK);
        data.setValueTypeface(tf);
        mChart.setData(data);

        // undo all highlights
        mChart.highlightValues(null);

        mChart.invalidate();
    }

    private SpannableString generateCenterSpannableText() {

        SpannableString s = new SpannableString("Indicadores de Alertas\nBD-ITAC");

        s.setSpan(new RelativeSizeSpan(1.7f), 0, 12, 0);
        s.setSpan(new StyleSpan(Typeface.NORMAL), 12, s.length() - 7, 0);
        s.setSpan(new ForegroundColorSpan(Color.GRAY), 12, s.length() - 7, 0);
        s.setSpan(new RelativeSizeSpan(.8f), 8, s.length() - 9, 0);
        s.setSpan(new StyleSpan(Typeface.ITALIC), s.length() - 8, s.length(), 0);
        s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length() - 8, s.length(), 0);

        return s;

    }

    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
        if (e == null) {
            return;
        }
    }

    @Override
    public void onNothingSelected() {

        Log.i("PieChart", "nothing selected");
    }

}
