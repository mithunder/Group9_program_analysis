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

import com.github.mithunder.statements.Annotation;
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
	private StatementFactory statementFactory = StatementFactory.newInstance();
	private VariableTable variableTable = VariableTable.newInstance();
    private List<Annotation> annotations = new ArrayList<Annotation>();
    private final List<Statement> endCommands = new ArrayList<Statement>();

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
    private List<Statement> handleExpression(
    		final List<Statement> statList, final List<Statement> eStatList,
    		final Value v1, final Value v2,
    		final int binaryType, final CommonTree bTree, final int valueType,
    		final Variable assignVar, final List<Annotation> annotations) {
    	
		final Value valStart = v1 != null ? v1 : statList.get(statList.size()-1).getAssign();
		final Value valEnd = v2 != null ? v2 : eStatList.get(eStatList.size()-1).getAssign();
		final Statement binaryStat = statementFactory.createSimpleStatement(
			binaryType, new CodeLocation(bTree.getLine()),
			annotations, assignVar, valStart, valEnd
		);
		return concatenateStatements(statList, binaryStat, eStatList);
    }
    
    private List<Statement> handleExpression(
    		final List<Statement> statList, final List<Statement> eStatList,
    		final Value v1, final Value v2,
    		final int binaryType, final CommonTree bTree, final int valueType) {
    		
		return handleExpression(statList, eStatList, v1, v2, binaryType, bTree, valueType,
			variableTable.createTemporaryVariable(valueType), null);
    }
    
    private List<Statement> handleUnaryExpression(List<Statement> eStatList,
    		final Value val, final int unaryType, final CommonTree uTree, final Variable var,
    		List<Annotation> annotations
    		) {
    		
    	//Is e a literal or a non-literal unary_expr?
    	if (eStatList == null) {eStatList = new ArrayList<Statement>();}
		final Value valEnd = val != null ? val : eStatList.get(eStatList.size()-1).getAssign();
		final Statement newestStat = statementFactory.createSimpleStatement(
			unaryType,
			new CodeLocation(uTree.getLine()),
			annotations,
			var,
			valEnd
		);
		eStatList.add(newestStat);
		return eStatList;
	}
    
    //TODO: This is slow if the common case is that l1 is small/null and l2 is large.
    //Can be fixed using LinkedList, but enforcing that requirement currently seems
    //like a bad idea.
    private List<Statement> concatenateStatements(
			List<Statement> l1, Statement s1, List<Statement> l2) {
		
		if (l1 == null && l2 == null) {
			l1 = new ArrayList<Statement>();
			l1.add(s1);
			return l1;
		}
		else if (l1 == null && l2 != null) {
			l2.add(s1);
			return l2;
		}
		else if (l1 != null && l2 == null) {
			l1.add(s1);
			return l1;
		}
		else {
			l1.addAll(l2);
			l1.add(s1);
			return l1;
		}
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
	
binary_operator returns [int type, int vtype]
	: b1=or {$type = b1.type; $vtype = b1.vtype;}
	| b2=and {$type = b2.type; $vtype = b2.vtype;}
	| b3=eqa {$type = b3.type; $vtype = b3.vtype;}
	| b4=rel {$type = b4.type; $vtype = b4.vtype;}
	| b5=term {$type = b5.type; $vtype = b5.vtype;}
	| b6=factor {$type = b6.type; $vtype = b6.vtype;}
	;


	
//		* Operator precedence and literals. *
	
expression returns[List<Statement> statList, Value val]
	: e=expr_or {if (e.statList != null) {$statList = e.statList;} else {$val = e.val;}}
	;

expr_or returns [List<Statement> statList, Value val]
	:
	(e1=expr_and b=or e2=expr_and
		{$statList = handleExpression(
				e1.statList, e2.statList, e1.val, e2.val, b.type, b.tree, b.vtype
		);}
	)
	(b=or e3=expr_and
		{handleExpression(
			$statList, e3.statList, null, e3.val, b.type, b.tree, b.vtype
		);}
	)*
	| (e4=expr_and) {if (e4.statList != null) {$statList = e4.statList;} else {$val = e4.val;}}
	;
	
expr_and returns [List<Statement> statList, Value val]
	:
	(e1=expr_eqa b=and e2=expr_eqa
		{$statList = handleExpression(
				e1.statList, e2.statList, e1.val, e2.val, b.type, b.tree, b.vtype
		);}
	)
	(b=and e3=expr_eqa
		{handleExpression(
			$statList, e3.statList, null, e3.val, b.type, b.tree, b.vtype
		);}
	)*
	| (e4=expr_eqa) {if (e4.statList != null) {$statList = e4.statList;} else {$val = e4.val;}}
	;
	
//Both eq and neq.
expr_eqa returns [List<Statement> statList, Value val]
	:
	(e1=expr_rel b=eqa e2=expr_rel
		{$statList = handleExpression(
				e1.statList, e2.statList, e1.val, e2.val, b.type, b.tree, b.vtype
		);}
	)
	(b=eqa e3=expr_rel
		{handleExpression(
			$statList, e3.statList, null, e3.val, b.type, b.tree, b.vtype
		);}
	)*
	| (e4=expr_rel) {if (e4.statList != null) {$statList = e4.statList;} else {$val = e4.val;}}
	;

expr_rel returns [List<Statement> statList, Value val]
	:
	(e1=expr_term b=rel e2=expr_term
		{$statList = handleExpression(
				e1.statList, e2.statList, e1.val, e2.val, b.type, b.tree, b.vtype
		);}
	)
	(b=rel e3=expr_term
		{handleExpression(
			$statList, e3.statList, null, e3.val, b.type, b.tree, b.vtype
		);}
	)*
	| (e4=expr_term) {if (e4.statList != null) {$statList = e4.statList;} else {$val = e4.val;}}
	;
	
expr_term returns [List<Statement> statList, Value val]
	:
	(e1=expr_factor b=term e2=expr_factor
		{$statList = handleExpression(
				e1.statList, e2.statList, e1.val, e2.val, b.type, b.tree, b.vtype
		);}
	)
	(b=term e3=expr_factor
		{handleExpression(
			$statList, e3.statList, null, e3.val, b.type, b.tree, b.vtype
		);}
	)*
	| (e4=expr_factor) {if (e4.statList != null) {$statList = e4.statList;} else {$val = e4.val;}}
	;
	
expr_factor returns [List<Statement> statList, Value val]
	:
	(e1=expr_unary b=factor e2=expr_unary
		{$statList = handleExpression(
				e1.statList, e2.statList, e1.val, e2.val, b.type, b.tree, b.vtype
		);}
	)
	(b=factor e3=expr_unary
		{handleExpression(
			$statList, e3.statList, null, e3.val, b.type, b.tree, b.vtype
		);}
	)*
	| (e4=expr_unary) {if (e4.statList != null) {$statList = e4.statList;} else {$val = e4.val;}}
	;

expr_unary returns [List<Statement> statList, Value val]
	: u=unary_operator e=expr_unary
		{
			$statList = handleUnaryExpression(e.statList, e.val, u.type, u.tree,
				variableTable.createTemporaryVariable(u.vtype), null
			);
		}
	| l=lite {$val = l.val;}
	| lp=lite_paren {$statList = lp.statList; $val = lp.val;}
	;
	
literal returns[List<Statement> statList, Value val]
	: l=lite {$val = l.val;}
	| lp=lite_paren {$statList = lp.statList; $val = lp.val;}
	;
	
lite returns[Value val]
	:
	inte=INTEGER_LITERAL
		{
			$val = ConstantValue.getConstantValue(
				ValueType.INTEGER_TYPE, Integer.parseInt(inte.getText())
			);
		}
	| tru=TRUE {$val = ConstantValue.TRUE;}
	| fal=FALSE {$val = ConstantValue.FALSE;}
	| id=IDENTIFIER {$val = variableTable.getVariable(id.getText());}
	;
	
lite_paren returns[List<Statement> statList, Value val]
	: LPAREN e=expression RPAREN
		{
			$statList = e.statList; $val = e.val;
		}
	;
	


//		* Commands. *

//firstSkip is used to avoid skips.
//encounteredAbort is indicated whether an abort was encountered.
command returns [List<Statement> commands, Statement firstSkip, Boolean encounteredAbort]
	:
	{
		if ($commands == null) {
			$commands = new ArrayList<Statement>();
		}
		$firstSkip = null;
		$encounteredAbort = false;
	}
	(a=comm {
			for (Statement st : a.commands) {
				//If we encounter an abort, do not add any more commands.
				if (st.getStatementType() == StatementType.ABORT) {
					$commands.add(st);
					$encounteredAbort = true;
					break;
				}
				if (st.getStatementType() != StatementType.SKIP) {$commands.add(st);}
				else if ($firstSkip == null) {$firstSkip = st;}
			}
		}
	| b=skip_cmd {$firstSkip = b.command;}
	)
	
	(SEMI c=comm {
			if (!$encounteredAbort) {
				for (Statement st : c.commands) {
					//If we encounter an abort, do not add any more commands.
					if (st.getStatementType() == StatementType.ABORT) {
						$commands.add(st);
						$encounteredAbort = true;
						break;
					}
					if (st.getStatementType() != StatementType.SKIP) {$commands.add(st);}
					else if ($firstSkip == null) {$firstSkip = st;}
				}
			}
		}
	| SEMI d=skip_cmd {if (!$encounteredAbort && $firstSkip == null) {$firstSkip = d.command;}}
	)*
	{
		if ($commands.size() == 0) {
			$commands.add($firstSkip);
		}
	}
	;
	
comm returns [List<Statement> commands]
	:
	{
		if ($commands == null) {
			$commands = new ArrayList<Statement>();
		}
	}
	
	( a=assignment_cmd			{$commands.addAll(a.commands);}
	| c=abort_cmd				{$commands.add(c.command);}
	| d=read_cmd				{$commands.add(d.command);}
	| e=write_cmd				{$commands.addAll(e.commands);}
	| l=LCURLY f=command RCURLY
		{
			/*$commands.add(statementFactory.createCompoundStatement(
				StatementType.SCOPE, new CodeLocation(l_tree.getLine()), annotations, f.commands
			));
			annotations = new ArrayList<Annotation>();*/
			$commands.addAll(f.commands);
		}
	| g=if_cmd 					{$commands.add(g.command);}
	| h=do_cmd 					{$commands.add(h.command);}
	)
	;

assignment_cmd returns [List<Statement> commands]
	: id=IDENTIFIER as=ASSIGN e=expression
		{
			/*if ($commands == null) {$commands = new ArrayList<Statement>();}
			final Value val = e.val != null ? e.val : e.statList.get(e.statList.size()-1).getAssign();
			final Statement assignStatement = statementFactory.createSimpleStatement(
				StatementType.ASSIGN, new CodeLocation(as_tree.getLine()), annotations,
				variableTable.getVariable(id.getText()),
				val
			);
			annotations = new ArrayList<Annotation>();
			if (e.val == null) {
				$commands.addAll(e.statList);
			}
			$commands.add(assignStatement);*/
			
			if ($commands == null) {$commands = new ArrayList<Statement>();}
			final List<Statement> eList = e.statList;
			if (eList == null) {
				//We have a literal!
				final Statement assignStatement = statementFactory.createSimpleStatement(
					StatementType.ASSIGN, new CodeLocation(as_tree.getLine()), annotations,
					variableTable.getVariable(id.getText()),
					e.val
				);
				annotations = new ArrayList<Annotation>();
				$commands.add(assignStatement);
			}
			else {
				//We don't have a literal...
				//We have a unary or binary operator.
				
				final Statement lastTempAssign = eList.get(eList.size()-1);
				final int sType = lastTempAssign.getStatementType();
				final Statement assignStatement = statementFactory.createSimpleStatement(
					sType, new CodeLocation(as_tree.getLine()), annotations,
					variableTable.getVariable(id.getText()),
					lastTempAssign.getValues()
				);
				annotations = new ArrayList<Annotation>();
				eList.remove(eList.size()-1);
				$commands.addAll(eList);
				$commands.add(assignStatement);
			}
		}
	;

skip_cmd returns [Statement command]
	: s=SKIP
		{
			$command = statementFactory.createSimpleStatement(
				StatementType.SKIP, new CodeLocation(s_tree.getLine()), annotations
			);
			annotations = new ArrayList<Annotation>();
		}
	;

abort_cmd returns [Statement command]
	: a=ABORT
		{
			$command = statementFactory.createSimpleStatement(
				StatementType.ABORT, new CodeLocation(a_tree.getLine()), annotations
			);
			annotations = new ArrayList<Annotation>();
			endCommands.add($command);
		}
	;

read_cmd returns [Statement command]
	: rea=READ id=IDENTIFIER
		{
			$command = statementFactory.createSimpleStatement(
				StatementType.READ, new CodeLocation(rea_tree.getLine()), annotations,
				variableTable.getVariable(id.getText())
			);
			annotations = new ArrayList<Annotation>();
		}
	;

write_cmd returns [List<Statement> commands]
	: wr=WRITE e=expression
		{
			if ($commands == null) {$commands = new ArrayList<Statement>();}
			final Value val = e.val != null ? e.val : e.statList.get(e.statList.size()-1).getAssign();
			final Statement assignStatement = statementFactory.createSimpleStatement(
				StatementType.WRITE, new CodeLocation(wr_tree.getLine()), annotations,
				null, val
			);
			annotations = new ArrayList<Annotation>();
			if (e.val == null) {
				$commands.addAll(e.statList);
			}
			$commands.add(assignStatement);
		}
	;

if_cmd returns [Statement command]
	: ift=IF gc=guarded_cmd FI
		{
			$command = statementFactory.createCompoundStatement(
				StatementType.IF, new CodeLocation(ift_tree.getLine()), annotations, gc.commands
			);
			annotations = new ArrayList<Annotation>();
		}
	;

do_cmd returns [Statement command]
	: dot=DO gc=guarded_cmd OD
		{
			$command = statementFactory.createCompoundStatement(
				StatementType.DO, new CodeLocation(dot_tree.getLine()), annotations, gc.commands
			);
			annotations = new ArrayList<Annotation>();
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
		if (e.val != null) {
			final Statement newestStat = statementFactory.createSimpleStatement(
				StatementType.ASSIGN,
				new CodeLocation(e.tree.getLine()),
				annotations,
				variableTable.createTemporaryVariable(e.val.getValueType()),
				e.val
			);
			$commands.add(newestStat);
		}
		else {
			$commands.add(statementFactory.createCompoundStatement(
				StatementType.SCOPE, new CodeLocation(e.tree.getLine()), annotations, e.statList
			));
		}
		annotations = new ArrayList<Annotation>();
		$commands.add(statementFactory.createCompoundStatement(
			StatementType.SCOPE, new CodeLocation(c.tree.getLine()), annotations, c.commands
		));
		annotations = new ArrayList<Annotation>();
	}
	(GUARD gc=guarded_cmd {$commands.addAll(gc.commands);} )*
	;
	
program returns [CompilationUnit compilationUnit]
	: m=MODULE id=IDENTIFIER COLON c=command END
		{
			final Statement command = statementFactory.createRootStatement(
				new CodeLocation(m_tree.getLine()), annotations, c.commands
			);
			annotations = new ArrayList<Annotation>();
			final Statement lastCommand = c.commands.get(c.commands.size()-1);
			if (!endCommands.contains(lastCommand)) {
				endCommands.add(lastCommand);
			}
			$compilationUnit = new CompilationUnit(
				id.getText(), command, variableTable, endCommands
			);
		}
	;
	
    
annotation
	:	('#@') id=IDENTIFIER '=' '"' te=(~('"')*) '"'
		{
			annotations.add(Annotation.newInstance(id.getText(), te.getText()));
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
