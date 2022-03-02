package android.reserver.c196v6.Database.Daos;

import android.reserver.c196v6.Models.Note;

import java.util.List;

public interface NoteDAOInterface {

    boolean addNote(Note note);

    Note getNoteById(int noteId);

    List<Note> getNotesByCourse(int courseId);

    int getNoteCount();

    List<Note> getNotes();

    boolean removeNote(Note note);
    boolean updateNote(Note note);
}
