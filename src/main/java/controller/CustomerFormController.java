package controller;

import entity.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import repo.CustomerRepo;
import tm.CustomerTm;

import java.util.List;

public class CustomerFormController {
    public TextField txtID;
    public TextField txtName;
    public TextField txtAddress;
    public TextField txtTel;
    public TableView <CustomerTm> tblCustomer;
    public Button btnSave;
    public Button btnDelete;

    public void initialize() {
        tblCustomer.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("id"));
        tblCustomer.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblCustomer.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("address"));
        tblCustomer.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("tel"));


        LoadAllCustomerTable();
        initUI();

    }

    private void initUI() {
        txtID.clear();
        txtName.clear();
        txtAddress.clear();
        txtTel.clear();
        txtID.setDisable(true);
        txtName.setDisable(true);
        txtAddress.setDisable(true);
        txtTel.setDisable(true);
        txtID.setEditable(false);
        btnSave.setDisable(true);
        btnDelete.setDisable(true);
    }

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
        try {
           CustomerRepo customerRepository = new CustomerRepo();
            ObservableList<CustomerTm> obList = FXCollections.observableArrayList();
            List<Customer> cusList = customerRepository.getAll();

            for (Customer customer : cusList) {
                obList.add(new CustomerTm(
                        customer.getId(),
                        customer.getName(),
                        customer.getAddress(),
                        customer.getTel()
                ));
            }
            tblCustomer.setItems(obList);
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, "SQL Error!").show();
        }



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

    public void txtSearch_OnAction(ActionEvent actionEvent) {

    }
}
