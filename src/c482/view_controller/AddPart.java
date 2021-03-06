package c482.view_controller;

import c482.logic.InHouse;
import c482.logic.Inventory;
import c482.logic.OutSourced;
import java.io.IOException;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class for add part page.
 *
 * @author joshuadorsett
 */
public class AddPart {

    @FXML
    private Text AddPartDynamicLabel;
    @FXML
    private TextField addPartNameText;
    @FXML
    private TextField addInvText;
    @FXML
    private TextField addPriceText;
    @FXML
    private TextField addMinText;
    @FXML
    private TextField addMaxText;
    @FXML
    private TextField addPartDynText;
    
    /**
     * class attributes
     */
    private boolean outSourced = true;
    private String exceptionMessage = new String();
  
    /**
     * InHouse Radio button.
     * sets the text to machine id and the field to an example integer.
     * @param event action event from the radio button.
     */
    @FXML
    public void InHouseSelected(ActionEvent event) {        
        outSourced = false;
        AddPartDynamicLabel.setText("Machine ID");
        addPartDynText.setText("0");
    }
 
    /**
     * OutSourced Radio Button.
     * sets the text to company name and the field to an example string.
     * @param event action event from the radio button.
     */
    @FXML
    public void OutSourcedSelected(ActionEvent event) {
        outSourced = true;
        AddPartDynamicLabel.setText("Company Name");
        addPartDynText.setText("company name");
    }

    /**
     * Cancels the part.
     * @param event action event from the cancel button.
     * @throws IOException is thrown if it cannot access the FXML loader path.
     */
    @FXML
    public void cancelAddedPart(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Confirmation Needed");
        alert.setHeaderText("Confirm Part Delete");
        alert.setContentText("Are you sure you want to cancel new part?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            sceneChange("mainPage.fxml", event);
        } 
    }
    
    /**
     * validate and saves the part.
     * @param event action event from the save button.
     * @throws IOException is thrown if it cannot access the FXML loader path.
     * @throws NumberFormatException this exception is thrown if there is an invalid entry in the text fields from a string attempting to be converted to a number. This program then catches it and sends an alert.
     */
    @FXML
    public void saveAddedPart(ActionEvent event) throws IOException {   
        try {
            String name = addPartNameText.getText();
            String price = addPriceText.getText();
            String inv = addInvText.getText();
            String min = addMinText.getText();
            String max = addMaxText.getText();
            String companyName;
            int machineId;
            exceptionMessage = MainPageController.validPart(name, Double.parseDouble(price), Integer.parseInt(inv), Integer.parseInt(min), Integer.parseInt(max), exceptionMessage);
            
            if (exceptionMessage.length() > 0) {

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error Adding Part");
                alert.setHeaderText("Error");
                alert.setContentText(exceptionMessage);
                alert.showAndWait();
                exceptionMessage = "";
                return;
            }
            if (outSourced){
                OutSourced outPart = new OutSourced(Inventory.getPartIdCounter(), name, Double.parseDouble(price), Integer.parseInt(inv), Integer.parseInt(min), Integer.parseInt(max), addPartDynText.getText());        
                Inventory.addPart(outPart);
            } else {
                InHouse inPart = new InHouse(Inventory.getPartIdCounter(),name, Double.parseDouble(price), Integer.parseInt(inv), Integer.parseInt(min), Integer.parseInt(max), Integer.parseInt(addPartDynText.getText()));
                Inventory.addPart(inPart);
            }
            sceneChange("mainPage.fxml", event);
        } catch(NumberFormatException e) {
            System.out.println("caught invalid type!");
            Alert alert3 = new Alert(Alert.AlertType.INFORMATION);
            alert3.setTitle("Error!");
            alert3.setHeaderText("Error!");
            alert3.setContentText("invalid input types!");
            alert3.showAndWait();
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
