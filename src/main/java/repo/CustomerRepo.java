package repo;

import entity.Customer;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.SessionFactoryConfig;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class CustomerRepo {
    private Session session;

    public CustomerRepo(){
        session = SessionFactoryConfig.getInstance().getSession();
    }
    public Customer getCustomer(String id){
        try {
            return session.get(Customer.class,id);
        }catch (Exception e){
            e.printStackTrace();
            throw e;
        }
    }

    public String saveCustomer(Customer customer) {
        session = SessionFactoryConfig.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        try {
            String id = (String) session.save(customer);
            transaction.commit();
            session.close();
            return id;
        } catch (Exception e) {
            transaction.rollback();
            session.close();
            e.printStackTrace();
            return "false";
        }
    }

    public boolean updateCustomer(Customer customer){
        Transaction transaction = session.beginTransaction();
        try {
            session.update(customer);
            return true;
        }catch (Exception e){
            transaction.rollback();
            session.close();
            e.printStackTrace();
            return false;
        }

    }

    public boolean deleteCustomer(Customer customer){
        Transaction transaction = session.beginTransaction();
        try{
            session.delete(customer);
            transaction.commit();
            session.close();
            return true;

        }catch (Exception e){
            transaction.rollback();
            session.close();
            e.printStackTrace();
            return false;
        }
    }
    public List<Customer> getAll(){
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Customer> cq = cb.createQuery(Customer.class);
        Root<Customer> rootEntry = cq.from(Customer.class);
        CriteriaQuery<Customer> all = cq.select(rootEntry);

        TypedQuery<Customer> allQuery = session.createQuery(all);
        return allQuery.getResultList();


    }
}


