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
import javafx.scene.control.Alert.AlertType;
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
public class MainPageController implements Initializable {
//    FXid's
    @FXML
    private TableView<Part> MainPartsTableView;
    @FXML
    private TableColumn<Part, Integer> MainPartIDCol;
    @FXML
    private TableColumn<Part, String> MainPartNameCol;
    @FXML
    private TableColumn<Part, Integer> MainPartInvCol;
    @FXML
    private TableColumn<Part, Double> MainPartPriceCol;
    @FXML
    private TextField MainPartsSearchField;
    @FXML
    private TableView<Product> MainProductsTableView;
    @FXML
    private TableColumn<Product, Integer> MainProductsIDCol;
    @FXML
    private TableColumn<Product, String> MainProductsNameCol;
    @FXML
    private TableColumn<Product, Integer> MainProductsInvCol;
    @FXML
    private TableColumn<Product, Double> MainProductsPriceCol;
    @FXML
    private TextField MainProductsSearchField;

//    class attributes
    private static Part modifyPart; /*the selected part to be modified*/
    private static int modifyPartIndex; /*the index of the selected part*/
    private static Product modifyProduct; /*the selected product to be modified*/
    private static int modifyProductIndex; /*the index of the selected product*/
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
//         load parts table
        MainPartsTableView.setItems(Inventory.getAllParts()); 
        MainPartIDCol.setCellValueFactory(new PropertyValueFactory<>("partId"));
        MainPartNameCol.setCellValueFactory(new PropertyValueFactory<>("partName"));
        MainPartInvCol.setCellValueFactory(new PropertyValueFactory<>("partInv"));
        MainPartPriceCol.setCellValueFactory(new PropertyValueFactory<>("partPrice"));
//         load products table
        MainProductsTableView.setItems(Inventory.getAllProducts());
        MainProductsIDCol.setCellValueFactory(new PropertyValueFactory<>("productId"));
        MainProductsNameCol.setCellValueFactory(new PropertyValueFactory<>("productName"));
        MainProductsInvCol.setCellValueFactory(new PropertyValueFactory<>("productInv"));
        MainProductsPriceCol.setCellValueFactory(new PropertyValueFactory<>("productPrice"));
    }    
    
//      the search field for parts.
    @FXML
    void MainPartsSearch(ActionEvent event){
        String searchPart = MainPartsSearchField.getText();
        int partIndex = -1;
        if (searchPart.length() == 0){
            MainPartsTableView.setItems(Inventory.getAllParts());
        } else {
            if (Inventory.lookupPart(searchPart) == -1) {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Search Error");
            alert.setHeaderText("Item not found");
            alert.setContentText("The search term does not match any item");
            alert.showAndWait();
            } else {
            partIndex = Inventory.lookupPart(searchPart);
            Part part = Inventory.getAllParts().get(partIndex);
            ObservableList<Part> searchedParts = FXCollections.observableArrayList();
            searchedParts.add(part);
            MainPartsTableView.setItems(searchedParts);
            }
        }
        
    }
     
//      the search field for products.
    @FXML
    void MainProductsSearchField(ActionEvent event) {
        String searchProduct = MainProductsSearchField.getText();
        int productIndex = -1;
        if (searchProduct.length() == 0){
            MainProductsTableView.setItems(Inventory.getAllProducts());
        } else {
            if (Inventory.lookupProduct(searchProduct) == -1) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Search Error");
                alert.setHeaderText("Item not found");
                alert.setContentText("The search term does not match any item");
                alert.showAndWait();
            } else {
                productIndex = Inventory.lookupProduct(searchProduct);
                Product product = Inventory.getAllProducts().get(productIndex);
                ObservableList<Product> searchedProducts = FXCollections.observableArrayList();
                searchedProducts.add(product);
                MainProductsTableView.setItems(searchedProducts);
            }
        }    
    }
    
//    opens the add parts scene

    /**
     *
     * @param event
     * @throws IOException
     */
    @FXML
    public void MainPartsAdd(ActionEvent event) throws IOException {
        sceneChange("addPart.fxml", event);
    }
 
//    opens the add products scene
    @FXML
    void MainProdAdd(ActionEvent event) throws IOException {
        sceneChange("addProduct.fxml", event);

    }
    
//    opens the modify parts scene
    @FXML
    void MainPartsModify(ActionEvent event) throws IOException  {
        modifyPart = MainPartsTableView.getSelectionModel().getSelectedItem();
        modifyPartIndex = Inventory.getAllParts().indexOf(modifyPart);
        sceneChange("modifyPart.fxml", event);

    }
    /**
     * @return modifyPartIndex
     */
    public static int partToModifyIndex() {
        return modifyPartIndex;
    }
     
//    opens the modify products scene
    @FXML
    void MainProdModify(ActionEvent event) throws IOException {
        modifyProduct = MainProductsTableView.getSelectionModel().getSelectedItem();
        modifyProductIndex = Inventory.getAllProducts().indexOf(modifyProduct);        
        sceneChange("modifyProduct.fxml", event);
    }
    /**
     *
     * @return modifyProductIndex
     */
    public static int productToModifyIndex() {
        return modifyProductIndex;
    }
    
//    deletes the selected part
    @FXML
    void MainPartsDelete(ActionEvent event) {
        Part part = MainPartsTableView.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Product Delete");
        alert.setHeaderText("Confirm?");
        alert.setContentText("Are you sure you want to delete " + part.getPartName() + "?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Inventory.deletePart(part);
            ObservableList<Part> update = Inventory.getAllParts();
            MainPartsTableView.setItems(update);               
            System.out.println("Part " + part.getPartName() + " was removed.");
        } else {
            System.out.println("Part " + part.getPartName() + " was not removed.");
        }
        Inventory.deletePart(part);
        ObservableList<Part> update = Inventory.getAllParts();
        MainPartsTableView.setItems(update);
    }

//    deletes the selected product
    @FXML
    void MainProdDelete(ActionEvent event) throws IOException {
        Product product = MainProductsTableView.getSelectionModel().getSelectedItem();
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Product Delete");
        alert.setHeaderText("Confirm?");
        alert.setContentText("Are you sure you want to delete " + product.getProductName() + "?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Inventory.deleteProduct(product);
            ObservableList<Product> update = Inventory.getAllProducts();
            MainProductsTableView.setItems(update);                
            System.out.println("Part " + product.getProductName() + " was removed.");
        } else {
            System.out.println("Part " + product.getProductName() + " was not removed.");
        }
    }

//    this exits the program
    @FXML
     void MainExit(ActionEvent event) {
         Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Confirmation Needed");
        alert.setHeaderText("Confirm Exit");
        alert.setContentText("Are you sure you want to exit?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            System.exit(0);
        } else {
            System.out.println("You clicked cancel.");
        }
    }

//    this provides the scene change code and accepts the new Path and the event as args

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
