package pgfsd.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import pgfsd.db.DBUtil;
import pgfsd.entities.Booking;
import pgfsd.entities.User;


import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BookingDao {
    public boolean addBooking(Booking booking) {
        SessionFactory sessionFactory = DBUtil.sessionFactory;
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.merge(booking);
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

    public List<Booking> getAllBookings(User user) {
        SessionFactory factory = DBUtil.sessionFactory;
        Session session = factory.openSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Booking> criteriaQuery = criteriaBuilder.createQuery(Booking.class);
        Root<Booking> root = criteriaQuery.from(Booking.class);
        List<Booking> bookings = new ArrayList<>();
        try {
            bookings = session
                    .createQuery(criteriaQuery.select(root).where(
                            criteriaBuilder.equal(root.get("user"), user)))
                    .getResultList();
        }
        catch (Exception e){
            System.out.println("error retrieving all bookings for user: " + user);
            session.close();
        }
        session.close();
        return bookings;
    }

    public boolean deleteBooking(Booking booking) {
        SessionFactory sessionFactory = DBUtil.sessionFactory;
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.delete(booking);
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
}
