import org.antlr.v4.runtime.tree.ParseTreeVisitor;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import org.antlr.v4.runtime.CharStreams;

import java.io.IOException;
import java.util.ArrayList;
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
    public String id;
    public Boolean value = null;

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
    public String name;
    public Boolean[] signal;

    public Trace(String name, Boolean[] signal) {this.name = name; this.signal = signal;}
}

class Start extends AST {
    public String hardware;
    public List<String> inputs;
    public List<String> outputs;
    public List<Latch> latches;
    public List<Update> updates;
    public Simulation simulate;
    Start(String hardware,
    List<String> inputs,
    List<String> outputs,
    List<Latch> latches,
    List<Update> updates,
    Simulation simulate) {
        this.hardware = hardware;
        this.inputs = inputs;
        this.outputs = outputs;
        this.latches = latches;
        this.updates = updates;
        this.simulate = simulate;
    }
}

class Latch extends AST {

}

class Update extends  AST {

}

class Simulation extends AST {

}