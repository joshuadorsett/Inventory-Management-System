package c482.view_controller;

import c482.logic.Inventory;
import c482.logic.Part;
import c482.logic.Product;
import static c482.view_controller.MainPageController.productToModifyIndex;
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
import javafx.scene.control.Label;
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
public class ModifyProductController implements Initializable {
//    FXid's
    @FXML
    private Label modProductIdText;
    @FXML
    private TextField ModProductNameText;
    @FXML
    private TextField ModProductInvText;
    @FXML
    private TextField ModProductPriceText;
    @FXML
    private TextField ModProductMinText;
    @FXML
    private TextField ModProductMaxText;
    @FXML
    private TableView<Part> modAllPartsTableView;
    @FXML
    private TableColumn<Part, Integer> ModifyProductPartIDCol;
    @FXML
    private TableColumn<Part, String> ModifyProductPartNameCol;
    @FXML
    private TableColumn<Part, Integer> ModifyProductPartInvCol;
    @FXML
    private TableColumn<Part, Double> ModifyProductPartPriceCol;
    @FXML
    private TextField modAddPartsSearchField;
    @FXML
    private TableView<Part> modAssociatedPartsTableView;
    @FXML
    private TableColumn<Part, Integer> ModifyProductCurrentPartIDCol;
    @FXML
    private TableColumn<Part, String> ModifyProductCurrentPartNameCol;
    @FXML
    private TableColumn<Part, Integer> ModifyProductCurrentPartInvCol;
    @FXML
    private TableColumn<Part,Double> ModifyProductCurrentPartPriceCol;
    
//  class attributes
    private int productIndex = productToModifyIndex();
    private ObservableList<Product> allProducts;
    private Product product;
    private int productId;
    private ObservableList<Part> currentParts = FXCollections.observableArrayList();
    private String exceptionMessage = new String();

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        allProducts = Inventory.getAllProducts(); 
        product = allProducts.get(productIndex);
        productId = allProducts.get(productIndex).getProductId();

//        adding all associated parts for selected product to a current list of associated parts
        ObservableList<Part> associatedParts = product.getAssociatedParts();
        for (Part p : associatedParts){
            currentParts.add(p);
        }
        
//        init the text fields
        modProductIdText.setText(Integer.toString(productId));
        ModProductNameText.setText(product.getProductName());
        ModProductInvText.setText(Integer.toString(product.getProductInv()));
        ModProductPriceText.setText(Double.toString(product.getProductPrice()));
        ModProductMinText.setText(Integer.toString(product.getProductMin()));
        ModProductMaxText.setText(Integer.toString(product.getProductMax()));
        
//        init all parts table
        modAllPartsTableView.setItems(Inventory.getAllParts());
        ModifyProductPartIDCol.setCellValueFactory(new PropertyValueFactory<>("partId"));
        ModifyProductPartNameCol.setCellValueFactory(new PropertyValueFactory<>("partName"));
        ModifyProductPartInvCol.setCellValueFactory(new PropertyValueFactory<>("partInv"));
        ModifyProductPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("partPrice"));  
        
//        init associated parts table
        generateModAssociatedPartsTable(currentParts);         
    }      
    
//    generates an updated associated parts table
    private void generateModAssociatedPartsTable(ObservableList<Part> table){
        modAssociatedPartsTableView.setItems(table);
        ModifyProductCurrentPartIDCol.setCellValueFactory(new PropertyValueFactory<>("partId"));
        ModifyProductCurrentPartNameCol.setCellValueFactory(new PropertyValueFactory<>("partName"));
        ModifyProductCurrentPartInvCol.setCellValueFactory(new PropertyValueFactory<>("partInv"));
        ModifyProductCurrentPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("partPrice"));  
    }
    
//    searchfield for parts
    @FXML
    private void modAddPartsSearchField(ActionEvent event){
        String searchPart = modAddPartsSearchField.getText();
        int partIndex = -1;
        if (searchPart.length() == 0){
            modAllPartsTableView.setItems(Inventory.getAllParts());
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
            modAllPartsTableView.setItems(searchedParts);
            }
        }
    }  
    
//    adds an associated part
    @FXML
    private void modAddassociatedPart(ActionEvent event) {
        Part part = modAllPartsTableView.getSelectionModel().getSelectedItem();
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
                generateModAssociatedPartsTable(currentParts);
            }       
        }
    }
    
//    removes an associated part
    @FXML
    private void modRemoveAssociatedPart(ActionEvent event) {
        Part part = modAssociatedPartsTableView.getSelectionModel().getSelectedItem();
        currentParts.remove(part);
        generateModAssociatedPartsTable(currentParts);       
    }  

//    cancels modifying the product
    @FXML
    private void modProductCancelButton(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Confirmation Needed");
        alert.setHeaderText("Confirm product cancel");
        alert.setContentText("Are you sure you want to cancel update of product?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            sceneChange("mainPage.fxml", event);
        } 
    }
    
//    validates and saves the product
    @FXML
    private void modProductSaveButton(ActionEvent event) throws IOException {
        
        String name = ModProductNameText.getText();
        String inv = ModProductInvText.getText();
        String price = ModProductPriceText.getText();
        String min = ModProductMinText.getText();
        String max = ModProductMaxText.getText();
        try {
            exceptionMessage = Product.validProduct(name, Double.parseDouble(price), Integer.parseInt(inv), Integer.parseInt(min), Integer.parseInt(max),
            currentParts, exceptionMessage);
            if (exceptionMessage.length() > 0) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error Modifying Product");
                alert.setHeaderText("Error");
                alert.setContentText(exceptionMessage);
                alert.showAndWait();
                exceptionMessage = "";
                return;
                }
            Product product = new Product(productId, name, Double.parseDouble(price), Integer.parseInt(inv),  
            Integer.parseInt(min), Integer.parseInt(max));
            product.addAssociatedPartsList(currentParts);
            Inventory.updateProduct(productIndex, product);
            sceneChange("mainPage.fxml", event);
        } catch(NumberFormatException e) {
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
