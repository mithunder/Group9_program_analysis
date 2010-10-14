// $ANTLR 3.2 debian-4 GuardCommand.g 2010-10-14 22:05:38

package com.github.mithunder.parser;

import com.github.mithunder.statements.Variable;
import com.github.mithunder.statements.VariableTable;
import com.github.mithunder.statements.StatementFactory;
import com.github.mithunder.statements.Statement;
import com.github.mithunder.statements.StatementType;
import com.github.mithunder.statements.Value;
import com.github.mithunder.statements.ValueType;
import com.github.mithunder.statements.ConstantValue;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import org.antlr.runtime.tree.*;

public class GuardCommandParser extends Parser {
    public static final String[] tokenNames = new String[] {
        "<invalid>", "<EOR>", "<DOWN>", "<UP>", "AND", "OR", "ASSIGN", "SEMI", "GREATER_THAN", "GREATER_EQ", "LESS_THAN", "LESS_EQ", "EQ", "NEQ", "PLUS", "MINUS", "MUL", "DIV", "NOT", "RPAREN", "LPAREN", "RCURLY", "LCURLY", "COLON", "IF", "FI", "DO", "OD", "GUARD", "ARROW", "SKIP", "ABORT", "WRITE", "READ", "MODULE", "END", "TRUE", "FALSE", "INTEGER_LITERAL", "IDENTIFIER", "LETTER", "WS"
    };
    public static final int LESS_EQ=11;
    public static final int GREATER_THAN=8;
    public static final int LETTER=40;
    public static final int DO=26;
    public static final int LCURLY=22;
    public static final int NOT=18;
    public static final int MINUS=15;
    public static final int MODULE=34;
    public static final int AND=4;
    public static final int EOF=-1;
    public static final int TRUE=36;
    public static final int SEMI=7;
    public static final int MUL=16;
    public static final int WRITE=32;
    public static final int OD=27;
    public static final int LPAREN=20;
    public static final int FI=25;
    public static final int IF=24;
    public static final int SKIP=30;
    public static final int COLON=23;
    public static final int RPAREN=19;
    public static final int WS=41;
    public static final int NEQ=13;
    public static final int READ=33;
    public static final int IDENTIFIER=39;
    public static final int INTEGER_LITERAL=38;
    public static final int RCURLY=21;
    public static final int OR=5;
    public static final int ASSIGN=6;
    public static final int LESS_THAN=10;
    public static final int GREATER_EQ=9;
    public static final int ARROW=29;
    public static final int PLUS=14;
    public static final int GUARD=28;
    public static final int EQ=12;
    public static final int DIV=17;
    public static final int END=35;
    public static final int ABORT=31;
    public static final int FALSE=37;

    // delegates
    // delegators


        public GuardCommandParser(TokenStream input) {
            this(input, new RecognizerSharedState());
        }
        public GuardCommandParser(TokenStream input, RecognizerSharedState state) {
            super(input, state);
            this.state.ruleMemo = new HashMap[40+1];
             
             
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


    public static class unary_operator_return extends ParserRuleReturnScope {
        public int type;
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "unary_operator"
    // GuardCommand.g:94:1: unary_operator returns [int type] : ( NOT | MINUS );
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
            // GuardCommand.g:95:2: ( NOT | MINUS )
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
                    // GuardCommand.g:95:4: NOT
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    NOT1=(Token)match(input,NOT,FOLLOW_NOT_in_unary_operator453); if (state.failed) return retval;
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
                    // GuardCommand.g:96:4: MINUS
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    MINUS2=(Token)match(input,MINUS,FOLLOW_MINUS_in_unary_operator460); if (state.failed) return retval;
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

    public static class binary_operator_return extends ParserRuleReturnScope {
        public int type;
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "binary_operator"
    // GuardCommand.g:99:1: binary_operator returns [int type] : ( AND | OR | GREATER_THAN | GREATER_EQ | LESS_THAN | LESS_EQ | EQ | NEQ | PLUS | MINUS | MUL | DIV );
    public final GuardCommandParser.binary_operator_return binary_operator() throws RecognitionException {
        GuardCommandParser.binary_operator_return retval = new GuardCommandParser.binary_operator_return();
        retval.start = input.LT(1);
        int binary_operator_StartIndex = input.index();
        CommonTree root_0 = null;

        Token AND3=null;
        Token OR4=null;
        Token GREATER_THAN5=null;
        Token GREATER_EQ6=null;
        Token LESS_THAN7=null;
        Token LESS_EQ8=null;
        Token EQ9=null;
        Token NEQ10=null;
        Token PLUS11=null;
        Token MINUS12=null;
        Token MUL13=null;
        Token DIV14=null;

        CommonTree AND3_tree=null;
        CommonTree OR4_tree=null;
        CommonTree GREATER_THAN5_tree=null;
        CommonTree GREATER_EQ6_tree=null;
        CommonTree LESS_THAN7_tree=null;
        CommonTree LESS_EQ8_tree=null;
        CommonTree EQ9_tree=null;
        CommonTree NEQ10_tree=null;
        CommonTree PLUS11_tree=null;
        CommonTree MINUS12_tree=null;
        CommonTree MUL13_tree=null;
        CommonTree DIV14_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 2) ) { return retval; }
            // GuardCommand.g:100:2: ( AND | OR | GREATER_THAN | GREATER_EQ | LESS_THAN | LESS_EQ | EQ | NEQ | PLUS | MINUS | MUL | DIV )
            int alt2=12;
            switch ( input.LA(1) ) {
            case AND:
                {
                alt2=1;
                }
                break;
            case OR:
                {
                alt2=2;
                }
                break;
            case GREATER_THAN:
                {
                alt2=3;
                }
                break;
            case GREATER_EQ:
                {
                alt2=4;
                }
                break;
            case LESS_THAN:
                {
                alt2=5;
                }
                break;
            case LESS_EQ:
                {
                alt2=6;
                }
                break;
            case EQ:
                {
                alt2=7;
                }
                break;
            case NEQ:
                {
                alt2=8;
                }
                break;
            case PLUS:
                {
                alt2=9;
                }
                break;
            case MINUS:
                {
                alt2=10;
                }
                break;
            case MUL:
                {
                alt2=11;
                }
                break;
            case DIV:
                {
                alt2=12;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 2, 0, input);

                throw nvae;
            }

            switch (alt2) {
                case 1 :
                    // GuardCommand.g:100:4: AND
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    AND3=(Token)match(input,AND,FOLLOW_AND_in_binary_operator476); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    AND3_tree = (CommonTree)adaptor.create(AND3);
                    adaptor.addChild(root_0, AND3_tree);
                    }
                    if ( state.backtracking==0 ) {
                      retval.type = StatementType.LOGIC_AND;
                    }

                    }
                    break;
                case 2 :
                    // GuardCommand.g:101:4: OR
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    OR4=(Token)match(input,OR,FOLLOW_OR_in_binary_operator483); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    OR4_tree = (CommonTree)adaptor.create(OR4);
                    adaptor.addChild(root_0, OR4_tree);
                    }
                    if ( state.backtracking==0 ) {
                      retval.type = StatementType.LOGIC_OR;
                    }

                    }
                    break;
                case 3 :
                    // GuardCommand.g:102:4: GREATER_THAN
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    GREATER_THAN5=(Token)match(input,GREATER_THAN,FOLLOW_GREATER_THAN_in_binary_operator490); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    GREATER_THAN5_tree = (CommonTree)adaptor.create(GREATER_THAN5);
                    adaptor.addChild(root_0, GREATER_THAN5_tree);
                    }
                    if ( state.backtracking==0 ) {
                      retval.type = StatementType.GT;
                    }

                    }
                    break;
                case 4 :
                    // GuardCommand.g:103:4: GREATER_EQ
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    GREATER_EQ6=(Token)match(input,GREATER_EQ,FOLLOW_GREATER_EQ_in_binary_operator497); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    GREATER_EQ6_tree = (CommonTree)adaptor.create(GREATER_EQ6);
                    adaptor.addChild(root_0, GREATER_EQ6_tree);
                    }
                    if ( state.backtracking==0 ) {
                      retval.type = StatementType.GT_EQ;
                    }

                    }
                    break;
                case 5 :
                    // GuardCommand.g:104:4: LESS_THAN
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    LESS_THAN7=(Token)match(input,LESS_THAN,FOLLOW_LESS_THAN_in_binary_operator504); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    LESS_THAN7_tree = (CommonTree)adaptor.create(LESS_THAN7);
                    adaptor.addChild(root_0, LESS_THAN7_tree);
                    }
                    if ( state.backtracking==0 ) {
                      retval.type = StatementType.LT;
                    }

                    }
                    break;
                case 6 :
                    // GuardCommand.g:105:4: LESS_EQ
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    LESS_EQ8=(Token)match(input,LESS_EQ,FOLLOW_LESS_EQ_in_binary_operator511); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    LESS_EQ8_tree = (CommonTree)adaptor.create(LESS_EQ8);
                    adaptor.addChild(root_0, LESS_EQ8_tree);
                    }
                    if ( state.backtracking==0 ) {
                      retval.type = StatementType.LT_EQ;
                    }

                    }
                    break;
                case 7 :
                    // GuardCommand.g:106:4: EQ
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    EQ9=(Token)match(input,EQ,FOLLOW_EQ_in_binary_operator518); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    EQ9_tree = (CommonTree)adaptor.create(EQ9);
                    adaptor.addChild(root_0, EQ9_tree);
                    }
                    if ( state.backtracking==0 ) {
                      retval.type = StatementType.EQ;
                    }

                    }
                    break;
                case 8 :
                    // GuardCommand.g:107:4: NEQ
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    NEQ10=(Token)match(input,NEQ,FOLLOW_NEQ_in_binary_operator525); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    NEQ10_tree = (CommonTree)adaptor.create(NEQ10);
                    adaptor.addChild(root_0, NEQ10_tree);
                    }
                    if ( state.backtracking==0 ) {
                      retval.type = StatementType.NEQ;
                    }

                    }
                    break;
                case 9 :
                    // GuardCommand.g:108:4: PLUS
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    PLUS11=(Token)match(input,PLUS,FOLLOW_PLUS_in_binary_operator532); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    PLUS11_tree = (CommonTree)adaptor.create(PLUS11);
                    adaptor.addChild(root_0, PLUS11_tree);
                    }
                    if ( state.backtracking==0 ) {
                      retval.type = StatementType.PLUS;
                    }

                    }
                    break;
                case 10 :
                    // GuardCommand.g:109:4: MINUS
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    MINUS12=(Token)match(input,MINUS,FOLLOW_MINUS_in_binary_operator539); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    MINUS12_tree = (CommonTree)adaptor.create(MINUS12);
                    adaptor.addChild(root_0, MINUS12_tree);
                    }
                    if ( state.backtracking==0 ) {
                      retval.type = StatementType.MINUS;
                    }

                    }
                    break;
                case 11 :
                    // GuardCommand.g:110:4: MUL
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    MUL13=(Token)match(input,MUL,FOLLOW_MUL_in_binary_operator546); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    MUL13_tree = (CommonTree)adaptor.create(MUL13);
                    adaptor.addChild(root_0, MUL13_tree);
                    }
                    if ( state.backtracking==0 ) {
                      retval.type = StatementType.MULTIPLY;
                    }

                    }
                    break;
                case 12 :
                    // GuardCommand.g:111:4: DIV
                    {
                    root_0 = (CommonTree)adaptor.nil();

                    DIV14=(Token)match(input,DIV,FOLLOW_DIV_in_binary_operator553); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    DIV14_tree = (CommonTree)adaptor.create(DIV14);
                    adaptor.addChild(root_0, DIV14_tree);
                    }
                    if ( state.backtracking==0 ) {
                      retval.type = StatementType.DIVIDE;
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
            if ( state.backtracking>0 ) { memoize(input, 2, binary_operator_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "binary_operator"

    public static class expression_return extends ParserRuleReturnScope {
        public List<Statement> statList;
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "expression"
    // GuardCommand.g:114:1: expression returns [List<Statement> statList] : (inte= INTEGER_LITERAL | TRUE | FALSE | id= IDENTIFIER | u= unary_operator e= expression | LPAREN e= expression RPAREN ) (b= binary_operator e= expression )* ;
    public final GuardCommandParser.expression_return expression() throws RecognitionException {
        GuardCommandParser.expression_return retval = new GuardCommandParser.expression_return();
        retval.start = input.LT(1);
        int expression_StartIndex = input.index();
        CommonTree root_0 = null;

        Token inte=null;
        Token id=null;
        Token TRUE15=null;
        Token FALSE16=null;
        Token LPAREN17=null;
        Token RPAREN18=null;
        GuardCommandParser.unary_operator_return u = null;

        GuardCommandParser.expression_return e = null;

        GuardCommandParser.binary_operator_return b = null;


        CommonTree inte_tree=null;
        CommonTree id_tree=null;
        CommonTree TRUE15_tree=null;
        CommonTree FALSE16_tree=null;
        CommonTree LPAREN17_tree=null;
        CommonTree RPAREN18_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 3) ) { return retval; }
            // GuardCommand.g:115:2: ( (inte= INTEGER_LITERAL | TRUE | FALSE | id= IDENTIFIER | u= unary_operator e= expression | LPAREN e= expression RPAREN ) (b= binary_operator e= expression )* )
            // GuardCommand.g:116:2: (inte= INTEGER_LITERAL | TRUE | FALSE | id= IDENTIFIER | u= unary_operator e= expression | LPAREN e= expression RPAREN ) (b= binary_operator e= expression )*
            {
            root_0 = (CommonTree)adaptor.nil();

            if ( state.backtracking==0 ) {

              		if (retval.statList == null) {
              			retval.statList = new ArrayList<Statement>();
              		}
              	
            }
            // GuardCommand.g:121:2: (inte= INTEGER_LITERAL | TRUE | FALSE | id= IDENTIFIER | u= unary_operator e= expression | LPAREN e= expression RPAREN )
            int alt3=6;
            switch ( input.LA(1) ) {
            case INTEGER_LITERAL:
                {
                alt3=1;
                }
                break;
            case TRUE:
                {
                alt3=2;
                }
                break;
            case FALSE:
                {
                alt3=3;
                }
                break;
            case IDENTIFIER:
                {
                alt3=4;
                }
                break;
            case MINUS:
            case NOT:
                {
                alt3=5;
                }
                break;
            case LPAREN:
                {
                alt3=6;
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
                    // GuardCommand.g:121:3: inte= INTEGER_LITERAL
                    {
                    inte=(Token)match(input,INTEGER_LITERAL,FOLLOW_INTEGER_LITERAL_in_expression576); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    inte_tree = (CommonTree)adaptor.create(inte);
                    adaptor.addChild(root_0, inte_tree);
                    }
                    if ( state.backtracking==0 ) {
                      retval.statList.add(statementFactory.createSimpleStatement(
                      			StatementType.ASSIGN,
                      			variableTable.createTemporaryVariable(),
                      			ConstantValue.getConstantValue(ValueType.INTEGER_TYPE, Integer.parseInt(inte.getText()))
                      		));
                      		
                    }

                    }
                    break;
                case 2 :
                    // GuardCommand.g:128:4: TRUE
                    {
                    TRUE15=(Token)match(input,TRUE,FOLLOW_TRUE_in_expression585); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    TRUE15_tree = (CommonTree)adaptor.create(TRUE15);
                    adaptor.addChild(root_0, TRUE15_tree);
                    }
                    if ( state.backtracking==0 ) {
                      retval.statList.add(statementFactory.createSimpleStatement(
                      			StatementType.ASSIGN,
                      			variableTable.createTemporaryVariable(),
                      			ConstantValue.TRUE
                      		));
                      		
                    }

                    }
                    break;
                case 3 :
                    // GuardCommand.g:135:4: FALSE
                    {
                    FALSE16=(Token)match(input,FALSE,FOLLOW_FALSE_in_expression594); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    FALSE16_tree = (CommonTree)adaptor.create(FALSE16);
                    adaptor.addChild(root_0, FALSE16_tree);
                    }
                    if ( state.backtracking==0 ) {
                      retval.statList.add(statementFactory.createSimpleStatement(
                      			StatementType.ASSIGN,
                      			variableTable.createTemporaryVariable(),
                      			ConstantValue.FALSE
                      		));
                      		
                    }

                    }
                    break;
                case 4 :
                    // GuardCommand.g:142:4: id= IDENTIFIER
                    {
                    id=(Token)match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_expression605); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    id_tree = (CommonTree)adaptor.create(id);
                    adaptor.addChild(root_0, id_tree);
                    }
                    if ( state.backtracking==0 ) {
                      retval.statList.add(statementFactory.createSimpleStatement(
                      			StatementType.ASSIGN,
                      			variableTable.createTemporaryVariable(),
                      			variableTable.getVariable(id.getText())
                      		));
                      		
                    }

                    }
                    break;
                case 5 :
                    // GuardCommand.g:149:4: u= unary_operator e= expression
                    {
                    pushFollow(FOLLOW_unary_operator_in_expression616);
                    u=unary_operator();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, u.getTree());
                    pushFollow(FOLLOW_expression_in_expression620);
                    e=expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, e.getTree());
                    if ( state.backtracking==0 ) {

                      		final Statement newestStat = statementFactory.createSimpleStatement(
                      			u.type,
                      			e.statList.get(e.statList.size()-1).getAssign(),
                      			variableTable.getVariable(id.getText())
                      		);
                      		retval.statList.addAll(e.statList);
                      		retval.statList.add(newestStat);
                      		
                    }

                    }
                    break;
                case 6 :
                    // GuardCommand.g:159:4: LPAREN e= expression RPAREN
                    {
                    LPAREN17=(Token)match(input,LPAREN,FOLLOW_LPAREN_in_expression629); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    LPAREN17_tree = (CommonTree)adaptor.create(LPAREN17);
                    adaptor.addChild(root_0, LPAREN17_tree);
                    }
                    pushFollow(FOLLOW_expression_in_expression633);
                    e=expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, e.getTree());
                    RPAREN18=(Token)match(input,RPAREN,FOLLOW_RPAREN_in_expression635); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    RPAREN18_tree = (CommonTree)adaptor.create(RPAREN18);
                    adaptor.addChild(root_0, RPAREN18_tree);
                    }
                    if ( state.backtracking==0 ) {
                      retval.statList.addAll(e.statList);
                    }

                    }
                    break;

            }

            // GuardCommand.g:161:2: (b= binary_operator e= expression )*
            loop4:
            do {
                int alt4=2;
                alt4 = dfa4.predict(input);
                switch (alt4) {
            	case 1 :
            	    // GuardCommand.g:161:3: b= binary_operator e= expression
            	    {
            	    pushFollow(FOLLOW_binary_operator_in_expression646);
            	    b=binary_operator();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, b.getTree());
            	    pushFollow(FOLLOW_expression_in_expression650);
            	    e=expression();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, e.getTree());
            	    if ( state.backtracking==0 ) {

            	      			final Value valStart = retval.statList.get(retval.statList.size()-1).getAssign();
            	      			final Value valEnd = e.statList.get(e.statList.size()-1).getAssign();
            	      			retval.statList.addAll(e.statList);
            	      			final Statement binaryStat = statementFactory.createSimpleStatement(
            	      				b.type, variableTable.createTemporaryVariable(), valStart, valEnd
            	      			);
            	      			retval.statList.add(binaryStat);
            	      		
            	    }

            	    }
            	    break;

            	default :
            	    break loop4;
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
            if ( state.backtracking>0 ) { memoize(input, 3, expression_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "expression"

    public static class command_return extends ParserRuleReturnScope {
        public List<Statement> commands;
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "command"
    // GuardCommand.g:174:1: command returns [List<Statement> commands] : (a= assignment_cmd | b= skip_cmd | c= abort_cmd | d= read_cmd | e= write_cmd | LCURLY f= command RCURLY | g= if_cmd | h= do_cmd ) ( SEMI i= command )* ;
    public final GuardCommandParser.command_return command() throws RecognitionException {
        GuardCommandParser.command_return retval = new GuardCommandParser.command_return();
        retval.start = input.LT(1);
        int command_StartIndex = input.index();
        CommonTree root_0 = null;

        Token LCURLY19=null;
        Token RCURLY20=null;
        Token SEMI21=null;
        GuardCommandParser.assignment_cmd_return a = null;

        GuardCommandParser.skip_cmd_return b = null;

        GuardCommandParser.abort_cmd_return c = null;

        GuardCommandParser.read_cmd_return d = null;

        GuardCommandParser.write_cmd_return e = null;

        GuardCommandParser.command_return f = null;

        GuardCommandParser.if_cmd_return g = null;

        GuardCommandParser.do_cmd_return h = null;

        GuardCommandParser.command_return i = null;


        CommonTree LCURLY19_tree=null;
        CommonTree RCURLY20_tree=null;
        CommonTree SEMI21_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 4) ) { return retval; }
            // GuardCommand.g:175:2: ( (a= assignment_cmd | b= skip_cmd | c= abort_cmd | d= read_cmd | e= write_cmd | LCURLY f= command RCURLY | g= if_cmd | h= do_cmd ) ( SEMI i= command )* )
            // GuardCommand.g:176:2: (a= assignment_cmd | b= skip_cmd | c= abort_cmd | d= read_cmd | e= write_cmd | LCURLY f= command RCURLY | g= if_cmd | h= do_cmd ) ( SEMI i= command )*
            {
            root_0 = (CommonTree)adaptor.nil();

            if ( state.backtracking==0 ) {

              		if (retval.commands == null) {
              			retval.commands = new ArrayList<Statement>();
              		}
              	
            }
            // GuardCommand.g:182:2: (a= assignment_cmd | b= skip_cmd | c= abort_cmd | d= read_cmd | e= write_cmd | LCURLY f= command RCURLY | g= if_cmd | h= do_cmd )
            int alt5=8;
            switch ( input.LA(1) ) {
            case IDENTIFIER:
                {
                alt5=1;
                }
                break;
            case SKIP:
                {
                alt5=2;
                }
                break;
            case ABORT:
                {
                alt5=3;
                }
                break;
            case READ:
                {
                alt5=4;
                }
                break;
            case WRITE:
                {
                alt5=5;
                }
                break;
            case LCURLY:
                {
                alt5=6;
                }
                break;
            case IF:
                {
                alt5=7;
                }
                break;
            case DO:
                {
                alt5=8;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 5, 0, input);

                throw nvae;
            }

            switch (alt5) {
                case 1 :
                    // GuardCommand.g:182:4: a= assignment_cmd
                    {
                    pushFollow(FOLLOW_assignment_cmd_in_command683);
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
                    // GuardCommand.g:183:4: b= skip_cmd
                    {
                    pushFollow(FOLLOW_skip_cmd_in_command694);
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
                    // GuardCommand.g:184:4: c= abort_cmd
                    {
                    pushFollow(FOLLOW_abort_cmd_in_command706);
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
                    // GuardCommand.g:185:4: d= read_cmd
                    {
                    pushFollow(FOLLOW_read_cmd_in_command718);
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
                    // GuardCommand.g:186:4: e= write_cmd
                    {
                    pushFollow(FOLLOW_write_cmd_in_command730);
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
                    // GuardCommand.g:187:4: LCURLY f= command RCURLY
                    {
                    LCURLY19=(Token)match(input,LCURLY,FOLLOW_LCURLY_in_command740); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    LCURLY19_tree = (CommonTree)adaptor.create(LCURLY19);
                    adaptor.addChild(root_0, LCURLY19_tree);
                    }
                    pushFollow(FOLLOW_command_in_command744);
                    f=command();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, f.getTree());
                    RCURLY20=(Token)match(input,RCURLY,FOLLOW_RCURLY_in_command746); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    RCURLY20_tree = (CommonTree)adaptor.create(RCURLY20);
                    adaptor.addChild(root_0, RCURLY20_tree);
                    }
                    if ( state.backtracking==0 ) {

                      			retval.commands.add(statementFactory.createCompoundStatement(StatementType.SCOPE, f.commands));
                      		
                    }

                    }
                    break;
                case 7 :
                    // GuardCommand.g:191:4: g= if_cmd
                    {
                    pushFollow(FOLLOW_if_cmd_in_command757);
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
                    // GuardCommand.g:192:4: h= do_cmd
                    {
                    pushFollow(FOLLOW_do_cmd_in_command771);
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

            // GuardCommand.g:195:2: ( SEMI i= command )*
            loop6:
            do {
                int alt6=2;
                int LA6_0 = input.LA(1);

                if ( (LA6_0==SEMI) ) {
                    int LA6_2 = input.LA(2);

                    if ( (synpred26_GuardCommand()) ) {
                        alt6=1;
                    }


                }


                switch (alt6) {
            	case 1 :
            	    // GuardCommand.g:195:3: SEMI i= command
            	    {
            	    SEMI21=(Token)match(input,SEMI,FOLLOW_SEMI_in_command787); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    SEMI21_tree = (CommonTree)adaptor.create(SEMI21);
            	    adaptor.addChild(root_0, SEMI21_tree);
            	    }
            	    pushFollow(FOLLOW_command_in_command791);
            	    i=command();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, i.getTree());
            	    if ( state.backtracking==0 ) {

            	      			retval.commands.addAll(i.commands);
            	      		
            	    }

            	    }
            	    break;

            	default :
            	    break loop6;
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
            if ( state.backtracking>0 ) { memoize(input, 4, command_StartIndex); }
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
    // GuardCommand.g:202:1: assignment_cmd returns [List<Statement> commands] : id= IDENTIFIER ASSIGN e= expression ;
    public final GuardCommandParser.assignment_cmd_return assignment_cmd() throws RecognitionException {
        GuardCommandParser.assignment_cmd_return retval = new GuardCommandParser.assignment_cmd_return();
        retval.start = input.LT(1);
        int assignment_cmd_StartIndex = input.index();
        CommonTree root_0 = null;

        Token id=null;
        Token ASSIGN22=null;
        GuardCommandParser.expression_return e = null;


        CommonTree id_tree=null;
        CommonTree ASSIGN22_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 5) ) { return retval; }
            // GuardCommand.g:203:2: (id= IDENTIFIER ASSIGN e= expression )
            // GuardCommand.g:203:4: id= IDENTIFIER ASSIGN e= expression
            {
            root_0 = (CommonTree)adaptor.nil();

            id=(Token)match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_assignment_cmd816); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            id_tree = (CommonTree)adaptor.create(id);
            adaptor.addChild(root_0, id_tree);
            }
            ASSIGN22=(Token)match(input,ASSIGN,FOLLOW_ASSIGN_in_assignment_cmd818); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            ASSIGN22_tree = (CommonTree)adaptor.create(ASSIGN22);
            adaptor.addChild(root_0, ASSIGN22_tree);
            }
            pushFollow(FOLLOW_expression_in_assignment_cmd822);
            e=expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, e.getTree());
            if ( state.backtracking==0 ) {

              			final Statement assignStatement = statementFactory.createSimpleStatement(
              				StatementType.ASSIGN,
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
            if ( state.backtracking>0 ) { memoize(input, 5, assignment_cmd_StartIndex); }
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
    // GuardCommand.g:214:1: skip_cmd returns [Statement command] : SKIP ;
    public final GuardCommandParser.skip_cmd_return skip_cmd() throws RecognitionException {
        GuardCommandParser.skip_cmd_return retval = new GuardCommandParser.skip_cmd_return();
        retval.start = input.LT(1);
        int skip_cmd_StartIndex = input.index();
        CommonTree root_0 = null;

        Token SKIP23=null;

        CommonTree SKIP23_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 6) ) { return retval; }
            // GuardCommand.g:215:2: ( SKIP )
            // GuardCommand.g:215:4: SKIP
            {
            root_0 = (CommonTree)adaptor.nil();

            SKIP23=(Token)match(input,SKIP,FOLLOW_SKIP_in_skip_cmd841); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            SKIP23_tree = (CommonTree)adaptor.create(SKIP23);
            adaptor.addChild(root_0, SKIP23_tree);
            }
            if ( state.backtracking==0 ) {
              retval.command = statementFactory.createSimpleStatement(StatementType.SKIP);
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
            if ( state.backtracking>0 ) { memoize(input, 6, skip_cmd_StartIndex); }
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
    // GuardCommand.g:218:1: abort_cmd returns [Statement command] : ABORT ;
    public final GuardCommandParser.abort_cmd_return abort_cmd() throws RecognitionException {
        GuardCommandParser.abort_cmd_return retval = new GuardCommandParser.abort_cmd_return();
        retval.start = input.LT(1);
        int abort_cmd_StartIndex = input.index();
        CommonTree root_0 = null;

        Token ABORT24=null;

        CommonTree ABORT24_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 7) ) { return retval; }
            // GuardCommand.g:219:2: ( ABORT )
            // GuardCommand.g:219:4: ABORT
            {
            root_0 = (CommonTree)adaptor.nil();

            ABORT24=(Token)match(input,ABORT,FOLLOW_ABORT_in_abort_cmd858); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            ABORT24_tree = (CommonTree)adaptor.create(ABORT24);
            adaptor.addChild(root_0, ABORT24_tree);
            }
            if ( state.backtracking==0 ) {
              retval.command = statementFactory.createSimpleStatement(StatementType.ABORT);
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
            if ( state.backtracking>0 ) { memoize(input, 7, abort_cmd_StartIndex); }
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
    // GuardCommand.g:222:1: read_cmd returns [Statement command] : READ id= IDENTIFIER ;
    public final GuardCommandParser.read_cmd_return read_cmd() throws RecognitionException {
        GuardCommandParser.read_cmd_return retval = new GuardCommandParser.read_cmd_return();
        retval.start = input.LT(1);
        int read_cmd_StartIndex = input.index();
        CommonTree root_0 = null;

        Token id=null;
        Token READ25=null;

        CommonTree id_tree=null;
        CommonTree READ25_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 8) ) { return retval; }
            // GuardCommand.g:223:2: ( READ id= IDENTIFIER )
            // GuardCommand.g:223:4: READ id= IDENTIFIER
            {
            root_0 = (CommonTree)adaptor.nil();

            READ25=(Token)match(input,READ,FOLLOW_READ_in_read_cmd875); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            READ25_tree = (CommonTree)adaptor.create(READ25);
            adaptor.addChild(root_0, READ25_tree);
            }
            id=(Token)match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_read_cmd879); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            id_tree = (CommonTree)adaptor.create(id);
            adaptor.addChild(root_0, id_tree);
            }
            if ( state.backtracking==0 ) {

              			retval.command = statementFactory.createSimpleStatement(
              				StatementType.READ, variableTable.getVariable(id.getText())
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
            if ( state.backtracking>0 ) { memoize(input, 8, read_cmd_StartIndex); }
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
    // GuardCommand.g:231:1: write_cmd returns [List<Statement> commands] : WRITE expression e= expression ;
    public final GuardCommandParser.write_cmd_return write_cmd() throws RecognitionException {
        GuardCommandParser.write_cmd_return retval = new GuardCommandParser.write_cmd_return();
        retval.start = input.LT(1);
        int write_cmd_StartIndex = input.index();
        CommonTree root_0 = null;

        Token WRITE26=null;
        GuardCommandParser.expression_return e = null;

        GuardCommandParser.expression_return expression27 = null;


        CommonTree WRITE26_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 9) ) { return retval; }
            // GuardCommand.g:232:2: ( WRITE expression e= expression )
            // GuardCommand.g:232:4: WRITE expression e= expression
            {
            root_0 = (CommonTree)adaptor.nil();

            WRITE26=(Token)match(input,WRITE,FOLLOW_WRITE_in_write_cmd898); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            WRITE26_tree = (CommonTree)adaptor.create(WRITE26);
            adaptor.addChild(root_0, WRITE26_tree);
            }
            pushFollow(FOLLOW_expression_in_write_cmd900);
            expression27=expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, expression27.getTree());
            pushFollow(FOLLOW_expression_in_write_cmd904);
            e=expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, e.getTree());
            if ( state.backtracking==0 ) {

              			final Statement assignStatement = statementFactory.createSimpleStatement(
              				StatementType.WRITE,
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
            if ( state.backtracking>0 ) { memoize(input, 9, write_cmd_StartIndex); }
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
    // GuardCommand.g:243:1: if_cmd returns [Statement command] : IF gc= guarded_cmd FI ;
    public final GuardCommandParser.if_cmd_return if_cmd() throws RecognitionException {
        GuardCommandParser.if_cmd_return retval = new GuardCommandParser.if_cmd_return();
        retval.start = input.LT(1);
        int if_cmd_StartIndex = input.index();
        CommonTree root_0 = null;

        Token IF28=null;
        Token FI29=null;
        GuardCommandParser.guarded_cmd_return gc = null;


        CommonTree IF28_tree=null;
        CommonTree FI29_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 10) ) { return retval; }
            // GuardCommand.g:244:2: ( IF gc= guarded_cmd FI )
            // GuardCommand.g:244:4: IF gc= guarded_cmd FI
            {
            root_0 = (CommonTree)adaptor.nil();

            IF28=(Token)match(input,IF,FOLLOW_IF_in_if_cmd923); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            IF28_tree = (CommonTree)adaptor.create(IF28);
            adaptor.addChild(root_0, IF28_tree);
            }
            pushFollow(FOLLOW_guarded_cmd_in_if_cmd927);
            gc=guarded_cmd();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, gc.getTree());
            FI29=(Token)match(input,FI,FOLLOW_FI_in_if_cmd929); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            FI29_tree = (CommonTree)adaptor.create(FI29);
            adaptor.addChild(root_0, FI29_tree);
            }
            if ( state.backtracking==0 ) {

              			retval.command = statementFactory.createCompoundStatement(StatementType.IF, gc.commands);
              		
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
            if ( state.backtracking>0 ) { memoize(input, 10, if_cmd_StartIndex); }
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
    // GuardCommand.g:250:1: do_cmd returns [Statement command] : DO gc= guarded_cmd OD ;
    public final GuardCommandParser.do_cmd_return do_cmd() throws RecognitionException {
        GuardCommandParser.do_cmd_return retval = new GuardCommandParser.do_cmd_return();
        retval.start = input.LT(1);
        int do_cmd_StartIndex = input.index();
        CommonTree root_0 = null;

        Token DO30=null;
        Token OD31=null;
        GuardCommandParser.guarded_cmd_return gc = null;


        CommonTree DO30_tree=null;
        CommonTree OD31_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 11) ) { return retval; }
            // GuardCommand.g:251:2: ( DO gc= guarded_cmd OD )
            // GuardCommand.g:251:4: DO gc= guarded_cmd OD
            {
            root_0 = (CommonTree)adaptor.nil();

            DO30=(Token)match(input,DO,FOLLOW_DO_in_do_cmd948); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            DO30_tree = (CommonTree)adaptor.create(DO30);
            adaptor.addChild(root_0, DO30_tree);
            }
            pushFollow(FOLLOW_guarded_cmd_in_do_cmd952);
            gc=guarded_cmd();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, gc.getTree());
            OD31=(Token)match(input,OD,FOLLOW_OD_in_do_cmd954); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            OD31_tree = (CommonTree)adaptor.create(OD31);
            adaptor.addChild(root_0, OD31_tree);
            }
            if ( state.backtracking==0 ) {

              			retval.command = statementFactory.createCompoundStatement(StatementType.DO, gc.commands);
              		
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
            if ( state.backtracking>0 ) { memoize(input, 11, do_cmd_StartIndex); }
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
    // GuardCommand.g:257:1: guarded_cmd returns [List<Statement> commands] : (e= expression ARROW c= command ) ( GUARD gc= guarded_cmd )* ;
    public final GuardCommandParser.guarded_cmd_return guarded_cmd() throws RecognitionException {
        GuardCommandParser.guarded_cmd_return retval = new GuardCommandParser.guarded_cmd_return();
        retval.start = input.LT(1);
        int guarded_cmd_StartIndex = input.index();
        CommonTree root_0 = null;

        Token ARROW32=null;
        Token GUARD33=null;
        GuardCommandParser.expression_return e = null;

        GuardCommandParser.command_return c = null;

        GuardCommandParser.guarded_cmd_return gc = null;


        CommonTree ARROW32_tree=null;
        CommonTree GUARD33_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 12) ) { return retval; }
            // GuardCommand.g:258:2: ( (e= expression ARROW c= command ) ( GUARD gc= guarded_cmd )* )
            // GuardCommand.g:259:2: (e= expression ARROW c= command ) ( GUARD gc= guarded_cmd )*
            {
            root_0 = (CommonTree)adaptor.nil();

            if ( state.backtracking==0 ) {

              		if (retval.commands == null) {
              			retval.commands = new ArrayList<Statement>();
              		}
              	
            }
            // GuardCommand.g:264:2: (e= expression ARROW c= command )
            // GuardCommand.g:264:3: e= expression ARROW c= command
            {
            pushFollow(FOLLOW_expression_in_guarded_cmd980);
            e=expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, e.getTree());
            ARROW32=(Token)match(input,ARROW,FOLLOW_ARROW_in_guarded_cmd982); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            ARROW32_tree = (CommonTree)adaptor.create(ARROW32);
            adaptor.addChild(root_0, ARROW32_tree);
            }
            pushFollow(FOLLOW_command_in_guarded_cmd986);
            c=command();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, c.getTree());

            }

            if ( state.backtracking==0 ) {

              		retval.commands.add(statementFactory.createCompoundStatement(StatementType.SCOPE, e.statList));
              		retval.commands.add(statementFactory.createCompoundStatement(StatementType.SCOPE, c.commands));
              	
            }
            // GuardCommand.g:268:2: ( GUARD gc= guarded_cmd )*
            loop7:
            do {
                int alt7=2;
                int LA7_0 = input.LA(1);

                if ( (LA7_0==GUARD) ) {
                    int LA7_2 = input.LA(2);

                    if ( (synpred27_GuardCommand()) ) {
                        alt7=1;
                    }


                }


                switch (alt7) {
            	case 1 :
            	    // GuardCommand.g:268:3: GUARD gc= guarded_cmd
            	    {
            	    GUARD33=(Token)match(input,GUARD,FOLLOW_GUARD_in_guarded_cmd993); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    GUARD33_tree = (CommonTree)adaptor.create(GUARD33);
            	    adaptor.addChild(root_0, GUARD33_tree);
            	    }
            	    pushFollow(FOLLOW_guarded_cmd_in_guarded_cmd997);
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
            	    break loop7;
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
            if ( state.backtracking>0 ) { memoize(input, 12, guarded_cmd_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "guarded_cmd"

    public static class program_return extends ParserRuleReturnScope {
        public List<Statement> commands;
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "program"
    // GuardCommand.g:271:1: program returns [List<Statement> commands] : MODULE IDENTIFIER COLON c= command END ;
    public final GuardCommandParser.program_return program() throws RecognitionException {
        GuardCommandParser.program_return retval = new GuardCommandParser.program_return();
        retval.start = input.LT(1);
        int program_StartIndex = input.index();
        CommonTree root_0 = null;

        Token MODULE34=null;
        Token IDENTIFIER35=null;
        Token COLON36=null;
        Token END37=null;
        GuardCommandParser.command_return c = null;


        CommonTree MODULE34_tree=null;
        CommonTree IDENTIFIER35_tree=null;
        CommonTree COLON36_tree=null;
        CommonTree END37_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 13) ) { return retval; }
            // GuardCommand.g:272:2: ( MODULE IDENTIFIER COLON c= command END )
            // GuardCommand.g:272:4: MODULE IDENTIFIER COLON c= command END
            {
            root_0 = (CommonTree)adaptor.nil();

            MODULE34=(Token)match(input,MODULE,FOLLOW_MODULE_in_program1018); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            MODULE34_tree = (CommonTree)adaptor.create(MODULE34);
            adaptor.addChild(root_0, MODULE34_tree);
            }
            IDENTIFIER35=(Token)match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_program1020); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            IDENTIFIER35_tree = (CommonTree)adaptor.create(IDENTIFIER35);
            adaptor.addChild(root_0, IDENTIFIER35_tree);
            }
            COLON36=(Token)match(input,COLON,FOLLOW_COLON_in_program1022); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            COLON36_tree = (CommonTree)adaptor.create(COLON36);
            adaptor.addChild(root_0, COLON36_tree);
            }
            pushFollow(FOLLOW_command_in_program1026);
            c=command();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, c.getTree());
            END37=(Token)match(input,END,FOLLOW_END_in_program1028); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            END37_tree = (CommonTree)adaptor.create(END37);
            adaptor.addChild(root_0, END37_tree);
            }
            if ( state.backtracking==0 ) {
              retval.commands = c.commands;
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
            if ( state.backtracking>0 ) { memoize(input, 13, program_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "program"

    // $ANTLR start synpred18_GuardCommand
    public final void synpred18_GuardCommand_fragment() throws RecognitionException {   
        GuardCommandParser.binary_operator_return b = null;

        GuardCommandParser.expression_return e = null;


        // GuardCommand.g:161:3: (b= binary_operator e= expression )
        // GuardCommand.g:161:3: b= binary_operator e= expression
        {
        pushFollow(FOLLOW_binary_operator_in_synpred18_GuardCommand646);
        b=binary_operator();

        state._fsp--;
        if (state.failed) return ;
        pushFollow(FOLLOW_expression_in_synpred18_GuardCommand650);
        e=expression();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred18_GuardCommand

    // $ANTLR start synpred26_GuardCommand
    public final void synpred26_GuardCommand_fragment() throws RecognitionException {   
        GuardCommandParser.command_return i = null;


        // GuardCommand.g:195:3: ( SEMI i= command )
        // GuardCommand.g:195:3: SEMI i= command
        {
        match(input,SEMI,FOLLOW_SEMI_in_synpred26_GuardCommand787); if (state.failed) return ;
        pushFollow(FOLLOW_command_in_synpred26_GuardCommand791);
        i=command();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred26_GuardCommand

    // $ANTLR start synpred27_GuardCommand
    public final void synpred27_GuardCommand_fragment() throws RecognitionException {   
        GuardCommandParser.guarded_cmd_return gc = null;


        // GuardCommand.g:268:3: ( GUARD gc= guarded_cmd )
        // GuardCommand.g:268:3: GUARD gc= guarded_cmd
        {
        match(input,GUARD,FOLLOW_GUARD_in_synpred27_GuardCommand993); if (state.failed) return ;
        pushFollow(FOLLOW_guarded_cmd_in_synpred27_GuardCommand997);
        gc=guarded_cmd();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred27_GuardCommand

    // Delegated rules

    public final boolean synpred26_GuardCommand() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred26_GuardCommand_fragment(); // can never throw exception
        } catch (RecognitionException re) {
            System.err.println("impossible: "+re);
        }
        boolean success = !state.failed;
        input.rewind(start);
        state.backtracking--;
        state.failed=false;
        return success;
    }
    public final boolean synpred27_GuardCommand() {
        state.backtracking++;
        int start = input.mark();
        try {
            synpred27_GuardCommand_fragment(); // can never throw exception
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


    protected DFA4 dfa4 = new DFA4(this);
    static final String DFA4_eotS =
        "\17\uffff";
    static final String DFA4_eofS =
        "\1\15\16\uffff";
    static final String DFA4_minS =
        "\1\4\14\0\2\uffff";
    static final String DFA4_maxS =
        "\1\47\14\0\2\uffff";
    static final String DFA4_acceptS =
        "\15\uffff\1\2\1\1";
    static final String DFA4_specialS =
        "\1\uffff\1\0\1\10\1\7\1\5\1\3\1\12\1\11\1\1\1\4\1\2\1\13\1\6\2\uffff}>";
    static final String[] DFA4_transitionS = {
            "\1\1\1\2\1\uffff\1\15\1\3\1\4\1\5\1\6\1\7\1\10\1\11\1\12\1\13"+
            "\1\14\4\15\3\uffff\1\15\1\uffff\3\15\5\uffff\5\15",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
            "\1\uffff",
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

    static final short[] DFA4_eot = DFA.unpackEncodedString(DFA4_eotS);
    static final short[] DFA4_eof = DFA.unpackEncodedString(DFA4_eofS);
    static final char[] DFA4_min = DFA.unpackEncodedStringToUnsignedChars(DFA4_minS);
    static final char[] DFA4_max = DFA.unpackEncodedStringToUnsignedChars(DFA4_maxS);
    static final short[] DFA4_accept = DFA.unpackEncodedString(DFA4_acceptS);
    static final short[] DFA4_special = DFA.unpackEncodedString(DFA4_specialS);
    static final short[][] DFA4_transition;

    static {
        int numStates = DFA4_transitionS.length;
        DFA4_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA4_transition[i] = DFA.unpackEncodedString(DFA4_transitionS[i]);
        }
    }

    class DFA4 extends DFA {

        public DFA4(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 4;
            this.eot = DFA4_eot;
            this.eof = DFA4_eof;
            this.min = DFA4_min;
            this.max = DFA4_max;
            this.accept = DFA4_accept;
            this.special = DFA4_special;
            this.transition = DFA4_transition;
        }
        public String getDescription() {
            return "()* loopback of 161:2: (b= binary_operator e= expression )*";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            TokenStream input = (TokenStream)_input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA4_1 = input.LA(1);

                         
                        int index4_1 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred18_GuardCommand()) ) {s = 14;}

                        else if ( (true) ) {s = 13;}

                         
                        input.seek(index4_1);
                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA4_8 = input.LA(1);

                         
                        int index4_8 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred18_GuardCommand()) ) {s = 14;}

                        else if ( (true) ) {s = 13;}

                         
                        input.seek(index4_8);
                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA4_10 = input.LA(1);

                         
                        int index4_10 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred18_GuardCommand()) ) {s = 14;}

                        else if ( (true) ) {s = 13;}

                         
                        input.seek(index4_10);
                        if ( s>=0 ) return s;
                        break;
                    case 3 : 
                        int LA4_5 = input.LA(1);

                         
                        int index4_5 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred18_GuardCommand()) ) {s = 14;}

                        else if ( (true) ) {s = 13;}

                         
                        input.seek(index4_5);
                        if ( s>=0 ) return s;
                        break;
                    case 4 : 
                        int LA4_9 = input.LA(1);

                         
                        int index4_9 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred18_GuardCommand()) ) {s = 14;}

                        else if ( (true) ) {s = 13;}

                         
                        input.seek(index4_9);
                        if ( s>=0 ) return s;
                        break;
                    case 5 : 
                        int LA4_4 = input.LA(1);

                         
                        int index4_4 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred18_GuardCommand()) ) {s = 14;}

                        else if ( (true) ) {s = 13;}

                         
                        input.seek(index4_4);
                        if ( s>=0 ) return s;
                        break;
                    case 6 : 
                        int LA4_12 = input.LA(1);

                         
                        int index4_12 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred18_GuardCommand()) ) {s = 14;}

                        else if ( (true) ) {s = 13;}

                         
                        input.seek(index4_12);
                        if ( s>=0 ) return s;
                        break;
                    case 7 : 
                        int LA4_3 = input.LA(1);

                         
                        int index4_3 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred18_GuardCommand()) ) {s = 14;}

                        else if ( (true) ) {s = 13;}

                         
                        input.seek(index4_3);
                        if ( s>=0 ) return s;
                        break;
                    case 8 : 
                        int LA4_2 = input.LA(1);

                         
                        int index4_2 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred18_GuardCommand()) ) {s = 14;}

                        else if ( (true) ) {s = 13;}

                         
                        input.seek(index4_2);
                        if ( s>=0 ) return s;
                        break;
                    case 9 : 
                        int LA4_7 = input.LA(1);

                         
                        int index4_7 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred18_GuardCommand()) ) {s = 14;}

                        else if ( (true) ) {s = 13;}

                         
                        input.seek(index4_7);
                        if ( s>=0 ) return s;
                        break;
                    case 10 : 
                        int LA4_6 = input.LA(1);

                         
                        int index4_6 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred18_GuardCommand()) ) {s = 14;}

                        else if ( (true) ) {s = 13;}

                         
                        input.seek(index4_6);
                        if ( s>=0 ) return s;
                        break;
                    case 11 : 
                        int LA4_11 = input.LA(1);

                         
                        int index4_11 = input.index();
                        input.rewind();
                        s = -1;
                        if ( (synpred18_GuardCommand()) ) {s = 14;}

                        else if ( (true) ) {s = 13;}

                         
                        input.seek(index4_11);
                        if ( s>=0 ) return s;
                        break;
            }
            if (state.backtracking>0) {state.failed=true; return -1;}
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 4, _s, input);
            error(nvae);
            throw nvae;
        }
    }
 

    public static final BitSet FOLLOW_NOT_in_unary_operator453 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MINUS_in_unary_operator460 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_AND_in_binary_operator476 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_OR_in_binary_operator483 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_GREATER_THAN_in_binary_operator490 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_GREATER_EQ_in_binary_operator497 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LESS_THAN_in_binary_operator504 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_LESS_EQ_in_binary_operator511 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_EQ_in_binary_operator518 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_NEQ_in_binary_operator525 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_PLUS_in_binary_operator532 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MINUS_in_binary_operator539 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_MUL_in_binary_operator546 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DIV_in_binary_operator553 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INTEGER_LITERAL_in_expression576 = new BitSet(new long[]{0x000000000003FF32L});
    public static final BitSet FOLLOW_TRUE_in_expression585 = new BitSet(new long[]{0x000000000003FF32L});
    public static final BitSet FOLLOW_FALSE_in_expression594 = new BitSet(new long[]{0x000000000003FF32L});
    public static final BitSet FOLLOW_IDENTIFIER_in_expression605 = new BitSet(new long[]{0x000000000003FF32L});
    public static final BitSet FOLLOW_unary_operator_in_expression616 = new BitSet(new long[]{0x000000F000148000L});
    public static final BitSet FOLLOW_expression_in_expression620 = new BitSet(new long[]{0x000000000003FF32L});
    public static final BitSet FOLLOW_LPAREN_in_expression629 = new BitSet(new long[]{0x000000F000148000L});
    public static final BitSet FOLLOW_expression_in_expression633 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_RPAREN_in_expression635 = new BitSet(new long[]{0x000000000003FF32L});
    public static final BitSet FOLLOW_binary_operator_in_expression646 = new BitSet(new long[]{0x000000F000148000L});
    public static final BitSet FOLLOW_expression_in_expression650 = new BitSet(new long[]{0x000000000003FF32L});
    public static final BitSet FOLLOW_assignment_cmd_in_command683 = new BitSet(new long[]{0x0000000000000082L});
    public static final BitSet FOLLOW_skip_cmd_in_command694 = new BitSet(new long[]{0x0000000000000082L});
    public static final BitSet FOLLOW_abort_cmd_in_command706 = new BitSet(new long[]{0x0000000000000082L});
    public static final BitSet FOLLOW_read_cmd_in_command718 = new BitSet(new long[]{0x0000000000000082L});
    public static final BitSet FOLLOW_write_cmd_in_command730 = new BitSet(new long[]{0x0000000000000082L});
    public static final BitSet FOLLOW_LCURLY_in_command740 = new BitSet(new long[]{0x00000083C5400000L});
    public static final BitSet FOLLOW_command_in_command744 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_RCURLY_in_command746 = new BitSet(new long[]{0x0000000000000082L});
    public static final BitSet FOLLOW_if_cmd_in_command757 = new BitSet(new long[]{0x0000000000000082L});
    public static final BitSet FOLLOW_do_cmd_in_command771 = new BitSet(new long[]{0x0000000000000082L});
    public static final BitSet FOLLOW_SEMI_in_command787 = new BitSet(new long[]{0x00000083C5400000L});
    public static final BitSet FOLLOW_command_in_command791 = new BitSet(new long[]{0x0000000000000082L});
    public static final BitSet FOLLOW_IDENTIFIER_in_assignment_cmd816 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_ASSIGN_in_assignment_cmd818 = new BitSet(new long[]{0x000000F000148000L});
    public static final BitSet FOLLOW_expression_in_assignment_cmd822 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_SKIP_in_skip_cmd841 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ABORT_in_abort_cmd858 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_READ_in_read_cmd875 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_IDENTIFIER_in_read_cmd879 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_WRITE_in_write_cmd898 = new BitSet(new long[]{0x000000F000148000L});
    public static final BitSet FOLLOW_expression_in_write_cmd900 = new BitSet(new long[]{0x000000F000148000L});
    public static final BitSet FOLLOW_expression_in_write_cmd904 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IF_in_if_cmd923 = new BitSet(new long[]{0x000000F000148000L});
    public static final BitSet FOLLOW_guarded_cmd_in_if_cmd927 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_FI_in_if_cmd929 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DO_in_do_cmd948 = new BitSet(new long[]{0x000000F000148000L});
    public static final BitSet FOLLOW_guarded_cmd_in_do_cmd952 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_OD_in_do_cmd954 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_guarded_cmd980 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_ARROW_in_guarded_cmd982 = new BitSet(new long[]{0x00000083C5400000L});
    public static final BitSet FOLLOW_command_in_guarded_cmd986 = new BitSet(new long[]{0x0000000010000002L});
    public static final BitSet FOLLOW_GUARD_in_guarded_cmd993 = new BitSet(new long[]{0x000000F000148000L});
    public static final BitSet FOLLOW_guarded_cmd_in_guarded_cmd997 = new BitSet(new long[]{0x0000000010000002L});
    public static final BitSet FOLLOW_MODULE_in_program1018 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_IDENTIFIER_in_program1020 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_COLON_in_program1022 = new BitSet(new long[]{0x00000083C5400000L});
    public static final BitSet FOLLOW_command_in_program1026 = new BitSet(new long[]{0x0000000800000000L});
    public static final BitSet FOLLOW_END_in_program1028 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_binary_operator_in_synpred18_GuardCommand646 = new BitSet(new long[]{0x000000F000148000L});
    public static final BitSet FOLLOW_expression_in_synpred18_GuardCommand650 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_SEMI_in_synpred26_GuardCommand787 = new BitSet(new long[]{0x00000083C5400000L});
    public static final BitSet FOLLOW_command_in_synpred26_GuardCommand791 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_GUARD_in_synpred27_GuardCommand993 = new BitSet(new long[]{0x000000F000148000L});
    public static final BitSet FOLLOW_guarded_cmd_in_synpred27_GuardCommand997 = new BitSet(new long[]{0x0000000000000002L});

}