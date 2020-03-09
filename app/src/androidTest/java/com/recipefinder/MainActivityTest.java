package com.recipefinder;


import android.support.test.espresso.DataInteraction;
import android.support.test.espresso.ViewInteraction;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.assertion.ViewAssertions.*;
import static android.support.test.espresso.matcher.ViewMatchers.*;

import com.recipefinder.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void mainActivityTest() {
        ViewInteraction searchAutoComplete = onView(
allOf(withClassName(is("android.widget.SearchView$SearchAutoComplete")),
childAtPosition(
allOf(withClassName(is("android.widget.LinearLayout")),
childAtPosition(
withClassName(is("android.widget.LinearLayout")),
1)),
0),
isDisplayed()));
        searchAutoComplete.perform(replaceText("chi"), closeSoftKeyboard());
        
        ViewInteraction searchAutoComplete2 = onView(
allOf(withClassName(is("android.widget.SearchView$SearchAutoComplete")), withText("chi"),
childAtPosition(
allOf(withClassName(is("android.widget.LinearLayout")),
childAtPosition(
withClassName(is("android.widget.LinearLayout")),
1)),
0),
isDisplayed()));
        searchAutoComplete2.perform(pressImeActionButton());
        
        ViewInteraction recyclerView = onView(
allOf(withId(R.id.recyclerview),
childAtPosition(
withClassName(is("android.widget.FrameLayout")),
0)));
        recyclerView.perform(actionOnItemAtPosition(0, click()));
        
        ViewInteraction tabView = onView(
allOf(withContentDescription("Ingredients"),
childAtPosition(
childAtPosition(
withId(R.id.tabs),
0),
1),
isDisplayed()));
        tabView.perform(click());
        
        ViewInteraction tabView2 = onView(
allOf(withContentDescription("Steps"),
childAtPosition(
childAtPosition(
withId(R.id.tabs),
0),
2),
isDisplayed()));
        tabView2.perform(click());
        
        pressBack();
        
        pressBack();
        }
    
    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup)parent).getChildAt(position));
            }
        };
    }
    }
