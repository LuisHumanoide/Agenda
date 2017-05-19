package translator;
import java_cup.runtime.*; // defines the Symbol class

// The generated scanner will return a Symbol for each token that it finds.
// A Symbol contains an Object field named value; that field will be of type
// TokenVal, defined below.
//
// A TokenVal object contains the line number on which the token occurs as
// well as the number of the character on that line that starts the token.
// Some tokens (literals and IDs) also include the value of the token.

class TokenVal {
  // fields
    int linenum;
    int charnum;
  // constructor
    TokenVal(int line, int ch) {
        linenum = line;
        charnum = ch;
    }
}

class IntLitTokenVal extends TokenVal {
  // new field: the value of the integer literal
    int intVal;
  // constructor
    IntLitTokenVal(int line, int ch, int val) {
        super(line, ch);
        intVal = val;
    }
}

class IdTokenVal extends TokenVal {
  // new field: the value of the identifier
    String idVal;
  // constructor
    IdTokenVal(int line, int ch, String val) {
        super(line, ch);
    idVal = val;
    }
}

class StrLitTokenVal extends TokenVal {
  // new field: the value of the string literal
    String strVal;
  // constructor
    StrLitTokenVal(int line, int ch, String val) {
        super(line, ch);
        strVal = val;
    }
}

// The following class is used to keep track of the character number at which
// the current token starts on its line.
class CharNum {
    static int num=1;
}
%%

DIGIT=        [0-9]
WHITESPACE=   [\040\t]
LETTER=       [a-zA-Z]
MAYUS=        [A-Z]
ESCAPEDCHAR=   [nt'\"?\\]
NOTNEWLINEORESCAPEDCHAR=   [^\nnt'\"?\\]
NOTNEWLINEORQUOTE= [^\n\"]
NOTNEWLINEORQUOTEORESCAPE= [^\n\"\\]

%implements java_cup.runtime.Scanner
%function next_token
%type java_cup.runtime.Symbol

%eofval{
return new Symbol(sym.EOF);
%eofval}

%line

%%

"create"          {return new Symbol(sym.CREATE, yychar, yyline);}   
"make"            {return new Symbol(sym.MAKE, yychar, yyline);}   
"add"             {return new Symbol(sym.ADD, yychar, yyline);}   
"delete"          {return new Symbol(sym.DELETE, yychar, yyline);}     
"remove"          {return new Symbol(sym.REMOVE, yychar, yyline);}     
"cancel"          {return new Symbol(sym.CANCEL, yychar, yyline);}     
"move"            {return new Symbol(sym.MOVE, yychar, yyline);}   
"change"          {return new Symbol(sym.CHANGE, yychar, yyline);}     
"date"            {return new Symbol(sym.DATE, yychar, yyline);}   
"commitment"      {return new Symbol(sym.COMMITMENT, yychar, yyline);}         
"with"            {return new Symbol(sym.WITH, yychar, yyline);}   
"at"              {return new Symbol(sym.AT, yychar, yyline);} 
"from"            {return new Symbol(sym.FROM, yychar, yyline);}   
"on"              {return new Symbol(sym.ON, yychar, yyline);} 
"in"              {return new Symbol(sym.IN, yychar, yyline);}           
"today"           {return new Symbol(sym.TODAY, yychar, yyline);}     
"monday"          {return new Symbol(sym.MONDAY, yychar, yyline);}     
"tuesday"         {return new Symbol(sym.TUESDAY, yychar, yyline);}       
"wednesday"       {return new Symbol(sym.WEDNESDAY, yychar, yyline);}         
"thursday"        {return new Symbol(sym.THURSDAY, yychar, yyline);}       
"friday"          {return new Symbol(sym.FRIDAY, yychar, yyline);}     
"saturday"        {return new Symbol(sym.SATURDAY, yychar, yyline);}       
"sunday"          {return new Symbol(sym.SUNDAY, yychar, yyline);}               
"january"         {return new Symbol(sym.JANUARY, yychar, yyline);}       
"february"        {return new Symbol(sym.FEBRUARY, yychar, yyline);}       
"march"           {return new Symbol(sym.MARCH, yychar, yyline);}     
"april"           {return new Symbol(sym.APRIL, yychar, yyline);}     
"may"             {return new Symbol(sym.MAY, yychar, yyline);}   
"june"            {return new Symbol(sym.JUNE, yychar, yyline);}   
"july"            {return new Symbol(sym.JULY, yychar, yyline);}   
"august"          {return new Symbol(sym.AUGUST, yychar, yyline);}     
"september"       {return new Symbol(sym.SEPTEMBER, yychar, yyline);}         
"october"         {return new Symbol(sym.OCTOBER, yychar, yyline);}       
"november"        {return new Symbol(sym.NOVEMBER, yychar, yyline);}       
"december"        {return new Symbol(sym.DECEMBER, yychar, yyline);}
"to"              {return new Symbol(sym.TO, yychar, yyline);}           

"long"            {return new Symbol(sym.LONG, yychar, yyline);}       
"medium"          {return new Symbol(sym.MEDIUM, yychar, yyline);}         
"short"           {return new Symbol(sym.SHORT, yychar, yyline);} 

"the"   {return new Symbol(sym.THE, yychar, yyline);} 
"my"    {return new Symbol(sym.MY, yychar, yyline);} 
"your"    {return new Symbol(sym.YOUR, yychar, yyline);} 
"its"   {return new Symbol(sym.ITS, yychar, yyline);} 
"his"   {return new Symbol(sym.HIS, yychar, yyline);} 
"her"   {return new Symbol(sym.HER, yychar, yyline);} 
"their"   {return new Symbol(sym.THEIR, yychar, yyline);} 
"our"   {return new Symbol(sym.OUR, yychar, yyline);} 
 

({MAYUS})({LETTER}|{DIGIT}|"_")* {
            {return new Symbol(sym.OBJECT, yychar, yyline, new String(yytext()));}
}


{DIGIT}+  {return new Symbol(sym.INTLITERAL, yychar, yyline, new String(yytext()));}

(({DIGIT}":"{DIGIT}{DIGIT})|({DIGIT}{DIGIT}":"{DIGIT}{DIGIT})) {return new Symbol(sym.HOUR, yychar, yyline, new String(yytext()));}

          
\"({NOTNEWLINEORQUOTEORESCAPE}|\\{ESCAPEDCHAR})*\" {return new Symbol(sym.STRINGLITERAL, yychar, yyline, new String(yytext()));}
          
\"({NOTNEWLINEORQUOTEORESCAPE}|\\{ESCAPEDCHAR})* {
            // unterminated string

          }
          
\"({NOTNEWLINEORQUOTEORESCAPE}|\\{ESCAPEDCHAR})*\\{NOTNEWLINEORESCAPEDCHAR}({NOTNEWLINEORQUOTE})*\" {
            // bad escape character

            CharNum.num += yytext().length();
          }
          
\"({NOTNEWLINEORQUOTEORESCAPE}|\\{ESCAPEDCHAR})*(\\{NOTNEWLINEORESCAPEDCHAR})?({NOTNEWLINEORQUOTEORESCAPE}|\\{ESCAPEDCHAR})*\\? {

          }          
          
\n        { CharNum.num = 1; }

{WHITESPACE}+  { CharNum.num += yytext().length(); }

("//"|"#")[^\n]*  { // comment - ignore. Note: don't need to update char num 
            // since everything to end of line will be ignored
          }

"/"       {return new Symbol(sym.SLASH, yychar, yyline);}


.         { 
            return new Symbol(sym.errorlex, yychar, yyline, new String (yytext()));
            
          }