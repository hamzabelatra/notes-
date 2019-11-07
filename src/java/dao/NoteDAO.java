package dao;

import entities.HibernateUtil;
import entities.Note;
import java.util.Date;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class NoteDAO {

    public static boolean add(Note e) {
        try {
            SessionFactory sf = HibernateUtil.getSessionFactory();
            Session s = sf.openSession();
            Transaction t = s.beginTransaction();
            s.save(e);
            t.commit();
            s.close();
            return true;
        } catch (Exception ex) {
            System.out.println("NoteDAO.add() -- > " + ex.getMessage());
            return false;
        }
    }

    public static List<Note> getAllNotes(String email) {
        try {
            SessionFactory sf = HibernateUtil.getSessionFactory();
            Session s = sf.openSession();
            Query q = s.createQuery("from Note where user.email  = :email  order by updated desc");
            q.setString("email", email);
            List<Note> notes = q.list();
            s.close();
            return notes;
        } catch (Exception ex) {
            System.out.println("NoteDAO.getAllNotes() -- > " + ex.getMessage());
            return null;
        }
    }

    public static Note getNote(int id) {
        try {
            SessionFactory sf = HibernateUtil.getSessionFactory();
            Session s = sf.openSession();
            Note note = (Note) s.get(Note.class, id);
            s.close();
            return note;
        } catch (Exception ex) {
            System.out.println("NoteDAO.getNote() -- > " + ex.getMessage());
            return null;
        }
    }

    public static boolean update(int id, String title, String text) {
        try {
            SessionFactory sf = HibernateUtil.getSessionFactory();
            Session s = sf.openSession();
            s.beginTransaction();
            Note note = (Note) s.get(Note.class, id);
            note.setTitle(title);
            note.setText(text);
            note.setUpdated(new Date());
            
            s.getTransaction().commit();
            s.close();
            return true;
        } catch (Exception ex) {
            System.out.println("NoteDAO.update() -- > " + ex.getMessage());
            return false;
        }
    }

    public static boolean delete(int id) {
        try {
            SessionFactory sf = HibernateUtil.getSessionFactory();
            Session s = sf.openSession();
            s.beginTransaction();
            Note e = (Note) s.load(Note.class, id);
            s.delete(e);
            s.getTransaction().commit();
            s.close();
            return true;
        } catch (Exception ex) {
            System.out.println("NoteDAO.delete() -- > " + ex.getMessage());
            return false;
        }
    }

    public static List<Note> searchNotes(String email, String pattern) {
        try {
            SessionFactory sf = HibernateUtil.getSessionFactory();
            Session s = sf.openSession();

            Query q = s.createQuery
               ("from Note where user.email = :email  and  (upper(title) like :pattern or upper(text) like :pattern)");
            q.setString("email", email);
            q.setString("pattern", "%" +  pattern.toUpperCase() + "%");
            List<Note> notes = q.list();
            s.close();
            return notes;  
        } catch (Exception ex) {
            System.out.println("NoteDAO.searchNotes() -- > " + ex.getMessage());
            return null;
        }
    }
}
