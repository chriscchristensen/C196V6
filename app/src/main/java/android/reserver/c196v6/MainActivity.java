package android.reserver.c196v6;

import android.content.Intent;
import android.os.Bundle;
import android.reserver.c196v6.UI.Activity.AssessmentListActivity;
import android.reserver.c196v6.UI.Activity.CourseListActivity;
import android.reserver.c196v6.UI.Activity.MentorEditorActivity;
import android.reserver.c196v6.UI.Activity.SettingsActivity;
import android.reserver.c196v6.UI.Activity.TermListActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;


public class MainActivity extends AppCompatActivity {

    DrawerLayout drawer;
    NavigationView navigationView;

    /**
     * This method sets the contentView and toolbar action items
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this::onNavigationItemSelected);
    }

    /**
     * @param view
     */
    public void showAssessments(View view) {
        Intent intent = new Intent(this, AssessmentListActivity.class);
        startActivity(intent);
    }

    /**
     * @param view
     */
    public void showCourses(View view) {
        Intent intent = new Intent(this, CourseListActivity.class);
        startActivity(intent);
    }

    /**
     * @param view
     */
    public void showTerms(View view) {
        Intent intent = new Intent(this, TermListActivity.class);
        startActivity(intent);
    }

    /**
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                NavUtils.navigateUpTo(this, new Intent(this, AssessmentListActivity.class));
                return true;
            case R.id.action_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();

        switch (id) {
            case R.id.nav_terms:
                Intent nTerms = new Intent(MainActivity.this, TermListActivity.class);
                startActivity(nTerms);
                break;
            case R.id.nav_courses:
                Intent nCourses = new Intent(MainActivity.this, CourseListActivity.class);
                startActivity(nCourses);
                break;
            case R.id.nav_assessments:
                Intent nAssessments = new Intent(MainActivity.this, AssessmentListActivity.class);
                startActivity(nAssessments);
                break;
            case R.id.nav_mentors:
                Intent nMentors = new Intent(MainActivity.this, MentorEditorActivity.class);
                startActivity(nMentors);
                break;
            case R.id.nav_settings:
                Intent nSettings = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(nSettings);
                break;
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}