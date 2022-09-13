grammar hardware;

// Parser:
start   : EOF ;

// Lexer:

KOMMENTAR : '//' ~[\n]* -> skip ;

FLKOMMENTAR : '/*' ((~[*]) | ('*'~[/]))*  '*/' -> skip;

WHITESPACE : [ \t\n]+ -> skip ;