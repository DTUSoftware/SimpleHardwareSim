grammar hardware;

//==========[ Parser ]==========//

start       :   seq=sequence EOF
            ;

sequence    :   e=element seq=sequence            # ElementSequence
            | 	   	                              # NOP
            ;

element     :   '.hardware' hardware=IDENTIFIER   # Hardware
            |   '.inputs'   inputs=IDENTIFIER+    # Inputs
            |   '.outputs'  outputs=IDENTIFIER+   # Outputs
            |   latches=latchDecl+                # Latches
            |   '.update'   updates=updateDecl+   # Update
            |   '.simulate' simulate=simInput     # Simulate
            ;

latchDecl   :   '.latch' triggerID=IDENTIFIER '->' latchID=IDENTIFIER   # LatchDeclaration
            ;

updateDecl  :   id=IDENTIFIER '=' exp=expr        # UpdateDeclaration
            ;

expr	    :   '(' exp=expr ')'                  # Parentheses
            |   '!' exp=expr                      # Negation
            |   exp1=expr ('&&'|'and') exp2=expr  # And
            |   exp1=expr ('||'|'or') exp2=expr   # Or
            |   id=IDENTIFIER                     # Identifier
            ;

simInput    :   id=IDENTIFIER '=' binary=BINARY   # Simulation
            ;

//==========[ Lexer ]==========//

IDENTIFIER  :   [a-zA-Z_] [a-zA-Z0-9_]*
            ;

BINARY      :   ('0'|'1')+
            ;

WHITESPACE  :   [ \t\n]+ -> skip
            ;

COMMENT     :   '//' ~[\n]* -> skip
            ;

COMMENTS    :   '/*'  ( '*'~[/] | ~[*]  )* '*/' -> skip
            ;
