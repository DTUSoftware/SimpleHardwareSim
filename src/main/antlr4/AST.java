import java.util.List;

public abstract class AST {
}

abstract class Element extends AST {
    abstract public void eval(Environment env);
}

class Sequence extends Element {
    Element e1, e2;

    Sequence(Element e1, Element e2) {
        this.e1 = e1;
        this.e2 = e2;
    }

    public void eval(Environment env) {
        e1.eval(env);
        e2.eval(env);
    }
}

class NOP extends Element {
    public void eval(Environment env) {
    }
}

class Hardware extends Element {
    String name;

    Hardware(String name) {
        this.name = name;
    }

    public void eval(Environment env) {
    }
}

class Inputs extends Element {
    List<String> inputs;

    Inputs(List<String> inputs) {
        this.inputs = inputs;
    }

    public void eval(Environment env) {
    }
}

class Outputs extends Element {
    List<String> outputs;

    Outputs(List<String> outputs) {
        this.outputs = outputs;
    }

    public void eval(Environment env) {
    }
}

class Latches extends Element {
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
}

class Updates extends Element {
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
}

class Simulate extends Element {
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
}

abstract class Expr extends AST {
    abstract public Boolean eval();
}

class Parantheses extends Expr {
    Expr expr;

    Parantheses(Expr expr) {
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
}