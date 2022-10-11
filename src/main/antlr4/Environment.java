import java.util.HashMap;
import java.util.Map;

public class Environment {
    private HashMap<String, Boolean[]> variableValues = new HashMap<>();
    private int currentCycle = 0;
    private int simulationLength;

    public Environment(int simulationLength) {
        this.simulationLength = simulationLength;
    }

    public void nextCycle() {
        // Check for breaking simulation length
        if (currentCycle + 1 > simulationLength) {
            System.out.println("[ERROR]: Environment exceeding simulation length!!!");
            System.exit(0);
        }

        // Move up the old value to the new cycle
        for (Boolean[] value : variableValues.values()) {
            value[currentCycle + 1] = (value[currentCycle]);
        }
        currentCycle++;
    }

    public HashMap<String, Boolean[]> getVariableValues() {
        return variableValues;
    }

    public int getSimulationLength() {
        return simulationLength;
    }

    /**
     * Sets the simulation length.
     * NOTE: If you change the simulation length, and you have arrays, you will HAVE TO
     * regenerate them!
     *
     * @param simulationLength the length of the simulation (binary length)
     */
    public void setSimulationLength(int simulationLength) {
        this.simulationLength = simulationLength;
    }

    public int getCurrentCycle() {
        return currentCycle;
    }

    public void setCurrentCycle(int currentCycle) {
        this.currentCycle = currentCycle;
    }

    public void resetVariable(String name) {
        if (variableValues.get(name) != null) {
            variableValues.put(name, null);
        }
    }

    /**
     * Resets the whole environment.
     * Only used for testing, basically wipes the environment.
     */
    public void reset() {
        setCurrentCycle(0);
        variableValues = new HashMap<>();
    }

    public void setVariable(String name, boolean value) {
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
