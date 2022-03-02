package android.reserver.c196v6.Database.Daos;

import android.reserver.c196v6.Models.Term;

import java.util.List;

public interface TermDAOInterface {

    boolean addTerm(Term term);

    Term getTermById(int termId);

    int getTermCount();

    List<Term> getTerms();

    boolean removeTerm(Term term);
    boolean updateTerm(Term term);
}
