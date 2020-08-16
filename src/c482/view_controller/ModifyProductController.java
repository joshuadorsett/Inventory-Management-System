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
 * FXML Controller class for modify product page
 *
 * @author joshuadorsett
 */
public class ModifyProductController implements Initializable {
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
    
  /**
   * class attributes
   */
    private int productIndex = productToModifyIndex();
    private ObservableList<Product> allProducts;
    private Product product;
    private int productId;
    private ObservableList<Part> temporaryPartsList = FXCollections.observableArrayList();
    private String exceptionMessage = new String();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        allProducts = Inventory.getAllProducts(); 
        product = allProducts.get(productIndex);
        productId = allProducts.get(productIndex).getProductId();

//        adding all associated parts for selected product to a current list of associated parts
        ObservableList<Part> associatedParts = product.getAssociatedParts();
        for (Part p : associatedParts){
            temporaryPartsList.add(p);
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
        ModifyProductPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        ModifyProductPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        ModifyProductPartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        ModifyProductPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));  
        
//        init associated parts table
        generateModAssociatedPartsTable(temporaryPartsList);         
    }      
    
    /**
     * generates an updated associated parts table
     * @param table table of current parts selected
     */
    public void generateModAssociatedPartsTable(ObservableList<Part> table){
        modAssociatedPartsTableView.setItems(table);
        ModifyProductCurrentPartIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        ModifyProductCurrentPartNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        ModifyProductCurrentPartInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        ModifyProductCurrentPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));  
    }
    
    /**
     * search field for parts
     * @param event event
     */
    @FXML
    public void modAddPartsSearchField(ActionEvent event){
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
    
    /**
     * adds an associated part
     * @param event event
     */
    @FXML
    public void modAddassociatedPart(ActionEvent event) {
        Part part = modAllPartsTableView.getSelectionModel().getSelectedItem();
        boolean repeatedItem = false;
        if (part != null) {
            int partId = part.getId();
            for (int i = 0; i < temporaryPartsList.size(); i++) {
                if (temporaryPartsList.get(i).getId() == partId) {
                    repeatedItem = true;
                }
            }
            if (!repeatedItem) {
                temporaryPartsList.add(part);
                generateModAssociatedPartsTable(temporaryPartsList);
            }       
        }
    }
    
    /**
     * removes an associated part
     * @param event event
     */
    @FXML
    public void modRemoveAssociatedPart(ActionEvent event) {
        Part part = modAssociatedPartsTableView.getSelectionModel().getSelectedItem();
        if (part == null){
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Confirmation Needed");
        alert.setHeaderText("Confirm removing associated part");
        alert.setContentText("Are you sure you want to remove associated part?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
        temporaryPartsList.remove(part);
        generateModAssociatedPartsTable(temporaryPartsList);       
        }
    }  
        
    /**
     * cancels modifying the product
     * @param event event
     * @throws IOException is thrown if it cannot access the FXML loader path.
     */
    @FXML
    public void modProductCancelButton(ActionEvent event) throws IOException {
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
    
    /**
     * validates and saves the product
     * @param event event
     * @throws IOException is thrown if it cannot access the FXML loader path.
     * @throws NumberFormatException this exception is thrown if there is an invalid entry in the text fields from a string attempting to be converted to a number. This program then catches it and sends an alert.
     */
    @FXML
    public void modProductSaveButton(ActionEvent event) throws IOException {  
        try {
            String name = ModProductNameText.getText();
            String inv = ModProductInvText.getText();
            String price = ModProductPriceText.getText();
            String min = ModProductMinText.getText();
            String max = ModProductMaxText.getText();
            exceptionMessage = MainPageController.validProduct(name, Double.parseDouble(price), Integer.parseInt(inv), Integer.parseInt(min), Integer.parseInt(max),
            temporaryPartsList, exceptionMessage);
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
            product.addAssociatedPartsList(temporaryPartsList);
            Inventory.updateProduct(productIndex, product);
            sceneChange("mainPage.fxml", event);
        } catch(NumberFormatException e) {
            Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
            alert2.setTitle("Error!");
            alert2.setHeaderText("Error!");
            alert2.setContentText("invalid input types!");
            alert2.showAndWait();
        }       
    }
    
    /**
     * changes scenes.
     * @param path path of the new scene
     * @param event action even that caused the scene change
     * @throws IOException is thrown if it cannot access the FXML loader path.
     */
    public void sceneChange(String path, ActionEvent event) throws IOException {
        Parent addPartParent = FXMLLoader.load(getClass().getResource(path));
        Scene addPartScene = new Scene(addPartParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(addPartScene);
        window.show();
      
    }
}
