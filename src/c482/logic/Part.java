package c482.logic;

 /** @author Joshua Paul Dorsett
        */
public abstract class Part {
    
    /**
     * instance attributes
     */
    private int partId;
    private String partName;
    private double partPrice;
    private int partInv;
    private int partMin;
    private int partMax;
    
    /**
     * constructor
     */ 
    public Part() {
        this.partId = 0;
        this.partName = "default";
        this.partPrice = 0.00;
        this.partInv = 0;
        this.partMin = 0;
        this.partMax = 0;
    }
   
    /**
     * set and get methods for instance attributes
     */
    public void setPartId(int id) { 
        this.partId = id; 
    }

    public int getPartId() { 
        return partId; 
    }


    public void setPartName(String name) { 
        this.partName = name; 
    }

    public String getPartName() { 
        return partName; 
    }


    public void setPartPrice(double price) { 
        this.partPrice = price; 
    }
    public double getPartPrice() { 
        return partPrice; 
    }

    public void setPartInv(int inv) { 
        this.partInv = inv; 
    }
    public int getPartInv() { 
        return partInv; 
    }


    public void setPartMin(int min) { 
        this.partMin = min; 
    }
    public int getPartMin() { 
        return partMin; 
    }


    public void setPartMax(int max) { 
        this.partMax = max; 
    }

    public int getPartMax() { 
        return partMax; 
    }
    
    /**
     * validate part and return error message
     * @param name is checked to see if it's empty
     * @param price must be greater than 0
     * @param inv must be greater than 0 and between min and max
     * @param min must be less than max and inv
     * @param max must be greater than min and inv
     * @param exceptionMessage this empty string will carry the message
     * @return the full exception message string
     */
    public static String validPart(String name, double price, int inv, int min, int max, String exceptionMessage) {
        if (name == null) exceptionMessage = exceptionMessage + ("Name cannot be empty. ");         
        if (price < 1) exceptionMessage = exceptionMessage + ("Price must be greater than 0. ");         
        if (inv < 1) exceptionMessage = exceptionMessage + ("Inventory must be greater than 0. ");         
        if (min > max) exceptionMessage = exceptionMessage + ("Min must be less than Max. ");      
        if (inv < min || inv > max) exceptionMessage = exceptionMessage + ("Inventory must be between Min and Max. ");        
        return exceptionMessage;
    }
}
