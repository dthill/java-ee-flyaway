package pgfsd.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import pgfsd.db.DBUtil;
import pgfsd.entities.Flight;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Arrays;
import java.util.List;

public class FlightDao {
    public boolean addFlight(Flight flight){
        SessionFactory sessionFactory = DBUtil.sessionFactory;
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.merge(flight);
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

    public List<Flight> getAllFlights(){
        SessionFactory factory = DBUtil.sessionFactory;
        Session session = factory.openSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Flight> criteriaQuery = criteriaBuilder.createQuery(Flight.class);
        Root<Flight> root = criteriaQuery.from(Flight.class);
        List<Flight> allFlights = session
                .createQuery(criteriaQuery.select(root))
                .getResultList();
        session.close();
        return  allFlights;
    }

    public boolean deleteFlight(Flight flight){
        SessionFactory sessionFactory = DBUtil.sessionFactory;
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.delete(flight);
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
