import java.util.List;

public abstract class AST {
}

abstract class Command extends AST {
    abstract public void eval(Environment env);
}

class Sequence extends Command{
    Command c1,c2;
    Sequence(Command c1,Command c2){this.c1=c1; this.c2=c2;}
    public void eval(Environment env){
        c1.eval(env);
        c2.eval(env);
    }
}

class NOP extends Command{
    public void eval(Environment env){};
}

class Hardware extends Command {
    String name;
    Hardware(String name) {this.name=name;}
    public void eval(Environment env) {}
}

class Inputs extends Command {
    List<String> inputs;
    Inputs(List<String>  inputs){this.inputs=inputs;}
    public void eval(Environment env) {}
}

class Outputs extends Command {
    List<String> outputs;
    Outputs(List<String>  outputs){this.outputs=outputs;}
    public void eval(Environment env) {}
}

class Latches extends Command {
    List<LatchDeclaration> latches;
    Latches(List<LatchDeclaration> latches) {this.latches=latches;}
    public void eval(Environment env) {}
}

class LatchDeclaration extends AST {
    String triggerId;
    String latchId;
    public LatchDeclaration(String triggerId, String latchId) {this.triggerId=triggerId; this.latchId=latchId;}
}

class Updates extends Command {
    List<UpdateDeclaration> updates;
    Updates(List<UpdateDeclaration> updates) {this.updates=updates;}
    public void eval(Environment env) {}
}

class UpdateDeclaration extends AST {
    String id;
    Expr e;
    public UpdateDeclaration(String id, Expr e) {this.id=id; this.e=e;}
}

class Simulate extends Command {
    Simulation simulation;
    Simulate(Simulation simulation){this.simulation=simulation;}
    public void eval(Environment env) {}
}

class Simulation extends AST {
    String id;
    String binary;
    public Simulation(String id, String binary) {this.id=id; this.binary=binary;}
}

abstract class Expr extends AST {
    abstract public Boolean eval();
}

class Parantheses extends Expr {
    Expr e;
    Parantheses(Expr e) { this.e = e; }

    public Boolean eval() {
        return e.eval();
    }
}

class Negation extends Expr {
    Expr e;
    Negation(Expr e) { this.e = e; }

    public Boolean eval() {
        return (!(e.eval()));
    }
}

class And extends Expr {
    Expr e1, e2;
    And(Expr e1, Expr e2) { this.e1 = e1; this.e2 = e2; }

    public Boolean eval() {
        return e1.eval() && e2.eval();
    }
}

class Or extends Expr {
    Expr e1, e2;
    Or(Expr e1, Expr e2) { this.e1 = e1; this.e2 = e2; }

    public Boolean eval() {
        return e1.eval() || e2.eval();
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

    public Trace(String name, Boolean[] signal) {this.name = name; this.signal = signal;}
}