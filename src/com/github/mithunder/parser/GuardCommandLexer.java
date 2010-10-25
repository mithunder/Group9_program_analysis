// $ANTLR 3.2 debian-4 GuardCommand.g 2010-10-25 16:12:53

package com.github.mithunder.parser;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class GuardCommandLexer extends Lexer {
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
    public static final int T__44=44;
    public static final int GREATER_THAN=8;
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

    public GuardCommandLexer() {;} 
    public GuardCommandLexer(CharStream input) {
        this(input, new RecognizerSharedState());
    }
    public GuardCommandLexer(CharStream input, RecognizerSharedState state) {
        super(input,state);

    }
    public String getGrammarFileName() { return "GuardCommand.g"; }

    // $ANTLR start "AND"
    public final void mAND() throws RecognitionException {
        try {
            int _type = AND;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GuardCommand.g:11:5: ( '&' )
            // GuardCommand.g:11:7: '&'
            {
            match('&'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "AND"

    // $ANTLR start "OR"
    public final void mOR() throws RecognitionException {
        try {
            int _type = OR;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GuardCommand.g:12:4: ( '|' )
            // GuardCommand.g:12:6: '|'
            {
            match('|'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "OR"

    // $ANTLR start "ASSIGN"
    public final void mASSIGN() throws RecognitionException {
        try {
            int _type = ASSIGN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GuardCommand.g:13:8: ( ':=' )
            // GuardCommand.g:13:10: ':='
            {
            match(":="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ASSIGN"

    // $ANTLR start "SEMI"
    public final void mSEMI() throws RecognitionException {
        try {
            int _type = SEMI;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GuardCommand.g:14:6: ( ';' )
            // GuardCommand.g:14:8: ';'
            {
            match(';'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "SEMI"

    // $ANTLR start "GREATER_THAN"
    public final void mGREATER_THAN() throws RecognitionException {
        try {
            int _type = GREATER_THAN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GuardCommand.g:15:14: ( '>' )
            // GuardCommand.g:15:16: '>'
            {
            match('>'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "GREATER_THAN"

    // $ANTLR start "GREATER_EQ"
    public final void mGREATER_EQ() throws RecognitionException {
        try {
            int _type = GREATER_EQ;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GuardCommand.g:16:12: ( '>=' )
            // GuardCommand.g:16:14: '>='
            {
            match(">="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "GREATER_EQ"

    // $ANTLR start "LESS_THAN"
    public final void mLESS_THAN() throws RecognitionException {
        try {
            int _type = LESS_THAN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GuardCommand.g:17:11: ( '<' )
            // GuardCommand.g:17:13: '<'
            {
            match('<'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LESS_THAN"

    // $ANTLR start "LESS_EQ"
    public final void mLESS_EQ() throws RecognitionException {
        try {
            int _type = LESS_EQ;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GuardCommand.g:18:9: ( '<=' )
            // GuardCommand.g:18:11: '<='
            {
            match("<="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LESS_EQ"

    // $ANTLR start "EQ"
    public final void mEQ() throws RecognitionException {
        try {
            int _type = EQ;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GuardCommand.g:19:4: ( '=' )
            // GuardCommand.g:19:6: '='
            {
            match('='); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "EQ"

    // $ANTLR start "NEQ"
    public final void mNEQ() throws RecognitionException {
        try {
            int _type = NEQ;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GuardCommand.g:20:5: ( '!=' )
            // GuardCommand.g:20:7: '!='
            {
            match("!="); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "NEQ"

    // $ANTLR start "PLUS"
    public final void mPLUS() throws RecognitionException {
        try {
            int _type = PLUS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GuardCommand.g:21:6: ( '+' )
            // GuardCommand.g:21:8: '+'
            {
            match('+'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "PLUS"

    // $ANTLR start "MINUS"
    public final void mMINUS() throws RecognitionException {
        try {
            int _type = MINUS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GuardCommand.g:22:7: ( '-' )
            // GuardCommand.g:22:9: '-'
            {
            match('-'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "MINUS"

    // $ANTLR start "MUL"
    public final void mMUL() throws RecognitionException {
        try {
            int _type = MUL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GuardCommand.g:23:5: ( '*' )
            // GuardCommand.g:23:7: '*'
            {
            match('*'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "MUL"

    // $ANTLR start "DIV"
    public final void mDIV() throws RecognitionException {
        try {
            int _type = DIV;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GuardCommand.g:24:5: ( '/' )
            // GuardCommand.g:24:7: '/'
            {
            match('/'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "DIV"

    // $ANTLR start "NOT"
    public final void mNOT() throws RecognitionException {
        try {
            int _type = NOT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GuardCommand.g:25:5: ( '!' )
            // GuardCommand.g:25:7: '!'
            {
            match('!'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "NOT"

    // $ANTLR start "RPAREN"
    public final void mRPAREN() throws RecognitionException {
        try {
            int _type = RPAREN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GuardCommand.g:26:8: ( ')' )
            // GuardCommand.g:26:10: ')'
            {
            match(')'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RPAREN"

    // $ANTLR start "LPAREN"
    public final void mLPAREN() throws RecognitionException {
        try {
            int _type = LPAREN;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GuardCommand.g:27:8: ( '(' )
            // GuardCommand.g:27:10: '('
            {
            match('('); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LPAREN"

    // $ANTLR start "RCURLY"
    public final void mRCURLY() throws RecognitionException {
        try {
            int _type = RCURLY;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GuardCommand.g:28:8: ( '}' )
            // GuardCommand.g:28:10: '}'
            {
            match('}'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "RCURLY"

    // $ANTLR start "LCURLY"
    public final void mLCURLY() throws RecognitionException {
        try {
            int _type = LCURLY;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GuardCommand.g:29:8: ( '{' )
            // GuardCommand.g:29:10: '{'
            {
            match('{'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LCURLY"

    // $ANTLR start "COLON"
    public final void mCOLON() throws RecognitionException {
        try {
            int _type = COLON;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GuardCommand.g:30:7: ( ':' )
            // GuardCommand.g:30:9: ':'
            {
            match(':'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "COLON"

    // $ANTLR start "IF"
    public final void mIF() throws RecognitionException {
        try {
            int _type = IF;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GuardCommand.g:31:4: ( 'if' )
            // GuardCommand.g:31:6: 'if'
            {
            match("if"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "IF"

    // $ANTLR start "FI"
    public final void mFI() throws RecognitionException {
        try {
            int _type = FI;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GuardCommand.g:32:4: ( 'fi' )
            // GuardCommand.g:32:6: 'fi'
            {
            match("fi"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "FI"

    // $ANTLR start "DO"
    public final void mDO() throws RecognitionException {
        try {
            int _type = DO;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GuardCommand.g:33:4: ( 'do' )
            // GuardCommand.g:33:6: 'do'
            {
            match("do"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "DO"

    // $ANTLR start "OD"
    public final void mOD() throws RecognitionException {
        try {
            int _type = OD;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GuardCommand.g:34:4: ( 'od' )
            // GuardCommand.g:34:6: 'od'
            {
            match("od"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "OD"

    // $ANTLR start "GUARD"
    public final void mGUARD() throws RecognitionException {
        try {
            int _type = GUARD;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GuardCommand.g:35:7: ( '[]' )
            // GuardCommand.g:35:9: '[]'
            {
            match("[]"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "GUARD"

    // $ANTLR start "ARROW"
    public final void mARROW() throws RecognitionException {
        try {
            int _type = ARROW;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GuardCommand.g:36:7: ( '->' )
            // GuardCommand.g:36:9: '->'
            {
            match("->"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ARROW"

    // $ANTLR start "SKIP"
    public final void mSKIP() throws RecognitionException {
        try {
            int _type = SKIP;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GuardCommand.g:37:6: ( 'skip' )
            // GuardCommand.g:37:8: 'skip'
            {
            match("skip"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "SKIP"

    // $ANTLR start "ABORT"
    public final void mABORT() throws RecognitionException {
        try {
            int _type = ABORT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GuardCommand.g:38:7: ( 'abort' )
            // GuardCommand.g:38:9: 'abort'
            {
            match("abort"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ABORT"

    // $ANTLR start "WRITE"
    public final void mWRITE() throws RecognitionException {
        try {
            int _type = WRITE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GuardCommand.g:39:7: ( 'write' )
            // GuardCommand.g:39:9: 'write'
            {
            match("write"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "WRITE"

    // $ANTLR start "READ"
    public final void mREAD() throws RecognitionException {
        try {
            int _type = READ;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GuardCommand.g:40:6: ( 'read' )
            // GuardCommand.g:40:8: 'read'
            {
            match("read"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "READ"

    // $ANTLR start "MODULE"
    public final void mMODULE() throws RecognitionException {
        try {
            int _type = MODULE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GuardCommand.g:41:8: ( 'module' )
            // GuardCommand.g:41:10: 'module'
            {
            match("module"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "MODULE"

    // $ANTLR start "END"
    public final void mEND() throws RecognitionException {
        try {
            int _type = END;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GuardCommand.g:42:5: ( 'end' )
            // GuardCommand.g:42:7: 'end'
            {
            match("end"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "END"

    // $ANTLR start "TRUE"
    public final void mTRUE() throws RecognitionException {
        try {
            int _type = TRUE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GuardCommand.g:43:6: ( 'true' )
            // GuardCommand.g:43:8: 'true'
            {
            match("true"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "TRUE"

    // $ANTLR start "FALSE"
    public final void mFALSE() throws RecognitionException {
        try {
            int _type = FALSE;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GuardCommand.g:44:7: ( 'false' )
            // GuardCommand.g:44:9: 'false'
            {
            match("false"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "FALSE"

    // $ANTLR start "T__44"
    public final void mT__44() throws RecognitionException {
        try {
            int _type = T__44;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GuardCommand.g:45:7: ( '#@' )
            // GuardCommand.g:45:9: '#@'
            {
            match("#@"); 


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__44"

    // $ANTLR start "T__45"
    public final void mT__45() throws RecognitionException {
        try {
            int _type = T__45;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GuardCommand.g:46:7: ( '\"' )
            // GuardCommand.g:46:9: '\"'
            {
            match('\"'); 

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "T__45"

    // $ANTLR start "ML_COMMENT"
    public final void mML_COMMENT() throws RecognitionException {
        try {
            int _type = ML_COMMENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GuardCommand.g:613:5: ( '/*' ( options {greedy=false; } : . )* '*/' )
            // GuardCommand.g:613:9: '/*' ( options {greedy=false; } : . )* '*/'
            {
            match("/*"); 

            // GuardCommand.g:613:14: ( options {greedy=false; } : . )*
            loop1:
            do {
                int alt1=2;
                int LA1_0 = input.LA(1);

                if ( (LA1_0=='*') ) {
                    int LA1_1 = input.LA(2);

                    if ( (LA1_1=='/') ) {
                        alt1=2;
                    }
                    else if ( ((LA1_1>='\u0000' && LA1_1<='.')||(LA1_1>='0' && LA1_1<='\uFFFF')) ) {
                        alt1=1;
                    }


                }
                else if ( ((LA1_0>='\u0000' && LA1_0<=')')||(LA1_0>='+' && LA1_0<='\uFFFF')) ) {
                    alt1=1;
                }


                switch (alt1) {
            	case 1 :
            	    // GuardCommand.g:613:41: .
            	    {
            	    matchAny(); 

            	    }
            	    break;

            	default :
            	    break loop1;
                }
            } while (true);

            match("*/"); 

            _channel=HIDDEN;

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "ML_COMMENT"

    // $ANTLR start "LINE_COMMENT"
    public final void mLINE_COMMENT() throws RecognitionException {
        try {
            int _type = LINE_COMMENT;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GuardCommand.g:617:5: ( ( '//' | '#' ) (~ ( '\\n' | '\\r' ) )* ( '\\r\\n' | '\\r' | '\\n' ) | ( '//' | '#' ) (~ ( '\\n' | '\\r' ) )* )
            int alt7=2;
            alt7 = dfa7.predict(input);
            switch (alt7) {
                case 1 :
                    // GuardCommand.g:617:9: ( '//' | '#' ) (~ ( '\\n' | '\\r' ) )* ( '\\r\\n' | '\\r' | '\\n' )
                    {
                    // GuardCommand.g:617:9: ( '//' | '#' )
                    int alt2=2;
                    int LA2_0 = input.LA(1);

                    if ( (LA2_0=='/') ) {
                        alt2=1;
                    }
                    else if ( (LA2_0=='#') ) {
                        alt2=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 2, 0, input);

                        throw nvae;
                    }
                    switch (alt2) {
                        case 1 :
                            // GuardCommand.g:617:10: '//'
                            {
                            match("//"); 


                            }
                            break;
                        case 2 :
                            // GuardCommand.g:617:15: '#'
                            {
                            match('#'); 

                            }
                            break;

                    }

                    // GuardCommand.g:617:20: (~ ( '\\n' | '\\r' ) )*
                    loop3:
                    do {
                        int alt3=2;
                        int LA3_0 = input.LA(1);

                        if ( ((LA3_0>='\u0000' && LA3_0<='\t')||(LA3_0>='\u000B' && LA3_0<='\f')||(LA3_0>='\u000E' && LA3_0<='\uFFFF')) ) {
                            alt3=1;
                        }


                        switch (alt3) {
                    	case 1 :
                    	    // GuardCommand.g:617:20: ~ ( '\\n' | '\\r' )
                    	    {
                    	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='\t')||(input.LA(1)>='\u000B' && input.LA(1)<='\f')||(input.LA(1)>='\u000E' && input.LA(1)<='\uFFFF') ) {
                    	        input.consume();

                    	    }
                    	    else {
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        recover(mse);
                    	        throw mse;}


                    	    }
                    	    break;

                    	default :
                    	    break loop3;
                        }
                    } while (true);

                    // GuardCommand.g:617:35: ( '\\r\\n' | '\\r' | '\\n' )
                    int alt4=3;
                    int LA4_0 = input.LA(1);

                    if ( (LA4_0=='\r') ) {
                        int LA4_1 = input.LA(2);

                        if ( (LA4_1=='\n') ) {
                            alt4=1;
                        }
                        else {
                            alt4=2;}
                    }
                    else if ( (LA4_0=='\n') ) {
                        alt4=3;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 4, 0, input);

                        throw nvae;
                    }
                    switch (alt4) {
                        case 1 :
                            // GuardCommand.g:617:36: '\\r\\n'
                            {
                            match("\r\n"); 


                            }
                            break;
                        case 2 :
                            // GuardCommand.g:617:45: '\\r'
                            {
                            match('\r'); 

                            }
                            break;
                        case 3 :
                            // GuardCommand.g:617:52: '\\n'
                            {
                            match('\n'); 

                            }
                            break;

                    }

                    _channel=HIDDEN;

                    }
                    break;
                case 2 :
                    // GuardCommand.g:618:9: ( '//' | '#' ) (~ ( '\\n' | '\\r' ) )*
                    {
                    // GuardCommand.g:618:9: ( '//' | '#' )
                    int alt5=2;
                    int LA5_0 = input.LA(1);

                    if ( (LA5_0=='/') ) {
                        alt5=1;
                    }
                    else if ( (LA5_0=='#') ) {
                        alt5=2;
                    }
                    else {
                        NoViableAltException nvae =
                            new NoViableAltException("", 5, 0, input);

                        throw nvae;
                    }
                    switch (alt5) {
                        case 1 :
                            // GuardCommand.g:618:10: '//'
                            {
                            match("//"); 


                            }
                            break;
                        case 2 :
                            // GuardCommand.g:618:15: '#'
                            {
                            match('#'); 

                            }
                            break;

                    }

                    // GuardCommand.g:618:20: (~ ( '\\n' | '\\r' ) )*
                    loop6:
                    do {
                        int alt6=2;
                        int LA6_0 = input.LA(1);

                        if ( ((LA6_0>='\u0000' && LA6_0<='\t')||(LA6_0>='\u000B' && LA6_0<='\f')||(LA6_0>='\u000E' && LA6_0<='\uFFFF')) ) {
                            alt6=1;
                        }


                        switch (alt6) {
                    	case 1 :
                    	    // GuardCommand.g:618:20: ~ ( '\\n' | '\\r' )
                    	    {
                    	    if ( (input.LA(1)>='\u0000' && input.LA(1)<='\t')||(input.LA(1)>='\u000B' && input.LA(1)<='\f')||(input.LA(1)>='\u000E' && input.LA(1)<='\uFFFF') ) {
                    	        input.consume();

                    	    }
                    	    else {
                    	        MismatchedSetException mse = new MismatchedSetException(null,input);
                    	        recover(mse);
                    	        throw mse;}


                    	    }
                    	    break;

                    	default :
                    	    break loop6;
                        }
                    } while (true);

                    _channel=HIDDEN;

                    }
                    break;

            }
            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "LINE_COMMENT"

    // $ANTLR start "INTEGER_LITERAL"
    public final void mINTEGER_LITERAL() throws RecognitionException {
        try {
            int _type = INTEGER_LITERAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GuardCommand.g:622:17: ( ( '0' | '1' .. '9' ( '0' .. '9' )* ) )
            // GuardCommand.g:622:19: ( '0' | '1' .. '9' ( '0' .. '9' )* )
            {
            // GuardCommand.g:622:19: ( '0' | '1' .. '9' ( '0' .. '9' )* )
            int alt9=2;
            int LA9_0 = input.LA(1);

            if ( (LA9_0=='0') ) {
                alt9=1;
            }
            else if ( ((LA9_0>='1' && LA9_0<='9')) ) {
                alt9=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 9, 0, input);

                throw nvae;
            }
            switch (alt9) {
                case 1 :
                    // GuardCommand.g:622:20: '0'
                    {
                    match('0'); 

                    }
                    break;
                case 2 :
                    // GuardCommand.g:622:26: '1' .. '9' ( '0' .. '9' )*
                    {
                    matchRange('1','9'); 
                    // GuardCommand.g:622:35: ( '0' .. '9' )*
                    loop8:
                    do {
                        int alt8=2;
                        int LA8_0 = input.LA(1);

                        if ( ((LA8_0>='0' && LA8_0<='9')) ) {
                            alt8=1;
                        }


                        switch (alt8) {
                    	case 1 :
                    	    // GuardCommand.g:622:35: '0' .. '9'
                    	    {
                    	    matchRange('0','9'); 

                    	    }
                    	    break;

                    	default :
                    	    break loop8;
                        }
                    } while (true);


                    }
                    break;

            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "INTEGER_LITERAL"

    // $ANTLR start "IDENTIFIER"
    public final void mIDENTIFIER() throws RecognitionException {
        try {
            int _type = IDENTIFIER;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            CommonToken b=null;
            CommonToken d=null;
            int e;
            int count;

            // GuardCommand.g:624:12: ( (b= LETTER (count= (d= LETTER | e= '0' .. '9' ) )* ) )
            // GuardCommand.g:624:14: (b= LETTER (count= (d= LETTER | e= '0' .. '9' ) )* )
            {
            // GuardCommand.g:624:14: (b= LETTER (count= (d= LETTER | e= '0' .. '9' ) )* )
            // GuardCommand.g:624:15: b= LETTER (count= (d= LETTER | e= '0' .. '9' ) )*
            {
            int bStart460 = getCharIndex();
            mLETTER(); 
            b = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, bStart460, getCharIndex()-1);
            // GuardCommand.g:624:29: (count= (d= LETTER | e= '0' .. '9' ) )*
            loop11:
            do {
                int alt11=2;
                int LA11_0 = input.LA(1);

                if ( (LA11_0=='$'||(LA11_0>='0' && LA11_0<='9')||(LA11_0>='A' && LA11_0<='Z')||LA11_0=='_'||(LA11_0>='a' && LA11_0<='z')) ) {
                    alt11=1;
                }


                switch (alt11) {
            	case 1 :
            	    // GuardCommand.g:624:29: count= (d= LETTER | e= '0' .. '9' )
            	    {
            	    // GuardCommand.g:624:30: (d= LETTER | e= '0' .. '9' )
            	    int alt10=2;
            	    int LA10_0 = input.LA(1);

            	    if ( (LA10_0=='$'||(LA10_0>='A' && LA10_0<='Z')||LA10_0=='_'||(LA10_0>='a' && LA10_0<='z')) ) {
            	        alt10=1;
            	    }
            	    else if ( ((LA10_0>='0' && LA10_0<='9')) ) {
            	        alt10=2;
            	    }
            	    else {
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 10, 0, input);

            	        throw nvae;
            	    }
            	    switch (alt10) {
            	        case 1 :
            	            // GuardCommand.g:624:31: d= LETTER
            	            {
            	            int dStart467 = getCharIndex();
            	            mLETTER(); 
            	            d = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, dStart467, getCharIndex()-1);

            	            }
            	            break;
            	        case 2 :
            	            // GuardCommand.g:624:40: e= '0' .. '9'
            	            {
            	            e = input.LA(1);
            	            matchRange('0','9'); 

            	            }
            	            break;

            	    }


            	    }
            	    break;

            	default :
            	    break loop11;
                }
            } while (true);


            }


            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "IDENTIFIER"

    // $ANTLR start "LETTER"
    public final void mLETTER() throws RecognitionException {
        try {
            // GuardCommand.g:628:2: ( '$' | 'A' .. 'Z' | 'a' .. 'z' | '_' )
            // GuardCommand.g:
            {
            if ( input.LA(1)=='$'||(input.LA(1)>='A' && input.LA(1)<='Z')||input.LA(1)=='_'||(input.LA(1)>='a' && input.LA(1)<='z') ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}


            }

        }
        finally {
        }
    }
    // $ANTLR end "LETTER"

    // $ANTLR start "WS"
    public final void mWS() throws RecognitionException {
        try {
            int _type = WS;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GuardCommand.g:634:5: ( ( ' ' | '\\r' | '\\t' | '\\u000C' | '\\n' ) )
            // GuardCommand.g:634:8: ( ' ' | '\\r' | '\\t' | '\\u000C' | '\\n' )
            {
            if ( (input.LA(1)>='\t' && input.LA(1)<='\n')||(input.LA(1)>='\f' && input.LA(1)<='\r')||input.LA(1)==' ' ) {
                input.consume();

            }
            else {
                MismatchedSetException mse = new MismatchedSetException(null,input);
                recover(mse);
                throw mse;}

               
                	_channel=HIDDEN;
                

            }

            state.type = _type;
            state.channel = _channel;
        }
        finally {
        }
    }
    // $ANTLR end "WS"

    public void mTokens() throws RecognitionException {
        // GuardCommand.g:1:8: ( AND | OR | ASSIGN | SEMI | GREATER_THAN | GREATER_EQ | LESS_THAN | LESS_EQ | EQ | NEQ | PLUS | MINUS | MUL | DIV | NOT | RPAREN | LPAREN | RCURLY | LCURLY | COLON | IF | FI | DO | OD | GUARD | ARROW | SKIP | ABORT | WRITE | READ | MODULE | END | TRUE | FALSE | T__44 | T__45 | ML_COMMENT | LINE_COMMENT | INTEGER_LITERAL | IDENTIFIER | WS )
        int alt12=41;
        alt12 = dfa12.predict(input);
        switch (alt12) {
            case 1 :
                // GuardCommand.g:1:10: AND
                {
                mAND(); 

                }
                break;
            case 2 :
                // GuardCommand.g:1:14: OR
                {
                mOR(); 

                }
                break;
            case 3 :
                // GuardCommand.g:1:17: ASSIGN
                {
                mASSIGN(); 

                }
                break;
            case 4 :
                // GuardCommand.g:1:24: SEMI
                {
                mSEMI(); 

                }
                break;
            case 5 :
                // GuardCommand.g:1:29: GREATER_THAN
                {
                mGREATER_THAN(); 

                }
                break;
            case 6 :
                // GuardCommand.g:1:42: GREATER_EQ
                {
                mGREATER_EQ(); 

                }
                break;
            case 7 :
                // GuardCommand.g:1:53: LESS_THAN
                {
                mLESS_THAN(); 

                }
                break;
            case 8 :
                // GuardCommand.g:1:63: LESS_EQ
                {
                mLESS_EQ(); 

                }
                break;
            case 9 :
                // GuardCommand.g:1:71: EQ
                {
                mEQ(); 

                }
                break;
            case 10 :
                // GuardCommand.g:1:74: NEQ
                {
                mNEQ(); 

                }
                break;
            case 11 :
                // GuardCommand.g:1:78: PLUS
                {
                mPLUS(); 

                }
                break;
            case 12 :
                // GuardCommand.g:1:83: MINUS
                {
                mMINUS(); 

                }
                break;
            case 13 :
                // GuardCommand.g:1:89: MUL
                {
                mMUL(); 

                }
                break;
            case 14 :
                // GuardCommand.g:1:93: DIV
                {
                mDIV(); 

                }
                break;
            case 15 :
                // GuardCommand.g:1:97: NOT
                {
                mNOT(); 

                }
                break;
            case 16 :
                // GuardCommand.g:1:101: RPAREN
                {
                mRPAREN(); 

                }
                break;
            case 17 :
                // GuardCommand.g:1:108: LPAREN
                {
                mLPAREN(); 

                }
                break;
            case 18 :
                // GuardCommand.g:1:115: RCURLY
                {
                mRCURLY(); 

                }
                break;
            case 19 :
                // GuardCommand.g:1:122: LCURLY
                {
                mLCURLY(); 

                }
                break;
            case 20 :
                // GuardCommand.g:1:129: COLON
                {
                mCOLON(); 

                }
                break;
            case 21 :
                // GuardCommand.g:1:135: IF
                {
                mIF(); 

                }
                break;
            case 22 :
                // GuardCommand.g:1:138: FI
                {
                mFI(); 

                }
                break;
            case 23 :
                // GuardCommand.g:1:141: DO
                {
                mDO(); 

                }
                break;
            case 24 :
                // GuardCommand.g:1:144: OD
                {
                mOD(); 

                }
                break;
            case 25 :
                // GuardCommand.g:1:147: GUARD
                {
                mGUARD(); 

                }
                break;
            case 26 :
                // GuardCommand.g:1:153: ARROW
                {
                mARROW(); 

                }
                break;
            case 27 :
                // GuardCommand.g:1:159: SKIP
                {
                mSKIP(); 

                }
                break;
            case 28 :
                // GuardCommand.g:1:164: ABORT
                {
                mABORT(); 

                }
                break;
            case 29 :
                // GuardCommand.g:1:170: WRITE
                {
                mWRITE(); 

                }
                break;
            case 30 :
                // GuardCommand.g:1:176: READ
                {
                mREAD(); 

                }
                break;
            case 31 :
                // GuardCommand.g:1:181: MODULE
                {
                mMODULE(); 

                }
                break;
            case 32 :
                // GuardCommand.g:1:188: END
                {
                mEND(); 

                }
                break;
            case 33 :
                // GuardCommand.g:1:192: TRUE
                {
                mTRUE(); 

                }
                break;
            case 34 :
                // GuardCommand.g:1:197: FALSE
                {
                mFALSE(); 

                }
                break;
            case 35 :
                // GuardCommand.g:1:203: T__44
                {
                mT__44(); 

                }
                break;
            case 36 :
                // GuardCommand.g:1:209: T__45
                {
                mT__45(); 

                }
                break;
            case 37 :
                // GuardCommand.g:1:215: ML_COMMENT
                {
                mML_COMMENT(); 

                }
                break;
            case 38 :
                // GuardCommand.g:1:226: LINE_COMMENT
                {
                mLINE_COMMENT(); 

                }
                break;
            case 39 :
                // GuardCommand.g:1:239: INTEGER_LITERAL
                {
                mINTEGER_LITERAL(); 

                }
                break;
            case 40 :
                // GuardCommand.g:1:255: IDENTIFIER
                {
                mIDENTIFIER(); 

                }
                break;
            case 41 :
                // GuardCommand.g:1:266: WS
                {
                mWS(); 

                }
                break;

        }

    }


    protected DFA7 dfa7 = new DFA7(this);
    protected DFA12 dfa12 = new DFA12(this);
    static final String DFA7_eotS =
        "\2\uffff\3\6\2\uffff";
    static final String DFA7_eofS =
        "\7\uffff";
    static final String DFA7_minS =
        "\1\43\1\57\3\0\2\uffff";
    static final String DFA7_maxS =
        "\2\57\3\uffff\2\uffff";
    static final String DFA7_acceptS =
        "\5\uffff\1\1\1\2";
    static final String DFA7_specialS =
        "\2\uffff\1\1\1\0\1\2\2\uffff}>";
    static final String[] DFA7_transitionS = {
            "\1\2\13\uffff\1\1",
            "\1\3",
            "\12\4\1\5\2\4\1\5\ufff2\4",
            "\12\4\1\5\2\4\1\5\ufff2\4",
            "\12\4\1\5\2\4\1\5\ufff2\4",
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
            return "616:1: LINE_COMMENT : ( ( '//' | '#' ) (~ ( '\\n' | '\\r' ) )* ( '\\r\\n' | '\\r' | '\\n' ) | ( '//' | '#' ) (~ ( '\\n' | '\\r' ) )* );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            IntStream input = _input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA7_3 = input.LA(1);

                        s = -1;
                        if ( ((LA7_3>='\u0000' && LA7_3<='\t')||(LA7_3>='\u000B' && LA7_3<='\f')||(LA7_3>='\u000E' && LA7_3<='\uFFFF')) ) {s = 4;}

                        else if ( (LA7_3=='\n'||LA7_3=='\r') ) {s = 5;}

                        else s = 6;

                        if ( s>=0 ) return s;
                        break;
                    case 1 : 
                        int LA7_2 = input.LA(1);

                        s = -1;
                        if ( ((LA7_2>='\u0000' && LA7_2<='\t')||(LA7_2>='\u000B' && LA7_2<='\f')||(LA7_2>='\u000E' && LA7_2<='\uFFFF')) ) {s = 4;}

                        else if ( (LA7_2=='\n'||LA7_2=='\r') ) {s = 5;}

                        else s = 6;

                        if ( s>=0 ) return s;
                        break;
                    case 2 : 
                        int LA7_4 = input.LA(1);

                        s = -1;
                        if ( ((LA7_4>='\u0000' && LA7_4<='\t')||(LA7_4>='\u000B' && LA7_4<='\f')||(LA7_4>='\u000E' && LA7_4<='\uFFFF')) ) {s = 4;}

                        else if ( (LA7_4=='\n'||LA7_4=='\r') ) {s = 5;}

                        else s = 6;

                        if ( s>=0 ) return s;
                        break;
            }
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 7, _s, input);
            error(nvae);
            throw nvae;
        }
    }
    static final String DFA12_eotS =
        "\3\uffff\1\43\1\uffff\1\45\1\47\1\uffff\1\51\1\uffff\1\53\1\uffff"+
        "\1\56\4\uffff\4\40\1\uffff\7\40\1\55\21\uffff\1\74\1\75\1\40\1\77"+
        "\1\100\7\40\1\110\2\uffff\1\40\2\uffff\5\40\1\117\1\40\1\uffff\1"+
        "\40\1\122\2\40\1\125\1\40\1\uffff\1\127\1\130\1\uffff\1\131\1\132"+
        "\1\uffff\1\40\4\uffff\1\134\1\uffff";
    static final String DFA12_eofS =
        "\135\uffff";
    static final String DFA12_minS =
        "\1\11\2\uffff\1\75\1\uffff\2\75\1\uffff\1\75\1\uffff\1\76\1\uffff"+
        "\1\52\4\uffff\1\146\1\141\1\157\1\144\1\uffff\1\153\1\142\1\162"+
        "\1\145\1\157\1\156\1\162\1\100\21\uffff\2\44\1\154\2\44\1\151\1"+
        "\157\1\151\1\141\2\144\1\165\1\0\2\uffff\1\163\2\uffff\1\160\1\162"+
        "\1\164\1\144\1\165\1\44\1\145\1\uffff\1\145\1\44\1\164\1\145\1\44"+
        "\1\154\1\uffff\2\44\1\uffff\2\44\1\uffff\1\145\4\uffff\1\44\1\uffff";
    static final String DFA12_maxS =
        "\1\175\2\uffff\1\75\1\uffff\2\75\1\uffff\1\75\1\uffff\1\76\1\uffff"+
        "\1\57\4\uffff\1\146\1\151\1\157\1\144\1\uffff\1\153\1\142\1\162"+
        "\1\145\1\157\1\156\1\162\1\100\21\uffff\2\172\1\154\2\172\1\151"+
        "\1\157\1\151\1\141\2\144\1\165\1\uffff\2\uffff\1\163\2\uffff\1\160"+
        "\1\162\1\164\1\144\1\165\1\172\1\145\1\uffff\1\145\1\172\1\164\1"+
        "\145\1\172\1\154\1\uffff\2\172\1\uffff\2\172\1\uffff\1\145\4\uffff"+
        "\1\172\1\uffff";
    static final String DFA12_acceptS =
        "\1\uffff\1\1\1\2\1\uffff\1\4\2\uffff\1\11\1\uffff\1\13\1\uffff\1"+
        "\15\1\uffff\1\20\1\21\1\22\1\23\4\uffff\1\31\10\uffff\1\44\1\47"+
        "\1\50\1\51\1\3\1\24\1\6\1\5\1\10\1\7\1\12\1\17\1\32\1\14\1\45\1"+
        "\46\1\16\15\uffff\1\25\1\26\1\uffff\1\27\1\30\7\uffff\1\43\6\uffff"+
        "\1\40\2\uffff\1\33\2\uffff\1\36\1\uffff\1\41\1\42\1\34\1\35\1\uffff"+
        "\1\37";
    static final String DFA12_specialS =
        "\73\uffff\1\0\41\uffff}>";
    static final String[] DFA12_transitionS = {
            "\2\41\1\uffff\2\41\22\uffff\1\41\1\10\1\36\1\35\1\40\1\uffff"+
            "\1\1\1\uffff\1\16\1\15\1\13\1\11\1\uffff\1\12\1\uffff\1\14\12"+
            "\37\1\3\1\4\1\6\1\7\1\5\2\uffff\32\40\1\25\3\uffff\1\40\1\uffff"+
            "\1\27\2\40\1\23\1\33\1\22\2\40\1\21\3\40\1\32\1\40\1\24\2\40"+
            "\1\31\1\26\1\34\2\40\1\30\3\40\1\20\1\2\1\17",
            "",
            "",
            "\1\42",
            "",
            "\1\44",
            "\1\46",
            "",
            "\1\50",
            "",
            "\1\52",
            "",
            "\1\54\4\uffff\1\55",
            "",
            "",
            "",
            "",
            "\1\57",
            "\1\61\7\uffff\1\60",
            "\1\62",
            "\1\63",
            "",
            "\1\64",
            "\1\65",
            "\1\66",
            "\1\67",
            "\1\70",
            "\1\71",
            "\1\72",
            "\1\73",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\40\13\uffff\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32"+
            "\40",
            "\1\40\13\uffff\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32"+
            "\40",
            "\1\76",
            "\1\40\13\uffff\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32"+
            "\40",
            "\1\40\13\uffff\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32"+
            "\40",
            "\1\101",
            "\1\102",
            "\1\103",
            "\1\104",
            "\1\105",
            "\1\106",
            "\1\107",
            "\0\55",
            "",
            "",
            "\1\111",
            "",
            "",
            "\1\112",
            "\1\113",
            "\1\114",
            "\1\115",
            "\1\116",
            "\1\40\13\uffff\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32"+
            "\40",
            "\1\120",
            "",
            "\1\121",
            "\1\40\13\uffff\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32"+
            "\40",
            "\1\123",
            "\1\124",
            "\1\40\13\uffff\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32"+
            "\40",
            "\1\126",
            "",
            "\1\40\13\uffff\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32"+
            "\40",
            "\1\40\13\uffff\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32"+
            "\40",
            "",
            "\1\40\13\uffff\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32"+
            "\40",
            "\1\40\13\uffff\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32"+
            "\40",
            "",
            "\1\133",
            "",
            "",
            "",
            "",
            "\1\40\13\uffff\12\40\7\uffff\32\40\4\uffff\1\40\1\uffff\32"+
            "\40",
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
            return "1:1: Tokens : ( AND | OR | ASSIGN | SEMI | GREATER_THAN | GREATER_EQ | LESS_THAN | LESS_EQ | EQ | NEQ | PLUS | MINUS | MUL | DIV | NOT | RPAREN | LPAREN | RCURLY | LCURLY | COLON | IF | FI | DO | OD | GUARD | ARROW | SKIP | ABORT | WRITE | READ | MODULE | END | TRUE | FALSE | T__44 | T__45 | ML_COMMENT | LINE_COMMENT | INTEGER_LITERAL | IDENTIFIER | WS );";
        }
        public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
            IntStream input = _input;
        	int _s = s;
            switch ( s ) {
                    case 0 : 
                        int LA12_59 = input.LA(1);

                        s = -1;
                        if ( ((LA12_59>='\u0000' && LA12_59<='\uFFFF')) ) {s = 45;}

                        else s = 72;

                        if ( s>=0 ) return s;
                        break;
            }
            NoViableAltException nvae =
                new NoViableAltException(getDescription(), 12, _s, input);
            error(nvae);
            throw nvae;
        }
    }
 

}