import java.util.HashMap;
import java.util.Map;

class Environment {
    private HashMap<String, Boolean[]> variableValues = new HashMap<>();
    private int currentCycle = 0;
    private int simulationLength;

    public Environment(int simulationLength) {
        this.simulationLength = simulationLength;
    }

    public void nextCycle() {
//        System.out.println("Starting new environment cycle, current cycle: " + currentCycle);
        // Move up the old value to the new cycle
        for (Boolean[] value : variableValues.values()) {
            //value.add(value.get(currentCycle));
            value[currentCycle+1]=(value[currentCycle]);
        }
        currentCycle++;
    }

    public void setVariable(String name, Boolean value) {
//        System.out.println("Setting " + name + " to " + value);
        if (variableValues.get(name) == null) {
//            System.out.println("it is null, initializing");
            variableValues.put(name, new Boolean[simulationLength]);
        }
//        System.out.println("Adding " + value + " as " + name + "'s " + currentCycle + " item");
//        System.out.println("Before: " + variableValues.get(name).toString());
          variableValues.get(name)[currentCycle] = value;
//        System.out.println("After: " + variableValues.get(name).toString());
    }

    public Boolean getVariable(String name) {
        // If not defined yet
        Boolean[] value = variableValues.get(name);
        if (value == null) {
//            System.err.println("Variable not defined, initializing to false: " + name);
            setVariable(name, false);
            value = variableValues.get(name); // update value
        }
        Boolean boolValue = value[currentCycle];
        if (boolValue == null) {
            System.err.println("Variable not defined: " + name);
            System.exit(-1);
        }
        return boolValue;
    }

    public Boolean[] getValues(String name) {
        return variableValues.get(name);
    }

    public String toString() {
        String table = "";
        for (Map.Entry<String, Boolean[]> entry : variableValues.entrySet()) {
            table += entry.getKey() + "\t-> " + entry.getValue()[currentCycle] + "\n";
        }
        return table;
    }

//    public String toString() {
//        StringBuilder table = new StringBuilder();
//        for (Map.Entry<String, ArrayList<Boolean>> entry : variableValues.entrySet()) {
//            StringBuilder binaryValues = new StringBuilder();
//            for (Boolean value : entry.getValue()) {
//                binaryValues.append(value ? "1" : "0");
//            }
//            table.append(binaryValues.toString()).append(" ").append(entry.getKey()).append("\n");
//        }
//        return table.toString();
//    }
}
