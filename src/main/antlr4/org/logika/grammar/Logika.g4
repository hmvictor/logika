grammar Logika;

import Propositions;

root: argument | command;

argument : premises NEWLINE '∴' conclusion definitions;

premises : propositionalExpression (NEWLINE propositionalExpression)*;

conclusion : propositionalExpression;

definitions : (NEWLINE definition)*;

definition : SENTENCE ')' TEXT;

command
    : deductionCommand
    | equivalenceCommand
    | pathCommand
    | expressionCommand
    | indirectProof;
/*    | thruth_table_command
    | print_command
    ;*/

indirectProof : 'P.I.';

deductionCommand : propExpOperand (',' propExpOperand)? DEDUCTION_RULE_NAME;

equivalenceCommand : propExpOperand expressionPath? EQ_RULE_NAME discriminator;

propExpOperand : integer | propositionalExpression;

discriminator : '(' integer ')';

expressionPath : (/ PATH)+;

integer
    : DIGIT+
    ;

pathCommand : propExpOperand expressionPath;

expressionCommand : propExpOperand;

/*
thruth_table_command
    : 'truth_table' (proposition_expr| 'argument')
    ;

print_command
    : 'print' (proposition_expr| 'argument')
    ;
*/

EQ_RULE_NAME
    : 'DeM.'
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

DEDUCTION_RULE_NAME
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

DIGIT : [0-9];

PATH : 'right' | 'left' | 'operand';

THEREOF : '∴';

TEXT : '"' ~["\\\r\n]* '"';

//TEXT_CHARACTER : ~["\\\r\n];

NEWLINE : '\n';

COMMA : ',';

WHITESPACE : ' ' -> skip;
