package c482.logic;

/** @author Joshua Paul Dorsett
 * 
 * This class extends the part to make an outsourced part with an associated company name
 */
public class OutSourced extends Part {
    /**
    * instance attributes
    */
    private String companyName;

    /**
     * constructor for OutSouced
     * @param id int
     * @param name String
     * @param price double
     * @param stock int
     * @param min int
     * @param max int
     * @param companyName String
     */
    public OutSourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }
    
    /**
     * Setter for company name
     * @param compName is set into this.companyName
     */
    public void setCompanyName(String compName){this.companyName = compName;}

    /**
     * Getter for company name
     * @return company name
     */
    public String getCompanyName(){return this.companyName;}
}
