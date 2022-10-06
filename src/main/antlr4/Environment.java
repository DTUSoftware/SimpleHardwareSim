import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class Environment {
    private HashMap<String, ArrayList<Boolean>> variableValues = new HashMap<>();
    private int currentCycle = 0;

    public Environment() {
    }

    public void nextCycle() {
        // Move up the old value to the new cycle
        for (ArrayList<Boolean> value : variableValues.values()) {
            value.add(value.get(currentCycle));
        }
        currentCycle++;
    }

    public void setVariable(String name, Boolean value) {
        if (variableValues.get(name) == null) {
            variableValues.put(name, new ArrayList<>());
        }
        variableValues.get(name).add(currentCycle, value);
    }

    public Boolean getVariable(String name) {
        // If not defined yet
        ArrayList<Boolean> value = variableValues.get(name);
        if (value == null) {
            System.err.println("Variable not defined, initializing to false: " + name);
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

    public String toString() {
        String table = "";
        for (Map.Entry<String, ArrayList<Boolean>> entry : variableValues.entrySet()) {
            table += entry.getKey() + "\t-> " + entry.getValue().get(currentCycle) + "\n";
        }
        return table;
    }
}
