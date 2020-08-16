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
 * FXML Controller class for modify part page.
 *
 * @author joshuadorsett
 */
public class ModifyPartController implements Initializable {
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
    
    /**
     * class attributes
     */
    private boolean outSourced;
    private int partIndex = partToModifyIndex();
    private ObservableList<Part> allParts; 
    private int partID;
    private String exceptionMessage = new String();
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
//        locate selected part to init
        allParts = Inventory.getAllParts();
        Part part = allParts.get(partIndex);
        partID = allParts.get(partIndex).getId();

//        init an InHouse part
        if (part instanceof InHouse) {
            InHouseRadioSelected.setSelected(true);
            outSourced = false;
            ModifyPartDynamicLabel.setText("Machine ID");
            modPartMachIdText.setText(Integer.toString(((InHouse) Inventory.getAllParts().get(partIndex)).getMachineId()));
            modPartIDText.setText(Integer.toString(partID));
            modPartNameText.setText(part.getName());
            modInvText.setText(Integer.toString(part.getStock()));
            modPriceText.setText(Double.toString(part.getPrice()));
            modMinText.setText(Integer.toString(part.getMin()));
            modMaxText.setText(Integer.toString(part.getMax()));

//        init OutSourced part
        } else {
            OutSourcedRadioSelected.setSelected(true);
            outSourced = true;
            ModifyPartDynamicLabel.setText("Company Name");
            modPartMachIdText.setText(((OutSourced) Inventory.getAllParts().get(partIndex)).getCompanyName());
            modPartNameText.setText(part.getName());
            modInvText.setText(Integer.toString(part.getStock()));
            modPriceText.setText(Double.toString(part.getPrice()));
            modMinText.setText(Integer.toString(part.getMin()));
            modMaxText.setText(Integer.toString(part.getMax()));
        }
    }    

    /**
     * InHouse Radio button.
     * sets the text to machine id and the field to an example integer.
     * @param event action event caused by the radio button.
     */
    @FXML
    public void InHouseSelected(ActionEvent event) {
        outSourced = false;
        ModifyPartDynamicLabel.setText("Machine ID");
        modPartMachIdText.setText("0");

    }
    
    /**
     * OutSourced Radio Button.
     * sets the text to company name and the field to an example string.
     * @param event action event caused by the radio button.
     */
    @FXML
    public void OutSourcedSelected(ActionEvent event) {
        outSourced = true;
        ModifyPartDynamicLabel.setText("Company Name");
        modPartMachIdText.setText("company name");
    }

    
    /**
     * Cancel modifying part.
     * @param event action event caused by the cancel button.
     * @throws IOException is thrown if it cannot access the FXML loader path.
     */
    @FXML
    public void CancelModifiedPart(ActionEvent event) throws IOException {
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

    /**
     * validate and save the part.
     * @param event action event caused by the save button.
     * @throws IOException is thrown if it cannot access the FXML loader path.
     * @throws NumberFormatException this exception is thrown if there is an invalid entry in the text fields from a string attempting to be converted to a number. This program then catches it and sends an alert.
     */
    @FXML
    public void saveModifiedPart(ActionEvent event) throws IOException { 
        try {
            String name = modPartNameText.getText();
            String inv = modInvText.getText();
            String price = modPriceText.getText();
            String min = modMinText.getText();
            String max = modMaxText.getText();
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
                OutSourced outPart = new OutSourced(partID, name, Double.parseDouble(price), Integer.parseInt(inv), Integer.parseInt(min), Integer.parseInt(max), modPartMachIdText.getText());    
                Inventory.updatePart(partIndex, outPart);
            } else {
                InHouse inPart = new InHouse(partID, name, Double.parseDouble(price), Integer.parseInt(inv), Integer.parseInt(min), Integer.parseInt(max), Integer.parseInt(modPartMachIdText.getText()));
                Inventory.updatePart(partIndex, inPart);
        }
        sceneChange("mainPage.fxml", event);          
        } catch(NumberFormatException e){
            Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
            alert2.setTitle("Error!");
            alert2.setHeaderText("Error!");
            alert2.setContentText("invalid input types!");
            alert2.showAndWait();
        }
            
    }
    
    /**
     * changes scenes.
     * @param path path of the new scene.
     * @param event action even that caused the scene change.
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
