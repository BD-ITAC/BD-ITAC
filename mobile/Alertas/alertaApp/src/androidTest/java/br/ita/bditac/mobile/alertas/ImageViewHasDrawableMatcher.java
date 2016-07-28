package br.ita.bditac.mobile.alertas;

import android.support.test.espresso.matcher.BoundedMatcher;
import android.view.View;
import android.widget.ImageView;

import org.hamcrest.Description;

/**
 * Created by marcos on 5/17/16.
 */
public class ImageViewHasDrawableMatcher {

    public static BoundedMatcher<View, ImageView> hasDrawable() {

        return new BoundedMatcher<View, ImageView>(ImageView.class) {
            @Override
            public void describeTo(Description description) {
                description.appendText("has drawable");
            }

            @Override
            public boolean matchesSafely(ImageView imageView) {
                return imageView.getDrawable() != null;
            }
        };

    }

}
