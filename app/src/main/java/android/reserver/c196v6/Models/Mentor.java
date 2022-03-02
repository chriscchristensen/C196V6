package android.reserver.c196v6.Models;

public class Mentor {
    private int id;
    private String name;
    private String phone;
    private String email;
    private int courseId;

    /**
     * @param id
     * @param name
     * @param phone
     * @param email
     * @param courseId
     */
    public Mentor(int id, String name, String phone, String email, int courseId) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.courseId = courseId;
    }

    /**
     * @return id
     */
    public int getId() { return this.id; }

    /**
     * @return email
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * @return name
     */
    public String getName() {
        return this.name;
    }

    /**
     * @return phone
     */
    public String getPhone() {
        return this.phone;
    }

    /**
     * @return courseId
     */
    public int getCourseId() {
        return this.courseId;
    }

    /**
     * @return
     */
    @Override
    public String toString() {
        return this.name;
    }

    /**
     * @return
     */
    public boolean isValid() {
        if (name.isEmpty() || email.isEmpty() || phone.isEmpty()) {
            return false;
        }
        return true;
    }
}
