// $ANTLR 3.2 debian-4 GuardCommand.g 2010-10-13 20:46:13

package com.github.mithunder.parser;


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


    public static class unary_operator_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "unary_operator"
    // GuardCommand.g:82:1: unary_operator : ( NOT | MINUS );
    public final GuardCommandParser.unary_operator_return unary_operator() throws RecognitionException {
        GuardCommandParser.unary_operator_return retval = new GuardCommandParser.unary_operator_return();
        retval.start = input.LT(1);
        int unary_operator_StartIndex = input.index();
        CommonTree root_0 = null;

        Token set1=null;

        CommonTree set1_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 1) ) { return retval; }
            // GuardCommand.g:83:2: ( NOT | MINUS )
            // GuardCommand.g:
            {
            root_0 = (CommonTree)adaptor.nil();

            set1=(Token)input.LT(1);
            if ( input.LA(1)==MINUS||input.LA(1)==NOT ) {
                input.consume();
                if ( state.backtracking==0 ) adaptor.addChild(root_0, (CommonTree)adaptor.create(set1));
                state.errorRecovery=false;state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
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
            if ( state.backtracking>0 ) { memoize(input, 1, unary_operator_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "unary_operator"

    public static class binary_operator_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "binary_operator"
    // GuardCommand.g:87:1: binary_operator : ( AND | OR | GREATER_THAN | GREATER_EQ | LESS_THAN | LESS_EQ | EQ | NEQ | PLUS | MINUS | MUL | DIV );
    public final GuardCommandParser.binary_operator_return binary_operator() throws RecognitionException {
        GuardCommandParser.binary_operator_return retval = new GuardCommandParser.binary_operator_return();
        retval.start = input.LT(1);
        int binary_operator_StartIndex = input.index();
        CommonTree root_0 = null;

        Token set2=null;

        CommonTree set2_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 2) ) { return retval; }
            // GuardCommand.g:88:2: ( AND | OR | GREATER_THAN | GREATER_EQ | LESS_THAN | LESS_EQ | EQ | NEQ | PLUS | MINUS | MUL | DIV )
            // GuardCommand.g:
            {
            root_0 = (CommonTree)adaptor.nil();

            set2=(Token)input.LT(1);
            if ( (input.LA(1)>=AND && input.LA(1)<=OR)||(input.LA(1)>=GREATER_THAN && input.LA(1)<=DIV) ) {
                input.consume();
                if ( state.backtracking==0 ) adaptor.addChild(root_0, (CommonTree)adaptor.create(set2));
                state.errorRecovery=false;state.failed=false;
            }
            else {
                if (state.backtracking>0) {state.failed=true; return retval;}
                MismatchedSetException mse = new MismatchedSetException(null,input);
                throw mse;
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
            if ( state.backtracking>0 ) { memoize(input, 2, binary_operator_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "binary_operator"

    public static class expression_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "expression"
    // GuardCommand.g:102:1: expression : ( INTEGER_LITERAL | TRUE | FALSE | IDENTIFIER | unary_operator expression | LPAREN expression RPAREN ) ( binary_operator expression )* ;
    public final GuardCommandParser.expression_return expression() throws RecognitionException {
        GuardCommandParser.expression_return retval = new GuardCommandParser.expression_return();
        retval.start = input.LT(1);
        int expression_StartIndex = input.index();
        CommonTree root_0 = null;

        Token INTEGER_LITERAL3=null;
        Token TRUE4=null;
        Token FALSE5=null;
        Token IDENTIFIER6=null;
        Token LPAREN9=null;
        Token RPAREN11=null;
        GuardCommandParser.unary_operator_return unary_operator7 = null;

        GuardCommandParser.expression_return expression8 = null;

        GuardCommandParser.expression_return expression10 = null;

        GuardCommandParser.binary_operator_return binary_operator12 = null;

        GuardCommandParser.expression_return expression13 = null;


        CommonTree INTEGER_LITERAL3_tree=null;
        CommonTree TRUE4_tree=null;
        CommonTree FALSE5_tree=null;
        CommonTree IDENTIFIER6_tree=null;
        CommonTree LPAREN9_tree=null;
        CommonTree RPAREN11_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 3) ) { return retval; }
            // GuardCommand.g:103:2: ( ( INTEGER_LITERAL | TRUE | FALSE | IDENTIFIER | unary_operator expression | LPAREN expression RPAREN ) ( binary_operator expression )* )
            // GuardCommand.g:103:4: ( INTEGER_LITERAL | TRUE | FALSE | IDENTIFIER | unary_operator expression | LPAREN expression RPAREN ) ( binary_operator expression )*
            {
            root_0 = (CommonTree)adaptor.nil();

            // GuardCommand.g:103:4: ( INTEGER_LITERAL | TRUE | FALSE | IDENTIFIER | unary_operator expression | LPAREN expression RPAREN )
            int alt1=6;
            switch ( input.LA(1) ) {
            case INTEGER_LITERAL:
                {
                alt1=1;
                }
                break;
            case TRUE:
                {
                alt1=2;
                }
                break;
            case FALSE:
                {
                alt1=3;
                }
                break;
            case IDENTIFIER:
                {
                alt1=4;
                }
                break;
            case MINUS:
            case NOT:
                {
                alt1=5;
                }
                break;
            case LPAREN:
                {
                alt1=6;
                }
                break;
            default:
                if (state.backtracking>0) {state.failed=true; return retval;}
                NoViableAltException nvae =
                    new NoViableAltException("", 1, 0, input);

                throw nvae;
            }

            switch (alt1) {
                case 1 :
                    // GuardCommand.g:103:5: INTEGER_LITERAL
                    {
                    INTEGER_LITERAL3=(Token)match(input,INTEGER_LITERAL,FOLLOW_INTEGER_LITERAL_in_expression533); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    INTEGER_LITERAL3_tree = (CommonTree)adaptor.create(INTEGER_LITERAL3);
                    adaptor.addChild(root_0, INTEGER_LITERAL3_tree);
                    }

                    }
                    break;
                case 2 :
                    // GuardCommand.g:103:23: TRUE
                    {
                    TRUE4=(Token)match(input,TRUE,FOLLOW_TRUE_in_expression537); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    TRUE4_tree = (CommonTree)adaptor.create(TRUE4);
                    adaptor.addChild(root_0, TRUE4_tree);
                    }

                    }
                    break;
                case 3 :
                    // GuardCommand.g:103:30: FALSE
                    {
                    FALSE5=(Token)match(input,FALSE,FOLLOW_FALSE_in_expression541); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    FALSE5_tree = (CommonTree)adaptor.create(FALSE5);
                    adaptor.addChild(root_0, FALSE5_tree);
                    }

                    }
                    break;
                case 4 :
                    // GuardCommand.g:103:38: IDENTIFIER
                    {
                    IDENTIFIER6=(Token)match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_expression545); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    IDENTIFIER6_tree = (CommonTree)adaptor.create(IDENTIFIER6);
                    adaptor.addChild(root_0, IDENTIFIER6_tree);
                    }

                    }
                    break;
                case 5 :
                    // GuardCommand.g:103:51: unary_operator expression
                    {
                    pushFollow(FOLLOW_unary_operator_in_expression549);
                    unary_operator7=unary_operator();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, unary_operator7.getTree());
                    pushFollow(FOLLOW_expression_in_expression551);
                    expression8=expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, expression8.getTree());

                    }
                    break;
                case 6 :
                    // GuardCommand.g:103:79: LPAREN expression RPAREN
                    {
                    LPAREN9=(Token)match(input,LPAREN,FOLLOW_LPAREN_in_expression555); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    LPAREN9_tree = (CommonTree)adaptor.create(LPAREN9);
                    adaptor.addChild(root_0, LPAREN9_tree);
                    }
                    pushFollow(FOLLOW_expression_in_expression557);
                    expression10=expression();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, expression10.getTree());
                    RPAREN11=(Token)match(input,RPAREN,FOLLOW_RPAREN_in_expression559); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    RPAREN11_tree = (CommonTree)adaptor.create(RPAREN11);
                    adaptor.addChild(root_0, RPAREN11_tree);
                    }

                    }
                    break;

            }

            // GuardCommand.g:103:105: ( binary_operator expression )*
            loop2:
            do {
                int alt2=2;
                int LA2_0 = input.LA(1);

                if ( ((LA2_0>=AND && LA2_0<=OR)||(LA2_0>=GREATER_THAN && LA2_0<=DIV)) ) {
                    int LA2_1 = input.LA(2);

                    if ( (synpred18_GuardCommand()) ) {
                        alt2=1;
                    }


                }


                switch (alt2) {
            	case 1 :
            	    // GuardCommand.g:103:106: binary_operator expression
            	    {
            	    pushFollow(FOLLOW_binary_operator_in_expression563);
            	    binary_operator12=binary_operator();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, binary_operator12.getTree());
            	    pushFollow(FOLLOW_expression_in_expression565);
            	    expression13=expression();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, expression13.getTree());

            	    }
            	    break;

            	default :
            	    break loop2;
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
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "command"
    // GuardCommand.g:106:1: command : ( assignment_cmd | skip_cmd | abort_cmd | read_cmd | write_cmd | LCURLY command RCURLY | if_cmd | do_cmd ) ( SEMI command )* ;
    public final GuardCommandParser.command_return command() throws RecognitionException {
        GuardCommandParser.command_return retval = new GuardCommandParser.command_return();
        retval.start = input.LT(1);
        int command_StartIndex = input.index();
        CommonTree root_0 = null;

        Token LCURLY19=null;
        Token RCURLY21=null;
        Token SEMI24=null;
        GuardCommandParser.assignment_cmd_return assignment_cmd14 = null;

        GuardCommandParser.skip_cmd_return skip_cmd15 = null;

        GuardCommandParser.abort_cmd_return abort_cmd16 = null;

        GuardCommandParser.read_cmd_return read_cmd17 = null;

        GuardCommandParser.write_cmd_return write_cmd18 = null;

        GuardCommandParser.command_return command20 = null;

        GuardCommandParser.if_cmd_return if_cmd22 = null;

        GuardCommandParser.do_cmd_return do_cmd23 = null;

        GuardCommandParser.command_return command25 = null;


        CommonTree LCURLY19_tree=null;
        CommonTree RCURLY21_tree=null;
        CommonTree SEMI24_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 4) ) { return retval; }
            // GuardCommand.g:107:2: ( ( assignment_cmd | skip_cmd | abort_cmd | read_cmd | write_cmd | LCURLY command RCURLY | if_cmd | do_cmd ) ( SEMI command )* )
            // GuardCommand.g:107:4: ( assignment_cmd | skip_cmd | abort_cmd | read_cmd | write_cmd | LCURLY command RCURLY | if_cmd | do_cmd ) ( SEMI command )*
            {
            root_0 = (CommonTree)adaptor.nil();

            // GuardCommand.g:107:4: ( assignment_cmd | skip_cmd | abort_cmd | read_cmd | write_cmd | LCURLY command RCURLY | if_cmd | do_cmd )
            int alt3=8;
            switch ( input.LA(1) ) {
            case IDENTIFIER:
                {
                alt3=1;
                }
                break;
            case SKIP:
                {
                alt3=2;
                }
                break;
            case ABORT:
                {
                alt3=3;
                }
                break;
            case READ:
                {
                alt3=4;
                }
                break;
            case WRITE:
                {
                alt3=5;
                }
                break;
            case LCURLY:
                {
                alt3=6;
                }
                break;
            case IF:
                {
                alt3=7;
                }
                break;
            case DO:
                {
                alt3=8;
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
                    // GuardCommand.g:107:5: assignment_cmd
                    {
                    pushFollow(FOLLOW_assignment_cmd_in_command579);
                    assignment_cmd14=assignment_cmd();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, assignment_cmd14.getTree());

                    }
                    break;
                case 2 :
                    // GuardCommand.g:107:22: skip_cmd
                    {
                    pushFollow(FOLLOW_skip_cmd_in_command583);
                    skip_cmd15=skip_cmd();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, skip_cmd15.getTree());

                    }
                    break;
                case 3 :
                    // GuardCommand.g:107:33: abort_cmd
                    {
                    pushFollow(FOLLOW_abort_cmd_in_command587);
                    abort_cmd16=abort_cmd();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, abort_cmd16.getTree());

                    }
                    break;
                case 4 :
                    // GuardCommand.g:107:45: read_cmd
                    {
                    pushFollow(FOLLOW_read_cmd_in_command591);
                    read_cmd17=read_cmd();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, read_cmd17.getTree());

                    }
                    break;
                case 5 :
                    // GuardCommand.g:107:56: write_cmd
                    {
                    pushFollow(FOLLOW_write_cmd_in_command595);
                    write_cmd18=write_cmd();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, write_cmd18.getTree());

                    }
                    break;
                case 6 :
                    // GuardCommand.g:107:68: LCURLY command RCURLY
                    {
                    LCURLY19=(Token)match(input,LCURLY,FOLLOW_LCURLY_in_command599); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    LCURLY19_tree = (CommonTree)adaptor.create(LCURLY19);
                    adaptor.addChild(root_0, LCURLY19_tree);
                    }
                    pushFollow(FOLLOW_command_in_command601);
                    command20=command();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, command20.getTree());
                    RCURLY21=(Token)match(input,RCURLY,FOLLOW_RCURLY_in_command603); if (state.failed) return retval;
                    if ( state.backtracking==0 ) {
                    RCURLY21_tree = (CommonTree)adaptor.create(RCURLY21);
                    adaptor.addChild(root_0, RCURLY21_tree);
                    }

                    }
                    break;
                case 7 :
                    // GuardCommand.g:107:92: if_cmd
                    {
                    pushFollow(FOLLOW_if_cmd_in_command607);
                    if_cmd22=if_cmd();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, if_cmd22.getTree());

                    }
                    break;
                case 8 :
                    // GuardCommand.g:107:101: do_cmd
                    {
                    pushFollow(FOLLOW_do_cmd_in_command611);
                    do_cmd23=do_cmd();

                    state._fsp--;
                    if (state.failed) return retval;
                    if ( state.backtracking==0 ) adaptor.addChild(root_0, do_cmd23.getTree());

                    }
                    break;

            }

            // GuardCommand.g:107:109: ( SEMI command )*
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( (LA4_0==SEMI) ) {
                    int LA4_2 = input.LA(2);

                    if ( (synpred26_GuardCommand()) ) {
                        alt4=1;
                    }


                }


                switch (alt4) {
            	case 1 :
            	    // GuardCommand.g:107:110: SEMI command
            	    {
            	    SEMI24=(Token)match(input,SEMI,FOLLOW_SEMI_in_command615); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    SEMI24_tree = (CommonTree)adaptor.create(SEMI24);
            	    adaptor.addChild(root_0, SEMI24_tree);
            	    }
            	    pushFollow(FOLLOW_command_in_command617);
            	    command25=command();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, command25.getTree());

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
            if ( state.backtracking>0 ) { memoize(input, 4, command_StartIndex); }
        }
        return retval;
    }
    // $ANTLR end "command"

    public static class assignment_cmd_return extends ParserRuleReturnScope {
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "assignment_cmd"
    // GuardCommand.g:110:1: assignment_cmd : IDENTIFIER ASSIGN expression ;
    public final GuardCommandParser.assignment_cmd_return assignment_cmd() throws RecognitionException {
        GuardCommandParser.assignment_cmd_return retval = new GuardCommandParser.assignment_cmd_return();
        retval.start = input.LT(1);
        int assignment_cmd_StartIndex = input.index();
        CommonTree root_0 = null;

        Token IDENTIFIER26=null;
        Token ASSIGN27=null;
        GuardCommandParser.expression_return expression28 = null;


        CommonTree IDENTIFIER26_tree=null;
        CommonTree ASSIGN27_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 5) ) { return retval; }
            // GuardCommand.g:111:2: ( IDENTIFIER ASSIGN expression )
            // GuardCommand.g:111:4: IDENTIFIER ASSIGN expression
            {
            root_0 = (CommonTree)adaptor.nil();

            IDENTIFIER26=(Token)match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_assignment_cmd630); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            IDENTIFIER26_tree = (CommonTree)adaptor.create(IDENTIFIER26);
            adaptor.addChild(root_0, IDENTIFIER26_tree);
            }
            ASSIGN27=(Token)match(input,ASSIGN,FOLLOW_ASSIGN_in_assignment_cmd632); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            ASSIGN27_tree = (CommonTree)adaptor.create(ASSIGN27);
            adaptor.addChild(root_0, ASSIGN27_tree);
            }
            pushFollow(FOLLOW_expression_in_assignment_cmd634);
            expression28=expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, expression28.getTree());

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
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "skip_cmd"
    // GuardCommand.g:114:1: skip_cmd : SKIP ;
    public final GuardCommandParser.skip_cmd_return skip_cmd() throws RecognitionException {
        GuardCommandParser.skip_cmd_return retval = new GuardCommandParser.skip_cmd_return();
        retval.start = input.LT(1);
        int skip_cmd_StartIndex = input.index();
        CommonTree root_0 = null;

        Token SKIP29=null;

        CommonTree SKIP29_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 6) ) { return retval; }
            // GuardCommand.g:115:2: ( SKIP )
            // GuardCommand.g:115:4: SKIP
            {
            root_0 = (CommonTree)adaptor.nil();

            SKIP29=(Token)match(input,SKIP,FOLLOW_SKIP_in_skip_cmd645); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            SKIP29_tree = (CommonTree)adaptor.create(SKIP29);
            adaptor.addChild(root_0, SKIP29_tree);
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
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "abort_cmd"
    // GuardCommand.g:118:1: abort_cmd : ABORT ;
    public final GuardCommandParser.abort_cmd_return abort_cmd() throws RecognitionException {
        GuardCommandParser.abort_cmd_return retval = new GuardCommandParser.abort_cmd_return();
        retval.start = input.LT(1);
        int abort_cmd_StartIndex = input.index();
        CommonTree root_0 = null;

        Token ABORT30=null;

        CommonTree ABORT30_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 7) ) { return retval; }
            // GuardCommand.g:119:2: ( ABORT )
            // GuardCommand.g:119:4: ABORT
            {
            root_0 = (CommonTree)adaptor.nil();

            ABORT30=(Token)match(input,ABORT,FOLLOW_ABORT_in_abort_cmd656); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            ABORT30_tree = (CommonTree)adaptor.create(ABORT30);
            adaptor.addChild(root_0, ABORT30_tree);
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
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "read_cmd"
    // GuardCommand.g:122:1: read_cmd : READ IDENTIFIER ;
    public final GuardCommandParser.read_cmd_return read_cmd() throws RecognitionException {
        GuardCommandParser.read_cmd_return retval = new GuardCommandParser.read_cmd_return();
        retval.start = input.LT(1);
        int read_cmd_StartIndex = input.index();
        CommonTree root_0 = null;

        Token READ31=null;
        Token IDENTIFIER32=null;

        CommonTree READ31_tree=null;
        CommonTree IDENTIFIER32_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 8) ) { return retval; }
            // GuardCommand.g:123:2: ( READ IDENTIFIER )
            // GuardCommand.g:123:4: READ IDENTIFIER
            {
            root_0 = (CommonTree)adaptor.nil();

            READ31=(Token)match(input,READ,FOLLOW_READ_in_read_cmd667); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            READ31_tree = (CommonTree)adaptor.create(READ31);
            adaptor.addChild(root_0, READ31_tree);
            }
            IDENTIFIER32=(Token)match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_read_cmd669); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            IDENTIFIER32_tree = (CommonTree)adaptor.create(IDENTIFIER32);
            adaptor.addChild(root_0, IDENTIFIER32_tree);
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
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "write_cmd"
    // GuardCommand.g:126:1: write_cmd : WRITE expression ;
    public final GuardCommandParser.write_cmd_return write_cmd() throws RecognitionException {
        GuardCommandParser.write_cmd_return retval = new GuardCommandParser.write_cmd_return();
        retval.start = input.LT(1);
        int write_cmd_StartIndex = input.index();
        CommonTree root_0 = null;

        Token WRITE33=null;
        GuardCommandParser.expression_return expression34 = null;


        CommonTree WRITE33_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 9) ) { return retval; }
            // GuardCommand.g:127:2: ( WRITE expression )
            // GuardCommand.g:127:4: WRITE expression
            {
            root_0 = (CommonTree)adaptor.nil();

            WRITE33=(Token)match(input,WRITE,FOLLOW_WRITE_in_write_cmd680); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            WRITE33_tree = (CommonTree)adaptor.create(WRITE33);
            adaptor.addChild(root_0, WRITE33_tree);
            }
            pushFollow(FOLLOW_expression_in_write_cmd682);
            expression34=expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, expression34.getTree());

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
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "if_cmd"
    // GuardCommand.g:130:1: if_cmd : IF guarded_cmd FI ;
    public final GuardCommandParser.if_cmd_return if_cmd() throws RecognitionException {
        GuardCommandParser.if_cmd_return retval = new GuardCommandParser.if_cmd_return();
        retval.start = input.LT(1);
        int if_cmd_StartIndex = input.index();
        CommonTree root_0 = null;

        Token IF35=null;
        Token FI37=null;
        GuardCommandParser.guarded_cmd_return guarded_cmd36 = null;


        CommonTree IF35_tree=null;
        CommonTree FI37_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 10) ) { return retval; }
            // GuardCommand.g:131:2: ( IF guarded_cmd FI )
            // GuardCommand.g:131:4: IF guarded_cmd FI
            {
            root_0 = (CommonTree)adaptor.nil();

            IF35=(Token)match(input,IF,FOLLOW_IF_in_if_cmd693); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            IF35_tree = (CommonTree)adaptor.create(IF35);
            adaptor.addChild(root_0, IF35_tree);
            }
            pushFollow(FOLLOW_guarded_cmd_in_if_cmd695);
            guarded_cmd36=guarded_cmd();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, guarded_cmd36.getTree());
            FI37=(Token)match(input,FI,FOLLOW_FI_in_if_cmd697); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            FI37_tree = (CommonTree)adaptor.create(FI37);
            adaptor.addChild(root_0, FI37_tree);
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
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "do_cmd"
    // GuardCommand.g:134:1: do_cmd : DO guarded_cmd OD ;
    public final GuardCommandParser.do_cmd_return do_cmd() throws RecognitionException {
        GuardCommandParser.do_cmd_return retval = new GuardCommandParser.do_cmd_return();
        retval.start = input.LT(1);
        int do_cmd_StartIndex = input.index();
        CommonTree root_0 = null;

        Token DO38=null;
        Token OD40=null;
        GuardCommandParser.guarded_cmd_return guarded_cmd39 = null;


        CommonTree DO38_tree=null;
        CommonTree OD40_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 11) ) { return retval; }
            // GuardCommand.g:135:2: ( DO guarded_cmd OD )
            // GuardCommand.g:135:4: DO guarded_cmd OD
            {
            root_0 = (CommonTree)adaptor.nil();

            DO38=(Token)match(input,DO,FOLLOW_DO_in_do_cmd708); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            DO38_tree = (CommonTree)adaptor.create(DO38);
            adaptor.addChild(root_0, DO38_tree);
            }
            pushFollow(FOLLOW_guarded_cmd_in_do_cmd710);
            guarded_cmd39=guarded_cmd();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, guarded_cmd39.getTree());
            OD40=(Token)match(input,OD,FOLLOW_OD_in_do_cmd712); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            OD40_tree = (CommonTree)adaptor.create(OD40);
            adaptor.addChild(root_0, OD40_tree);
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
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "guarded_cmd"
    // GuardCommand.g:138:1: guarded_cmd : ( expression ARROW command ) ( GUARD guarded_cmd )* ;
    public final GuardCommandParser.guarded_cmd_return guarded_cmd() throws RecognitionException {
        GuardCommandParser.guarded_cmd_return retval = new GuardCommandParser.guarded_cmd_return();
        retval.start = input.LT(1);
        int guarded_cmd_StartIndex = input.index();
        CommonTree root_0 = null;

        Token ARROW42=null;
        Token GUARD44=null;
        GuardCommandParser.expression_return expression41 = null;

        GuardCommandParser.command_return command43 = null;

        GuardCommandParser.guarded_cmd_return guarded_cmd45 = null;


        CommonTree ARROW42_tree=null;
        CommonTree GUARD44_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 12) ) { return retval; }
            // GuardCommand.g:139:2: ( ( expression ARROW command ) ( GUARD guarded_cmd )* )
            // GuardCommand.g:139:4: ( expression ARROW command ) ( GUARD guarded_cmd )*
            {
            root_0 = (CommonTree)adaptor.nil();

            // GuardCommand.g:139:4: ( expression ARROW command )
            // GuardCommand.g:139:5: expression ARROW command
            {
            pushFollow(FOLLOW_expression_in_guarded_cmd724);
            expression41=expression();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, expression41.getTree());
            ARROW42=(Token)match(input,ARROW,FOLLOW_ARROW_in_guarded_cmd726); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            ARROW42_tree = (CommonTree)adaptor.create(ARROW42);
            adaptor.addChild(root_0, ARROW42_tree);
            }
            pushFollow(FOLLOW_command_in_guarded_cmd728);
            command43=command();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, command43.getTree());

            }

            // GuardCommand.g:139:31: ( GUARD guarded_cmd )*
            loop5:
            do {
                int alt5=2;
                int LA5_0 = input.LA(1);

                if ( (LA5_0==GUARD) ) {
                    int LA5_2 = input.LA(2);

                    if ( (synpred27_GuardCommand()) ) {
                        alt5=1;
                    }


                }


                switch (alt5) {
            	case 1 :
            	    // GuardCommand.g:139:32: GUARD guarded_cmd
            	    {
            	    GUARD44=(Token)match(input,GUARD,FOLLOW_GUARD_in_guarded_cmd732); if (state.failed) return retval;
            	    if ( state.backtracking==0 ) {
            	    GUARD44_tree = (CommonTree)adaptor.create(GUARD44);
            	    adaptor.addChild(root_0, GUARD44_tree);
            	    }
            	    pushFollow(FOLLOW_guarded_cmd_in_guarded_cmd734);
            	    guarded_cmd45=guarded_cmd();

            	    state._fsp--;
            	    if (state.failed) return retval;
            	    if ( state.backtracking==0 ) adaptor.addChild(root_0, guarded_cmd45.getTree());

            	    }
            	    break;

            	default :
            	    break loop5;
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
        CommonTree tree;
        public Object getTree() { return tree; }
    };

    // $ANTLR start "program"
    // GuardCommand.g:142:1: program : MODULE IDENTIFIER COLON command END ;
    public final GuardCommandParser.program_return program() throws RecognitionException {
        GuardCommandParser.program_return retval = new GuardCommandParser.program_return();
        retval.start = input.LT(1);
        int program_StartIndex = input.index();
        CommonTree root_0 = null;

        Token MODULE46=null;
        Token IDENTIFIER47=null;
        Token COLON48=null;
        Token END50=null;
        GuardCommandParser.command_return command49 = null;


        CommonTree MODULE46_tree=null;
        CommonTree IDENTIFIER47_tree=null;
        CommonTree COLON48_tree=null;
        CommonTree END50_tree=null;

        try {
            if ( state.backtracking>0 && alreadyParsedRule(input, 13) ) { return retval; }
            // GuardCommand.g:143:2: ( MODULE IDENTIFIER COLON command END )
            // GuardCommand.g:143:4: MODULE IDENTIFIER COLON command END
            {
            root_0 = (CommonTree)adaptor.nil();

            MODULE46=(Token)match(input,MODULE,FOLLOW_MODULE_in_program748); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            MODULE46_tree = (CommonTree)adaptor.create(MODULE46);
            adaptor.addChild(root_0, MODULE46_tree);
            }
            IDENTIFIER47=(Token)match(input,IDENTIFIER,FOLLOW_IDENTIFIER_in_program750); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            IDENTIFIER47_tree = (CommonTree)adaptor.create(IDENTIFIER47);
            adaptor.addChild(root_0, IDENTIFIER47_tree);
            }
            COLON48=(Token)match(input,COLON,FOLLOW_COLON_in_program752); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            COLON48_tree = (CommonTree)adaptor.create(COLON48);
            adaptor.addChild(root_0, COLON48_tree);
            }
            pushFollow(FOLLOW_command_in_program754);
            command49=command();

            state._fsp--;
            if (state.failed) return retval;
            if ( state.backtracking==0 ) adaptor.addChild(root_0, command49.getTree());
            END50=(Token)match(input,END,FOLLOW_END_in_program756); if (state.failed) return retval;
            if ( state.backtracking==0 ) {
            END50_tree = (CommonTree)adaptor.create(END50);
            adaptor.addChild(root_0, END50_tree);
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
        // GuardCommand.g:103:106: ( binary_operator expression )
        // GuardCommand.g:103:106: binary_operator expression
        {
        pushFollow(FOLLOW_binary_operator_in_synpred18_GuardCommand563);
        binary_operator();

        state._fsp--;
        if (state.failed) return ;
        pushFollow(FOLLOW_expression_in_synpred18_GuardCommand565);
        expression();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred18_GuardCommand

    // $ANTLR start synpred26_GuardCommand
    public final void synpred26_GuardCommand_fragment() throws RecognitionException {   
        // GuardCommand.g:107:110: ( SEMI command )
        // GuardCommand.g:107:110: SEMI command
        {
        match(input,SEMI,FOLLOW_SEMI_in_synpred26_GuardCommand615); if (state.failed) return ;
        pushFollow(FOLLOW_command_in_synpred26_GuardCommand617);
        command();

        state._fsp--;
        if (state.failed) return ;

        }
    }
    // $ANTLR end synpred26_GuardCommand

    // $ANTLR start synpred27_GuardCommand
    public final void synpred27_GuardCommand_fragment() throws RecognitionException {   
        // GuardCommand.g:139:32: ( GUARD guarded_cmd )
        // GuardCommand.g:139:32: GUARD guarded_cmd
        {
        match(input,GUARD,FOLLOW_GUARD_in_synpred27_GuardCommand732); if (state.failed) return ;
        pushFollow(FOLLOW_guarded_cmd_in_synpred27_GuardCommand734);
        guarded_cmd();

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


 

    public static final BitSet FOLLOW_set_in_unary_operator0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_set_in_binary_operator0 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_INTEGER_LITERAL_in_expression533 = new BitSet(new long[]{0x000000000003FF32L});
    public static final BitSet FOLLOW_TRUE_in_expression537 = new BitSet(new long[]{0x000000000003FF32L});
    public static final BitSet FOLLOW_FALSE_in_expression541 = new BitSet(new long[]{0x000000000003FF32L});
    public static final BitSet FOLLOW_IDENTIFIER_in_expression545 = new BitSet(new long[]{0x000000000003FF32L});
    public static final BitSet FOLLOW_unary_operator_in_expression549 = new BitSet(new long[]{0x000000F000148000L});
    public static final BitSet FOLLOW_expression_in_expression551 = new BitSet(new long[]{0x000000000003FF32L});
    public static final BitSet FOLLOW_LPAREN_in_expression555 = new BitSet(new long[]{0x000000F000148000L});
    public static final BitSet FOLLOW_expression_in_expression557 = new BitSet(new long[]{0x0000000000080000L});
    public static final BitSet FOLLOW_RPAREN_in_expression559 = new BitSet(new long[]{0x000000000003FF32L});
    public static final BitSet FOLLOW_binary_operator_in_expression563 = new BitSet(new long[]{0x000000F000148000L});
    public static final BitSet FOLLOW_expression_in_expression565 = new BitSet(new long[]{0x000000000003FF32L});
    public static final BitSet FOLLOW_assignment_cmd_in_command579 = new BitSet(new long[]{0x0000000000000082L});
    public static final BitSet FOLLOW_skip_cmd_in_command583 = new BitSet(new long[]{0x0000000000000082L});
    public static final BitSet FOLLOW_abort_cmd_in_command587 = new BitSet(new long[]{0x0000000000000082L});
    public static final BitSet FOLLOW_read_cmd_in_command591 = new BitSet(new long[]{0x0000000000000082L});
    public static final BitSet FOLLOW_write_cmd_in_command595 = new BitSet(new long[]{0x0000000000000082L});
    public static final BitSet FOLLOW_LCURLY_in_command599 = new BitSet(new long[]{0x00000083C5400000L});
    public static final BitSet FOLLOW_command_in_command601 = new BitSet(new long[]{0x0000000000200000L});
    public static final BitSet FOLLOW_RCURLY_in_command603 = new BitSet(new long[]{0x0000000000000082L});
    public static final BitSet FOLLOW_if_cmd_in_command607 = new BitSet(new long[]{0x0000000000000082L});
    public static final BitSet FOLLOW_do_cmd_in_command611 = new BitSet(new long[]{0x0000000000000082L});
    public static final BitSet FOLLOW_SEMI_in_command615 = new BitSet(new long[]{0x00000083C5400000L});
    public static final BitSet FOLLOW_command_in_command617 = new BitSet(new long[]{0x0000000000000082L});
    public static final BitSet FOLLOW_IDENTIFIER_in_assignment_cmd630 = new BitSet(new long[]{0x0000000000000040L});
    public static final BitSet FOLLOW_ASSIGN_in_assignment_cmd632 = new BitSet(new long[]{0x000000F000148000L});
    public static final BitSet FOLLOW_expression_in_assignment_cmd634 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_SKIP_in_skip_cmd645 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_ABORT_in_abort_cmd656 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_READ_in_read_cmd667 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_IDENTIFIER_in_read_cmd669 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_WRITE_in_write_cmd680 = new BitSet(new long[]{0x000000F000148000L});
    public static final BitSet FOLLOW_expression_in_write_cmd682 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_IF_in_if_cmd693 = new BitSet(new long[]{0x000000F000148000L});
    public static final BitSet FOLLOW_guarded_cmd_in_if_cmd695 = new BitSet(new long[]{0x0000000002000000L});
    public static final BitSet FOLLOW_FI_in_if_cmd697 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_DO_in_do_cmd708 = new BitSet(new long[]{0x000000F000148000L});
    public static final BitSet FOLLOW_guarded_cmd_in_do_cmd710 = new BitSet(new long[]{0x0000000008000000L});
    public static final BitSet FOLLOW_OD_in_do_cmd712 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_expression_in_guarded_cmd724 = new BitSet(new long[]{0x0000000020000000L});
    public static final BitSet FOLLOW_ARROW_in_guarded_cmd726 = new BitSet(new long[]{0x00000083C5400000L});
    public static final BitSet FOLLOW_command_in_guarded_cmd728 = new BitSet(new long[]{0x0000000010000002L});
    public static final BitSet FOLLOW_GUARD_in_guarded_cmd732 = new BitSet(new long[]{0x000000F000148000L});
    public static final BitSet FOLLOW_guarded_cmd_in_guarded_cmd734 = new BitSet(new long[]{0x0000000010000002L});
    public static final BitSet FOLLOW_MODULE_in_program748 = new BitSet(new long[]{0x0000008000000000L});
    public static final BitSet FOLLOW_IDENTIFIER_in_program750 = new BitSet(new long[]{0x0000000000800000L});
    public static final BitSet FOLLOW_COLON_in_program752 = new BitSet(new long[]{0x00000083C5400000L});
    public static final BitSet FOLLOW_command_in_program754 = new BitSet(new long[]{0x0000000800000000L});
    public static final BitSet FOLLOW_END_in_program756 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_binary_operator_in_synpred18_GuardCommand563 = new BitSet(new long[]{0x000000F000148000L});
    public static final BitSet FOLLOW_expression_in_synpred18_GuardCommand565 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_SEMI_in_synpred26_GuardCommand615 = new BitSet(new long[]{0x00000083C5400000L});
    public static final BitSet FOLLOW_command_in_synpred26_GuardCommand617 = new BitSet(new long[]{0x0000000000000002L});
    public static final BitSet FOLLOW_GUARD_in_synpred27_GuardCommand732 = new BitSet(new long[]{0x000000F000148000L});
    public static final BitSet FOLLOW_guarded_cmd_in_synpred27_GuardCommand734 = new BitSet(new long[]{0x0000000000000002L});

}