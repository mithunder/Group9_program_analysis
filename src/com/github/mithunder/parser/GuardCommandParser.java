// $ANTLR 3.2 debian-4 GuardCommand.g 2010-10-16 20:15:20

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
            this.state.ruleMemo = new HashMap[75+1];
             
             
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


    public static class unary_operator_return extends ParserRuleReturnScope {
        public int type;
        public int vtype;
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "unary_operator"
    // GuardCommand.g:147:1: unary_operator returns [int type, int vtype] : ( NOT | MINUS );
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
            // GuardCommand.g:148:2: ( NOT | MINUS )
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
                    // GuardCommand.g:148:4: NOT
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
                    // GuardCommand.g:149:4: MINUS
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
    // GuardCommand.g:152:1: or returns [int type, int vtype] : OR ;
    public final GuardCommandParser.or_return or() throws RecognitionException {
        GuardCommandParser.or_return retval = new GuardCommandParser.or_return();
        retval.start = input.LT(1);
        int or_StartIndex = input.index();
        CommonTree root_0 = null;

        Token OR3=null;

        CommonTree OR3_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 2) ) { return retval; }
            // GuardCommand.g:153:2: ( OR )
            // GuardCommand.g:153:4: OR
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
    // GuardCommand.g:155:1: and returns [int type, int vtype] : AND ;
    public final GuardCommandParser.and_return and() throws RecognitionException {
        GuardCommandParser.and_return retval = new GuardCommandParser.and_return();
        retval.start = input.LT(1);
        int and_StartIndex = input.index();
        CommonTree root_0 = null;

        Token AND4=null;

        CommonTree AND4_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 3) ) { return retval; }
            // GuardCommand.g:156:2: ( AND )
            // GuardCommand.g:156:4: AND
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
    // GuardCommand.g:158:1: eq returns [int type, int vtype] : EQ ;
    public final GuardCommandParser.eq_return eq() throws RecognitionException {
        GuardCommandParser.eq_return retval = new GuardCommandParser.eq_return();
        retval.start = input.LT(1);
        int eq_StartIndex = input.index();
        CommonTree root_0 = null;

        Token EQ5=null;

        CommonTree EQ5_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 4) ) { return retval; }
            // GuardCommand.g:159:2: ( EQ )
            // GuardCommand.g:159:4: EQ
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
    // GuardCommand.g:160:1: neq returns [int type, int vtype] : NEQ ;
    public final GuardCommandParser.neq_return neq() throws RecognitionException {
        GuardCommandParser.neq_return retval = new GuardCommandParser.neq_return();
        retval.start = input.LT(1);
        int neq_StartIndex = input.index();
        CommonTree root_0 = null;

        Token NEQ6=null;

        CommonTree NEQ6_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 5) ) { return retval; }
            // GuardCommand.g:161:2: ( NEQ )
            // GuardCommand.g:161:4: NEQ
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
    // GuardCommand.g:162:1: eqa returns [int type, int vtype] : ( (a= eq ) | (b= neq ) );
    public final GuardCommandParser.eqa_return eqa() throws RecognitionException {
        GuardCommandParser.eqa_return retval = new GuardCommandParser.eqa_return();
        retval.start = input.LT(1);
        int eqa_StartIndex = input.index();
        CommonTree root_0 = null;

        GuardCommandParser.eq_return a = null;

        GuardCommandParser.neq_return b = null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 6) ) { return retval; }
            // GuardCommand.g:163:2: ( (a= eq ) | (b= neq ) )
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
                    // GuardCommand.g:163:4: (a= eq )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    // GuardCommand.g:163:4: (a= eq )
                    // GuardCommand.g:163:5: a= eq
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
                    // GuardCommand.g:164:4: (b= neq )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    // GuardCommand.g:164:4: (b= neq )
                    // GuardCommand.g:164:5: b= neq
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
    // GuardCommand.g:167:1: gt returns [int type, int vtype] : GREATER_THAN ;
    public final GuardCommandParser.gt_return gt() throws RecognitionException {
        GuardCommandParser.gt_return retval = new GuardCommandParser.gt_return();
        retval.start = input.LT(1);
        int gt_StartIndex = input.index();
        CommonTree root_0 = null;

        Token GREATER_THAN7=null;

        CommonTree GREATER_THAN7_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 7) ) { return retval; }
            // GuardCommand.g:168:2: ( GREATER_THAN )
            // GuardCommand.g:168:4: GREATER_THAN
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
    // GuardCommand.g:169:1: gt_eq returns [int type, int vtype] : GREATER_EQ ;
    public final GuardCommandParser.gt_eq_return gt_eq() throws RecognitionException {
        GuardCommandParser.gt_eq_return retval = new GuardCommandParser.gt_eq_return();
        retval.start = input.LT(1);
        int gt_eq_StartIndex = input.index();
        CommonTree root_0 = null;

        Token GREATER_EQ8=null;

        CommonTree GREATER_EQ8_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 8) ) { return retval; }
            // GuardCommand.g:170:2: ( GREATER_EQ )
            // GuardCommand.g:170:4: GREATER_EQ
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
    // GuardCommand.g:171:1: lt returns [int type, int vtype] : LESS_THAN ;
    public final GuardCommandParser.lt_return lt() throws RecognitionException {
        GuardCommandParser.lt_return retval = new GuardCommandParser.lt_return();
        retval.start = input.LT(1);
        int lt_StartIndex = input.index();
        CommonTree root_0 = null;

        Token LESS_THAN9=null;

        CommonTree LESS_THAN9_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 9) ) { return retval; }
            // GuardCommand.g:172:2: ( LESS_THAN )
            // GuardCommand.g:172:4: LESS_THAN
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
    // GuardCommand.g:173:1: lt_eq returns [int type, int vtype] : LESS_EQ ;
    public final GuardCommandParser.lt_eq_return lt_eq() throws RecognitionException {
        GuardCommandParser.lt_eq_return retval = new GuardCommandParser.lt_eq_return();
        retval.start = input.LT(1);
        int lt_eq_StartIndex = input.index();
        CommonTree root_0 = null;

        Token LESS_EQ10=null;

        CommonTree LESS_EQ10_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 10) ) { return retval; }
            // GuardCommand.g:174:2: ( LESS_EQ )
            // GuardCommand.g:174:4: LESS_EQ
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
    // GuardCommand.g:175:1: rel returns [int type, int vtype] : ( (a= gt ) | (b= gt_eq ) | (c= lt ) | (d= lt_eq ) );
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
            // GuardCommand.g:176:2: ( (a= gt ) | (b= gt_eq ) | (c= lt ) | (d= lt_eq ) )
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
                    // GuardCommand.g:176:4: (a= gt )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    // GuardCommand.g:176:4: (a= gt )
                    // GuardCommand.g:176:5: a= gt
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
                    // GuardCommand.g:177:4: (b= gt_eq )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    // GuardCommand.g:177:4: (b= gt_eq )
                    // GuardCommand.g:177:5: b= gt_eq
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
                    // GuardCommand.g:178:4: (c= lt )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    // GuardCommand.g:178:4: (c= lt )
                    // GuardCommand.g:178:5: c= lt
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
                    // GuardCommand.g:179:4: (d= lt_eq )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    // GuardCommand.g:179:4: (d= lt_eq )
                    // GuardCommand.g:179:5: d= lt_eq
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
    // GuardCommand.g:182:1: plus returns [int type, int vtype] : PLUS ;
    public final GuardCommandParser.plus_return plus() throws RecognitionException {
        GuardCommandParser.plus_return retval = new GuardCommandParser.plus_return();
        retval.start = input.LT(1);
        int plus_StartIndex = input.index();
        CommonTree root_0 = null;

        Token PLUS11=null;

        CommonTree PLUS11_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 12) ) { return retval; }
            // GuardCommand.g:183:2: ( PLUS )
            // GuardCommand.g:183:4: PLUS
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
    // GuardCommand.g:184:1: minus returns [int type, int vtype] : MINUS ;
    public final GuardCommandParser.minus_return minus() throws RecognitionException {
        GuardCommandParser.minus_return retval = new GuardCommandParser.minus_return();
        retval.start = input.LT(1);
        int minus_StartIndex = input.index();
        CommonTree root_0 = null;

        Token MINUS12=null;

        CommonTree MINUS12_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 13) ) { return retval; }
            // GuardCommand.g:185:2: ( MINUS )
            // GuardCommand.g:185:4: MINUS
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
    // GuardCommand.g:186:1: term returns [int type, int vtype] : ( (a= plus ) | (b= minus ) );
    public final GuardCommandParser.term_return term() throws RecognitionException {
        GuardCommandParser.term_return retval = new GuardCommandParser.term_return();
        retval.start = input.LT(1);
        int term_StartIndex = input.index();
        CommonTree root_0 = null;

        GuardCommandParser.plus_return a = null;

        GuardCommandParser.minus_return b = null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 14) ) { return retval; }
            // GuardCommand.g:187:2: ( (a= plus ) | (b= minus ) )
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
                    // GuardCommand.g:187:4: (a= plus )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    // GuardCommand.g:187:4: (a= plus )
                    // GuardCommand.g:187:5: a= plus
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
                    // GuardCommand.g:188:4: (b= minus )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    // GuardCommand.g:188:4: (b= minus )
                    // GuardCommand.g:188:5: b= minus
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
    // GuardCommand.g:191:1: mul returns [int type, int vtype] : MUL ;
    public final GuardCommandParser.mul_return mul() throws RecognitionException {
        GuardCommandParser.mul_return retval = new GuardCommandParser.mul_return();
        retval.start = input.LT(1);
        int mul_StartIndex = input.index();
        CommonTree root_0 = null;

        Token MUL13=null;

        CommonTree MUL13_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 15) ) { return retval; }
            // GuardCommand.g:192:2: ( MUL )
            // GuardCommand.g:192:4: MUL
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
    // GuardCommand.g:193:1: div returns [int type, int vtype] : DIV ;
    public final GuardCommandParser.div_return div() throws RecognitionException {
        GuardCommandParser.div_return retval = new GuardCommandParser.div_return();
        retval.start = input.LT(1);
        int div_StartIndex = input.index();
        CommonTree root_0 = null;

        Token DIV14=null;

        CommonTree DIV14_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 16) ) { return retval; }
            // GuardCommand.g:194:2: ( DIV )
            // GuardCommand.g:194:4: DIV
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
    // GuardCommand.g:195:1: factor returns [int type, int vtype] : ( (a= mul ) | (b= div ) );
    public final GuardCommandParser.factor_return factor() throws RecognitionException {
        GuardCommandParser.factor_return retval = new GuardCommandParser.factor_return();
        retval.start = input.LT(1);
        int factor_StartIndex = input.index();
        CommonTree root_0 = null;

        GuardCommandParser.mul_return a = null;

        GuardCommandParser.div_return b = null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 17) ) { return retval; }
            // GuardCommand.g:196:2: ( (a= mul ) | (b= div ) )
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
                    // GuardCommand.g:196:4: (a= mul )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    // GuardCommand.g:196:4: (a= mul )
                    // GuardCommand.g:196:5: a= mul
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
                    // GuardCommand.g:197:4: (b= div )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    // GuardCommand.g:197:4: (b= div )
                    // GuardCommand.g:197:5: b= div
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

    public static class expression_return extends ParserRuleReturnScope {
        public List<Statement> statList;
        public Value val;
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "expression"
    // GuardCommand.g:204:1: expression returns [List<Statement> statList, Value val] : e= expr_or ;
    public final GuardCommandParser.expression_return expression() throws RecognitionException {
        GuardCommandParser.expression_return retval = new GuardCommandParser.expression_return();
        retval.start = input.LT(1);
        int expression_StartIndex = input.index();
        CommonTree root_0 = null;

        GuardCommandParser.expr_or_return e = null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 18) ) { return retval; }
            // GuardCommand.g:205:2: (e= expr_or )
            // GuardCommand.g:205:4: e= expr_or
            {
            root_0 = (CommonTree)adaptor.nil();

            pushFollow(FOLLOW_expr_or_in_expression795);
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
            if ( state.backtracking>0 ) { memoize(input, 18, expression_StartIndex); }
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
    // GuardCommand.g:208:1: expr_or returns [List<Statement> statList, Value val] : ( (e1= expr_and b= or e2= expr_and ) (b= or e3= expr_and )* | (e4= expr_and ) );
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
            if ( state.backtracking>0 && alreadyParsedRule(input, 19) ) { return retval; }
            // GuardCommand.g:209:2: ( (e1= expr_and b= or e2= expr_and ) (b= or e3= expr_and )* | (e4= expr_and ) )
            int alt7=2;
            alt7 = dfa7.predict(input);
            switch (alt7) {
                case 1 :
                    // GuardCommand.g:210:2: (e1= expr_and b= or e2= expr_and ) (b= or e3= expr_and )*
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    // GuardCommand.g:210:2: (e1= expr_and b= or e2= expr_and )
                    // GuardCommand.g:210:3: e1= expr_and b= or e2= expr_and
                    {
                    pushFollow(FOLLOW_expr_and_in_expr_or816);
                    e1=expr_and();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, e1.getTree());
                    pushFollow(FOLLOW_or_in_expr_or820);
                    b=or();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, b.getTree());
                    pushFollow(FOLLOW_expr_and_in_expr_or824);
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

                    // GuardCommand.g:215:2: (b= or e3= expr_and )*
                    loop6:
                    do {
                        int alt6=2;
                        int LA6_0 = input.LA(1);

                        if ( (LA6_0==OR) ) {
                            alt6=1;
                        }


                        switch (alt6) {
                    	case 1 :
                    	    // GuardCommand.g:215:3: b= or e3= expr_and
                    	    {
                    	    pushFollow(FOLLOW_or_in_expr_or837);
                    	    b=or();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) adaptor.addChild(root_0, b.getTree());
                    	    pushFollow(FOLLOW_expr_and_in_expr_or841);
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
                    	    break loop6;
                        }
                    } while (true);


                    }
                    break;
                case 2 :
                    // GuardCommand.g:220:4: (e4= expr_and )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    // GuardCommand.g:220:4: (e4= expr_and )
                    // GuardCommand.g:220:5: e4= expr_and
                    {
                    pushFollow(FOLLOW_expr_and_in_expr_or857);
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
            if ( state.backtracking>0 ) { memoize(input, 19, expr_or_StartIndex); }
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
    // GuardCommand.g:223:1: expr_and returns [List<Statement> statList, Value val] : ( (e1= expr_eqa b= and e2= expr_eqa ) (b= and e3= expr_eqa )* | (e4= expr_eqa ) );
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
            if ( state.backtracking>0 && alreadyParsedRule(input, 20) ) { return retval; }
            // GuardCommand.g:224:2: ( (e1= expr_eqa b= and e2= expr_eqa ) (b= and e3= expr_eqa )* | (e4= expr_eqa ) )
            int alt9=2;
            alt9 = dfa9.predict(input);
            switch (alt9) {
                case 1 :
                    // GuardCommand.g:225:2: (e1= expr_eqa b= and e2= expr_eqa ) (b= and e3= expr_eqa )*
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    // GuardCommand.g:225:2: (e1= expr_eqa b= and e2= expr_eqa )
                    // GuardCommand.g:225:3: e1= expr_eqa b= and e2= expr_eqa
                    {
                    pushFollow(FOLLOW_expr_eqa_in_expr_and880);
                    e1=expr_eqa();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, e1.getTree());
                    pushFollow(FOLLOW_and_in_expr_and884);
                    b=and();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, b.getTree());
                    pushFollow(FOLLOW_expr_eqa_in_expr_and888);
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

                    // GuardCommand.g:230:2: (b= and e3= expr_eqa )*
                    loop8:
                    do {
                        int alt8=2;
                        int LA8_0 = input.LA(1);

                        if ( (LA8_0==AND) ) {
                            alt8=1;
                        }


                        switch (alt8) {
                    	case 1 :
                    	    // GuardCommand.g:230:3: b= and e3= expr_eqa
                    	    {
                    	    pushFollow(FOLLOW_and_in_expr_and901);
                    	    b=and();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) adaptor.addChild(root_0, b.getTree());
                    	    pushFollow(FOLLOW_expr_eqa_in_expr_and905);
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
                    	    break loop8;
                        }
                    } while (true);


                    }
                    break;
                case 2 :
                    // GuardCommand.g:235:4: (e4= expr_eqa )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    // GuardCommand.g:235:4: (e4= expr_eqa )
                    // GuardCommand.g:235:5: e4= expr_eqa
                    {
                    pushFollow(FOLLOW_expr_eqa_in_expr_and921);
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
            if ( state.backtracking>0 ) { memoize(input, 20, expr_and_StartIndex); }
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
    // GuardCommand.g:239:1: expr_eqa returns [List<Statement> statList, Value val] : ( (e1= expr_rel b= eqa e2= expr_rel ) (b= eqa e3= expr_rel )* | (e4= expr_rel ) );
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
            if ( state.backtracking>0 && alreadyParsedRule(input, 21) ) { return retval; }
            // GuardCommand.g:240:2: ( (e1= expr_rel b= eqa e2= expr_rel ) (b= eqa e3= expr_rel )* | (e4= expr_rel ) )
            int alt11=2;
            alt11 = dfa11.predict(input);
            switch (alt11) {
                case 1 :
                    // GuardCommand.g:241:2: (e1= expr_rel b= eqa e2= expr_rel ) (b= eqa e3= expr_rel )*
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    // GuardCommand.g:241:2: (e1= expr_rel b= eqa e2= expr_rel )
                    // GuardCommand.g:241:3: e1= expr_rel b= eqa e2= expr_rel
                    {
                    pushFollow(FOLLOW_expr_rel_in_expr_eqa945);
                    e1=expr_rel();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, e1.getTree());
                    pushFollow(FOLLOW_eqa_in_expr_eqa949);
                    b=eqa();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, b.getTree());
                    pushFollow(FOLLOW_expr_rel_in_expr_eqa953);
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

                    // GuardCommand.g:246:2: (b= eqa e3= expr_rel )*
                    loop10:
                    do {
                        int alt10=2;
                        int LA10_0 = input.LA(1);

                        if ( ((LA10_0>=EQ && LA10_0<=NEQ)) ) {
                            alt10=1;
                        }


                        switch (alt10) {
                    	case 1 :
                    	    // GuardCommand.g:246:3: b= eqa e3= expr_rel
                    	    {
                    	    pushFollow(FOLLOW_eqa_in_expr_eqa966);
                    	    b=eqa();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) adaptor.addChild(root_0, b.getTree());
                    	    pushFollow(FOLLOW_expr_rel_in_expr_eqa970);
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
                    	    break loop10;
                        }
                    } while (true);


                    }
                    break;
                case 2 :
                    // GuardCommand.g:251:4: (e4= expr_rel )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    // GuardCommand.g:251:4: (e4= expr_rel )
                    // GuardCommand.g:251:5: e4= expr_rel
                    {
                    pushFollow(FOLLOW_expr_rel_in_expr_eqa986);
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
            if ( state.backtracking>0 ) { memoize(input, 21, expr_eqa_StartIndex); }
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
    // GuardCommand.g:254:1: expr_rel returns [List<Statement> statList, Value val] : ( (e1= expr_term b= rel e2= expr_term ) (b= rel e3= expr_term )* | (e4= expr_term ) );
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
            if ( state.backtracking>0 && alreadyParsedRule(input, 22) ) { return retval; }
            // GuardCommand.g:255:2: ( (e1= expr_term b= rel e2= expr_term ) (b= rel e3= expr_term )* | (e4= expr_term ) )
            int alt13=2;
            alt13 = dfa13.predict(input);
            switch (alt13) {
                case 1 :
                    // GuardCommand.g:256:2: (e1= expr_term b= rel e2= expr_term ) (b= rel e3= expr_term )*
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    // GuardCommand.g:256:2: (e1= expr_term b= rel e2= expr_term )
                    // GuardCommand.g:256:3: e1= expr_term b= rel e2= expr_term
                    {
                    pushFollow(FOLLOW_expr_term_in_expr_rel1008);
                    e1=expr_term();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, e1.getTree());
                    pushFollow(FOLLOW_rel_in_expr_rel1012);
                    b=rel();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, b.getTree());
                    pushFollow(FOLLOW_expr_term_in_expr_rel1016);
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

                    // GuardCommand.g:261:2: (b= rel e3= expr_term )*
                    loop12:
                    do {
                        int alt12=2;
                        int LA12_0 = input.LA(1);

                        if ( ((LA12_0>=GREATER_THAN && LA12_0<=LESS_EQ)) ) {
                            alt12=1;
                        }


                        switch (alt12) {
                    	case 1 :
                    	    // GuardCommand.g:261:3: b= rel e3= expr_term
                    	    {
                    	    pushFollow(FOLLOW_rel_in_expr_rel1029);
                    	    b=rel();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) adaptor.addChild(root_0, b.getTree());
                    	    pushFollow(FOLLOW_expr_term_in_expr_rel1033);
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
                    	    break loop12;
                        }
                    } while (true);


                    }
                    break;
                case 2 :
                    // GuardCommand.g:266:4: (e4= expr_term )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    // GuardCommand.g:266:4: (e4= expr_term )
                    // GuardCommand.g:266:5: e4= expr_term
                    {
                    pushFollow(FOLLOW_expr_term_in_expr_rel1049);
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
            if ( state.backtracking>0 ) { memoize(input, 22, expr_rel_StartIndex); }
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
    // GuardCommand.g:269:1: expr_term returns [List<Statement> statList, Value val] : ( (e1= expr_factor b= term e2= expr_factor ) (b= term e3= expr_factor )* | (e4= expr_factor ) );
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
            if ( state.backtracking>0 && alreadyParsedRule(input, 23) ) { return retval; }
            // GuardCommand.g:270:2: ( (e1= expr_factor b= term e2= expr_factor ) (b= term e3= expr_factor )* | (e4= expr_factor ) )
            int alt15=2;
            alt15 = dfa15.predict(input);
            switch (alt15) {
                case 1 :
                    // GuardCommand.g:271:2: (e1= expr_factor b= term e2= expr_factor ) (b= term e3= expr_factor )*
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    // GuardCommand.g:271:2: (e1= expr_factor b= term e2= expr_factor )
                    // GuardCommand.g:271:3: e1= expr_factor b= term e2= expr_factor
                    {
                    pushFollow(FOLLOW_expr_factor_in_expr_term1072);
                    e1=expr_factor();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, e1.getTree());
                    pushFollow(FOLLOW_term_in_expr_term1076);
                    b=term();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, b.getTree());
                    pushFollow(FOLLOW_expr_factor_in_expr_term1080);
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

                    // GuardCommand.g:276:2: (b= term e3= expr_factor )*
                    loop14:
                    do {
                        int alt14=2;
                        int LA14_0 = input.LA(1);

                        if ( ((LA14_0>=PLUS && LA14_0<=MINUS)) ) {
                            alt14=1;
                        }


                        switch (alt14) {
                    	case 1 :
                    	    // GuardCommand.g:276:3: b= term e3= expr_factor
                    	    {
                    	    pushFollow(FOLLOW_term_in_expr_term1093);
                    	    b=term();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) adaptor.addChild(root_0, b.getTree());
                    	    pushFollow(FOLLOW_expr_factor_in_expr_term1097);
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
                    	    break loop14;
                        }
                    } while (true);


                    }
                    break;
                case 2 :
                    // GuardCommand.g:281:4: (e4= expr_factor )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    // GuardCommand.g:281:4: (e4= expr_factor )
                    // GuardCommand.g:281:5: e4= expr_factor
                    {
                    pushFollow(FOLLOW_expr_factor_in_expr_term1113);
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
            if ( state.backtracking>0 ) { memoize(input, 23, expr_term_StartIndex); }
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
    // GuardCommand.g:284:1: expr_factor returns [List<Statement> statList, Value val] : ( (e1= expr_unary b= factor e2= expr_unary ) (b= factor e3= expr_unary )* | (e4= expr_unary ) );
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
            if ( state.backtracking>0 && alreadyParsedRule(input, 24) ) { return retval; }
            // GuardCommand.g:285:2: ( (e1= expr_unary b= factor e2= expr_unary ) (b= factor e3= expr_unary )* | (e4= expr_unary ) )
            int alt17=2;
            alt17 = dfa17.predict(input);
            switch (alt17) {
                case 1 :
                    // GuardCommand.g:286:2: (e1= expr_unary b= factor e2= expr_unary ) (b= factor e3= expr_unary )*
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    // GuardCommand.g:286:2: (e1= expr_unary b= factor e2= expr_unary )
                    // GuardCommand.g:286:3: e1= expr_unary b= factor e2= expr_unary
                    {
                    pushFollow(FOLLOW_expr_unary_in_expr_factor1136);
                    e1=expr_unary();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, e1.getTree());
                    pushFollow(FOLLOW_factor_in_expr_factor1140);
                    b=factor();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, b.getTree());
                    pushFollow(FOLLOW_expr_unary_in_expr_factor1144);
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

                    // GuardCommand.g:291:2: (b= factor e3= expr_unary )*
                    loop16:
                    do {
                        int alt16=2;
                        int LA16_0 = input.LA(1);

                        if ( ((LA16_0>=MUL && LA16_0<=DIV)) ) {
                            alt16=1;
                        }


                        switch (alt16) {
                    	case 1 :
                    	    // GuardCommand.g:291:3: b= factor e3= expr_unary
                    	    {
                    	    pushFollow(FOLLOW_factor_in_expr_factor1157);
                    	    b=factor();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) adaptor.addChild(root_0, b.getTree());
                    	    pushFollow(FOLLOW_expr_unary_in_expr_factor1161);
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
                    	    break loop16;
                        }
                    } while (true);


                    }
                    break;
                case 2 :
                    // GuardCommand.g:296:4: (e4= expr_unary )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    // GuardCommand.g:296:4: (e4= expr_unary )
                    // GuardCommand.g:296:5: e4= expr_unary
                    {
                    pushFollow(FOLLOW_expr_unary_in_expr_factor1177);
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
            if ( state.backtracking>0 ) { memoize(input, 24, expr_factor_StartIndex); }
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
    // GuardCommand.g:299:1: expr_unary returns [List<Statement> statList, Value val] : (u= unary_operator e= expr_unary | l= lite | lp= lite_paren );
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
            if ( state.backtracking>0 && alreadyParsedRule(input, 25) ) { return retval; }
            // GuardCommand.g:300:2: (u= unary_operator e= expr_unary | l= lite | lp= lite_paren )
            int alt18=3;
            switch ( input.LA(1) ) {
            case MINUS:
            case NOT:
                {
                alt18=1;
                }
                break;
            case TRUE:
            case FALSE:
            case INTEGER_LITERAL:
            case IDENTIFIER:
                {
                alt18=2;
                }
                break;
            case LPAREN:
                {
                alt18=3;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 18, 0, input);

                throw nvae;
            }

            switch (alt18) {
                case 1 :
                    // GuardCommand.g:300:4: u= unary_operator e= expr_unary
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    pushFollow(FOLLOW_unary_operator_in_expr_unary1197);
                    u=unary_operator();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, u.getTree());
                    pushFollow(FOLLOW_expr_unary_in_expr_unary1201);
                    e=expr_unary();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, e.getTree());
                    if ( state.backtracking==0 ) {

                      			//Is e a literal or a non-literal unary_expr?
                      			retval.statList = e.statList == null ? new ArrayList<Statement>() : e.statList;
                      			final Value valEnd = e.val != null ? e.val : retval.statList.get(retval.statList.size()-1).getAssign();
                      			final Statement newestStat = statementFactory.createSimpleStatement(
                      				u.type,
                      				new CodeLocation(u.tree.getLine()),
                      				null,
                      				variableTable.createTemporaryVariable(u.vtype),
                      				valEnd
                      			);
                      			retval.statList.add(newestStat);
                      		
                    }

                    }
                    break;
                case 2 :
                    // GuardCommand.g:314:4: l= lite
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    pushFollow(FOLLOW_lite_in_expr_unary1212);
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
                    // GuardCommand.g:315:4: lp= lite_paren
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    pushFollow(FOLLOW_lite_paren_in_expr_unary1221);
                    lp=lite_paren();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, lp.getTree());
                    if ( state.backtracking==0 ) {
                      retval.statList = lp.statList;
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
            if ( state.backtracking>0 ) { memoize(input, 25, expr_unary_StartIndex); }
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
    // GuardCommand.g:318:1: literal returns [List<Statement> statList, Value val] : (l= lite | lp= lite_paren );
    public final GuardCommandParser.literal_return literal() throws RecognitionException {
        GuardCommandParser.literal_return retval = new GuardCommandParser.literal_return();
        retval.start = input.LT(1);
        int literal_StartIndex = input.index();
        CommonTree root_0 = null;

        GuardCommandParser.lite_return l = null;

        GuardCommandParser.lite_paren_return lp = null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 26) ) { return retval; }
            // GuardCommand.g:319:2: (l= lite | lp= lite_paren )
            int alt19=2;
            int LA19_0 = input.LA(1);

            if ( ((LA19_0>=TRUE && LA19_0<=IDENTIFIER)) ) {
                alt19=1;
            }
            else if ( (LA19_0==LPAREN) ) {
                alt19=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 19, 0, input);

                throw nvae;
            }
            switch (alt19) {
                case 1 :
                    // GuardCommand.g:319:4: l= lite
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    pushFollow(FOLLOW_lite_in_literal1240);
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
                    // GuardCommand.g:320:4: lp= lite_paren
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    pushFollow(FOLLOW_lite_paren_in_literal1249);
                    lp=lite_paren();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, lp.getTree());
                    if ( state.backtracking==0 ) {
                      retval.statList = lp.statList;
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
            if ( state.backtracking>0 ) { memoize(input, 26, literal_StartIndex); }
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
    // GuardCommand.g:323:1: lite returns [Value val] : (inte= INTEGER_LITERAL | tru= TRUE | fal= FALSE | id= IDENTIFIER );
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
            if ( state.backtracking>0 && alreadyParsedRule(input, 27) ) { return retval; }
            // GuardCommand.g:324:2: (inte= INTEGER_LITERAL | tru= TRUE | fal= FALSE | id= IDENTIFIER )
            int alt20=4;
            switch ( input.LA(1) ) {
            case INTEGER_LITERAL:
                {
                alt20=1;
                }
                break;
            case TRUE:
                {
                alt20=2;
                }
                break;
            case FALSE:
                {
                alt20=3;
                }
                break;
            case IDENTIFIER:
                {
                alt20=4;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 20, 0, input);

                throw nvae;
            }

            switch (alt20) {
                case 1 :
                    // GuardCommand.g:325:2: inte= INTEGER_LITERAL
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    inte=(Token)match(input,INTEGER_LITERAL,FOLLOW_INTEGER_LITERAL_in_lite1269); if (state.failed) return retval;
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
                    // GuardCommand.g:331:4: tru= TRUE
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    tru=(Token)match(input,TRUE,FOLLOW_TRUE_in_lite1280); if (state.failed) return retval;
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
                    // GuardCommand.g:332:4: fal= FALSE
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    fal=(Token)match(input,FALSE,FOLLOW_FALSE_in_lite1289); if (state.failed) return retval;
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
                    // GuardCommand.g:333:4: id= IDENTIFIER
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    id=(Token)match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_lite1298); if (state.failed) return retval;
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
            if ( state.backtracking>0 ) { memoize(input, 27, lite_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "lite"

    public static class lite_paren_return extends ParserRuleReturnScope {
        public List<Statement> statList;
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "lite_paren"
    // GuardCommand.g:336:1: lite_paren returns [List<Statement> statList] : LPAREN e= expression RPAREN ;
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
            if ( state.backtracking>0 && alreadyParsedRule(input, 28) ) { return retval; }
            // GuardCommand.g:337:2: ( LPAREN e= expression RPAREN )
            // GuardCommand.g:337:4: LPAREN e= expression RPAREN
            {
            root_0 = (CommonTree)adaptor.nil();

            LPAREN15=(Token)match(input,LPAREN,FOLLOW_LPAREN_in_lite_paren1315); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            LPAREN15_tree = (CommonTree)adaptor.create(LPAREN15);
            adaptor.addChild(root_0, LPAREN15_tree);
            }
            pushFollow(FOLLOW_expression_in_lite_paren1319);
            e=expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, e.getTree());
            RPAREN16=(Token)match(input,RPAREN,FOLLOW_RPAREN_in_lite_paren1321); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            RPAREN16_tree = (CommonTree)adaptor.create(RPAREN16);
            adaptor.addChild(root_0, RPAREN16_tree);
            }
            if ( state.backtracking==0 ) {

              			if (retval.statList == null) {retval.statList = new ArrayList<Statement>();}
              			retval.statList = e.statList;
              		
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
            if ( state.backtracking>0 ) { memoize(input, 28, lite_paren_StartIndex); }
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
    // GuardCommand.g:350:1: command returns [List<Statement> commands] : (a= assignment_cmd | b= skip_cmd | c= abort_cmd | d= read_cmd | e= write_cmd | l= LCURLY f= command RCURLY | g= if_cmd | h= do_cmd ) ( SEMI i= command )* ;
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
            if ( state.backtracking>0 && alreadyParsedRule(input, 29) ) { return retval; }
            // GuardCommand.g:351:2: ( (a= assignment_cmd | b= skip_cmd | c= abort_cmd | d= read_cmd | e= write_cmd | l= LCURLY f= command RCURLY | g= if_cmd | h= do_cmd ) ( SEMI i= command )* )
            // GuardCommand.g:352:2: (a= assignment_cmd | b= skip_cmd | c= abort_cmd | d= read_cmd | e= write_cmd | l= LCURLY f= command RCURLY | g= if_cmd | h= do_cmd ) ( SEMI i= command )*
            {
            root_0 = (CommonTree)adaptor.nil();

            if ( state.backtracking==0 ) {

              		if (retval.commands == null) {
              			retval.commands = new ArrayList<Statement>();
              		}
              	
            }
            // GuardCommand.g:358:2: (a= assignment_cmd | b= skip_cmd | c= abort_cmd | d= read_cmd | e= write_cmd | l= LCURLY f= command RCURLY | g= if_cmd | h= do_cmd )
            int alt21=8;
            switch ( input.LA(1) ) {
            case IDENTIFIER:
                {
                alt21=1;
                }
                break;
            case SKIP:
                {
                alt21=2;
                }
                break;
            case ABORT:
                {
                alt21=3;
                }
                break;
            case READ:
                {
                alt21=4;
                }
                break;
            case WRITE:
                {
                alt21=5;
                }
                break;
            case LCURLY:
                {
                alt21=6;
                }
                break;
            case IF:
                {
                alt21=7;
                }
                break;
            case DO:
                {
                alt21=8;
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
                    // GuardCommand.g:358:4: a= assignment_cmd
                    {
                    pushFollow(FOLLOW_assignment_cmd_in_command1358);
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
                    // GuardCommand.g:359:4: b= skip_cmd
                    {
                    pushFollow(FOLLOW_skip_cmd_in_command1369);
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
                    // GuardCommand.g:360:4: c= abort_cmd
                    {
                    pushFollow(FOLLOW_abort_cmd_in_command1381);
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
                    // GuardCommand.g:361:4: d= read_cmd
                    {
                    pushFollow(FOLLOW_read_cmd_in_command1393);
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
                    // GuardCommand.g:362:4: e= write_cmd
                    {
                    pushFollow(FOLLOW_write_cmd_in_command1405);
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
                    // GuardCommand.g:363:4: l= LCURLY f= command RCURLY
                    {
                    l=(Token)match(input,LCURLY,FOLLOW_LCURLY_in_command1417); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    l_tree = (CommonTree)adaptor.create(l);
                    adaptor.addChild(root_0, l_tree);
                    }
                    pushFollow(FOLLOW_command_in_command1421);
                    f=command();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, f.getTree());
                    RCURLY17=(Token)match(input,RCURLY,FOLLOW_RCURLY_in_command1423); if (state.failed) return retval;
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
                    // GuardCommand.g:370:4: g= if_cmd
                    {
                    pushFollow(FOLLOW_if_cmd_in_command1434);
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
                    // GuardCommand.g:371:4: h= do_cmd
                    {
                    pushFollow(FOLLOW_do_cmd_in_command1448);
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

            // GuardCommand.g:374:2: ( SEMI i= command )*
            loop22:
            do {
                int alt22=2;
                int LA22_0 = input.LA(1);

                if ( (LA22_0==SEMI) ) {
                    int LA22_2 = input.LA(2);

                    if ( (synpred33_GuardCommand()) ) {
                        alt22=1;
                    }


                }


                switch (alt22) {
            	case 1 :
            	    // GuardCommand.g:374:3: SEMI i= command
            	    {
            	    SEMI18=(Token)match(input,SEMI,FOLLOW_SEMI_in_command1464); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    SEMI18_tree = (CommonTree)adaptor.create(SEMI18);
            	    adaptor.addChild(root_0, SEMI18_tree);
            	    }
            	    pushFollow(FOLLOW_command_in_command1468);
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
            	    break loop22;
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
            if ( state.backtracking>0 ) { memoize(input, 29, command_StartIndex); }
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
    // GuardCommand.g:382:1: assignment_cmd returns [List<Statement> commands] : id= IDENTIFIER as= ASSIGN e= expression ;
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
            if ( state.backtracking>0 && alreadyParsedRule(input, 30) ) { return retval; }
            // GuardCommand.g:383:2: (id= IDENTIFIER as= ASSIGN e= expression )
            // GuardCommand.g:383:4: id= IDENTIFIER as= ASSIGN e= expression
            {
            root_0 = (CommonTree)adaptor.nil();

            id=(Token)match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_assignment_cmd1493); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            id_tree = (CommonTree)adaptor.create(id);
            adaptor.addChild(root_0, id_tree);
            }
            as=(Token)match(input,ASSIGN,FOLLOW_ASSIGN_in_assignment_cmd1497); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            as_tree = (CommonTree)adaptor.create(as);
            adaptor.addChild(root_0, as_tree);
            }
            pushFollow(FOLLOW_expression_in_assignment_cmd1501);
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
            if ( state.backtracking>0 ) { memoize(input, 30, assignment_cmd_StartIndex); }
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
    // GuardCommand.g:400:1: skip_cmd returns [Statement command] : s= SKIP ;
    public final GuardCommandParser.skip_cmd_return skip_cmd() throws RecognitionException {
        GuardCommandParser.skip_cmd_return retval = new GuardCommandParser.skip_cmd_return();
        retval.start = input.LT(1);
        int skip_cmd_StartIndex = input.index();
        CommonTree root_0 = null;

        Token s=null;

        CommonTree s_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 31) ) { return retval; }
            // GuardCommand.g:401:2: (s= SKIP )
            // GuardCommand.g:401:4: s= SKIP
            {
            root_0 = (CommonTree)adaptor.nil();

            s=(Token)match(input,SKIP,FOLLOW_SKIP_in_skip_cmd1522); if (state.failed) return retval;
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
            if ( state.backtracking>0 ) { memoize(input, 31, skip_cmd_StartIndex); }
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
    // GuardCommand.g:410:1: abort_cmd returns [Statement command] : a= ABORT ;
    public final GuardCommandParser.abort_cmd_return abort_cmd() throws RecognitionException {
        GuardCommandParser.abort_cmd_return retval = new GuardCommandParser.abort_cmd_return();
        retval.start = input.LT(1);
        int abort_cmd_StartIndex = input.index();
        CommonTree root_0 = null;

        Token a=null;

        CommonTree a_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 32) ) { return retval; }
            // GuardCommand.g:411:2: (a= ABORT )
            // GuardCommand.g:411:4: a= ABORT
            {
            root_0 = (CommonTree)adaptor.nil();

            a=(Token)match(input,ABORT,FOLLOW_ABORT_in_abort_cmd1543); if (state.failed) return retval;
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
            if ( state.backtracking>0 ) { memoize(input, 32, abort_cmd_StartIndex); }
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
    // GuardCommand.g:420:1: read_cmd returns [Statement command] : rea= READ id= IDENTIFIER ;
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
            if ( state.backtracking>0 && alreadyParsedRule(input, 33) ) { return retval; }
            // GuardCommand.g:421:2: (rea= READ id= IDENTIFIER )
            // GuardCommand.g:421:4: rea= READ id= IDENTIFIER
            {
            root_0 = (CommonTree)adaptor.nil();

            rea=(Token)match(input,READ,FOLLOW_READ_in_read_cmd1564); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            rea_tree = (CommonTree)adaptor.create(rea);
            adaptor.addChild(root_0, rea_tree);
            }
            id=(Token)match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_read_cmd1568); if (state.failed) return retval;
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
            if ( state.backtracking>0 ) { memoize(input, 33, read_cmd_StartIndex); }
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
    // GuardCommand.g:431:1: write_cmd returns [List<Statement> commands] : wr= WRITE e= expression ;
    public final GuardCommandParser.write_cmd_return write_cmd() throws RecognitionException {
        GuardCommandParser.write_cmd_return retval = new GuardCommandParser.write_cmd_return();
        retval.start = input.LT(1);
        int write_cmd_StartIndex = input.index();
        CommonTree root_0 = null;

        Token wr=null;
        GuardCommandParser.expression_return e = null;


        CommonTree wr_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 34) ) { return retval; }
            // GuardCommand.g:432:2: (wr= WRITE e= expression )
            // GuardCommand.g:432:4: wr= WRITE e= expression
            {
            root_0 = (CommonTree)adaptor.nil();

            wr=(Token)match(input,WRITE,FOLLOW_WRITE_in_write_cmd1589); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            wr_tree = (CommonTree)adaptor.create(wr);
            adaptor.addChild(root_0, wr_tree);
            }
            pushFollow(FOLLOW_expression_in_write_cmd1593);
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
            if ( state.backtracking>0 ) { memoize(input, 34, write_cmd_StartIndex); }
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
    // GuardCommand.g:448:1: if_cmd returns [Statement command] : ift= IF gc= guarded_cmd FI ;
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
            if ( state.backtracking>0 && alreadyParsedRule(input, 35) ) { return retval; }
            // GuardCommand.g:449:2: (ift= IF gc= guarded_cmd FI )
            // GuardCommand.g:449:4: ift= IF gc= guarded_cmd FI
            {
            root_0 = (CommonTree)adaptor.nil();

            ift=(Token)match(input,IF,FOLLOW_IF_in_if_cmd1614); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            ift_tree = (CommonTree)adaptor.create(ift);
            adaptor.addChild(root_0, ift_tree);
            }
            pushFollow(FOLLOW_guarded_cmd_in_if_cmd1618);
            gc=guarded_cmd();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, gc.getTree());
            FI19=(Token)match(input,FI,FOLLOW_FI_in_if_cmd1620); if (state.failed) return retval;
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
            if ( state.backtracking>0 ) { memoize(input, 35, if_cmd_StartIndex); }
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
    // GuardCommand.g:458:1: do_cmd returns [Statement command] : dot= DO gc= guarded_cmd OD ;
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
            if ( state.backtracking>0 && alreadyParsedRule(input, 36) ) { return retval; }
            // GuardCommand.g:459:2: (dot= DO gc= guarded_cmd OD )
            // GuardCommand.g:459:4: dot= DO gc= guarded_cmd OD
            {
            root_0 = (CommonTree)adaptor.nil();

            dot=(Token)match(input,DO,FOLLOW_DO_in_do_cmd1641); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            dot_tree = (CommonTree)adaptor.create(dot);
            adaptor.addChild(root_0, dot_tree);
            }
            pushFollow(FOLLOW_guarded_cmd_in_do_cmd1645);
            gc=guarded_cmd();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, gc.getTree());
            OD20=(Token)match(input,OD,FOLLOW_OD_in_do_cmd1647); if (state.failed) return retval;
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
            if ( state.backtracking>0 ) { memoize(input, 36, do_cmd_StartIndex); }
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
    // GuardCommand.g:468:1: guarded_cmd returns [List<Statement> commands] : (e= expression ARROW c= command ) ( GUARD gc= guarded_cmd )* ;
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
            if ( state.backtracking>0 && alreadyParsedRule(input, 37) ) { return retval; }
            // GuardCommand.g:469:2: ( (e= expression ARROW c= command ) ( GUARD gc= guarded_cmd )* )
            // GuardCommand.g:470:2: (e= expression ARROW c= command ) ( GUARD gc= guarded_cmd )*
            {
            root_0 = (CommonTree)adaptor.nil();

            if ( state.backtracking==0 ) {

              		if (retval.commands == null) {
              			retval.commands = new ArrayList<Statement>();
              		}
              	
            }
            // GuardCommand.g:475:2: (e= expression ARROW c= command )
            // GuardCommand.g:475:3: e= expression ARROW c= command
            {
            pushFollow(FOLLOW_expression_in_guarded_cmd1673);
            e=expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, e.getTree());
            ARROW21=(Token)match(input,ARROW,FOLLOW_ARROW_in_guarded_cmd1675); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            ARROW21_tree = (CommonTree)adaptor.create(ARROW21);
            adaptor.addChild(root_0, ARROW21_tree);
            }
            pushFollow(FOLLOW_command_in_guarded_cmd1679);
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
            // GuardCommand.g:497:2: ( GUARD gc= guarded_cmd )*
            loop23:
            do {
                int alt23=2;
                int LA23_0 = input.LA(1);

                if ( (LA23_0==GUARD) ) {
                    int LA23_2 = input.LA(2);

                    if ( (synpred34_GuardCommand()) ) {
                        alt23=1;
                    }


                }


                switch (alt23) {
            	case 1 :
            	    // GuardCommand.g:497:3: GUARD gc= guarded_cmd
            	    {
            	    GUARD22=(Token)match(input,GUARD,FOLLOW_GUARD_in_guarded_cmd1686); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    GUARD22_tree = (CommonTree)adaptor.create(GUARD22);
            	    adaptor.addChild(root_0, GUARD22_tree);
            	    }
            	    pushFollow(FOLLOW_guarded_cmd_in_guarded_cmd1690);
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
            if ( state.backtracking>0 ) { memoize(input, 37, guarded_cmd_StartIndex); }
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
    // GuardCommand.g:500:1: program returns [CompilationUnit compilationUnit] : m= MODULE id= IDENTIFIER COLON c= command END ;
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
            if ( state.backtracking>0 && alreadyParsedRule(input, 38) ) { return retval; }
            // GuardCommand.g:501:2: (m= MODULE id= IDENTIFIER COLON c= command END )
            // GuardCommand.g:501:4: m= MODULE id= IDENTIFIER COLON c= command END
            {
            root_0 = (CommonTree)adaptor.nil();

            m=(Token)match(input,MODULE,FOLLOW_MODULE_in_program1713); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            m_tree = (CommonTree)adaptor.create(m);
            adaptor.addChild(root_0, m_tree);
            }
            id=(Token)match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_program1717); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            id_tree = (CommonTree)adaptor.create(id);
            adaptor.addChild(root_0, id_tree);
            }
            COLON23=(Token)match(input,COLON,FOLLOW_COLON_in_program1719); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            COLON23_tree = (CommonTree)adaptor.create(COLON23);
            adaptor.addChild(root_0, COLON23_tree);
            }
            pushFollow(FOLLOW_command_in_program1723);
            c=command();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, c.getTree());
            END24=(Token)match(input,END,FOLLOW_END_in_program1725); if (state.failed) return retval;
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
            if ( state.backtracking>0 ) { memoize(input, 38, program_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "program"

    public static class annotation_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "annotation"
    // GuardCommand.g:514:1: annotation : ( '#@' ) id= IDENTIFIER '=' '\"' te= ( (~ ( '\"' ) )* ) '\"' ;
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
            if ( state.backtracking>0 && alreadyParsedRule(input, 39) ) { return retval; }
            // GuardCommand.g:515:2: ( ( '#@' ) id= IDENTIFIER '=' '\"' te= ( (~ ( '\"' ) )* ) '\"' )
            // GuardCommand.g:515:4: ( '#@' ) id= IDENTIFIER '=' '\"' te= ( (~ ( '\"' ) )* ) '\"'
            {
            root_0 = (CommonTree)adaptor.nil();

            // GuardCommand.g:515:4: ( '#@' )
            // GuardCommand.g:515:5: '#@'
            {
            string_literal25=(Token)match(input,44,FOLLOW_44_in_annotation1747); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            string_literal25_tree = (CommonTree)adaptor.create(string_literal25);
            adaptor.addChild(root_0, string_literal25_tree);
            }

            }

            id=(Token)match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_annotation1752); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            id_tree = (CommonTree)adaptor.create(id);
            adaptor.addChild(root_0, id_tree);
            }
            char_literal26=(Token)match(input,EQ,FOLLOW_EQ_in_annotation1754); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal26_tree = (CommonTree)adaptor.create(char_literal26);
            adaptor.addChild(root_0, char_literal26_tree);
            }
            char_literal27=(Token)match(input,45,FOLLOW_45_in_annotation1756); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            char_literal27_tree = (CommonTree)adaptor.create(char_literal27);
            adaptor.addChild(root_0, char_literal27_tree);
            }
            // GuardCommand.g:515:36: ( (~ ( '\"' ) )* )
            // GuardCommand.g:515:37: (~ ( '\"' ) )*
            {
            // GuardCommand.g:515:37: (~ ( '\"' ) )*
            loop24:
            do {
                int alt24=2;
                int LA24_0 = input.LA(1);

                if ( ((LA24_0>=AND && LA24_0<=44)) ) {
                    alt24=1;
                }


                switch (alt24) {
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
            	    break loop24;
                }
            } while (true);


            }

            char_literal29=(Token)match(input,45,FOLLOW_45_in_annotation1768); if (state.failed) return retval;
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
            if ( state.backtracking>0 ) { memoize(input, 39, annotation_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "annotation"

    // $ANTLR start synpred9_GuardCommand
    public final void synpred9_GuardCommand_fragment() throws RecognitionException {   
        GuardCommandParser.expr_and_return e1 = null;

        GuardCommandParser.or_return b = null;

        GuardCommandParser.expr_and_return e2 = null;

        GuardCommandParser.expr_and_return e3 = null;


        // GuardCommand.g:210:2: ( (e1= expr_and b= or e2= expr_and ) (b= or e3= expr_and )* )
        // GuardCommand.g:210:2: (e1= expr_and b= or e2= expr_and ) (b= or e3= expr_and )*
        {
        // GuardCommand.g:210:2: (e1= expr_and b= or e2= expr_and )
        // GuardCommand.g:210:3: e1= expr_and b= or e2= expr_and
        {
        pushFollow(FOLLOW_expr_and_in_synpred9_GuardCommand816);
        e1=expr_and();

        state._fsp--;
        if (state.failed) return ;
        pushFollow(FOLLOW_or_in_synpred9_GuardCommand820);
        b=or();

        state._fsp--;
        if (state.failed) return ;
        pushFollow(FOLLOW_expr_and_in_synpred9_GuardCommand824);
        e2=expr_and();

        state._fsp--;
        if (state.failed) return ;

        }

        // GuardCommand.g:215:2: (b= or e3= expr_and )*
        loop25:
        do {
            int alt25=2;
            int LA25_0 = input.LA(1);

            if ( (LA25_0==OR) ) {
                alt25=1;
            }


            switch (alt25) {
        	case 1 :
        	    // GuardCommand.g:215:3: b= or e3= expr_and
        	    {
        	    pushFollow(FOLLOW_or_in_synpred9_GuardCommand837);
        	    b=or();

        	    state._fsp--;
        	    if (state.failed) return ;
        	    pushFollow(FOLLOW_expr_and_in_synpred9_GuardCommand841);
        	    e3=expr_and();

        	    state._fsp--;
        	    if (state.failed) return ;

        	    }
        	    break;

        	default :
        	    break loop25;
            }
        } while (true);


        }
    }
    // $ANTLR end synpred9_GuardCommand

    // $ANTLR start synpred11_GuardCommand
    public final void synpred11_GuardCommand_fragment() throws RecognitionException {   
        GuardCommandParser.expr_eqa_return e1 = null;

        GuardCommandParser.and_return b = null;

        GuardCommandParser.expr_eqa_return e2 = null;

        GuardCommandParser.expr_eqa_return e3 = null;


        // GuardCommand.g:225:2: ( (e1= expr_eqa b= and e2= expr_eqa ) (b= and e3= expr_eqa )* )
        // GuardCommand.g:225:2: (e1= expr_eqa b= and e2= expr_eqa ) (b= and e3= expr_eqa )*
        {
        // GuardCommand.g:225:2: (e1= expr_eqa b= and e2= expr_eqa )
        // GuardCommand.g:225:3: e1= expr_eqa b= and e2= expr_eqa
        {
        pushFollow(FOLLOW_expr_eqa_in_synpred11_GuardCommand880);
        e1=expr_eqa();

        state._fsp--;
        if (state.failed) return ;
        pushFollow(FOLLOW_and_in_synpred11_GuardCommand884);
        b=and();

        state._fsp--;
        if (state.failed) return ;
        pushFollow(FOLLOW_expr_eqa_in_synpred11_GuardCommand888);
        e2=expr_eqa();

        state._fsp--;
        if (state.failed) return ;

        }

        // GuardCommand.g:230:2: (b= and e3= expr_eqa )*
        loop26:
        do {
            int alt26=2;
            int LA26_0 = input.LA(1);

            if ( (LA26_0==AND) ) {
                alt26=1;
            }


            switch (alt26) {
        	case 1 :
        	    // GuardCommand.g:230:3: b= and e3= expr_eqa
        	    {
        	    pushFollow(FOLLOW_and_in_synpred11_GuardCommand901);
        	    b=and();

        	    state._fsp--;
        	    if (state.failed) return ;
        	    pushFollow(FOLLOW_expr_eqa_in_synpred11_GuardCommand905);
        	    e3=expr_eqa();

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
    // $ANTLR end synpred11_GuardCommand

    // $ANTLR start synpred13_GuardCommand
    public final void synpred13_GuardCommand_fragment() throws RecognitionException {   
        GuardCommandParser.expr_rel_return e1 = null;

        GuardCommandParser.eqa_return b = null;

        GuardCommandParser.expr_rel_return e2 = null;

        GuardCommandParser.expr_rel_return e3 = null;


        // GuardCommand.g:241:2: ( (e1= expr_rel b= eqa e2= expr_rel ) (b= eqa e3= expr_rel )* )
        // GuardCommand.g:241:2: (e1= expr_rel b= eqa e2= expr_rel ) (b= eqa e3= expr_rel )*
        {
        // GuardCommand.g:241:2: (e1= expr_rel b= eqa e2= expr_rel )
        // GuardCommand.g:241:3: e1= expr_rel b= eqa e2= expr_rel
        {
        pushFollow(FOLLOW_expr_rel_in_synpred13_GuardCommand945);
        e1=expr_rel();

        state._fsp--;
        if (state.failed) return ;
        pushFollow(FOLLOW_eqa_in_synpred13_GuardCommand949);
        b=eqa();

        state._fsp--;
        if (state.failed) return ;
        pushFollow(FOLLOW_expr_rel_in_synpred13_GuardCommand953);
        e2=expr_rel();

        state._fsp--;
        if (state.failed) return ;

        }

        // GuardCommand.g:246:2: (b= eqa e3= expr_rel )*
        loop27:
        do {
            int alt27=2;
            int LA27_0 = input.LA(1);

            if ( ((LA27_0>=EQ && LA27_0<=NEQ)) ) {
                alt27=1;
            }


            switch (alt27) {
        	case 1 :
        	    // GuardCommand.g:246:3: b= eqa e3= expr_rel
        	    {
        	    pushFollow(FOLLOW_eqa_in_synpred13_GuardCommand966);
        	    b=eqa();

        	    state._fsp--;
        	    if (state.failed) return ;
        	    pushFollow(FOLLOW_expr_rel_in_synpred13_GuardCommand970);
        	    e3=expr_rel();

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
    // $ANTLR end synpred13_GuardCommand

    // $ANTLR start synpred15_GuardCommand
    public final void synpred15_GuardCommand_fragment() throws RecognitionException {   
        GuardCommandParser.expr_term_return e1 = null;

        GuardCommandParser.rel_return b = null;

        GuardCommandParser.expr_term_return e2 = null;

        GuardCommandParser.expr_term_return e3 = null;


        // GuardCommand.g:256:2: ( (e1= expr_term b= rel e2= expr_term ) (b= rel e3= expr_term )* )
        // GuardCommand.g:256:2: (e1= expr_term b= rel e2= expr_term ) (b= rel e3= expr_term )*
        {
        // GuardCommand.g:256:2: (e1= expr_term b= rel e2= expr_term )
        // GuardCommand.g:256:3: e1= expr_term b= rel e2= expr_term
        {
        pushFollow(FOLLOW_expr_term_in_synpred15_GuardCommand1008);
        e1=expr_term();

        state._fsp--;
        if (state.failed) return ;
        pushFollow(FOLLOW_rel_in_synpred15_GuardCommand1012);
        b=rel();

        state._fsp--;
        if (state.failed) return ;
        pushFollow(FOLLOW_expr_term_in_synpred15_GuardCommand1016);
        e2=expr_term();

        state._fsp--;
        if (state.failed) return ;

        }

        // GuardCommand.g:261:2: (b= rel e3= expr_term )*
        loop28:
        do {
            int alt28=2;
            int LA28_0 = input.LA(1);

            if ( ((LA28_0>=GREATER_THAN && LA28_0<=LESS_EQ)) ) {
                alt28=1;
            }


            switch (alt28) {
        	case 1 :
        	    // GuardCommand.g:261:3: b= rel e3= expr_term
        	    {
        	    pushFollow(FOLLOW_rel_in_synpred15_GuardCommand1029);
        	    b=rel();

        	    state._fsp--;
        	    if (state.failed) return ;
        	    pushFollow(FOLLOW_expr_term_in_synpred15_GuardCommand1033);
        	    e3=expr_term();

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
    // $ANTLR end synpred15_GuardCommand

    // $ANTLR start synpred17_GuardCommand
    public final void synpred17_GuardCommand_fragment() throws RecognitionException {   
        GuardCommandParser.expr_factor_return e1 = null;

        GuardCommandParser.term_return b = null;

        GuardCommandParser.expr_factor_return e2 = null;

        GuardCommandParser.expr_factor_return e3 = null;


        // GuardCommand.g:271:2: ( (e1= expr_factor b= term e2= expr_factor ) (b= term e3= expr_factor )* )
        // GuardCommand.g:271:2: (e1= expr_factor b= term e2= expr_factor ) (b= term e3= expr_factor )*
        {
        // GuardCommand.g:271:2: (e1= expr_factor b= term e2= expr_factor )
        // GuardCommand.g:271:3: e1= expr_factor b= term e2= expr_factor
        {
        pushFollow(FOLLOW_expr_factor_in_synpred17_GuardCommand1072);
        e1=expr_factor();

        state._fsp--;
        if (state.failed) return ;
        pushFollow(FOLLOW_term_in_synpred17_GuardCommand1076);
        b=term();

        state._fsp--;
        if (state.failed) return ;
        pushFollow(FOLLOW_expr_factor_in_synpred17_GuardCommand1080);
        e2=expr_factor();

        state._fsp--;
        if (state.failed) return ;

        }

        // GuardCommand.g:276:2: (b= term e3= expr_factor )*
        loop29:
        do {
            int alt29=2;
            int LA29_0 = input.LA(1);

            if ( ((LA29_0>=PLUS && LA29_0<=MINUS)) ) {
                alt29=1;
            }


            switch (alt29) {
        	case 1 :
        	    // GuardCommand.g:276:3: b= term e3= expr_factor
        	    {
        	    pushFollow(FOLLOW_term_in_synpred17_GuardCommand1093);
        	    b=term();

        	    state._fsp--;
        	    if (state.failed) return ;
        	    pushFollow(FOLLOW_expr_factor_in_synpred17_GuardCommand1097);
        	    e3=expr_factor();

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
    // $ANTLR end synpred17_GuardCommand

    // $ANTLR start synpred19_GuardCommand
    public final void synpred19_GuardCommand_fragment() throws RecognitionException {   
        GuardCommandParser.expr_unary_return e1 = null;

        GuardCommandParser.factor_return b = null;

        GuardCommandParser.expr_unary_return e2 = null;

        GuardCommandParser.expr_unary_return e3 = null;


        // GuardCommand.g:286:2: ( (e1= expr_unary b= factor e2= expr_unary ) (b= factor e3= expr_unary )* )
        // GuardCommand.g:286:2: (e1= expr_unary b= factor e2= expr_unary ) (b= factor e3= expr_unary )*
        {
        // GuardCommand.g:286:2: (e1= expr_unary b= factor e2= expr_unary )
        // GuardCommand.g:286:3: e1= expr_unary b= factor e2= expr_unary
        {
        pushFollow(FOLLOW_expr_unary_in_synpred19_GuardCommand1136);
        e1=expr_unary();

        state._fsp--;
        if (state.failed) return ;
        pushFollow(FOLLOW_factor_in_synpred19_GuardCommand1140);
        b=factor();

        state._fsp--;
        if (state.failed) return ;
        pushFollow(FOLLOW_expr_unary_in_synpred19_GuardCommand1144);
        e2=expr_unary();

        state._fsp--;
        if (state.failed) return ;

        }

        // GuardCommand.g:291:2: (b= factor e3= expr_unary )*
        loop30:
        do {
            int alt30=2;
            int LA30_0 = input.LA(1);

            if ( ((LA30_0>=MUL && LA30_0<=DIV)) ) {
                alt30=1;
            }


            switch (alt30) {
        	case 1 :
        	    // GuardCommand.g:291:3: b= factor e3= expr_unary
        	    {
        	    pushFollow(FOLLOW_factor_in_synpred19_GuardCommand1157);
        	    b=factor();

        	    state._fsp--;
        	    if (state.failed) return ;
        	    pushFollow(FOLLOW_expr_unary_in_synpred19_GuardCommand1161);
        	    e3=expr_unary();

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
    // $ANTLR end synpred19_GuardCommand

    // $ANTLR start synpred33_GuardCommand
    public final void synpred33_GuardCommand_fragment() throws RecognitionException {   
        GuardCommandParser.command_return i = null;


        // GuardCommand.g:374:3: ( SEMI i= command )
        // GuardCommand.g:374:3: SEMI i= command
        {
        match(input,SEMI,FOLLOW_SEMI_in_synpred33_GuardCommand1464); if (state.failed) return ;
        pushFollow(FOLLOW_command_in_synpred33_GuardCommand1468);
        i=command();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred33_GuardCommand

    // $ANTLR start synpred34_GuardCommand
    public final void synpred34_GuardCommand_fragment() throws RecognitionException {   
        GuardCommandParser.guarded_cmd_return gc = null;


        // GuardCommand.g:497:3: ( GUARD gc= guarded_cmd )
        // GuardCommand.g:497:3: GUARD gc= guarded_cmd
        {
        match(input,GUARD,FOLLOW_GUARD_in_synpred34_GuardCommand1686); if (state.failed) return ;
        pushFollow(FOLLOW_guarded_cmd_in_synpred34_GuardCommand1690);
        gc=guarded_cmd();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred34_GuardCommand

    // Delegated rules

    public final boolean synpred33_GuardCommand() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred33_GuardCommand_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred11_GuardCommand() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred11_GuardCommand_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred15_GuardCommand() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred15_GuardCommand_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred9_GuardCommand() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred9_GuardCommand_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred17_GuardCommand() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred17_GuardCommand_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred34_GuardCommand() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred34_GuardCommand_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred19_GuardCommand() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred19_GuardCommand_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred13_GuardCommand() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred13_GuardCommand_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }


    protected DFA7 dfa7 = new DFA7(this);
    protected DFA9 dfa9 = new DFA9(this);
    protected DFA11 dfa11 = new DFA11(this);
    protected DFA13 dfa13 = new DFA13(this);
    protected DFA15 dfa15 = new DFA15(this);
    protected DFA17 dfa17 = new DFA17(this);
    static final String DFA7_eotS =
        "\12\uffff";
    static final String DFA7_eofS =
        "\12\uffff";
    static final String DFA7_minS =
        "\1\17\7\0\2\uffff";
    static final String DFA7_maxS =
        "\1\47\7\0\2\uffff";
    static final String DFA7_acceptS =
        "\10\uffff\1\1\1\2";
    static final String DFA7_specialS =
        "\1\uffff\1\0\1\1\1\2\1\3\1\4\1\5\1\6\2\uffff}>";
    static final String[] DFA7_transitionS = {
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

    static final short[] DFA7_eot = DFA.unpackEncodedString(DFA7_eotS);
    static final short[] DFA7_eof = DFA.unpackEncodedString(DFA7_eofS);
    static final char[] DFA7_min = DFA.unpackEncodedStringToUnsignedChars(DFA7_minS);
    static final char[] DFA7_max = DFA.unpackEncodedStringToUnsignedChars(DFA7_maxS);
    static final short[] DFA7_accept = DFA.unpackEncodedString(DFA7_acceptS);
    static final short[] DFA7_special = DFA.unpackEncodedString(DFA7_specialS);
    static final short[][] DFA7_transition;

    static {
        int numStates = DFA7_transitionS.length;
        DFA7_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA7_transition[i] = DFA.unpackEncodedString(DFA7_transitionS[i]);
        }
    }

    class DFA7 extends DFA {

        public DFA7(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 7;
            this.eot = DFA7_eot;
            this.eof = DFA7_eof;
            this.min = DFA7_min;
            this.max = DFA7_max;
            this.accept = DFA7_accept;
            this.special = DFA7_special;
            this.transition = DFA7_transition;
        }
        public String getDescription() {
            return "208:1: expr_or returns [List<Statement> statList, Value val] : ( (e1= expr_and b= or e2= expr_and ) (b= or e3= expr_and )* | (e4= expr_and ) );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA7_1 = input.LA(1);

                         
                        int index7_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred9_GuardCommand()) ) {s = 8;}

                        else if ( (true) ) {s = 9;}

                         
                        input.seek(index7_1);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA7_2 = input.LA(1);

                         
                        int index7_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred9_GuardCommand()) ) {s = 8;}

                        else if ( (true) ) {s = 9;}

                         
                        input.seek(index7_2);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA7_3 = input.LA(1);

                         
                        int index7_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred9_GuardCommand()) ) {s = 8;}

                        else if ( (true) ) {s = 9;}

                         
                        input.seek(index7_3);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA7_4 = input.LA(1);

                         
                        int index7_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred9_GuardCommand()) ) {s = 8;}

                        else if ( (true) ) {s = 9;}

                         
                        input.seek(index7_4);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA7_5 = input.LA(1);

                         
                        int index7_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred9_GuardCommand()) ) {s = 8;}

                        else if ( (true) ) {s = 9;}

                         
                        input.seek(index7_5);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA7_6 = input.LA(1);

                         
                        int index7_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred9_GuardCommand()) ) {s = 8;}

                        else if ( (true) ) {s = 9;}

                         
                        input.seek(index7_6);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA7_7 = input.LA(1);

                         
                        int index7_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred9_GuardCommand()) ) {s = 8;}

                        else if ( (true) ) {s = 9;}

                         
                        input.seek(index7_7);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 7, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA9_eotS =
        "\12\uffff";
    static final String DFA9_eofS =
        "\12\uffff";
    static final String DFA9_minS =
        "\1\17\7\0\2\uffff";
    static final String DFA9_maxS =
        "\1\47\7\0\2\uffff";
    static final String DFA9_acceptS =
        "\10\uffff\1\1\1\2";
    static final String DFA9_specialS =
        "\1\uffff\1\0\1\1\1\2\1\3\1\4\1\5\1\6\2\uffff}>";
    static final String[] DFA9_transitionS = {
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

    static final short[] DFA9_eot = DFA.unpackEncodedString(DFA9_eotS);
    static final short[] DFA9_eof = DFA.unpackEncodedString(DFA9_eofS);
    static final char[] DFA9_min = DFA.unpackEncodedStringToUnsignedChars(DFA9_minS);
    static final char[] DFA9_max = DFA.unpackEncodedStringToUnsignedChars(DFA9_maxS);
    static final short[] DFA9_accept = DFA.unpackEncodedString(DFA9_acceptS);
    static final short[] DFA9_special = DFA.unpackEncodedString(DFA9_specialS);
    static final short[][] DFA9_transition;

    static {
        int numStates = DFA9_transitionS.length;
        DFA9_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA9_transition[i] = DFA.unpackEncodedString(DFA9_transitionS[i]);
        }
    }

    class DFA9 extends DFA {

        public DFA9(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 9;
            this.eot = DFA9_eot;
            this.eof = DFA9_eof;
            this.min = DFA9_min;
            this.max = DFA9_max;
            this.accept = DFA9_accept;
            this.special = DFA9_special;
            this.transition = DFA9_transition;
        }
        public String getDescription() {
            return "223:1: expr_and returns [List<Statement> statList, Value val] : ( (e1= expr_eqa b= and e2= expr_eqa ) (b= and e3= expr_eqa )* | (e4= expr_eqa ) );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA9_1 = input.LA(1);

                         
                        int index9_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred11_GuardCommand()) ) {s = 8;}

                        else if ( (true) ) {s = 9;}

                         
                        input.seek(index9_1);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA9_2 = input.LA(1);

                         
                        int index9_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred11_GuardCommand()) ) {s = 8;}

                        else if ( (true) ) {s = 9;}

                         
                        input.seek(index9_2);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA9_3 = input.LA(1);

                         
                        int index9_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred11_GuardCommand()) ) {s = 8;}

                        else if ( (true) ) {s = 9;}

                         
                        input.seek(index9_3);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA9_4 = input.LA(1);

                         
                        int index9_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred11_GuardCommand()) ) {s = 8;}

                        else if ( (true) ) {s = 9;}

                         
                        input.seek(index9_4);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA9_5 = input.LA(1);

                         
                        int index9_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred11_GuardCommand()) ) {s = 8;}

                        else if ( (true) ) {s = 9;}

                         
                        input.seek(index9_5);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA9_6 = input.LA(1);

                         
                        int index9_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred11_GuardCommand()) ) {s = 8;}

                        else if ( (true) ) {s = 9;}

                         
                        input.seek(index9_6);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA9_7 = input.LA(1);

                         
                        int index9_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred11_GuardCommand()) ) {s = 8;}

                        else if ( (true) ) {s = 9;}

                         
                        input.seek(index9_7);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 9, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA11_eotS =
        "\12\uffff";
    static final String DFA11_eofS =
        "\12\uffff";
    static final String DFA11_minS =
        "\1\17\7\0\2\uffff";
    static final String DFA11_maxS =
        "\1\47\7\0\2\uffff";
    static final String DFA11_acceptS =
        "\10\uffff\1\1\1\2";
    static final String DFA11_specialS =
        "\1\uffff\1\0\1\1\1\2\1\3\1\4\1\5\1\6\2\uffff}>";
    static final String[] DFA11_transitionS = {
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

    static final short[] DFA11_eot = DFA.unpackEncodedString(DFA11_eotS);
    static final short[] DFA11_eof = DFA.unpackEncodedString(DFA11_eofS);
    static final char[] DFA11_min = DFA.unpackEncodedStringToUnsignedChars(DFA11_minS);
    static final char[] DFA11_max = DFA.unpackEncodedStringToUnsignedChars(DFA11_maxS);
    static final short[] DFA11_accept = DFA.unpackEncodedString(DFA11_acceptS);
    static final short[] DFA11_special = DFA.unpackEncodedString(DFA11_specialS);
    static final short[][] DFA11_transition;

    static {
        int numStates = DFA11_transitionS.length;
        DFA11_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA11_transition[i] = DFA.unpackEncodedString(DFA11_transitionS[i]);
        }
    }

    class DFA11 extends DFA {

        public DFA11(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 11;
            this.eot = DFA11_eot;
            this.eof = DFA11_eof;
            this.min = DFA11_min;
            this.max = DFA11_max;
            this.accept = DFA11_accept;
            this.special = DFA11_special;
            this.transition = DFA11_transition;
        }
        public String getDescription() {
            return "239:1: expr_eqa returns [List<Statement> statList, Value val] : ( (e1= expr_rel b= eqa e2= expr_rel ) (b= eqa e3= expr_rel )* | (e4= expr_rel ) );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA11_1 = input.LA(1);

                         
                        int index11_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred13_GuardCommand()) ) {s = 8;}

                        else if ( (true) ) {s = 9;}

                         
                        input.seek(index11_1);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA11_2 = input.LA(1);

                         
                        int index11_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred13_GuardCommand()) ) {s = 8;}

                        else if ( (true) ) {s = 9;}

                         
                        input.seek(index11_2);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA11_3 = input.LA(1);

                         
                        int index11_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred13_GuardCommand()) ) {s = 8;}

                        else if ( (true) ) {s = 9;}

                         
                        input.seek(index11_3);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA11_4 = input.LA(1);

                         
                        int index11_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred13_GuardCommand()) ) {s = 8;}

                        else if ( (true) ) {s = 9;}

                         
                        input.seek(index11_4);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA11_5 = input.LA(1);

                         
                        int index11_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred13_GuardCommand()) ) {s = 8;}

                        else if ( (true) ) {s = 9;}

                         
                        input.seek(index11_5);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA11_6 = input.LA(1);

                         
                        int index11_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred13_GuardCommand()) ) {s = 8;}

                        else if ( (true) ) {s = 9;}

                         
                        input.seek(index11_6);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA11_7 = input.LA(1);

                         
                        int index11_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred13_GuardCommand()) ) {s = 8;}

                        else if ( (true) ) {s = 9;}

                         
                        input.seek(index11_7);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 11, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA13_eotS =
        "\12\uffff";
    static final String DFA13_eofS =
        "\12\uffff";
    static final String DFA13_minS =
        "\1\17\7\0\2\uffff";
    static final String DFA13_maxS =
        "\1\47\7\0\2\uffff";
    static final String DFA13_acceptS =
        "\10\uffff\1\1\1\2";
    static final String DFA13_specialS =
        "\1\uffff\1\0\1\1\1\2\1\3\1\4\1\5\1\6\2\uffff}>";
    static final String[] DFA13_transitionS = {
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

    static final short[] DFA13_eot = DFA.unpackEncodedString(DFA13_eotS);
    static final short[] DFA13_eof = DFA.unpackEncodedString(DFA13_eofS);
    static final char[] DFA13_min = DFA.unpackEncodedStringToUnsignedChars(DFA13_minS);
    static final char[] DFA13_max = DFA.unpackEncodedStringToUnsignedChars(DFA13_maxS);
    static final short[] DFA13_accept = DFA.unpackEncodedString(DFA13_acceptS);
    static final short[] DFA13_special = DFA.unpackEncodedString(DFA13_specialS);
    static final short[][] DFA13_transition;

    static {
        int numStates = DFA13_transitionS.length;
        DFA13_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA13_transition[i] = DFA.unpackEncodedString(DFA13_transitionS[i]);
        }
    }

    class DFA13 extends DFA {

        public DFA13(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 13;
            this.eot = DFA13_eot;
            this.eof = DFA13_eof;
            this.min = DFA13_min;
            this.max = DFA13_max;
            this.accept = DFA13_accept;
            this.special = DFA13_special;
            this.transition = DFA13_transition;
        }
        public String getDescription() {
            return "254:1: expr_rel returns [List<Statement> statList, Value val] : ( (e1= expr_term b= rel e2= expr_term ) (b= rel e3= expr_term )* | (e4= expr_term ) );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA13_1 = input.LA(1);

                         
                        int index13_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred15_GuardCommand()) ) {s = 8;}

                        else if ( (true) ) {s = 9;}

                         
                        input.seek(index13_1);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA13_2 = input.LA(1);

                         
                        int index13_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred15_GuardCommand()) ) {s = 8;}

                        else if ( (true) ) {s = 9;}

                         
                        input.seek(index13_2);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA13_3 = input.LA(1);

                         
                        int index13_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred15_GuardCommand()) ) {s = 8;}

                        else if ( (true) ) {s = 9;}

                         
                        input.seek(index13_3);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA13_4 = input.LA(1);

                         
                        int index13_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred15_GuardCommand()) ) {s = 8;}

                        else if ( (true) ) {s = 9;}

                         
                        input.seek(index13_4);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA13_5 = input.LA(1);

                         
                        int index13_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred15_GuardCommand()) ) {s = 8;}

                        else if ( (true) ) {s = 9;}

                         
                        input.seek(index13_5);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA13_6 = input.LA(1);

                         
                        int index13_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred15_GuardCommand()) ) {s = 8;}

                        else if ( (true) ) {s = 9;}

                         
                        input.seek(index13_6);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA13_7 = input.LA(1);

                         
                        int index13_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred15_GuardCommand()) ) {s = 8;}

                        else if ( (true) ) {s = 9;}

                         
                        input.seek(index13_7);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 13, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA15_eotS =
        "\12\uffff";
    static final String DFA15_eofS =
        "\12\uffff";
    static final String DFA15_minS =
        "\1\17\7\0\2\uffff";
    static final String DFA15_maxS =
        "\1\47\7\0\2\uffff";
    static final String DFA15_acceptS =
        "\10\uffff\1\1\1\2";
    static final String DFA15_specialS =
        "\1\uffff\1\0\1\1\1\2\1\3\1\4\1\5\1\6\2\uffff}>";
    static final String[] DFA15_transitionS = {
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

    static final short[] DFA15_eot = DFA.unpackEncodedString(DFA15_eotS);
    static final short[] DFA15_eof = DFA.unpackEncodedString(DFA15_eofS);
    static final char[] DFA15_min = DFA.unpackEncodedStringToUnsignedChars(DFA15_minS);
    static final char[] DFA15_max = DFA.unpackEncodedStringToUnsignedChars(DFA15_maxS);
    static final short[] DFA15_accept = DFA.unpackEncodedString(DFA15_acceptS);
    static final short[] DFA15_special = DFA.unpackEncodedString(DFA15_specialS);
    static final short[][] DFA15_transition;

    static {
        int numStates = DFA15_transitionS.length;
        DFA15_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA15_transition[i] = DFA.unpackEncodedString(DFA15_transitionS[i]);
        }
    }

    class DFA15 extends DFA {

        public DFA15(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 15;
            this.eot = DFA15_eot;
            this.eof = DFA15_eof;
            this.min = DFA15_min;
            this.max = DFA15_max;
            this.accept = DFA15_accept;
            this.special = DFA15_special;
            this.transition = DFA15_transition;
        }
        public String getDescription() {
            return "269:1: expr_term returns [List<Statement> statList, Value val] : ( (e1= expr_factor b= term e2= expr_factor ) (b= term e3= expr_factor )* | (e4= expr_factor ) );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA15_1 = input.LA(1);

                         
                        int index15_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred17_GuardCommand()) ) {s = 8;}

                        else if ( (true) ) {s = 9;}

                         
                        input.seek(index15_1);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA15_2 = input.LA(1);

                         
                        int index15_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred17_GuardCommand()) ) {s = 8;}

                        else if ( (true) ) {s = 9;}

                         
                        input.seek(index15_2);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA15_3 = input.LA(1);

                         
                        int index15_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred17_GuardCommand()) ) {s = 8;}

                        else if ( (true) ) {s = 9;}

                         
                        input.seek(index15_3);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA15_4 = input.LA(1);

                         
                        int index15_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred17_GuardCommand()) ) {s = 8;}

                        else if ( (true) ) {s = 9;}

                         
                        input.seek(index15_4);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA15_5 = input.LA(1);

                         
                        int index15_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred17_GuardCommand()) ) {s = 8;}

                        else if ( (true) ) {s = 9;}

                         
                        input.seek(index15_5);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA15_6 = input.LA(1);

                         
                        int index15_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred17_GuardCommand()) ) {s = 8;}

                        else if ( (true) ) {s = 9;}

                         
                        input.seek(index15_6);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA15_7 = input.LA(1);

                         
                        int index15_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred17_GuardCommand()) ) {s = 8;}

                        else if ( (true) ) {s = 9;}

                         
                        input.seek(index15_7);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 15, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA17_eotS =
        "\12\uffff";
    static final String DFA17_eofS =
        "\12\uffff";
    static final String DFA17_minS =
        "\1\17\7\0\2\uffff";
    static final String DFA17_maxS =
        "\1\47\7\0\2\uffff";
    static final String DFA17_acceptS =
        "\10\uffff\1\1\1\2";
    static final String DFA17_specialS =
        "\1\uffff\1\0\1\1\1\2\1\3\1\4\1\5\1\6\2\uffff}>";
    static final String[] DFA17_transitionS = {
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

    static final short[] DFA17_eot = DFA.unpackEncodedString(DFA17_eotS);
    static final short[] DFA17_eof = DFA.unpackEncodedString(DFA17_eofS);
    static final char[] DFA17_min = DFA.unpackEncodedStringToUnsignedChars(DFA17_minS);
    static final char[] DFA17_max = DFA.unpackEncodedStringToUnsignedChars(DFA17_maxS);
    static final short[] DFA17_accept = DFA.unpackEncodedString(DFA17_acceptS);
    static final short[] DFA17_special = DFA.unpackEncodedString(DFA17_specialS);
    static final short[][] DFA17_transition;

    static {
        int numStates = DFA17_transitionS.length;
        DFA17_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA17_transition[i] = DFA.unpackEncodedString(DFA17_transitionS[i]);
        }
    }

    class DFA17 extends DFA {

        public DFA17(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 17;
            this.eot = DFA17_eot;
            this.eof = DFA17_eof;
            this.min = DFA17_min;
            this.max = DFA17_max;
            this.accept = DFA17_accept;
            this.special = DFA17_special;
            this.transition = DFA17_transition;
        }
        public String getDescription() {
            return "284:1: expr_factor returns [List<Statement> statList, Value val] : ( (e1= expr_unary b= factor e2= expr_unary ) (b= factor e3= expr_unary )* | (e4= expr_unary ) );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA17_1 = input.LA(1);

                         
                        int index17_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred19_GuardCommand()) ) {s = 8;}

                        else if ( (true) ) {s = 9;}

                         
                        input.seek(index17_1);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA17_2 = input.LA(1);

                         
                        int index17_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred19_GuardCommand()) ) {s = 8;}

                        else if ( (true) ) {s = 9;}

                         
                        input.seek(index17_2);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA17_3 = input.LA(1);

                         
                        int index17_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred19_GuardCommand()) ) {s = 8;}

                        else if ( (true) ) {s = 9;}

                         
                        input.seek(index17_3);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA17_4 = input.LA(1);

                         
                        int index17_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred19_GuardCommand()) ) {s = 8;}

                        else if ( (true) ) {s = 9;}

                         
                        input.seek(index17_4);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA17_5 = input.LA(1);

                         
                        int index17_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred19_GuardCommand()) ) {s = 8;}

                        else if ( (true) ) {s = 9;}

                         
                        input.seek(index17_5);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA17_6 = input.LA(1);

                         
                        int index17_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred19_GuardCommand()) ) {s = 8;}

                        else if ( (true) ) {s = 9;}

                         
                        input.seek(index17_6);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA17_7 = input.LA(1);

                         
                        int index17_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred19_GuardCommand()) ) {s = 8;}

                        else if ( (true) ) {s = 9;}

                         
                        input.seek(index17_7);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 17, _s, input);
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
    public static final BitSet FOLLOW_expr_or_in_expression795 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expr_and_in_expr_or816 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_or_in_expr_or820 = new BitSet(new long[]{0x000000F000148000L});
    public static final BitSet FOLLOW_expr_and_in_expr_or824 = new BitSet(new long[]{0x0000000000000022L});
    public static final BitSet FOLLOW_or_in_expr_or837 = new BitSet(new long[]{0x000000F000148000L});
    public static final BitSet FOLLOW_expr_and_in_expr_or841 = new BitSet(new long[]{0x0000000000000022L});
    public static final BitSet FOLLOW_expr_and_in_expr_or857 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expr_eqa_in_expr_and880 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_and_in_expr_and884 = new BitSet(new long[]{0x000000F000148000L});
    public static final BitSet FOLLOW_expr_eqa_in_expr_and888 = new BitSet(new long[]{0x0000000000000012L});
    public static final BitSet FOLLOW_and_in_expr_and901 = new BitSet(new long[]{0x000000F000148000L});
    public static final BitSet FOLLOW_expr_eqa_in_expr_and905 = new BitSet(new long[]{0x0000000000000012L});
    public static final BitSet FOLLOW_expr_eqa_in_expr_and921 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expr_rel_in_expr_eqa945 = new BitSet(new long[]{0x0000000000003000L});
    public static final BitSet FOLLOW_eqa_in_expr_eqa949 = new BitSet(new long[]{0x000000F000148000L});
    public static final BitSet FOLLOW_expr_rel_in_expr_eqa953 = new BitSet(new long[]{0x0000000000003002L});
    public static final BitSet FOLLOW_eqa_in_expr_eqa966 = new BitSet(new long[]{0x000000F000148000L});
    public static final BitSet FOLLOW_expr_rel_in_expr_eqa970 = new BitSet(new long[]{0x0000000000003002L});
    public static final BitSet FOLLOW_expr_rel_in_expr_eqa986 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expr_term_in_expr_rel1008 = new BitSet(new long[]{0x0000000000000F00L});
    public static final BitSet FOLLOW_rel_in_expr_rel1012 = new BitSet(new long[]{0x000000F000148000L});
    public static final BitSet FOLLOW_expr_term_in_expr_rel1016 = new BitSet(new long[]{0x0000000000000F02L});
    public static final BitSet FOLLOW_rel_in_expr_rel1029 = new BitSet(new long[]{0x000000F000148000L});
    public static final BitSet FOLLOW_expr_term_in_expr_rel1033 = new BitSet(new long[]{0x0000000000000F02L});
    public static final BitSet FOLLOW_expr_term_in_expr_rel1049 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expr_factor_in_expr_term1072 = new BitSet(new long[]{0x000000000000C000L});
    public static final BitSet FOLLOW_term_in_expr_term1076 = new BitSet(new long[]{0x000000F000148000L});
    public static final BitSet FOLLOW_expr_factor_in_expr_term1080 = new BitSet(new long[]{0x000000000000C002L});
    public static final BitSet FOLLOW_term_in_expr_term1093 = new BitSet(new long[]{0x000000F000148000L});
    public static final BitSet FOLLOW_expr_factor_in_expr_term1097 = new BitSet(new long[]{0x000000000000C002L});
    public static final BitSet FOLLOW_expr_factor_in_expr_term1113 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expr_unary_in_expr_factor1136 = new BitSet(new long[]{0x0000000000030000L});
    public static final BitSet FOLLOW_factor_in_expr_factor1140 = new BitSet(new long[]{0x000000F000148000L});
    public static final BitSet FOLLOW_expr_unary_in_expr_factor1144 = new BitSet(new long[]{0x0000000000030002L});
    public static final BitSet FOLLOW_factor_in_expr_factor1157 = new BitSet(new long[]{0x000000F000148000L});
    public static final BitSet FOLLOW_expr_unary_in_expr_factor1161 = new BitSet(new long[]{0x0000000000030002L});
    public static final BitSet FOLLOW_expr_unary_in_expr_factor1177 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_unary_operator_in_expr_unary1197 = new BitSet(new long[]{0x000000F000148000L});
    public static final BitSet FOLLOW_expr_unary_in_expr_unary1201 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_lite_in_expr_unary1212 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_lite_paren_in_expr_unary1221 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_lite_in_literal1240 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_lite_paren_in_literal1249 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INTEGER_LITERAL_in_lite1269 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TRUE_in_lite1280 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FALSE_in_lite1289 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENTIFIER_in_lite1298 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LPAREN_in_lite_paren1315 = new BitSet(new long[]{0x000000F000148000L});
    public static final BitSet FOLLOW_expression_in_lite_paren1319 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_RPAREN_in_lite_paren1321 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_assignment_cmd_in_command1358 = new BitSet(new long[]{0x0000000000000082L});
    public static final BitSet FOLLOW_skip_cmd_in_command1369 = new BitSet(new long[]{0x0000000000000082L});
    public static final BitSet FOLLOW_abort_cmd_in_command1381 = new BitSet(new long[]{0x0000000000000082L});
    public static final BitSet FOLLOW_read_cmd_in_command1393 = new BitSet(new long[]{0x0000000000000082L});
    public static final BitSet FOLLOW_write_cmd_in_command1405 = new BitSet(new long[]{0x0000000000000082L});
    public static final BitSet FOLLOW_LCURLY_in_command1417 = new BitSet(new long[]{0x00000083C5400000L});
    public static final BitSet FOLLOW_command_in_command1421 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_RCURLY_in_command1423 = new BitSet(new long[]{0x0000000000000082L});
    public static final BitSet FOLLOW_if_cmd_in_command1434 = new BitSet(new long[]{0x0000000000000082L});
    public static final BitSet FOLLOW_do_cmd_in_command1448 = new BitSet(new long[]{0x0000000000000082L});
    public static final BitSet FOLLOW_SEMI_in_command1464 = new BitSet(new long[]{0x00000083C5400000L});
    public static final BitSet FOLLOW_command_in_command1468 = new BitSet(new long[]{0x0000000000000082L});
    public static final BitSet FOLLOW_IDENTIFIER_in_assignment_cmd1493 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_ASSIGN_in_assignment_cmd1497 = new BitSet(new long[]{0x000000F000148000L});
    public static final BitSet FOLLOW_expression_in_assignment_cmd1501 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_SKIP_in_skip_cmd1522 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ABORT_in_abort_cmd1543 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_READ_in_read_cmd1564 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_IDENTIFIER_in_read_cmd1568 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_WRITE_in_write_cmd1589 = new BitSet(new long[]{0x000000F000148000L});
    public static final BitSet FOLLOW_expression_in_write_cmd1593 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IF_in_if_cmd1614 = new BitSet(new long[]{0x000000F000148000L});
    public static final BitSet FOLLOW_guarded_cmd_in_if_cmd1618 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_FI_in_if_cmd1620 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DO_in_do_cmd1641 = new BitSet(new long[]{0x000000F000148000L});
    public static final BitSet FOLLOW_guarded_cmd_in_do_cmd1645 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_OD_in_do_cmd1647 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_guarded_cmd1673 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_ARROW_in_guarded_cmd1675 = new BitSet(new long[]{0x00000083C5400000L});
    public static final BitSet FOLLOW_command_in_guarded_cmd1679 = new BitSet(new long[]{0x0000000010000002L});
    public static final BitSet FOLLOW_GUARD_in_guarded_cmd1686 = new BitSet(new long[]{0x000000F000148000L});
    public static final BitSet FOLLOW_guarded_cmd_in_guarded_cmd1690 = new BitSet(new long[]{0x0000000010000002L});
    public static final BitSet FOLLOW_MODULE_in_program1713 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_IDENTIFIER_in_program1717 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_COLON_in_program1719 = new BitSet(new long[]{0x00000083C5400000L});
    public static final BitSet FOLLOW_command_in_program1723 = new BitSet(new long[]{0x0000000800000000L});
    public static final BitSet FOLLOW_END_in_program1725 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_44_in_annotation1747 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_IDENTIFIER_in_annotation1752 = new BitSet(new long[]{0x0000000000001000L});
    public static final BitSet FOLLOW_EQ_in_annotation1754 = new BitSet(new long[]{0x0000200000000000L});
    public static final BitSet FOLLOW_45_in_annotation1756 = new BitSet(new long[]{0x00003FFFFFFFFFF0L});
    public static final BitSet FOLLOW_set_in_annotation1761 = new BitSet(new long[]{0x00003FFFFFFFFFF0L});
    public static final BitSet FOLLOW_45_in_annotation1768 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expr_and_in_synpred9_GuardCommand816 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_or_in_synpred9_GuardCommand820 = new BitSet(new long[]{0x000000F000148000L});
    public static final BitSet FOLLOW_expr_and_in_synpred9_GuardCommand824 = new BitSet(new long[]{0x0000000000000022L});
    public static final BitSet FOLLOW_or_in_synpred9_GuardCommand837 = new BitSet(new long[]{0x000000F000148000L});
    public static final BitSet FOLLOW_expr_and_in_synpred9_GuardCommand841 = new BitSet(new long[]{0x0000000000000022L});
    public static final BitSet FOLLOW_expr_eqa_in_synpred11_GuardCommand880 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_and_in_synpred11_GuardCommand884 = new BitSet(new long[]{0x000000F000148000L});
    public static final BitSet FOLLOW_expr_eqa_in_synpred11_GuardCommand888 = new BitSet(new long[]{0x0000000000000012L});
    public static final BitSet FOLLOW_and_in_synpred11_GuardCommand901 = new BitSet(new long[]{0x000000F000148000L});
    public static final BitSet FOLLOW_expr_eqa_in_synpred11_GuardCommand905 = new BitSet(new long[]{0x0000000000000012L});
    public static final BitSet FOLLOW_expr_rel_in_synpred13_GuardCommand945 = new BitSet(new long[]{0x0000000000003000L});
    public static final BitSet FOLLOW_eqa_in_synpred13_GuardCommand949 = new BitSet(new long[]{0x000000F000148000L});
    public static final BitSet FOLLOW_expr_rel_in_synpred13_GuardCommand953 = new BitSet(new long[]{0x0000000000003002L});
    public static final BitSet FOLLOW_eqa_in_synpred13_GuardCommand966 = new BitSet(new long[]{0x000000F000148000L});
    public static final BitSet FOLLOW_expr_rel_in_synpred13_GuardCommand970 = new BitSet(new long[]{0x0000000000003002L});
    public static final BitSet FOLLOW_expr_term_in_synpred15_GuardCommand1008 = new BitSet(new long[]{0x0000000000000F00L});
    public static final BitSet FOLLOW_rel_in_synpred15_GuardCommand1012 = new BitSet(new long[]{0x000000F000148000L});
    public static final BitSet FOLLOW_expr_term_in_synpred15_GuardCommand1016 = new BitSet(new long[]{0x0000000000000F02L});
    public static final BitSet FOLLOW_rel_in_synpred15_GuardCommand1029 = new BitSet(new long[]{0x000000F000148000L});
    public static final BitSet FOLLOW_expr_term_in_synpred15_GuardCommand1033 = new BitSet(new long[]{0x0000000000000F02L});
    public static final BitSet FOLLOW_expr_factor_in_synpred17_GuardCommand1072 = new BitSet(new long[]{0x000000000000C000L});
    public static final BitSet FOLLOW_term_in_synpred17_GuardCommand1076 = new BitSet(new long[]{0x000000F000148000L});
    public static final BitSet FOLLOW_expr_factor_in_synpred17_GuardCommand1080 = new BitSet(new long[]{0x000000000000C002L});
    public static final BitSet FOLLOW_term_in_synpred17_GuardCommand1093 = new BitSet(new long[]{0x000000F000148000L});
    public static final BitSet FOLLOW_expr_factor_in_synpred17_GuardCommand1097 = new BitSet(new long[]{0x000000000000C002L});
    public static final BitSet FOLLOW_expr_unary_in_synpred19_GuardCommand1136 = new BitSet(new long[]{0x0000000000030000L});
    public static final BitSet FOLLOW_factor_in_synpred19_GuardCommand1140 = new BitSet(new long[]{0x000000F000148000L});
    public static final BitSet FOLLOW_expr_unary_in_synpred19_GuardCommand1144 = new BitSet(new long[]{0x0000000000030002L});
    public static final BitSet FOLLOW_factor_in_synpred19_GuardCommand1157 = new BitSet(new long[]{0x000000F000148000L});
    public static final BitSet FOLLOW_expr_unary_in_synpred19_GuardCommand1161 = new BitSet(new long[]{0x0000000000030002L});
    public static final BitSet FOLLOW_SEMI_in_synpred33_GuardCommand1464 = new BitSet(new long[]{0x00000083C5400000L});
    public static final BitSet FOLLOW_command_in_synpred33_GuardCommand1468 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_GUARD_in_synpred34_GuardCommand1686 = new BitSet(new long[]{0x000000F000148000L});
    public static final BitSet FOLLOW_guarded_cmd_in_synpred34_GuardCommand1690 = new BitSet(new long[]{0x0000000000000002L});

}