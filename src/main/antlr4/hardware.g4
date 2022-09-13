grammar hardware;

//Parser
start   : '.hardware' IDENTIFIER 
	'.inputs' IDENTIFIER+ 
	'.outputs' IDENTIFIER+ 
	'.latch' IDENTIFIER '->' IDENTIFIER
	'.update' IDENTIFIER '=' bool
	'.simulate' IDENTIFIER '=' ('0'|'1')+    
	EOF
	;

//Variable
bool : '!'? IDENTIFIER '&&' '!'? IDENTIFIER ;

//Lexer
IDENTIFIER : [a-zA-Z_]+ ;

HVIDRUM : [ \t\n]+ -> skip ;
KOMMENTAR : '//' ~[\n]* -> skip ;
MULTILINECOMMENTS :  '/*'  ( '*'~[/] | ~[*]  )* '*/' -> skip; 
