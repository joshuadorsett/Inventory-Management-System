package c482.logic;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** @author Joshua Paul Dorsett
 * 
 * this class creates a product instance
 */
public class Product {
    /**
    * instance attributes
    */
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();;
    private int productId;
    private String productName;
    private Double productPrice;
    private int productInv;
    private int productMin;
    private int productMax;

    /**
     * constructor
     * @param productId int
     * @param productName String
     * @param productPrice double
     * @param productInv int
     * @param productMin int
     * @param productMax int
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
     * @param id setter
     */
    public void setProductId(int id){
        this.productId = id; 
    }
    
    /**
     * @return the productId
     */
    public int getProductId(){ 
        return productId; 
    }

    /**
     * @param name setter
     */
    public void setProductName(String name){ 
        this.productName = name; 
    }
    
    /**
     * @return the productName
     */
    public String getProductName(){ 
        return productName; 
    }

    /**
     * @param price setter
     */
    public void setProductPrice(double price){ 
        this.productPrice = price; 
    }
    
    /**
     * @return the id
     */
    public double getProductPrice(){ 
        return productPrice; 
    }

    /**
     * @param inv setter
     */
    public void setProductInv(int inv){ 
        this.productInv = inv; 
    }
    
    /**
     * @return the productInv
     */
    public int getProductInv(){ 
        return productInv; 
    }

    /**
     * @param min setter
     */
    public void setProductMin(int min){
        this.productMin = min;
    }
    
    /**
     * @return the productMin
     */
    public int getProductMin(){ 
        return productMin; 
    }

    /**
     * @param max setter
     */
    public void setProductMax(int max){ 
        this.productMax = max; 
    }
    
    /**
     * @return the productMax
     */
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
     * @return isDeleted
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
     * @return associatedParts
     */
    public ObservableList<Part> getAssociatedParts(){ 
        return this.associatedParts; 
    }
}
