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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

        initUI();
        LoadAllCustomerTable();


    }

    private void initUI() {
        txtID.clear();
        txtName.clear();
        txtAddress.clear();
        txtTel.clear();
       // txtID.setDisable(true);
        //txtName.setDisable(true);
        //txtAddress.setDisable(true);
        //txtTel.setDisable(true);
      //  txtID.setEditable(false);
        //btnSave.setDisable(true);
        //btnDelete.setDisable(true);
    }

    public void save_OnAction(ActionEvent actionEvent) {
        boolean isValidate = validateCustomer();
        if (isValidate) {
            Customer customer = new Customer(txtID.getText(), txtName.getText(), txtAddress.getText(), Integer.parseInt(txtTel.getText()));
            CustomerRepo customerRepo = new CustomerRepo();
            String Cid = customerRepo.saveCustomer(customer);
            System.out.println("Save Customer Id" + Cid);
        } else {
          new Alert(Alert.AlertType.WARNING,"INPUT RIGHT DETAILS..!").show();
        }
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

    private boolean validateCustomer() {
        String id_value=txtID.getText();
        Pattern complie=Pattern.compile("[C][0-9]{3}");
        Matcher matcher=complie.matcher(id_value);
        boolean matches=matcher.matches();
        if (!matches){
            new Alert(Alert.AlertType.ERROR,"INVALID CUSTOMER ID").show();
            return  false;
        }
        String address=txtAddress.getText();
        Pattern compile1 = Pattern.compile("[A-Za-z]{4,}");
        Matcher matcher1=compile1.matcher(address);
        boolean isAddress=matcher1.matches();
        if (!isAddress){
            new Alert(Alert.AlertType.ERROR,"WRONG ADDRSS TYPE").show();
        }
        String nameText=txtName.getText();
        boolean isnameValid= Pattern.compile("[A-Za-z]{3,}").matcher(nameText).matches();

        if (!isnameValid){
            new Alert(Alert.AlertType.ERROR,"WRONG NAME TYPE").show();
        }
        String telx=txtTel.getText();
        boolean isTel=Pattern.compile("[0]\\d{9,}").matcher(telx).matches();
          if (!isTel){
              new Alert(Alert.AlertType.ERROR,"WRONG NUMBER TYPE").show();
          }

        return true;

    }


}
