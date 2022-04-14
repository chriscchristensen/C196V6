package android.reserver.c196v6;

import android.content.Intent;
import android.os.Bundle;
import android.reserver.c196v6.UI.Activity.AssessmentListActivity;
import android.reserver.c196v6.UI.Activity.CourseListActivity;
import android.reserver.c196v6.UI.Activity.MentorEditorActivity;
import android.reserver.c196v6.UI.Activity.SettingsActivity;
import android.reserver.c196v6.UI.Activity.TermListActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    DrawerLayout drawer;
    NavigationView navigationView;
    // creating variables for
    // our ui components.
    private RecyclerView courseRV;
    // variable for our adapter
    // class and array list
    private CourseAdapter adapter;
    private ArrayList<CourseModal> courseModalArrayList;

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

        // initializing our variables.
        courseRV = findViewById(R.id.idRVCourses);

        // calling method to
        // build recycler view.
        buildRecyclerView();
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
        // below line is to get our inflater
        MenuInflater inflater = getMenuInflater();

        // inside inflater we are inflating our menu file.
        inflater.inflate(R.menu.search_menu, menu);

        // below line is to get our menu item.
        MenuItem searchItem = menu.findItem(R.id.actionSearch);

        // getting search view of our item.
        SearchView searchView = (SearchView) searchItem.getActionView();

        // below line is to call set on query text listener method.
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // inside on query text change method we are
                // calling a method to filter our recycler view.
                filter(newText);
                return false;
            }
        });
        return true;
    }

    private void filter(String text) {
        // creating a new array list to filter our data.
        ArrayList<CourseModal> filteredlist = new ArrayList<>();

        // running a for loop to compare elements.
        for (CourseModal item : courseModalArrayList) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.getCourseName().toLowerCase().contains(text.toLowerCase())) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredlist.add(item);
            }
        }
        if (filteredlist.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show();
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            adapter.filterList(filteredlist);
        }
    }

    private void buildRecyclerView() {

        // below line we are creating a new array list
        courseModalArrayList = new ArrayList<>();

        // below line is to add data to our array list.
        courseModalArrayList.add(new CourseModal("DSA", "DSA Self Paced Course"));
        courseModalArrayList.add(new CourseModal("JAVA", "JAVA Self Paced Course"));
        courseModalArrayList.add(new CourseModal("C++", "C++ Self Paced Course"));
        courseModalArrayList.add(new CourseModal("Python", "Python Self Paced Course"));
        courseModalArrayList.add(new CourseModal("Fork CPP", "Fork CPP Self Paced Course"));

        // initializing our adapter class.
        adapter = new CourseAdapter(courseModalArrayList, MainActivity.this);

        // adding layout manager to our recycler view.
        LinearLayoutManager manager = new LinearLayoutManager(this);
        courseRV.setHasFixedSize(true);

        // setting layout manager
        // to our recycler view.
        courseRV.setLayoutManager(manager);

        // setting adapter to
        // our recycler view.
        courseRV.setAdapter(adapter);
    }

}