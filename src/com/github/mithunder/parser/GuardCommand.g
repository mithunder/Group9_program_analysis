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

import com.github.mithunder.statements.CompilationUnit;
import com.github.mithunder.statements.Variable;
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

		CompilationUnit compilationUnit;

        try {
        	
        	program_return program_r = parser.program();
        	compilationUnit = program_r.compilationUnit;
        
        } catch (RecognitionException e)  {
            e.printStackTrace();
        }
    }
    
    //Adds everything to the first statement list given.
    private void handleExpression(final List<Statement> statList, final List<Statement> eStatList,
    	final int binaryType, final CommonTree bTree, final int valueType) {
    	
		final Value valStart = statList.get(statList.size()-1).getAssign();
		final Value valEnd = eStatList.get(eStatList.size()-1).getAssign();
		statList.addAll(eStatList);
		final Statement binaryStat = statementFactory.createSimpleStatement(
			binaryType, new CodeLocation(bTree.getLine()),
			null, variableTable.createTemporaryVariable(valueType), valStart, valEnd
		);
		statList.add(binaryStat);
    }
}



/*------------------------------------------------------------------
 * PARSER RULES
 *------------------------------------------------------------------*/



//		* Operators. *

unary_operator returns[int type, int vtype]
	: NOT {$type = StatementType.LOGIC_NOT; $vtype = ValueType.BOOLEAN_TYPE;}
	| MINUS {$type = StatementType.SIGN_INVERT; $vtype = ValueType.INTEGER_TYPE;}
	;
	
or returns[int type, int vtype]
	: OR {$type = StatementType.LOGIC_OR; $vtype = ValueType.BOOLEAN_TYPE;};

and returns[int type, int vtype]
	: AND {$type = StatementType.LOGIC_AND; $vtype = ValueType.BOOLEAN_TYPE;};

eq returns[int type, int vtype]
	: EQ {$type = StatementType.EQ; $vtype = ValueType.BOOLEAN_TYPE;};
neq returns[int type, int vtype]
	: NEQ {$type = StatementType.NEQ; $vtype = ValueType.BOOLEAN_TYPE;};
eqa returns[int type, int vtype]
	: (a=eq {$type = a.type; $vtype = a.vtype;})
	| (b=neq {$type = b.type; $vtype = b.vtype;})
	;

gt returns[int type, int vtype]
	: GREATER_THAN {$type = StatementType.GT; $vtype = ValueType.BOOLEAN_TYPE;};
gt_eq returns[int type, int vtype]
	: GREATER_EQ {$type = StatementType.GT_EQ; $vtype = ValueType.BOOLEAN_TYPE;};
lt returns[int type, int vtype]
	: LESS_THAN {$type = StatementType.LT; $vtype = ValueType.BOOLEAN_TYPE;};
lt_eq returns[int type, int vtype]
	: LESS_EQ {$type = StatementType.LT_EQ; $vtype = ValueType.BOOLEAN_TYPE;};
rel returns[int type, int vtype]
	: (a=gt {$type = a.type; $vtype = a.vtype;})
	| (b=gt_eq {$type = b.type; $vtype = b.vtype;})
	| (c=lt {$type = c.type; $vtype = c.vtype;})
	| (d=lt_eq {$type = d.type; $vtype = d.vtype;})
	;

plus returns[int type, int vtype]
	: PLUS {$type = StatementType.PLUS; $vtype = ValueType.INTEGER_TYPE;};
minus returns[int type, int vtype]
	: MINUS {$type = StatementType.MINUS; $vtype = ValueType.INTEGER_TYPE;};
term returns[int type, int vtype]
	: (a=plus {$type = a.type; $vtype = a.vtype;})
	| (b=minus {$type = b.type; $vtype = b.vtype;})
	;

mul returns[int type, int vtype]
	: MUL {$type = StatementType.MULTIPLY; $vtype = ValueType.INTEGER_TYPE;};
div returns[int type, int vtype]
	: DIV {$type = StatementType.DIVIDE; $vtype = ValueType.INTEGER_TYPE;};
factor returns[int type, int vtype]
	: (a=mul {$type = a.type; $vtype = a.vtype;})
	| (b=div {$type = b.type; $vtype = b.vtype;})
	;
	
	
	
//		* Operator precedence and literals. *
	
expression returns[List<Statement> statList]
	: e=expr_or {$statList = e.statList;}
	;

expr_or returns [List<Statement> statList]
	:
	(e1=expr_and b=or e2=expr_and
		{$statList = e1.statList; handleExpression(e1.statList, e2.statList, b.type, b.tree, b.vtype);}
	)
	(b=or e3=expr_and {handleExpression($statList, e3.statList, b.type, b.tree, b.vtype);})*
	| (e4=expr_and) {$statList = e4.statList;}
	;
	
expr_and returns [List<Statement> statList]
	:
	(e1=expr_eqa b=and e2=expr_eqa
		{$statList = e1.statList; handleExpression(e1.statList, e2.statList, b.type, b.tree, b.vtype);}
	)
	(b=and e3=expr_eqa {handleExpression($statList, e3.statList, b.type, b.tree, b.vtype);})*
	| (e4=expr_eqa) {$statList = e4.statList;}
	;
	
//Both eq and neq.
expr_eqa returns [List<Statement> statList]
	:
	(e1=expr_rel b=eqa e2=expr_rel
		{$statList = e1.statList; handleExpression(e1.statList, e2.statList, b.type, b.tree, b.vtype);}
	)
	(b=eqa e3=expr_rel {handleExpression($statList, e3.statList, b.type, b.tree, b.vtype);})*
	| (e4=expr_rel) {$statList = e4.statList;}
	;

expr_rel returns [List<Statement> statList]
	:
	(e1=expr_term b=rel e2=expr_term
		{$statList = e1.statList; handleExpression(e1.statList, e2.statList, b.type, b.tree, b.vtype);}
	)
	(b=rel e3=expr_term {handleExpression($statList, e3.statList, b.type, b.tree, b.vtype);})*
	| (e4=expr_term) {$statList = e4.statList;}
	;
	
expr_term returns [List<Statement> statList]
	:
	(e1=expr_factor b=term e2=expr_factor
		{$statList = e1.statList; handleExpression(e1.statList, e2.statList, b.type, b.tree, b.vtype);}
	)
	(b=term e3=expr_factor {handleExpression($statList, e3.statList, b.type, b.tree, b.vtype);})*
	| (e4=expr_factor) {$statList = e4.statList;}
	;
	
expr_factor returns [List<Statement> statList]
	:
	(e1=expr_unary b=factor e2=expr_unary
		{$statList = e1.statList; handleExpression(e1.statList, e2.statList, b.type, b.tree, b.vtype);}
	)
	(b=factor e3=expr_unary {handleExpression($statList, e3.statList, b.type, b.tree, b.vtype);})*
	| (e4=expr_unary) {$statList = e4.statList;}
	;

expr_unary returns [List<Statement> statList]
	:
	( u=unary_operator e=expression
		{
			if ($statList == null) {$statList = new ArrayList<Statement>();}
			final Statement newestStat = statementFactory.createSimpleStatement(
				u.type,
				new CodeLocation(u.tree.getLine()),
				null,
				variableTable.createTemporaryVariable(u.vtype),
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
	inte=INTEGER_LITERAL
		{
			if ($statList == null) {$statList = new ArrayList<Statement>();}
			$statList.add(statementFactory.createSimpleStatement(
				StatementType.ASSIGN,
				new CodeLocation(inte_tree.getLine()),
				null,
				variableTable.createTemporaryVariable(ValueType.INTEGER_TYPE),
				ConstantValue.getConstantValue(ValueType.INTEGER_TYPE, Integer.parseInt(inte.getText()))
			));
		}
	| tru=TRUE
		{
			if ($statList == null) {$statList = new ArrayList<Statement>();}
			$statList.add(statementFactory.createSimpleStatement(
				StatementType.ASSIGN,
				new CodeLocation(tru_tree.getLine()),
				null,
				variableTable.createTemporaryVariable(ValueType.BOOLEAN_TYPE),
				ConstantValue.TRUE
			));
		}
	| fal=FALSE
		{
			if ($statList == null) {$statList = new ArrayList<Statement>();}
			$statList.add(statementFactory.createSimpleStatement(
				StatementType.ASSIGN,
				new CodeLocation(fal_tree.getLine()),
				null,
				variableTable.createTemporaryVariable(ValueType.BOOLEAN_TYPE),
				ConstantValue.FALSE
			));
		}
	| id=IDENTIFIER
		{
			if ($statList == null) {$statList = new ArrayList<Statement>();}
			final Variable var = variableTable.getVariable(id.getText());
			$statList.add(statementFactory.createSimpleStatement(
				StatementType.ASSIGN,
				new CodeLocation(id_tree.getLine()),
				null,
				variableTable.createTemporaryVariable(var.getValueType()),
				var
			));
		}
	| LPAREN e=expression RPAREN
		{
			if ($statList == null) {$statList = new ArrayList<Statement>();}
			$statList = e.statList;
		}
	;
	


//		* Commands. *

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
			if ($commands == null) {$commands = new ArrayList<Statement>();}
			final Statement assignStatement = statementFactory.createSimpleStatement(
				StatementType.ASSIGN, new CodeLocation(as_tree.getLine()), null,
				variableTable.getVariable(id.getText()),
				e.statList.get(e.statList.size()-1).getAssign()
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
	: wr=WRITE e=expression
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
	
program returns [CompilationUnit compilationUnit]
	: m=MODULE id=IDENTIFIER COLON c=command END
		{
			final Statement command = statementFactory.createRootStatement(
				new CodeLocation(m_tree.getLine()), null, c.commands
			);
			$compilationUnit = new CompilationUnit(
				id.getText(), command, variableTable
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
