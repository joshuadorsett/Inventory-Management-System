package c482.logic;

/** @author Joshua Paul Dorsett
 * 
 * This class extends part to make an InHouse Part with a machine code
 */
public class InHouse extends Part {
    /**
    * instance attributes
    */
    private int machineId;
    
    /**
     * constructor for InHouse
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
     * Setter for machine ID
     * @param machineId is set into this.machineId
     */
    public void setMachineId(int machineId){
        this.machineId = machineId;
    }

    /**
     * Getter for machine ID
     * @return machine ID
     */
    public int getMachineId(){ 
        return this.machineId; 
    }
}