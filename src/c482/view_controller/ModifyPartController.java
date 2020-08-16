package c482.view_controller;

import c482.logic.InHouse;
import c482.logic.Inventory;
import c482.logic.OutSourced;
import c482.logic.Part;
import static c482.view_controller.MainPageController.partToModifyIndex;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author joshuadorsett
 */
public class ModifyPartController implements Initializable {
//    FXid's
    @FXML
    private RadioButton InHouseRadioSelected;
    @FXML
    private RadioButton OutSourcedRadioSelected;
    @FXML
    private Text ModifyPartDynamicLabel;
    @FXML
    private Text modPartIDText;
    @FXML
    private TextField modPartNameText;
    @FXML
    private TextField modInvText;
    @FXML
    private TextField modPriceText;
    @FXML
    private TextField modMinText;
    @FXML
    private TextField modMaxText;
    @FXML
    private TextField modPartMachIdText;
    
//    class attributes
    private boolean outSourced;
    private int partIndex = partToModifyIndex();
    private ObservableList<Part> allParts; 
    private int partID;
    private String exceptionMessage = new String();
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        locate selected part to init
        allParts = Inventory.getAllParts();
        Part part = allParts.get(partIndex);
        partID = allParts.get(partIndex).getPartId();

//        init an InHouse part
        if (part instanceof InHouse) {
            InHouseRadioSelected.setSelected(true);
            outSourced = false;
            ModifyPartDynamicLabel.setText("Machine ID");
            modPartMachIdText.setText(Integer.toString(((InHouse) Inventory.getAllParts().get(partIndex)).getMachineId()));
            modPartIDText.setText(Integer.toString(partID));
            modPartNameText.setText(part.getPartName());
            modInvText.setText(Integer.toString(part.getPartInv()));
            modPriceText.setText(Double.toString(part.getPartPrice()));
            modMinText.setText(Integer.toString(part.getPartMin()));
            modMaxText.setText(Integer.toString(part.getPartMax()));

//        init OutSourced part
        } else {
            OutSourcedRadioSelected.setSelected(true);
            outSourced = true;
            ModifyPartDynamicLabel.setText("Company Name");
            modPartMachIdText.setText(((OutSourced) Inventory.getAllParts().get(partIndex)).getCompanyName());
            modPartNameText.setText(part.getPartName());
            modInvText.setText(Integer.toString(part.getPartInv()));
            modPriceText.setText(Double.toString(part.getPartPrice()));
            modMinText.setText(Integer.toString(part.getPartMin()));
            modMaxText.setText(Integer.toString(part.getPartMax()));
        }
    }    

//    InHouse Radio button
    @FXML
    private void InHouseSelected(ActionEvent event) {
        outSourced = false;
        ModifyPartDynamicLabel.setText("Machine ID");
        modPartMachIdText.setText("0");

    }
    
//    OutSourced Radio Button
    @FXML
    private void OutSourcedSelected(ActionEvent event) {
        outSourced = true;
        ModifyPartDynamicLabel.setText("Company Name");
        modPartMachIdText.setText("company name");
    }

    
//    Cancel modifying part
    @FXML
    private void CancelModifiedPart(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.initModality(Modality.NONE);
        alert.setTitle("Confirmation Needed");
        alert.setHeaderText("Confirm Part Delete");
        alert.setContentText("Are you sure you want to cancel update of part?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            sceneChange("mainPage.fxml", event);
        }
    }

//    validate and save the part
    @FXML
    private void saveModifiedPart(ActionEvent event) throws IOException { 
        String name = modPartNameText.getText();
        int inv = Integer.parseInt(modInvText.getText());
        double price = Double.parseDouble(modPriceText.getText());
        int min = Integer.parseInt(modMinText.getText());
        int max = Integer.parseInt(modMaxText.getText());
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
                outPart.setPartId(partID);
                outPart.setPartName(name);
                outPart.setPartInv(inv);
                outPart.setPartPrice(price);
                outPart.setPartMin(min);
                outPart.setPartMax(max);
                companyName = modPartMachIdText.getText();
                outPart.setCompanyName(companyName);       
                Inventory.updatePart(partIndex, outPart);
            } else {
                InHouse inPart = new InHouse();
                inPart.setPartId(partID);
                inPart.setPartName(name);
                inPart.setPartInv(inv);
                inPart.setPartPrice(price);
                inPart.setPartMin(min);
                inPart.setPartMax(max);
                machineId = Integer.parseInt(modPartMachIdText.getText());
                inPart.setMachineId(machineId);       
                Inventory.updatePart(partIndex, inPart);
        }
        sceneChange("mainPage.fxml", event);          
        } catch(NumberFormatException e){
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
