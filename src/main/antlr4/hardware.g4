grammar hardware;

//==========[ Parser ]==========//

start       :   '.hardware' hardware=IDENTIFIER
                '.inputs'   inputs=IDENTIFIER+
                '.outputs'  outputs=IDENTIFIER+
                            latches=latchDecl+
                '.update'   updates=updateDecl+
                '.simulate' simulate=simInput
                EOF
            ;

// Variables

latchDecl   :   '.latch' triggerID=IDENTIFIER '->' latchID=IDENTIFIER   # Latch ;

updateDecl  :   id=IDENTIFIER '=' e=expr        # Update
            ;

expr	    :   '(' e=expr ')'                  # Parentheses
            |   '!' e=expr                      # Negation
            |   e1=expr ('&&'|'and') e2=expr    # And
            |   e1=expr ('||'|'or') e2=expr     # Or
            |   id=IDENTIFIER                   # Identifier
            ;

simInput    :   id=IDENTIFIER '=' binary=BINARY # Simulation    ;

//==========[ Lexer ]==========//

//IDENTIFIER : [A-Z] [a-zA-Z0-9_]* ;
IDENTIFIER  :   [a-zA-Z]+   ;

BINARY      :   ('0'|'1')+  ;

WHITESPACE  :   [ \t\n]+ -> skip    ;
COMMENT     :   '//' ~[\n]* -> skip ;
COMMENTS    :   '/*'  ( '*'~[/] | ~[*]  )* '*/' -> skip ;
