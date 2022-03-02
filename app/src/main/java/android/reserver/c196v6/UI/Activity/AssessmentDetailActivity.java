package android.reserver.c196v6.UI.Activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.reserver.c196v6.Database.Database;
import android.reserver.c196v6.MainActivity;
import android.reserver.c196v6.Models.Assessment;
import android.reserver.c196v6.R;
import android.reserver.c196v6.Receivers.AlarmReceiver;
import android.reserver.c196v6.UI.Fragments.AssessmentDetailFragment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AssessmentDetailActivity extends AppCompatActivity {

    private Assessment selectedAssessment;
    public Database db;

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_detail);
        db = new Database(this);
        db.open();
        Toolbar toolbar = findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        final int selectedAssessmentId = getIntent().getIntExtra(AssessmentDetailFragment.ARG_ASSESSMENT_ID, 0);
        selectedAssessment = db.assessmentDAO.getAssessmentById(selectedAssessmentId);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AssessmentDetailActivity.this, AssessmentEditorActivity.class);
                intent.putExtra(AssessmentDetailFragment.ARG_ASSESSMENT_ID, selectedAssessmentId);
                startActivity(intent);
            }
        });
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        if (savedInstanceState == null) {
            Bundle arguments = new Bundle();
            arguments.putInt(AssessmentDetailFragment.ARG_ASSESSMENT_ID,
                    getIntent().getIntExtra(AssessmentDetailFragment.ARG_ASSESSMENT_ID, 0));

            AssessmentDetailFragment fragment = new AssessmentDetailFragment();

            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.assessment_detail_container, fragment)
                    .commit();
        }
    }

    /**
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_reminder, menu);
        return true;
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
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                NavUtils.navigateUpTo(this, new Intent(this, AssessmentListActivity.class));
                return true;
            case R.id.action_reminder:
                String alarmTitle = "Assessment Reminder";
                String alarmText = "Assessment '" + selectedAssessment.getName() + "' is today.";

                Intent alarmIntent = new Intent(this.getApplicationContext(), AlarmReceiver.class);
                alarmIntent.putExtra("mNotificationTitle", alarmTitle);
                alarmIntent.putExtra("mNotificationContent", alarmText);

                PendingIntent pendingIntent = PendingIntent.getBroadcast(this.getApplicationContext(), 123456789, alarmIntent, PendingIntent.FLAG_IMMUTABLE);
                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
                    Date date = dateFormat.parse(selectedAssessment.getDate());
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(date);

                    alarmManager.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return true;
            case R.id.action_home:
                Intent iHome = new Intent(this, MainActivity.class);
                startActivity(iHome);
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
