package pgfsd.db;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;


public class DBUtil {

    public static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder()
                    .configure("hibernate.cfg.xml")
                    .build();
            Metadata metaData = new MetadataSources(standardRegistry)
                    .getMetadataBuilder()
                    .build();
            return metaData
                    .getSessionFactoryBuilder()
                    .build();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ExceptionInInitializerError(e);
        }
    }


}
