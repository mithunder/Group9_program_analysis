// $ANTLR 3.2 debian-4 GuardCommand.g 2010-10-15 13:41:31

package com.github.mithunder.parser;

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
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "AND", "OR", "ASSIGN", "SEMI", "GREATER_THAN", "GREATER_EQ", "LESS_THAN", "LESS_EQ", "EQ", "NEQ", "PLUS", "MINUS", "MUL", "DIV", "NOT", "RPAREN", "LPAREN", "RCURLY", "LCURLY", "COLON", "IF", "FI", "DO", "OD", "GUARD", "ARROW", "SKIP", "ABORT", "WRITE", "READ", "MODULE", "END", "TRUE", "FALSE", "INTEGER_LITERAL", "IDENTIFIER", "ML_COMMENT", "LINE_COMMENT", "ANNOTATION", "LETTER", "WS"
    };
    public static final int LETTER=43;
    public static final int ANNOTATION=42;
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
    public static final int WS=44;
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
            this.state.ruleMemo = new HashMap[69+1];
             
             
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


    public static class unary_operator_return extends ParserRuleReturnScope {
        public int type;
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "unary_operator"
    // GuardCommand.g:112:1: unary_operator returns [int type] : ( NOT | MINUS );
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
            // GuardCommand.g:113:2: ( NOT | MINUS )
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
                    // GuardCommand.g:113:4: NOT
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    NOT1=(Token)match(input,NOT,FOLLOW_NOT_in_unary_operator457); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    NOT1_tree = (CommonTree)adaptor.create(NOT1);
                    adaptor.addChild(root_0, NOT1_tree);
                    }
                    if ( state.backtracking==0 ) {
                      retval.type = StatementType.LOGIC_NOT;
                    }

                    }
                    break;
                case 2 :
                    // GuardCommand.g:114:4: MINUS
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    MINUS2=(Token)match(input,MINUS,FOLLOW_MINUS_in_unary_operator464); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    MINUS2_tree = (CommonTree)adaptor.create(MINUS2);
                    adaptor.addChild(root_0, MINUS2_tree);
                    }
                    if ( state.backtracking==0 ) {
                      retval.type = StatementType.SIGN_INVERT;
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
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "or"
    // GuardCommand.g:117:1: or returns [int type] : OR ;
    public final GuardCommandParser.or_return or() throws RecognitionException {
        GuardCommandParser.or_return retval = new GuardCommandParser.or_return();
        retval.start = input.LT(1);
        int or_StartIndex = input.index();
        CommonTree root_0 = null;

        Token OR3=null;

        CommonTree OR3_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 2) ) { return retval; }
            // GuardCommand.g:117:22: ( OR )
            // GuardCommand.g:117:24: OR
            {
            root_0 = (CommonTree)adaptor.nil();

            OR3=(Token)match(input,OR,FOLLOW_OR_in_or480); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            OR3_tree = (CommonTree)adaptor.create(OR3);
            adaptor.addChild(root_0, OR3_tree);
            }
            if ( state.backtracking==0 ) {
              retval.type = StatementType.LOGIC_OR;
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
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "and"
    // GuardCommand.g:119:1: and returns [int type] : AND ;
    public final GuardCommandParser.and_return and() throws RecognitionException {
        GuardCommandParser.and_return retval = new GuardCommandParser.and_return();
        retval.start = input.LT(1);
        int and_StartIndex = input.index();
        CommonTree root_0 = null;

        Token AND4=null;

        CommonTree AND4_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 3) ) { return retval; }
            // GuardCommand.g:119:23: ( AND )
            // GuardCommand.g:119:25: AND
            {
            root_0 = (CommonTree)adaptor.nil();

            AND4=(Token)match(input,AND,FOLLOW_AND_in_and493); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            AND4_tree = (CommonTree)adaptor.create(AND4);
            adaptor.addChild(root_0, AND4_tree);
            }
            if ( state.backtracking==0 ) {
              retval.type = StatementType.LOGIC_AND;
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
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "eq"
    // GuardCommand.g:121:1: eq returns [int type] : EQ ;
    public final GuardCommandParser.eq_return eq() throws RecognitionException {
        GuardCommandParser.eq_return retval = new GuardCommandParser.eq_return();
        retval.start = input.LT(1);
        int eq_StartIndex = input.index();
        CommonTree root_0 = null;

        Token EQ5=null;

        CommonTree EQ5_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 4) ) { return retval; }
            // GuardCommand.g:121:22: ( EQ )
            // GuardCommand.g:121:24: EQ
            {
            root_0 = (CommonTree)adaptor.nil();

            EQ5=(Token)match(input,EQ,FOLLOW_EQ_in_eq506); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            EQ5_tree = (CommonTree)adaptor.create(EQ5);
            adaptor.addChild(root_0, EQ5_tree);
            }
            if ( state.backtracking==0 ) {
              retval.type = StatementType.EQ;
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
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "neq"
    // GuardCommand.g:122:1: neq returns [int type] : NEQ ;
    public final GuardCommandParser.neq_return neq() throws RecognitionException {
        GuardCommandParser.neq_return retval = new GuardCommandParser.neq_return();
        retval.start = input.LT(1);
        int neq_StartIndex = input.index();
        CommonTree root_0 = null;

        Token NEQ6=null;

        CommonTree NEQ6_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 5) ) { return retval; }
            // GuardCommand.g:122:23: ( NEQ )
            // GuardCommand.g:122:25: NEQ
            {
            root_0 = (CommonTree)adaptor.nil();

            NEQ6=(Token)match(input,NEQ,FOLLOW_NEQ_in_neq518); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            NEQ6_tree = (CommonTree)adaptor.create(NEQ6);
            adaptor.addChild(root_0, NEQ6_tree);
            }
            if ( state.backtracking==0 ) {
              retval.type = StatementType.NEQ;
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
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "eqa"
    // GuardCommand.g:123:1: eqa returns [int type] : ( (a= eq ) | (b= neq ) );
    public final GuardCommandParser.eqa_return eqa() throws RecognitionException {
        GuardCommandParser.eqa_return retval = new GuardCommandParser.eqa_return();
        retval.start = input.LT(1);
        int eqa_StartIndex = input.index();
        CommonTree root_0 = null;

        GuardCommandParser.eq_return a = null;

        GuardCommandParser.neq_return b = null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 6) ) { return retval; }
            // GuardCommand.g:124:2: ( (a= eq ) | (b= neq ) )
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
                    // GuardCommand.g:124:4: (a= eq )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    // GuardCommand.g:124:4: (a= eq )
                    // GuardCommand.g:124:5: a= eq
                    {
                    pushFollow(FOLLOW_eq_in_eqa534);
                    a=eq();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, a.getTree());
                    if ( state.backtracking==0 ) {
                      retval.type = a.type;
                    }

                    }


                    }
                    break;
                case 2 :
                    // GuardCommand.g:125:4: (b= neq )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    // GuardCommand.g:125:4: (b= neq )
                    // GuardCommand.g:125:5: b= neq
                    {
                    pushFollow(FOLLOW_neq_in_eqa545);
                    b=neq();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, b.getTree());
                    if ( state.backtracking==0 ) {
                      retval.type = b.type;
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
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "gt"
    // GuardCommand.g:128:1: gt returns [int type] : GREATER_THAN ;
    public final GuardCommandParser.gt_return gt() throws RecognitionException {
        GuardCommandParser.gt_return retval = new GuardCommandParser.gt_return();
        retval.start = input.LT(1);
        int gt_StartIndex = input.index();
        CommonTree root_0 = null;

        Token GREATER_THAN7=null;

        CommonTree GREATER_THAN7_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 7) ) { return retval; }
            // GuardCommand.g:128:22: ( GREATER_THAN )
            // GuardCommand.g:128:24: GREATER_THAN
            {
            root_0 = (CommonTree)adaptor.nil();

            GREATER_THAN7=(Token)match(input,GREATER_THAN,FOLLOW_GREATER_THAN_in_gt561); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            GREATER_THAN7_tree = (CommonTree)adaptor.create(GREATER_THAN7);
            adaptor.addChild(root_0, GREATER_THAN7_tree);
            }
            if ( state.backtracking==0 ) {
              retval.type = StatementType.GT;
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
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "gt_eq"
    // GuardCommand.g:129:1: gt_eq returns [int type] : GREATER_EQ ;
    public final GuardCommandParser.gt_eq_return gt_eq() throws RecognitionException {
        GuardCommandParser.gt_eq_return retval = new GuardCommandParser.gt_eq_return();
        retval.start = input.LT(1);
        int gt_eq_StartIndex = input.index();
        CommonTree root_0 = null;

        Token GREATER_EQ8=null;

        CommonTree GREATER_EQ8_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 8) ) { return retval; }
            // GuardCommand.g:129:25: ( GREATER_EQ )
            // GuardCommand.g:129:27: GREATER_EQ
            {
            root_0 = (CommonTree)adaptor.nil();

            GREATER_EQ8=(Token)match(input,GREATER_EQ,FOLLOW_GREATER_EQ_in_gt_eq573); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            GREATER_EQ8_tree = (CommonTree)adaptor.create(GREATER_EQ8);
            adaptor.addChild(root_0, GREATER_EQ8_tree);
            }
            if ( state.backtracking==0 ) {
              retval.type = StatementType.GT_EQ;
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
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "lt"
    // GuardCommand.g:130:1: lt returns [int type] : LESS_THAN ;
    public final GuardCommandParser.lt_return lt() throws RecognitionException {
        GuardCommandParser.lt_return retval = new GuardCommandParser.lt_return();
        retval.start = input.LT(1);
        int lt_StartIndex = input.index();
        CommonTree root_0 = null;

        Token LESS_THAN9=null;

        CommonTree LESS_THAN9_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 9) ) { return retval; }
            // GuardCommand.g:130:22: ( LESS_THAN )
            // GuardCommand.g:130:24: LESS_THAN
            {
            root_0 = (CommonTree)adaptor.nil();

            LESS_THAN9=(Token)match(input,LESS_THAN,FOLLOW_LESS_THAN_in_lt585); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            LESS_THAN9_tree = (CommonTree)adaptor.create(LESS_THAN9);
            adaptor.addChild(root_0, LESS_THAN9_tree);
            }
            if ( state.backtracking==0 ) {
              retval.type = StatementType.LT;
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
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "lt_eq"
    // GuardCommand.g:131:1: lt_eq returns [int type] : LESS_EQ ;
    public final GuardCommandParser.lt_eq_return lt_eq() throws RecognitionException {
        GuardCommandParser.lt_eq_return retval = new GuardCommandParser.lt_eq_return();
        retval.start = input.LT(1);
        int lt_eq_StartIndex = input.index();
        CommonTree root_0 = null;

        Token LESS_EQ10=null;

        CommonTree LESS_EQ10_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 10) ) { return retval; }
            // GuardCommand.g:131:25: ( LESS_EQ )
            // GuardCommand.g:131:27: LESS_EQ
            {
            root_0 = (CommonTree)adaptor.nil();

            LESS_EQ10=(Token)match(input,LESS_EQ,FOLLOW_LESS_EQ_in_lt_eq597); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            LESS_EQ10_tree = (CommonTree)adaptor.create(LESS_EQ10);
            adaptor.addChild(root_0, LESS_EQ10_tree);
            }
            if ( state.backtracking==0 ) {
              retval.type = StatementType.LT_EQ;
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
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "rel"
    // GuardCommand.g:132:1: rel returns [int type] : ( (a= gt ) | (b= gt_eq ) | (c= lt ) | (d= lt_eq ) );
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
            // GuardCommand.g:133:2: ( (a= gt ) | (b= gt_eq ) | (c= lt ) | (d= lt_eq ) )
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
                    // GuardCommand.g:133:4: (a= gt )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    // GuardCommand.g:133:4: (a= gt )
                    // GuardCommand.g:133:5: a= gt
                    {
                    pushFollow(FOLLOW_gt_in_rel613);
                    a=gt();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, a.getTree());
                    if ( state.backtracking==0 ) {
                      retval.type = a.type;
                    }

                    }


                    }
                    break;
                case 2 :
                    // GuardCommand.g:134:4: (b= gt_eq )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    // GuardCommand.g:134:4: (b= gt_eq )
                    // GuardCommand.g:134:5: b= gt_eq
                    {
                    pushFollow(FOLLOW_gt_eq_in_rel624);
                    b=gt_eq();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, b.getTree());
                    if ( state.backtracking==0 ) {
                      retval.type = b.type;
                    }

                    }


                    }
                    break;
                case 3 :
                    // GuardCommand.g:135:4: (c= lt )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    // GuardCommand.g:135:4: (c= lt )
                    // GuardCommand.g:135:5: c= lt
                    {
                    pushFollow(FOLLOW_lt_in_rel635);
                    c=lt();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, c.getTree());
                    if ( state.backtracking==0 ) {
                      retval.type = c.type;
                    }

                    }


                    }
                    break;
                case 4 :
                    // GuardCommand.g:136:4: (d= lt_eq )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    // GuardCommand.g:136:4: (d= lt_eq )
                    // GuardCommand.g:136:5: d= lt_eq
                    {
                    pushFollow(FOLLOW_lt_eq_in_rel646);
                    d=lt_eq();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, d.getTree());
                    if ( state.backtracking==0 ) {
                      retval.type = d.type;
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
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "plus"
    // GuardCommand.g:139:1: plus returns [int type] : PLUS ;
    public final GuardCommandParser.plus_return plus() throws RecognitionException {
        GuardCommandParser.plus_return retval = new GuardCommandParser.plus_return();
        retval.start = input.LT(1);
        int plus_StartIndex = input.index();
        CommonTree root_0 = null;

        Token PLUS11=null;

        CommonTree PLUS11_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 12) ) { return retval; }
            // GuardCommand.g:139:24: ( PLUS )
            // GuardCommand.g:139:26: PLUS
            {
            root_0 = (CommonTree)adaptor.nil();

            PLUS11=(Token)match(input,PLUS,FOLLOW_PLUS_in_plus662); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            PLUS11_tree = (CommonTree)adaptor.create(PLUS11);
            adaptor.addChild(root_0, PLUS11_tree);
            }
            if ( state.backtracking==0 ) {
              retval.type = StatementType.PLUS;
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
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "minus"
    // GuardCommand.g:140:1: minus returns [int type] : MINUS ;
    public final GuardCommandParser.minus_return minus() throws RecognitionException {
        GuardCommandParser.minus_return retval = new GuardCommandParser.minus_return();
        retval.start = input.LT(1);
        int minus_StartIndex = input.index();
        CommonTree root_0 = null;

        Token MINUS12=null;

        CommonTree MINUS12_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 13) ) { return retval; }
            // GuardCommand.g:140:25: ( MINUS )
            // GuardCommand.g:140:27: MINUS
            {
            root_0 = (CommonTree)adaptor.nil();

            MINUS12=(Token)match(input,MINUS,FOLLOW_MINUS_in_minus674); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            MINUS12_tree = (CommonTree)adaptor.create(MINUS12);
            adaptor.addChild(root_0, MINUS12_tree);
            }
            if ( state.backtracking==0 ) {
              retval.type = StatementType.MINUS;
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
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "term"
    // GuardCommand.g:141:1: term returns [int type] : ( (a= plus ) | (b= minus ) );
    public final GuardCommandParser.term_return term() throws RecognitionException {
        GuardCommandParser.term_return retval = new GuardCommandParser.term_return();
        retval.start = input.LT(1);
        int term_StartIndex = input.index();
        CommonTree root_0 = null;

        GuardCommandParser.plus_return a = null;

        GuardCommandParser.minus_return b = null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 14) ) { return retval; }
            // GuardCommand.g:142:2: ( (a= plus ) | (b= minus ) )
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
                    // GuardCommand.g:142:4: (a= plus )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    // GuardCommand.g:142:4: (a= plus )
                    // GuardCommand.g:142:5: a= plus
                    {
                    pushFollow(FOLLOW_plus_in_term690);
                    a=plus();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, a.getTree());
                    if ( state.backtracking==0 ) {
                      retval.type = a.type;
                    }

                    }


                    }
                    break;
                case 2 :
                    // GuardCommand.g:143:4: (b= minus )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    // GuardCommand.g:143:4: (b= minus )
                    // GuardCommand.g:143:5: b= minus
                    {
                    pushFollow(FOLLOW_minus_in_term701);
                    b=minus();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, b.getTree());
                    if ( state.backtracking==0 ) {
                      retval.type = b.type;
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
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "mul"
    // GuardCommand.g:146:1: mul returns [int type] : MUL ;
    public final GuardCommandParser.mul_return mul() throws RecognitionException {
        GuardCommandParser.mul_return retval = new GuardCommandParser.mul_return();
        retval.start = input.LT(1);
        int mul_StartIndex = input.index();
        CommonTree root_0 = null;

        Token MUL13=null;

        CommonTree MUL13_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 15) ) { return retval; }
            // GuardCommand.g:146:23: ( MUL )
            // GuardCommand.g:146:25: MUL
            {
            root_0 = (CommonTree)adaptor.nil();

            MUL13=(Token)match(input,MUL,FOLLOW_MUL_in_mul717); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            MUL13_tree = (CommonTree)adaptor.create(MUL13);
            adaptor.addChild(root_0, MUL13_tree);
            }
            if ( state.backtracking==0 ) {
              retval.type = StatementType.MULTIPLY;
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
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "div"
    // GuardCommand.g:147:1: div returns [int type] : DIV ;
    public final GuardCommandParser.div_return div() throws RecognitionException {
        GuardCommandParser.div_return retval = new GuardCommandParser.div_return();
        retval.start = input.LT(1);
        int div_StartIndex = input.index();
        CommonTree root_0 = null;

        Token DIV14=null;

        CommonTree DIV14_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 16) ) { return retval; }
            // GuardCommand.g:147:23: ( DIV )
            // GuardCommand.g:147:25: DIV
            {
            root_0 = (CommonTree)adaptor.nil();

            DIV14=(Token)match(input,DIV,FOLLOW_DIV_in_div729); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            DIV14_tree = (CommonTree)adaptor.create(DIV14);
            adaptor.addChild(root_0, DIV14_tree);
            }
            if ( state.backtracking==0 ) {
              retval.type = StatementType.DIVIDE;
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
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "factor"
    // GuardCommand.g:148:1: factor returns [int type] : ( (a= mul ) | (b= div ) );
    public final GuardCommandParser.factor_return factor() throws RecognitionException {
        GuardCommandParser.factor_return retval = new GuardCommandParser.factor_return();
        retval.start = input.LT(1);
        int factor_StartIndex = input.index();
        CommonTree root_0 = null;

        GuardCommandParser.mul_return a = null;

        GuardCommandParser.div_return b = null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 17) ) { return retval; }
            // GuardCommand.g:149:2: ( (a= mul ) | (b= div ) )
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
                    // GuardCommand.g:149:4: (a= mul )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    // GuardCommand.g:149:4: (a= mul )
                    // GuardCommand.g:149:5: a= mul
                    {
                    pushFollow(FOLLOW_mul_in_factor745);
                    a=mul();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, a.getTree());
                    if ( state.backtracking==0 ) {
                      retval.type = a.type;
                    }

                    }


                    }
                    break;
                case 2 :
                    // GuardCommand.g:150:4: (b= div )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    // GuardCommand.g:150:4: (b= div )
                    // GuardCommand.g:150:5: b= div
                    {
                    pushFollow(FOLLOW_div_in_factor756);
                    b=div();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, b.getTree());
                    if ( state.backtracking==0 ) {
                      retval.type = b.type;
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
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "expression"
    // GuardCommand.g:157:1: expression returns [List<Statement> statList] : expr_or ;
    public final GuardCommandParser.expression_return expression() throws RecognitionException {
        GuardCommandParser.expression_return retval = new GuardCommandParser.expression_return();
        retval.start = input.LT(1);
        int expression_StartIndex = input.index();
        CommonTree root_0 = null;

        GuardCommandParser.expr_or_return expr_or15 = null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 18) ) { return retval; }
            // GuardCommand.g:158:2: ( expr_or )
            // GuardCommand.g:158:4: expr_or
            {
            root_0 = (CommonTree)adaptor.nil();

            pushFollow(FOLLOW_expr_or_in_expression781);
            expr_or15=expr_or();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, expr_or15.getTree());

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
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "expr_or"
    // GuardCommand.g:161:1: expr_or returns [List<Statement> statList] : ( (e1= expr_and b= or e2= expr_and ) (b= or e3= expr_and )* | (e4= expr_and ) );
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
            // GuardCommand.g:162:2: ( (e1= expr_and b= or e2= expr_and ) (b= or e3= expr_and )* | (e4= expr_and ) )
            int alt7=2;
            switch ( input.LA(1) ) {
            case INTEGER_LITERAL:
                {
                int LA7_1 = input.LA(2);

                if ( (synpred9_GuardCommand()) ) {
                    alt7=1;
                }
                else if ( (true) ) {
                    alt7=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 7, 1, input);

                    throw nvae;
                }
                }
                break;
            case TRUE:
                {
                int LA7_2 = input.LA(2);

                if ( (synpred9_GuardCommand()) ) {
                    alt7=1;
                }
                else if ( (true) ) {
                    alt7=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 7, 2, input);

                    throw nvae;
                }
                }
                break;
            case FALSE:
                {
                int LA7_3 = input.LA(2);

                if ( (synpred9_GuardCommand()) ) {
                    alt7=1;
                }
                else if ( (true) ) {
                    alt7=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 7, 3, input);

                    throw nvae;
                }
                }
                break;
            case IDENTIFIER:
                {
                int LA7_4 = input.LA(2);

                if ( (synpred9_GuardCommand()) ) {
                    alt7=1;
                }
                else if ( (true) ) {
                    alt7=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 7, 4, input);

                    throw nvae;
                }
                }
                break;
            case LPAREN:
                {
                int LA7_5 = input.LA(2);

                if ( (synpred9_GuardCommand()) ) {
                    alt7=1;
                }
                else if ( (true) ) {
                    alt7=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 7, 5, input);

                    throw nvae;
                }
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 7, 0, input);

                throw nvae;
            }

            switch (alt7) {
                case 1 :
                    // GuardCommand.g:163:2: (e1= expr_and b= or e2= expr_and ) (b= or e3= expr_and )*
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    // GuardCommand.g:163:2: (e1= expr_and b= or e2= expr_and )
                    // GuardCommand.g:163:3: e1= expr_and b= or e2= expr_and
                    {
                    pushFollow(FOLLOW_expr_and_in_expr_or800);
                    e1=expr_and();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, e1.getTree());
                    pushFollow(FOLLOW_or_in_expr_or804);
                    b=or();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, b.getTree());
                    pushFollow(FOLLOW_expr_and_in_expr_or808);
                    e2=expr_and();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, e2.getTree());
                    if ( state.backtracking==0 ) {
                      retval.statList = e1.statList; handleExpression(e1.statList, e2.statList, b.type, b.tree);
                    }

                    }

                    // GuardCommand.g:166:2: (b= or e3= expr_and )*
                    loop6:
                    do {
                        int alt6=2;
                        int LA6_0 = input.LA(1);

                        if ( (LA6_0==OR) ) {
                            alt6=1;
                        }


                        switch (alt6) {
                    	case 1 :
                    	    // GuardCommand.g:166:3: b= or e3= expr_and
                    	    {
                    	    pushFollow(FOLLOW_or_in_expr_or821);
                    	    b=or();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) adaptor.addChild(root_0, b.getTree());
                    	    pushFollow(FOLLOW_expr_and_in_expr_or825);
                    	    e3=expr_and();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) adaptor.addChild(root_0, e3.getTree());
                    	    if ( state.backtracking==0 ) {
                    	      handleExpression(retval.statList, e3.statList, b.type, b.tree);
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
                    // GuardCommand.g:167:4: (e4= expr_and )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    // GuardCommand.g:167:4: (e4= expr_and )
                    // GuardCommand.g:167:5: e4= expr_and
                    {
                    pushFollow(FOLLOW_expr_and_in_expr_or837);
                    e4=expr_and();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, e4.getTree());

                    }

                    if ( state.backtracking==0 ) {
                      retval.statList = e4.statList;
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
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "expr_and"
    // GuardCommand.g:170:1: expr_and returns [List<Statement> statList] : ( (e1= expr_eqa b= and e2= expr_eqa ) (b= and e3= expr_eqa )* | (e4= expr_eqa ) );
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
            // GuardCommand.g:171:2: ( (e1= expr_eqa b= and e2= expr_eqa ) (b= and e3= expr_eqa )* | (e4= expr_eqa ) )
            int alt9=2;
            switch ( input.LA(1) ) {
            case INTEGER_LITERAL:
                {
                int LA9_1 = input.LA(2);

                if ( (synpred11_GuardCommand()) ) {
                    alt9=1;
                }
                else if ( (true) ) {
                    alt9=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 9, 1, input);

                    throw nvae;
                }
                }
                break;
            case TRUE:
                {
                int LA9_2 = input.LA(2);

                if ( (synpred11_GuardCommand()) ) {
                    alt9=1;
                }
                else if ( (true) ) {
                    alt9=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 9, 2, input);

                    throw nvae;
                }
                }
                break;
            case FALSE:
                {
                int LA9_3 = input.LA(2);

                if ( (synpred11_GuardCommand()) ) {
                    alt9=1;
                }
                else if ( (true) ) {
                    alt9=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 9, 3, input);

                    throw nvae;
                }
                }
                break;
            case IDENTIFIER:
                {
                int LA9_4 = input.LA(2);

                if ( (synpred11_GuardCommand()) ) {
                    alt9=1;
                }
                else if ( (true) ) {
                    alt9=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 9, 4, input);

                    throw nvae;
                }
                }
                break;
            case LPAREN:
                {
                int LA9_5 = input.LA(2);

                if ( (synpred11_GuardCommand()) ) {
                    alt9=1;
                }
                else if ( (true) ) {
                    alt9=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 9, 5, input);

                    throw nvae;
                }
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 9, 0, input);

                throw nvae;
            }

            switch (alt9) {
                case 1 :
                    // GuardCommand.g:172:2: (e1= expr_eqa b= and e2= expr_eqa ) (b= and e3= expr_eqa )*
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    // GuardCommand.g:172:2: (e1= expr_eqa b= and e2= expr_eqa )
                    // GuardCommand.g:172:3: e1= expr_eqa b= and e2= expr_eqa
                    {
                    pushFollow(FOLLOW_expr_eqa_in_expr_and860);
                    e1=expr_eqa();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, e1.getTree());
                    pushFollow(FOLLOW_and_in_expr_and864);
                    b=and();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, b.getTree());
                    pushFollow(FOLLOW_expr_eqa_in_expr_and868);
                    e2=expr_eqa();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, e2.getTree());
                    if ( state.backtracking==0 ) {
                      retval.statList = e1.statList; handleExpression(e1.statList, e2.statList, b.type, b.tree);
                    }

                    }

                    // GuardCommand.g:175:2: (b= and e3= expr_eqa )*
                    loop8:
                    do {
                        int alt8=2;
                        int LA8_0 = input.LA(1);

                        if ( (LA8_0==AND) ) {
                            alt8=1;
                        }


                        switch (alt8) {
                    	case 1 :
                    	    // GuardCommand.g:175:3: b= and e3= expr_eqa
                    	    {
                    	    pushFollow(FOLLOW_and_in_expr_and881);
                    	    b=and();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) adaptor.addChild(root_0, b.getTree());
                    	    pushFollow(FOLLOW_expr_eqa_in_expr_and885);
                    	    e3=expr_eqa();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) adaptor.addChild(root_0, e3.getTree());
                    	    if ( state.backtracking==0 ) {
                    	      handleExpression(retval.statList, e3.statList, b.type, b.tree);
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
                    // GuardCommand.g:176:4: (e4= expr_eqa )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    // GuardCommand.g:176:4: (e4= expr_eqa )
                    // GuardCommand.g:176:5: e4= expr_eqa
                    {
                    pushFollow(FOLLOW_expr_eqa_in_expr_and897);
                    e4=expr_eqa();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, e4.getTree());

                    }

                    if ( state.backtracking==0 ) {
                      retval.statList = e4.statList;
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
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "expr_eqa"
    // GuardCommand.g:180:1: expr_eqa returns [List<Statement> statList] : ( (e1= expr_rel b= eqa e2= expr_rel ) (b= eqa e3= expr_rel )* | (e4= expr_rel ) );
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
            // GuardCommand.g:181:2: ( (e1= expr_rel b= eqa e2= expr_rel ) (b= eqa e3= expr_rel )* | (e4= expr_rel ) )
            int alt11=2;
            switch ( input.LA(1) ) {
            case INTEGER_LITERAL:
                {
                int LA11_1 = input.LA(2);

                if ( (synpred13_GuardCommand()) ) {
                    alt11=1;
                }
                else if ( (true) ) {
                    alt11=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 11, 1, input);

                    throw nvae;
                }
                }
                break;
            case TRUE:
                {
                int LA11_2 = input.LA(2);

                if ( (synpred13_GuardCommand()) ) {
                    alt11=1;
                }
                else if ( (true) ) {
                    alt11=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 11, 2, input);

                    throw nvae;
                }
                }
                break;
            case FALSE:
                {
                int LA11_3 = input.LA(2);

                if ( (synpred13_GuardCommand()) ) {
                    alt11=1;
                }
                else if ( (true) ) {
                    alt11=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 11, 3, input);

                    throw nvae;
                }
                }
                break;
            case IDENTIFIER:
                {
                int LA11_4 = input.LA(2);

                if ( (synpred13_GuardCommand()) ) {
                    alt11=1;
                }
                else if ( (true) ) {
                    alt11=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 11, 4, input);

                    throw nvae;
                }
                }
                break;
            case LPAREN:
                {
                int LA11_5 = input.LA(2);

                if ( (synpred13_GuardCommand()) ) {
                    alt11=1;
                }
                else if ( (true) ) {
                    alt11=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 11, 5, input);

                    throw nvae;
                }
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 11, 0, input);

                throw nvae;
            }

            switch (alt11) {
                case 1 :
                    // GuardCommand.g:182:2: (e1= expr_rel b= eqa e2= expr_rel ) (b= eqa e3= expr_rel )*
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    // GuardCommand.g:182:2: (e1= expr_rel b= eqa e2= expr_rel )
                    // GuardCommand.g:182:3: e1= expr_rel b= eqa e2= expr_rel
                    {
                    pushFollow(FOLLOW_expr_rel_in_expr_eqa921);
                    e1=expr_rel();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, e1.getTree());
                    pushFollow(FOLLOW_eqa_in_expr_eqa925);
                    b=eqa();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, b.getTree());
                    pushFollow(FOLLOW_expr_rel_in_expr_eqa929);
                    e2=expr_rel();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, e2.getTree());
                    if ( state.backtracking==0 ) {
                      retval.statList = e1.statList; handleExpression(e1.statList, e2.statList, b.type, b.tree);
                    }

                    }

                    // GuardCommand.g:185:2: (b= eqa e3= expr_rel )*
                    loop10:
                    do {
                        int alt10=2;
                        int LA10_0 = input.LA(1);

                        if ( ((LA10_0>=EQ && LA10_0<=NEQ)) ) {
                            alt10=1;
                        }


                        switch (alt10) {
                    	case 1 :
                    	    // GuardCommand.g:185:3: b= eqa e3= expr_rel
                    	    {
                    	    pushFollow(FOLLOW_eqa_in_expr_eqa942);
                    	    b=eqa();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) adaptor.addChild(root_0, b.getTree());
                    	    pushFollow(FOLLOW_expr_rel_in_expr_eqa946);
                    	    e3=expr_rel();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) adaptor.addChild(root_0, e3.getTree());
                    	    if ( state.backtracking==0 ) {
                    	      handleExpression(retval.statList, e3.statList, b.type, b.tree);
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
                    // GuardCommand.g:186:4: (e4= expr_rel )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    // GuardCommand.g:186:4: (e4= expr_rel )
                    // GuardCommand.g:186:5: e4= expr_rel
                    {
                    pushFollow(FOLLOW_expr_rel_in_expr_eqa958);
                    e4=expr_rel();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, e4.getTree());

                    }

                    if ( state.backtracking==0 ) {
                      retval.statList = e4.statList;
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
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "expr_rel"
    // GuardCommand.g:189:1: expr_rel returns [List<Statement> statList] : ( (e1= expr_term b= rel e2= expr_term ) (b= rel e3= expr_term )* | (e4= expr_term ) );
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
            // GuardCommand.g:190:2: ( (e1= expr_term b= rel e2= expr_term ) (b= rel e3= expr_term )* | (e4= expr_term ) )
            int alt13=2;
            switch ( input.LA(1) ) {
            case INTEGER_LITERAL:
                {
                int LA13_1 = input.LA(2);

                if ( (synpred15_GuardCommand()) ) {
                    alt13=1;
                }
                else if ( (true) ) {
                    alt13=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 13, 1, input);

                    throw nvae;
                }
                }
                break;
            case TRUE:
                {
                int LA13_2 = input.LA(2);

                if ( (synpred15_GuardCommand()) ) {
                    alt13=1;
                }
                else if ( (true) ) {
                    alt13=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 13, 2, input);

                    throw nvae;
                }
                }
                break;
            case FALSE:
                {
                int LA13_3 = input.LA(2);

                if ( (synpred15_GuardCommand()) ) {
                    alt13=1;
                }
                else if ( (true) ) {
                    alt13=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 13, 3, input);

                    throw nvae;
                }
                }
                break;
            case IDENTIFIER:
                {
                int LA13_4 = input.LA(2);

                if ( (synpred15_GuardCommand()) ) {
                    alt13=1;
                }
                else if ( (true) ) {
                    alt13=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 13, 4, input);

                    throw nvae;
                }
                }
                break;
            case LPAREN:
                {
                int LA13_5 = input.LA(2);

                if ( (synpred15_GuardCommand()) ) {
                    alt13=1;
                }
                else if ( (true) ) {
                    alt13=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 13, 5, input);

                    throw nvae;
                }
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 13, 0, input);

                throw nvae;
            }

            switch (alt13) {
                case 1 :
                    // GuardCommand.g:191:2: (e1= expr_term b= rel e2= expr_term ) (b= rel e3= expr_term )*
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    // GuardCommand.g:191:2: (e1= expr_term b= rel e2= expr_term )
                    // GuardCommand.g:191:3: e1= expr_term b= rel e2= expr_term
                    {
                    pushFollow(FOLLOW_expr_term_in_expr_rel980);
                    e1=expr_term();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, e1.getTree());
                    pushFollow(FOLLOW_rel_in_expr_rel984);
                    b=rel();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, b.getTree());
                    pushFollow(FOLLOW_expr_term_in_expr_rel988);
                    e2=expr_term();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, e2.getTree());
                    if ( state.backtracking==0 ) {
                      retval.statList = e1.statList; handleExpression(e1.statList, e2.statList, b.type, b.tree);
                    }

                    }

                    // GuardCommand.g:194:2: (b= rel e3= expr_term )*
                    loop12:
                    do {
                        int alt12=2;
                        int LA12_0 = input.LA(1);

                        if ( ((LA12_0>=GREATER_THAN && LA12_0<=LESS_EQ)) ) {
                            alt12=1;
                        }


                        switch (alt12) {
                    	case 1 :
                    	    // GuardCommand.g:194:3: b= rel e3= expr_term
                    	    {
                    	    pushFollow(FOLLOW_rel_in_expr_rel1001);
                    	    b=rel();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) adaptor.addChild(root_0, b.getTree());
                    	    pushFollow(FOLLOW_expr_term_in_expr_rel1005);
                    	    e3=expr_term();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) adaptor.addChild(root_0, e3.getTree());
                    	    if ( state.backtracking==0 ) {
                    	      handleExpression(retval.statList, e3.statList, b.type, b.tree);
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
                    // GuardCommand.g:195:4: (e4= expr_term )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    // GuardCommand.g:195:4: (e4= expr_term )
                    // GuardCommand.g:195:5: e4= expr_term
                    {
                    pushFollow(FOLLOW_expr_term_in_expr_rel1017);
                    e4=expr_term();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, e4.getTree());

                    }

                    if ( state.backtracking==0 ) {
                      retval.statList = e4.statList;
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
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "expr_term"
    // GuardCommand.g:198:1: expr_term returns [List<Statement> statList] : ( (e1= expr_factor b= term e2= expr_factor ) (b= term e3= expr_factor )* | (e4= expr_factor ) );
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
            // GuardCommand.g:199:2: ( (e1= expr_factor b= term e2= expr_factor ) (b= term e3= expr_factor )* | (e4= expr_factor ) )
            int alt15=2;
            switch ( input.LA(1) ) {
            case INTEGER_LITERAL:
                {
                int LA15_1 = input.LA(2);

                if ( (synpred17_GuardCommand()) ) {
                    alt15=1;
                }
                else if ( (true) ) {
                    alt15=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 15, 1, input);

                    throw nvae;
                }
                }
                break;
            case TRUE:
                {
                int LA15_2 = input.LA(2);

                if ( (synpred17_GuardCommand()) ) {
                    alt15=1;
                }
                else if ( (true) ) {
                    alt15=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 15, 2, input);

                    throw nvae;
                }
                }
                break;
            case FALSE:
                {
                int LA15_3 = input.LA(2);

                if ( (synpred17_GuardCommand()) ) {
                    alt15=1;
                }
                else if ( (true) ) {
                    alt15=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 15, 3, input);

                    throw nvae;
                }
                }
                break;
            case IDENTIFIER:
                {
                int LA15_4 = input.LA(2);

                if ( (synpred17_GuardCommand()) ) {
                    alt15=1;
                }
                else if ( (true) ) {
                    alt15=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 15, 4, input);

                    throw nvae;
                }
                }
                break;
            case LPAREN:
                {
                int LA15_5 = input.LA(2);

                if ( (synpred17_GuardCommand()) ) {
                    alt15=1;
                }
                else if ( (true) ) {
                    alt15=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 15, 5, input);

                    throw nvae;
                }
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 15, 0, input);

                throw nvae;
            }

            switch (alt15) {
                case 1 :
                    // GuardCommand.g:200:2: (e1= expr_factor b= term e2= expr_factor ) (b= term e3= expr_factor )*
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    // GuardCommand.g:200:2: (e1= expr_factor b= term e2= expr_factor )
                    // GuardCommand.g:200:3: e1= expr_factor b= term e2= expr_factor
                    {
                    pushFollow(FOLLOW_expr_factor_in_expr_term1040);
                    e1=expr_factor();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, e1.getTree());
                    pushFollow(FOLLOW_term_in_expr_term1044);
                    b=term();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, b.getTree());
                    pushFollow(FOLLOW_expr_factor_in_expr_term1048);
                    e2=expr_factor();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, e2.getTree());
                    if ( state.backtracking==0 ) {
                      retval.statList = e1.statList; handleExpression(e1.statList, e2.statList, b.type, b.tree);
                    }

                    }

                    // GuardCommand.g:203:2: (b= term e3= expr_factor )*
                    loop14:
                    do {
                        int alt14=2;
                        int LA14_0 = input.LA(1);

                        if ( ((LA14_0>=PLUS && LA14_0<=MINUS)) ) {
                            alt14=1;
                        }


                        switch (alt14) {
                    	case 1 :
                    	    // GuardCommand.g:203:3: b= term e3= expr_factor
                    	    {
                    	    pushFollow(FOLLOW_term_in_expr_term1061);
                    	    b=term();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) adaptor.addChild(root_0, b.getTree());
                    	    pushFollow(FOLLOW_expr_factor_in_expr_term1065);
                    	    e3=expr_factor();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) adaptor.addChild(root_0, e3.getTree());
                    	    if ( state.backtracking==0 ) {
                    	      handleExpression(retval.statList, e3.statList, b.type, b.tree);
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
                    // GuardCommand.g:204:4: (e4= expr_factor )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    // GuardCommand.g:204:4: (e4= expr_factor )
                    // GuardCommand.g:204:5: e4= expr_factor
                    {
                    pushFollow(FOLLOW_expr_factor_in_expr_term1077);
                    e4=expr_factor();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, e4.getTree());

                    }

                    if ( state.backtracking==0 ) {
                      retval.statList = e4.statList;
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
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "expr_factor"
    // GuardCommand.g:207:1: expr_factor returns [List<Statement> statList] : ( (e1= literal b= factor e2= literal ) (b= factor e3= literal )* | (e4= literal ) );
    public final GuardCommandParser.expr_factor_return expr_factor() throws RecognitionException {
        GuardCommandParser.expr_factor_return retval = new GuardCommandParser.expr_factor_return();
        retval.start = input.LT(1);
        int expr_factor_StartIndex = input.index();
        CommonTree root_0 = null;

        GuardCommandParser.literal_return e1 = null;

        GuardCommandParser.factor_return b = null;

        GuardCommandParser.literal_return e2 = null;

        GuardCommandParser.literal_return e3 = null;

        GuardCommandParser.literal_return e4 = null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 24) ) { return retval; }
            // GuardCommand.g:208:2: ( (e1= literal b= factor e2= literal ) (b= factor e3= literal )* | (e4= literal ) )
            int alt17=2;
            switch ( input.LA(1) ) {
            case INTEGER_LITERAL:
                {
                int LA17_1 = input.LA(2);

                if ( (synpred19_GuardCommand()) ) {
                    alt17=1;
                }
                else if ( (true) ) {
                    alt17=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 17, 1, input);

                    throw nvae;
                }
                }
                break;
            case TRUE:
                {
                int LA17_2 = input.LA(2);

                if ( (synpred19_GuardCommand()) ) {
                    alt17=1;
                }
                else if ( (true) ) {
                    alt17=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 17, 2, input);

                    throw nvae;
                }
                }
                break;
            case FALSE:
                {
                int LA17_3 = input.LA(2);

                if ( (synpred19_GuardCommand()) ) {
                    alt17=1;
                }
                else if ( (true) ) {
                    alt17=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 17, 3, input);

                    throw nvae;
                }
                }
                break;
            case IDENTIFIER:
                {
                int LA17_4 = input.LA(2);

                if ( (synpred19_GuardCommand()) ) {
                    alt17=1;
                }
                else if ( (true) ) {
                    alt17=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 17, 4, input);

                    throw nvae;
                }
                }
                break;
            case LPAREN:
                {
                int LA17_5 = input.LA(2);

                if ( (synpred19_GuardCommand()) ) {
                    alt17=1;
                }
                else if ( (true) ) {
                    alt17=2;
                }
                else {
                    if (state.backtracking>0) {state.failed=true; return retval;}
                    NoViableAltException nvae =
                        new NoViableAltException("", 17, 5, input);

                    throw nvae;
                }
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 17, 0, input);

                throw nvae;
            }

            switch (alt17) {
                case 1 :
                    // GuardCommand.g:209:2: (e1= literal b= factor e2= literal ) (b= factor e3= literal )*
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    // GuardCommand.g:209:2: (e1= literal b= factor e2= literal )
                    // GuardCommand.g:209:3: e1= literal b= factor e2= literal
                    {
                    pushFollow(FOLLOW_literal_in_expr_factor1100);
                    e1=literal();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, e1.getTree());
                    pushFollow(FOLLOW_factor_in_expr_factor1104);
                    b=factor();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, b.getTree());
                    pushFollow(FOLLOW_literal_in_expr_factor1108);
                    e2=literal();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, e2.getTree());
                    if ( state.backtracking==0 ) {
                      retval.statList = e1.statList; handleExpression(e1.statList, e2.statList, b.type, b.tree);
                    }

                    }

                    // GuardCommand.g:212:2: (b= factor e3= literal )*
                    loop16:
                    do {
                        int alt16=2;
                        int LA16_0 = input.LA(1);

                        if ( ((LA16_0>=MUL && LA16_0<=DIV)) ) {
                            alt16=1;
                        }


                        switch (alt16) {
                    	case 1 :
                    	    // GuardCommand.g:212:3: b= factor e3= literal
                    	    {
                    	    pushFollow(FOLLOW_factor_in_expr_factor1121);
                    	    b=factor();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) adaptor.addChild(root_0, b.getTree());
                    	    pushFollow(FOLLOW_literal_in_expr_factor1125);
                    	    e3=literal();

                    	    state._fsp--;
                    	    if (state.failed) return retval;
                    	    if ( state.backtracking==0 ) adaptor.addChild(root_0, e3.getTree());
                    	    if ( state.backtracking==0 ) {
                    	      handleExpression(retval.statList, e3.statList, b.type, b.tree);
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
                    // GuardCommand.g:213:4: (e4= literal )
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    // GuardCommand.g:213:4: (e4= literal )
                    // GuardCommand.g:213:5: e4= literal
                    {
                    pushFollow(FOLLOW_literal_in_expr_factor1137);
                    e4=literal();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, e4.getTree());

                    }

                    if ( state.backtracking==0 ) {
                      retval.statList = e4.statList;
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
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "expr_unary"
    // GuardCommand.g:216:1: expr_unary returns [List<Statement> statList] : (u= unary_operator e= expression | l= literal ) ;
    public final GuardCommandParser.expr_unary_return expr_unary() throws RecognitionException {
        GuardCommandParser.expr_unary_return retval = new GuardCommandParser.expr_unary_return();
        retval.start = input.LT(1);
        int expr_unary_StartIndex = input.index();
        CommonTree root_0 = null;

        GuardCommandParser.unary_operator_return u = null;

        GuardCommandParser.expression_return e = null;

        GuardCommandParser.literal_return l = null;



        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 25) ) { return retval; }
            // GuardCommand.g:217:2: ( (u= unary_operator e= expression | l= literal ) )
            // GuardCommand.g:218:2: (u= unary_operator e= expression | l= literal )
            {
            root_0 = (CommonTree)adaptor.nil();

            if ( state.backtracking==0 ) {

              		if (retval.statList == null) {
              			retval.statList = new ArrayList<Statement>();
              		}
              	
            }
            // GuardCommand.g:223:2: (u= unary_operator e= expression | l= literal )
            int alt18=2;
            int LA18_0 = input.LA(1);

            if ( (LA18_0==MINUS||LA18_0==NOT) ) {
                alt18=1;
            }
            else if ( (LA18_0==LPAREN||(LA18_0>=TRUE && LA18_0<=IDENTIFIER)) ) {
                alt18=2;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 18, 0, input);

                throw nvae;
            }
            switch (alt18) {
                case 1 :
                    // GuardCommand.g:223:4: u= unary_operator e= expression
                    {
                    pushFollow(FOLLOW_unary_operator_in_expr_unary1163);
                    u=unary_operator();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, u.getTree());
                    pushFollow(FOLLOW_expression_in_expr_unary1167);
                    e=expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, e.getTree());
                    if ( state.backtracking==0 ) {

                      		final Statement newestStat = statementFactory.createSimpleStatement(
                      			u.type,
                      			new CodeLocation(u.tree.getLine()),
                      			null,
                      			variableTable.createTemporaryVariable(),
                      			e.statList.get(e.statList.size()-1).getAssign()
                      		);
                      		retval.statList.addAll(e.statList);
                      		retval.statList.add(newestStat);
                      		
                    }

                    }
                    break;
                case 2 :
                    // GuardCommand.g:235:4: l= literal
                    {
                    pushFollow(FOLLOW_literal_in_expr_unary1178);
                    l=literal();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, l.getTree());
                    if ( state.backtracking==0 ) {
                      retval.statList = l.statList;
                    }

                    }
                    break;

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
            if ( state.backtracking>0 ) { memoize(input, 25, expr_unary_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "expr_unary"

    public static class literal_return extends ParserRuleReturnScope {
        public List<Statement> statList;
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "literal"
    // GuardCommand.g:239:1: literal returns [List<Statement> statList] : (inte= INTEGER_LITERAL | tru= TRUE | fal= FALSE | id= IDENTIFIER | LPAREN e= expression RPAREN );
    public final GuardCommandParser.literal_return literal() throws RecognitionException {
        GuardCommandParser.literal_return retval = new GuardCommandParser.literal_return();
        retval.start = input.LT(1);
        int literal_StartIndex = input.index();
        CommonTree root_0 = null;

        Token inte=null;
        Token tru=null;
        Token fal=null;
        Token id=null;
        Token LPAREN16=null;
        Token RPAREN17=null;
        GuardCommandParser.expression_return e = null;


        CommonTree inte_tree=null;
        CommonTree tru_tree=null;
        CommonTree fal_tree=null;
        CommonTree id_tree=null;
        CommonTree LPAREN16_tree=null;
        CommonTree RPAREN17_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 26) ) { return retval; }
            // GuardCommand.g:240:2: (inte= INTEGER_LITERAL | tru= TRUE | fal= FALSE | id= IDENTIFIER | LPAREN e= expression RPAREN )
            int alt19=5;
            switch ( input.LA(1) ) {
            case INTEGER_LITERAL:
                {
                alt19=1;
                }
                break;
            case TRUE:
                {
                alt19=2;
                }
                break;
            case FALSE:
                {
                alt19=3;
                }
                break;
            case IDENTIFIER:
                {
                alt19=4;
                }
                break;
            case LPAREN:
                {
                alt19=5;
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
                    // GuardCommand.g:241:2: inte= INTEGER_LITERAL
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    if ( state.backtracking==0 ) {

                      		if (retval.statList == null) {
                      			retval.statList = new ArrayList<Statement>();
                      		}
                      	
                    }
                    inte=(Token)match(input,INTEGER_LITERAL,FOLLOW_INTEGER_LITERAL_in_literal1204); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    inte_tree = (CommonTree)adaptor.create(inte);
                    adaptor.addChild(root_0, inte_tree);
                    }
                    if ( state.backtracking==0 ) {
                      retval.statList.add(statementFactory.createSimpleStatement(
                      			StatementType.ASSIGN,
                      			new CodeLocation(inte_tree.getLine()),
                      			null,
                      			variableTable.createTemporaryVariable(),
                      			ConstantValue.getConstantValue(ValueType.INTEGER_TYPE, Integer.parseInt(inte.getText()))
                      		));
                      		
                    }

                    }
                    break;
                case 2 :
                    // GuardCommand.g:255:4: tru= TRUE
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    tru=(Token)match(input,TRUE,FOLLOW_TRUE_in_literal1215); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    tru_tree = (CommonTree)adaptor.create(tru);
                    adaptor.addChild(root_0, tru_tree);
                    }
                    if ( state.backtracking==0 ) {
                      retval.statList.add(statementFactory.createSimpleStatement(
                      			StatementType.ASSIGN,
                      			new CodeLocation(tru_tree.getLine()),
                      			null,
                      			variableTable.createTemporaryVariable(),
                      			ConstantValue.TRUE
                      		));
                      		
                    }

                    }
                    break;
                case 3 :
                    // GuardCommand.g:264:4: fal= FALSE
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    fal=(Token)match(input,FALSE,FOLLOW_FALSE_in_literal1226); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    fal_tree = (CommonTree)adaptor.create(fal);
                    adaptor.addChild(root_0, fal_tree);
                    }
                    if ( state.backtracking==0 ) {
                      retval.statList.add(statementFactory.createSimpleStatement(
                      			StatementType.ASSIGN,
                      			new CodeLocation(fal_tree.getLine()),
                      			null,
                      			variableTable.createTemporaryVariable(),
                      			ConstantValue.FALSE
                      		));
                      		
                    }

                    }
                    break;
                case 4 :
                    // GuardCommand.g:273:4: id= IDENTIFIER
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    id=(Token)match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_literal1237); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    id_tree = (CommonTree)adaptor.create(id);
                    adaptor.addChild(root_0, id_tree);
                    }
                    if ( state.backtracking==0 ) {
                      retval.statList.add(statementFactory.createSimpleStatement(
                      			StatementType.ASSIGN,
                      			new CodeLocation(id_tree.getLine()),
                      			null,
                      			variableTable.createTemporaryVariable(),
                      			variableTable.getVariable(id.getText())
                      		));
                      		
                    }

                    }
                    break;
                case 5 :
                    // GuardCommand.g:282:4: LPAREN e= expression RPAREN
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    LPAREN16=(Token)match(input,LPAREN,FOLLOW_LPAREN_in_literal1246); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    LPAREN16_tree = (CommonTree)adaptor.create(LPAREN16);
                    adaptor.addChild(root_0, LPAREN16_tree);
                    }
                    pushFollow(FOLLOW_expression_in_literal1250);
                    e=expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, e.getTree());
                    RPAREN17=(Token)match(input,RPAREN,FOLLOW_RPAREN_in_literal1252); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    RPAREN17_tree = (CommonTree)adaptor.create(RPAREN17);
                    adaptor.addChild(root_0, RPAREN17_tree);
                    }
                    if ( state.backtracking==0 ) {
                      retval.statList = e.statList;
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

    public static class command_return extends ParserRuleReturnScope {
        public List<Statement> commands;
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "command"
    // GuardCommand.g:285:1: command returns [List<Statement> commands] : (a= assignment_cmd | b= skip_cmd | c= abort_cmd | d= read_cmd | e= write_cmd | l= LCURLY f= command RCURLY | g= if_cmd | h= do_cmd ) ( SEMI i= command )* ;
    public final GuardCommandParser.command_return command() throws RecognitionException {
        GuardCommandParser.command_return retval = new GuardCommandParser.command_return();
        retval.start = input.LT(1);
        int command_StartIndex = input.index();
        CommonTree root_0 = null;

        Token l=null;
        Token RCURLY18=null;
        Token SEMI19=null;
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
        CommonTree RCURLY18_tree=null;
        CommonTree SEMI19_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 27) ) { return retval; }
            // GuardCommand.g:286:2: ( (a= assignment_cmd | b= skip_cmd | c= abort_cmd | d= read_cmd | e= write_cmd | l= LCURLY f= command RCURLY | g= if_cmd | h= do_cmd ) ( SEMI i= command )* )
            // GuardCommand.g:287:2: (a= assignment_cmd | b= skip_cmd | c= abort_cmd | d= read_cmd | e= write_cmd | l= LCURLY f= command RCURLY | g= if_cmd | h= do_cmd ) ( SEMI i= command )*
            {
            root_0 = (CommonTree)adaptor.nil();

            if ( state.backtracking==0 ) {

              		if (retval.commands == null) {
              			retval.commands = new ArrayList<Statement>();
              		}
              	
            }
            // GuardCommand.g:293:2: (a= assignment_cmd | b= skip_cmd | c= abort_cmd | d= read_cmd | e= write_cmd | l= LCURLY f= command RCURLY | g= if_cmd | h= do_cmd )
            int alt20=8;
            switch ( input.LA(1) ) {
            case IDENTIFIER:
                {
                alt20=1;
                }
                break;
            case SKIP:
                {
                alt20=2;
                }
                break;
            case ABORT:
                {
                alt20=3;
                }
                break;
            case READ:
                {
                alt20=4;
                }
                break;
            case WRITE:
                {
                alt20=5;
                }
                break;
            case LCURLY:
                {
                alt20=6;
                }
                break;
            case IF:
                {
                alt20=7;
                }
                break;
            case DO:
                {
                alt20=8;
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
                    // GuardCommand.g:293:4: a= assignment_cmd
                    {
                    pushFollow(FOLLOW_assignment_cmd_in_command1279);
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
                    // GuardCommand.g:294:4: b= skip_cmd
                    {
                    pushFollow(FOLLOW_skip_cmd_in_command1290);
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
                    // GuardCommand.g:295:4: c= abort_cmd
                    {
                    pushFollow(FOLLOW_abort_cmd_in_command1302);
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
                    // GuardCommand.g:296:4: d= read_cmd
                    {
                    pushFollow(FOLLOW_read_cmd_in_command1314);
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
                    // GuardCommand.g:297:4: e= write_cmd
                    {
                    pushFollow(FOLLOW_write_cmd_in_command1326);
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
                    // GuardCommand.g:298:4: l= LCURLY f= command RCURLY
                    {
                    l=(Token)match(input,LCURLY,FOLLOW_LCURLY_in_command1338); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    l_tree = (CommonTree)adaptor.create(l);
                    adaptor.addChild(root_0, l_tree);
                    }
                    pushFollow(FOLLOW_command_in_command1342);
                    f=command();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, f.getTree());
                    RCURLY18=(Token)match(input,RCURLY,FOLLOW_RCURLY_in_command1344); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    RCURLY18_tree = (CommonTree)adaptor.create(RCURLY18);
                    adaptor.addChild(root_0, RCURLY18_tree);
                    }
                    if ( state.backtracking==0 ) {

                      			retval.commands.add(statementFactory.createCompoundStatement(
                      				StatementType.SCOPE, new CodeLocation(l_tree.getLine()), null, f.commands
                      			));
                      		
                    }

                    }
                    break;
                case 7 :
                    // GuardCommand.g:304:4: g= if_cmd
                    {
                    pushFollow(FOLLOW_if_cmd_in_command1355);
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
                    // GuardCommand.g:305:4: h= do_cmd
                    {
                    pushFollow(FOLLOW_do_cmd_in_command1369);
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

            // GuardCommand.g:308:2: ( SEMI i= command )*
            loop21:
            do {
                int alt21=2;
                int LA21_0 = input.LA(1);

                if ( (LA21_0==SEMI) ) {
                    int LA21_2 = input.LA(2);

                    if ( (synpred32_GuardCommand()) ) {
                        alt21=1;
                    }


                }


                switch (alt21) {
            	case 1 :
            	    // GuardCommand.g:308:3: SEMI i= command
            	    {
            	    SEMI19=(Token)match(input,SEMI,FOLLOW_SEMI_in_command1385); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    SEMI19_tree = (CommonTree)adaptor.create(SEMI19);
            	    adaptor.addChild(root_0, SEMI19_tree);
            	    }
            	    pushFollow(FOLLOW_command_in_command1389);
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
            	    break loop21;
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
            if ( state.backtracking>0 ) { memoize(input, 27, command_StartIndex); }
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
    // GuardCommand.g:316:1: assignment_cmd returns [List<Statement> commands] : id= IDENTIFIER as= ASSIGN e= expression ;
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
            if ( state.backtracking>0 && alreadyParsedRule(input, 28) ) { return retval; }
            // GuardCommand.g:317:2: (id= IDENTIFIER as= ASSIGN e= expression )
            // GuardCommand.g:317:4: id= IDENTIFIER as= ASSIGN e= expression
            {
            root_0 = (CommonTree)adaptor.nil();

            id=(Token)match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_assignment_cmd1414); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            id_tree = (CommonTree)adaptor.create(id);
            adaptor.addChild(root_0, id_tree);
            }
            as=(Token)match(input,ASSIGN,FOLLOW_ASSIGN_in_assignment_cmd1418); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            as_tree = (CommonTree)adaptor.create(as);
            adaptor.addChild(root_0, as_tree);
            }
            pushFollow(FOLLOW_expression_in_assignment_cmd1422);
            e=expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, e.getTree());
            if ( state.backtracking==0 ) {

              			final Statement assignStatement = statementFactory.createSimpleStatement(
              				StatementType.ASSIGN, new CodeLocation(as_tree.getLine()), null,
              				variableTable.getVariable(id.getText()), e.statList.get(e.statList.size()-1).getAssign()
              			);
              			e.statList.add(assignStatement);
              			retval.commands = e.statList;
              		
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
            if ( state.backtracking>0 ) { memoize(input, 28, assignment_cmd_StartIndex); }
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
    // GuardCommand.g:328:1: skip_cmd returns [Statement command] : s= SKIP ;
    public final GuardCommandParser.skip_cmd_return skip_cmd() throws RecognitionException {
        GuardCommandParser.skip_cmd_return retval = new GuardCommandParser.skip_cmd_return();
        retval.start = input.LT(1);
        int skip_cmd_StartIndex = input.index();
        CommonTree root_0 = null;

        Token s=null;

        CommonTree s_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 29) ) { return retval; }
            // GuardCommand.g:329:2: (s= SKIP )
            // GuardCommand.g:329:4: s= SKIP
            {
            root_0 = (CommonTree)adaptor.nil();

            s=(Token)match(input,SKIP,FOLLOW_SKIP_in_skip_cmd1443); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            s_tree = (CommonTree)adaptor.create(s);
            adaptor.addChild(root_0, s_tree);
            }
            if ( state.backtracking==0 ) {

              			retval.command = statementFactory.createSimpleStatement(
              				StatementType.SKIP, new CodeLocation(s_tree.getLine()), null
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
            if ( state.backtracking>0 ) { memoize(input, 29, skip_cmd_StartIndex); }
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
    // GuardCommand.g:337:1: abort_cmd returns [Statement command] : a= ABORT ;
    public final GuardCommandParser.abort_cmd_return abort_cmd() throws RecognitionException {
        GuardCommandParser.abort_cmd_return retval = new GuardCommandParser.abort_cmd_return();
        retval.start = input.LT(1);
        int abort_cmd_StartIndex = input.index();
        CommonTree root_0 = null;

        Token a=null;

        CommonTree a_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 30) ) { return retval; }
            // GuardCommand.g:338:2: (a= ABORT )
            // GuardCommand.g:338:4: a= ABORT
            {
            root_0 = (CommonTree)adaptor.nil();

            a=(Token)match(input,ABORT,FOLLOW_ABORT_in_abort_cmd1464); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            a_tree = (CommonTree)adaptor.create(a);
            adaptor.addChild(root_0, a_tree);
            }
            if ( state.backtracking==0 ) {

              			retval.command = statementFactory.createSimpleStatement(
              				StatementType.ABORT, new CodeLocation(a_tree.getLine()), null
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
            if ( state.backtracking>0 ) { memoize(input, 30, abort_cmd_StartIndex); }
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
    // GuardCommand.g:346:1: read_cmd returns [Statement command] : rea= READ id= IDENTIFIER ;
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
            if ( state.backtracking>0 && alreadyParsedRule(input, 31) ) { return retval; }
            // GuardCommand.g:347:2: (rea= READ id= IDENTIFIER )
            // GuardCommand.g:347:4: rea= READ id= IDENTIFIER
            {
            root_0 = (CommonTree)adaptor.nil();

            rea=(Token)match(input,READ,FOLLOW_READ_in_read_cmd1485); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            rea_tree = (CommonTree)adaptor.create(rea);
            adaptor.addChild(root_0, rea_tree);
            }
            id=(Token)match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_read_cmd1489); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            id_tree = (CommonTree)adaptor.create(id);
            adaptor.addChild(root_0, id_tree);
            }
            if ( state.backtracking==0 ) {

              			retval.command = statementFactory.createSimpleStatement(
              				StatementType.READ, new CodeLocation(rea_tree.getLine()), null,
              				variableTable.getVariable(id.getText())
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
            if ( state.backtracking>0 ) { memoize(input, 31, read_cmd_StartIndex); }
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
    // GuardCommand.g:356:1: write_cmd returns [List<Statement> commands] : wr= WRITE expression e= expression ;
    public final GuardCommandParser.write_cmd_return write_cmd() throws RecognitionException {
        GuardCommandParser.write_cmd_return retval = new GuardCommandParser.write_cmd_return();
        retval.start = input.LT(1);
        int write_cmd_StartIndex = input.index();
        CommonTree root_0 = null;

        Token wr=null;
        GuardCommandParser.expression_return e = null;

        GuardCommandParser.expression_return expression20 = null;


        CommonTree wr_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 32) ) { return retval; }
            // GuardCommand.g:357:2: (wr= WRITE expression e= expression )
            // GuardCommand.g:357:4: wr= WRITE expression e= expression
            {
            root_0 = (CommonTree)adaptor.nil();

            wr=(Token)match(input,WRITE,FOLLOW_WRITE_in_write_cmd1510); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            wr_tree = (CommonTree)adaptor.create(wr);
            adaptor.addChild(root_0, wr_tree);
            }
            pushFollow(FOLLOW_expression_in_write_cmd1512);
            expression20=expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, expression20.getTree());
            pushFollow(FOLLOW_expression_in_write_cmd1516);
            e=expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, e.getTree());
            if ( state.backtracking==0 ) {

              			final Statement assignStatement = statementFactory.createSimpleStatement(
              				StatementType.WRITE, new CodeLocation(wr_tree.getLine()), null,
              				null, e.statList.get(e.statList.size()-1).getAssign()
              			);
              			e.statList.add(assignStatement);
              			retval.commands = e.statList;
              		
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
            if ( state.backtracking>0 ) { memoize(input, 32, write_cmd_StartIndex); }
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
    // GuardCommand.g:368:1: if_cmd returns [Statement command] : ift= IF gc= guarded_cmd FI ;
    public final GuardCommandParser.if_cmd_return if_cmd() throws RecognitionException {
        GuardCommandParser.if_cmd_return retval = new GuardCommandParser.if_cmd_return();
        retval.start = input.LT(1);
        int if_cmd_StartIndex = input.index();
        CommonTree root_0 = null;

        Token ift=null;
        Token FI21=null;
        GuardCommandParser.guarded_cmd_return gc = null;


        CommonTree ift_tree=null;
        CommonTree FI21_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 33) ) { return retval; }
            // GuardCommand.g:369:2: (ift= IF gc= guarded_cmd FI )
            // GuardCommand.g:369:4: ift= IF gc= guarded_cmd FI
            {
            root_0 = (CommonTree)adaptor.nil();

            ift=(Token)match(input,IF,FOLLOW_IF_in_if_cmd1537); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            ift_tree = (CommonTree)adaptor.create(ift);
            adaptor.addChild(root_0, ift_tree);
            }
            pushFollow(FOLLOW_guarded_cmd_in_if_cmd1541);
            gc=guarded_cmd();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, gc.getTree());
            FI21=(Token)match(input,FI,FOLLOW_FI_in_if_cmd1543); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            FI21_tree = (CommonTree)adaptor.create(FI21);
            adaptor.addChild(root_0, FI21_tree);
            }
            if ( state.backtracking==0 ) {

              			retval.command = statementFactory.createCompoundStatement(
              				StatementType.IF, new CodeLocation(ift_tree.getLine()), null, gc.commands
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
            if ( state.backtracking>0 ) { memoize(input, 33, if_cmd_StartIndex); }
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
    // GuardCommand.g:377:1: do_cmd returns [Statement command] : dot= DO gc= guarded_cmd OD ;
    public final GuardCommandParser.do_cmd_return do_cmd() throws RecognitionException {
        GuardCommandParser.do_cmd_return retval = new GuardCommandParser.do_cmd_return();
        retval.start = input.LT(1);
        int do_cmd_StartIndex = input.index();
        CommonTree root_0 = null;

        Token dot=null;
        Token OD22=null;
        GuardCommandParser.guarded_cmd_return gc = null;


        CommonTree dot_tree=null;
        CommonTree OD22_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 34) ) { return retval; }
            // GuardCommand.g:378:2: (dot= DO gc= guarded_cmd OD )
            // GuardCommand.g:378:4: dot= DO gc= guarded_cmd OD
            {
            root_0 = (CommonTree)adaptor.nil();

            dot=(Token)match(input,DO,FOLLOW_DO_in_do_cmd1564); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            dot_tree = (CommonTree)adaptor.create(dot);
            adaptor.addChild(root_0, dot_tree);
            }
            pushFollow(FOLLOW_guarded_cmd_in_do_cmd1568);
            gc=guarded_cmd();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, gc.getTree());
            OD22=(Token)match(input,OD,FOLLOW_OD_in_do_cmd1570); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            OD22_tree = (CommonTree)adaptor.create(OD22);
            adaptor.addChild(root_0, OD22_tree);
            }
            if ( state.backtracking==0 ) {

              			retval.command = statementFactory.createCompoundStatement(
              				StatementType.DO, new CodeLocation(dot_tree.getLine()), null, gc.commands
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
            if ( state.backtracking>0 ) { memoize(input, 34, do_cmd_StartIndex); }
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
    // GuardCommand.g:386:1: guarded_cmd returns [List<Statement> commands] : (e= expression ARROW c= command ) ( GUARD gc= guarded_cmd )* ;
    public final GuardCommandParser.guarded_cmd_return guarded_cmd() throws RecognitionException {
        GuardCommandParser.guarded_cmd_return retval = new GuardCommandParser.guarded_cmd_return();
        retval.start = input.LT(1);
        int guarded_cmd_StartIndex = input.index();
        CommonTree root_0 = null;

        Token ARROW23=null;
        Token GUARD24=null;
        GuardCommandParser.expression_return e = null;

        GuardCommandParser.command_return c = null;

        GuardCommandParser.guarded_cmd_return gc = null;


        CommonTree ARROW23_tree=null;
        CommonTree GUARD24_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 35) ) { return retval; }
            // GuardCommand.g:387:2: ( (e= expression ARROW c= command ) ( GUARD gc= guarded_cmd )* )
            // GuardCommand.g:388:2: (e= expression ARROW c= command ) ( GUARD gc= guarded_cmd )*
            {
            root_0 = (CommonTree)adaptor.nil();

            if ( state.backtracking==0 ) {

              		if (retval.commands == null) {
              			retval.commands = new ArrayList<Statement>();
              		}
              	
            }
            // GuardCommand.g:393:2: (e= expression ARROW c= command )
            // GuardCommand.g:393:3: e= expression ARROW c= command
            {
            pushFollow(FOLLOW_expression_in_guarded_cmd1596);
            e=expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, e.getTree());
            ARROW23=(Token)match(input,ARROW,FOLLOW_ARROW_in_guarded_cmd1598); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            ARROW23_tree = (CommonTree)adaptor.create(ARROW23);
            adaptor.addChild(root_0, ARROW23_tree);
            }
            pushFollow(FOLLOW_command_in_guarded_cmd1602);
            c=command();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, c.getTree());

            }

            if ( state.backtracking==0 ) {

              		retval.commands.add(statementFactory.createCompoundStatement(
              			StatementType.SCOPE, new CodeLocation(e.tree.getLine()), null, e.statList
              		));
              		retval.commands.add(statementFactory.createCompoundStatement(
              			StatementType.SCOPE, new CodeLocation(c.tree.getLine()), null, c.commands
              		));
              	
            }
            // GuardCommand.g:401:2: ( GUARD gc= guarded_cmd )*
            loop22:
            do {
                int alt22=2;
                int LA22_0 = input.LA(1);

                if ( (LA22_0==GUARD) ) {
                    int LA22_2 = input.LA(2);

                    if ( (synpred33_GuardCommand()) ) {
                        alt22=1;
                    }


                }


                switch (alt22) {
            	case 1 :
            	    // GuardCommand.g:401:3: GUARD gc= guarded_cmd
            	    {
            	    GUARD24=(Token)match(input,GUARD,FOLLOW_GUARD_in_guarded_cmd1609); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    GUARD24_tree = (CommonTree)adaptor.create(GUARD24);
            	    adaptor.addChild(root_0, GUARD24_tree);
            	    }
            	    pushFollow(FOLLOW_guarded_cmd_in_guarded_cmd1613);
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
            if ( state.backtracking>0 ) { memoize(input, 35, guarded_cmd_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "guarded_cmd"

    public static class program_return extends ParserRuleReturnScope {
        public Statement command;
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "program"
    // GuardCommand.g:404:1: program returns [Statement command] : m= MODULE IDENTIFIER COLON c= command END ;
    public final GuardCommandParser.program_return program() throws RecognitionException {
        GuardCommandParser.program_return retval = new GuardCommandParser.program_return();
        retval.start = input.LT(1);
        int program_StartIndex = input.index();
        CommonTree root_0 = null;

        Token m=null;
        Token IDENTIFIER25=null;
        Token COLON26=null;
        Token END27=null;
        GuardCommandParser.command_return c = null;


        CommonTree m_tree=null;
        CommonTree IDENTIFIER25_tree=null;
        CommonTree COLON26_tree=null;
        CommonTree END27_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 36) ) { return retval; }
            // GuardCommand.g:405:2: (m= MODULE IDENTIFIER COLON c= command END )
            // GuardCommand.g:405:4: m= MODULE IDENTIFIER COLON c= command END
            {
            root_0 = (CommonTree)adaptor.nil();

            m=(Token)match(input,MODULE,FOLLOW_MODULE_in_program1636); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            m_tree = (CommonTree)adaptor.create(m);
            adaptor.addChild(root_0, m_tree);
            }
            IDENTIFIER25=(Token)match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_program1638); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            IDENTIFIER25_tree = (CommonTree)adaptor.create(IDENTIFIER25);
            adaptor.addChild(root_0, IDENTIFIER25_tree);
            }
            COLON26=(Token)match(input,COLON,FOLLOW_COLON_in_program1640); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            COLON26_tree = (CommonTree)adaptor.create(COLON26);
            adaptor.addChild(root_0, COLON26_tree);
            }
            pushFollow(FOLLOW_command_in_program1644);
            c=command();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, c.getTree());
            END27=(Token)match(input,END,FOLLOW_END_in_program1646); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            END27_tree = (CommonTree)adaptor.create(END27);
            adaptor.addChild(root_0, END27_tree);
            }
            if ( state.backtracking==0 ) {

              			retval.command = statementFactory.createRootStatement(
              				new CodeLocation(m_tree.getLine()), null, c.commands
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
            if ( state.backtracking>0 ) { memoize(input, 36, program_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "program"

    // $ANTLR start synpred9_GuardCommand
    public final void synpred9_GuardCommand_fragment() throws RecognitionException {   
        GuardCommandParser.expr_and_return e1 = null;

        GuardCommandParser.or_return b = null;

        GuardCommandParser.expr_and_return e2 = null;

        GuardCommandParser.expr_and_return e3 = null;


        // GuardCommand.g:163:2: ( (e1= expr_and b= or e2= expr_and ) (b= or e3= expr_and )* )
        // GuardCommand.g:163:2: (e1= expr_and b= or e2= expr_and ) (b= or e3= expr_and )*
        {
        // GuardCommand.g:163:2: (e1= expr_and b= or e2= expr_and )
        // GuardCommand.g:163:3: e1= expr_and b= or e2= expr_and
        {
        pushFollow(FOLLOW_expr_and_in_synpred9_GuardCommand800);
        e1=expr_and();

        state._fsp--;
        if (state.failed) return ;
        pushFollow(FOLLOW_or_in_synpred9_GuardCommand804);
        b=or();

        state._fsp--;
        if (state.failed) return ;
        pushFollow(FOLLOW_expr_and_in_synpred9_GuardCommand808);
        e2=expr_and();

        state._fsp--;
        if (state.failed) return ;

        }

        // GuardCommand.g:166:2: (b= or e3= expr_and )*
        loop23:
        do {
            int alt23=2;
            int LA23_0 = input.LA(1);

            if ( (LA23_0==OR) ) {
                alt23=1;
            }


            switch (alt23) {
        	case 1 :
        	    // GuardCommand.g:166:3: b= or e3= expr_and
        	    {
        	    pushFollow(FOLLOW_or_in_synpred9_GuardCommand821);
        	    b=or();

        	    state._fsp--;
        	    if (state.failed) return ;
        	    pushFollow(FOLLOW_expr_and_in_synpred9_GuardCommand825);
        	    e3=expr_and();

        	    state._fsp--;
        	    if (state.failed) return ;

        	    }
        	    break;

        	default :
        	    break loop23;
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


        // GuardCommand.g:172:2: ( (e1= expr_eqa b= and e2= expr_eqa ) (b= and e3= expr_eqa )* )
        // GuardCommand.g:172:2: (e1= expr_eqa b= and e2= expr_eqa ) (b= and e3= expr_eqa )*
        {
        // GuardCommand.g:172:2: (e1= expr_eqa b= and e2= expr_eqa )
        // GuardCommand.g:172:3: e1= expr_eqa b= and e2= expr_eqa
        {
        pushFollow(FOLLOW_expr_eqa_in_synpred11_GuardCommand860);
        e1=expr_eqa();

        state._fsp--;
        if (state.failed) return ;
        pushFollow(FOLLOW_and_in_synpred11_GuardCommand864);
        b=and();

        state._fsp--;
        if (state.failed) return ;
        pushFollow(FOLLOW_expr_eqa_in_synpred11_GuardCommand868);
        e2=expr_eqa();

        state._fsp--;
        if (state.failed) return ;

        }

        // GuardCommand.g:175:2: (b= and e3= expr_eqa )*
        loop24:
        do {
            int alt24=2;
            int LA24_0 = input.LA(1);

            if ( (LA24_0==AND) ) {
                alt24=1;
            }


            switch (alt24) {
        	case 1 :
        	    // GuardCommand.g:175:3: b= and e3= expr_eqa
        	    {
        	    pushFollow(FOLLOW_and_in_synpred11_GuardCommand881);
        	    b=and();

        	    state._fsp--;
        	    if (state.failed) return ;
        	    pushFollow(FOLLOW_expr_eqa_in_synpred11_GuardCommand885);
        	    e3=expr_eqa();

        	    state._fsp--;
        	    if (state.failed) return ;

        	    }
        	    break;

        	default :
        	    break loop24;
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


        // GuardCommand.g:182:2: ( (e1= expr_rel b= eqa e2= expr_rel ) (b= eqa e3= expr_rel )* )
        // GuardCommand.g:182:2: (e1= expr_rel b= eqa e2= expr_rel ) (b= eqa e3= expr_rel )*
        {
        // GuardCommand.g:182:2: (e1= expr_rel b= eqa e2= expr_rel )
        // GuardCommand.g:182:3: e1= expr_rel b= eqa e2= expr_rel
        {
        pushFollow(FOLLOW_expr_rel_in_synpred13_GuardCommand921);
        e1=expr_rel();

        state._fsp--;
        if (state.failed) return ;
        pushFollow(FOLLOW_eqa_in_synpred13_GuardCommand925);
        b=eqa();

        state._fsp--;
        if (state.failed) return ;
        pushFollow(FOLLOW_expr_rel_in_synpred13_GuardCommand929);
        e2=expr_rel();

        state._fsp--;
        if (state.failed) return ;

        }

        // GuardCommand.g:185:2: (b= eqa e3= expr_rel )*
        loop25:
        do {
            int alt25=2;
            int LA25_0 = input.LA(1);

            if ( ((LA25_0>=EQ && LA25_0<=NEQ)) ) {
                alt25=1;
            }


            switch (alt25) {
        	case 1 :
        	    // GuardCommand.g:185:3: b= eqa e3= expr_rel
        	    {
        	    pushFollow(FOLLOW_eqa_in_synpred13_GuardCommand942);
        	    b=eqa();

        	    state._fsp--;
        	    if (state.failed) return ;
        	    pushFollow(FOLLOW_expr_rel_in_synpred13_GuardCommand946);
        	    e3=expr_rel();

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
    // $ANTLR end synpred13_GuardCommand

    // $ANTLR start synpred15_GuardCommand
    public final void synpred15_GuardCommand_fragment() throws RecognitionException {   
        GuardCommandParser.expr_term_return e1 = null;

        GuardCommandParser.rel_return b = null;

        GuardCommandParser.expr_term_return e2 = null;

        GuardCommandParser.expr_term_return e3 = null;


        // GuardCommand.g:191:2: ( (e1= expr_term b= rel e2= expr_term ) (b= rel e3= expr_term )* )
        // GuardCommand.g:191:2: (e1= expr_term b= rel e2= expr_term ) (b= rel e3= expr_term )*
        {
        // GuardCommand.g:191:2: (e1= expr_term b= rel e2= expr_term )
        // GuardCommand.g:191:3: e1= expr_term b= rel e2= expr_term
        {
        pushFollow(FOLLOW_expr_term_in_synpred15_GuardCommand980);
        e1=expr_term();

        state._fsp--;
        if (state.failed) return ;
        pushFollow(FOLLOW_rel_in_synpred15_GuardCommand984);
        b=rel();

        state._fsp--;
        if (state.failed) return ;
        pushFollow(FOLLOW_expr_term_in_synpred15_GuardCommand988);
        e2=expr_term();

        state._fsp--;
        if (state.failed) return ;

        }

        // GuardCommand.g:194:2: (b= rel e3= expr_term )*
        loop26:
        do {
            int alt26=2;
            int LA26_0 = input.LA(1);

            if ( ((LA26_0>=GREATER_THAN && LA26_0<=LESS_EQ)) ) {
                alt26=1;
            }


            switch (alt26) {
        	case 1 :
        	    // GuardCommand.g:194:3: b= rel e3= expr_term
        	    {
        	    pushFollow(FOLLOW_rel_in_synpred15_GuardCommand1001);
        	    b=rel();

        	    state._fsp--;
        	    if (state.failed) return ;
        	    pushFollow(FOLLOW_expr_term_in_synpred15_GuardCommand1005);
        	    e3=expr_term();

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
    // $ANTLR end synpred15_GuardCommand

    // $ANTLR start synpred17_GuardCommand
    public final void synpred17_GuardCommand_fragment() throws RecognitionException {   
        GuardCommandParser.expr_factor_return e1 = null;

        GuardCommandParser.term_return b = null;

        GuardCommandParser.expr_factor_return e2 = null;

        GuardCommandParser.expr_factor_return e3 = null;


        // GuardCommand.g:200:2: ( (e1= expr_factor b= term e2= expr_factor ) (b= term e3= expr_factor )* )
        // GuardCommand.g:200:2: (e1= expr_factor b= term e2= expr_factor ) (b= term e3= expr_factor )*
        {
        // GuardCommand.g:200:2: (e1= expr_factor b= term e2= expr_factor )
        // GuardCommand.g:200:3: e1= expr_factor b= term e2= expr_factor
        {
        pushFollow(FOLLOW_expr_factor_in_synpred17_GuardCommand1040);
        e1=expr_factor();

        state._fsp--;
        if (state.failed) return ;
        pushFollow(FOLLOW_term_in_synpred17_GuardCommand1044);
        b=term();

        state._fsp--;
        if (state.failed) return ;
        pushFollow(FOLLOW_expr_factor_in_synpred17_GuardCommand1048);
        e2=expr_factor();

        state._fsp--;
        if (state.failed) return ;

        }

        // GuardCommand.g:203:2: (b= term e3= expr_factor )*
        loop27:
        do {
            int alt27=2;
            int LA27_0 = input.LA(1);

            if ( ((LA27_0>=PLUS && LA27_0<=MINUS)) ) {
                alt27=1;
            }


            switch (alt27) {
        	case 1 :
        	    // GuardCommand.g:203:3: b= term e3= expr_factor
        	    {
        	    pushFollow(FOLLOW_term_in_synpred17_GuardCommand1061);
        	    b=term();

        	    state._fsp--;
        	    if (state.failed) return ;
        	    pushFollow(FOLLOW_expr_factor_in_synpred17_GuardCommand1065);
        	    e3=expr_factor();

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
    // $ANTLR end synpred17_GuardCommand

    // $ANTLR start synpred19_GuardCommand
    public final void synpred19_GuardCommand_fragment() throws RecognitionException {   
        GuardCommandParser.literal_return e1 = null;

        GuardCommandParser.factor_return b = null;

        GuardCommandParser.literal_return e2 = null;

        GuardCommandParser.literal_return e3 = null;


        // GuardCommand.g:209:2: ( (e1= literal b= factor e2= literal ) (b= factor e3= literal )* )
        // GuardCommand.g:209:2: (e1= literal b= factor e2= literal ) (b= factor e3= literal )*
        {
        // GuardCommand.g:209:2: (e1= literal b= factor e2= literal )
        // GuardCommand.g:209:3: e1= literal b= factor e2= literal
        {
        pushFollow(FOLLOW_literal_in_synpred19_GuardCommand1100);
        e1=literal();

        state._fsp--;
        if (state.failed) return ;
        pushFollow(FOLLOW_factor_in_synpred19_GuardCommand1104);
        b=factor();

        state._fsp--;
        if (state.failed) return ;
        pushFollow(FOLLOW_literal_in_synpred19_GuardCommand1108);
        e2=literal();

        state._fsp--;
        if (state.failed) return ;

        }

        // GuardCommand.g:212:2: (b= factor e3= literal )*
        loop28:
        do {
            int alt28=2;
            int LA28_0 = input.LA(1);

            if ( ((LA28_0>=MUL && LA28_0<=DIV)) ) {
                alt28=1;
            }


            switch (alt28) {
        	case 1 :
        	    // GuardCommand.g:212:3: b= factor e3= literal
        	    {
        	    pushFollow(FOLLOW_factor_in_synpred19_GuardCommand1121);
        	    b=factor();

        	    state._fsp--;
        	    if (state.failed) return ;
        	    pushFollow(FOLLOW_literal_in_synpred19_GuardCommand1125);
        	    e3=literal();

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
    // $ANTLR end synpred19_GuardCommand

    // $ANTLR start synpred32_GuardCommand
    public final void synpred32_GuardCommand_fragment() throws RecognitionException {   
        GuardCommandParser.command_return i = null;


        // GuardCommand.g:308:3: ( SEMI i= command )
        // GuardCommand.g:308:3: SEMI i= command
        {
        match(input,SEMI,FOLLOW_SEMI_in_synpred32_GuardCommand1385); if (state.failed) return ;
        pushFollow(FOLLOW_command_in_synpred32_GuardCommand1389);
        i=command();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred32_GuardCommand

    // $ANTLR start synpred33_GuardCommand
    public final void synpred33_GuardCommand_fragment() throws RecognitionException {   
        GuardCommandParser.guarded_cmd_return gc = null;


        // GuardCommand.g:401:3: ( GUARD gc= guarded_cmd )
        // GuardCommand.g:401:3: GUARD gc= guarded_cmd
        {
        match(input,GUARD,FOLLOW_GUARD_in_synpred33_GuardCommand1609); if (state.failed) return ;
        pushFollow(FOLLOW_guarded_cmd_in_synpred33_GuardCommand1613);
        gc=guarded_cmd();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred33_GuardCommand

    // Delegated rules

    public final boolean synpred32_GuardCommand() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred32_GuardCommand_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
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


 

    public static final BitSet FOLLOW_NOT_in_unary_operator457 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MINUS_in_unary_operator464 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_OR_in_or480 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_AND_in_and493 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_EQ_in_eq506 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NEQ_in_neq518 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_eq_in_eqa534 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_neq_in_eqa545 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_GREATER_THAN_in_gt561 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_GREATER_EQ_in_gt_eq573 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LESS_THAN_in_lt585 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LESS_EQ_in_lt_eq597 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_gt_in_rel613 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_gt_eq_in_rel624 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_lt_in_rel635 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_lt_eq_in_rel646 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PLUS_in_plus662 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MINUS_in_minus674 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_plus_in_term690 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_minus_in_term701 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MUL_in_mul717 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DIV_in_div729 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_mul_in_factor745 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_div_in_factor756 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expr_or_in_expression781 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expr_and_in_expr_or800 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_or_in_expr_or804 = new BitSet(new long[]{0x000000F000100000L});
    public static final BitSet FOLLOW_expr_and_in_expr_or808 = new BitSet(new long[]{0x0000000000000022L});
    public static final BitSet FOLLOW_or_in_expr_or821 = new BitSet(new long[]{0x000000F000100000L});
    public static final BitSet FOLLOW_expr_and_in_expr_or825 = new BitSet(new long[]{0x0000000000000022L});
    public static final BitSet FOLLOW_expr_and_in_expr_or837 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expr_eqa_in_expr_and860 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_and_in_expr_and864 = new BitSet(new long[]{0x000000F000100000L});
    public static final BitSet FOLLOW_expr_eqa_in_expr_and868 = new BitSet(new long[]{0x0000000000000012L});
    public static final BitSet FOLLOW_and_in_expr_and881 = new BitSet(new long[]{0x000000F000100000L});
    public static final BitSet FOLLOW_expr_eqa_in_expr_and885 = new BitSet(new long[]{0x0000000000000012L});
    public static final BitSet FOLLOW_expr_eqa_in_expr_and897 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expr_rel_in_expr_eqa921 = new BitSet(new long[]{0x0000000000003000L});
    public static final BitSet FOLLOW_eqa_in_expr_eqa925 = new BitSet(new long[]{0x000000F000100000L});
    public static final BitSet FOLLOW_expr_rel_in_expr_eqa929 = new BitSet(new long[]{0x0000000000003002L});
    public static final BitSet FOLLOW_eqa_in_expr_eqa942 = new BitSet(new long[]{0x000000F000100000L});
    public static final BitSet FOLLOW_expr_rel_in_expr_eqa946 = new BitSet(new long[]{0x0000000000003002L});
    public static final BitSet FOLLOW_expr_rel_in_expr_eqa958 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expr_term_in_expr_rel980 = new BitSet(new long[]{0x0000000000000F00L});
    public static final BitSet FOLLOW_rel_in_expr_rel984 = new BitSet(new long[]{0x000000F000100000L});
    public static final BitSet FOLLOW_expr_term_in_expr_rel988 = new BitSet(new long[]{0x0000000000000F02L});
    public static final BitSet FOLLOW_rel_in_expr_rel1001 = new BitSet(new long[]{0x000000F000100000L});
    public static final BitSet FOLLOW_expr_term_in_expr_rel1005 = new BitSet(new long[]{0x0000000000000F02L});
    public static final BitSet FOLLOW_expr_term_in_expr_rel1017 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expr_factor_in_expr_term1040 = new BitSet(new long[]{0x000000000000C000L});
    public static final BitSet FOLLOW_term_in_expr_term1044 = new BitSet(new long[]{0x000000F000100000L});
    public static final BitSet FOLLOW_expr_factor_in_expr_term1048 = new BitSet(new long[]{0x000000000000C002L});
    public static final BitSet FOLLOW_term_in_expr_term1061 = new BitSet(new long[]{0x000000F000100000L});
    public static final BitSet FOLLOW_expr_factor_in_expr_term1065 = new BitSet(new long[]{0x000000000000C002L});
    public static final BitSet FOLLOW_expr_factor_in_expr_term1077 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_literal_in_expr_factor1100 = new BitSet(new long[]{0x0000000000030000L});
    public static final BitSet FOLLOW_factor_in_expr_factor1104 = new BitSet(new long[]{0x000000F000100000L});
    public static final BitSet FOLLOW_literal_in_expr_factor1108 = new BitSet(new long[]{0x0000000000030002L});
    public static final BitSet FOLLOW_factor_in_expr_factor1121 = new BitSet(new long[]{0x000000F000100000L});
    public static final BitSet FOLLOW_literal_in_expr_factor1125 = new BitSet(new long[]{0x0000000000030002L});
    public static final BitSet FOLLOW_literal_in_expr_factor1137 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_unary_operator_in_expr_unary1163 = new BitSet(new long[]{0x000000F000100000L});
    public static final BitSet FOLLOW_expression_in_expr_unary1167 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_literal_in_expr_unary1178 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INTEGER_LITERAL_in_literal1204 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_TRUE_in_literal1215 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_FALSE_in_literal1226 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IDENTIFIER_in_literal1237 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LPAREN_in_literal1246 = new BitSet(new long[]{0x000000F000100000L});
    public static final BitSet FOLLOW_expression_in_literal1250 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_RPAREN_in_literal1252 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_assignment_cmd_in_command1279 = new BitSet(new long[]{0x0000000000000082L});
    public static final BitSet FOLLOW_skip_cmd_in_command1290 = new BitSet(new long[]{0x0000000000000082L});
    public static final BitSet FOLLOW_abort_cmd_in_command1302 = new BitSet(new long[]{0x0000000000000082L});
    public static final BitSet FOLLOW_read_cmd_in_command1314 = new BitSet(new long[]{0x0000000000000082L});
    public static final BitSet FOLLOW_write_cmd_in_command1326 = new BitSet(new long[]{0x0000000000000082L});
    public static final BitSet FOLLOW_LCURLY_in_command1338 = new BitSet(new long[]{0x00000083C5400000L});
    public static final BitSet FOLLOW_command_in_command1342 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_RCURLY_in_command1344 = new BitSet(new long[]{0x0000000000000082L});
    public static final BitSet FOLLOW_if_cmd_in_command1355 = new BitSet(new long[]{0x0000000000000082L});
    public static final BitSet FOLLOW_do_cmd_in_command1369 = new BitSet(new long[]{0x0000000000000082L});
    public static final BitSet FOLLOW_SEMI_in_command1385 = new BitSet(new long[]{0x00000083C5400000L});
    public static final BitSet FOLLOW_command_in_command1389 = new BitSet(new long[]{0x0000000000000082L});
    public static final BitSet FOLLOW_IDENTIFIER_in_assignment_cmd1414 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_ASSIGN_in_assignment_cmd1418 = new BitSet(new long[]{0x000000F000100000L});
    public static final BitSet FOLLOW_expression_in_assignment_cmd1422 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_SKIP_in_skip_cmd1443 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ABORT_in_abort_cmd1464 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_READ_in_read_cmd1485 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_IDENTIFIER_in_read_cmd1489 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_WRITE_in_write_cmd1510 = new BitSet(new long[]{0x000000F000100000L});
    public static final BitSet FOLLOW_expression_in_write_cmd1512 = new BitSet(new long[]{0x000000F000100000L});
    public static final BitSet FOLLOW_expression_in_write_cmd1516 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IF_in_if_cmd1537 = new BitSet(new long[]{0x000000F000100000L});
    public static final BitSet FOLLOW_guarded_cmd_in_if_cmd1541 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_FI_in_if_cmd1543 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DO_in_do_cmd1564 = new BitSet(new long[]{0x000000F000100000L});
    public static final BitSet FOLLOW_guarded_cmd_in_do_cmd1568 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_OD_in_do_cmd1570 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_guarded_cmd1596 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_ARROW_in_guarded_cmd1598 = new BitSet(new long[]{0x00000083C5400000L});
    public static final BitSet FOLLOW_command_in_guarded_cmd1602 = new BitSet(new long[]{0x0000000010000002L});
    public static final BitSet FOLLOW_GUARD_in_guarded_cmd1609 = new BitSet(new long[]{0x000000F000100000L});
    public static final BitSet FOLLOW_guarded_cmd_in_guarded_cmd1613 = new BitSet(new long[]{0x0000000010000002L});
    public static final BitSet FOLLOW_MODULE_in_program1636 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_IDENTIFIER_in_program1638 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_COLON_in_program1640 = new BitSet(new long[]{0x00000083C5400000L});
    public static final BitSet FOLLOW_command_in_program1644 = new BitSet(new long[]{0x0000000800000000L});
    public static final BitSet FOLLOW_END_in_program1646 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expr_and_in_synpred9_GuardCommand800 = new BitSet(new long[]{0x0000000000000020L});
    public static final BitSet FOLLOW_or_in_synpred9_GuardCommand804 = new BitSet(new long[]{0x000000F000100000L});
    public static final BitSet FOLLOW_expr_and_in_synpred9_GuardCommand808 = new BitSet(new long[]{0x0000000000000022L});
    public static final BitSet FOLLOW_or_in_synpred9_GuardCommand821 = new BitSet(new long[]{0x000000F000100000L});
    public static final BitSet FOLLOW_expr_and_in_synpred9_GuardCommand825 = new BitSet(new long[]{0x0000000000000022L});
    public static final BitSet FOLLOW_expr_eqa_in_synpred11_GuardCommand860 = new BitSet(new long[]{0x0000000000000010L});
    public static final BitSet FOLLOW_and_in_synpred11_GuardCommand864 = new BitSet(new long[]{0x000000F000100000L});
    public static final BitSet FOLLOW_expr_eqa_in_synpred11_GuardCommand868 = new BitSet(new long[]{0x0000000000000012L});
    public static final BitSet FOLLOW_and_in_synpred11_GuardCommand881 = new BitSet(new long[]{0x000000F000100000L});
    public static final BitSet FOLLOW_expr_eqa_in_synpred11_GuardCommand885 = new BitSet(new long[]{0x0000000000000012L});
    public static final BitSet FOLLOW_expr_rel_in_synpred13_GuardCommand921 = new BitSet(new long[]{0x0000000000003000L});
    public static final BitSet FOLLOW_eqa_in_synpred13_GuardCommand925 = new BitSet(new long[]{0x000000F000100000L});
    public static final BitSet FOLLOW_expr_rel_in_synpred13_GuardCommand929 = new BitSet(new long[]{0x0000000000003002L});
    public static final BitSet FOLLOW_eqa_in_synpred13_GuardCommand942 = new BitSet(new long[]{0x000000F000100000L});
    public static final BitSet FOLLOW_expr_rel_in_synpred13_GuardCommand946 = new BitSet(new long[]{0x0000000000003002L});
    public static final BitSet FOLLOW_expr_term_in_synpred15_GuardCommand980 = new BitSet(new long[]{0x0000000000000F00L});
    public static final BitSet FOLLOW_rel_in_synpred15_GuardCommand984 = new BitSet(new long[]{0x000000F000100000L});
    public static final BitSet FOLLOW_expr_term_in_synpred15_GuardCommand988 = new BitSet(new long[]{0x0000000000000F02L});
    public static final BitSet FOLLOW_rel_in_synpred15_GuardCommand1001 = new BitSet(new long[]{0x000000F000100000L});
    public static final BitSet FOLLOW_expr_term_in_synpred15_GuardCommand1005 = new BitSet(new long[]{0x0000000000000F02L});
    public static final BitSet FOLLOW_expr_factor_in_synpred17_GuardCommand1040 = new BitSet(new long[]{0x000000000000C000L});
    public static final BitSet FOLLOW_term_in_synpred17_GuardCommand1044 = new BitSet(new long[]{0x000000F000100000L});
    public static final BitSet FOLLOW_expr_factor_in_synpred17_GuardCommand1048 = new BitSet(new long[]{0x000000000000C002L});
    public static final BitSet FOLLOW_term_in_synpred17_GuardCommand1061 = new BitSet(new long[]{0x000000F000100000L});
    public static final BitSet FOLLOW_expr_factor_in_synpred17_GuardCommand1065 = new BitSet(new long[]{0x000000000000C002L});
    public static final BitSet FOLLOW_literal_in_synpred19_GuardCommand1100 = new BitSet(new long[]{0x0000000000030000L});
    public static final BitSet FOLLOW_factor_in_synpred19_GuardCommand1104 = new BitSet(new long[]{0x000000F000100000L});
    public static final BitSet FOLLOW_literal_in_synpred19_GuardCommand1108 = new BitSet(new long[]{0x0000000000030002L});
    public static final BitSet FOLLOW_factor_in_synpred19_GuardCommand1121 = new BitSet(new long[]{0x000000F000100000L});
    public static final BitSet FOLLOW_literal_in_synpred19_GuardCommand1125 = new BitSet(new long[]{0x0000000000030002L});
    public static final BitSet FOLLOW_SEMI_in_synpred32_GuardCommand1385 = new BitSet(new long[]{0x00000083C5400000L});
    public static final BitSet FOLLOW_command_in_synpred32_GuardCommand1389 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_GUARD_in_synpred33_GuardCommand1609 = new BitSet(new long[]{0x000000F000100000L});
    public static final BitSet FOLLOW_guarded_cmd_in_synpred33_GuardCommand1613 = new BitSet(new long[]{0x0000000000000002L});

}