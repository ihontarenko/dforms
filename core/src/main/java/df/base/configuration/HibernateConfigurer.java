package df.base.configuration;

import df.base.common.hibernate.support.ProtectDeleteListener;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.internal.SessionFactoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static java.util.Objects.requireNonNull;

@Component
public class HibernateConfigurer {

    @PersistenceUnit
    private EntityManagerFactory emf;

    @Autowired
    private ProtectDeleteListener listener;

    @PostConstruct
    protected void init() {
        SessionFactoryImpl sessionFactory = emf.unwrap(SessionFactoryImpl.class);
        EventListenerRegistry registry = sessionFactory
                .getServiceRegistry().getService(EventListenerRegistry.class);

        requireNonNull(registry)
                .getEventListenerGroup(EventType.PRE_DELETE).appendListener(listener);
    }

}
