grammar impl;

start   : cs=commands EOF;  

commands : c=command cs=commands   # Sequence
	 | 	   	      # NOP
	 ;

// commands : command* ;


command : x=IDENTIFIER '=' e=expr ';'          # Assignment
	| 'while' '(' c=condition ')' b=block  # While
	| 'output' '(' e=expr ')'  ';'  # Output
	;
	
block : '{' cs=commands '}'     # MultiCommand
      | c=command	     	     # SingleCommand
      ;

condition : e1=expr op=('>'|'<'|'=='|'!=') e2=expr ;

expr : e1=expr op=('*'|'/') e2=expr   # Multiplication
     | e1=expr op=('+'|'-') e2=expr   # Addition
     | '(' e1=expr ')'   	   # Parentheses
     | c=CONST		     	   # Constant
     | x=IDENTIFIER	     	   # Variable
     ;

IDENTIFIER : [a-zA-Z_] [a-zA-Z0-9_]*;

CONST : [0-9]+ ('.' [0-9]+)? ;

HVIDRUM : [ \t\n]+ -> skip ;
KOMMENTAR : '//' ~[\n]* -> skip ;
MULTILINECOMMENTS :  '/*'  ( '*'~[/] | ~[*]  )* '*/' -> skip; 

