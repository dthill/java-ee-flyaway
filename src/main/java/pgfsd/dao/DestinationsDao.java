package pgfsd.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import pgfsd.entities.Destination;
import pgfsd.db.DBUtil;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Arrays;
import java.util.List;

public class DestinationsDao {
    public boolean addDestination(Destination destination){
        SessionFactory sessionFactory = DBUtil.sessionFactory;
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.merge(destination);
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

    public List<Destination> getAllDestinations(){
        SessionFactory factory = DBUtil.sessionFactory;
        Session session = factory.openSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Destination> criteriaQuery = criteriaBuilder.createQuery(Destination.class);
        Root<Destination> root = criteriaQuery.from(Destination.class);
        List<Destination> allDestinations = session
                .createQuery(criteriaQuery.select(root))
                .getResultList();
        session.close();
        return  allDestinations;
    }

    public boolean deleteDestination(Destination destination){
        SessionFactory sessionFactory = DBUtil.sessionFactory;
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.delete(destination);
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
