package pgfsd.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import pgfsd.db.DBUtil;
import pgfsd.entities.Airline;


import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Arrays;
import java.util.List;

public class AirlineDao {
    public boolean addAirline(Airline airline){
        SessionFactory sessionFactory = DBUtil.sessionFactory;
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.merge(airline);
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

    public List<Airline> getAllAirlines(){
        SessionFactory factory = DBUtil.sessionFactory;
        Session session = factory.openSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Airline> criteriaQuery = criteriaBuilder.createQuery(Airline.class);
        Root<Airline> root = criteriaQuery.from(Airline.class);
        List<Airline> allDestinations = session
                .createQuery(criteriaQuery.select(root))
                .getResultList();
        session.close();
        return  allDestinations;
    }

    public boolean deleteAirline(Airline airline){
        SessionFactory sessionFactory = DBUtil.sessionFactory;
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.delete(airline);
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
