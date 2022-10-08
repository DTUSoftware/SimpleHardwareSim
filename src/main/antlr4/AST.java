import java.util.ArrayList;
import java.util.List;

public abstract class AST {
}

class Circuit extends AST {
    Hardware hardware;
    Inputs inputs;
    Outputs outputs;
    Latches latches;
    Updates updates;
    Simulate simulate;

    Circuit(Hardware hardware, Inputs inputs, Outputs outputs, Latches latches, Updates updates, Simulate simulate) {
        this.hardware = hardware;
        this.inputs = inputs;
        this.outputs = outputs;
        this.latches = latches;
        this.updates = updates;
        this.simulate = simulate;
    }

    public void runSimulator(Environment env) {
        int n = simulate.getSimulationLength();
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
        for (UpdateDeclaration update : updates.updates) {
            env.setVariable(update.id, false);
        }
        latches.eval(env);
    }

    public void nextCycle(Environment env, int i) {
        // Input
        env.setVariable(inputs.inputs.get(0), simulate.simulation.binary.charAt(i) == '1');
        // Update latches
        latches.eval(env);
        // Run updates
        updates.eval(env);
    }

    public void printOutput(Environment env) {
        ArrayList<String> variables = new ArrayList<>();
        variables.addAll(inputs.inputs);
        variables.addAll(outputs.outputs);
        for (String variable : variables) {
            Trace binaryTrace = new Trace(variable, env.getValues(variable));
            System.out.println(binaryTrace + " " + variable);
        }
    }
}


class Hardware extends AST {
    String name;

    Hardware(String name) {
        this.name = name;
    }
}

class Inputs extends AST {
    List<String> inputs;

    Inputs(List<String> inputs) {
        this.inputs = inputs;
    }

    public void eval(Environment env) {
        for (String input : inputs) {
            env.setVariable(input, false);
        }
    }
}

class Outputs extends AST {
    List<String> outputs;

    Outputs(List<String> outputs) {
        this.outputs = outputs;
    }

    public void eval(Environment env) {
        for (String output : outputs) {
            env.setVariable(output, false);
        }
    }
}

class Latches extends AST {
    List<LatchDeclaration> latches;

    Latches(List<LatchDeclaration> latches) {
        this.latches = latches;
    }

    public void eval(Environment env) {
        for (LatchDeclaration latch : latches) {
            latch.eval(env);
        }
    }
}

class LatchDeclaration extends AST {
    String triggerId;
    String latchId;

    public LatchDeclaration(String triggerId, String latchId) {
        this.triggerId = triggerId;
        this.latchId = latchId;
    }

    public void eval(Environment env) {
        env.setVariable(latchId, env.getVariable(triggerId));
    }
}

class Updates extends AST {
    List<UpdateDeclaration> updates;

    Updates(List<UpdateDeclaration> updates) {
        this.updates = updates;
    }

    public void eval(Environment env) {
        for (UpdateDeclaration update : updates) {
            update.eval(env);
        }
    }
}

class UpdateDeclaration extends AST {
    String id;
    Expr expr;

    public UpdateDeclaration(String id, Expr expr) {
        this.id = id;
        this.expr = expr;
    }

    public void eval(Environment env) {
        env.setVariable(id, expr.eval(env));
    }
}

class Simulate extends AST {
    Simulation simulation;

    Simulate(Simulation simulation) {
        this.simulation = simulation;
    }

    public int getSimulationLength() {
        return simulation.binary.length();
    }
}

class Simulation extends AST {
    String id;
    String binary;

    public Simulation(String id, String binary) {
        this.id = id;
        this.binary = binary;
    }
}

abstract class Expr extends AST {

    abstract public Boolean eval(Environment env);
}

class Parentheses extends Expr {
    Expr expr;

    Parentheses(Expr expr) {
        this.expr = expr;
    }

    public Boolean eval(Environment env) {
        return expr.eval(env);
    }
}

class Negation extends Expr {
    Expr expr;

    Negation(Expr expr) {
        this.expr = expr;
    }

    public Boolean eval(Environment env) {
        return (!(expr.eval(env)));
    }
}

class And extends Expr {
    Expr expr1, expr2;

    And(Expr expr1, Expr expr2) {
        this.expr1 = expr1;
        this.expr2 = expr2;
    }

    public Boolean eval(Environment env) {
        return expr1.eval(env) && expr2.eval(env);
    }
}

class Or extends Expr {
    Expr expr1, expr2;

    Or(Expr expr1, Expr expr2) {
        this.expr1 = expr1;
        this.expr2 = expr2;
    }

    public Boolean eval(Environment env) {
        return expr1.eval(env) || expr2.eval(env);
    }
}

class Identifier extends Expr {
    String id;

    Identifier(String id) {
        this.id = id;
    }

    public Boolean eval(Environment env) {
        if (env.getVariable(id) == null) {
            System.out.println("Identifier not initialized to value, assuming " + id + "=0");
            env.setVariable(id, false); // set to false
            return env.getVariable(id);
        }
        return env.getVariable(id);
    }
}

class Trace extends AST {
    String name;
    Boolean[] signal;

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
