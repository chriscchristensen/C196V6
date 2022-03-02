package android.reserver.c196v6.UI.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.reserver.c196v6.Database.Database;
import android.reserver.c196v6.Models.Term;
import android.reserver.c196v6.R;
import android.reserver.c196v6.UI.Fragments.TermDetailFragment;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class TermDetailActivity extends AppCompatActivity {
    public static final String TERM_ID = "android.reserver.c196v6.termdetailactivity.termid";
    private Term selectedTerm;
    public Database db;

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_detail);
        db = new Database(this);
        db.open();
        Toolbar toolbar = findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        final int selectedTermId = getIntent().getIntExtra(TermDetailFragment.ARG_TERM_ID, 0);
        selectedTerm = db.termDAO.getTermById(selectedTermId);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TermDetailActivity.this, TermEditorActivity.class);

                intent.putExtra(TermDetailFragment.ARG_TERM_ID, selectedTermId);

                startActivity(intent);
            }
        });
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        //fragments api guide: http://developer.android.com/guide/components/fragments.html ***For my reference
        if (savedInstanceState == null) {
            Bundle arguments = new Bundle();
            arguments.putInt(TermDetailFragment.ARG_TERM_ID,
                    getIntent().getIntExtra(TermDetailFragment.ARG_TERM_ID, 0));

            TermDetailFragment fragment = new TermDetailFragment();

            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.term_detail_container, fragment)
                    .commit();
        }
    }

    /**
     */
    @Override
    public void onDestroy() {
        db.close();
        super.onDestroy();
    }

    /**
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                NavUtils.navigateUpTo(this, new Intent(this, TermListActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * @param view
     */
    public void onCourseEdit(View view) {
        Intent intent = new Intent(this, CourseEditorActivity.class);
        intent.putExtra(TermDetailFragment.ARG_TERM_ID, selectedTerm.getId());
        startActivity(intent);
    }
}
