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

import com.github.mithunder.statements.Variable;
import com.github.mithunder.statements.VariableTable;
import com.github.mithunder.statements.StatementFactory;
import com.github.mithunder.statements.Statement;
import com.github.mithunder.statements.StatementType;
import com.github.mithunder.statements.Value;
import com.github.mithunder.statements.ValueType;
import com.github.mithunder.statements.ConstantValue;
}

@members {
	StatementFactory statementFactory = StatementFactory.newInstance();
	VariableTable variableTable = VariableTable.newInstance();

    public static void main(String[] args) throws Exception {
    
        GuardCommandLexer lex = new GuardCommandLexer(new ANTLRFileStream(args[0]));
       	CommonTokenStream tokens = new CommonTokenStream(lex);

        GuardCommandParser parser = new GuardCommandParser(tokens);

        try {
        
        	parser.program();
        
        } catch (RecognitionException e)  {
            e.printStackTrace();
        }
    }
}



/*------------------------------------------------------------------
 * PARSER RULES
 *------------------------------------------------------------------*/

unary_operator returns[int type]
	: NOT {$type = StatementType.LOGIC_NOT;}
	| MINUS {$type = StatementType.SIGN_INVERT;}
	;

binary_operator returns[int type]
	: AND {$type = StatementType.LOGIC_AND;}
	| OR {$type = StatementType.LOGIC_OR;}
	| GREATER_THAN {$type = StatementType.GT;}
	| GREATER_EQ {$type = StatementType.GT_EQ;}
	| LESS_THAN {$type = StatementType.LT;}
	| LESS_EQ {$type = StatementType.LT_EQ;}
	| EQ {$type = StatementType.EQ;}
	| NEQ {$type = StatementType.NEQ;}
	| PLUS {$type = StatementType.PLUS;}
	| MINUS {$type = StatementType.MINUS;}
	| MUL {$type = StatementType.MULTIPLY;}
	| DIV {$type = StatementType.DIVIDE;}
	;

expression returns[List<Statement> statList]
	:
	{
		if ($statList == null) {
			$statList = new ArrayList<Statement>();
		}
	}
	(inte=INTEGER_LITERAL
		{$statList.add(statementFactory.createSimpleStatement(
			StatementType.ASSIGN,
			variableTable.createTemporaryVariable(),
			ConstantValue.getConstantValue(ValueType.INTEGER_TYPE, Integer.parseInt(inte.getText()))
		));
		}
	| TRUE
		{$statList.add(statementFactory.createSimpleStatement(
			StatementType.ASSIGN,
			variableTable.createTemporaryVariable(),
			ConstantValue.TRUE
		));
		}
	| FALSE
		{$statList.add(statementFactory.createSimpleStatement(
			StatementType.ASSIGN,
			variableTable.createTemporaryVariable(),
			ConstantValue.FALSE
		));
		}
	| id=IDENTIFIER
		{$statList.add(statementFactory.createSimpleStatement(
			StatementType.ASSIGN,
			variableTable.createTemporaryVariable(),
			variableTable.getVariable(id.getText())
		));
		}
	| u=unary_operator e=expression
		{
		final Statement newestStat = statementFactory.createSimpleStatement(
			u.type,
			e.statList.get(e.statList.size()-1).getAssign(),
			variableTable.getVariable(id.getText())
		);
		$statList.addAll(e.statList);
		$statList.add(newestStat);
		}
	| LPAREN e=expression RPAREN {$statList.addAll(e.statList);}
	)
	(b=binary_operator e=expression
		{
			final Value valStart = $statList.get($statList.size()-1).getAssign();
			final Value valEnd = e.statList.get(e.statList.size()-1).getAssign();
			$statList.addAll(e.statList);
			final Statement binaryStat = statementFactory.createSimpleStatement(
				b.type, variableTable.createTemporaryVariable(), valStart, valEnd
			);
			$statList.add(binaryStat);
		}
	)*
	;

command returns [List<Statement> commands]
	:
	{
		if ($commands == null) {
			$commands = new ArrayList<Statement>();
		}
	}
	
	( a=assignment_cmd			{$commands.addAll(a.commands);}
	| b=skip_cmd				{$commands.add(b.command);}
	| c=abort_cmd				{$commands.add(c.command);}
	| d=read_cmd				{$commands.add(d.command);}
	| e=write_cmd				{$commands.addAll(e.commands);}
	| LCURLY f=command RCURLY
		{
			$commands.add(statementFactory.createCompoundStatement(StatementType.SCOPE, f.commands));
		}
	| g=if_cmd 					{$commands.add(g.command);}
	| h=do_cmd 					{$commands.add(h.command);}
	)
	
	(SEMI i=command
		{
			$commands.addAll(i.commands);
		}
	)*
	;

assignment_cmd returns [List<Statement> commands]
	: id=IDENTIFIER ASSIGN e=expression
		{
			final Statement assignStatement = statementFactory.createSimpleStatement(
				StatementType.ASSIGN,
				variableTable.getVariable(id.getText()), e.statList.get(e.statList.size()-1).getAssign()
			);
			e.statList.add(assignStatement);
			$commands = e.statList;
		}
	;

skip_cmd returns [Statement command]
	: SKIP {$command = statementFactory.createSimpleStatement(StatementType.SKIP);}
	;

abort_cmd returns [Statement command]
	: ABORT {$command = statementFactory.createSimpleStatement(StatementType.ABORT);}
	;

read_cmd returns [Statement command]
	: READ id=IDENTIFIER
		{
			$command = statementFactory.createSimpleStatement(
				StatementType.READ, variableTable.getVariable(id.getText())
			);
		}
	;

write_cmd returns [List<Statement> commands]
	: WRITE expression e=expression
		{
			final Statement assignStatement = statementFactory.createSimpleStatement(
				StatementType.WRITE,
				null, e.statList.get(e.statList.size()-1).getAssign()
			);
			e.statList.add(assignStatement);
			$commands = e.statList;
		}
	;

if_cmd returns [Statement command]
	: IF gc=guarded_cmd FI
		{
			$command = statementFactory.createCompoundStatement(StatementType.IF, gc.commands);
		}
	;

do_cmd returns [Statement command]
	: DO gc=guarded_cmd OD
		{
			$command = statementFactory.createCompoundStatement(StatementType.DO, gc.commands);
		}
	;

guarded_cmd returns [List<Statement> commands]
	:
	{
		if ($commands == null) {
			$commands = new ArrayList<Statement>();
		}
	}
	(e=expression ARROW c=command) {
		$commands.add(statementFactory.createCompoundStatement(StatementType.SCOPE, e.statList));
		$commands.add(statementFactory.createCompoundStatement(StatementType.SCOPE, c.commands));
	}
	(GUARD gc=guarded_cmd {$commands.addAll(gc.commands);} )*
	;
	
program returns [List<Statement> commands]
	: MODULE IDENTIFIER COLON c=command END {$commands = c.commands;}
	;
	


/*------------------------------------------------------------------
 * LEXER RULES
 *------------------------------------------------------------------*/

INTEGER_LITERAL : ('0' | '1'..'9' '0'..'9'*);

IDENTIFIER : (b=LETTER count=(d=LETTER|e='0'..'9')*);
	
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
