import org.antlr.v4.runtime.tree.ParseTreeVisitor;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import org.antlr.v4.runtime.CharStreams;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class AST {
};

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
    Parantheses(Expr e) { this.e = e; }

    public Boolean eval() {
        return (!(e.eval()));
    }
}

class And extends Expr {
    Expr e1, e2;
    Parantheses(Expr e1, Expr e2) { this.e1 = e1; this.e2 = e2; }

    public Boolean eval() {
        return e1.eval() && e2.eval();
    }
}

class Or extends Expr {
    Expr e1, e2;
    Parantheses(Expr e1, Expr e2) { this.e1 = e1; this.e2 = e2; }

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