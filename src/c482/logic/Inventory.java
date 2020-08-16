package c482.logic;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** @author Joshua Paul Dorsett
 */

public class Inventory {
//    static attributes
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    private static int partIdCounter;
    private static int productIdCounter;

//    constructor
    Inventory(){
        partIdCounter = 0;
        productIdCounter = 0;
    }
    
//    get the current ID count
    /**
     * @return current part ID count
     */
    public static int getPartIdCounter() {
        return partIdCounter++;
    }

    /**
     * @return current product ID count
     */
    public static int getProductIdCounter() {
        return productIdCounter++;
    }
    
//    add new items to inventory lists
    /**
     * @param newPart to be added
     */
    public static void addPart(Part newPart){
        allParts.add(newPart);
    }
    /**
     * @param newProduct to be added
     */
    public static void addProduct(Product newProduct){
        allProducts.add(newProduct);
    }    
      
//      lookup methods
    /**
     * @param part to find
     * @return the part
     */
    public static int lookupPart(String part){
        boolean found = false;
        int index = 0;
        if (isInteger(part)){
            for (int i = 0; i < allParts.size(); i++) {
                if (Integer.parseInt(part) == allParts.get(i).getPartId()) {
                    index = i;
                    found = true;
                }
            }
        } else {
            for (int i = 0; i < allParts.size(); i++) {
                if (part.equals(allParts.get(i).getPartName())) {
                    index = i;
                    found = true;
                }
            }
        }
        if (found) {
            return index;
        } else {
            System.out.println("Not found.");
            return -1;
        }
    }
    /**
     * @param product to find
     * @return the product
     */
    public static int lookupProduct(String product){
        boolean found = false;
        int index = 0;
        if (isInteger(product)){
            for (int i = 0; i < allProducts.size(); i++) {
                if (Integer.parseInt(product) == allProducts.get(i).getProductId()) {
                    index = i;
                    found = true;
                }
            }
        } else {
            for (int i = 0; i < allProducts.size(); i++) {
                if (product.equals(allProducts.get(i).getProductName())) {
                    index = i;
                    found = true;
                }
            }
        }
        if (found) {
            return index;
        } else {
            System.out.println("Not found.");
            return -1;
        }
    }
    /**
     *
     * @param s is the string to check
     * @return true if s can be an int
     */
    public static boolean isInteger(String s) {
        try { 
            Integer.parseInt(s); 
        } catch(NumberFormatException e) { 
            return false; 
        } catch(NullPointerException e) {
            return false;
        }
        return true;
}
    
//    updates inventory methods
    /**
     * @param index
     * @param selectedPart
     */
    public static void updatePart ( int index, Part selectedPart){
        allParts.set(index, selectedPart);
    }
    /**
     * @param index
     * @param newProduct
     */
    public static void updateProduct( int index, Product newProduct){
        allProducts.set(index, newProduct);
    }
       
//    delete methods
    /**
     * @param selectedPart to be deleted
     * @return true if the part was deleted
     */
    public static boolean deletePart(Part selectedPart){
        allParts.remove(selectedPart);
        boolean isDeleted = true;
        for ( Part p : allParts){
            if (p == selectedPart) {
                isDeleted = false;
                break;
            }
        }
        return isDeleted;
    }
    /**
     *
     * @param selectedProduct to be deleted
     * @return true if the product was deleted
     */
    public static boolean deleteProduct(Product selectedProduct) {
        allProducts.remove(selectedProduct);
        boolean isDeleted = true;
        for (Product p : allProducts) {
            if (p == selectedProduct) {
                isDeleted = false;
                break;
            }
        }
        return isDeleted;
    }
       
//    get the inventory lists
    /**
     * @return an observable list of all parts
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
        }

    /**
     * @return an observable list of all products
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
            }
}
