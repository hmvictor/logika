grammar Logika;

/*root
    : argument
    | proposition
    ;*/

argument
    : premises NEWLINE '∴' conclusion definitions
    ;

premises
    : proposition (NEWLINE proposition)*
    ;

conclusion
    : proposition
    ;

definitions
    : (NEWLINE definition)*
    ;

definition
    : SENTENCE ')' TEXT
    ;

command
    : path_command
    | function_command
    | equiv_command
    | thruth_table_command
    | print_command
    ;

path_command
    : proposition COMMA path_expr
    ;

function_command
    : FUNCNAME proposition_expr (COMMA proposition_expr)*
    ;

equiv_command
    : EQ_FN variation? proposition_expr path_expr?
    ;

variation
    : '(' integer ')' 
    ;

path_expr
    : PATH (/ PATH)* 
    ;

integer
    : DIGIT+
    ;

thruth_table_command
    : 'truth_table' (proposition_expr| 'argument')
    ;

print_command
    : 'print' (proposition_expr| 'argument')
    ;

proposition_expr
    : integer
    | proposition
    ;

/*root
    : argument
    ;
*/
proposition 
    : unary_operation
    | binary_operation
    | sentence_expression
    /*| simple_binary_operation */
    ;

sentence_expression
    : SENTENCE
    ;

operation
    : unary_operation 
    | binary_operation
    ;

unary_operation
    : UNARY_OPERATOR proposition
    ;

/*simple_binary_operation 
    : sentence_expression BINARY_OPERATOR sentence_expression
    ;*/

binary_operation 
    : '(' proposition BINARY_OPERATOR proposition ')'
    ;

EQ_FN
    : 'De.M.'
    | 'Conm.'
    | 'Asoc.'
    | 'Dist.'
    | 'D.N.'
    | 'Trans.'
    | 'Impl.'
    | 'Equiv.'
    | 'Exp.'
    | 'Taut.'
    ;

DIGIT
    : [0-9]
    ;

PATH
    : 'right'
    | 'left'
    | 'operand'
    ;

FUNCNAME
    : 'M.P.'
    | 'M.T.'
    | 'S.H.'
    | 'S.D.'    
    | 'Abs.'
    | 'Simp.'
    | 'D.C.'
    | 'Conj.'
    | 'Ad.'
    ;

SENTENCE 
    : [A-W]
    ;

UNARY_OPERATOR 
    : '~' 
    ;

BINARY_OPERATOR 
    : '˅' 
    | '⊃'
    | '≡'
    | '•'
    ;

THEREOF
    : '∴'
    ;

TEXT
    : '"' ~["\\\r\n]* '"'
    ;

NEWLINE
    : '\n'
    ;

COMMA
    : ','
    ;

WHITESPACE 
    : ' ' -> skip
    ;
