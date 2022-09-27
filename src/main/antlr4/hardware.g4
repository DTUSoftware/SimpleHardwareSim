grammar hardware;

//==========[ Parser ]==========//

start : cs=commands EOF;

      commands : c=command cs=commands   # Sequence
      	 | 	   	      # NOP
      	 ;

      command : '.hardware' hardware=IDENTIFIER # Hardware
      	| '.inputs'   inputs=IDENTIFIER+  # Inputs
      	| '.outputs'  outputs=IDENTIFIER+  # Outputs
      	| latches=latchDecl+ # Latches
      	| '.update'   updates=updateDecl+ # Update
      	| '.simulate' simulate=simInput # Simulate
      	;

latchDecl   :   '.latch' triggerID=IDENTIFIER '->' latchID=IDENTIFIER   # LatchDeclaration ;

updateDecl  :   id=IDENTIFIER '=' e=expr        # UpdateDeclaration
            ;

expr	    :   '(' e=expr ')'                  # Parentheses
            |   '!' e=expr                      # Negation
            |   e1=expr ('&&'|'and') e2=expr    # And
            |   e1=expr ('||'|'or') e2=expr     # Or
            |   id=IDENTIFIER                   # Identifier
            ;

simInput    :   id=IDENTIFIER '=' binary=BINARY # Simulation  ;

//==========[ Lexer ]==========//

//IDENTIFIER : [a-zA-Z_] [a-zA-Z0-9_]*;
IDENTIFIER  :   [a-zA-Z]+   ;

BINARY      :   ('0'|'1')+  ;

WHITESPACE  :   [ \t\n]+ -> skip    ;
COMMENT     :   '//' ~[\n]* -> skip ;
COMMENTS    :   '/*'  ( '*'~[/] | ~[*]  )* '*/' -> skip ;
