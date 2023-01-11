package io.startform.parent.library.jpa;

import io.startform.parent.library.function.TriConsumer;
import io.startform.parent.library.structure.Pair;
import org.hibernate.*;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

import static java.lang.String.format;
import static java.util.Arrays.asList;

@SuppressWarnings({"all"})
abstract public class AbstractRepository<E> {

    @Autowired
    protected static SessionFactory factory;

    protected Session session() {
        Session session = null;

        try {
            session = factory.getCurrentSession();
        } catch (HibernateException e) {
            try {
                session = factory.openSession();
            } catch (HibernateException ignore) {
            }
        } finally {
            if (session == null) {
                throw new HibernateException("COULD NOT OBTAIN SESSION");
            }
        }

        return session;
    }

    public <T> T execute(Function<Session, T> function) {
        Session     session     = session();
        Transaction transaction = session.getTransaction();
        T           result      = null;

        try {
            transaction.begin();
            result = function.apply(session);
            transaction.commit();
        } catch (TransactionException exception) {
            transaction.rollback();
        } finally {
            session.close();
        }

        return result;
    }

    public void execute(Consumer<Session> consumer) {
        execute(em -> {
            consumer.accept(em);
            return null;
        });
    }

    public void persist(E entity) {
        execute(session -> {
            session.persist(entity);
        });
    }

    public void save(E entity) {
        session().save(entity);
    }

    public void merge(E entity) {
        session().merge(entity);
    }

    public void saveOrUpdate(E entity) {
        session().saveOrUpdate(entity);
    }

    public void flush() {
        session().flush();
    }

    public void remove(E entity) {
        session().delete(entity);
    }

    public void remove(int id) {
        remove(get(id));
    }

    public <T extends Serializable> E get(T id) {
        return execute(session -> {
            return session.get(getEntityClassType(), id);
        });
    }

    public void refresh(E entity) {
        execute(session -> {
            session.refresh(entity);
        });
    }

    public CriteriaBuilder criteriaBuilder() {
        return session().getCriteriaBuilder();
    }

    public List<E> getList(CriteriaQuery<E> criteria) {
        return execute(session -> {
            return createQuery(criteria).getResultList();
        });
    }

    public List<E> getList(String column, Object value) {
        return getList(criteriaQuery(column, value));
    }

    public List<E> getList() {
        return createQuery().getResultList();
    }

    public E uniqueResult(CriteriaQuery<E> criteria) {
        return createQuery(criteria).uniqueResult();
    }

    public E uniqueResult() {
        return createQuery().uniqueResult();
    }

    public <T extends Serializable> List<E> getList(T... ids) {
        return getList(asList(ids));
    }

    public <T extends Serializable> List<E> getList(List<T> ids) {
        return getMultiAccessor(getEntityClassType()).multiLoad(cleanIds(ids));
    }

    public MultiIdentifierLoadAccess getMultiAccessor(Class<E> reflection) {
        return session().byMultipleIds(reflection);
    }

    public Query<E> createQuery() {
        return session().createQuery(format("from %s", getEntityClassType().getSimpleName()));
    }

    public Query<E> createQuery(CriteriaQuery<E> criteria) {
        return session().createQuery(criteria);
    }

    public Query<E> createQuery(QueryConsumer<E> consumer) {
        return createQuery(criteriaQuery(consumer));
    }

    public CriteriaQuery<E> criteriaQuery(String column, Object value) {
        return criteriaQuery((builder, query, root) -> {
            query.where(builder.equal(root.get(column), value));
        });
    }

    public CriteriaQuery<E> criteriaQuery(QueryConsumer<E> consumer) {
        CriteriaBuilder  builder = criteriaBuilder();
        CriteriaQuery<E> query   = builder.createQuery(getEntityClassType());
        Root<E>          root    = query.from(getEntityClassType());

        query.select(root);

        consumer.accept(builder, query, root);

        return query;
    }

    public E getFirst(String column, String value) {
        return uniqueResult(criteriaQuery(column, value));
    }

    public List<E> getList(String column, String value) {
        return getList(criteriaQuery(column, value));
    }

    public E getFirst(QueryConsumer<E> consumer) {
        return uniqueResult(criteriaQuery(consumer));
    }

    public List<E> getList(QueryConsumer<E> consumer) {
        return getList(criteriaQuery(consumer));
    }

    public QueryConsumer<E> getConsumer(List<Pair<String, ?>> pairs) {
        return (builder, query, root) -> {
            List<Predicate> predicates = new ArrayList<>();

            for (Pair<String, ?> pair : pairs) {
                predicates.add(builder.equal(root.get(pair.getValueA()), pair.getValueB()));
            }

            query.where(builder.and(predicates.toArray(new Predicate[]{})));
        };
    }

    public CriteriaQuery<E> getQuery(List<Pair<String, ?>> pairs) {
        return criteriaQuery(getConsumer(pairs));
    }

    public CriteriaQuery<E> getQuery(Pair<String, ?>... pairs) {
        return getQuery(asList(pairs));
    }

    public <T extends Serializable> List<T> cleanIds(List<T> ids) {
        return cleanIds(ids, Objects::isNull);
    }

    public <T extends Serializable> List<T> cleanIds(List<T> ids, java.util.function.Predicate<T> predicate) {
        ids.removeIf(predicate);

        return ids;
    }

    abstract public Class<E> getEntityClassType();

    @FunctionalInterface
    public interface QueryConsumer<E> extends TriConsumer<CriteriaBuilder, CriteriaQuery<E>, Root<E>> {

        default QueryConsumer<E> before(QueryConsumer<E> consumer) {
            return (a, b, c) -> {
                consumer.accept(a, b, c);
                accept(a, b, c);
            };
        }

        default QueryConsumer<E> after(QueryConsumer<E> consumer) {
            return (a, b, c) -> {
                accept(a, b, c);
                consumer.accept(a, b, c);
            };
        }

    }

}
