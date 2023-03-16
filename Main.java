import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        int flag = 0;
        while (flag!=1) {
            Scanner scan = new Scanner(System.in);
            System.out.print("Введите дробное выражение (пример ввода ( 1/2 + 1/2 ) * 2/3). Если хотите завершить введите \"quit\": ");
            String expression = scan.nextLine();
            if (expression.equals("quit")){
                flag+=1;
            }else {
                Fraction result = evaluate(expression);
                System.out.println("Результат: " + result);
            }
        }
    }
    public static Fraction evaluate(String expression) {
        String[] tokens = convert(expression.split(" "));
        Fraction[] elements = new Fraction[tokens.length];
        int top = -1;
        for (String token : tokens) {
            if (Mathematicalactions(token)) {
                Fraction value2 = elements[top--];
                Fraction value1 = elements[top--];
                switch (token) {
                    case "+":
                        elements[++top] = value1.summation(value2);
                        break;
                    case "-":
                        elements[++top] = value1.subtraction(value2);
                        break;
                    case "*":
                        elements[++top] = value1.Multiplication(value2);
                        break;
                    case ":":
                        elements[++top] = value1.Division(value2);
                        break;
                }
            } else {
                elements[++top] = new Fraction(token);
            }
        }
        return elements[top];
    }
    public static String[] convert(String[] tokens) {
        StringBuilder output = new StringBuilder();
        String[] stack = new String[tokens.length];
        int top = -1;
        for (String token : tokens) {
            if (Mathematicalactions(token)) {
                while (top >= 0 && Precedence(token) <= Precedence(stack[top])) {
                    output.append(stack[top--]).append(" ");
                }
                stack[++top] = token;

            } else if (token.equals("(")) {stack[++top] = token;
            } else if (token.equals(")")) {
                while (!stack[top].equals("(")) {
                    output.append(stack[top--]).append(" ");
                }
                top--;
            } else {
                output.append(token).append(" ");
            }
        }
        while (top >= 0) {
            output.append(stack[top--]).append(" ");
        }
        return output.toString().trim().split(" ");
    }
    public static boolean Mathematicalactions(String token) {
        return token.equals("+") || token.equals("-") || token.equals("*") || token.equals
                (":") || token.equals("/");
    }
    public static int Precedence(String token) {
        switch (token) {
            case "+":
            case "-":
                return 1;
            case "*":
            case ":":
            case "/":
                return 2;
            default:
                return 0;
        }
    }
    public static class Fraction {
        private int numerator;
        private int denominator;
        public Fraction(String fraction) {
            String[] parts = fraction.split("/");
            this.numerator = Integer.parseInt(parts[0]);
            this.denominator = Integer.parseInt(parts[1]);
         if (this.denominator == 0) {
            throw new RuntimeException("Знаменатель не может быть равен нулю!");
        }}
        public Fraction(int numerator, int denominator) {
            this.numerator = numerator;
            this.denominator = denominator;
        if (this.denominator == 0){
            throw new RuntimeException("Знаменатель не может быть равен нулю!");
        }}
        public Fraction summation(Fraction other) {
            int newNumerator = this.numerator * other.denominator + this.denominator * other.numerator;
            int newDenominator = this.denominator * other.denominator;
            return new Fraction(newNumerator, newDenominator).simplification();
        }
        public Fraction subtraction(Fraction other) {
            int newNumerator = this.numerator * other.denominator - this.denominator * other.numerator;
            int newDenominator = this.denominator * other.denominator;
            return new Fraction(newNumerator, newDenominator).simplification();
        }
        public Fraction Multiplication(Fraction other) {
            int newNumerator = this.numerator * other.numerator;
            int newDenominator = this.denominator * other.denominator;
            return new Fraction(newNumerator, newDenominator).simplification();
        }
        public Fraction Division(Fraction other) {
            int newNumerator = this.numerator * other.denominator;
            int newDenominator = this.denominator * other.numerator;
            return new Fraction(newNumerator, newDenominator).simplification();
        }
        public Fraction simplification() {
            int gcd = gcd(numerator, denominator);
            return new Fraction(numerator / gcd, denominator / gcd);
        }
        private int gcd(int a, int b) {
            return b == 0 ? a : gcd(b, a % b);
        }
        public String toString() {
            return numerator + "/" + denominator;
        }
    }
}
