package shuntingyard;
import java.util.Stack;
import java.lang.StringBuilder;
public class Expression {
    private String contents;
    
    public Expression(){
        this("");
    }

    public Expression(String contents){
        this.contents = contents;
    }

    public String convertToPostfix(){
        Stack<Character> waitin = new Stack<>();
        StringBuilder output = new StringBuilder();
        char to_check;

        for(int i = 0; i <this.contents.length(); i++){
            to_check = this.contents.charAt(i);
            if(isDigit(to_check))
                output.append(to_check);
            else if(isLetter(to_check))
                output.append(to_check);
            else if(isOperator(to_check)){
                while(waitin.size() != 0 && compareOperators(to_check, waitin.peek())){
                    output.append(waitin.pop());
                }
                waitin.push(to_check);
            }
            else if(to_check == '(')
                waitin.push(to_check);
            else if(to_check == ')'){
                while(waitin.peek() != '('){
                    output.append(waitin.pop());
                    if(waitin.size() == 0) throw new IllegalArgumentException("Expression must have matched parenthesis");
                }
                waitin.pop(); // remove the left parenthesis.
            }
            else throw new IllegalStateException("Cannot convert an expression with illegal characters to postfix");
        }

        while(waitin.size() != 0){
            output.append(waitin.pop());
        }

        this.contents = output.toString();
        return this.contents;
    }

    public String toString(){
        return this.contents;
    }

    //Ascii chars: "!"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\]^_`abcdefghijklmnopqrstuvwxyz{|}~"

    protected static boolean isOperator(char to_check){
        return to_check == '+' || to_check == '-' || to_check == '*' || to_check == '/' || to_check == '^';
    }

    protected static boolean isDigit(char to_check){
        return to_check <= '9' && to_check >= '0';
    }

    protected static boolean isLetter(char to_check){
        return isLowerCase(to_check) || isUpperCase(to_check);
    }

    protected static boolean isLowerCase(char to_check){
        return  to_check >= 'a' && to_check <= 'z';
    }
    protected static boolean isUpperCase(char to_check){
        return to_check >= 'A' && to_check <= 'Z';
    }

    protected static boolean compareOperators(char A, char B){
        return operatorPrecedence(B) >= operatorPrecedence(A);
    }

    protected static int operatorPrecedence(char op){
        if(op == '(') return 0; //parenthesis are NOT handled by operator preference. This forces it to leave them alone.
        if(op == '+') return 1;
        if(op == '-') return 1;
        if(op == '*') return 2;
        if(op == '/') return 2;
        if(op == '^') return 3;
        throw new IllegalArgumentException();
    }
}
