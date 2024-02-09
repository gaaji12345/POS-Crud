package controller;

import entity.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import repo.custom.ItemDao;
import repo.impl.ItemIMPL;
import tm.Itemtm;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ItemFormController {

    public TextField txtCode;
    public TextField txtName;
    public TextField txtPrice;
    public TextField txtQty;
   // public Button btnSave;
    public Button btnDelete;
    public TableView <Itemtm>tblItem;
    public Button btnSaves;

    ItemDao itemDao=new ItemIMPL();

    public void initialize() {
        tblItem.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("code"));
        tblItem.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblItem.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("price"));
        tblItem.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("qty"));


       // initUI();
        tblItem.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnDelete.setDisable(newValue == null);
            btnSaves.setText(newValue != null ? "Update" : "Save");
            btnSaves.setDisable(newValue == null);

            if (newValue != null) {
                txtCode.setText(newValue.getCode());
                txtName.setText(newValue.getName());
                txtPrice.setText(String.valueOf(newValue.getPrice()));
                txtQty.setText(String.valueOf(newValue.getQty()));

                txtCode.setDisable(false);
                txtName.setDisable(false);
                txtPrice.setDisable(false);
                txtQty.setDisable(false);
            }
        });

        txtQty.setOnAction(event -> btnSaves.fire());


        loadAllCustomers();
    }

    private void loadAllCustomers() {
        List<Itemtm> itemtms = FXCollections.observableArrayList();
        for (Item item : itemDao.getAll()) {
           itemtms.add(new Itemtm(
                   item.getCode(),
                   item.getName(),
                   item.getPrice(),
                   item.getQty()
            ));
        }
      tblItem.setItems((ObservableList<Itemtm>) itemtms);


    }


    public void txtSerarch_OnAction(ActionEvent actionEvent) {
    }

    public void delete_ONAction(ActionEvent actionEvent) {
    }

    public void btnSave_OnAction(ActionEvent actionEvent) {

       if (btnSaves.getText().equalsIgnoreCase("save")) {
            itemDao.save(new Item(
                    txtCode.getText(),
                    txtName.getText(),
                     Double.valueOf(txtPrice.getText()),
                    Integer.valueOf(txtQty.getText())
            ));

        }else {
            itemDao.update(new Item(
                    txtCode.getText(),
                    txtName.getText(),
                    Double.valueOf(txtPrice.getText()),
                    Integer.valueOf(txtQty.getText())
            ));

        }
        new Alert(Alert.AlertType.CONFIRMATION,"SUCCESS...!").show();

    }
}
