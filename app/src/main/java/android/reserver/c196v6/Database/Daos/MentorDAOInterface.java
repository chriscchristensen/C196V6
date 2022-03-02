package android.reserver.c196v6.Database.Daos;

import android.reserver.c196v6.Models.Mentor;

import java.util.List;

public interface MentorDAOInterface {

    boolean addMentor(Mentor mentor);

    Mentor getMentorById(int mentorId);

    List<Mentor> getMentorsByCourse(int courseId);

    int getMentorCount();

    List<Mentor> getMentors();

    boolean removeMentor(Mentor mentor);
    boolean updateMentor(Mentor mentor);
}
