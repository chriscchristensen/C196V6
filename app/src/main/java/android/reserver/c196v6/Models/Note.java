package android.reserver.c196v6.Models;

public class Note {
    private int id;
    private String title;
    private String text;
    private int courseId;

    /**
     * @param id
     * @param title
     * @param text
     * @param courseId
     */
    public Note(int id, String title, String text, int courseId) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.courseId = courseId;
    }

    /**
     * @return id
     */
    public int getId() { return this.id; }

    /**
     * @return title
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * @return text
     */
    public String getText() {
        return this.text;
    }

    /**
     * @return associated course ID
     */
    public int getCourseId() {
        return this.courseId;
    }

    /**
     * @return string repr
     */
    @Override
    public String toString() {
        return this.title;
    }

    /**
     * @return is it valid?
     */
    public boolean isValid() {
        if (title.isEmpty() || text.isEmpty()) {
            return false;
        }
        return true;
    }
}
