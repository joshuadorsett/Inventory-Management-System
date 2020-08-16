package c482.logic;

/** @author Joshua Paul Dorsett
 */
public class InHouse extends Part {
//    instance attributes
    private int machineId;
    
//    constructor
    public InHouse(){
        this.machineId = 0;
    }
    
//    set/get machine id
    /**
     * @param machineId is set into this.machineId
     */
    public void setMachineId(int machineId){
        this.machineId = machineId;
    }

    /**
     * @return machine ID
     */
    public int getMachineId(){ 
        return this.machineId; 
    }
}