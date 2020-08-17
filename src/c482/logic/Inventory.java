package c482.logic;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** 
 * This class holds the static inventory of products and parts.
 * It includes methods for navigating through these items.
 * in a future version this inventory will be added into a Database and there will be methods for converting the Java objects into entries into the table with SQL.
 * @author Joshua Paul Dorsett
 */

public class Inventory {
    /**
    * static attributes
    */
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    private static int partIdCounter = 0;
    private static int productIdCounter = 0;
    
    /**
     * Getter for part ID count.
     * This is used to make a new ID for each new part, however the counter is kept the same for modifications.
     * @return current part ID count.
     */
    public static int getPartIdCounter() {
        return partIdCounter++;
    }

    /**
     * Getter for product ID count.
     *This is used to make a new ID for each new product, however the counter is kept the same for modifications.
     * @return current product ID count.
     */
    public static int getProductIdCounter() {
        return productIdCounter++;
    }
    
    /**
     * adds a part to inventory.
     * in a future version this will inventory will be added into a Database and there will be methods for converting the Java object into SQL.
     * @param newPart to be added.
     */
    public static void addPart(Part newPart){
        allParts.add(newPart);
    }
    
    /**
     * adds a product to inventory.
     * in a future version this will inventory will be added into a Database and there will be methods for converting the Java object into SQL.
     * @param newProduct to be added.
     */
    public static void addProduct(Product newProduct){
        allProducts.add(newProduct);
    }    
      
    /**
     * looks up part with ID or name.
     * @param part to find.
     * @return the part.
     */
    public static int lookupPart(String part){
        boolean found = false;
        int index = 0;
        if (isInteger(part)){
            for (int i = 0; i < allParts.size(); i++) {
                if (Integer.parseInt(part) == allParts.get(i).getId()) {
                    index = i;
                    found = true;
                }
            }
        } else {
            for (int i = 0; i < allParts.size(); i++) {
                if (part.equals(allParts.get(i).getName())) {
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
     * looks up product with ID or name.
     * @param product to find.
     * @return the product.
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
     * checks to see if string is an integer.
     * @param s is the string to check.
     * @return true if s can be an int.
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
    
    /**
     * updates an existing part.
     * @param index index
     * @param selectedPart part selected
     */
    public static void updatePart ( int index, Part selectedPart){
        allParts.set(index, selectedPart);
    }
    /**
     * update an existing product.
     * @param index index
     * @param newProduct  new updated product
     */
    public static void updateProduct( int index, Product newProduct){
        allProducts.set(index, newProduct);
    }
       
    /**
     * deletes an existing part.
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
     * deletes an existing product.
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
       
    /**
     * returns list of all parts.
     * @return an observable list of all parts
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
        }

    /**
     * returns a list of all products.
     * @return an observable list of all products
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
            }
}
