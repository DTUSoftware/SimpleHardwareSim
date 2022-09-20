grammar hardware;

//Parser
start   : '.hardware' IDENTIFIER 
	'.inputs' IDENTIFIER+ 
	'.outputs' IDENTIFIER+ 
	latchDecl+
	'.update' updateDecl+
	'.simulate' IDENTIFIER '=' ('0'|'1')+    
	EOF
	;

//Variable
latchDecl 	: '.latch' IDENTIFIER '->' IDENTIFIER ;

updateDecl : IDENTIFIER '=' expr ;

expr	: '(' expr ')'
		| '!' expr
		| expr '&&' expr
		| expr '||' expr
		| IDENTIFIER
		;

//Lexer
//IDENTIFIER : [A-Z] [a-zA-Z0-9_]* ;
IDENTIFIER : [a-zA-Z]+ ;


WHITESPACE: [ \t\n]+ -> skip ;
COMMENT: '//' ~[\n]* -> skip ;
MULTILINECOMMENTS :  '/*'  ( '*'~[/] | ~[*]  )* '*/' -> skip; 
