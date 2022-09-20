grammar hardware;

//Parser
start   : '.hardware' hardware=IDENTIFIER
	'.inputs' inputs=IDENTIFIER+
	'.outputs' outputs=IDENTIFIER+
	latches=latchDecl+
	'.update' updates=updateDecl+
	'.simulate' simulate=simInp
	EOF
	;

//Variables
latchDecl 	: '.latch' id=IDENTIFIER '->' latch=IDENTIFIER #Latch ;

updateDecl : id=IDENTIFIER '=' expr #Update ;

expr	: '(' e=expr ')'      #Paramtheses
		| '!' e=expr          #Negation
		| e1=expr '&&' e2=expr    #And
		| e1=expr '||' e2=expr    #Or
		| id=IDENTIFIER        #Identifier
		;

simInp : id=IDENTIFIER '=' binary=BINARY #Simulate;

//Lexer
//IDENTIFIER : [A-Z] [a-zA-Z0-9_]* ;
IDENTIFIER : [a-zA-Z]+ ;

BINARY : ('0'|'1')+ ;


WHITESPACE: [ \t\n]+ -> skip ;
COMMENT: '//' ~[\n]* -> skip ;
MULTILINECOMMENTS :  '/*'  ( '*'~[/] | ~[*]  )* '*/' -> skip; 
