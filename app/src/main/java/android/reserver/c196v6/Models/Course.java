package android.reserver.c196v6.Models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Course {
    private final int id;
    private final String title;
    private final String startDate;
    private final String endDate;
    private final String status;
    private final int termId;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");

    /**
     * @param id
     * @param title
     * @param startDate
     * @param endDate
     * @param status
     * @param termId
     */
    public Course(int id, String title, String startDate, String endDate,
                  String status, int termId) {
        this.id = id;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.termId = termId;
    }

    /**
     * @return id
     */
    public int getId() {
        return this.id;
    }

    /**
     * @return title
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * @return starDate
     */
    public String getStartDate() {
        return this.startDate;
    }

    /**
     * @return endDate
     */
    public String getEndDate() {
        return this.endDate;
    }

    /**
     * @return status
     */
    public String getStatus() {
        return this.status;
    }

    /**
     * @return
     */
    public String getDates() {
        return startDate + " to " + endDate;
    }

    /**
     * @return termId
     */
    public int getTermId() {
        return this.termId;
    }

    /**
     * @return
     */
    @Override
    public String toString() {
        return title + " (" + getDates() + ")";
    }

    /**
     * @return
     */
    public boolean isValid() {
        if (title.isEmpty() || startDate.isEmpty() || endDate.isEmpty() || status.isEmpty()) {
            return false;
        }
        try {
            Date start = dateFormat.parse(startDate);
            Date end = dateFormat.parse(endDate);
            if (!start.before(end)) {
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
