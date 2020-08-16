package c482.logic;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** @author Joshua Paul Dorsett
 */
public class Product {
//      instance attributes
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();;
    private int productId;
    private String productName;
    private Double productPrice;
    private int productInv;
    private int productMin;
    private int productMax;

    /**
     * constructor
     * @param productId
     * @param productName
     * @param productPrice
     * @param productInv
     * @param productMin
     * @param productMax
     */
    public Product(int productId, String productName, double productPrice, int productInv, int productMin, int productMax){
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productInv = productInv;
        this.productMin = productMin;
        this.productMax = productMax;
    }

    /**
     * set/get methods for instance attributes
     */
    public void setProductId(int id){
        this.productId = id; 
    }
    public int getProductId(){ 
        return productId; 
    }

    public void setProductName(String name){ 
        this.productName = name; 
    }
    public String getProductName(){ 
        return productName; 
    }

    public void setProductPrice(double price){ 
        this.productPrice = price; 
    }
    public double getProductPrice(){ 
        return productPrice; 
    }

    public void setProductInv(int inv){ 
        this.productInv = inv; 
    }
    public int getProductInv(){ 
        return productInv; 
    }

    public void setProductMin(int min){
        this.productMin = min;
    }
    public int getProductMin(){ 
        return productMin; 
    }

    public void setProductMax(int max){ 
        this.productMax = max; 
    }
    public int getProductMax(){ 
        return productMax; 
    }

    /**
     * add list of associated parts
     * @param partsList adds current selection
     */
    public void addAssociatedPartsList(ObservableList<Part> partsList){
        this.associatedParts = partsList;
    }

    /**
     * delete an associated part
     * @param part to be deleted
     * @return true if deleted
     */
    public boolean deleteAssociatedPart(Part part){
        this.associatedParts.remove(part);
        boolean isDeleted = true; 
        for ( Part p : associatedParts){
            if (p == part) {
                isDeleted = false;
                break;
          }
        }
        return isDeleted;
    }
    
    /**
     * get an associated part
     * @return
     */
    public ObservableList<Part> getAssociatedParts(){ 
        return this.associatedParts; 
    }

    /**
     * validate product and return an error message
     * @param name is checked to see if it's empty
     * @param price must be greater than 0 
     * @param inv must be greater than 0 and between min and max
     * @param min must be less than max and inv
     * @param max must be greater than min and inv
     * @param parts must have at least one associated part
     * @param exceptionMessage this empty string will carry the message
     * @return the full exception message string
     */
    public static String validProduct(String name, double price, int inv, int min, int max, ObservableList<Part> parts, String exceptionMessage) {
        double partsCost = 0.00;
        for (Part p : parts) partsCost = partsCost + p.getPartPrice();
        
        if (name.equals("")) exceptionMessage = exceptionMessage + ("Name cannot be empty. "); 
        if (price < 0) exceptionMessage = exceptionMessage + ("Price must be greater than 0. ");
        if (inv < min || inv > max) exceptionMessage = exceptionMessage + ("Inventory must be between Min and Max. ");
        if (min < 0) exceptionMessage = exceptionMessage + ("Inventory must be greater than 0. ");
        if (min > max) exceptionMessage = exceptionMessage + ("Min must be less than Max. ");
        if (parts.size() < 1) exceptionMessage = exceptionMessage + ("Product must contain at least 1 associated part. ");
        if (partsCost > price) exceptionMessage = exceptionMessage + ("Price of product must be greater than cost of all associated parts. ");
        return exceptionMessage;
    }
}
