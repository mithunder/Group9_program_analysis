// $ANTLR 3.2 debian-4 GuardCommand.g 2010-10-14 23:06:32

package com.github.mithunder.parser;


import org.antlr.runtime.*;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class GuardCommandLexer extends Lexer {
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
    public static final int IF=24;
    public static final int FI=25;
    public static final int SKIP=30;
    public static final int COLON=23;
    public static final int RPAREN=19;
    public static final int NEQ=13;
    public static final int WS=41;
    public static final int READ=33;
    public static final int INTEGER_LITERAL=38;
    public static final int IDENTIFIER=39;
    public static final int OR=5;
    public static final int RCURLY=21;
    public static final int LESS_THAN=10;
    public static final int ASSIGN=6;
    public static final int GREATER_EQ=9;
    public static final int ARROW=29;
    public static final int PLUS=14;
    public static final int GUARD=28;
    public static final int DIV=17;
    public static final int EQ=12;
    public static final int ABORT=31;
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

    // $ANTLR start "INTEGER_LITERAL"
    public final void mINTEGER_LITERAL() throws RecognitionException {
        try {
            int _type = INTEGER_LITERAL;
            int _channel = DEFAULT_TOKEN_CHANNEL;
            // GuardCommand.g:312:17: ( ( '0' | '1' .. '9' ( '0' .. '9' )* ) )
            // GuardCommand.g:312:19: ( '0' | '1' .. '9' ( '0' .. '9' )* )
            {
            // GuardCommand.g:312:19: ( '0' | '1' .. '9' ( '0' .. '9' )* )
            int alt2=2;
            int LA2_0 = input.LA(1);

            if ( (LA2_0=='0') ) {
                alt2=1;
            }
            else if ( ((LA2_0>='1' && LA2_0<='9')) ) {
                alt2=2;
            }
            else {
                NoViableAltException nvae =
                    new NoViableAltException("", 2, 0, input);

                throw nvae;
            }
            switch (alt2) {
                case 1 :
                    // GuardCommand.g:312:20: '0'
                    {
                    match('0'); 

                    }
                    break;
                case 2 :
                    // GuardCommand.g:312:26: '1' .. '9' ( '0' .. '9' )*
                    {
                    matchRange('1','9'); 
                    // GuardCommand.g:312:35: ( '0' .. '9' )*
                    loop1:
                    do {
                        int alt1=2;
                        int LA1_0 = input.LA(1);

                        if ( ((LA1_0>='0' && LA1_0<='9')) ) {
                            alt1=1;
                        }


                        switch (alt1) {
                    	case 1 :
                    	    // GuardCommand.g:312:35: '0' .. '9'
                    	    {
                    	    matchRange('0','9'); 

                    	    }
                    	    break;

                    	default :
                    	    break loop1;
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

            // GuardCommand.g:314:12: ( (b= LETTER (count= (d= LETTER | e= '0' .. '9' ) )* ) )
            // GuardCommand.g:314:14: (b= LETTER (count= (d= LETTER | e= '0' .. '9' ) )* )
            {
            // GuardCommand.g:314:14: (b= LETTER (count= (d= LETTER | e= '0' .. '9' ) )* )
            // GuardCommand.g:314:15: b= LETTER (count= (d= LETTER | e= '0' .. '9' ) )*
            {
            int bStart329 = getCharIndex();
            mLETTER(); 
            b = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, bStart329, getCharIndex()-1);
            // GuardCommand.g:314:29: (count= (d= LETTER | e= '0' .. '9' ) )*
            loop4:
            do {
                int alt4=2;
                int LA4_0 = input.LA(1);

                if ( (LA4_0=='$'||(LA4_0>='0' && LA4_0<='9')||(LA4_0>='A' && LA4_0<='Z')||LA4_0=='_'||(LA4_0>='a' && LA4_0<='z')) ) {
                    alt4=1;
                }


                switch (alt4) {
            	case 1 :
            	    // GuardCommand.g:314:29: count= (d= LETTER | e= '0' .. '9' )
            	    {
            	    // GuardCommand.g:314:30: (d= LETTER | e= '0' .. '9' )
            	    int alt3=2;
            	    int LA3_0 = input.LA(1);

            	    if ( (LA3_0=='$'||(LA3_0>='A' && LA3_0<='Z')||LA3_0=='_'||(LA3_0>='a' && LA3_0<='z')) ) {
            	        alt3=1;
            	    }
            	    else if ( ((LA3_0>='0' && LA3_0<='9')) ) {
            	        alt3=2;
            	    }
            	    else {
            	        NoViableAltException nvae =
            	            new NoViableAltException("", 3, 0, input);

            	        throw nvae;
            	    }
            	    switch (alt3) {
            	        case 1 :
            	            // GuardCommand.g:314:31: d= LETTER
            	            {
            	            int dStart336 = getCharIndex();
            	            mLETTER(); 
            	            d = new CommonToken(input, Token.INVALID_TOKEN_TYPE, Token.DEFAULT_CHANNEL, dStart336, getCharIndex()-1);

            	            }
            	            break;
            	        case 2 :
            	            // GuardCommand.g:314:40: e= '0' .. '9'
            	            {
            	            e = input.LA(1);
            	            matchRange('0','9'); 

            	            }
            	            break;

            	    }


            	    }
            	    break;

            	default :
            	    break loop4;
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
            // GuardCommand.g:318:2: ( '$' | 'A' .. 'Z' | 'a' .. 'z' | '_' )
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
            // GuardCommand.g:324:5: ( ( ' ' | '\\r' | '\\t' | '\\u000C' | '\\n' ) )
            // GuardCommand.g:324:8: ( ' ' | '\\r' | '\\t' | '\\u000C' | '\\n' )
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
        // GuardCommand.g:1:8: ( AND | OR | ASSIGN | SEMI | GREATER_THAN | GREATER_EQ | LESS_THAN | LESS_EQ | EQ | NEQ | PLUS | MINUS | MUL | DIV | NOT | RPAREN | LPAREN | RCURLY | LCURLY | COLON | IF | FI | DO | OD | GUARD | ARROW | SKIP | ABORT | WRITE | READ | MODULE | END | TRUE | FALSE | INTEGER_LITERAL | IDENTIFIER | WS )
        int alt5=37;
        alt5 = dfa5.predict(input);
        switch (alt5) {
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
                // GuardCommand.g:1:203: INTEGER_LITERAL
                {
                mINTEGER_LITERAL(); 

                }
                break;
            case 36 :
                // GuardCommand.g:1:219: IDENTIFIER
                {
                mIDENTIFIER(); 

                }
                break;
            case 37 :
                // GuardCommand.g:1:230: WS
                {
                mWS(); 

                }
                break;

        }

    }


    protected DFA5 dfa5 = new DFA5(this);
    static final String DFA5_eotS =
        "\3\uffff\1\41\1\uffff\1\43\1\45\1\uffff\1\47\1\uffff\1\51\6\uffff"+
        "\4\36\1\uffff\7\36\15\uffff\1\66\1\67\1\36\1\71\1\72\7\36\2\uffff"+
        "\1\36\2\uffff\5\36\1\110\2\36\1\113\2\36\1\116\1\36\1\uffff\1\120"+
        "\1\121\1\uffff\1\122\1\123\1\uffff\1\36\4\uffff\1\125\1\uffff";
    static final String DFA5_eofS =
        "\126\uffff";
    static final String DFA5_minS =
        "\1\11\2\uffff\1\75\1\uffff\2\75\1\uffff\1\75\1\uffff\1\76\6\uffff"+
        "\1\146\1\141\1\157\1\144\1\uffff\1\153\1\142\1\162\1\145\1\157\1"+
        "\156\1\162\15\uffff\2\44\1\154\2\44\1\151\1\157\1\151\1\141\2\144"+
        "\1\165\2\uffff\1\163\2\uffff\1\160\1\162\1\164\1\144\1\165\1\44"+
        "\2\145\1\44\1\164\1\145\1\44\1\154\1\uffff\2\44\1\uffff\2\44\1\uffff"+
        "\1\145\4\uffff\1\44\1\uffff";
    static final String DFA5_maxS =
        "\1\175\2\uffff\1\75\1\uffff\2\75\1\uffff\1\75\1\uffff\1\76\6\uffff"+
        "\1\146\1\151\1\157\1\144\1\uffff\1\153\1\142\1\162\1\145\1\157\1"+
        "\156\1\162\15\uffff\2\172\1\154\2\172\1\151\1\157\1\151\1\141\2"+
        "\144\1\165\2\uffff\1\163\2\uffff\1\160\1\162\1\164\1\144\1\165\1"+
        "\172\2\145\1\172\1\164\1\145\1\172\1\154\1\uffff\2\172\1\uffff\2"+
        "\172\1\uffff\1\145\4\uffff\1\172\1\uffff";
    static final String DFA5_acceptS =
        "\1\uffff\1\1\1\2\1\uffff\1\4\2\uffff\1\11\1\uffff\1\13\1\uffff\1"+
        "\15\1\16\1\20\1\21\1\22\1\23\4\uffff\1\31\7\uffff\1\43\1\44\1\45"+
        "\1\3\1\24\1\6\1\5\1\10\1\7\1\12\1\17\1\32\1\14\14\uffff\1\25\1\26"+
        "\1\uffff\1\27\1\30\15\uffff\1\40\2\uffff\1\33\2\uffff\1\36\1\uffff"+
        "\1\41\1\42\1\34\1\35\1\uffff\1\37";
    static final String DFA5_specialS =
        "\126\uffff}>";
    static final String[] DFA5_transitionS = {
            "\2\37\1\uffff\2\37\22\uffff\1\37\1\10\2\uffff\1\36\1\uffff\1"+
            "\1\1\uffff\1\16\1\15\1\13\1\11\1\uffff\1\12\1\uffff\1\14\12"+
            "\35\1\3\1\4\1\6\1\7\1\5\2\uffff\32\36\1\25\3\uffff\1\36\1\uffff"+
            "\1\27\2\36\1\23\1\33\1\22\2\36\1\21\3\36\1\32\1\36\1\24\2\36"+
            "\1\31\1\26\1\34\2\36\1\30\3\36\1\20\1\2\1\17",
            "",
            "",
            "\1\40",
            "",
            "\1\42",
            "\1\44",
            "",
            "\1\46",
            "",
            "\1\50",
            "",
            "",
            "",
            "",
            "",
            "",
            "\1\52",
            "\1\54\7\uffff\1\53",
            "\1\55",
            "\1\56",
            "",
            "\1\57",
            "\1\60",
            "\1\61",
            "\1\62",
            "\1\63",
            "\1\64",
            "\1\65",
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
            "\1\36\13\uffff\12\36\7\uffff\32\36\4\uffff\1\36\1\uffff\32"+
            "\36",
            "\1\36\13\uffff\12\36\7\uffff\32\36\4\uffff\1\36\1\uffff\32"+
            "\36",
            "\1\70",
            "\1\36\13\uffff\12\36\7\uffff\32\36\4\uffff\1\36\1\uffff\32"+
            "\36",
            "\1\36\13\uffff\12\36\7\uffff\32\36\4\uffff\1\36\1\uffff\32"+
            "\36",
            "\1\73",
            "\1\74",
            "\1\75",
            "\1\76",
            "\1\77",
            "\1\100",
            "\1\101",
            "",
            "",
            "\1\102",
            "",
            "",
            "\1\103",
            "\1\104",
            "\1\105",
            "\1\106",
            "\1\107",
            "\1\36\13\uffff\12\36\7\uffff\32\36\4\uffff\1\36\1\uffff\32"+
            "\36",
            "\1\111",
            "\1\112",
            "\1\36\13\uffff\12\36\7\uffff\32\36\4\uffff\1\36\1\uffff\32"+
            "\36",
            "\1\114",
            "\1\115",
            "\1\36\13\uffff\12\36\7\uffff\32\36\4\uffff\1\36\1\uffff\32"+
            "\36",
            "\1\117",
            "",
            "\1\36\13\uffff\12\36\7\uffff\32\36\4\uffff\1\36\1\uffff\32"+
            "\36",
            "\1\36\13\uffff\12\36\7\uffff\32\36\4\uffff\1\36\1\uffff\32"+
            "\36",
            "",
            "\1\36\13\uffff\12\36\7\uffff\32\36\4\uffff\1\36\1\uffff\32"+
            "\36",
            "\1\36\13\uffff\12\36\7\uffff\32\36\4\uffff\1\36\1\uffff\32"+
            "\36",
            "",
            "\1\124",
            "",
            "",
            "",
            "",
            "\1\36\13\uffff\12\36\7\uffff\32\36\4\uffff\1\36\1\uffff\32"+
            "\36",
            ""
    };

    static final short[] DFA5_eot = DFA.unpackEncodedString(DFA5_eotS);
    static final short[] DFA5_eof = DFA.unpackEncodedString(DFA5_eofS);
    static final char[] DFA5_min = DFA.unpackEncodedStringToUnsignedChars(DFA5_minS);
    static final char[] DFA5_max = DFA.unpackEncodedStringToUnsignedChars(DFA5_maxS);
    static final short[] DFA5_accept = DFA.unpackEncodedString(DFA5_acceptS);
    static final short[] DFA5_special = DFA.unpackEncodedString(DFA5_specialS);
    static final short[][] DFA5_transition;

    static {
        int numStates = DFA5_transitionS.length;
        DFA5_transition = new short[numStates][];
        for (int i=0; i<numStates; i++) {
            DFA5_transition[i] = DFA.unpackEncodedString(DFA5_transitionS[i]);
        }
    }

    class DFA5 extends DFA {

        public DFA5(BaseRecognizer recognizer) {
            this.recognizer = recognizer;
            this.decisionNumber = 5;
            this.eot = DFA5_eot;
            this.eof = DFA5_eof;
            this.min = DFA5_min;
            this.max = DFA5_max;
            this.accept = DFA5_accept;
            this.special = DFA5_special;
            this.transition = DFA5_transition;
        }
        public String getDescription() {
            return "1:1: Tokens : ( AND | OR | ASSIGN | SEMI | GREATER_THAN | GREATER_EQ | LESS_THAN | LESS_EQ | EQ | NEQ | PLUS | MINUS | MUL | DIV | NOT | RPAREN | LPAREN | RCURLY | LCURLY | COLON | IF | FI | DO | OD | GUARD | ARROW | SKIP | ABORT | WRITE | READ | MODULE | END | TRUE | FALSE | INTEGER_LITERAL | IDENTIFIER | WS );";
        }
    }
 

}