package android.reserver.c196v6.Models;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Assessment {
    private int id;
    private String name;
    private String type;
    private String date;
    private int courseId;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");

    /**
     * @param id
     * @param name
     * @param type
     * @param date
     * @param courseId
     */
    public Assessment(int id, String name, String type, String date, int courseId) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.date = date;
        this.courseId = courseId;
    }

    /**
     * @return id
     */
    public int getId() {
        return this.id;
    }

    /**
     * @return name
     */
    public String getName() {
        return this.name;
    }

    /**
     * @return type
     */
    public String getType() {
        return this.type;
    }

    /**
     * @return date
     */
    public String getDate() {
        return this.date;
    }

    /**
     * @return associated course ID
     */
    public int getCourseId() {
        return this.courseId;
    }

    /**
     * @return string
     */
    @Override
    public String toString() {
        return this.name + " (" + this.type + ")";
    }

    /**
     * @return isValid
     */
    public boolean isValid() {
        if (name.isEmpty() || type.isEmpty() || date.isEmpty()) {
            return false;
        }
        try {
            dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
