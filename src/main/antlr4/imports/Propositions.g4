grammar Propositions;

propositionalExpression: sentenceExpression | operation;

sentenceExpression: SENTENCE;

operation: unaryOperation | binaryOperation;

complexOperand : unaryOperation | '(' binaryOperation ')';

unaryOperation  : UNARY_OPERATOR sentenceExpression 
                | UNARY_OPERATOR complexOperand;

binaryOperation : sentenceExpression BINARY_OPERATOR sentenceExpression 
                | sentenceExpression BINARY_OPERATOR complexOperand 
                | complexOperand BINARY_OPERATOR sentenceExpression
                | complexOperand BINARY_OPERATOR complexOperand; 

UNARY_OPERATOR : '~';

BINARY_OPERATOR : '˅' | '⊃' | '≡' | '•';

SENTENCE : [A-Z];
