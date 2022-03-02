package android.reserver.c196v6.UI.Activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.reserver.c196v6.Database.Database;
import android.reserver.c196v6.MainActivity;
import android.reserver.c196v6.Models.Course;
import android.reserver.c196v6.R;
import android.reserver.c196v6.Receivers.AlarmReceiver;
import android.reserver.c196v6.UI.Fragments.CourseDetailFragment;
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

public class CourseDetailActivity extends AppCompatActivity {

    private Course selectedCourse;
    public Database db;

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);
        db = new Database(this);
        db.open();
        Toolbar toolbar = findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        final int selectedCourseId = getIntent().getIntExtra(CourseDetailFragment.ARG_COURSE_ID, 0);
        selectedCourse = db.courseDAO.getCourseById(selectedCourseId);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CourseDetailActivity.this, CourseEditorActivity.class);
                intent.putExtra(CourseDetailFragment.ARG_COURSE_ID, selectedCourseId);
                startActivity(intent);
            }
        });
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        if (savedInstanceState == null) {
            Bundle arguments = new Bundle();
            arguments.putInt(CourseDetailFragment.ARG_COURSE_ID,
                    getIntent().getIntExtra(CourseDetailFragment.ARG_COURSE_ID, 0));

            CourseDetailFragment fragment = new CourseDetailFragment();

            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.course_detail_container, fragment)
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
                //start course alarm setup
                String startNotificationTitle = "Course Start Reminder";
                String startNotificationText = selectedCourse.getTitle() + " begins today.";

                Intent startNotificationIntent = new Intent(this.getApplicationContext(), AlarmReceiver.class);
                startNotificationIntent.putExtra("mNotificationTitle", startNotificationTitle);
                startNotificationIntent.putExtra("mNotificationContent", startNotificationText);

                PendingIntent startPendingIntent = PendingIntent.getBroadcast(this.getApplicationContext(), 0, startNotificationIntent, PendingIntent.FLAG_IMMUTABLE);
                AlarmManager startAlarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                //end course alarm setup
                String endNotificationTitle = "Course End Reminder";
                String endNotificationText = selectedCourse.getTitle() + " ends today.";

                Intent endNotificationIntent = new Intent(this.getApplicationContext(), AlarmReceiver.class);
                endNotificationIntent.putExtra("mNotificationTitle", endNotificationTitle);
                endNotificationIntent.putExtra("mNotificationContent", endNotificationText);

                PendingIntent endPendingIntent = PendingIntent.getBroadcast(this.getApplicationContext(), 1, endNotificationIntent, PendingIntent.FLAG_IMMUTABLE);
                AlarmManager endAlarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
                    //start alarm
                    Date startDate = dateFormat.parse(selectedCourse.getStartDate());
                    Calendar startCal = Calendar.getInstance();
                    assert startDate != null;
                    startCal.setTime(startDate);
                    startAlarmManager.set(AlarmManager.RTC_WAKEUP, startCal.getTimeInMillis(), startPendingIntent);
                    //end alarm
                    Date endDate = dateFormat.parse(selectedCourse.getEndDate());
                    Calendar endCal = Calendar.getInstance();
                    assert endDate != null;
                    endCal.setTime(endDate);
                    endAlarmManager.set(AlarmManager.RTC_WAKEUP, endCal.getTimeInMillis(), endPendingIntent);
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

    /**
     * @param view
     */
    public void onMentorEdit(View view) {
        Intent intent = new Intent(this, MentorEditorActivity.class);
        intent.putExtra(CourseDetailFragment.ARG_COURSE_ID, selectedCourse.getId());
        startActivity(intent);
    }

    /**
     * @param view
     */
    public void onAssessmentEdit(View view) {
        Intent intent = new Intent(this, AssessmentEditorActivity.class);
        intent.putExtra(CourseDetailFragment.ARG_COURSE_ID, selectedCourse.getId());
        startActivity(intent);
    }

    /**
     * @param view
     */
    public void onNoteEdit(View view) {
        Intent intent = new Intent(this, NoteEditorActivity.class);
        intent.putExtra(CourseDetailFragment.ARG_COURSE_ID, selectedCourse.getId());
        startActivity(intent);
    }
}
