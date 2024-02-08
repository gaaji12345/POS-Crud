package controller;

import entity.Customer;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import repo.CustomerRepo;
import tm.CustomerTm;

public class CustomerFormController {
    public TextField txtID;
    public TextField txtName;
    public TextField txtAddress;
    public TextField txtTel;
    public TableView <CustomerTm> tblCustomer;

    public void save_OnAction(ActionEvent actionEvent) {
        Customer customer=new Customer(txtID.getText(),txtName.getText(),txtAddress.getText(),Integer.parseInt(txtTel.getText()));
        CustomerRepo customerRepo=new CustomerRepo();
        String  Cid=customerRepo.saveCustomer(customer);
        System.out.println("Save Customer Id"+Cid);
    }

    public void update_OnActon(ActionEvent actionEvent) {
   CustomerRepo customerRepo=new CustomerRepo();
       Customer  customer = customerRepo.getCustomer(txtID.getText());
           customer.setAddress(txtAddress.getText());
       customer.setName(txtName.getText());
       customer.setTel(Integer.parseInt(txtTel.getText()));
      customer.setId(txtID.getText());
        boolean isUpdated = customerRepo.updateCustomer(customer);
        if (isUpdated) {
           // System.out.println("Customer Updated!");
            new Alert(Alert.AlertType.CONFIRMATION,"IS UPDATED").show();
      } else {
            //System.out.println("Customer Update Failed!");
            new Alert(Alert.AlertType.ERROR,"CAN'T UPDATED").show();
        }
        LoadAllCustomerTable();

    }

    private void LoadAllCustomerTable() {

    }



    public void delete_OnAction(ActionEvent actionEvent) {
       CustomerRepo customerRepo=new CustomerRepo();
        Customer  customer = customerRepo.getCustomer(txtID.getText());
        boolean isDeleted = customerRepo.deleteCustomer(customer);
        if (isDeleted) {
            System.out.println("Customer Deleted!");
        } else {
            System.out.println("Customer Deletion Failed!");
        }
    }
}
