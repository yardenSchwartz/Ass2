import org.hibernate.HibernateException;
import org.hibernate.Metamodel;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.metamodel.EntityType;

import java.util.Map;

public class Main {
    private static final SessionFactory ourSessionFactory;

    static {
        try {
            Configuration configuration = new Configuration();
            configuration.configure();

            ourSessionFactory = configuration.buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Session getSession() throws HibernateException {
        return ourSessionFactory.openSession();
    }

    public static void main(final String[] args) throws Exception {
        final Session session = getSession();
        try {
            System.out.println("querying all the managed entities...");
            final Metamodel metamodel = session.getSessionFactory().getMetamodel();
            for (EntityType<?> entityType : metamodel.getEntities()) {
                final String entityName = entityType.getName();
                final Query query = session.createQuery("from " + entityName);
                System.out.println("executing: " + query.getQueryString());
                for (Object o : query.list()) {
                    System.out.println("  " + o);
                }
            }
        } finally {
            session.close();
        }
//        Assignment.insertUser("ilan","1234","ilan1","kama1",
//                "8","3","1994");
//        Assignment.insertUser("ilan1","1234","ilan2","kama2",
//                "8","4","1994");
//        Assignment.insertUser("ilan2","1234","ilan3","kam2a",
//                "8","5","1994");
//        Assignment.insertUser("ilan3","1234","ilan4","ka2ma",
//                "8","6","1994");
//
//        Assignment.getNumberOfRegistredUsers(2);
        System.out.println(Assignment.isExistUsername("eden"));
//        System.out.println(ans + " should be null");

    }
}