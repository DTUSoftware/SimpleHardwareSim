import java.util.Arrays;
import java.util.List;

public abstract class AST {
    abstract public void eval(Environment env);
}

//class NOP extends Element {
//    public void eval(Environment env) {
//    }
//}

class Hardware extends AST {
    String name;

    Hardware(String name) {
        this.name = name;
    }

    public void eval(Environment env) {
    }
}

class Inputs extends AST {
    List<String> inputs;

    Inputs(List<String> inputs) {
        this.inputs = inputs;
    }

    public void eval(Environment env) {
    }
}

class Outputs extends AST {
    List<String> outputs;

    Outputs(List<String> outputs) {
        this.outputs = outputs;
    }

    public void eval(Environment env) {
    }
}

class Latches extends AST {
    List<LatchDeclaration> latches;

    Latches(List<LatchDeclaration> latches) {
        this.latches = latches;
    }

    public void eval(Environment env) {
    }
}

class LatchDeclaration extends AST {
    String triggerId;
    String latchId;

    public LatchDeclaration(String triggerId, String latchId) {
        this.triggerId = triggerId;
        this.latchId = latchId;
    }

    @Override
    public void eval(Environment env) {
    }

    public void initialize(Environment env) {
        env.setVariable(triggerId,false);
    }

    public void nextCycle(Environment env, Boolean inputSignal) {
        env.setVariable(triggerId,inputSignal);
    }
}

class Updates extends AST {
    List<UpdateDeclaration> updates;

    Updates(List<UpdateDeclaration> updates) {
        this.updates = updates;
    }

    public void eval(Environment env) {
    }
}

class UpdateDeclaration extends AST {
    String id;
    Expr expr;

    public UpdateDeclaration(String id, Expr expr) {
        this.id = id;
        this.expr = expr;
    }

    @Override
    public void eval(Environment env) {
        env.setVariable(id,expr.eval());
    }
}

class Simulate extends AST {
    Simulation simulation;

    Simulate(Simulation simulation) {
        this.simulation = simulation;
    }

    public void eval(Environment env) {
    }
}

class Simulation extends AST {
    String id;
    String binary;

    public Simulation(String id, String binary) {
        this.id = id;
        this.binary = binary;
    }

    @Override
    public void eval(Environment env) {

    }
}

abstract class Expr extends AST {
    @Override
    public void eval(Environment env) {
        eval();
    }

    abstract public Boolean eval();
}

class Parentheses extends Expr {
    Expr expr;

    Parentheses(Expr expr) {
        this.expr = expr;
    }

    public Boolean eval() {
        return expr.eval();
    }
}

class Negation extends Expr {
    Expr expr;

    Negation(Expr expr) {
        this.expr = expr;
    }

    public Boolean eval() {
        return (!(expr.eval()));
    }
}

class And extends Expr {
    Expr expr1, expr2;

    And(Expr expr1, Expr expr2) {
        this.expr1 = expr1;
        this.expr2 = expr2;
    }

    public Boolean eval() {
        return expr1.eval() && expr2.eval();
    }
}

class Or extends Expr {
    Expr expr1, expr2;

    Or(Expr expr1, Expr expr2) {
        this.expr1 = expr1;
        this.expr2 = expr2;
    }

    public Boolean eval() {
        return expr1.eval() || expr2.eval();
    }
}

class Identifier extends Expr {
    String id;
    Boolean value = null;

    Identifier(String id) {
        this.id = id;
    }

    public Boolean eval() {
        if (this.value == null) {
            System.out.println("Identifier not initialized to value, assuming " + id + "=0");
            return false;
        }
        return value;
    }
}

class Trace extends AST {
    String name;
    Boolean[] signal;

    public Trace(String name, Boolean[] signal) {
        this.name = name;
        this.signal = signal;
    }

    @Override
    public void eval(Environment env) {

    }

    public String toString() {
        StringBuilder output = new StringBuilder();
        for (Boolean s: signal) {
            if (s) {
                output.append(1);
            } else {
                output.append(0);
            }
        }
        return output + " " + name;
    }
}