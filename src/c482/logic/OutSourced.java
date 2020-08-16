package c482.logic;

/** @author Joshua Paul Dorsett
 */
public class OutSourced extends Part {
//    instance attributes
    private String companyName;
   
//    consructor
    public OutSourced() {
        this.companyName = "default";
    }
    
//    set/get company name
    /**
     * @param compName is set into this.companyName
     */
    public void setCompanyName(String compName){this.companyName = compName;}

    /**
     * @return company name
     */
    public String getCompanyName(){return this.companyName;}
}
