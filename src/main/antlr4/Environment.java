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
        // Move up the old value to the new cycle
        for (Boolean[] value : variableValues.values()) {
            value[currentCycle + 1] = (value[currentCycle]);
        }
        currentCycle++;
    }

    public void setVariable(String name, Boolean value) {
        if (variableValues.get(name) == null) {
            variableValues.put(name, new Boolean[simulationLength]);
        }
        variableValues.get(name)[currentCycle] = value;
    }

    public Boolean getVariable(String name) {
        // If not defined yet
        Boolean[] value = variableValues.get(name);
        if (value == null) {
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
}
