grammar GuardCommand;

options {
    language= Java;
    backtrack = true; 
    memoize = true;
    output = AST;
    ASTLabelType = CommonTree;
}

tokens {
    AND = '&';
    OR = '|';
    ASSIGN = ':=';
    SEMI = ';';
    GREATER_THAN = '>';
	GREATER_EQ = '>=';
    LESS_THAN = '<';
	LESS_EQ = '<=';
	EQ = '=';
	NEQ = '!=';
    PLUS = '+';
    MINUS = '-';
    MUL = '*';
    DIV = '/';
    NOT = '!';
    RPAREN = ')';
    LPAREN = '(';
    RCURLY = '}';
    LCURLY = '{';
    COLON = ':';
    IF = 'if';
	FI = 'fi';
    DO = 'do';
	OD = 'od';
	GUARD = '[]';
	ARROW = '->';
    SKIP = 'skip';
	ABORT = 'abort';
    WRITE = 'write';
    READ = 'read';
    MODULE = 'module';
    END = 'end';
	TRUE = 'true';
	FALSE = 'false';
}


    
@lexer::header{
package com.github.mithunder.parser;
}

@parser::header{
package com.github.mithunder.parser;
}

@members {
    public static void main(String[] args) throws Exception {
    
        GuardCommandLexer lex = new GuardCommandLexer(new ANTLRFileStream(args[0]));
       	CommonTokenStream tokens = new CommonTokenStream(lex);

        GuardCommandParser parser = new GuardCommandParser(tokens);

        try {
        
        	parser.abort_cmd();
        
        } catch (RecognitionException e)  {
            e.printStackTrace();
        }
    }
}



/*------------------------------------------------------------------
 * PARSER RULES
 *------------------------------------------------------------------*/

unary_operator
	: NOT
	| MINUS
	;

binary_operator
	: AND
	| OR
	| GREATER_THAN
	| GREATER_EQ
	| LESS_THAN
	| LESS_EQ
	| EQ
	| NEQ
	| PLUS
	| MINUS
	| MUL
	| DIV
	;

expression
	: (INTEGER_LITERAL | TRUE | FALSE | IDENTIFIER | unary_operator expression | LPAREN expression RPAREN) (binary_operator expression)*
	;

command
	: (assignment_cmd | skip_cmd | abort_cmd | read_cmd | write_cmd | LCURLY command RCURLY | if_cmd | do_cmd) (SEMI command)*
	;

assignment_cmd
	: IDENTIFIER ASSIGN expression
	;

skip_cmd
	: SKIP
	;

abort_cmd
	: ABORT
	;

read_cmd
	: READ IDENTIFIER
	;

write_cmd
	: WRITE expression
	;

if_cmd
	: IF guarded_cmd FI
	;

do_cmd
	: DO guarded_cmd OD
	;

guarded_cmd
	: (expression ARROW command) (GUARD guarded_cmd)*
	;
	
program
	: MODULE IDENTIFIER COLON command END
	;
	


/*------------------------------------------------------------------
 * LEXER RULES
 *------------------------------------------------------------------*/

INTEGER_LITERAL : ('0' | '1'..'9' '0'..'9'*);

IDENTIFIER
	:	LETTER (LETTER|'0'..'9')*
	;
	
fragment
LETTER
	:	'$'
	|	'A'..'Z'
	|	'a'..'z'
	|	'_'
	;

WS  :  (' '|'\r'|'\t'|'\u000C'|'\n') 
    {   
    	$channel=HIDDEN;
    }
    ;
