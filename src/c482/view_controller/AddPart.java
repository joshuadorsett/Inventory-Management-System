package c482.view_controller;

import c482.logic.InHouse;
import c482.logic.Inventory;
import c482.logic.OutSourced;
import c482.logic.Part;
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
 * FXML Controller class
 *
 * @author joshuadorsett
 */
public class AddPart {
//    FXid's
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
    
//    class attributes
    private boolean outSourced = true;
    private String exceptionMessage = new String();
  
//    InHouse Radio button
    @FXML
    private void InHouseSelected(ActionEvent event) {        
        outSourced = false;
        AddPartDynamicLabel.setText("Machine ID");
        addPartDynText.setText("0");
    }
 
//    OutSourced Radio Button
    @FXML
    private void OutSourcedSelected(ActionEvent event) {
        outSourced = true;
        AddPartDynamicLabel.setText("Company Name");
        addPartDynText.setText("company name");
    }

//    Cancel the part
    @FXML
    private void cancelAddedPart(ActionEvent event) throws IOException {
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
    
//    validate and save the part
    @FXML
    private void saveAddedPart(ActionEvent event) throws IOException {
        String name = addPartNameText.getText();
        double price = Double.parseDouble(addPriceText.getText());
        int inv = Integer.parseInt(addInvText.getText());
        int min = Integer.parseInt(addMinText.getText());
        int max = Integer.parseInt(addMaxText.getText());
        String companyName;
        int machineId;
        try {
            exceptionMessage = Part.validPart(name, price, inv, min, max, exceptionMessage);
            
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
                OutSourced outPart = new OutSourced();
                outPart.setPartId(Inventory.getPartIdCounter());
                outPart.setPartName(name);
                outPart.setPartPrice(price);
                outPart.setPartInv(inv);
                outPart.setPartMin(min);
                outPart.setPartMax(max);
                companyName = addPartDynText.getText();
                outPart.setCompanyName(companyName);
                Inventory.addPart(outPart);
            } else {
                InHouse inPart = new InHouse();     
                inPart.setPartId(Inventory.getPartIdCounter());
                inPart.setPartName(addPartNameText.getText());
                inPart.setPartPrice(Double.parseDouble(addPriceText.getText()));
                inPart.setPartInv(Integer.parseInt(addInvText.getText()));
                inPart.setPartMin(Integer.parseInt(addMinText.getText()));
                inPart.setPartMax(Integer.parseInt(addMaxText.getText()));
                machineId = Integer.parseInt(addPartDynText.getText());
                inPart.setMachineId(machineId);
                Inventory.addPart(inPart);
            }
            sceneChange("mainPage.fxml", event);
        } catch(NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error !");
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
