import java.util.ArrayList;
import java.util.List;

public abstract class AST {
}

class Circuit extends AST {
    private Hardware hardware;
    private Inputs inputs;
    private Outputs outputs;
    private Latches latches;
    private Updates updates;
    private Simulate simulate;

    public Circuit(Hardware hardware, Inputs inputs, Outputs outputs, Latches latches, Updates updates, Simulate simulate) {
        this.hardware = hardware;
        this.inputs = inputs;
        this.outputs = outputs;
        this.latches = latches;
        this.updates = updates;
        this.simulate = simulate;
    }

    public Hardware getHardware() {
        return hardware;
    }

    public Inputs getInputs() {
        return inputs;
    }

    public Outputs getOutputs() {
        return outputs;
    }

    public Latches getLatches() {
        return latches;
    }

    public Updates getUpdates() {
        return updates;
    }

    public Simulate getSimulator() {
        return simulate;
    }

    public void runSimulator(Environment env) {
        int n = simulate.getSimulation().getBinaryLength();
        initialize(env);
        for (int i = 0; i < n; i++) {
            nextCycle(env, i);
            if (i + 1 < n) {
                // Start next cycle in environment
                env.nextCycle();
            }
        }
        printOutput(env);
    }

    public void initialize(Environment env) {
        inputs.eval(env);
        outputs.eval(env);
        for (UpdateDeclaration update : updates.getUpdates()) {
            env.setVariable(update.getID(), false);
        }
        latches.eval(env);
    }

    public void nextCycle(Environment env, int i) {
        // Input
        env.setVariable(inputs.getInputs().get(0), simulate.getSimulation().getBinary().charAt(i) == '1');
        // Update latches
        latches.eval(env);
        // Run updates
        updates.eval(env);
    }

    public void printOutput(Environment env) {
        ArrayList<String> variables = new ArrayList<>();
        variables.addAll(inputs.getInputs());
        variables.addAll(outputs.getOutputs());
        for (String variable : variables) {
            Trace binaryTrace = new Trace(variable, env.getValues(variable));
            System.out.println(binaryTrace + " " + variable);
        }
    }
}


class Hardware extends AST {
    private String name;

    public Hardware(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

class Inputs extends AST {
    private List<String> inputs;

    public Inputs(List<String> inputs) {
        this.inputs = inputs;
    }

    public List<String> getInputs() {
        return inputs;
    }

    public void eval(Environment env) {
        for (String input : inputs) {
            env.setVariable(input, false);
        }
    }
}

class Outputs extends AST {
    private List<String> outputs;

    public Outputs(List<String> outputs) {
        this.outputs = outputs;
    }

    public List<String> getOutputs() {
        return outputs;
    }

    public void eval(Environment env) {
        for (String output : outputs) {
            env.setVariable(output, false);
        }
    }
}

class Latches extends AST {
    private List<LatchDeclaration> latches;

    public Latches(List<LatchDeclaration> latches) {
        this.latches = latches;
    }

    public List<LatchDeclaration> getLatches() {
        return latches;
    }

    public void eval(Environment env) {
        for (LatchDeclaration latch : latches) {
            latch.eval(env);
        }
    }
}

class LatchDeclaration extends AST {
    private String triggerId;
    private String latchId;

    public LatchDeclaration(String triggerId, String latchId) {
        this.triggerId = triggerId;
        this.latchId = latchId;
    }

    public void eval(Environment env) {
        env.setVariable(latchId, env.getVariable(triggerId));
    }
}

class Updates extends AST {
    private List<UpdateDeclaration> updates;

    Updates(List<UpdateDeclaration> updates) {
        this.updates = updates;
    }

    public List<UpdateDeclaration> getUpdates() {
        return updates;
    }

    public void eval(Environment env) {
        for (UpdateDeclaration update : updates) {
            update.eval(env);
        }
    }
}

class UpdateDeclaration extends AST {
    private String id;
    private Expr expr;

    public UpdateDeclaration(String id, Expr expr) {
        this.id = id;
        this.expr = expr;
    }

    public String getID() {
        return id;
    }

    public void eval(Environment env) {
        env.setVariable(id, expr.eval(env));
    }
}

class Simulate extends AST {
    private Simulation simulation;

    Simulate(Simulation simulation) {
        this.simulation = simulation;
    }

    public Simulation getSimulation() {
        return simulation;
    }
}

class Simulation extends AST {
    private String id;
    private String binary;

    public Simulation(String id, String binary) {
        this.id = id;
        this.binary = binary;
    }

    public String getID() {
        return id;
    }

    public String getBinary() {
        return binary;
    }

    public int getBinaryLength() {
        return binary.length();
    }
}

abstract class Expr extends AST {
    abstract public Boolean eval(Environment env);
}

class Parentheses extends Expr {
    private Expr expr;

    public Parentheses(Expr expr) {
        this.expr = expr;
    }

    public Boolean eval(Environment env) {
        return expr.eval(env);
    }
}

class Negation extends Expr {
    private Expr expr;

    public Negation(Expr expr) {
        this.expr = expr;
    }

    public Boolean eval(Environment env) {
        return (!(expr.eval(env)));
    }
}

class And extends Expr {
    private Expr expr1, expr2;

    public And(Expr expr1, Expr expr2) {
        this.expr1 = expr1;
        this.expr2 = expr2;
    }

    public Boolean eval(Environment env) {
        return expr1.eval(env) && expr2.eval(env);
    }
}

class Or extends Expr {
    private Expr expr1, expr2;

    public Or(Expr expr1, Expr expr2) {
        this.expr1 = expr1;
        this.expr2 = expr2;
    }

    public Boolean eval(Environment env) {
        return expr1.eval(env) || expr2.eval(env);
    }
}

class Identifier extends Expr {
    private String id;

    public Identifier(String id) {
        this.id = id;
    }

    public Boolean eval(Environment env) {
        if (env.getVariable(id) == null) {
            // If the variable doesn't exist, create it and initialize it to false
            System.out.println("Identifier not initialized to value, assuming " + id + "=0");
            env.setVariable(id, false); // set to false
            return env.getVariable(id);
        }
        return env.getVariable(id);
    }
}

class Trace extends AST {
    private String name;
    private Boolean[] signal;

    public Trace(String name, Boolean[] signal) {
        this.name = name;
        this.signal = signal;
    }

    public String toString() {
        StringBuilder binaryValues = new StringBuilder();
        for (Boolean value : signal) {
            binaryValues.append(value ? "1" : "0");
        }
        return binaryValues.toString();
    }
}
