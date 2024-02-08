package repo.impl;

import entity.Item;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.SessionFactoryConfig;

import java.util.List;

public class ItemIMPL {

    public boolean save(Item entity) {
        Session session = SessionFactoryConfig.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        session.save(entity);

        transaction.commit();
        session.close();

        return true;
    }

    public boolean update(Item entity) {
        Session session = SessionFactoryConfig.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        session.update(entity);

        transaction.commit();
        session.close();

        return true;
    }
    public boolean delete(Item entity) {
        Session session = SessionFactoryConfig.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        session.delete(entity);

        transaction.commit();
        session.close();

        return true;
    }

    public Item search(String code) {
        Session session = SessionFactoryConfig.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

         Item item=session.get(Item.class,code);
        transaction.commit();
        session.close();

        return item;
    }

    public List<Item> getAll() {
        Session session = SessionFactoryConfig.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        List<Item> allItem = session.createQuery("from  Item").list();

        transaction.commit();
        session.close();

        return allItem;
    }

}
