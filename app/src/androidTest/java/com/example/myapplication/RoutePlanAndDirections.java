package com.example.myapplication;


import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class RoutePlanAndDirections {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void routePlanAndDirections() {

        int action_bar_id = mActivityTestRule.getActivity().getResources().getIdentifier("action_bar", "id", "com.example.myapplication");
        int action_bar_container_id = mActivityTestRule.getActivity().getResources().getIdentifier("action_bar_container", "id", "com.example.myapplication");
        
        ViewInteraction materialButton = onView(
                allOf(withId(R.id.dayPlannerButton), withText("dayPlanner"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialButton.perform(click());

        ViewInteraction appCompatImageButton = onView(
                allOf(withContentDescription("Navigate up"),
                        childAtPosition(
                                allOf(withId(action_bar_id),
                                        childAtPosition(
                                                withId(action_bar_container_id),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction actionMenuItemView = onView(
                allOf(withId(R.id.action_search), withContentDescription("Search"),
                        childAtPosition(
                                childAtPosition(
                                        withId(action_bar_id),
                                        1),
                                0),
                        isDisplayed()));
        actionMenuItemView.perform(click());

        DataInteraction relativeLayout = onData(anything())
                .inAdapterView(allOf(withId(R.id.listview),
                        childAtPosition(
                                withClassName(is("android.widget.RelativeLayout")),
                                2)))
                .atPosition(0);
        relativeLayout.perform(click());

        DataInteraction relativeLayout2 = onData(anything())
                .inAdapterView(allOf(withId(R.id.listview),
                        childAtPosition(
                                withClassName(is("android.widget.RelativeLayout")),
                                2)))
                .atPosition(1);
        relativeLayout2.perform(click());

        DataInteraction relativeLayout3 = onData(anything())
                .inAdapterView(allOf(withId(R.id.listview),
                        childAtPosition(
                                withClassName(is("android.widget.RelativeLayout")),
                                2)))
                .atPosition(2);
        relativeLayout3.perform(click());

        DataInteraction relativeLayout4 = onData(anything())
                .inAdapterView(allOf(withId(R.id.listview),
                        childAtPosition(
                                withClassName(is("android.widget.RelativeLayout")),
                                2)))
                .atPosition(3);
        relativeLayout4.perform(click());

        DataInteraction relativeLayout5 = onData(anything())
                .inAdapterView(allOf(withId(R.id.listview),
                        childAtPosition(
                                withClassName(is("android.widget.RelativeLayout")),
                                2)))
                .atPosition(4);
        relativeLayout5.perform(click());

        DataInteraction relativeLayout6 = onData(anything())
                .inAdapterView(allOf(withId(R.id.listview),
                        childAtPosition(
                                withClassName(is("android.widget.RelativeLayout")),
                                2)))
                .atPosition(5);
        relativeLayout6.perform(click());

        DataInteraction relativeLayout7 = onData(anything())
                .inAdapterView(allOf(withId(R.id.listview),
                        childAtPosition(
                                withClassName(is("android.widget.RelativeLayout")),
                                2)))
                .atPosition(6);
        relativeLayout7.perform(click());

        ViewInteraction appCompatImageButton2 = onView(
                allOf(withContentDescription("Navigate up"),
                        childAtPosition(
                                allOf(withId(action_bar_id),
                                        childAtPosition(
                                                withId(action_bar_container_id),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton2.perform(click());

        ViewInteraction materialButton2 = onView(
                allOf(withId(R.id.dayPlannerButton), withText("dayPlanner"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialButton2.perform(click());

        ViewInteraction materialTextView = onView(
                allOf(withId(R.id.add_remove), withText("x"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.dayplanner_items),
                                        0),
                                1),
                        isDisplayed()));
        materialTextView.perform(click());

        ViewInteraction materialTextView2 = onView(
                allOf(withId(R.id.add_remove), withText("x"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.dayplanner_items),
                                        0),
                                1),
                        isDisplayed()));
        materialTextView2.perform(click());

        ViewInteraction materialTextView3 = onView(
                allOf(withId(R.id.add_remove), withText("x"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.dayplanner_items),
                                        0),
                                1),
                        isDisplayed()));
        materialTextView3.perform(click());

        ViewInteraction materialTextView4 = onView(
                allOf(withId(R.id.add_remove), withText("x"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.dayplanner_items),
                                        0),
                                1),
                        isDisplayed()));
        materialTextView4.perform(click());

        ViewInteraction materialTextView5 = onView(
                allOf(withId(R.id.add_remove), withText("x"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.dayplanner_items),
                                        0),
                                1),
                        isDisplayed()));
        materialTextView5.perform(click());

        ViewInteraction materialTextView6 = onView(
                allOf(withId(R.id.add_remove), withText("x"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.dayplanner_items),
                                        0),
                                1),
                        isDisplayed()));
        materialTextView6.perform(click());

        ViewInteraction materialTextView7 = onView(
                allOf(withId(R.id.add_remove), withText("x"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.dayplanner_items),
                                        0),
                                1),
                        isDisplayed()));
        materialTextView7.perform(click());

        ViewInteraction appCompatImageButton3 = onView(
                allOf(withContentDescription("Navigate up"),
                        childAtPosition(
                                allOf(withId(action_bar_id),
                                        childAtPosition(
                                                withId(action_bar_container_id),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton3.perform(click());

        ViewInteraction actionMenuItemView2 = onView(
                allOf(withId(R.id.action_search), withContentDescription("Search"),
                        childAtPosition(
                                childAtPosition(
                                        withId(action_bar_id),
                                        1),
                                0),
                        isDisplayed()));
        actionMenuItemView2.perform(click());

        DataInteraction relativeLayout8 = onData(anything())
                .inAdapterView(allOf(withId(R.id.listview),
                        childAtPosition(
                                withClassName(is("android.widget.RelativeLayout")),
                                2)))
                .atPosition(0);
        relativeLayout8.perform(click());

        DataInteraction relativeLayout9 = onData(anything())
                .inAdapterView(allOf(withId(R.id.listview),
                        childAtPosition(
                                withClassName(is("android.widget.RelativeLayout")),
                                2)))
                .atPosition(1);
        relativeLayout9.perform(click());

        DataInteraction relativeLayout10 = onData(anything())
                .inAdapterView(allOf(withId(R.id.listview),
                        childAtPosition(
                                withClassName(is("android.widget.RelativeLayout")),
                                2)))
                .atPosition(5);
        relativeLayout10.perform(click());

        DataInteraction relativeLayout11 = onData(anything())
                .inAdapterView(allOf(withId(R.id.listview),
                        childAtPosition(
                                withClassName(is("android.widget.RelativeLayout")),
                                2)))
                .atPosition(2);
        relativeLayout11.perform(click());

        ViewInteraction appCompatImageButton4 = onView(
                allOf(withContentDescription("Navigate up"),
                        childAtPosition(
                                allOf(withId(action_bar_id),
                                        childAtPosition(
                                                withId(action_bar_container_id),
                                                0)),
                                1),
                        isDisplayed()));
        appCompatImageButton4.perform(click());

        ViewInteraction materialButton3 = onView(
                allOf(withId(R.id.dayPlannerButton), withText("dayPlanner"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialButton3.perform(click());

        ViewInteraction materialButton4 = onView(
                allOf(withId(R.id.routeplan_btn), withText("ROUTE PLAN"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                2),
                        isDisplayed()));
        materialButton4.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.routeplanset), withText("1. Entrance and Exit Gate\n2. Entrance Plaza\n3. Gorillas\n4. Elephant Odyssey\n5. Entrance and Exit Gate\n"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        textView.check(matches(withText("1. Entrance and Exit Gate\n2. Entrance Plaza\n3. Gorillas\n4. Elephant Odyssey\n5. Entrance and Exit Gate\n")));

        ViewInteraction materialButton5 = onView(
                allOf(withId(R.id.directions_btn), withText("Directions"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        materialButton5.perform(click());

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.directionsSet), withText("Starting from Entrance and Exit Gate\n\n1. Walk 10 meters along Entrance Way to get to Entrance Plaza.\n\nYou have now reached Entrance Plaza!\n"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        textView2.check(matches(withText("Starting from Entrance and Exit Gate\n\n1. Walk 10 meters along Entrance Way to get to Entrance Plaza.\n\nYou have now reached Entrance Plaza!\n")));

        ViewInteraction materialButton6 = onView(
                allOf(withId(R.id.next_btn), withText("Next"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        materialButton6.perform(click());

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.directionsSet), withText("Starting from Entrance Plaza\n\n1. Walk 200 meters along Africa Rocks Street to get to Gorillas.\n\nYou have now reached Gorillas!\n"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        textView3.check(matches(withText("Starting from Entrance Plaza\n\n1. Walk 200 meters along Africa Rocks Street to get to Gorillas.\n\nYou have now reached Gorillas!\n")));

        ViewInteraction materialButton7 = onView(
                allOf(withId(R.id.next_btn), withText("Next"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        materialButton7.perform(click());

        ViewInteraction textView4 = onView(
                allOf(withId(R.id.directionsSet), withText("Starting from Gorillas\n\n1. Walk 200 meters along Africa Rocks Street to get to Lions.\n2. Walk 200 meters along Africa Rocks Street to get to Elephant Odyssey.\n\nYou have now reached Elephant Odyssey!\n"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        textView4.check(matches(withText("Starting from Gorillas\n\n1. Walk 200 meters along Africa Rocks Street to get to Lions.\n2. Walk 200 meters along Africa Rocks Street to get to Elephant Odyssey.\n\nYou have now reached Elephant Odyssey!\n")));

        ViewInteraction materialButton8 = onView(
                allOf(withId(R.id.next_btn), withText("Next"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        materialButton8.perform(click());

        ViewInteraction textView5 = onView(
                allOf(withId(R.id.directionsSet), withText("Starting from Elephant Odyssey\n\n1. Walk 200 meters along Africa Rocks Street to get to Lions.\n2. Walk 200 meters along Sharp Teeth Shortcut to get to Alligators.\n3. Walk 100 meters along Reptile Road to get to Entrance Plaza.\n4. Walk 10 meters along Entrance Way to get to Entrance and Exit Gate.\n\nYou have now reached Entrance and Exit Gate!\n"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        textView5.check(matches(withText("Starting from Elephant Odyssey\n\n1. Walk 200 meters along Africa Rocks Street to get to Lions.\n2. Walk 200 meters along Sharp Teeth Shortcut to get to Alligators.\n3. Walk 100 meters along Reptile Road to get to Entrance Plaza.\n4. Walk 10 meters along Entrance Way to get to Entrance and Exit Gate.\n\nYou have now reached Entrance and Exit Gate!\n")));

        ViewInteraction materialButton9 = onView(
                allOf(withId(R.id.next_btn), withText("Next"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        materialButton9.perform(click());

        ViewInteraction textView6 = onView(
                allOf(withId(android.R.id.message), withText("No more exhibits! \nClick back to return to Route Plan."),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.ScrollView.class))),
                        isDisplayed()));
        textView6.check(matches(withText("No more exhibits! \nClick back to return to Route Plan.")));
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
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
