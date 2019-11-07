package dao;

import entities.HibernateUtil;
import entities.User;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class UserDAO {

    public static boolean register(User u) {

        try {
            SessionFactory sf = HibernateUtil.getSessionFactory();
            Session s = sf.openSession();
            Transaction t = s.beginTransaction();
            s.save(u);
            t.commit();
            s.close();
            return true;
        } catch (Exception ex) {
            System.out.println("UserDAO.register() -- > " + ex.getMessage());
            return false;
        }
    }

    public static User login(String email, String password) {

        try {
            SessionFactory sf = HibernateUtil.getSessionFactory();
            Session s = sf.openSession();
            User u = (User) s.get(User.class, email);
            if (u != null) {
                if (u.getPassword().equals(password)) {
                    return u;
                } else {
                    return null;
                }
            }
            s.close();
            return null;
        } catch (Exception ex) {
            System.out.println("UserDAO.login() -- > " + ex.getMessage());
            return null;
        }
    }

    //  returns true on success, false on failure 
    public static boolean changePassword(String email, String password, String newPassword) {

        try {
            SessionFactory sf = HibernateUtil.getSessionFactory();
            Session s = sf.openSession();
            Transaction t = s.beginTransaction();
            User u = (User) s.get(User.class, email);
            if (u == null) {
                return false;
            }
            if (!u.getPassword().equals(password)) {
                return false;
            }
            u.setPassword(newPassword);

            t.commit();
            s.close();
            return true;
        } catch (Exception ex) {
            System.out.println("UserDAO.changePassword() -- > " + ex.getMessage());
            return false;
        }
    }

    public static User getUser(String email) {
        try {
            SessionFactory sf = HibernateUtil.getSessionFactory();
            Session s = sf.openSession();
            User u = (User) s.get(User.class, email);
            s.close();
            return u;
        } catch (Exception ex) {
            System.out.println("UserDAO.getUser() -- > " + ex.getMessage());
            return null;
        }
    }
}
