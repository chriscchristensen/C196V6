package android.reserver.c196v6.Database.Daos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.reserver.c196v6.Database.DbContentProvider;
import android.reserver.c196v6.Models.Assessment;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class AssessmentDAO extends DbContentProvider implements AssessmentSchema, AssessmentDAOInterface {
    private Cursor cursor;
    private ContentValues initialValues;

    public AssessmentDAO(SQLiteDatabase db) {
        super(db);
    }

    /**
     * @param assessment
     * @return
     */
    public boolean addAssessment(Assessment assessment) {
        setContentValue(assessment);
        try {
            return super.insert(TABLE_ASSESSMENTS, getContentValue()) > 0;
        } catch (SQLiteConstraintException ex){
            return false;
        }
    }

    /**
     * @param courseId
     * @return
     */
    public List<Assessment> getAssessmentsByCourse(int courseId) {
        final String selectionArgs[] = { String.valueOf(courseId) };
        final String selection = ASSESSMENT_COURSE_ID + " = ?";
        List<Assessment> assessmentList = new ArrayList<>();

        cursor = super.query(TABLE_ASSESSMENTS, ASSESSMENTS_COLUMNS, selection,
                selectionArgs, ASSESSMENT_ID);

        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Assessment assessment = cursorToEntity(cursor);
                assessmentList.add(assessment);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return assessmentList;
    }

    /**
     * @param assessmentId
     * @return
     */
    public Assessment getAssessmentById(int assessmentId) {
        final String selectionArgs[] = { String.valueOf(assessmentId) };
        final String selection = ASSESSMENT_ID + " = ?";

        Assessment assessment = null;

        cursor = super.query(TABLE_ASSESSMENTS, ASSESSMENTS_COLUMNS, selection,
                selectionArgs, ASSESSMENT_ID);

        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                assessment = cursorToEntity(cursor);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return assessment;
    }

    /**
     * @return
     */
    public int getAssessmentCount() {
        List<Assessment> assessmentList = getAssessments();
        return assessmentList.size();
    }

    /**
     * @return
     */
    public List<Assessment> getAssessments() {
        List<Assessment> assessmentList = new ArrayList<>();

        cursor = super.query(TABLE_ASSESSMENTS, ASSESSMENTS_COLUMNS, null,
                null, ASSESSMENT_ID);

        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Assessment assessment = cursorToEntity(cursor);
                assessmentList.add(assessment);
                cursor.moveToNext();
            }
            cursor.close();
        }
        return assessmentList;
    }

    /**
     * @param assessment
     * @return
     */
    public boolean removeAssessment(Assessment assessment) {
        final String selectionArgs[] = { String.valueOf(assessment.getId()) };
        final String selection = ASSESSMENT_ID + " = ?";
        return super.delete(TABLE_ASSESSMENTS, selection, selectionArgs) > 0;
    }

    /**
     * @param assessment
     * @return
     */
    public boolean updateAssessment(Assessment assessment) {
        final String selectionArgs[] = { String.valueOf(assessment.getId()) };
        final String selection = ASSESSMENT_ID + " = ?";

        setContentValue(assessment);
        try {
            return super.update(TABLE_ASSESSMENTS, getContentValue(), selection, selectionArgs) > 0;
        } catch (SQLiteConstraintException ex){
            return false;
        }
    }

    /**
     * @param cursor
     * @return
     */
    protected Assessment cursorToEntity(Cursor cursor) {
        int assessmentIdIdx;
        int assessmentNameIdx;
        int assessmentTypeIdx;
        int assessmentDateIdx;
        int assessmentCourseIdIdx;
        int assessmentId = -1;
        String assessmentName = "";
        String assessmentType = "";
        String assessmentDate = "";
        int assessmentCourseId = -1;

        if (cursor != null) {
            if (cursor.getColumnIndex(ASSESSMENT_ID) != -1) {
                assessmentIdIdx = cursor.getColumnIndexOrThrow(ASSESSMENT_ID);
                assessmentId = cursor.getInt(assessmentIdIdx);
            }
            if (cursor.getColumnIndex(ASSESSMENT_NAME) != -1) {
                assessmentNameIdx = cursor.getColumnIndexOrThrow(ASSESSMENT_NAME);
                assessmentName = cursor.getString(assessmentNameIdx);
            }
            if (cursor.getColumnIndex(ASSESSMENT_DATE) != -1) {
                assessmentDateIdx = cursor.getColumnIndexOrThrow(ASSESSMENT_DATE);
                assessmentDate = cursor.getString(assessmentDateIdx);
            }
            if (cursor.getColumnIndex(ASSESSMENT_TYPE) != -1) {
                assessmentTypeIdx = cursor.getColumnIndexOrThrow(ASSESSMENT_TYPE);
                assessmentType = cursor.getString(assessmentTypeIdx);
            }
            if (cursor.getColumnIndex(ASSESSMENT_COURSE_ID) != -1) {
                assessmentCourseIdIdx = cursor.getColumnIndexOrThrow(ASSESSMENT_COURSE_ID);
                assessmentCourseId = cursor.getInt(assessmentCourseIdIdx);
            }
        }
        return new Assessment(assessmentId, assessmentName, assessmentType, assessmentDate, assessmentCourseId);
    }

    /**
     * @param assessment
     */
    private void setContentValue(Assessment assessment) {
        initialValues = new ContentValues();
        initialValues.put(ASSESSMENT_ID, assessment.getId());
        initialValues.put(ASSESSMENT_NAME, assessment.getName());
        initialValues.put(ASSESSMENT_DATE, assessment.getDate());
        initialValues.put(ASSESSMENT_TYPE, assessment.getType());
        initialValues.put(ASSESSMENT_COURSE_ID, assessment.getCourseId());
    }

    /**
     * @return
     */
    private ContentValues getContentValue() {
        return initialValues;
    }
}
