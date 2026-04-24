import java.util.*;

public class revisiStack {

    static int precedence(char c) {
        if (c == '+' || c == '-') return 1;
        if (c == '*' || c == '/') return 2;
        if (c == '^') return 3;
        return -1;
    }

    static String infixToPostfix(String exp) {
        Stack<Character> stack = new Stack<>();
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < exp.length(); i++) {
            char c = exp.charAt(i);

            // ✅ FIX: baca semua digit berturut-turut sebagai satu angka utuh
            if (Character.isDigit(c)) {
                StringBuilder angka = new StringBuilder();
                while (i < exp.length() && Character.isDigit(exp.charAt(i))) {
                    angka.append(exp.charAt(i));
                    i++;
                }
                i--; // mundur 1 karena for-loop akan i++ lagi

                result.append(angka).append(" "); // pisah dengan spasi
                System.out.println("Operand: " + angka);
            }

            else if (c == '(') {
                stack.push(c);
                System.out.println("Push: " + c);
            }

            else if (c == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    System.out.println("Pop: " + stack.peek());
                    result.append(stack.pop()).append(" ");
                }
                stack.pop(); // buang '('
                System.out.println("Pop: (");
            }

            else if (precedence(c) > 0) {
                while (!stack.isEmpty() &&
                        precedence(c) <= precedence(stack.peek())) {
                    System.out.println("Pop: " + stack.peek());
                    result.append(stack.pop()).append(" ");
                }
                stack.push(c);
                System.out.println("Push: " + c);
            }
        }

        while (!stack.isEmpty()) {
            System.out.println("Pop: " + stack.peek());
            result.append(stack.pop()).append(" ");
        }

        return result.toString().trim();
    }

    static int evaluatePostfix(String exp) {
        Stack<Integer> stack = new Stack<>();

        // ✅ FIX: token dipisah berdasarkan spasi, bukan karakter satu-satu
        String[] tokens = exp.split(" ");

        for (String token : tokens) {
            if (token.isEmpty()) continue;

            // Cek apakah token adalah angka (termasuk multi-digit)
            if (token.matches("-?\\d+")) {
                stack.push(Integer.parseInt(token));
            } else {
                char op = token.charAt(0);
                int b = stack.pop();
                int a = stack.pop();

                switch (op) {
                    case '+': stack.push(a + b); break;
                    case '-': stack.push(a - b); break;
                    case '*': stack.push(a * b); break;
                    case '/': stack.push(a / b); break;
                    case '^': stack.push((int) Math.pow(a, b)); break;
                }
            }
        }

        return stack.pop();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Input Infix: ");
        String infix = sc.next();

        String postfix = infixToPostfix(infix);
        System.out.println("Postfix: " + postfix);

        int result = evaluatePostfix(postfix);
        System.out.println("Hasil: " + result);
    }
}
