package repo;

import entity.Customer;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.SessionFactoryConfig;

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

}
