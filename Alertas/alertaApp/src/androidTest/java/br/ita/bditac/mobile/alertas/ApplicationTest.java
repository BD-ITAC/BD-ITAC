
package br.ita.bditac.mobile.alertas;


import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.SmallTest;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


@RunWith(AndroidJUnit4.class)
@SmallTest
public class ApplicationTest {

    @Rule
    public ActivityTestRule<CadastrarCriseActivity> cadastrarEventoActivityActivityTestRule = new ActivityTestRule<CadastrarCriseActivity>(CadastrarCriseActivity.class);

    @Test
    public void test01CadastrarEvento() {
        onView(withId(R.id.categoria)).perform(click());
    }

}