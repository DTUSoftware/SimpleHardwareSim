grammar hardware;

//Parser
start   : '.hardware' IDENTIFIER 
	'.inputs' IDENTIFIER+ 
	'.outputs' IDENTIFIER+ 
	('.latch' IDENTIFIER '->' IDENTIFIER)+
	'.update' (IDENTIFIER '=' updateIdentifiers)+
	'.simulate' IDENTIFIER '=' ('0'|'1')+    
	EOF
	;

//Variable
updateIdentifiers: '(' updateIdentifiers ')'
		| '!'? IDENTIFIER
		| updateIdentifiers '&&' updateIdentifiers
		| updateIdentifiers '||' updateIdentifiers
		;

//Lexer
IDENTIFIER : [a-zA-Z_]+ ;

HVIDRUM : [ \t\n]+ -> skip ;
KOMMENTAR : '//' ~[\n]* -> skip ;
MULTILINECOMMENTS :  '/*'  ( '*'~[/] | ~[*]  )* '*/' -> skip; 
