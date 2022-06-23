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
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class FlightDao {
    public boolean addFlight(Flight flight) {
        SessionFactory sessionFactory = DBUtil.sessionFactory;
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.merge(flight);
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

    public List<Flight> getAllFlights() {
        SessionFactory factory = DBUtil.sessionFactory;
        Session session = factory.openSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Flight> criteriaQuery = criteriaBuilder.createQuery(Flight.class);
        Root<Flight> root = criteriaQuery.from(Flight.class);
        List<Flight> allFlights = session
                .createQuery(criteriaQuery.select(root))
                .getResultList();
        session.close();
        return allFlights;
    }

    public boolean deleteFlight(Flight flight) {
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

    public List<Flight> searchFlights(Flight flight){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(flight.getDepartureDate());
        calendar.add(Calendar.DATE, 1);
        Date nextDay = calendar.getTime();
        SessionFactory factory = DBUtil.sessionFactory;
        Session session = factory.openSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Flight> criteriaQuery = criteriaBuilder.createQuery(Flight.class);
        Root<Flight> root = criteriaQuery.from(Flight.class);
        List<Flight> matchingFlights = session
                .createQuery(criteriaQuery.select(root).where(criteriaBuilder.and(
                        criteriaBuilder.equal(root.get("departureDestination"), flight.getDepartureDestination()),
                        criteriaBuilder.equal(root.get("arrivalDestination"), flight.getArrivalDestination()),
                        criteriaBuilder.between(root.get("departureDate"),flight.getDepartureDate(), nextDay)
                )))
                .getResultList();
        session.close();
        return matchingFlights;
    }

    public Flight getFlightById(String id) {
        SessionFactory factory = DBUtil.sessionFactory;
        Session session = factory.openSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Flight> criteriaQuery = criteriaBuilder.createQuery(Flight.class);
        Root<Flight> root = criteriaQuery.from(Flight.class);
        Flight flight = null;
        try{
            flight = session
                    .createQuery(criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("id"), id)))
                    .getSingleResult();
        } catch (Exception e){
            System.out.println("No flight found for flight id booking");
            System.out.println(e.getMessage());
        }
        session.close();
        return flight;
    }
}
