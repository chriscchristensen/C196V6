package android.reserver.c196v6.Models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Term {
    private int id;
    private String title;
    private String startDate;
    private String endDate;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");

    /**
     * @param id
     * @param title
     * @param startDate
     * @param endDate
     */
    public Term(int id, String title, String startDate, String endDate) {
        this.id = id;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
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
     * @return
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
     * @return
     */
    public String getDates() {
        return startDate + " to " + endDate;
    }

    /**
     * @return
     */
    @Override
    public String toString() {
        return title + " (" + getDates() + ")";
    }

    /**
     * @return is it valid?
     */
    public boolean isValid() {
        if (title.isEmpty() || startDate.isEmpty() || endDate.isEmpty()) {
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
