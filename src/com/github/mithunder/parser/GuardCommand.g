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

import com.github.mithunder.statements.VariableTable;
import com.github.mithunder.statements.StatementFactory;
import com.github.mithunder.statements.Statement;
import com.github.mithunder.statements.StatementType;
import com.github.mithunder.statements.Value;
import com.github.mithunder.statements.ValueType;
import com.github.mithunder.statements.ConstantValue;
import com.github.mithunder.statements.CodeLocation;
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
    
    //Adds everything to the first statement list given.
    private void handleExpression(List<Statement> statList, List<Statement> eStatList,
    	int binaryType, CommonTree bTree) {
    	
		final Value valStart = statList.get(statList.size()-1).getAssign();
		final Value valEnd = eStatList.get(eStatList.size()-1).getAssign();
		statList.addAll(eStatList);
		final Statement binaryStat = statementFactory.createSimpleStatement(
			binaryType, new CodeLocation(bTree.getLine()),
			null, variableTable.createTemporaryVariable(), valStart, valEnd
		);
		statList.add(binaryStat);
    }
}



/*------------------------------------------------------------------
 * PARSER RULES
 *------------------------------------------------------------------*/



//		* Operators. *

unary_operator returns[int type]
	: NOT {$type = StatementType.LOGIC_NOT;}
	| MINUS {$type = StatementType.SIGN_INVERT;}
	;
	
or returns[int type] : OR {$type = StatementType.LOGIC_OR;};

and returns[int type] : AND {$type = StatementType.LOGIC_AND;};

eq returns[int type] : EQ {$type = StatementType.EQ;};
neq returns[int type] : NEQ {$type = StatementType.NEQ;};
eqa returns[int type]
	: (a=eq {$type = a.type;})
	| (b=neq {$type = b.type;})
	;

gt returns[int type] : GREATER_THAN {$type = StatementType.GT;};
gt_eq returns[int type] : GREATER_EQ {$type = StatementType.GT_EQ;};
lt returns[int type] : LESS_THAN {$type = StatementType.LT;};
lt_eq returns[int type] : LESS_EQ {$type = StatementType.LT_EQ;};
rel returns[int type]
	: (a=gt {$type = a.type;})
	| (b=gt_eq {$type = b.type;})
	| (c=lt {$type = c.type;})
	| (d=lt_eq {$type = d.type;})
	;

plus returns[int type] : PLUS {$type = StatementType.PLUS;};
minus returns[int type] : MINUS {$type = StatementType.MINUS;};
term returns[int type]
	: (a=plus {$type = a.type;})
	| (b=minus {$type = b.type;})
	;

mul returns[int type] : MUL {$type = StatementType.MULTIPLY;};
div returns[int type] : DIV {$type = StatementType.DIVIDE;};
factor returns[int type]
	: (a=mul {$type = a.type;})
	| (b=div {$type = b.type;})
	;
	
	
	
//		* Operator precedence and literals. *
	
expression returns[List<Statement> statList]
	: expr_or
	;

expr_or returns [List<Statement> statList]
	:
	(e1=expr_and b=or e2=expr_and
		{$statList = e1.statList; handleExpression(e1.statList, e2.statList, b.type, b.tree);}
	)
	(b=or e3=expr_and {handleExpression($statList, e3.statList, b.type, b.tree);})*
	| (e4=expr_and) {$statList = e4.statList;}
	;
	
expr_and returns [List<Statement> statList]
	:
	(e1=expr_eqa b=and e2=expr_eqa
		{$statList = e1.statList; handleExpression(e1.statList, e2.statList, b.type, b.tree);}
	)
	(b=and e3=expr_eqa {handleExpression($statList, e3.statList, b.type, b.tree);})*
	| (e4=expr_eqa) {$statList = e4.statList;}
	;
	
//Both eq and neq.
expr_eqa returns [List<Statement> statList]
	:
	(e1=expr_rel b=eqa e2=expr_rel
		{$statList = e1.statList; handleExpression(e1.statList, e2.statList, b.type, b.tree);}
	)
	(b=eqa e3=expr_rel {handleExpression($statList, e3.statList, b.type, b.tree);})*
	| (e4=expr_rel) {$statList = e4.statList;}
	;

expr_rel returns [List<Statement> statList]
	:
	(e1=expr_term b=rel e2=expr_term
		{$statList = e1.statList; handleExpression(e1.statList, e2.statList, b.type, b.tree);}
	)
	(b=rel e3=expr_term {handleExpression($statList, e3.statList, b.type, b.tree);})*
	| (e4=expr_term) {$statList = e4.statList;}
	;
	
expr_term returns [List<Statement> statList]
	:
	(e1=expr_factor b=term e2=expr_factor
		{$statList = e1.statList; handleExpression(e1.statList, e2.statList, b.type, b.tree);}
	)
	(b=term e3=expr_factor {handleExpression($statList, e3.statList, b.type, b.tree);})*
	| (e4=expr_factor) {$statList = e4.statList;}
	;
	
expr_factor returns [List<Statement> statList]
	:
	(e1=literal b=factor e2=literal
		{$statList = e1.statList; handleExpression(e1.statList, e2.statList, b.type, b.tree);}
	)
	(b=factor e3=literal {handleExpression($statList, e3.statList, b.type, b.tree);})*
	| (e4=literal) {$statList = e4.statList;}
	;

expr_unary returns [List<Statement> statList]
	:
	{
		if ($statList == null) {
			$statList = new ArrayList<Statement>();
		}
	}
	( u=unary_operator e=expression
		{
		final Statement newestStat = statementFactory.createSimpleStatement(
			u.type,
			new CodeLocation(u.tree.getLine()),
			null,
			variableTable.createTemporaryVariable(),
			e.statList.get(e.statList.size()-1).getAssign()
		);
		$statList.addAll(e.statList);
		$statList.add(newestStat);
		}
	| l=literal {$statList = l.statList;}
	)
	;
	
literal returns[List<Statement> statList]
	:
	{
		if ($statList == null) {
			$statList = new ArrayList<Statement>();
		}
	}
	inte=INTEGER_LITERAL
		{$statList.add(statementFactory.createSimpleStatement(
			StatementType.ASSIGN,
			new CodeLocation(inte_tree.getLine()),
			null,
			variableTable.createTemporaryVariable(),
			ConstantValue.getConstantValue(ValueType.INTEGER_TYPE, Integer.parseInt(inte.getText()))
		));
		}
	| tru=TRUE
		{$statList.add(statementFactory.createSimpleStatement(
			StatementType.ASSIGN,
			new CodeLocation(tru_tree.getLine()),
			null,
			variableTable.createTemporaryVariable(),
			ConstantValue.TRUE
		));
		}
	| fal=FALSE
		{$statList.add(statementFactory.createSimpleStatement(
			StatementType.ASSIGN,
			new CodeLocation(fal_tree.getLine()),
			null,
			variableTable.createTemporaryVariable(),
			ConstantValue.FALSE
		));
		}
	| id=IDENTIFIER
		{$statList.add(statementFactory.createSimpleStatement(
			StatementType.ASSIGN,
			new CodeLocation(id_tree.getLine()),
			null,
			variableTable.createTemporaryVariable(),
			variableTable.getVariable(id.getText())
		));
		}
	| LPAREN e=expression RPAREN {$statList = e.statList;}
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
	| l=LCURLY f=command RCURLY
		{
			$commands.add(statementFactory.createCompoundStatement(
				StatementType.SCOPE, new CodeLocation(l_tree.getLine()), null, f.commands
			));
		}
	| g=if_cmd 					{$commands.add(g.command);}
	| h=do_cmd 					{$commands.add(h.command);}
	)
	
	(SEMI i=command
		{
			//TODO: Ensure that skip statements are not added.
			$commands.addAll(i.commands);
		}
	)*
	;

assignment_cmd returns [List<Statement> commands]
	: id=IDENTIFIER as=ASSIGN e=expression
		{
			final Statement assignStatement = statementFactory.createSimpleStatement(
				StatementType.ASSIGN, new CodeLocation(as_tree.getLine()), null,
				variableTable.getVariable(id.getText()), e.statList.get(e.statList.size()-1).getAssign()
			);
			e.statList.add(assignStatement);
			$commands = e.statList;
		}
	;

skip_cmd returns [Statement command]
	: s=SKIP
		{
			$command = statementFactory.createSimpleStatement(
				StatementType.SKIP, new CodeLocation(s_tree.getLine()), null
			);
		}
	;

abort_cmd returns [Statement command]
	: a=ABORT
		{
			$command = statementFactory.createSimpleStatement(
				StatementType.ABORT, new CodeLocation(a_tree.getLine()), null
			);
		}
	;

read_cmd returns [Statement command]
	: rea=READ id=IDENTIFIER
		{
			$command = statementFactory.createSimpleStatement(
				StatementType.READ, new CodeLocation(rea_tree.getLine()), null,
				variableTable.getVariable(id.getText())
			);
		}
	;

write_cmd returns [List<Statement> commands]
	: wr=WRITE expression e=expression
		{
			final Statement assignStatement = statementFactory.createSimpleStatement(
				StatementType.WRITE, new CodeLocation(wr_tree.getLine()), null,
				null, e.statList.get(e.statList.size()-1).getAssign()
			);
			e.statList.add(assignStatement);
			$commands = e.statList;
		}
	;

if_cmd returns [Statement command]
	: ift=IF gc=guarded_cmd FI
		{
			$command = statementFactory.createCompoundStatement(
				StatementType.IF, new CodeLocation(ift_tree.getLine()), null, gc.commands
			);
		}
	;

do_cmd returns [Statement command]
	: dot=DO gc=guarded_cmd OD
		{
			$command = statementFactory.createCompoundStatement(
				StatementType.DO, new CodeLocation(dot_tree.getLine()), null, gc.commands
			);
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
		$commands.add(statementFactory.createCompoundStatement(
			StatementType.SCOPE, new CodeLocation(e.tree.getLine()), null, e.statList
		));
		$commands.add(statementFactory.createCompoundStatement(
			StatementType.SCOPE, new CodeLocation(c.tree.getLine()), null, c.commands
		));
	}
	(GUARD gc=guarded_cmd {$commands.addAll(gc.commands);} )*
	;
	
program returns [Statement command]
	: m=MODULE IDENTIFIER COLON c=command END
		{
			$command = statementFactory.createRootStatement(
				new CodeLocation(m_tree.getLine()), null, c.commands
			);
		}
	;
	


/*------------------------------------------------------------------
 * LEXER RULES
 *------------------------------------------------------------------*/
 
ML_COMMENT
    :   '/*' (options {greedy=false;} : .)* '*/' {$channel=HIDDEN;}
    ;
	
LINE_COMMENT
    :   ('//'|'#') ~('\n'|'\r')*  ('\r\n' | '\r' | '\n') {$channel=HIDDEN;}
    |   ('//'|'#') ~('\n'|'\r')* {$channel=HIDDEN;}
    // a line comment could appear at the end of the file without CR/LF
    ;
    
ANNOTATION
	:	('#@') IDENTIFIER '=' '"' ~('"')* '"'
	;

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
