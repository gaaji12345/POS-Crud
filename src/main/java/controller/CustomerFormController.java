package controller;

import entity.Customer;
import javafx.event.ActionEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import repo.CustomerRepo;

public class CustomerFormController {
    public TextField txtID;
    public TextField txtName;
    public TextField txtAddress;
    public TextField txtTel;
    public TableView tblCustomer;

    public void save_OnAction(ActionEvent actionEvent) {
        Customer customer=new Customer(txtID.getText(),txtName.getText(),txtAddress.getText(),Integer.parseInt(txtTel.getText()));
        CustomerRepo customerRepo=new CustomerRepo();
        String  Cid=customerRepo.saveCustomer(customer);
        System.out.println("Save Customer Id"+Cid);
    }

    public void update_OnActon(ActionEvent actionEvent) {
    }

    public void delete_OnAction(ActionEvent actionEvent) {
    }
}
