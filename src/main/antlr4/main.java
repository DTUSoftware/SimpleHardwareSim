import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import org.antlr.v4.runtime.CharStreams;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class main {
    public static void main(String[] args) throws IOException {

        // we expect exactly one argument: the name of the input file
        if (args.length != 1) {
            System.err.println("\n");
            System.err.println("Hardware Interpreter\n");
            System.err.println("=================\n\n");
            System.err.println("Please give as input argument a filename\n");
            System.exit(-1);
        }
        String filename = args[0];

        // open the input file
        CharStream input = CharStreams.fromFileName(filename);
        //new ANTLRFileStream (filename); // deprecated

        // create a lexer/scanner
        hardwareLexer lex = new hardwareLexer(input);

        // get the stream of tokens from the scanner
        CommonTokenStream tokens = new CommonTokenStream(lex);

        // create a parser
        hardwareParser parser = new hardwareParser(tokens);

        // and parse anything from the grammar for "start"
        ParseTree parseTree = parser.start();

        // Construct an interpreter and run it on the parse tree
        Interpreter interpreter = new Interpreter();
        Element result = (Element) interpreter.visit(parseTree);
        //System.out.println("The result is: "+
        result.eval(new Environment());
    }
}

// We write an interpreter that implements interface
// "hardwareVisitor<T>" that is automatically generated by ANTLR
// This is parameterized over a return type "<T>" which is in our case
// shardwarey a Integer.

class Interpreter extends AbstractParseTreeVisitor<AST> implements hardwareVisitor<AST> {

    @Override
    public AST visitStart(hardwareParser.StartContext ctx) {
        return visit(ctx.seq);
    }

    @Override
    public AST visitElementSequence(hardwareParser.ElementSequenceContext ctx) {
        return new Sequence((Element) visit(ctx.e), (Element) visit(ctx.seq));
    }

    @Override
    public AST visitNOP(hardwareParser.NOPContext ctx) {
        return new NOP();
    }

    @Override
    public AST visitHardware(hardwareParser.HardwareContext ctx) {
        return new Hardware(ctx.hardware.getText());
    }

    @Override
    public AST visitInputs(hardwareParser.InputsContext ctx) {
        List<String> inputs = new ArrayList<>();
        for (int i = 0; i < ctx.getChildCount(); i++) {
            if (ctx.getChild(i) instanceof TerminalNode) {
                inputs.add(ctx.getChild(i).getText());
            }
        }
        return new Inputs(inputs);
    }

    @Override
    public AST visitOutputs(hardwareParser.OutputsContext ctx) {
        List<String> outputs = new ArrayList<>();
        for (int i = 0; i < ctx.getChildCount(); i++) {
            if (ctx.getChild(i) instanceof TerminalNode) {
                outputs.add(ctx.getChild(i).getText());
            }
        }
        return new Outputs(outputs);
    }

    @Override
    public AST visitLatches(hardwareParser.LatchesContext ctx) {
        List<LatchDeclaration> latches = new ArrayList<>();
        for (int i = 0; i < ctx.getChildCount(); i++) {
            if (ctx.getChild(i) instanceof TerminalNode) {
                latches.add((LatchDeclaration) visit(ctx.getChild(i)));
            }
        }
        return new Latches(latches);
    }

    @Override
    public AST visitUpdate(hardwareParser.UpdateContext ctx) {
        List<UpdateDeclaration> updates = new ArrayList<>();
        for (int i = 0; i < ctx.getChildCount(); i++) {
            if (ctx.getChild(i) instanceof TerminalNode) {
                updates.add((UpdateDeclaration) visit(ctx.getChild(i)));
            }
        }
        return new Updates(updates);
    }

    @Override
    public AST visitSimulate(hardwareParser.SimulateContext ctx) {
        return new Simulate((Simulation) visit(ctx.simulate));
    }

    @Override
    public AST visitLatchDeclaration(hardwareParser.LatchDeclarationContext ctx) {
        return new LatchDeclaration(ctx.triggerID.getText(), ctx.latchID.getText());
    }

    @Override
    public AST visitUpdateDeclaration(hardwareParser.UpdateDeclarationContext ctx) {
        return new UpdateDeclaration(ctx.id.getText(), (Expr) visit(ctx.exp));
    }

    @Override
    public AST visitIdentifier(hardwareParser.IdentifierContext ctx) {
        return new Identifier(ctx.id.getText());
    }

    @Override
    public AST visitOr(hardwareParser.OrContext ctx) {
        return new Or((Expr) visit(ctx.exp1), (Expr) visit(ctx.exp2));
    }

    @Override
    public AST visitNegation(hardwareParser.NegationContext ctx) {
        return new Negation((Expr) visit(ctx.exp));
    }

    @Override
    public AST visitAnd(hardwareParser.AndContext ctx) {
        return new And((Expr) visit(ctx.exp1), (Expr) visit(ctx.exp2));
    }

    @Override
    public AST visitParentheses(hardwareParser.ParenthesesContext ctx) {
        return new Parentheses((Expr) visit(ctx.exp));
    }

    @Override
    public AST visitSimulation(hardwareParser.SimulationContext ctx) {
        return new Simulation(ctx.id.getText(), ctx.binary.getText());
    }
}