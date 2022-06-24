package pgfsd.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import pgfsd.db.DBUtil;
import pgfsd.entities.User;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Arrays;
import java.util.List;

public class UserDao {
    public boolean addUser(User user) {
        user.setEmail(user.getEmail().toLowerCase());
        SessionFactory sessionFactory = DBUtil.sessionFactory;
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.merge(user);
            transaction.commit();
            session.close();
            return true;
        } catch (HibernateException e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
            if (transaction != null) {
                transaction.rollback();
            }
            session.close();
            return false;
        }
    }

    public List<User> getAllUsers() {
        SessionFactory factory = DBUtil.sessionFactory;
        Session session = factory.openSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> root = criteriaQuery.from(User.class);
        List<User> allUsers = session
                .createQuery(criteriaQuery.select(root))
                .getResultList();
        session.close();
        return allUsers;
    }

    public boolean deleteUser(User user) {
        user.setEmail(user.getEmail().toLowerCase());
        SessionFactory sessionFactory = DBUtil.sessionFactory;
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.delete(user);
            transaction.commit();
            session.close();
            return true;
        } catch (Exception e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
            if (transaction != null) {
                transaction.rollback();
            }
            session.close();
            return false;
        }
    }

    public User checkPassword(User user) {
        user.setEmail(user.getEmail().toLowerCase());
        SessionFactory factory = DBUtil.sessionFactory;
        Session session = factory.openSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
        Root<User> root = criteriaQuery.from(User.class);
        User matchingUser = null;
        try{
            matchingUser = session
                    .createQuery(criteriaQuery.select(root).where(
                            criteriaBuilder.and(
                                    criteriaBuilder.equal(root.get("email"), user.getEmail()),
                                    criteriaBuilder.equal(root.get("password"), user.getPassword())
                            )
                    ))
                    .getSingleResult();
        } catch (Exception e){
            System.out.println("No single user found matching credentials");
            session.close();
            return null;
        }
        session.close();
        return matchingUser;
    }

    public boolean registerNewUser(User user){
        user.setEmail(user.getEmail().toLowerCase());
        if(getAllUsers().size() == 0){
            user.setAdmin(true);
        }
        SessionFactory sessionFactory = DBUtil.sessionFactory;
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.save(user);
            transaction.commit();
            session.close();
            return true;
        } catch (HibernateException e) {
            System.out.println(Arrays.toString(e.getStackTrace()));
            if (transaction != null) {
                transaction.rollback();
            }
            session.close();
            return false;
        }
    }
}
