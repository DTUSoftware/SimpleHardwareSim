grammar hardware;

//==========[ Parser ]==========//

start       :   '.hardware' hardware=IDENTIFIER
                '.inputs'   inputs+=IDENTIFIER+
                '.outputs'  outputs+=IDENTIFIER+
                latches+=latchDecl+
                '.update'   updates+=updateDecl+
                '.simulate' simulate=simInput
                EOF
            ;

latchDecl   :   '.latch' triggerID=IDENTIFIER '->' latchID=IDENTIFIER   # LatchDeclaration
            ;

updateDecl  :   id=IDENTIFIER '=' exp=expr        # UpdateDeclaration
            ;

expr        :   '(' exp=expr ')'                  # Parentheses
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

WHITESPACE  :   [ \t\n\r]+ -> skip
            ;

COMMENT     :   '//' ~[\n]* -> skip
            ;

COMMENTS    :   '/*'  ( '*'~[/] | ~[*]  )* '*/' -> skip
            ;
