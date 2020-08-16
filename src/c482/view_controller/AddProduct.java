package c482.view_controller;

import c482.logic.Inventory;
import c482.logic.Part;
import c482.logic.Product;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author joshuadorsett
 */
public class AddProduct implements Initializable {
//    FXid's
    @FXML
    private TextField AddProductNameText;
    @FXML
    private TextField AddProductInvText;
    @FXML
    private TextField AddProductPriceText;
    @FXML
    private TextField AddProductMinText;
    @FXML
    private TextField AddProductMaxText;
    @FXML
    private TableView<Part> allPartsTableView;
    @FXML
    private TableColumn<Part, Integer> AddProductPartIDCol;
    @FXML
    private TableColumn<Part, String> AddProductPartNameCol;
    @FXML
    private TableColumn<Part, Integer> AddProductPartInvCol;
    @FXML
    private TableColumn<Part, Double> AddProductPartPriceCol;
    @FXML
    private TextField addPartsSearchField;
    @FXML
    private TableView<Part> associatedPartsTableView;
    @FXML
    private TableColumn<Part, Integer> addProductCurrentPartIDCol;
    @FXML
    private TableColumn<Part, String> addProductCurrentPartNameCol;
    @FXML
    private TableColumn<Part, Integer> addProductCurrentPartInvCol;
    @FXML
    private TableColumn<Part, Double> addProductCurrentPartPriceCol;
    
//  class attributes
    private ObservableList<Part> currentParts = FXCollections.observableArrayList();
    private String exceptionMessage = new String();

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        init the all parts table
        allPartsTableView.setItems(Inventory.getAllParts());
        AddProductPartIDCol.setCellValueFactory(new PropertyValueFactory<>("partId"));
        AddProductPartNameCol.setCellValueFactory(new PropertyValueFactory<>("partName"));
        AddProductPartInvCol.setCellValueFactory(new PropertyValueFactory<>("partInv"));
        AddProductPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("partPrice"));                           
    }    
    
//    add an associated part
    @FXML
    private void addPart(ActionEvent event) {
        Part part = allPartsTableView.getSelectionModel().getSelectedItem();
        boolean repeatedItem = false;
        if (part != null) {
            int partId = part.getPartId();
            for (int i = 0; i < currentParts.size(); i++) {
                if (currentParts.get(i).getPartId() == partId) {
                    repeatedItem = true;
                }
            }
            if (!repeatedItem) {
                currentParts.add(part);
                generateAssociatedPartsTable(currentParts);
            }       
        }
    }
     
//    generates an updated associated parts table
    private void generateAssociatedPartsTable(ObservableList<Part> table){
        associatedPartsTableView.setItems(table);
        addProductCurrentPartIDCol.setCellValueFactory(new PropertyValueFactory<>("partId"));
        addProductCurrentPartNameCol.setCellValueFactory(new PropertyValueFactory<>("partName"));
        addProductCurrentPartInvCol.setCellValueFactory(new PropertyValueFactory<>("partInv"));
        addProductCurrentPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("partPrice"));  
    }
    
//    searchfield for parts
    @FXML
    private void addPartsSearchField(ActionEvent event){
        String searchPart = addPartsSearchField.getText();
        int partIndex = -1;
        if (searchPart.length() == 0){
            allPartsTableView.setItems(Inventory.getAllParts());
        } else {
            if (Inventory.lookupPart(searchPart) == -1) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Search Error");
            alert.setHeaderText("Item not found");
            alert.setContentText("The search term does not match any item");
            alert.showAndWait();
            } else {
            partIndex = Inventory.lookupPart(searchPart);
            Part part = Inventory.getAllParts().get(partIndex);
            ObservableList<Part> searchedParts = FXCollections.observableArrayList();
            searchedParts.add(part);
            allPartsTableView.setItems(searchedParts);
            }
        }
    }
     
//    remove an associated part from product
    @FXML
    private void RemoveAssociatedPart(ActionEvent event) {
        Part part = associatedPartsTableView.getSelectionModel().getSelectedItem();
        currentParts.remove(part);
    }

//    cancels the product
    @FXML
    private void addProductCancelButton(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Confirmation Needed");
        alert.setHeaderText("Confirm product cancel");
        alert.setContentText("Are you sure you want to cancel this new product?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            sceneChange("mainPage.fxml", event);
        } 
    } 
    
//    validates and saves the product
    @FXML
    private void addProductSaveButton(ActionEvent event) throws IOException {
       
        String name = AddProductNameText.getText();
        String inv = AddProductInvText.getText();
        String price = AddProductPriceText.getText();
        String min = AddProductMinText.getText();
        String max = AddProductMaxText.getText();
        int id = Inventory.getProductIdCounter();
        try {
            exceptionMessage = Product.validProduct(name, Double.parseDouble(price), Integer.parseInt(inv), Integer.parseInt(min), Integer.parseInt(max),
            currentParts, exceptionMessage);
            if (exceptionMessage.length() > 0) {

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error Adding Product");
                alert.setHeaderText("Error");
                alert.setContentText(exceptionMessage);
                alert.showAndWait();
                exceptionMessage = "";
                return;
            }
            Product product = new Product(id, name, Double.parseDouble(price), Integer.parseInt(inv), Integer.parseInt(min), Integer.parseInt(max));
            product.addAssociatedPartsList(currentParts);
            Inventory.addProduct(product);
            sceneChange("mainPage.fxml", event);
        } catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error!");
            alert.setHeaderText("Error!");
            alert.setContentText("Fields cannot be empty!");
            alert.showAndWait();
        }
    }
    
    /**
     *
     * @param path of the new scene
     * @param event that caused the scene change
     * @throws IOException
     */
    public void sceneChange(String path, ActionEvent event) throws IOException {
        Parent addPartParent = FXMLLoader.load(getClass().getResource(path));
        Scene addPartScene = new Scene(addPartParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(addPartScene);
        window.show();     
    }
}
