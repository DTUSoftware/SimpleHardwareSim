grammar time;

// Parser:
start   : IDENTIFIER '-' ('-'? INTEGER) FLOAT DATO EOF ;

// Lexer:

IDENTIFIER : [a-zA-Z_][a-zA-Z0-9_]* ;

INTEGER :  [0-9]+ ;

FLOAT : [+-]? [0-9]+ '.' [0-9]+ ;

fragment
DAG : ('0'? [1-9]) | ([12] [0-9]) | ('3'[01] ) ;

fragment
MAANED : ('0'? [1-9]) | ('1' ('0' | '1' | '2')) ;

DATO : DAG '.' MAANED '.' ;

KOMMENTAR : '//' ~[\n]* -> skip ;

FLKOMMENTAR : '/*' ((~[*]) | ('*'~[/]))*  '*/' -> skip;

WHITESPACE : [ \t\n]+ -> skip ;