grammar hardware;

//==========[ Parser ]==========//

start       :   seq EOF
            ;

seq         :   e=element seq                       # Sequence
            | 	   	                                # NOP
            ;

element     :   '.hardware' hardware=IDENTIFIER     # Hardware
            |   '.inputs'   inputs=IDENTIFIER+      # Inputs
            |   '.outputs'  outputs=IDENTIFIER+     # Outputs
            |   latches=latchDecl+                  # Latches
            |   '.update'   updates=updateDecl+     # Update
            |   '.simulate' simulate=simInput       # Simulate
            ;

latchDecl   :   '.latch' triggerID=IDENTIFIER '->' latchID=IDENTIFIER   # LatchDeclaration
            ;

updateDecl  :   id=IDENTIFIER '=' expr          # UpdateDeclaration
            ;

expr	    :   '(' expr ')'                        # Parentheses
            |   '!' expr                            # Negation
            |   expr1=expr ('&&'|'and') expr2=expr  # And
            |   expr1=expr ('||'|'or') expr2=expr   # Or
            |   id=IDENTIFIER                       # Identifier
            ;

simInput    :   id=IDENTIFIER '=' binary=BINARY     # Simulation
            ;

//==========[ Lexer ]==========//

IDENTIFIER  :   [a-zA-Z-] [a-zA-Z0-9_]*
            ;

BINARY      :   ('0'|'1')+
            ;

WHITESPACE  :   [ \t\n]+ -> skip
            ;

COMMENT     :   '//' ~[\n]* -> skip
            ;

COMMENTS    :   '/*'  ( '*'~[/] | ~[*]  )* '*/' -> skip
            ;
