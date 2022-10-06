import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Environment {
    private HashMap<String, ArrayList<Boolean>> variableValues = new HashMap<>();
    private int currentCycle = 0;

    public Environment() {
    }

    public void nextCycle() {
//        System.out.println("Starting new environment cycle, current cycle: " + currentCycle);
        // Move up the old value to the new cycle
        for (ArrayList<Boolean> value : variableValues.values()) {
            value.add(value.get(currentCycle));
        }
        currentCycle++;
    }

    public void setVariable(String name, Boolean value) {
//        System.out.println("Setting " + name + " to " + value);
        if (variableValues.get(name) == null) {
//            System.out.println("it is null, initializing");
            variableValues.put(name, new ArrayList<>());
        }
//        System.out.println("Adding " + value + " as " + name + "'s " + currentCycle + " item");
//        System.out.println("Before: " + variableValues.get(name).toString());
        if (variableValues.get(name).size() <= currentCycle) {
            variableValues.get(name).add(value);
        }
        else {
            variableValues.get(name).set(currentCycle, value);
        }
//        System.out.println("After: " + variableValues.get(name).toString());
    }

    public Boolean getVariable(String name) {
        // If not defined yet
        ArrayList<Boolean> value = variableValues.get(name);
        if (value == null) {
//            System.err.println("Variable not defined, initializing to false: " + name);
            setVariable(name, false);
            value = variableValues.get(name); // update value
        }
        Boolean boolValue = value.get(currentCycle);
        if (boolValue == null) {
            System.err.println("Variable not defined: " + name);
            System.exit(-1);
        }
        return boolValue;
    }

    public List<Boolean> getValues(String name) {
        return variableValues.get(name);
    }

    public String toString() {
        String table = "";
        for (Map.Entry<String, ArrayList<Boolean>> entry : variableValues.entrySet()) {
            table += entry.getKey() + "\t-> " + entry.getValue().get(currentCycle) + "\n";
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
