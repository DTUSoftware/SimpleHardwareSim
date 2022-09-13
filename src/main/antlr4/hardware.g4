grammar hardware;

// Parser:
start   : '.hardware' IDENTIFIER 
	'.inputs' IDENTIFIER+ 
	'.outputs' IDENTIFIER+ 
	'.latch' IDENTIFIER '->' IDENTIFIER
	'.update' IDENTIFIER '='    
	EOF
	;

// Lexer:
IDENTIFIER : [a-zA-Z_]+ ;

KOMMENTAR : '//' ~[\n]* -> skip ;

FLKOMMENTAR : '/*' ((~[*]) | ('*'~[/]))*  '*/' -> skip;

WHITESPACE : [ \t\n]+ -> skip ;