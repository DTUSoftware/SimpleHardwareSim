grammar html;

// Parser:
start   : html* EOF ;

html : '<b>' html* '</b>'
     | '<em>' html* '</em>'
     | BEGINTOKEN html* ENDTOKEN
     | '<html>' html* '</html>'
     | TEXT
     ;

// Lexer: 

BEGINTOKEN : '<' ID '>' ;

ENDTOKEN : '</' ID '>' ;

fragment
ID : [a-zA-Z][a-zA-Z0-9_]* ; 

TEXT : ~[<>/ \t\n]+ ;

WHITESPACE : [ \t\n] -> skip;




/*
regex : CHARACTER
      | EPSILON
      | regexp '|' regexp
      | regexp regexp
      | regexp '*'
      ;
*/
