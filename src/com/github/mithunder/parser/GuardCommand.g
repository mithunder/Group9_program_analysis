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
    private List<Statement> handleExpression(
    		final List<Statement> statList, final List<Statement> eStatList,
    		final Value v1, final Value v2,
    		final int binaryType, final CommonTree bTree, final int valueType) {
    	
		final Value valStart = v1 != null ? v1 : statList.get(statList.size()-1).getAssign();
		final Value valEnd = v2 != null ? v2 : eStatList.get(eStatList.size()-1).getAssign();
		final Statement binaryStat = statementFactory.createSimpleStatement(
			binaryType, new CodeLocation(bTree.getLine()),
			null, variableTable.createTemporaryVariable(valueType), valStart, valEnd
		);
		return concatenateStatements(statList, binaryStat, eStatList);
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
    
    private final List<Annotation> annotations = new ArrayList<Annotation>();
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
			//Is e a literal or a non-literal unary_expr?
			$statList = e.statList == null ? new ArrayList<Statement>() : e.statList;
			final Value valEnd = e.val != null ? e.val : $statList.get($statList.size()-1).getAssign();
			final Statement newestStat = statementFactory.createSimpleStatement(
				u.type,
				new CodeLocation(u.tree.getLine()),
				null,
				variableTable.createTemporaryVariable(u.vtype),
				valEnd
			);
			$statList.add(newestStat);
		}
	| l=lite {$val = l.val;}
	| lp=lite_paren {$statList = lp.statList;}
	;
	
literal returns[List<Statement> statList, Value val]
	: l=lite {$val = l.val;}
	| lp=lite_paren {$statList = lp.statList;}
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
	
lite_paren returns[List<Statement> statList]
	: LPAREN e=expression RPAREN
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
				StatementType.SCOPE, new CodeLocation(l_tree.getLine()), annotations, f.commands
			));
			annotations.clear();
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
			final Value val = e.val != null ? e.val : e.statList.get(e.statList.size()-1).getAssign();
			final Statement assignStatement = statementFactory.createSimpleStatement(
				StatementType.ASSIGN, new CodeLocation(as_tree.getLine()), annotations,
				variableTable.getVariable(id.getText()),
				val
			);
			annotations.clear();
			if (e.val == null) {
				$commands.addAll(e.statList);
			}
			$commands.add(assignStatement);
		}
	;

skip_cmd returns [Statement command]
	: s=SKIP
		{
			$command = statementFactory.createSimpleStatement(
				StatementType.SKIP, new CodeLocation(s_tree.getLine()), annotations
			);
			annotations.clear();
		}
	;

abort_cmd returns [Statement command]
	: a=ABORT
		{
			$command = statementFactory.createSimpleStatement(
				StatementType.ABORT, new CodeLocation(a_tree.getLine()), annotations
			);
			annotations.clear();
		}
	;

read_cmd returns [Statement command]
	: rea=READ id=IDENTIFIER
		{
			$command = statementFactory.createSimpleStatement(
				StatementType.READ, new CodeLocation(rea_tree.getLine()), annotations,
				variableTable.getVariable(id.getText())
			);
			annotations.clear();
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
			annotations.clear();
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
			annotations.clear();
		}
	;

do_cmd returns [Statement command]
	: dot=DO gc=guarded_cmd OD
		{
			$command = statementFactory.createCompoundStatement(
				StatementType.DO, new CodeLocation(dot_tree.getLine()), annotations, gc.commands
			);
			annotations.clear();
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
		annotations.clear();
		$commands.add(statementFactory.createCompoundStatement(
			StatementType.SCOPE, new CodeLocation(c.tree.getLine()), annotations, c.commands
		));
		annotations.clear();
	}
	(GUARD gc=guarded_cmd {$commands.addAll(gc.commands);} )*
	;
	
program returns [CompilationUnit compilationUnit]
	: m=MODULE id=IDENTIFIER COLON c=command END
		{
			final Statement command = statementFactory.createRootStatement(
				new CodeLocation(m_tree.getLine()), annotations, c.commands
			);
			annotations.clear();
			$compilationUnit = new CompilationUnit(
				id.getText(), command, variableTable
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
