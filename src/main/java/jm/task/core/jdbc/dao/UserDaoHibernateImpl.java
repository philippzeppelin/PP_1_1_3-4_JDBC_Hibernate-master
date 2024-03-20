package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDaoHibernateImpl implements UserDao {
    public static final Logger logger = Logger.getLogger(UserDaoHibernateImpl.class.getName());
    public static final SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            String sqlQuery = "CREATE TABLE IF NOT EXISTS User(id BIGINT PRIMARY KEY AUTO_INCREMENT, " + "name VARCHAR(40), " + "lastname VARCHAR(40), " + "age INT(120));";

            session.beginTransaction();
            session.createSQLQuery(sqlQuery).executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            logger.log(Level.SEVERE, "Произошла ошибка в createUsersTable: " + e);
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            String sqlQuery = "DROP TABLE IF EXISTS User;";

            session.beginTransaction();
            session.createSQLQuery(sqlQuery).executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            logger.log(Level.SEVERE, "Произошла ошибка в dropUsersTable: " + e);
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = sessionFactory.openSession()) {
            User user = new User(name, lastName, age);

            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            logger.log(Level.SEVERE, "Произошла ошибка в saveUser", e);
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = sessionFactory.openSession()) {
            User user = session.get(User.class, id);

            session.beginTransaction();
            session.delete(user);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            logger.log(Level.SEVERE, "Произошла ошибка в removeUserById", e);
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            users = session.createQuery("FROM User").getResultList();

            session.getTransaction().commit();
        } catch (HibernateException e) {
            logger.log(Level.SEVERE, "Произошла ошибка в getAllUsers", e);
        }

        return users;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            session.createQuery("DELETE FROM User").executeUpdate();

            session.getTransaction().commit();
        } catch (HibernateException e) {
            logger.log(Level.SEVERE, "Произошла ошибка в cleanUsersTable", e);
        }
    }
}