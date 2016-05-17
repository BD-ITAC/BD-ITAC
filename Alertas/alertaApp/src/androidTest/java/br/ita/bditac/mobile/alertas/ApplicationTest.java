
package br.ita.bditac.mobile.alertas;


import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.provider.MediaStore;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static br.ita.bditac.mobile.alertas.ImageViewHasDrawableMatcher.hasDrawable;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.not;


@RunWith(AndroidJUnit4.class)
@SmallTest
public class ApplicationTest extends AndroidTestCase {

    private LocationManager locationManager;

    @Rule
    public IntentsTestRule<CadastrarCriseActivity> capturarImagemTestRule = new IntentsTestRule<>(CadastrarCriseActivity.class, true, true);

    @Override
    protected void setUp() throws Exception {

        locationManager = (LocationManager) this.getContext().getSystemService(Context.LOCATION_SERVICE);
        locationManager.addTestProvider("Test", false, false, false, false, false, false, false, Criteria.POWER_LOW, Criteria.ACCURACY_FINE);
        locationManager.setTestProviderEnabled("Test", true);

        Location location = new Location("Test");
        location.setLatitude(-15.7976);
        location.setLongitude(-47.8344);
        locationManager.setTestProviderLocation("Test", location);

    }

    @Override
    protected void tearDown() throws Exception {

        super.tearDown();

        locationManager.removeTestProvider("Test");

    }

    @Test
    public void test01CadastrarCriseSemFoto() {

        // Na tela de cadastro selecionar o tipo de crise
        onView(withId(R.id.categoria))
                .perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Alagamento")))
                .perform(click());

        // Informar o texto da crise
        onView(withId(R.id.mensagem))
                .perform(typeText("Alagamento da Lagoa. Uma pessoa desaparecida"), closeSoftKeyboard());

        // Acionar o botão para salvar a crise
        onView(withText(R.string.save))
                .perform(scrollTo(), click());

    }

    @Test
    public void test02CadastrarCriseComFoto() {

        // Na tela de cadastro selecionar o tipo de crise
        onView(withId(R.id.categoria))
                .perform(click());
        onData(allOf(is(instanceOf(String.class)), is("Alagamento")))
                .perform(click());

        // Informar o texto da crise
        onView(withId(R.id.mensagem))
                .perform(typeText("Alagamento da Lagoa. Uma pessoa desaparecida"), closeSoftKeyboard());

        // Capturar a imagem
        // Criar um bitmap que vai ser a imagem capturada
        Bitmap icon = BitmapFactory.decodeResource(
                InstrumentationRegistry.getTargetContext().getResources(),
                R.mipmap.ic_launcher);
        // Criar o resultado que vai retornar da camera
        Intent resultData = new Intent();
        resultData.putExtra("data", icon);
        Instrumentation.ActivityResult result = new Instrumentation.ActivityResult(Activity.RESULT_OK, resultData);
        // Simular a camera
        intending(hasAction(MediaStore.ACTION_IMAGE_CAPTURE)).respondWith(result);

        // Verifica que não tem uma imagem capturada
        onView(withId(R.id.camera_image)).check(matches(not(hasDrawable())));

        // Acionar o botão da captura de image
        onView(withText(R.string.take_photo))
                .perform(scrollTo(), click());

        // Verifica que tem uma imagem capturada
        onView(withId(R.id.camera_image)).check(matches(hasDrawable()));

        // Acionar o botão para salvar a crise
        onView(withText(R.string.save))
                .perform(scrollTo(), click());

    }

}