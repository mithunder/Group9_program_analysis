// $ANTLR 3.2 debian-4 GuardCommand.g 2010-10-17 15:47:14

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


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import org.antlr.runtime.tree.*;

public class GuardCommandParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "AND", "OR", "ASSIGN", "SEMI", "GREATER_THAN", "GREATER_EQ", "LESS_THAN", "LESS_EQ", "EQ", "NEQ", "PLUS", "MINUS", "MUL", "DIV", "NOT", "RPAREN", "LPAREN", "RCURLY", "LCURLY", "COLON", "IF", "FI", "DO", "OD", "GUARD", "ARROW", "SKIP", "ABORT", "WRITE", "READ", "MODULE", "END", "TRUE", "FALSE", "INTEGER_LITERAL", "IDENTIFIER", "ML_COMMENT", "LINE_COMMENT", "LETTER", "WS", "'#@'", "'\"'"
    };
    public static final int LETTER=42;
    public static final int DO=26;
    public static final int NOT=18;
    public static final int AND=4;
    public static final int EOF=-1;
    public static final int LPAREN=20;
    public static final int IF=24;
    public static final int ML_COMMENT=40;
    public static final int RPAREN=19;
    public static final int IDENTIFIER=39;
    public static final int GREATER_EQ=9;
    public static final int PLUS=14;
    public static final int EQ=12;
    public static final int ABORT=31;
    public static final int LESS_EQ=11;
    public static final int GREATER_THAN=8;
    public static final int T__44=44;
    public static final int T__45=45;
    public static final int LINE_COMMENT=41;
    public static final int LCURLY=22;
    public static final int MINUS=15;
    public static final int MODULE=34;
    public static final int MUL=16;
    public static final int SEMI=7;
    public static final int TRUE=36;
    public static final int OD=27;
    public static final int WRITE=32;
    public static final int FI=25;
    public static final int SKIP=30;
    public static final int COLON=23;
    public static final int WS=43;
    public static final int NEQ=13;
    public static final int READ=33;
    public static final int INTEGER_LITERAL=38;
    public static final int OR=5;
    public static final int RCURLY=21;
    public static final int LESS_THAN=10;
    public static final int ASSIGN=6;
    public static final int ARROW=29;
    public static final int GUARD=28;
    public static final int DIV=17;
    public static final int END=35;
    public static final int FALSE=37;

    // delegates
    // delegators


        public GuardCommandParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public GuardCommandParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
            this.state.ruleMemo = new HashMap[81+1];
             
             
        }
        
    protected TreeAdaptor adaptor = new CommonTreeAdaptor();

    public void setTreeAdaptor(TreeAdaptor adaptor) {
        this.adaptor = adaptor;
    }
    public TreeAdaptor getTreeAdaptor() {
        return adaptor;
    }

    public String[] getTokenNames() { return GuardCommandParser.tokenNames; }
    public String getGrammarFileName() { return "GuardCommand.g"; }


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
        
        private final List<Annotation> annotations = new ArrayList<Annotation>();


    public static class unary_operator_return extends ParserRuleReturnScope {
        public int type;
        public int vtype;
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "unary_operator"
    // GuardCommand.g:176:1: unary_operator returns [int type, int vtype] : ( NOT | MINUS );
    public final GuardCommandParser.unary_operator_return unary_operator() throws RecognitionException {
        GuardCommandParser.unary_operator_return retval = new GuardCommandParser.unary_operator_return();
        retval.start = input.LT(1);
        int unary_operator_StartIndex = input.index();
        CommonTree root_0 = null;

        Token NOT1=null;
        Token MINUS2=null;

        CommonTree NOT1_tree=null;
        CommonTree MINUS2_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 1) ) { return retval; }
            // GuardCommand.g:177:2: ( NOT | MINUS )
            int alt1=2;
            int LA1_0 = input.LA(1);

            if ( (LA1_0==NOT) ) {
                alt1=1;
            }
            else if ( (LA1_0==MINUS) ) {
                alt1=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 1, 0, input);

                throw nvae;
            }
            switch (alt1) {
                case 1 :
                    // GuardCommand.g:177:4: NOT
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    NOT1=(Token)match(input,NOT,FOLLOW_NOT_in_unary_operator457); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    NOT1_tree = (CommonTree)adaptor.create(NOT1);
                    adaptor.addChild(root_0, NOT1_tree);
                    }
                    if ( state.backtracking==0 ) {
                      retval.type = StatementType.LOGIC_NOT; retval.vtype = ValueType.BOOLEAN_TYPE;
                    }

                    }
                    break;
                case 2 :
                    // GuardCommand.g:178:4: MINUS
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    MINUS2=(Token)match(input,MINUS,FOLLOW_MINUS_in_unary_operator464); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    MINUS2_tree = (CommonTree)adaptor.create(MINUS2);
                    adaptor.addChild(root_0, MINUS2_tree);
                    }
                    if ( state.backtracking==0 ) {
                      retval.type = StatementType.SIGN_INVERT; retval.vtype = ValueType.INTEGER_TYPE;
                    }

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 1, unary_operator_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "unary_operator"

    public static class or_return extends ParserRuleReturnScope {
        public int type;
        public int vtype;
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "or"
    // GuardCommand.g:181:1: or returns [int type, int vtype] : OR ;
    public final GuardCommandParser.or_return or() throws RecognitionException {
        GuardCommandParser.or_return retval = new GuardCommandParser.or_return();
        retval.start = input.LT(1);
        int or_StartIndex = input.index();
        CommonTree root_0 = null;

        Token OR3=null;

        CommonTree OR3_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 2) ) { return retval; }
            // GuardCommand.g:182:2: ( OR )
            // GuardCommand.g:182:4: OR
            {
            root_0 = (CommonTree)adaptor.nil();

            OR3=(Token)match(input,OR,FOLLOW_OR_in_or481); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            OR3_tree = (CommonTree)adaptor.create(OR3);
            adaptor.addChild(root_0, OR3_tree);
            }
            if ( state.backtracking==0 ) {
              retval.type = StatementType.LOGIC_OR; retval.vtype = ValueType.BOOLEAN_TYPE;
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 2, or_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "or"

    public static class and_return extends ParserRuleReturnScope {
        public int type;
        public int vtype;
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "and"
    // GuardCommand.g:184:1: and returns [int type, int vtype] : AND ;
    public final GuardCommandParser.and_return and() throws RecognitionException {
        GuardCommandParser.and_return retval = new GuardCommandParser.and_return();
        retval.start = input.LT(1);
        int and_StartIndex = input.index();
        CommonTree root_0 = null;

        Token AND4=null;

        CommonTree AND4_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 3) ) { return retval; }
            // GuardCommand.g:185:2: ( AND )
            // GuardCommand.g:185:4: AND
            {
            root_0 = (CommonTree)adaptor.nil();

            AND4=(Token)match(input,AND,FOLLOW_AND_in_and495); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            AND4_tree = (CommonTree)adaptor.create(AND4);
            adaptor.addChild(root_0, AND4_tree);
            }
            if ( state.backtracking==0 ) {
              retval.type = StatementType.LOGIC_AND; retval.vtype = ValueType.BOOLEAN_TYPE;
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 3, and_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "and"

    public static class eq_return extends ParserRuleReturnScope {
        public int type;
        public int vtype;
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "eq"
    // GuardCommand.g:187:1: eq returns [int type, int vtype] : EQ ;
    public final GuardCommandParser.eq_return eq() throws RecognitionException {
        GuardCommandParser.eq_return retval = new GuardCommandParser.eq_return();
        retval.start = input.LT(1);
        int eq_StartIndex = input.index();
        CommonTree root_0 = null;

        Token EQ5=null;

        CommonTree EQ5_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 4) ) { return retval; }
            // GuardCommand.g:188:2: ( EQ )
            // GuardCommand.g:188:4: EQ
            {
            root_0 = (CommonTree)adaptor.nil();

            EQ5=(Token)match(input,EQ,FOLLOW_EQ_in_eq509); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            EQ5_tree = (CommonTree)adaptor.create(EQ5);
            adaptor.addChild(root_0, EQ5_tree);
            }
            if ( state.backtracking==0 ) {
              retval.type = StatementType.EQ; retval.vtype = ValueType.BOOLEAN_TYPE;
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 4, eq_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "eq"

    public static class neq_return extends ParserRuleReturnScope {
        public int type;
        public int vtype;
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "neq"
    // GuardCommand.g:189:1: neq returns [int type, int vtype] : NEQ ;
    public final GuardCommandParser.neq_return neq() throws RecognitionException {
        GuardCommandParser.neq_return retval = new GuardCommandParser.neq_return();
        retval.start = input.LT(1);
        int neq_StartIndex = input.index();
        CommonTree root_0 = null;

        Token NEQ6=null;

        CommonTree NEQ6_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 5) ) { return retval; }
            // GuardCommand.g:190:2: ( NEQ )
            // GuardCommand.g:190:4: NEQ
            {
            root_0 = (CommonTree)adaptor.nil();

            NEQ6=(Token)match(input,NEQ,FOLLOW_NEQ_in_neq522); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            NEQ6_tree = (CommonTree)adaptor.create(NEQ6);
            adaptor.addChild(root_0, NEQ6_tree);
            }
            if ( state.backtracking==0 ) {
              retval.type = StatementType.NEQ; retval.vtype = ValueType.BOOLEAN_TYPE;
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 5, neq_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "neq"

    public static class eqa_return extends ParserRuleReturnScope {
        public int type;
        public int vtype;
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "eqa"
    // GuardCommand.g:191:1: eqa returns [int type, int vtype] : ( (a= eq ) | (b= neq ) );
    public final GuardCommandParser.eqa_return eqa() throws RecognitionException {
        GuardCommandParser.eqa_return retval = new GuardCommandParser.eqa_return();
        retval.start = input.LT(1);
        int eqa_StartIndex = input.index();
        CommonTree root_0 = null;

        GuardCommandParser.eq_return a = null;

        GuardCommandParser.neq_return b = null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 6) ) { return retval; }
            // GuardCommand.g:192:2: ( (a= eq ) | (b= neq ) )
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0==EQ) ) {
                alt2=1;
            }
            else if ( (LA2_0==NEQ) ) {
                alt2=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 2, 0, input);

                throw nvae;
            }
            switch (alt2) {
                case 1 :
                    // GuardCommand.g:192:4: (a= eq )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    // GuardCommand.g:192:4: (a= eq )
                    // GuardCommand.g:192:5: a= eq
                    {
                    pushFollow(FOLLOW_eq_in_eqa538);
                    a=eq();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, a.getTree());
                    if ( state.backtracking==0 ) {
                      retval.type = a.type; retval.vtype = a.vtype;
                    }

                    }


                    }
                    break;
                case 2 :
                    // GuardCommand.g:193:4: (b= neq )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    // GuardCommand.g:193:4: (b= neq )
                    // GuardCommand.g:193:5: b= neq
                    {
                    pushFollow(FOLLOW_neq_in_eqa549);
                    b=neq();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, b.getTree());
                    if ( state.backtracking==0 ) {
                      retval.type = b.type; retval.vtype = b.vtype;
                    }

                    }


                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 6, eqa_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "eqa"

    public static class gt_return extends ParserRuleReturnScope {
        public int type;
        public int vtype;
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "gt"
    // GuardCommand.g:196:1: gt returns [int type, int vtype] : GREATER_THAN ;
    public final GuardCommandParser.gt_return gt() throws RecognitionException {
        GuardCommandParser.gt_return retval = new GuardCommandParser.gt_return();
        retval.start = input.LT(1);
        int gt_StartIndex = input.index();
        CommonTree root_0 = null;

        Token GREATER_THAN7=null;

        CommonTree GREATER_THAN7_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 7) ) { return retval; }
            // GuardCommand.g:197:2: ( GREATER_THAN )
            // GuardCommand.g:197:4: GREATER_THAN
            {
            root_0 = (CommonTree)adaptor.nil();

            GREATER_THAN7=(Token)match(input,GREATER_THAN,FOLLOW_GREATER_THAN_in_gt566); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            GREATER_THAN7_tree = (CommonTree)adaptor.create(GREATER_THAN7);
            adaptor.addChild(root_0, GREATER_THAN7_tree);
            }
            if ( state.backtracking==0 ) {
              retval.type = StatementType.GT; retval.vtype = ValueType.BOOLEAN_TYPE;
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 7, gt_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "gt"

    public static class gt_eq_return extends ParserRuleReturnScope {
        public int type;
        public int vtype;
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "gt_eq"
    // GuardCommand.g:198:1: gt_eq returns [int type, int vtype] : GREATER_EQ ;
    public final GuardCommandParser.gt_eq_return gt_eq() throws RecognitionException {
        GuardCommandParser.gt_eq_return retval = new GuardCommandParser.gt_eq_return();
        retval.start = input.LT(1);
        int gt_eq_StartIndex = input.index();
        CommonTree root_0 = null;

        Token GREATER_EQ8=null;

        CommonTree GREATER_EQ8_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 8) ) { return retval; }
            // GuardCommand.g:199:2: ( GREATER_EQ )
            // GuardCommand.g:199:4: GREATER_EQ
            {
            root_0 = (CommonTree)adaptor.nil();

            GREATER_EQ8=(Token)match(input,GREATER_EQ,FOLLOW_GREATER_EQ_in_gt_eq579); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            GREATER_EQ8_tree = (CommonTree)adaptor.create(GREATER_EQ8);
            adaptor.addChild(root_0, GREATER_EQ8_tree);
            }
            if ( state.backtracking==0 ) {
              retval.type = StatementType.GT_EQ; retval.vtype = ValueType.BOOLEAN_TYPE;
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 8, gt_eq_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "gt_eq"

    public static class lt_return extends ParserRuleReturnScope {
        public int type;
        public int vtype;
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "lt"
    // GuardCommand.g:200:1: lt returns [int type, int vtype] : LESS_THAN ;
    public final GuardCommandParser.lt_return lt() throws RecognitionException {
        GuardCommandParser.lt_return retval = new GuardCommandParser.lt_return();
        retval.start = input.LT(1);
        int lt_StartIndex = input.index();
        CommonTree root_0 = null;

        Token LESS_THAN9=null;

        CommonTree LESS_THAN9_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 9) ) { return retval; }
            // GuardCommand.g:201:2: ( LESS_THAN )
            // GuardCommand.g:201:4: LESS_THAN
            {
            root_0 = (CommonTree)adaptor.nil();

            LESS_THAN9=(Token)match(input,LESS_THAN,FOLLOW_LESS_THAN_in_lt592); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            LESS_THAN9_tree = (CommonTree)adaptor.create(LESS_THAN9);
            adaptor.addChild(root_0, LESS_THAN9_tree);
            }
            if ( state.backtracking==0 ) {
              retval.type = StatementType.LT; retval.vtype = ValueType.BOOLEAN_TYPE;
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 9, lt_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "lt"

    public static class lt_eq_return extends ParserRuleReturnScope {
        public int type;
        public int vtype;
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "lt_eq"
    // GuardCommand.g:202:1: lt_eq returns [int type, int vtype] : LESS_EQ ;
    public final GuardCommandParser.lt_eq_return lt_eq() throws RecognitionException {
        GuardCommandParser.lt_eq_return retval = new GuardCommandParser.lt_eq_return();
        retval.start = input.LT(1);
        int lt_eq_StartIndex = input.index();
        CommonTree root_0 = null;

        Token LESS_EQ10=null;

        CommonTree LESS_EQ10_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 10) ) { return retval; }
            // GuardCommand.g:203:2: ( LESS_EQ )
            // GuardCommand.g:203:4: LESS_EQ
            {
            root_0 = (CommonTree)adaptor.nil();

            LESS_EQ10=(Token)match(input,LESS_EQ,FOLLOW_LESS_EQ_in_lt_eq605); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            LESS_EQ10_tree = (CommonTree)adaptor.create(LESS_EQ10);
            adaptor.addChild(root_0, LESS_EQ10_tree);
            }
            if ( state.backtracking==0 ) {
              retval.type = StatementType.LT_EQ; retval.vtype = ValueType.BOOLEAN_TYPE;
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 10, lt_eq_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "lt_eq"

    public static class rel_return extends ParserRuleReturnScope {
        public int type;
        public int vtype;
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "rel"
    // GuardCommand.g:204:1: rel returns [int type, int vtype] : ( (a= gt ) | (b= gt_eq ) | (c= lt ) | (d= lt_eq ) );
    public final GuardCommandParser.rel_return rel() throws RecognitionException {
        GuardCommandParser.rel_return retval = new GuardCommandParser.rel_return();
        retval.start = input.LT(1);
        int rel_StartIndex = input.index();
        CommonTree root_0 = null;

        GuardCommandParser.gt_return a = null;

        GuardCommandParser.gt_eq_return b = null;

        GuardCommandParser.lt_return c = null;

        GuardCommandParser.lt_eq_return d = null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 11) ) { return retval; }
            // GuardCommand.g:205:2: ( (a= gt ) | (b= gt_eq ) | (c= lt ) | (d= lt_eq ) )
            int alt3=4;
            switch ( input.LA(1) ) {
            case GREATER_THAN:
                {
                alt3=1;
                }
                break;
            case GREATER_EQ:
                {
                alt3=2;
                }
                break;
            case LESS_THAN:
                {
                alt3=3;
                }
                break;
            case LESS_EQ:
                {
                alt3=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 3, 0, input);

                throw nvae;
            }

            switch (alt3) {
                case 1 :
                    // GuardCommand.g:205:4: (a= gt )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    // GuardCommand.g:205:4: (a= gt )
                    // GuardCommand.g:205:5: a= gt
                    {
                    pushFollow(FOLLOW_gt_in_rel621);
                    a=gt();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, a.getTree());
                    if ( state.backtracking==0 ) {
                      retval.type = a.type; retval.vtype = a.vtype;
                    }

                    }


                    }
                    break;
                case 2 :
                    // GuardCommand.g:206:4: (b= gt_eq )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    // GuardCommand.g:206:4: (b= gt_eq )
                    // GuardCommand.g:206:5: b= gt_eq
                    {
                    pushFollow(FOLLOW_gt_eq_in_rel632);
                    b=gt_eq();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, b.getTree());
                    if ( state.backtracking==0 ) {
                      retval.type = b.type; retval.vtype = b.vtype;
                    }

                    }


                    }
                    break;
                case 3 :
                    // GuardCommand.g:207:4: (c= lt )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    // GuardCommand.g:207:4: (c= lt )
                    // GuardCommand.g:207:5: c= lt
                    {
                    pushFollow(FOLLOW_lt_in_rel643);
                    c=lt();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, c.getTree());
                    if ( state.backtracking==0 ) {
                      retval.type = c.type; retval.vtype = c.vtype;
                    }

                    }


                    }
                    break;
                case 4 :
                    // GuardCommand.g:208:4: (d= lt_eq )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    // GuardCommand.g:208:4: (d= lt_eq )
                    // GuardCommand.g:208:5: d= lt_eq
                    {
                    pushFollow(FOLLOW_lt_eq_in_rel654);
                    d=lt_eq();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, d.getTree());
                    if ( state.backtracking==0 ) {
                      retval.type = d.type; retval.vtype = d.vtype;
                    }

                    }


                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 11, rel_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "rel"

    public static class plus_return extends ParserRuleReturnScope {
        public int type;
        public int vtype;
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "plus"
    // GuardCommand.g:211:1: plus returns [int type, int vtype] : PLUS ;
    public final GuardCommandParser.plus_return plus() throws RecognitionException {
        GuardCommandParser.plus_return retval = new GuardCommandParser.plus_return();
        retval.start = input.LT(1);
        int plus_StartIndex = input.index();
        CommonTree root_0 = null;

        Token PLUS11=null;

        CommonTree PLUS11_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 12) ) { return retval; }
            // GuardCommand.g:212:2: ( PLUS )
            // GuardCommand.g:212:4: PLUS
            {
            root_0 = (CommonTree)adaptor.nil();

            PLUS11=(Token)match(input,PLUS,FOLLOW_PLUS_in_plus671); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            PLUS11_tree = (CommonTree)adaptor.create(PLUS11);
            adaptor.addChild(root_0, PLUS11_tree);
            }
            if ( state.backtracking==0 ) {
              retval.type = StatementType.PLUS; retval.vtype = ValueType.INTEGER_TYPE;
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 12, plus_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "plus"

    public static class minus_return extends ParserRuleReturnScope {
        public int type;
        public int vtype;
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "minus"
    // GuardCommand.g:213:1: minus returns [int type, int vtype] : MINUS ;
    public final GuardCommandParser.minus_return minus() throws RecognitionException {
        GuardCommandParser.minus_return retval = new GuardCommandParser.minus_return();
        retval.start = input.LT(1);
        int minus_StartIndex = input.index();
        CommonTree root_0 = null;

        Token MINUS12=null;

        CommonTree MINUS12_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 13) ) { return retval; }
            // GuardCommand.g:214:2: ( MINUS )
            // GuardCommand.g:214:4: MINUS
            {
            root_0 = (CommonTree)adaptor.nil();

            MINUS12=(Token)match(input,MINUS,FOLLOW_MINUS_in_minus684); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            MINUS12_tree = (CommonTree)adaptor.create(MINUS12);
            adaptor.addChild(root_0, MINUS12_tree);
            }
            if ( state.backtracking==0 ) {
              retval.type = StatementType.MINUS; retval.vtype = ValueType.INTEGER_TYPE;
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 13, minus_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "minus"

    public static class term_return extends ParserRuleReturnScope {
        public int type;
        public int vtype;
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "term"
    // GuardCommand.g:215:1: term returns [int type, int vtype] : ( (a= plus ) | (b= minus ) );
    public final GuardCommandParser.term_return term() throws RecognitionException {
        GuardCommandParser.term_return retval = new GuardCommandParser.term_return();
        retval.start = input.LT(1);
        int term_StartIndex = input.index();
        CommonTree root_0 = null;

        GuardCommandParser.plus_return a = null;

        GuardCommandParser.minus_return b = null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 14) ) { return retval; }
            // GuardCommand.g:216:2: ( (a= plus ) | (b= minus ) )
            int alt4=2;
            int LA4_0 = input.LA(1);

            if ( (LA4_0==PLUS) ) {
                alt4=1;
            }
            else if ( (LA4_0==MINUS) ) {
                alt4=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 4, 0, input);

                throw nvae;
            }
            switch (alt4) {
                case 1 :
                    // GuardCommand.g:216:4: (a= plus )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    // GuardCommand.g:216:4: (a= plus )
                    // GuardCommand.g:216:5: a= plus
                    {
                    pushFollow(FOLLOW_plus_in_term700);
                    a=plus();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, a.getTree());
                    if ( state.backtracking==0 ) {
                      retval.type = a.type; retval.vtype = a.vtype;
                    }

                    }


                    }
                    break;
                case 2 :
                    // GuardCommand.g:217:4: (b= minus )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    // GuardCommand.g:217:4: (b= minus )
                    // GuardCommand.g:217:5: b= minus
                    {
                    pushFollow(FOLLOW_minus_in_term711);
                    b=minus();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, b.getTree());
                    if ( state.backtracking==0 ) {
                      retval.type = b.type; retval.vtype = b.vtype;
                    }

                    }


                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 14, term_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "term"

    public static class mul_return extends ParserRuleReturnScope {
        public int type;
        public int vtype;
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "mul"
    // GuardCommand.g:220:1: mul returns [int type, int vtype] : MUL ;
    public final GuardCommandParser.mul_return mul() throws RecognitionException {
        GuardCommandParser.mul_return retval = new GuardCommandParser.mul_return();
        retval.start = input.LT(1);
        int mul_StartIndex = input.index();
        CommonTree root_0 = null;

        Token MUL13=null;

        CommonTree MUL13_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 15) ) { return retval; }
            // GuardCommand.g:221:2: ( MUL )
            // GuardCommand.g:221:4: MUL
            {
            root_0 = (CommonTree)adaptor.nil();

            MUL13=(Token)match(input,MUL,FOLLOW_MUL_in_mul728); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            MUL13_tree = (CommonTree)adaptor.create(MUL13);
            adaptor.addChild(root_0, MUL13_tree);
            }
            if ( state.backtracking==0 ) {
              retval.type = StatementType.MULTIPLY; retval.vtype = ValueType.INTEGER_TYPE;
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 15, mul_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "mul"

    public static class div_return extends ParserRuleReturnScope {
        public int type;
        public int vtype;
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "div"
    // GuardCommand.g:222:1: div returns [int type, int vtype] : DIV ;
    public final GuardCommandParser.div_return div() throws RecognitionException {
        GuardCommandParser.div_return retval = new GuardCommandParser.div_return();
        retval.start = input.LT(1);
        int div_StartIndex = input.index();
        CommonTree root_0 = null;

        Token DIV14=null;

        CommonTree DIV14_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 16) ) { return retval; }
            // GuardCommand.g:223:2: ( DIV )
            // GuardCommand.g:223:4: DIV
            {
            root_0 = (CommonTree)adaptor.nil();

            DIV14=(Token)match(input,DIV,FOLLOW_DIV_in_div741); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            DIV14_tree = (CommonTree)adaptor.create(DIV14);
            adaptor.addChild(root_0, DIV14_tree);
            }
            if ( state.backtracking==0 ) {
              retval.type = StatementType.DIVIDE; retval.vtype = ValueType.INTEGER_TYPE;
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 16, div_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "div"

    public static class factor_return extends ParserRuleReturnScope {
        public int type;
        public int vtype;
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "factor"
    // GuardCommand.g:224:1: factor returns [int type, int vtype] : ( (a= mul ) | (b= div ) );
    public final GuardCommandParser.factor_return factor() throws RecognitionException {
        GuardCommandParser.factor_return retval = new GuardCommandParser.factor_return();
        retval.start = input.LT(1);
        int factor_StartIndex = input.index();
        CommonTree root_0 = null;

        GuardCommandParser.mul_return a = null;

        GuardCommandParser.div_return b = null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 17) ) { return retval; }
            // GuardCommand.g:225:2: ( (a= mul ) | (b= div ) )
            int alt5=2;
            int LA5_0 = input.LA(1);

            if ( (LA5_0==MUL) ) {
                alt5=1;
            }
            else if ( (LA5_0==DIV) ) {
                alt5=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 5, 0, input);

                throw nvae;
            }
            switch (alt5) {
                case 1 :
                    // GuardCommand.g:225:4: (a= mul )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    // GuardCommand.g:225:4: (a= mul )
                    // GuardCommand.g:225:5: a= mul
                    {
                    pushFollow(FOLLOW_mul_in_factor757);
                    a=mul();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, a.getTree());
                    if ( state.backtracking==0 ) {
                      retval.type = a.type; retval.vtype = a.vtype;
                    }

                    }


                    }
                    break;
                case 2 :
                    // GuardCommand.g:226:4: (b= div )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    // GuardCommand.g:226:4: (b= div )
                    // GuardCommand.g:226:5: b= div
                    {
                    pushFollow(FOLLOW_div_in_factor768);
                    b=div();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, b.getTree());
                    if ( state.backtracking==0 ) {
                      retval.type = b.type; retval.vtype = b.vtype;
                    }

                    }


                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 17, factor_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "factor"

    public static class binary_operator_return extends ParserRuleReturnScope {
        public int type;
        public int vtype;
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "binary_operator"
    // GuardCommand.g:229:1: binary_operator returns [int type, int vtype] : (b1= or | b2= and | b3= eqa | b4= rel | b5= term | b6= factor );
    public final GuardCommandParser.binary_operator_return binary_operator() throws RecognitionException {
        GuardCommandParser.binary_operator_return retval = new GuardCommandParser.binary_operator_return();
        retval.start = input.LT(1);
        int binary_operator_StartIndex = input.index();
        CommonTree root_0 = null;

        GuardCommandParser.or_return b1 = null;

        GuardCommandParser.and_return b2 = null;

        GuardCommandParser.eqa_return b3 = null;

        GuardCommandParser.rel_return b4 = null;

        GuardCommandParser.term_return b5 = null;

        GuardCommandParser.factor_return b6 = null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 18) ) { return retval; }
            // GuardCommand.g:230:2: (b1= or | b2= and | b3= eqa | b4= rel | b5= term | b6= factor )
            int alt6=6;
            switch ( input.LA(1) ) {
            case OR:
                {
                alt6=1;
                }
                break;
            case AND:
                {
                alt6=2;
                }
                break;
            case EQ:
            case NEQ:
                {
                alt6=3;
                }
                break;
            case GREATER_THAN:
            case GREATER_EQ:
            case LESS_THAN:
            case LESS_EQ:
                {
                alt6=4;
                }
                break;
            case PLUS:
            case MINUS:
                {
                alt6=5;
                }
                break;
            case MUL:
            case DIV:
                {
                alt6=6;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 6, 0, input);

                throw nvae;
            }

            switch (alt6) {
                case 1 :
                    // GuardCommand.g:230:4: b1= or
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    pushFollow(FOLLOW_or_in_binary_operator789);
                    b1=or();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, b1.getTree());
                    if ( state.backtracking==0 ) {
                      retval.type = b1.type; retval.vtype = b1.vtype;
                    }

                    }
                    break;
                case 2 :
                    // GuardCommand.g:231:4: b2= and
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    pushFollow(FOLLOW_and_in_binary_operator798);
                    b2=and();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, b2.getTree());
                    if ( state.backtracking==0 ) {
                      retval.type = b2.type; retval.vtype = b2.vtype;
                    }

                    }
                    break;
                case 3 :
                    // GuardCommand.g:232:4: b3= eqa
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    pushFollow(FOLLOW_eqa_in_binary_operator807);
                    b3=eqa();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, b3.getTree());
                    if ( state.backtracking==0 ) {
                      retval.type = b3.type; retval.vtype = b3.vtype;
                    }

                    }
                    break;
                case 4 :
                    // GuardCommand.g:233:4: b4= rel
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    pushFollow(FOLLOW_rel_in_binary_operator816);
                    b4=rel();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, b4.getTree());
                    if ( state.backtracking==0 ) {
                      retval.type = b4.type; retval.vtype = b4.vtype;
                    }

                    }
                    break;
                case 5 :
                    // GuardCommand.g:234:4: b5= term
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    pushFollow(FOLLOW_term_in_binary_operator825);
                    b5=term();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, b5.getTree());
                    if ( state.backtracking==0 ) {
                      retval.type = b5.type; retval.vtype = b5.vtype;
                    }

                    }
                    break;
                case 6 :
                    // GuardCommand.g:235:4: b6= factor
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    pushFollow(FOLLOW_factor_in_binary_operator834);
                    b6=factor();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, b6.getTree());
                    if ( state.backtracking==0 ) {
                      retval.type = b6.type; retval.vtype = b6.vtype;
                    }

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 18, binary_operator_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "binary_operator"

    public static class expression_return extends ParserRuleReturnScope {
        public List<Statement> statList;
        public Value val;
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "expression"
    // GuardCommand.g:242:1: expression returns [List<Statement> statList, Value val] : e= expr_or ;
    public final GuardCommandParser.expression_return expression() throws RecognitionException {
        GuardCommandParser.expression_return retval = new GuardCommandParser.expression_return();
        retval.start = input.LT(1);
        int expression_StartIndex = input.index();
        CommonTree root_0 = null;

        GuardCommandParser.expr_or_return e = null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 19) ) { return retval; }
            // GuardCommand.g:243:2: (e= expr_or )
            // GuardCommand.g:243:4: e= expr_or
            {
            root_0 = (CommonTree)adaptor.nil();

            pushFollow(FOLLOW_expr_or_in_expression858);
            e=expr_or();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, e.getTree());
            if ( state.backtracking==0 ) {
              if (e.statList != null) {retval.statList = e.statList;} else {retval.val = e.val;}
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 19, expression_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "expression"

    public static class expr_or_return extends ParserRuleReturnScope {
        public List<Statement> statList;
        public Value val;
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "expr_or"
    // GuardCommand.g:246:1: expr_or returns [List<Statement> statList, Value val] : ( (e1= expr_and b= or e2= expr_and ) (b= or e3= expr_and )* | (e4= expr_and ) );
    public final GuardCommandParser.expr_or_return expr_or() throws RecognitionException {
        GuardCommandParser.expr_or_return retval = new GuardCommandParser.expr_or_return();
        retval.start = input.LT(1);
        int expr_or_StartIndex = input.index();
        CommonTree root_0 = null;

        GuardCommandParser.expr_and_return e1 = null;

        GuardCommandParser.or_return b = null;

        GuardCommandParser.expr_and_return e2 = null;

        GuardCommandParser.expr_and_return e3 = null;

        GuardCommandParser.expr_and_return e4 = null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 20) ) { return retval; }
            // GuardCommand.g:247:2: ( (e1= expr_and b= or e2= expr_and ) (b= or e3= expr_and )* | (e4= expr_and ) )
            int alt8=2;
            alt8 = dfa8.predict(input);
            switch (alt8) {
                case 1 :
                    // GuardCommand.g:248:2: (e1= expr_and b= or e2= expr_and ) (b= or e3= expr_and )*
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    // GuardCommand.g:248:2: (e1= expr_and b= or e2= expr_and )
                    // GuardCommand.g:248:3: e1= expr_and b= or e2= expr_and
                    {
                    pushFollow(FOLLOW_expr_and_in_expr_or879);
                    e1=expr_and();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, e1.getTree());
                    pushFollow(FOLLOW_or_in_expr_or883);
                    b=or();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, b.getTree());
                    pushFollow(FOLLOW_expr_and_in_expr_or887);
                    e2=expr_and();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, e2.getTree());
                    if ( state.backtracking==0 ) {
                      retval.statList = handleExpression(
                      				e1.statList, e2.statList, e1.val, e2.val, b.type, b.tree, b.vtype
                      		);
                    }

                    }

                    // GuardCommand.g:253:2: (b= or e3= expr_and )*
                    loop7:
                    do {
                        int alt7=2;
                        int LA7_0 = input.LA(1);

                        if ( (LA7_0==OR) ) {
                            alt7=1;
                        }


                        switch (alt7) {
                    	case 1 :
                    	    // GuardCommand.g:253:3: b= or e3= expr_and
                    	    {
                    	    pushFollow(FOLLOW_or_in_expr_or900);
                    	    b=or();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) adaptor.addChild(root_0, b.getTree());
                    	    pushFollow(FOLLOW_expr_and_in_expr_or904);
                    	    e3=expr_and();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) adaptor.addChild(root_0, e3.getTree());
                    	    if ( state.backtracking==0 ) {
                    	      handleExpression(
                    	      			retval.statList, e3.statList, null, e3.val, b.type, b.tree, b.vtype
                    	      		);
                    	    }

                    	    }
                    	    break;

                    	default :
                    	    break loop7;
                        }
                    } while (true);


                    }
                    break;
                case 2 :
                    // GuardCommand.g:258:4: (e4= expr_and )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    // GuardCommand.g:258:4: (e4= expr_and )
                    // GuardCommand.g:258:5: e4= expr_and
                    {
                    pushFollow(FOLLOW_expr_and_in_expr_or920);
                    e4=expr_and();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, e4.getTree());

                    }

                    if ( state.backtracking==0 ) {
                      if (e4.statList != null) {retval.statList = e4.statList;} else {retval.val = e4.val;}
                    }

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 20, expr_or_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "expr_or"

    public static class expr_and_return extends ParserRuleReturnScope {
        public List<Statement> statList;
        public Value val;
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "expr_and"
    // GuardCommand.g:261:1: expr_and returns [List<Statement> statList, Value val] : ( (e1= expr_eqa b= and e2= expr_eqa ) (b= and e3= expr_eqa )* | (e4= expr_eqa ) );
    public final GuardCommandParser.expr_and_return expr_and() throws RecognitionException {
        GuardCommandParser.expr_and_return retval = new GuardCommandParser.expr_and_return();
        retval.start = input.LT(1);
        int expr_and_StartIndex = input.index();
        CommonTree root_0 = null;

        GuardCommandParser.expr_eqa_return e1 = null;

        GuardCommandParser.and_return b = null;

        GuardCommandParser.expr_eqa_return e2 = null;

        GuardCommandParser.expr_eqa_return e3 = null;

        GuardCommandParser.expr_eqa_return e4 = null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 21) ) { return retval; }
            // GuardCommand.g:262:2: ( (e1= expr_eqa b= and e2= expr_eqa ) (b= and e3= expr_eqa )* | (e4= expr_eqa ) )
            int alt10=2;
            alt10 = dfa10.predict(input);
            switch (alt10) {
                case 1 :
                    // GuardCommand.g:263:2: (e1= expr_eqa b= and e2= expr_eqa ) (b= and e3= expr_eqa )*
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    // GuardCommand.g:263:2: (e1= expr_eqa b= and e2= expr_eqa )
                    // GuardCommand.g:263:3: e1= expr_eqa b= and e2= expr_eqa
                    {
                    pushFollow(FOLLOW_expr_eqa_in_expr_and943);
                    e1=expr_eqa();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, e1.getTree());
                    pushFollow(FOLLOW_and_in_expr_and947);
                    b=and();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, b.getTree());
                    pushFollow(FOLLOW_expr_eqa_in_expr_and951);
                    e2=expr_eqa();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, e2.getTree());
                    if ( state.backtracking==0 ) {
                      retval.statList = handleExpression(
                      				e1.statList, e2.statList, e1.val, e2.val, b.type, b.tree, b.vtype
                      		);
                    }

                    }

                    // GuardCommand.g:268:2: (b= and e3= expr_eqa )*
                    loop9:
                    do {
                        int alt9=2;
                        int LA9_0 = input.LA(1);

                        if ( (LA9_0==AND) ) {
                            alt9=1;
                        }


                        switch (alt9) {
                    	case 1 :
                    	    // GuardCommand.g:268:3: b= and e3= expr_eqa
                    	    {
                    	    pushFollow(FOLLOW_and_in_expr_and964);
                    	    b=and();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) adaptor.addChild(root_0, b.getTree());
                    	    pushFollow(FOLLOW_expr_eqa_in_expr_and968);
                    	    e3=expr_eqa();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) adaptor.addChild(root_0, e3.getTree());
                    	    if ( state.backtracking==0 ) {
                    	      handleExpression(
                    	      			retval.statList, e3.statList, null, e3.val, b.type, b.tree, b.vtype
                    	      		);
                    	    }

                    	    }
                    	    break;

                    	default :
                    	    break loop9;
                        }
                    } while (true);


                    }
                    break;
                case 2 :
                    // GuardCommand.g:273:4: (e4= expr_eqa )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    // GuardCommand.g:273:4: (e4= expr_eqa )
                    // GuardCommand.g:273:5: e4= expr_eqa
                    {
                    pushFollow(FOLLOW_expr_eqa_in_expr_and984);
                    e4=expr_eqa();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, e4.getTree());

                    }

                    if ( state.backtracking==0 ) {
                      if (e4.statList != null) {retval.statList = e4.statList;} else {retval.val = e4.val;}
                    }

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 21, expr_and_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "expr_and"

    public static class expr_eqa_return extends ParserRuleReturnScope {
        public List<Statement> statList;
        public Value val;
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "expr_eqa"
    // GuardCommand.g:277:1: expr_eqa returns [List<Statement> statList, Value val] : ( (e1= expr_rel b= eqa e2= expr_rel ) (b= eqa e3= expr_rel )* | (e4= expr_rel ) );
    public final GuardCommandParser.expr_eqa_return expr_eqa() throws RecognitionException {
        GuardCommandParser.expr_eqa_return retval = new GuardCommandParser.expr_eqa_return();
        retval.start = input.LT(1);
        int expr_eqa_StartIndex = input.index();
        CommonTree root_0 = null;

        GuardCommandParser.expr_rel_return e1 = null;

        GuardCommandParser.eqa_return b = null;

        GuardCommandParser.expr_rel_return e2 = null;

        GuardCommandParser.expr_rel_return e3 = null;

        GuardCommandParser.expr_rel_return e4 = null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 22) ) { return retval; }
            // GuardCommand.g:278:2: ( (e1= expr_rel b= eqa e2= expr_rel ) (b= eqa e3= expr_rel )* | (e4= expr_rel ) )
            int alt12=2;
            alt12 = dfa12.predict(input);
            switch (alt12) {
                case 1 :
                    // GuardCommand.g:279:2: (e1= expr_rel b= eqa e2= expr_rel ) (b= eqa e3= expr_rel )*
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    // GuardCommand.g:279:2: (e1= expr_rel b= eqa e2= expr_rel )
                    // GuardCommand.g:279:3: e1= expr_rel b= eqa e2= expr_rel
                    {
                    pushFollow(FOLLOW_expr_rel_in_expr_eqa1008);
                    e1=expr_rel();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, e1.getTree());
                    pushFollow(FOLLOW_eqa_in_expr_eqa1012);
                    b=eqa();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, b.getTree());
                    pushFollow(FOLLOW_expr_rel_in_expr_eqa1016);
                    e2=expr_rel();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, e2.getTree());
                    if ( state.backtracking==0 ) {
                      retval.statList = handleExpression(
                      				e1.statList, e2.statList, e1.val, e2.val, b.type, b.tree, b.vtype
                      		);
                    }

                    }

                    // GuardCommand.g:284:2: (b= eqa e3= expr_rel )*
                    loop11:
                    do {
                        int alt11=2;
                        int LA11_0 = input.LA(1);

                        if ( ((LA11_0>=EQ && LA11_0<=NEQ)) ) {
                            alt11=1;
                        }


                        switch (alt11) {
                    	case 1 :
                    	    // GuardCommand.g:284:3: b= eqa e3= expr_rel
                    	    {
                    	    pushFollow(FOLLOW_eqa_in_expr_eqa1029);
                    	    b=eqa();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) adaptor.addChild(root_0, b.getTree());
                    	    pushFollow(FOLLOW_expr_rel_in_expr_eqa1033);
                    	    e3=expr_rel();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) adaptor.addChild(root_0, e3.getTree());
                    	    if ( state.backtracking==0 ) {
                    	      handleExpression(
                    	      			retval.statList, e3.statList, null, e3.val, b.type, b.tree, b.vtype
                    	      		);
                    	    }

                    	    }
                    	    break;

                    	default :
                    	    break loop11;
                        }
                    } while (true);


                    }
                    break;
                case 2 :
                    // GuardCommand.g:289:4: (e4= expr_rel )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    // GuardCommand.g:289:4: (e4= expr_rel )
                    // GuardCommand.g:289:5: e4= expr_rel
                    {
                    pushFollow(FOLLOW_expr_rel_in_expr_eqa1049);
                    e4=expr_rel();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, e4.getTree());

                    }

                    if ( state.backtracking==0 ) {
                      if (e4.statList != null) {retval.statList = e4.statList;} else {retval.val = e4.val;}
                    }

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 22, expr_eqa_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "expr_eqa"

    public static class expr_rel_return extends ParserRuleReturnScope {
        public List<Statement> statList;
        public Value val;
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "expr_rel"
    // GuardCommand.g:292:1: expr_rel returns [List<Statement> statList, Value val] : ( (e1= expr_term b= rel e2= expr_term ) (b= rel e3= expr_term )* | (e4= expr_term ) );
    public final GuardCommandParser.expr_rel_return expr_rel() throws RecognitionException {
        GuardCommandParser.expr_rel_return retval = new GuardCommandParser.expr_rel_return();
        retval.start = input.LT(1);
        int expr_rel_StartIndex = input.index();
        CommonTree root_0 = null;

        GuardCommandParser.expr_term_return e1 = null;

        GuardCommandParser.rel_return b = null;

        GuardCommandParser.expr_term_return e2 = null;

        GuardCommandParser.expr_term_return e3 = null;

        GuardCommandParser.expr_term_return e4 = null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 23) ) { return retval; }
            // GuardCommand.g:293:2: ( (e1= expr_term b= rel e2= expr_term ) (b= rel e3= expr_term )* | (e4= expr_term ) )
            int alt14=2;
            alt14 = dfa14.predict(input);
            switch (alt14) {
                case 1 :
                    // GuardCommand.g:294:2: (e1= expr_term b= rel e2= expr_term ) (b= rel e3= expr_term )*
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    // GuardCommand.g:294:2: (e1= expr_term b= rel e2= expr_term )
                    // GuardCommand.g:294:3: e1= expr_term b= rel e2= expr_term
                    {
                    pushFollow(FOLLOW_expr_term_in_expr_rel1071);
                    e1=expr_term();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, e1.getTree());
                    pushFollow(FOLLOW_rel_in_expr_rel1075);
                    b=rel();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, b.getTree());
                    pushFollow(FOLLOW_expr_term_in_expr_rel1079);
                    e2=expr_term();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, e2.getTree());
                    if ( state.backtracking==0 ) {
                      retval.statList = handleExpression(
                      				e1.statList, e2.statList, e1.val, e2.val, b.type, b.tree, b.vtype
                      		);
                    }

                    }

                    // GuardCommand.g:299:2: (b= rel e3= expr_term )*
                    loop13:
                    do {
                        int alt13=2;
                        int LA13_0 = input.LA(1);

                        if ( ((LA13_0>=GREATER_THAN && LA13_0<=LESS_EQ)) ) {
                            alt13=1;
                        }


                        switch (alt13) {
                    	case 1 :
                    	    // GuardCommand.g:299:3: b= rel e3= expr_term
                    	    {
                    	    pushFollow(FOLLOW_rel_in_expr_rel1092);
                    	    b=rel();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) adaptor.addChild(root_0, b.getTree());
                    	    pushFollow(FOLLOW_expr_term_in_expr_rel1096);
                    	    e3=expr_term();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) adaptor.addChild(root_0, e3.getTree());
                    	    if ( state.backtracking==0 ) {
                    	      handleExpression(
                    	      			retval.statList, e3.statList, null, e3.val, b.type, b.tree, b.vtype
                    	      		);
                    	    }

                    	    }
                    	    break;

                    	default :
                    	    break loop13;
                        }
                    } while (true);


                    }
                    break;
                case 2 :
                    // GuardCommand.g:304:4: (e4= expr_term )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    // GuardCommand.g:304:4: (e4= expr_term )
                    // GuardCommand.g:304:5: e4= expr_term
                    {
                    pushFollow(FOLLOW_expr_term_in_expr_rel1112);
                    e4=expr_term();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, e4.getTree());

                    }

                    if ( state.backtracking==0 ) {
                      if (e4.statList != null) {retval.statList = e4.statList;} else {retval.val = e4.val;}
                    }

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 23, expr_rel_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "expr_rel"

    public static class expr_term_return extends ParserRuleReturnScope {
        public List<Statement> statList;
        public Value val;
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "expr_term"
    // GuardCommand.g:307:1: expr_term returns [List<Statement> statList, Value val] : ( (e1= expr_factor b= term e2= expr_factor ) (b= term e3= expr_factor )* | (e4= expr_factor ) );
    public final GuardCommandParser.expr_term_return expr_term() throws RecognitionException {
        GuardCommandParser.expr_term_return retval = new GuardCommandParser.expr_term_return();
        retval.start = input.LT(1);
        int expr_term_StartIndex = input.index();
        CommonTree root_0 = null;

        GuardCommandParser.expr_factor_return e1 = null;

        GuardCommandParser.term_return b = null;

        GuardCommandParser.expr_factor_return e2 = null;

        GuardCommandParser.expr_factor_return e3 = null;

        GuardCommandParser.expr_factor_return e4 = null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 24) ) { return retval; }
            // GuardCommand.g:308:2: ( (e1= expr_factor b= term e2= expr_factor ) (b= term e3= expr_factor )* | (e4= expr_factor ) )
            int alt16=2;
            alt16 = dfa16.predict(input);
            switch (alt16) {
                case 1 :
                    // GuardCommand.g:309:2: (e1= expr_factor b= term e2= expr_factor ) (b= term e3= expr_factor )*
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    // GuardCommand.g:309:2: (e1= expr_factor b= term e2= expr_factor )
                    // GuardCommand.g:309:3: e1= expr_factor b= term e2= expr_factor
                    {
                    pushFollow(FOLLOW_expr_factor_in_expr_term1135);
                    e1=expr_factor();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, e1.getTree());
                    pushFollow(FOLLOW_term_in_expr_term1139);
                    b=term();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, b.getTree());
                    pushFollow(FOLLOW_expr_factor_in_expr_term1143);
                    e2=expr_factor();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, e2.getTree());
                    if ( state.backtracking==0 ) {
                      retval.statList = handleExpression(
                      				e1.statList, e2.statList, e1.val, e2.val, b.type, b.tree, b.vtype
                      		);
                    }

                    }

                    // GuardCommand.g:314:2: (b= term e3= expr_factor )*
                    loop15:
                    do {
                        int alt15=2;
                        int LA15_0 = input.LA(1);

                        if ( ((LA15_0>=PLUS && LA15_0<=MINUS)) ) {
                            alt15=1;
                        }


                        switch (alt15) {
                    	case 1 :
                    	    // GuardCommand.g:314:3: b= term e3= expr_factor
                    	    {
                    	    pushFollow(FOLLOW_term_in_expr_term1156);
                    	    b=term();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) adaptor.addChild(root_0, b.getTree());
                    	    pushFollow(FOLLOW_expr_factor_in_expr_term1160);
                    	    e3=expr_factor();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) adaptor.addChild(root_0, e3.getTree());
                    	    if ( state.backtracking==0 ) {
                    	      handleExpression(
                    	      			retval.statList, e3.statList, null, e3.val, b.type, b.tree, b.vtype
                    	      		);
                    	    }

                    	    }
                    	    break;

                    	default :
                    	    break loop15;
                        }
                    } while (true);


                    }
                    break;
                case 2 :
                    // GuardCommand.g:319:4: (e4= expr_factor )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    // GuardCommand.g:319:4: (e4= expr_factor )
                    // GuardCommand.g:319:5: e4= expr_factor
                    {
                    pushFollow(FOLLOW_expr_factor_in_expr_term1176);
                    e4=expr_factor();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, e4.getTree());

                    }

                    if ( state.backtracking==0 ) {
                      if (e4.statList != null) {retval.statList = e4.statList;} else {retval.val = e4.val;}
                    }

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 24, expr_term_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "expr_term"

    public static class expr_factor_return extends ParserRuleReturnScope {
        public List<Statement> statList;
        public Value val;
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "expr_factor"
    // GuardCommand.g:322:1: expr_factor returns [List<Statement> statList, Value val] : ( (e1= expr_unary b= factor e2= expr_unary ) (b= factor e3= expr_unary )* | (e4= expr_unary ) );
    public final GuardCommandParser.expr_factor_return expr_factor() throws RecognitionException {
        GuardCommandParser.expr_factor_return retval = new GuardCommandParser.expr_factor_return();
        retval.start = input.LT(1);
        int expr_factor_StartIndex = input.index();
        CommonTree root_0 = null;

        GuardCommandParser.expr_unary_return e1 = null;

        GuardCommandParser.factor_return b = null;

        GuardCommandParser.expr_unary_return e2 = null;

        GuardCommandParser.expr_unary_return e3 = null;

        GuardCommandParser.expr_unary_return e4 = null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 25) ) { return retval; }
            // GuardCommand.g:323:2: ( (e1= expr_unary b= factor e2= expr_unary ) (b= factor e3= expr_unary )* | (e4= expr_unary ) )
            int alt18=2;
            alt18 = dfa18.predict(input);
            switch (alt18) {
                case 1 :
                    // GuardCommand.g:324:2: (e1= expr_unary b= factor e2= expr_unary ) (b= factor e3= expr_unary )*
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    // GuardCommand.g:324:2: (e1= expr_unary b= factor e2= expr_unary )
                    // GuardCommand.g:324:3: e1= expr_unary b= factor e2= expr_unary
                    {
                    pushFollow(FOLLOW_expr_unary_in_expr_factor1199);
                    e1=expr_unary();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, e1.getTree());
                    pushFollow(FOLLOW_factor_in_expr_factor1203);
                    b=factor();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, b.getTree());
                    pushFollow(FOLLOW_expr_unary_in_expr_factor1207);
                    e2=expr_unary();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, e2.getTree());
                    if ( state.backtracking==0 ) {
                      retval.statList = handleExpression(
                      				e1.statList, e2.statList, e1.val, e2.val, b.type, b.tree, b.vtype
                      		);
                    }

                    }

                    // GuardCommand.g:329:2: (b= factor e3= expr_unary )*
                    loop17:
                    do {
                        int alt17=2;
                        int LA17_0 = input.LA(1);

                        if ( ((LA17_0>=MUL && LA17_0<=DIV)) ) {
                            alt17=1;
                        }


                        switch (alt17) {
                    	case 1 :
                    	    // GuardCommand.g:329:3: b= factor e3= expr_unary
                    	    {
                    	    pushFollow(FOLLOW_factor_in_expr_factor1220);
                    	    b=factor();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) adaptor.addChild(root_0, b.getTree());
                    	    pushFollow(FOLLOW_expr_unary_in_expr_factor1224);
                    	    e3=expr_unary();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) adaptor.addChild(root_0, e3.getTree());
                    	    if ( state.backtracking==0 ) {
                    	      handleExpression(
                    	      			retval.statList, e3.statList, null, e3.val, b.type, b.tree, b.vtype
                    	      		);
                    	    }

                    	    }
                    	    break;

                    	default :
                    	    break loop17;
                        }
                    } while (true);


                    }
                    break;
                case 2 :
                    // GuardCommand.g:334:4: (e4= expr_unary )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    // GuardCommand.g:334:4: (e4= expr_unary )
                    // GuardCommand.g:334:5: e4= expr_unary
                    {
                    pushFollow(FOLLOW_expr_unary_in_expr_factor1240);
                    e4=expr_unary();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, e4.getTree());

                    }

                    if ( state.backtracking==0 ) {
                      if (e4.statList != null) {retval.statList = e4.statList;} else {retval.val = e4.val;}
                    }

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 25, expr_factor_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "expr_factor"

    public static class expr_unary_return extends ParserRuleReturnScope {
        public List<Statement> statList;
        public Value val;
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "expr_unary"
    // GuardCommand.g:337:1: expr_unary returns [List<Statement> statList, Value val] : (u= unary_operator e= expr_unary | l= lite | lp= lite_paren );
    public final GuardCommandParser.expr_unary_return expr_unary() throws RecognitionException {
        GuardCommandParser.expr_unary_return retval = new GuardCommandParser.expr_unary_return();
        retval.start = input.LT(1);
        int expr_unary_StartIndex = input.index();
        CommonTree root_0 = null;

        GuardCommandParser.unary_operator_return u = null;

        GuardCommandParser.expr_unary_return e = null;

        GuardCommandParser.lite_return l = null;

        GuardCommandParser.lite_paren_return lp = null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 26) ) { return retval; }
            // GuardCommand.g:338:2: (u= unary_operator e= expr_unary | l= lite | lp= lite_paren )
            int alt19=3;
            switch ( input.LA(1) ) {
            case MINUS:
            case NOT:
                {
                alt19=1;
                }
                break;
            case TRUE:
            case FALSE:
            case INTEGER_LITERAL:
            case IDENTIFIER:
                {
                alt19=2;
                }
                break;
            case LPAREN:
                {
                alt19=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 19, 0, input);

                throw nvae;
            }

            switch (alt19) {
                case 1 :
                    // GuardCommand.g:338:4: u= unary_operator e= expr_unary
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    pushFollow(FOLLOW_unary_operator_in_expr_unary1260);
                    u=unary_operator();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, u.getTree());
                    pushFollow(FOLLOW_expr_unary_in_expr_unary1264);
                    e=expr_unary();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, e.getTree());
                    if ( state.backtracking==0 ) {

                      			retval.statList = handleUnaryExpression(e.statList, e.val, u.type, u.tree,
                      				variableTable.createTemporaryVariable(u.vtype), null
                      			);
                      		
                    }

                    }
                    break;
                case 2 :
                    // GuardCommand.g:344:4: l= lite
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    pushFollow(FOLLOW_lite_in_expr_unary1275);
                    l=lite();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, l.getTree());
                    if ( state.backtracking==0 ) {
                      retval.val = l.val;
                    }

                    }
                    break;
                case 3 :
                    // GuardCommand.g:345:4: lp= lite_paren
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    pushFollow(FOLLOW_lite_paren_in_expr_unary1284);
                    lp=lite_paren();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, lp.getTree());
                    if ( state.backtracking==0 ) {
                      retval.statList = lp.statList; retval.val = lp.val;
                    }

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 26, expr_unary_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "expr_unary"

    public static class literal_return extends ParserRuleReturnScope {
        public List<Statement> statList;
        public Value val;
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "literal"
    // GuardCommand.g:348:1: literal returns [List<Statement> statList, Value val] : (l= lite | lp= lite_paren );
    public final GuardCommandParser.literal_return literal() throws RecognitionException {
        GuardCommandParser.literal_return retval = new GuardCommandParser.literal_return();
        retval.start = input.LT(1);
        int literal_StartIndex = input.index();
        CommonTree root_0 = null;

        GuardCommandParser.lite_return l = null;

        GuardCommandParser.lite_paren_return lp = null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 27) ) { return retval; }
            // GuardCommand.g:349:2: (l= lite | lp= lite_paren )
            int alt20=2;
            int LA20_0 = input.LA(1);

            if ( ((LA20_0>=TRUE && LA20_0<=IDENTIFIER)) ) {
                alt20=1;
            }
            else if ( (LA20_0==LPAREN) ) {
                alt20=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 20, 0, input);

                throw nvae;
            }
            switch (alt20) {
                case 1 :
                    // GuardCommand.g:349:4: l= lite
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    pushFollow(FOLLOW_lite_in_literal1303);
                    l=lite();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, l.getTree());
                    if ( state.backtracking==0 ) {
                      retval.val = l.val;
                    }

                    }
                    break;
                case 2 :
                    // GuardCommand.g:350:4: lp= lite_paren
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    pushFollow(FOLLOW_lite_paren_in_literal1312);
                    lp=lite_paren();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, lp.getTree());
                    if ( state.backtracking==0 ) {
                      retval.statList = lp.statList; retval.val = lp.val;
                    }

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 27, literal_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "literal"

    public static class lite_return extends ParserRuleReturnScope {
        public Value val;
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "lite"
    // GuardCommand.g:353:1: lite returns [Value val] : (inte= INTEGER_LITERAL | tru= TRUE | fal= FALSE | id= IDENTIFIER );
    public final GuardCommandParser.lite_return lite() throws RecognitionException {
        GuardCommandParser.lite_return retval = new GuardCommandParser.lite_return();
        retval.start = input.LT(1);
        int lite_StartIndex = input.index();
        CommonTree root_0 = null;

        Token inte=null;
        Token tru=null;
        Token fal=null;
        Token id=null;

        CommonTree inte_tree=null;
        CommonTree tru_tree=null;
        CommonTree fal_tree=null;
        CommonTree id_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 28) ) { return retval; }
            // GuardCommand.g:354:2: (inte= INTEGER_LITERAL | tru= TRUE | fal= FALSE | id= IDENTIFIER )
            int alt21=4;
            switch ( input.LA(1) ) {
            case INTEGER_LITERAL:
                {
                alt21=1;
                }
                break;
            case TRUE:
                {
                alt21=2;
                }
                break;
            case FALSE:
                {
                alt21=3;
                }
                break;
            case IDENTIFIER:
                {
                alt21=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 21, 0, input);

                throw nvae;
            }

            switch (alt21) {
                case 1 :
                    // GuardCommand.g:355:2: inte= INTEGER_LITERAL
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    inte=(Token)match(input,INTEGER_LITERAL,FOLLOW_INTEGER_LITERAL_in_lite1332); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    inte_tree = (CommonTree)adaptor.create(inte);
                    adaptor.addChild(root_0, inte_tree);
                    }
                    if ( state.backtracking==0 ) {

                      			retval.val = ConstantValue.getConstantValue(
                      				ValueType.INTEGER_TYPE, Integer.parseInt(inte.getText())
                      			);
                      		
                    }

                    }
                    break;
                case 2 :
                    // GuardCommand.g:361:4: tru= TRUE
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    tru=(Token)match(input,TRUE,FOLLOW_TRUE_in_lite1343); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    tru_tree = (CommonTree)adaptor.create(tru);
                    adaptor.addChild(root_0, tru_tree);
                    }
                    if ( state.backtracking==0 ) {
                      retval.val = ConstantValue.TRUE;
                    }

                    }
                    break;
                case 3 :
                    // GuardCommand.g:362:4: fal= FALSE
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    fal=(Token)match(input,FALSE,FOLLOW_FALSE_in_lite1352); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    fal_tree = (CommonTree)adaptor.create(fal);
                    adaptor.addChild(root_0, fal_tree);
                    }
                    if ( state.backtracking==0 ) {
                      retval.val = ConstantValue.FALSE;
                    }

                    }
                    break;
                case 4 :
                    // GuardCommand.g:363:4: id= IDENTIFIER
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    id=(Token)match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_lite1361); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    id_tree = (CommonTree)adaptor.create(id);
                    adaptor.addChild(root_0, id_tree);
                    }
                    if ( state.backtracking==0 ) {
                      retval.val = variableTable.getVariable(id.getText());
                    }

                    }
                    break;

            }
            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 28, lite_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "lite"

    public static class lite_paren_return extends ParserRuleReturnScope {
        public List<Statement> statList;
        public Value val;
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "lite_paren"
    // GuardCommand.g:366:1: lite_paren returns [List<Statement> statList, Value val] : LPAREN e= expression RPAREN ;
    public final GuardCommandParser.lite_paren_return lite_paren() throws RecognitionException {
        GuardCommandParser.lite_paren_return retval = new GuardCommandParser.lite_paren_return();
        retval.start = input.LT(1);
        int lite_paren_StartIndex = input.index();
        CommonTree root_0 = null;

        Token LPAREN15=null;
        Token RPAREN16=null;
        GuardCommandParser.expression_return e = null;


        CommonTree LPAREN15_tree=null;
        CommonTree RPAREN16_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 29) ) { return retval; }
            // GuardCommand.g:367:2: ( LPAREN e= expression RPAREN )
            // GuardCommand.g:367:4: LPAREN e= expression RPAREN
            {
            root_0 = (CommonTree)adaptor.nil();

            LPAREN15=(Token)match(input,LPAREN,FOLLOW_LPAREN_in_lite_paren1378); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            LPAREN15_tree = (CommonTree)adaptor.create(LPAREN15);
            adaptor.addChild(root_0, LPAREN15_tree);
            }
            pushFollow(FOLLOW_expression_in_lite_paren1382);
            e=expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, e.getTree());
            RPAREN16=(Token)match(input,RPAREN,FOLLOW_RPAREN_in_lite_paren1384); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            RPAREN16_tree = (CommonTree)adaptor.create(RPAREN16);
            adaptor.addChild(root_0, RPAREN16_tree);
            }
            if ( state.backtracking==0 ) {

              			retval.statList = e.statList; retval.val = e.val;
              		
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 29, lite_paren_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "lite_paren"

    public static class command_return extends ParserRuleReturnScope {
        public List<Statement> commands;
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "command"
    // GuardCommand.g:377:1: command returns [List<Statement> commands] : (a= assignment_cmd | b= skip_cmd | c= abort_cmd | d= read_cmd | e= write_cmd | l= LCURLY f= command RCURLY | g= if_cmd | h= do_cmd ) ( SEMI i= command )* ;
    public final GuardCommandParser.command_return command() throws RecognitionException {
        GuardCommandParser.command_return retval = new GuardCommandParser.command_return();
        retval.start = input.LT(1);
        int command_StartIndex = input.index();
        CommonTree root_0 = null;

        Token l=null;
        Token RCURLY17=null;
        Token SEMI18=null;
        GuardCommandParser.assignment_cmd_return a = null;

        GuardCommandParser.skip_cmd_return b = null;

        GuardCommandParser.abort_cmd_return c = null;

        GuardCommandParser.read_cmd_return d = null;

        GuardCommandParser.write_cmd_return e = null;

        GuardCommandParser.command_return f = null;

        GuardCommandParser.if_cmd_return g = null;

        GuardCommandParser.do_cmd_return h = null;

        GuardCommandParser.command_return i = null;


        CommonTree l_tree=null;
        CommonTree RCURLY17_tree=null;
        CommonTree SEMI18_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 30) ) { return retval; }
            // GuardCommand.g:378:2: ( (a= assignment_cmd | b= skip_cmd | c= abort_cmd | d= read_cmd | e= write_cmd | l= LCURLY f= command RCURLY | g= if_cmd | h= do_cmd ) ( SEMI i= command )* )
            // GuardCommand.g:379:2: (a= assignment_cmd | b= skip_cmd | c= abort_cmd | d= read_cmd | e= write_cmd | l= LCURLY f= command RCURLY | g= if_cmd | h= do_cmd ) ( SEMI i= command )*
            {
            root_0 = (CommonTree)adaptor.nil();

            if ( state.backtracking==0 ) {

              		if (retval.commands == null) {
              			retval.commands = new ArrayList<Statement>();
              		}
              	
            }
            // GuardCommand.g:385:2: (a= assignment_cmd | b= skip_cmd | c= abort_cmd | d= read_cmd | e= write_cmd | l= LCURLY f= command RCURLY | g= if_cmd | h= do_cmd )
            int alt22=8;
            switch ( input.LA(1) ) {
            case IDENTIFIER:
                {
                alt22=1;
                }
                break;
            case SKIP:
                {
                alt22=2;
                }
                break;
            case ABORT:
                {
                alt22=3;
                }
                break;
            case READ:
                {
                alt22=4;
                }
                break;
            case WRITE:
                {
                alt22=5;
                }
                break;
            case LCURLY:
                {
                alt22=6;
                }
                break;
            case IF:
                {
                alt22=7;
                }
                break;
            case DO:
                {
                alt22=8;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 22, 0, input);

                throw nvae;
            }

            switch (alt22) {
                case 1 :
                    // GuardCommand.g:385:4: a= assignment_cmd
                    {
                    pushFollow(FOLLOW_assignment_cmd_in_command1418);
                    a=assignment_cmd();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, a.getTree());
                    if ( state.backtracking==0 ) {
                      retval.commands.addAll(a.commands);
                    }

                    }
                    break;
                case 2 :
                    // GuardCommand.g:386:4: b= skip_cmd
                    {
                    pushFollow(FOLLOW_skip_cmd_in_command1429);
                    b=skip_cmd();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, b.getTree());
                    if ( state.backtracking==0 ) {
                      retval.commands.add(b.command);
                    }

                    }
                    break;
                case 3 :
                    // GuardCommand.g:387:4: c= abort_cmd
                    {
                    pushFollow(FOLLOW_abort_cmd_in_command1441);
                    c=abort_cmd();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, c.getTree());
                    if ( state.backtracking==0 ) {
                      retval.commands.add(c.command);
                    }

                    }
                    break;
                case 4 :
                    // GuardCommand.g:388:4: d= read_cmd
                    {
                    pushFollow(FOLLOW_read_cmd_in_command1453);
                    d=read_cmd();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, d.getTree());
                    if ( state.backtracking==0 ) {
                      retval.commands.add(d.command);
                    }

                    }
                    break;
                case 5 :
                    // GuardCommand.g:389:4: e= write_cmd
                    {
                    pushFollow(FOLLOW_write_cmd_in_command1465);
                    e=write_cmd();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, e.getTree());
                    if ( state.backtracking==0 ) {
                      retval.commands.addAll(e.commands);
                    }

                    }
                    break;
                case 6 :
                    // GuardCommand.g:390:4: l= LCURLY f= command RCURLY
                    {
                    l=(Token)match(input,LCURLY,FOLLOW_LCURLY_in_command1477); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    l_tree = (CommonTree)adaptor.create(l);
                    adaptor.addChild(root_0, l_tree);
                    }
                    pushFollow(FOLLOW_command_in_command1481);
                    f=command();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, f.getTree());
                    RCURLY17=(Token)match(input,RCURLY,FOLLOW_RCURLY_in_command1483); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    RCURLY17_tree = (CommonTree)adaptor.create(RCURLY17);
                    adaptor.addChild(root_0, RCURLY17_tree);
                    }
                    if ( state.backtracking==0 ) {

                      			retval.commands.add(statementFactory.createCompoundStatement(
                      				StatementType.SCOPE, new CodeLocation(l_tree.getLine()), annotations, f.commands
                      			));
                      			annotations.clear();
                      		
                    }

                    }
                    break;
                case 7 :
                    // GuardCommand.g:397:4: g= if_cmd
                    {
                    pushFollow(FOLLOW_if_cmd_in_command1494);
                    g=if_cmd();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, g.getTree());
                    if ( state.backtracking==0 ) {
                      retval.commands.add(g.command);
                    }

                    }
                    break;
                case 8 :
                    // GuardCommand.g:398:4: h= do_cmd
                    {
                    pushFollow(FOLLOW_do_cmd_in_command1508);
                    h=do_cmd();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, h.getTree());
                    if ( state.backtracking==0 ) {
                      retval.commands.add(h.command);
                    }

                    }
                    break;

            }

            // GuardCommand.g:401:2: ( SEMI i= command )*
            loop23:
            do {
                int alt23=2;
                int LA23_0 = input.LA(1);

                if ( (LA23_0==SEMI) ) {
                    int LA23_2 = input.LA(2);

                    if ( (synpred38_GuardCommand()) ) {
                        alt23=1;
                    }


                }


                switch (alt23) {
            	case 1 :
            	    // GuardCommand.g:401:3: SEMI i= command
            	    {
            	    SEMI18=(Token)match(input,SEMI,FOLLOW_SEMI_in_command1524); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    SEMI18_tree = (CommonTree)adaptor.create(SEMI18);
            	    adaptor.addChild(root_0, SEMI18_tree);
            	    }
            	    pushFollow(FOLLOW_command_in_command1528);
            	    i=command();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, i.getTree());
            	    if ( state.backtracking==0 ) {

            	      			//TODO: Ensure that skip statements are not added.
            	      			retval.commands.addAll(i.commands);
            	      		
            	    }

            	    }
            	    break;

            	default :
            	    break loop23;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 30, command_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "command"

    public static class assignment_cmd_return extends ParserRuleReturnScope {
        public List<Statement> commands;
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "assignment_cmd"
    // GuardCommand.g:409:1: assignment_cmd returns [List<Statement> commands] : id= IDENTIFIER as= ASSIGN e= expression ;
    public final GuardCommandParser.assignment_cmd_return assignment_cmd() throws RecognitionException {
        GuardCommandParser.assignment_cmd_return retval = new GuardCommandParser.assignment_cmd_return();
        retval.start = input.LT(1);
        int assignment_cmd_StartIndex = input.index();
        CommonTree root_0 = null;

        Token id=null;
        Token as=null;
        GuardCommandParser.expression_return e = null;


        CommonTree id_tree=null;
        CommonTree as_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 31) ) { return retval; }
            // GuardCommand.g:410:2: (id= IDENTIFIER as= ASSIGN e= expression )
            // GuardCommand.g:410:4: id= IDENTIFIER as= ASSIGN e= expression
            {
            root_0 = (CommonTree)adaptor.nil();

            id=(Token)match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_assignment_cmd1553); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            id_tree = (CommonTree)adaptor.create(id);
            adaptor.addChild(root_0, id_tree);
            }
            as=(Token)match(input,ASSIGN,FOLLOW_ASSIGN_in_assignment_cmd1557); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            as_tree = (CommonTree)adaptor.create(as);
            adaptor.addChild(root_0, as_tree);
            }
            pushFollow(FOLLOW_expression_in_assignment_cmd1561);
            e=expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, e.getTree());
            if ( state.backtracking==0 ) {

              			if (retval.commands == null) {retval.commands = new ArrayList<Statement>();}
              			final Value val = e.val != null ? e.val : e.statList.get(e.statList.size()-1).getAssign();
              			final Statement assignStatement = statementFactory.createSimpleStatement(
              				StatementType.ASSIGN, new CodeLocation(as_tree.getLine()), annotations,
              				variableTable.getVariable(id.getText()),
              				val
              			);
              			annotations.clear();
              			if (e.val == null) {
              				retval.commands.addAll(e.statList);
              			}
              			retval.commands.add(assignStatement);
              		
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 31, assignment_cmd_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "assignment_cmd"

    public static class skip_cmd_return extends ParserRuleReturnScope {
        public Statement command;
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "skip_cmd"
    // GuardCommand.g:427:1: skip_cmd returns [Statement command] : s= SKIP ;
    public final GuardCommandParser.skip_cmd_return skip_cmd() throws RecognitionException {
        GuardCommandParser.skip_cmd_return retval = new GuardCommandParser.skip_cmd_return();
        retval.start = input.LT(1);
        int skip_cmd_StartIndex = input.index();
        CommonTree root_0 = null;

        Token s=null;

        CommonTree s_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 32) ) { return retval; }
            // GuardCommand.g:428:2: (s= SKIP )
            // GuardCommand.g:428:4: s= SKIP
            {
            root_0 = (CommonTree)adaptor.nil();

            s=(Token)match(input,SKIP,FOLLOW_SKIP_in_skip_cmd1582); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            s_tree = (CommonTree)adaptor.create(s);
            adaptor.addChild(root_0, s_tree);
            }
            if ( state.backtracking==0 ) {

              			retval.command = statementFactory.createSimpleStatement(
              				StatementType.SKIP, new CodeLocation(s_tree.getLine()), annotations
              			);
              			annotations.clear();
              		
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 32, skip_cmd_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "skip_cmd"

    public static class abort_cmd_return extends ParserRuleReturnScope {
        public Statement command;
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "abort_cmd"
    // GuardCommand.g:437:1: abort_cmd returns [Statement command] : a= ABORT ;
    public final GuardCommandParser.abort_cmd_return abort_cmd() throws RecognitionException {
        GuardCommandParser.abort_cmd_return retval = new GuardCommandParser.abort_cmd_return();
        retval.start = input.LT(1);
        int abort_cmd_StartIndex = input.index();
        CommonTree root_0 = null;

        Token a=null;

        CommonTree a_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 33) ) { return retval; }
            // GuardCommand.g:438:2: (a= ABORT )
            // GuardCommand.g:438:4: a= ABORT
            {
            root_0 = (CommonTree)adaptor.nil();

            a=(Token)match(input,ABORT,FOLLOW_ABORT_in_abort_cmd1603); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            a_tree = (CommonTree)adaptor.create(a);
            adaptor.addChild(root_0, a_tree);
            }
            if ( state.backtracking==0 ) {

              			retval.command = statementFactory.createSimpleStatement(
              				StatementType.ABORT, new CodeLocation(a_tree.getLine()), annotations
              			);
              			annotations.clear();
              		
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 33, abort_cmd_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "abort_cmd"

    public static class read_cmd_return extends ParserRuleReturnScope {
        public Statement command;
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "read_cmd"
    // GuardCommand.g:447:1: read_cmd returns [Statement command] : rea= READ id= IDENTIFIER ;
    public final GuardCommandParser.read_cmd_return read_cmd() throws RecognitionException {
        GuardCommandParser.read_cmd_return retval = new GuardCommandParser.read_cmd_return();
        retval.start = input.LT(1);
        int read_cmd_StartIndex = input.index();
        CommonTree root_0 = null;

        Token rea=null;
        Token id=null;

        CommonTree rea_tree=null;
        CommonTree id_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 34) ) { return retval; }
            // GuardCommand.g:448:2: (rea= READ id= IDENTIFIER )
            // GuardCommand.g:448:4: rea= READ id= IDENTIFIER
            {
            root_0 = (CommonTree)adaptor.nil();

            rea=(Token)match(input,READ,FOLLOW_READ_in_read_cmd1624); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            rea_tree = (CommonTree)adaptor.create(rea);
            adaptor.addChild(root_0, rea_tree);
            }
            id=(Token)match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_read_cmd1628); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            id_tree = (CommonTree)adaptor.create(id);
            adaptor.addChild(root_0, id_tree);
            }
            if ( state.backtracking==0 ) {

              			retval.command = statementFactory.createSimpleStatement(
              				StatementType.READ, new CodeLocation(rea_tree.getLine()), annotations,
              				variableTable.getVariable(id.getText())
              			);
              			annotations.clear();
              		
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 34, read_cmd_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "read_cmd"

    public static class write_cmd_return extends ParserRuleReturnScope {
        public List<Statement> commands;
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "write_cmd"
    // GuardCommand.g:458:1: write_cmd returns [List<Statement> commands] : wr= WRITE e= expression ;
    public final GuardCommandParser.write_cmd_return write_cmd() throws RecognitionException {
        GuardCommandParser.write_cmd_return retval = new GuardCommandParser.write_cmd_return();
        retval.start = input.LT(1);
        int write_cmd_StartIndex = input.index();
        CommonTree root_0 = null;

        Token wr=null;
        GuardCommandParser.expression_return e = null;


        CommonTree wr_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 35) ) { return retval; }
            // GuardCommand.g:459:2: (wr= WRITE e= expression )
            // GuardCommand.g:459:4: wr= WRITE e= expression
            {
            root_0 = (CommonTree)adaptor.nil();

            wr=(Token)match(input,WRITE,FOLLOW_WRITE_in_write_cmd1649); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            wr_tree = (CommonTree)adaptor.create(wr);
            adaptor.addChild(root_0, wr_tree);
            }
            pushFollow(FOLLOW_expression_in_write_cmd1653);
            e=expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, e.getTree());
            if ( state.backtracking==0 ) {

              			if (retval.commands == null) {retval.commands = new ArrayList<Statement>();}
              			final Value val = e.val != null ? e.val : e.statList.get(e.statList.size()-1).getAssign();
              			final Statement assignStatement = statementFactory.createSimpleStatement(
              				StatementType.WRITE, new CodeLocation(wr_tree.getLine()), annotations,
              				null, val
              			);
              			annotations.clear();
              			if (e.val == null) {
              				retval.commands.addAll(e.statList);
              			}
              			retval.commands.add(assignStatement);
              		
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 35, write_cmd_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "write_cmd"

    public static class if_cmd_return extends ParserRuleReturnScope {
        public Statement command;
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "if_cmd"
    // GuardCommand.g:475:1: if_cmd returns [Statement command] : ift= IF gc= guarded_cmd FI ;
    public final GuardCommandParser.if_cmd_return if_cmd() throws RecognitionException {
        GuardCommandParser.if_cmd_return retval = new GuardCommandParser.if_cmd_return();
        retval.start = input.LT(1);
        int if_cmd_StartIndex = input.index();
        CommonTree root_0 = null;

        Token ift=null;
        Token FI19=null;
        GuardCommandParser.guarded_cmd_return gc = null;


        CommonTree ift_tree=null;
        CommonTree FI19_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 36) ) { return retval; }
            // GuardCommand.g:476:2: (ift= IF gc= guarded_cmd FI )
            // GuardCommand.g:476:4: ift= IF gc= guarded_cmd FI
            {
            root_0 = (CommonTree)adaptor.nil();

            ift=(Token)match(input,IF,FOLLOW_IF_in_if_cmd1674); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            ift_tree = (CommonTree)adaptor.create(ift);
            adaptor.addChild(root_0, ift_tree);
            }
            pushFollow(FOLLOW_guarded_cmd_in_if_cmd1678);
            gc=guarded_cmd();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, gc.getTree());
            FI19=(Token)match(input,FI,FOLLOW_FI_in_if_cmd1680); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            FI19_tree = (CommonTree)adaptor.create(FI19);
            adaptor.addChild(root_0, FI19_tree);
            }
            if ( state.backtracking==0 ) {

              			retval.command = statementFactory.createCompoundStatement(
              				StatementType.IF, new CodeLocation(ift_tree.getLine()), annotations, gc.commands
              			);
              			annotations.clear();
              		
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 36, if_cmd_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "if_cmd"

    public static class do_cmd_return extends ParserRuleReturnScope {
        public Statement command;
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "do_cmd"
    // GuardCommand.g:485:1: do_cmd returns [Statement command] : dot= DO gc= guarded_cmd OD ;
    public final GuardCommandParser.do_cmd_return do_cmd() throws RecognitionException {
        GuardCommandParser.do_cmd_return retval = new GuardCommandParser.do_cmd_return();
        retval.start = input.LT(1);
        int do_cmd_StartIndex = input.index();
        CommonTree root_0 = null;

        Token dot=null;
        Token OD20=null;
        GuardCommandParser.guarded_cmd_return gc = null;


        CommonTree dot_tree=null;
        CommonTree OD20_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 37) ) { return retval; }
            // GuardCommand.g:486:2: (dot= DO gc= guarded_cmd OD )
            // GuardCommand.g:486:4: dot= DO gc= guarded_cmd OD
            {
            root_0 = (CommonTree)adaptor.nil();

            dot=(Token)match(input,DO,FOLLOW_DO_in_do_cmd1701); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            dot_tree = (CommonTree)adaptor.create(dot);
            adaptor.addChild(root_0, dot_tree);
            }
            pushFollow(FOLLOW_guarded_cmd_in_do_cmd1705);
            gc=guarded_cmd();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, gc.getTree());
            OD20=(Token)match(input,OD,FOLLOW_OD_in_do_cmd1707); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            OD20_tree = (CommonTree)adaptor.create(OD20);
            adaptor.addChild(root_0, OD20_tree);
            }
            if ( state.backtracking==0 ) {

              			retval.command = statementFactory.createCompoundStatement(
              				StatementType.DO, new CodeLocation(dot_tree.getLine()), annotations, gc.commands
              			);
              			annotations.clear();
              		
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 37, do_cmd_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "do_cmd"

    public static class guarded_cmd_return extends ParserRuleReturnScope {
        public List<Statement> commands;
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "guarded_cmd"
    // GuardCommand.g:495:1: guarded_cmd returns [List<Statement> commands] : (e= expression ARROW c= command ) ( GUARD gc= guarded_cmd )* ;
    public final GuardCommandParser.guarded_cmd_return guarded_cmd() throws RecognitionException {
        GuardCommandParser.guarded_cmd_return retval = new GuardCommandParser.guarded_cmd_return();
        retval.start = input.LT(1);
        int guarded_cmd_StartIndex = input.index();
        CommonTree root_0 = null;

        Token ARROW21=null;
        Token GUARD22=null;
        GuardCommandParser.expression_return e = null;

        GuardCommandParser.command_return c = null;

        GuardCommandParser.guarded_cmd_return gc = null;


        CommonTree ARROW21_tree=null;
        CommonTree GUARD22_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 38) ) { return retval; }
            // GuardCommand.g:496:2: ( (e= expression ARROW c= command ) ( GUARD gc= guarded_cmd )* )
            // GuardCommand.g:497:2: (e= expression ARROW c= command ) ( GUARD gc= guarded_cmd )*
            {
            root_0 = (CommonTree)adaptor.nil();

            if ( state.backtracking==0 ) {

              		if (retval.commands == null) {
              			retval.commands = new ArrayList<Statement>();
              		}
              	
            }
            // GuardCommand.g:502:2: (e= expression ARROW c= command )
            // GuardCommand.g:502:3: e= expression ARROW c= command
            {
            pushFollow(FOLLOW_expression_in_guarded_cmd1733);
            e=expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, e.getTree());
            ARROW21=(Token)match(input,ARROW,FOLLOW_ARROW_in_guarded_cmd1735); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            ARROW21_tree = (CommonTree)adaptor.create(ARROW21);
            adaptor.addChild(root_0, ARROW21_tree);
            }
            pushFollow(FOLLOW_command_in_guarded_cmd1739);
            c=command();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, c.getTree());

            }

            if ( state.backtracking==0 ) {

              		if (e.val != null) {
              			final Statement newestStat = statementFactory.createSimpleStatement(
              				StatementType.ASSIGN,
              				new CodeLocation(e.tree.getLine()),
              				annotations,
              				variableTable.createTemporaryVariable(e.val.getValueType()),
              				e.val
              			);
              			retval.commands.add(newestStat);
              		}
              		else {
              			retval.commands.add(statementFactory.createCompoundStatement(
              				StatementType.SCOPE, new CodeLocation(e.tree.getLine()), annotations, e.statList
              			));
              		}
              		annotations.clear();
              		retval.commands.add(statementFactory.createCompoundStatement(
              			StatementType.SCOPE, new CodeLocation(c.tree.getLine()), annotations, c.commands
              		));
              		annotations.clear();
              	
            }
            // GuardCommand.g:524:2: ( GUARD gc= guarded_cmd )*
            loop24:
            do {
                int alt24=2;
                int LA24_0 = input.LA(1);

                if ( (LA24_0==GUARD) ) {
                    int LA24_2 = input.LA(2);

                    if ( (synpred39_GuardCommand()) ) {
                        alt24=1;
                    }


                }


                switch (alt24) {
            	case 1 :
            	    // GuardCommand.g:524:3: GUARD gc= guarded_cmd
            	    {
            	    GUARD22=(Token)match(input,GUARD,FOLLOW_GUARD_in_guarded_cmd1746); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    GUARD22_tree = (CommonTree)adaptor.create(GUARD22);
            	    adaptor.addChild(root_0, GUARD22_tree);
            	    }
            	    pushFollow(FOLLOW_guarded_cmd_in_guarded_cmd1750);
            	    gc=guarded_cmd();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, gc.getTree());
            	    if ( state.backtracking==0 ) {
            	      retval.commands.addAll(gc.commands);
            	    }

            	    }
            	    break;

            	default :
            	    break loop24;
                }
            } while (true);


            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 38, guarded_cmd_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "guarded_cmd"

    public static class program_return extends ParserRuleReturnScope {
        public CompilationUnit compilationUnit;
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "program"
    // GuardCommand.g:527:1: program returns [CompilationUnit compilationUnit] : m= MODULE id= IDENTIFIER COLON c= command END ;
    public final GuardCommandParser.program_return program() throws RecognitionException {
        GuardCommandParser.program_return retval = new GuardCommandParser.program_return();
        retval.start = input.LT(1);
        int program_StartIndex = input.index();
        CommonTree root_0 = null;

        Token m=null;
        Token id=null;
        Token COLON23=null;
        Token END24=null;
        GuardCommandParser.command_return c = null;


        CommonTree m_tree=null;
        CommonTree id_tree=null;
        CommonTree COLON23_tree=null;
        CommonTree END24_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 39) ) { return retval; }
            // GuardCommand.g:528:2: (m= MODULE id= IDENTIFIER COLON c= command END )
            // GuardCommand.g:528:4: m= MODULE id= IDENTIFIER COLON c= command END
            {
            root_0 = (CommonTree)adaptor.nil();

            m=(Token)match(input,MODULE,FOLLOW_MODULE_in_program1773); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            m_tree = (CommonTree)adaptor.create(m);
            adaptor.addChild(root_0, m_tree);
            }
            id=(Token)match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_program1777); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            id_tree = (CommonTree)adaptor.create(id);
            adaptor.addChild(root_0, id_tree);
            }
            COLON23=(Token)match(input,COLON,FOLLOW_COLON_in_program1779); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            COLON23_tree = (CommonTree)adaptor.create(COLON23);
            adaptor.addChild(root_0, COLON23_tree);
            }
            pushFollow(FOLLOW_command_in_program1783);
            c=command();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, c.getTree());
            END24=(Token)match(input,END,FOLLOW_END_in_program1785); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            END24_tree = (CommonTree)adaptor.create(END24);
            adaptor.addChild(root_0, END24_tree);
            }
            if ( state.backtracking==0 ) {

              			final Statement command = statementFactory.createRootStatement(
              				new CodeLocation(m_tree.getLine()), annotations, c.commands
              			);
              			annotations.clear();
              			retval.compilationUnit = new CompilationUnit(
              				id.getText(), command, variableTable
              			);
              		
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 39, program_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "program"

    public static class annotation_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "annotation"
    // GuardCommand.g:541:1: annotation : ( '#@' ) id= IDENTIFIER '=' '\"' te= ( (~ ( '\"' ) )* ) '\"' ;
    public final GuardCommandParser.annotation_return annotation() throws RecognitionException {
        GuardCommandParser.annotation_return retval = new GuardCommandParser.annotation_return();
        retval.start = input.LT(1);
        int annotation_StartIndex = input.index();
        CommonTree root_0 = null;

        Token id=null;
        Token te=null;
        Token string_literal25=null;
        Token char_literal26=null;
        Token char_literal27=null;
        Token set28=null;
        Token char_literal29=null;

        CommonTree id_tree=null;
        CommonTree te_tree=null;
        CommonTree string_literal25_tree=null;
        CommonTree char_literal26_tree=null;
        CommonTree char_literal27_tree=null;
        CommonTree set28_tree=null;
        CommonTree char_literal29_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 40) ) { return retval; }
            // GuardCommand.g:542:2: ( ( '#@' ) id= IDENTIFIER '=' '\"' te= ( (~ ( '\"' ) )* ) '\"' )
            // GuardCommand.g:542:4: ( '#@' ) id= IDENTIFIER '=' '\"' te= ( (~ ( '\"' ) )* ) '\"'
            {
            root_0 = (CommonTree)adaptor.nil();

            // GuardCommand.g:542:4: ( '#@' )
            // GuardCommand.g:542:5: '#@'
            {
            string_literal25=(Token)match(input,44,FOLLOW_44_in_annotation1807); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            string_literal25_tree = (CommonTree)adaptor.create(string_literal25);
            adaptor.addChild(root_0, string_literal25_tree);
            }

            }

            id=(Token)match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_annotation1812); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            id_tree = (CommonTree)adaptor.create(id);
            adaptor.addChild(root_0, id_tree);
            }
            char_literal26=(Token)match(input,EQ,FOLLOW_EQ_in_annotation1814); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal26_tree = (CommonTree)adaptor.create(char_literal26);
            adaptor.addChild(root_0, char_literal26_tree);
            }
            char_literal27=(Token)match(input,45,FOLLOW_45_in_annotation1816); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal27_tree = (CommonTree)adaptor.create(char_literal27);
            adaptor.addChild(root_0, char_literal27_tree);
            }
            // GuardCommand.g:542:36: ( (~ ( '\"' ) )* )
            // GuardCommand.g:542:37: (~ ( '\"' ) )*
            {
            // GuardCommand.g:542:37: (~ ( '\"' ) )*
            loop25:
            do {
                int alt25=2;
                int LA25_0 = input.LA(1);

                if ( ((LA25_0>=AND && LA25_0<=44)) ) {
                    alt25=1;
                }


                switch (alt25) {
            	case 1 :
            	    // GuardCommand.g:0:0: ~ ( '\"' )
            	    {
            	    set28=(Token)input.LT(1);
            	    if ( (input.LA(1)>=AND && input.LA(1)<=44) ) {
            	        input.consume();
            	        if ( state.backtracking==0 ) adaptor.addChild(root_0, (CommonTree)adaptor.create(set28));
            	        state.errorRecovery=false;state.failed=false;
            	    }
            	    else {
            	        if (state.backtracking>0) {state.failed=true; return retval;}
            	        MismatchedSetException mse = new MismatchedSetException(null,input);
            	        throw mse;
            	    }


            	    }
            	    break;

            	default :
            	    break loop25;
                }
            } while (true);


            }

            char_literal29=(Token)match(input,45,FOLLOW_45_in_annotation1828); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal29_tree = (CommonTree)adaptor.create(char_literal29);
            adaptor.addChild(root_0, char_literal29_tree);
            }
            if ( state.backtracking==0 ) {

              			annotations.add(Annotation.newInstance(id.getText(), te.getText()));
              		
            }

            }

            retval.stop = input.LT(-1);

            if ( state.backtracking==0 ) {

            retval.tree = (CommonTree)adaptor.rulePostProcessing(root_0);
            adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
            }
        }
        catch (RecognitionException re) {
            reportError(re);
            recover(input,re);
    	retval.tree = (CommonTree)adaptor.errorNode(input, retval.start, input.LT(-1), re);

        }
        finally {
            if ( state.backtracking>0 ) { memoize(input, 40, annotation_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "annotation"

    // $ANTLR start synpred14_GuardCommand
    public final void synpred14_GuardCommand_fragment() throws RecognitionException {   
        GuardCommandParser.expr_and_return e1 = null;

        GuardCommandParser.or_return b = null;

        GuardCommandParser.expr_and_return e2 = null;

        GuardCommandParser.expr_and_return e3 = null;


        // GuardCommand.g:248:2: ( (e1= expr_and b= or e2= expr_and ) (b= or e3= expr_and )* )
        // GuardCommand.g:248:2: (e1= expr_and b= or e2= expr_and ) (b= or e3= expr_and )*
        {
        // GuardCommand.g:248:2: (e1= expr_and b= or e2= expr_and )
        // GuardCommand.g:248:3: e1= expr_and b= or e2= expr_and
        {
        pushFollow(FOLLOW_expr_and_in_synpred14_GuardCommand879);
        e1=expr_and();

        state._fsp--;
        if (state.failed) return ;
        pushFollow(FOLLOW_or_in_synpred14_GuardCommand883);
        b=or();

        state._fsp--;
        if (state.failed) return ;
        pushFollow(FOLLOW_expr_and_in_synpred14_GuardCommand887);
        e2=expr_and();

        state._fsp--;
        if (state.failed) return ;

        }

        // GuardCommand.g:253:2: (b= or e3= expr_and )*
        loop26:
        do {
            int alt26=2;
            int LA26_0 = input.LA(1);

            if ( (LA26_0==OR) ) {
                alt26=1;
            }


            switch (alt26) {
        	case 1 :
        	    // GuardCommand.g:253:3: b= or e3= expr_and
        	    {
        	    pushFollow(FOLLOW_or_in_synpred14_GuardCommand900);
        	    b=or();

        	    state._fsp--;
        	    if (state.failed) return ;
        	    pushFollow(FOLLOW_expr_and_in_synpred14_GuardCommand904);
        	    e3=expr_and();

        	    state._fsp--;
        	    if (state.failed) return ;

        	    }
        	    break;

        	default :
        	    break loop26;
            }
        } while (true);


        }
    }
    // $ANTLR end synpred14_GuardCommand

    // $ANTLR start synpred16_GuardCommand
    public final void synpred16_GuardCommand_fragment() throws RecognitionException {   
        GuardCommandParser.expr_eqa_return e1 = null;

        GuardCommandParser.and_return b = null;

        GuardCommandParser.expr_eqa_return e2 = null;

        GuardCommandParser.expr_eqa_return e3 = null;


        // GuardCommand.g:263:2: ( (e1= expr_eqa b= and e2= expr_eqa ) (b= and e3= expr_eqa )* )
        // GuardCommand.g:263:2: (e1= expr_eqa b= and e2= expr_eqa ) (b= and e3= expr_eqa )*
        {
        // GuardCommand.g:263:2: (e1= expr_eqa b= and e2= expr_eqa )
        // GuardCommand.g:263:3: e1= expr_eqa b= and e2= expr_eqa
        {
        pushFollow(FOLLOW_expr_eqa_in_synpred16_GuardCommand943);
        e1=expr_eqa();

        state._fsp--;
        if (state.failed) return ;
        pushFollow(FOLLOW_and_in_synpred16_GuardCommand947);
        b=and();

        state._fsp--;
        if (state.failed) return ;
        pushFollow(FOLLOW_expr_eqa_in_synpred16_GuardCommand951);
        e2=expr_eqa();

        state._fsp--;
        if (state.failed) return ;

        }

        // GuardCommand.g:268:2: (b= and e3= expr_eqa )*
        loop27:
        do {
            int alt27=2;
            int LA27_0 = input.LA(1);

            if ( (LA27_0==AND) ) {
                alt27=1;
            }


            switch (alt27) {
        	case 1 :
        	    // GuardCommand.g:268:3: b= and e3= expr_eqa
        	    {
        	    pushFollow(FOLLOW_and_in_synpred16_GuardCommand964);
        	    b=and();

        	    state._fsp--;
        	    if (state.failed) return ;
        	    pushFollow(FOLLOW_expr_eqa_in_synpred16_GuardCommand968);
        	    e3=expr_eqa();

        	    state._fsp--;
        	    if (state.failed) return ;

        	    }
        	    break;

        	default :
        	    break loop27;
            }
        } while (true);


        }
    }
    // $ANTLR end synpred16_GuardCommand

    // $ANTLR start synpred18_GuardCommand
    public final void synpred18_GuardCommand_fragment() throws RecognitionException {   
        GuardCommandParser.expr_rel_return e1 = null;

        GuardCommandParser.eqa_return b = null;

        GuardCommandParser.expr_rel_return e2 = null;

        GuardCommandParser.expr_rel_return e3 = null;


        // GuardCommand.g:279:2: ( (e1= expr_rel b= eqa e2= expr_rel ) (b= eqa e3= expr_rel )* )
        // GuardCommand.g:279:2: (e1= expr_rel b= eqa e2= expr_rel ) (b= eqa e3= expr_rel )*
        {
        // GuardCommand.g:279:2: (e1= expr_rel b= eqa e2= expr_rel )
        // GuardCommand.g:279:3: e1= expr_rel b= eqa e2= expr_rel
        {
        pushFollow(FOLLOW_expr_rel_in_synpred18_GuardCommand1008);
        e1=expr_rel();

        state._fsp--;
        if (state.failed) return ;
        pushFollow(FOLLOW_eqa_in_synpred18_GuardCommand1012);
        b=eqa();

        state._fsp--;
        if (state.failed) return ;
        pushFollow(FOLLOW_expr_rel_in_synpred18_GuardCommand1016);
        e2=expr_rel();

        state._fsp--;
        if (state.failed) return ;

        }

        // GuardCommand.g:284:2: (b= eqa e3= expr_rel )*
        loop28:
        do {
            int alt28=2;
            int LA28_0 = input.LA(1);

            if ( ((LA28_0>=EQ && LA28_0<=NEQ)) ) {
                alt28=1;
            }


            switch (alt28) {
        	case 1 :
        	    // GuardCommand.g:284:3: b= eqa e3= expr_rel
        	    {
        	    pushFollow(FOLLOW_eqa_in_synpred18_GuardCommand1029);
        	    b=eqa();

        	    state._fsp--;
        	    if (state.failed) return ;
        	    pushFollow(FOLLOW_expr_rel_in_synpred18_GuardCommand1033);
        	    e3=expr_rel();

        	    state._fsp--;
        	    if (state.failed) return ;

        	    }
        	    break;

        	default :
        	    break loop28;
            }
        } while (true);


        }
    }
    // $ANTLR end synpred18_GuardCommand

    // $ANTLR start synpred20_GuardCommand
    public final void synpred20_GuardCommand_fragment() throws RecognitionException {   
        GuardCommandParser.expr_term_return e1 = null;

        GuardCommandParser.rel_return b = null;

        GuardCommandParser.expr_term_return e2 = null;

        GuardCommandParser.expr_term_return e3 = null;


        // GuardCommand.g:294:2: ( (e1= expr_term b= rel e2= expr_term ) (b= rel e3= expr_term )* )
        // GuardCommand.g:294:2: (e1= expr_term b= rel e2= expr_term ) (b= rel e3= expr_term )*
        {
        // GuardCommand.g:294:2: (e1= expr_term b= rel e2= expr_term )
        // GuardCommand.g:294:3: e1= expr_term b= rel e2= expr_term
        {
        pushFollow(FOLLOW_expr_term_in_synpred20_GuardCommand1071);
        e1=expr_term();

        state._fsp--;
        if (state.failed) return ;
        pushFollow(FOLLOW_rel_in_synpred20_GuardCommand1075);
        b=rel();

        state._fsp--;
        if (state.failed) return ;
        pushFollow(FOLLOW_expr_term_in_synpred20_GuardCommand1079);
        e2=expr_term();

        state._fsp--;
        if (state.failed) return ;

        }

        // GuardCommand.g:299:2: (b= rel e3= expr_term )*
        loop29:
        do {
            int alt29=2;
            int LA29_0 = input.LA(1);

            if ( ((LA29_0>=GREATER_THAN && LA29_0<=LESS_EQ)) ) {
                alt29=1;
            }


            switch (alt29) {
        	case 1 :
        	    // GuardCommand.g:299:3: b= rel e3= expr_term
        	    {
        	    pushFollow(FOLLOW_rel_in_synpred20_GuardCommand1092);
        	    b=rel();

        	    state._fsp--;
        	    if (state.failed) return ;
        	    pushFollow(FOLLOW_expr_term_in_synpred20_GuardCommand1096);
        	    e3=expr_term();

        	    state._fsp--;
        	    if (state.failed) return ;

        	    }
        	    break;

        	default :
        	    break loop29;
            }
        } while (true);


        }
    }
    // $ANTLR end synpred20_GuardCommand

    // $ANTLR start synpred22_GuardCommand
    public final void synpred22_GuardCommand_fragment() throws RecognitionException {   
        GuardCommandParser.expr_factor_return e1 = null;

        GuardCommandParser.term_return b = null;

        GuardCommandParser.expr_factor_return e2 = null;

        GuardCommandParser.expr_factor_return e3 = null;


        // GuardCommand.g:309:2: ( (e1= expr_factor b= term e2= expr_factor ) (b= term e3= expr_factor )* )
        // GuardCommand.g:309:2: (e1= expr_factor b= term e2= expr_factor ) (b= term e3= expr_factor )*
        {
        // GuardCommand.g:309:2: (e1= expr_factor b= term e2= expr_factor )
        // GuardCommand.g:309:3: e1= expr_factor b= term e2= expr_factor
        {
        pushFollow(FOLLOW_expr_factor_in_synpred22_GuardCommand1135);
        e1=expr_factor();

        state._fsp--;
        if (state.failed) return ;
        pushFollow(FOLLOW_term_in_synpred22_GuardCommand1139);
        b=term();

        state._fsp--;
        if (state.failed) return ;
        pushFollow(FOLLOW_expr_factor_in_synpred22_GuardCommand1143);
        e2=expr_factor();

        state._fsp--;
        if (state.failed) return ;

        }

        // GuardCommand.g:314:2: (b= term e3= expr_factor )*
        loop30:
        do {
            int alt30=2;
            int LA30_0 = input.LA(1);

            if ( ((LA30_0>=PLUS && LA30_0<=MINUS)) ) {
                alt30=1;
            }


            switch (alt30) {
        	case 1 :
        	    // GuardCommand.g:314:3: b= term e3= expr_factor
        	    {
        	    pushFollow(FOLLOW_term_in_synpred22_GuardCommand1156);
        	    b=term();

        	    state._fsp--;
        	    if (state.failed) return ;
        	    pushFollow(FOLLOW_expr_factor_in_synpred22_GuardCommand1160);
        	    e3=expr_factor();

        	    state._fsp--;
        	    if (state.failed) return ;

        	    }
        	    break;

        	default :
        	    break loop30;
            }
        } while (true);


        }
    }
    // $ANTLR end synpred22_GuardCommand

    // $ANTLR start synpred24_GuardCommand
    public final void synpred24_GuardCommand_fragment() throws RecognitionException {   
        GuardCommandParser.expr_unary_return e1 = null;

        GuardCommandParser.factor_return b = null;

        GuardCommandParser.expr_unary_return e2 = null;

        GuardCommandParser.expr_unary_return e3 = null;


        // GuardCommand.g:324:2: ( (e1= expr_unary b= factor e2= expr_unary ) (b= factor e3= expr_unary )* )
        // GuardCommand.g:324:2: (e1= expr_unary b= factor e2= expr_unary ) (b= factor e3= expr_unary )*
        {
        // GuardCommand.g:324:2: (e1= expr_unary b= factor e2= expr_unary )
        // GuardCommand.g:324:3: e1= expr_unary b= factor e2= expr_unary
        {
        pushFollow(FOLLOW_expr_unary_in_synpred24_GuardCommand1199);
        e1=expr_unary();

        state._fsp--;
        if (state.failed) return ;
        pushFollow(FOLLOW_factor_in_synpred24_GuardCommand1203);
        b=factor();

        state._fsp--;
        if (state.failed) return ;
        pushFollow(FOLLOW_expr_unary_in_synpred24_GuardCommand1207);
        e2=expr_unary();

        state._fsp--;
        if (state.failed) return ;

        }

        // GuardCommand.g:329:2: (b= factor e3= expr_unary )*
        loop31:
        do {
            int alt31=2;
            int LA31_0 = input.LA(1);

            if ( ((LA31_0>=MUL && LA31_0<=DIV)) ) {
                alt31=1;
            }


            switch (alt31) {
        	case 1 :
        	    // GuardCommand.g:329:3: b= factor e3= expr_unary
        	    {
        	    pushFollow(FOLLOW_factor_in_synpred24_GuardCommand1220);
        	    b=factor();

        	    state._fsp--;
        	    if (state.failed) return ;
        	    pushFollow(FOLLOW_expr_unary_in_synpred24_GuardCommand1224);
        	    e3=expr_unary();

        	    state._fsp--;
        	    if (state.failed) return ;

        	    }
        	    break;

        	default :
        	    break loop31;
            }
        } while (true);


        }
    }
    // $ANTLR end synpred24_GuardCommand

    // $ANTLR start synpred38_GuardCommand
    public final void synpred38_GuardCommand_fragment() throws RecognitionException {   
        GuardCommandParser.command_return i = null;


        // GuardCommand.g:401:3: ( SEMI i= command )
        // GuardCommand.g:401:3: SEMI i= command
        {
        match(input,SEMI,FOLLOW_SEMI_in_synpred38_GuardCommand1524); if (state.failed) return ;
        pushFollow(FOLLOW_command_in_synpred38_GuardCommand1528);
        i=command();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred38_GuardCommand

    // $ANTLR start synpred39_GuardCommand
    public final void synpred39_GuardCommand_fragment() throws RecognitionException {   
        GuardCommandParser.guarded_cmd_return gc = null;


        // GuardCommand.g:524:3: ( GUARD gc= guarded_cmd )
        // GuardCommand.g:524:3: GUARD gc= guarded_cmd
        {
        match(input,GUARD,FOLLOW_GUARD_in_synpred39_GuardCommand1746); if (state.failed) return ;
        pushFollow(FOLLOW_guarded_cmd_in_synpred39_GuardCommand1750);
        gc=guarded_cmd();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred39_GuardCommand

    // Delegated rules

    public final boolean synpred22_GuardCommand() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred22_GuardCommand_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred14_GuardCommand() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred14_GuardCommand_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred39_GuardCommand() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred39_GuardCommand_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred16_GuardCommand() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred16_GuardCommand_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred24_GuardCommand() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred24_GuardCommand_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred20_GuardCommand() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred20_GuardCommand_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred38_GuardCommand() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred38_GuardCommand_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred18_GuardCommand() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred18_GuardCommand_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }


    protected DFA8 dfa8 = new DFA8(this);
    protected DFA10 dfa10 = new DFA10(this);
    protected DFA12 dfa12 = new DFA12(this);
    protected DFA14 dfa14 = new DFA14(this);
    protected DFA16 dfa16 = new DFA16(this);
    protected DFA18 dfa18 = new DFA18(this);
    static final String DFA8_eotS =
        "\12\uffff";
    static final String DFA8_eofS =
        "\12\uffff";
    static final String DFA8_minS =
        "\1\17\7\0\2\uffff";
    static final String DFA8_maxS =
        "\1\47\7\0\2\uffff";
    static final String DFA8_acceptS =
        "\10\uffff\1\1\1\2";
    static final String DFA8_specialS =
        "\1\uffff\1\0\1\1\1\2\1\3\1\4\1\5\1\6\2\uffff}>";
    static final String[] DFA8_transitionS = {
            "\1\2\2\uffff\1\1\1\uffff\1\7\17\uffff\1\4\1\5\1\3\1\6",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "",
            ""
    };

    static final short[] DFA8_eot = DFA.unpackEncodedString(DFA8_eotS);
    static final short[] DFA8_eof = DFA.unpackEncodedString(DFA8_eofS);
    static final char[] DFA8_min = DFA.unpackEncodedStringToUnsignedChars(DFA8_minS);
    static final char[] DFA8_max = DFA.unpackEncodedStringToUnsignedChars(DFA8_maxS);
    static final short[] DFA8_accept = DFA.unpackEncodedString(DFA8_acceptS);
    static final short[] DFA8_special = DFA.unpackEncodedString(DFA8_specialS);
    static final short[][] DFA8_transition;

    static {
        int numStates = DFA8_transitionS.length;
        DFA8_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA8_transition[i] = DFA.unpackEncodedString(DFA8_transitionS[i]);
        }
    }

    class DFA8 extends DFA {

        public DFA8(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 8;
            this.eot = DFA8_eot;
            this.eof = DFA8_eof;
            this.min = DFA8_min;
            this.max = DFA8_max;
            this.accept = DFA8_accept;
            this.special = DFA8_special;
            this.transition = DFA8_transition;
        }
        public String getDescription() {
            return "246:1: expr_or returns [List<Statement> statList, Value val] : ( (e1= expr_and b= or e2= expr_and ) (b= or e3= expr_and )* | (e4= expr_and ) );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA8_1 = input.LA(1);

                         
                        int index8_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred14_GuardCommand()) ) {s = 8;}

                        else if ( (true) ) {s = 9;}

                         
                        input.seek(index8_1);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA8_2 = input.LA(1);

                         
                        int index8_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred14_GuardCommand()) ) {s = 8;}

                        else if ( (true) ) {s = 9;}

                         
                        input.seek(index8_2);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA8_3 = input.LA(1);

                         
                        int index8_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred14_GuardCommand()) ) {s = 8;}

                        else if ( (true) ) {s = 9;}

                         
                        input.seek(index8_3);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA8_4 = input.LA(1);

                         
                        int index8_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred14_GuardCommand()) ) {s = 8;}

                        else if ( (true) ) {s = 9;}

                         
                        input.seek(index8_4);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA8_5 = input.LA(1);

                         
                        int index8_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred14_GuardCommand()) ) {s = 8;}

                        else if ( (true) ) {s = 9;}

                         
                        input.seek(index8_5);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA8_6 = input.LA(1);

                         
                        int index8_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred14_GuardCommand()) ) {s = 8;}

                        else if ( (true) ) {s = 9;}

                         
                        input.seek(index8_6);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA8_7 = input.LA(1);

                         
                        int index8_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred14_GuardCommand()) ) {s = 8;}

                        else if ( (true) ) {s = 9;}

                         
                        input.seek(index8_7);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 8, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA10_eotS =
        "\12\uffff";
    static final String DFA10_eofS =
        "\12\uffff";
    static final String DFA10_minS =
        "\1\17\7\0\2\uffff";
    static final String DFA10_maxS =
        "\1\47\7\0\2\uffff";
    static final String DFA10_acceptS =
        "\10\uffff\1\1\1\2";
    static final String DFA10_specialS =
        "\1\uffff\1\0\1\1\1\2\1\3\1\4\1\5\1\6\2\uffff}>";
    static final String[] DFA10_transitionS = {
            "\1\2\2\uffff\1\1\1\uffff\1\7\17\uffff\1\4\1\5\1\3\1\6",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "",
            ""
    };

    static final short[] DFA10_eot = DFA.unpackEncodedString(DFA10_eotS);
    static final short[] DFA10_eof = DFA.unpackEncodedString(DFA10_eofS);
    static final char[] DFA10_min = DFA.unpackEncodedStringToUnsignedChars(DFA10_minS);
    static final char[] DFA10_max = DFA.unpackEncodedStringToUnsignedChars(DFA10_maxS);
    static final short[] DFA10_accept = DFA.unpackEncodedString(DFA10_acceptS);
    static final short[] DFA10_special = DFA.unpackEncodedString(DFA10_specialS);
    static final short[][] DFA10_transition;

    static {
        int numStates = DFA10_transitionS.length;
        DFA10_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA10_transition[i] = DFA.unpackEncodedString(DFA10_transitionS[i]);
        }
    }

    class DFA10 extends DFA {

        public DFA10(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 10;
            this.eot = DFA10_eot;
            this.eof = DFA10_eof;
            this.min = DFA10_min;
            this.max = DFA10_max;
            this.accept = DFA10_accept;
            this.special = DFA10_special;
            this.transition = DFA10_transition;
        }
        public String getDescription() {
            return "261:1: expr_and returns [List<Statement> statList, Value val] : ( (e1= expr_eqa b= and e2= expr_eqa ) (b= and e3= expr_eqa )* | (e4= expr_eqa ) );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA10_1 = input.LA(1);

                         
                        int index10_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred16_GuardCommand()) ) {s = 8;}

                        else if ( (true) ) {s = 9;}

                         
                        input.seek(index10_1);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA10_2 = input.LA(1);

                         
                        int index10_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred16_GuardCommand()) ) {s = 8;}

                        else if ( (true) ) {s = 9;}

                         
                        input.seek(index10_2);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA10_3 = input.LA(1);

                         
                        int index10_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred16_GuardCommand()) ) {s = 8;}

                        else if ( (true) ) {s = 9;}

                         
                        input.seek(index10_3);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA10_4 = input.LA(1);

                         
                        int index10_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred16_GuardCommand()) ) {s = 8;}

                        else if ( (true) ) {s = 9;}

                         
                        input.seek(index10_4);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA10_5 = input.LA(1);

                         
                        int index10_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred16_GuardCommand()) ) {s = 8;}

                        else if ( (true) ) {s = 9;}

                         
                        input.seek(index10_5);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA10_6 = input.LA(1);

                         
                        int index10_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred16_GuardCommand()) ) {s = 8;}

                        else if ( (true) ) {s = 9;}

                         
                        input.seek(index10_6);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA10_7 = input.LA(1);

                         
                        int index10_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred16_GuardCommand()) ) {s = 8;}

                        else if ( (true) ) {s = 9;}

                         
                        input.seek(index10_7);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 10, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA12_eotS =
        "\12\uffff";
    static final String DFA12_eofS =
        "\12\uffff";
    static final String DFA12_minS =
        "\1\17\7\0\2\uffff";
    static final String DFA12_maxS =
        "\1\47\7\0\2\uffff";
    static final String DFA12_acceptS =
        "\10\uffff\1\1\1\2";
    static final String DFA12_specialS =
        "\1\uffff\1\0\1\1\1\2\1\3\1\4\1\5\1\6\2\uffff}>";
    static final String[] DFA12_transitionS = {
            "\1\2\2\uffff\1\1\1\uffff\1\7\17\uffff\1\4\1\5\1\3\1\6",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "",
            ""
    };

    static final short[] DFA12_eot = DFA.unpackEncodedString(DFA12_eotS);
    static final short[] DFA12_eof = DFA.unpackEncodedString(DFA12_eofS);
    static final char[] DFA12_min = DFA.unpackEncodedStringToUnsignedChars(DFA12_minS);
    static final char[] DFA12_max = DFA.unpackEncodedStringToUnsignedChars(DFA12_maxS);
    static final short[] DFA12_accept = DFA.unpackEncodedString(DFA12_acceptS);
    static final short[] DFA12_special = DFA.unpackEncodedString(DFA12_specialS);
    static final short[][] DFA12_transition;

    static {
        int numStates = DFA12_transitionS.length;
        DFA12_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA12_transition[i] = DFA.unpackEncodedString(DFA12_transitionS[i]);
        }
    }

    class DFA12 extends DFA {

        public DFA12(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 12;
            this.eot = DFA12_eot;
            this.eof = DFA12_eof;
            this.min = DFA12_min;
            this.max = DFA12_max;
            this.accept = DFA12_accept;
            this.special = DFA12_special;
            this.transition = DFA12_transition;
        }
        public String getDescription() {
            return "277:1: expr_eqa returns [List<Statement> statList, Value val] : ( (e1= expr_rel b= eqa e2= expr_rel ) (b= eqa e3= expr_rel )* | (e4= expr_rel ) );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA12_1 = input.LA(1);

                         
                        int index12_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred18_GuardCommand()) ) {s = 8;}

                        else if ( (true) ) {s = 9;}

                         
                        input.seek(index12_1);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA12_2 = input.LA(1);

                         
                        int index12_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred18_GuardCommand()) ) {s = 8;}

                        else if ( (true) ) {s = 9;}

                         
                        input.seek(index12_2);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA12_3 = input.LA(1);

                         
                        int index12_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred18_GuardCommand()) ) {s = 8;}

                        else if ( (true) ) {s = 9;}

                         
                        input.seek(index12_3);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA12_4 = input.LA(1);

                         
                        int index12_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred18_GuardCommand()) ) {s = 8;}

                        else if ( (true) ) {s = 9;}

                         
                        input.seek(index12_4);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA12_5 = input.LA(1);

                         
                        int index12_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred18_GuardCommand()) ) {s = 8;}

                        else if ( (true) ) {s = 9;}

                         
                        input.seek(index12_5);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA12_6 = input.LA(1);

                         
                        int index12_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred18_GuardCommand()) ) {s = 8;}

                        else if ( (true) ) {s = 9;}

                         
                        input.seek(index12_6);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA12_7 = input.LA(1);

                         
                        int index12_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred18_GuardCommand()) ) {s = 8;}

                        else if ( (true) ) {s = 9;}

                         
                        input.seek(index12_7);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 12, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA14_eotS =
        "\12\uffff";
    static final String DFA14_eofS =
        "\12\uffff";
    static final String DFA14_minS =
        "\1\17\7\0\2\uffff";
    static final String DFA14_maxS =
        "\1\47\7\0\2\uffff";
    static final String DFA14_acceptS =
        "\10\uffff\1\1\1\2";
    static final String DFA14_specialS =
        "\1\uffff\1\0\1\1\1\2\1\3\1\4\1\5\1\6\2\uffff}>";
    static final String[] DFA14_transitionS = {
            "\1\2\2\uffff\1\1\1\uffff\1\7\17\uffff\1\4\1\5\1\3\1\6",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "",
            ""
    };

    static final short[] DFA14_eot = DFA.unpackEncodedString(DFA14_eotS);
    static final short[] DFA14_eof = DFA.unpackEncodedString(DFA14_eofS);
    static final char[] DFA14_min = DFA.unpackEncodedStringToUnsignedChars(DFA14_minS);
    static final char[] DFA14_max = DFA.unpackEncodedStringToUnsignedChars(DFA14_maxS);
    static final short[] DFA14_accept = DFA.unpackEncodedString(DFA14_acceptS);
    static final short[] DFA14_special = DFA.unpackEncodedString(DFA14_specialS);
    static final short[][] DFA14_transition;

    static {
        int numStates = DFA14_transitionS.length;
        DFA14_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA14_transition[i] = DFA.unpackEncodedString(DFA14_transitionS[i]);
        }
    }

    class DFA14 extends DFA {

        public DFA14(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 14;
            this.eot = DFA14_eot;
            this.eof = DFA14_eof;
            this.min = DFA14_min;
            this.max = DFA14_max;
            this.accept = DFA14_accept;
            this.special = DFA14_special;
            this.transition = DFA14_transition;
        }
        public String getDescription() {
            return "292:1: expr_rel returns [List<Statement> statList, Value val] : ( (e1= expr_term b= rel e2= expr_term ) (b= rel e3= expr_term )* | (e4= expr_term ) );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA14_1 = input.LA(1);

                         
                        int index14_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred20_GuardCommand()) ) {s = 8;}

                        else if ( (true) ) {s = 9;}

                         
                        input.seek(index14_1);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA14_2 = input.LA(1);

                         
                        int index14_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred20_GuardCommand()) ) {s = 8;}

                        else if ( (true) ) {s = 9;}

                         
                        input.seek(index14_2);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA14_3 = input.LA(1);

                         
                        int index14_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred20_GuardCommand()) ) {s = 8;}

                        else if ( (true) ) {s = 9;}

                         
                        input.seek(index14_3);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA14_4 = input.LA(1);

                         
                        int index14_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred20_GuardCommand()) ) {s = 8;}

                        else if ( (true) ) {s = 9;}

                         
                        input.seek(index14_4);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA14_5 = input.LA(1);

                         
                        int index14_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred20_GuardCommand()) ) {s = 8;}

                        else if ( (true) ) {s = 9;}

                         
                        input.seek(index14_5);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA14_6 = input.LA(1);

                         
                        int index14_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred20_GuardCommand()) ) {s = 8;}

                        else if ( (true) ) {s = 9;}

                         
                        input.seek(index14_6);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA14_7 = input.LA(1);

                         
                        int index14_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred20_GuardCommand()) ) {s = 8;}

                        else if ( (true) ) {s = 9;}

                         
                        input.seek(index14_7);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 14, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA16_eotS =
        "\12\uffff";
    static final String DFA16_eofS =
        "\12\uffff";
    static final String DFA16_minS =
        "\1\17\7\0\2\uffff";
    static final String DFA16_maxS =
        "\1\47\7\0\2\uffff";
    static final String DFA16_acceptS =
        "\10\uffff\1\1\1\2";
    static final String DFA16_specialS =
        "\1\uffff\1\0\1\1\1\2\1\3\1\4\1\5\1\6\2\uffff}>";
    static final String[] DFA16_transitionS = {
            "\1\2\2\uffff\1\1\1\uffff\1\7\17\uffff\1\4\1\5\1\3\1\6",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "",
            ""
    };

    static final short[] DFA16_eot = DFA.unpackEncodedString(DFA16_eotS);
    static final short[] DFA16_eof = DFA.unpackEncodedString(DFA16_eofS);
    static final char[] DFA16_min = DFA.unpackEncodedStringToUnsignedChars(DFA16_minS);
    static final char[] DFA16_max = DFA.unpackEncodedStringToUnsignedChars(DFA16_maxS);
    static final short[] DFA16_accept = DFA.unpackEncodedString(DFA16_acceptS);
    static final short[] DFA16_special = DFA.unpackEncodedString(DFA16_specialS);
    static final short[][] DFA16_transition;

    static {
        int numStates = DFA16_transitionS.length;
        DFA16_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA16_transition[i] = DFA.unpackEncodedString(DFA16_transitionS[i]);
        }
    }

    class DFA16 extends DFA {

        public DFA16(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 16;
            this.eot = DFA16_eot;
            this.eof = DFA16_eof;
            this.min = DFA16_min;
            this.max = DFA16_max;
            this.accept = DFA16_accept;
            this.special = DFA16_special;
            this.transition = DFA16_transition;
        }
        public String getDescription() {
            return "307:1: expr_term returns [List<Statement> statList, Value val] : ( (e1= expr_factor b= term e2= expr_factor ) (b= term e3= expr_factor )* | (e4= expr_factor ) );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA16_1 = input.LA(1);

                         
                        int index16_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred22_GuardCommand()) ) {s = 8;}

                        else if ( (true) ) {s = 9;}

                         
                        input.seek(index16_1);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA16_2 = input.LA(1);

                         
                        int index16_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred22_GuardCommand()) ) {s = 8;}

                        else if ( (true) ) {s = 9;}

                         
                        input.seek(index16_2);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA16_3 = input.LA(1);

                         
                        int index16_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred22_GuardCommand()) ) {s = 8;}

                        else if ( (true) ) {s = 9;}

                         
                        input.seek(index16_3);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA16_4 = input.LA(1);

                         
                        int index16_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred22_GuardCommand()) ) {s = 8;}

                        else if ( (true) ) {s = 9;}

                         
                        input.seek(index16_4);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA16_5 = input.LA(1);

                         
                        int index16_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred22_GuardCommand()) ) {s = 8;}

                        else if ( (true) ) {s = 9;}

                         
                        input.seek(index16_5);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA16_6 = input.LA(1);

                         
                        int index16_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred22_GuardCommand()) ) {s = 8;}

                        else if ( (true) ) {s = 9;}

                         
                        input.seek(index16_6);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA16_7 = input.LA(1);

                         
                        int index16_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred22_GuardCommand()) ) {s = 8;}

                        else if ( (true) ) {s = 9;}

                         
                        input.seek(index16_7);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 16, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA18_eotS =
        "\12\uffff";
    static final String DFA18_eofS =
        "\12\uffff";
    static final String DFA18_minS =
        "\1\17\7\0\2\uffff";
    static final String DFA18_maxS =
        "\1\47\7\0\2\uffff";
    static final String DFA18_acceptS =
        "\10\uffff\1\1\1\2";
    static final String DFA18_specialS =
        "\1\uffff\1\0\1\1\1\2\1\3\1\4\1\5\1\6\2\uffff}>";
    static final String[] DFA18_transitionS = {
            "\1\2\2\uffff\1\1\1\uffff\1\7\17\uffff\1\4\1\5\1\3\1\6",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "",
            ""
    };

    static final short[] DFA18_eot = DFA.unpackEncodedString(DFA18_eotS);
    static final short[] DFA18_eof = DFA.unpackEncodedString(DFA18_eofS);
    static final char[] DFA18_min = DFA.unpackEncodedStringToUnsignedChars(DFA18_minS);
    static final char[] DFA18_max = DFA.unpackEncodedStringToUnsignedChars(DFA18_maxS);
    static final short[] DFA18_accept = DFA.unpackEncodedString(DFA18_acceptS);
    static final short[] DFA18_special = DFA.unpackEncodedString(DFA18_specialS);
    static final short[][] DFA18_transition;

    static {
        int numStates = DFA18_transitionS.length;
        DFA18_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA18_transition[i] = DFA.unpackEncodedString(DFA18_transitionS[i]);
        }
    }

    class DFA18 extends DFA {

        public DFA18(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 18;
            this.eot = DFA18_eot;
            this.eof = DFA18_eof;
            this.min = DFA18_min;
            this.max = DFA18_max;
            this.accept = DFA18_accept;
            this.special = DFA18_special;
            this.transition = DFA18_transition;
        }
        public String getDescription() {
            return "322:1: expr_factor returns [List<Statement> statList, Value val] : ( (e1= expr_unary b= factor e2= expr_unary ) (b= factor e3= expr_unary )* | (e4= expr_unary ) );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA18_1 = input.LA(1);

                         
                        int index18_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred24_GuardCommand()) ) {s = 8;}

                        else if ( (true) ) {s = 9;}

                         
                        input.seek(index18_1);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA18_2 = input.LA(1);

                         
                        int index18_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred24_GuardCommand()) ) {s = 8;}

                        else if ( (true) ) {s = 9;}

                         
                        input.seek(index18_2);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA18_3 = input.LA(1);

                         
                        int index18_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred24_GuardCommand()) ) {s = 8;}

                        else if ( (true) ) {s = 9;}

                         
                        input.seek(index18_3);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA18_4 = input.LA(1);

                         
                        int index18_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred24_GuardCommand()) ) {s = 8;}

                        else if ( (true) ) {s = 9;}

                         
                        input.seek(index18_4);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA18_5 = input.LA(1);

                         
                        int index18_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred24_GuardCommand()) ) {s = 8;}

                        else if ( (true) ) {s = 9;}

                         
                        input.seek(index18_5);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA18_6 = input.LA(1);

                         
                        int index18_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred24_GuardCommand()) ) {s = 8;}

                        else if ( (true) ) {s = 9;}

                         
                        input.seek(index18_6);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA18_7 = input.LA(1);

                         
                        int index18_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred24_GuardCommand()) ) {s = 8;}

                        else if ( (true) ) {s = 9;}

                         
                        input.seek(index18_7);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 18, _s, input);
            error(nvae);
            throw nvae;
        }
    }
 

    public static final BitSet FOLLOW_NOT_in_unary_operator457 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MINUS_in_unary_operator464 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_OR_in_or481 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_AND_in_and495 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_EQ_in_eq509 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NEQ_in_neq522 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_eq_in_eqa538 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_neq_in_eqa549 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_GREATER_THAN_in_gt566 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_GREATER_EQ_in_gt_eq579 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LESS_THAN_in_lt592 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LESS_EQ_in_lt_eq605 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_gt_in_rel621 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_gt_eq_in_rel632 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_lt_in_rel643 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_lt_eq_in_rel654 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PLUS_in_plus671 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MINUS_in_minus684 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_plus_in_term700 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_minus_in_term711 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MUL_in_mul728 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DIV_in_div741 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_mul_in_factor757 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_div_in_factor768 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_or_in_binary_operator789 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_and_in_binary_operator798 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_eqa_in_binary_operator807 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_rel_in_binary_operator816 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_term_in_binary_operator825 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_factor_in_binary_operator834 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expr_or_in_expression858 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expr_and_in_expr_or879 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_or_in_expr_or883 = new BitSet(new long[]{0x000000F000148000L});
    public static final BitSet FOLLOW_expr_and_in_expr_or887 = new BitSet(new long[]{0x0000000000000022L});
    public static final BitSet FOLLOW_or_in_expr_or900 = new BitSet(new long[]{0x000000F000148000L});
    public static final BitSet FOLLOW_expr_and_in_expr_or904 = new BitSet(new long[]{0x0000000000000022L});
    public static final BitSet FOLLOW_expr_and_in_expr_or920 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expr_eqa_in_expr_and943 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_and_in_expr_and947 = new BitSet(new long[]{0x000000F000148000L});
    public static final BitSet FOLLOW_expr_eqa_in_expr_and951 = new BitSet(new long[]{0x0000000000000012L});
    public static final BitSet FOLLOW_and_in_expr_and964 = new BitSet(new long[]{0x000000F000148000L});
    public static final BitSet FOLLOW_expr_eqa_in_expr_and968 = new BitSet(new long[]{0x0000000000000012L});
    public static final BitSet FOLLOW_expr_eqa_in_expr_and984 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expr_rel_in_expr_eqa1008 = new BitSet(new long[]{0x0000000000003000L});
    public static final BitSet FOLLOW_eqa_in_expr_eqa1012 = new BitSet(new long[]{0x000000F000148000L});
    public static final BitSet FOLLOW_expr_rel_in_expr_eqa1016 = new BitSet(new long[]{0x0000000000003002L});
    public static final BitSet FOLLOW_eqa_in_expr_eqa1029 = new BitSet(new long[]{0x000000F000148000L});
    public static final BitSet FOLLOW_expr_rel_in_expr_eqa1033 = new BitSet(new long[]{0x0000000000003002L});
    public static final BitSet FOLLOW_expr_rel_in_expr_eqa1049 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expr_term_in_expr_rel1071 = new BitSet(new long[]{0x0000000000000F00L});
    public static final BitSet FOLLOW_rel_in_expr_rel1075 = new BitSet(new long[]{0x000000F000148000L});
    public static final BitSet FOLLOW_expr_term_in_expr_rel1079 = new BitSet(new long[]{0x0000000000000F02L});
    public static final BitSet FOLLOW_rel_in_expr_rel1092 = new BitSet(new long[]{0x000000F000148000L});
    public static final BitSet FOLLOW_expr_term_in_expr_rel1096 = new BitSet(new long[]{0x0000000000000F02L});
    public static final BitSet FOLLOW_expr_term_in_expr_rel1112 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expr_factor_in_expr_term1135 = new BitSet(new long[]{0x000000000000C000L});
    public static final BitSet FOLLOW_term_in_expr_term1139 = new BitSet(new long[]{0x000000F000148000L});
    public static final BitSet FOLLOW_expr_factor_in_expr_term1143 = new BitSet(new long[]{0x000000000000C002L});
    public static final BitSet FOLLOW_term_in_expr_term1156 = new BitSet(new long[]{0x000000F000148000L});
    public static final BitSet FOLLOW_expr_factor_in_expr_term1160 = new BitSet(new long[]{0x000000000000C002L});
    public static final BitSet FOLLOW_expr_factor_in_expr_term1176 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expr_unary_in_expr_factor1199 = new BitSet(new long[]{0x0000000000030000L});
    public static final BitSet FOLLOW_factor_in_expr_factor1203 = new BitSet(new long[]{0x000000F000148000L});
    public static final BitSet FOLLOW_expr_unary_in_expr_factor1207 = new BitSet(new long[]{0x0000000000030002L});
    public static final BitSet FOLLOW_factor_in_expr_factor1220 = new BitSet(new long[]{0x000000F000148000L});
    public static final BitSet FOLLOW_expr_unary_in_expr_factor1224 = new BitSet(new long[]{0x0000000000030002L});
    public static final BitSet FOLLOW_expr_unary_in_expr_factor1240 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_unary_operator_in_expr_unary1260 = new BitSet(new long[]{0x000000F000148000L});
    public static final BitSet FOLLOW_expr_unary_in_expr_unary1264 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_lite_in_expr_unary1275 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_lite_paren_in_expr_unary1284 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_lite_in_literal1303 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_lite_paren_in_literal1312 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INTEGER_LITERAL_in_lite1332 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TRUE_in_lite1343 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FALSE_in_lite1352 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENTIFIER_in_lite1361 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LPAREN_in_lite_paren1378 = new BitSet(new long[]{0x000000F000148000L});
    public static final BitSet FOLLOW_expression_in_lite_paren1382 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_RPAREN_in_lite_paren1384 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_assignment_cmd_in_command1418 = new BitSet(new long[]{0x0000000000000082L});
    public static final BitSet FOLLOW_skip_cmd_in_command1429 = new BitSet(new long[]{0x0000000000000082L});
    public static final BitSet FOLLOW_abort_cmd_in_command1441 = new BitSet(new long[]{0x0000000000000082L});
    public static final BitSet FOLLOW_read_cmd_in_command1453 = new BitSet(new long[]{0x0000000000000082L});
    public static final BitSet FOLLOW_write_cmd_in_command1465 = new BitSet(new long[]{0x0000000000000082L});
    public static final BitSet FOLLOW_LCURLY_in_command1477 = new BitSet(new long[]{0x00000083C5400000L});
    public static final BitSet FOLLOW_command_in_command1481 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_RCURLY_in_command1483 = new BitSet(new long[]{0x0000000000000082L});
    public static final BitSet FOLLOW_if_cmd_in_command1494 = new BitSet(new long[]{0x0000000000000082L});
    public static final BitSet FOLLOW_do_cmd_in_command1508 = new BitSet(new long[]{0x0000000000000082L});
    public static final BitSet FOLLOW_SEMI_in_command1524 = new BitSet(new long[]{0x00000083C5400000L});
    public static final BitSet FOLLOW_command_in_command1528 = new BitSet(new long[]{0x0000000000000082L});
    public static final BitSet FOLLOW_IDENTIFIER_in_assignment_cmd1553 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_ASSIGN_in_assignment_cmd1557 = new BitSet(new long[]{0x000000F000148000L});
    public static final BitSet FOLLOW_expression_in_assignment_cmd1561 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_SKIP_in_skip_cmd1582 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ABORT_in_abort_cmd1603 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_READ_in_read_cmd1624 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_IDENTIFIER_in_read_cmd1628 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_WRITE_in_write_cmd1649 = new BitSet(new long[]{0x000000F000148000L});
    public static final BitSet FOLLOW_expression_in_write_cmd1653 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IF_in_if_cmd1674 = new BitSet(new long[]{0x000000F000148000L});
    public static final BitSet FOLLOW_guarded_cmd_in_if_cmd1678 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_FI_in_if_cmd1680 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DO_in_do_cmd1701 = new BitSet(new long[]{0x000000F000148000L});
    public static final BitSet FOLLOW_guarded_cmd_in_do_cmd1705 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_OD_in_do_cmd1707 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_guarded_cmd1733 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_ARROW_in_guarded_cmd1735 = new BitSet(new long[]{0x00000083C5400000L});
    public static final BitSet FOLLOW_command_in_guarded_cmd1739 = new BitSet(new long[]{0x0000000010000002L});
    public static final BitSet FOLLOW_GUARD_in_guarded_cmd1746 = new BitSet(new long[]{0x000000F000148000L});
    public static final BitSet FOLLOW_guarded_cmd_in_guarded_cmd1750 = new BitSet(new long[]{0x0000000010000002L});
    public static final BitSet FOLLOW_MODULE_in_program1773 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_IDENTIFIER_in_program1777 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_COLON_in_program1779 = new BitSet(new long[]{0x00000083C5400000L});
    public static final BitSet FOLLOW_command_in_program1783 = new BitSet(new long[]{0x0000000800000000L});
    public static final BitSet FOLLOW_END_in_program1785 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_44_in_annotation1807 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_IDENTIFIER_in_annotation1812 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_EQ_in_annotation1814 = new BitSet(new long[]{0x0000200000000000L});
    public static final BitSet FOLLOW_45_in_annotation1816 = new BitSet(new long[]{0x00003FFFFFFFFFF0L});
    public static final BitSet FOLLOW_set_in_annotation1821 = new BitSet(new long[]{0x00003FFFFFFFFFF0L});
    public static final BitSet FOLLOW_45_in_annotation1828 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expr_and_in_synpred14_GuardCommand879 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_or_in_synpred14_GuardCommand883 = new BitSet(new long[]{0x000000F000148000L});
    public static final BitSet FOLLOW_expr_and_in_synpred14_GuardCommand887 = new BitSet(new long[]{0x0000000000000022L});
    public static final BitSet FOLLOW_or_in_synpred14_GuardCommand900 = new BitSet(new long[]{0x000000F000148000L});
    public static final BitSet FOLLOW_expr_and_in_synpred14_GuardCommand904 = new BitSet(new long[]{0x0000000000000022L});
    public static final BitSet FOLLOW_expr_eqa_in_synpred16_GuardCommand943 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_and_in_synpred16_GuardCommand947 = new BitSet(new long[]{0x000000F000148000L});
    public static final BitSet FOLLOW_expr_eqa_in_synpred16_GuardCommand951 = new BitSet(new long[]{0x0000000000000012L});
    public static final BitSet FOLLOW_and_in_synpred16_GuardCommand964 = new BitSet(new long[]{0x000000F000148000L});
    public static final BitSet FOLLOW_expr_eqa_in_synpred16_GuardCommand968 = new BitSet(new long[]{0x0000000000000012L});
    public static final BitSet FOLLOW_expr_rel_in_synpred18_GuardCommand1008 = new BitSet(new long[]{0x0000000000003000L});
    public static final BitSet FOLLOW_eqa_in_synpred18_GuardCommand1012 = new BitSet(new long[]{0x000000F000148000L});
    public static final BitSet FOLLOW_expr_rel_in_synpred18_GuardCommand1016 = new BitSet(new long[]{0x0000000000003002L});
    public static final BitSet FOLLOW_eqa_in_synpred18_GuardCommand1029 = new BitSet(new long[]{0x000000F000148000L});
    public static final BitSet FOLLOW_expr_rel_in_synpred18_GuardCommand1033 = new BitSet(new long[]{0x0000000000003002L});
    public static final BitSet FOLLOW_expr_term_in_synpred20_GuardCommand1071 = new BitSet(new long[]{0x0000000000000F00L});
    public static final BitSet FOLLOW_rel_in_synpred20_GuardCommand1075 = new BitSet(new long[]{0x000000F000148000L});
    public static final BitSet FOLLOW_expr_term_in_synpred20_GuardCommand1079 = new BitSet(new long[]{0x0000000000000F02L});
    public static final BitSet FOLLOW_rel_in_synpred20_GuardCommand1092 = new BitSet(new long[]{0x000000F000148000L});
    public static final BitSet FOLLOW_expr_term_in_synpred20_GuardCommand1096 = new BitSet(new long[]{0x0000000000000F02L});
    public static final BitSet FOLLOW_expr_factor_in_synpred22_GuardCommand1135 = new BitSet(new long[]{0x000000000000C000L});
    public static final BitSet FOLLOW_term_in_synpred22_GuardCommand1139 = new BitSet(new long[]{0x000000F000148000L});
    public static final BitSet FOLLOW_expr_factor_in_synpred22_GuardCommand1143 = new BitSet(new long[]{0x000000000000C002L});
    public static final BitSet FOLLOW_term_in_synpred22_GuardCommand1156 = new BitSet(new long[]{0x000000F000148000L});
    public static final BitSet FOLLOW_expr_factor_in_synpred22_GuardCommand1160 = new BitSet(new long[]{0x000000000000C002L});
    public static final BitSet FOLLOW_expr_unary_in_synpred24_GuardCommand1199 = new BitSet(new long[]{0x0000000000030000L});
    public static final BitSet FOLLOW_factor_in_synpred24_GuardCommand1203 = new BitSet(new long[]{0x000000F000148000L});
    public static final BitSet FOLLOW_expr_unary_in_synpred24_GuardCommand1207 = new BitSet(new long[]{0x0000000000030002L});
    public static final BitSet FOLLOW_factor_in_synpred24_GuardCommand1220 = new BitSet(new long[]{0x000000F000148000L});
    public static final BitSet FOLLOW_expr_unary_in_synpred24_GuardCommand1224 = new BitSet(new long[]{0x0000000000030002L});
    public static final BitSet FOLLOW_SEMI_in_synpred38_GuardCommand1524 = new BitSet(new long[]{0x00000083C5400000L});
    public static final BitSet FOLLOW_command_in_synpred38_GuardCommand1528 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_GUARD_in_synpred39_GuardCommand1746 = new BitSet(new long[]{0x000000F000148000L});
    public static final BitSet FOLLOW_guarded_cmd_in_synpred39_GuardCommand1750 = new BitSet(new long[]{0x0000000000000002L});

}