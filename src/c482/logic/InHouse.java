package c482.logic;

/** 
 * This class extends Part class to make an InHouse Part.
 * It adds a machine code for the machine used in house.
 * @author Joshua Paul Dorsett
 */
public class InHouse extends Part {
    /**
    * instance attributes
    */
    private int machineId;
    
    /**
     * Constructor for InHouse class.
     * This calls super() to access the Part constructor for the first 6 arguments.
     * It then sets machine ID argument in this.machineId.
     * @param id int
     * @param name String
     * @param price double
     * @param stock int
     * @param min int
     * @param max int
     * @param machineId int
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId){
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }
    
    /**
     * Setter for machine ID.
     * @param machineId is set into this.machineId for this instance of InHouse.
     */
    public void setMachineId(int machineId){
        this.machineId = machineId;
    }

    /**
     * Getter for machine ID.
     * @return machine ID for this instance of InHouse.
     */
    public int getMachineId(){ 
        return this.machineId; 
    }
}