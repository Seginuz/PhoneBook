import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int input = scanner.nextInt();

        int digit1 = input / 1000;
        int digit2 = (input - 1000 * digit1) / 100;
        int digit3 = (input - 1000 * digit1 - 100 * digit2) / 10;
        int digit4 = (input - 1000 * digit1 - 100 * digit2 - 10 * digit3);

        if (digit1 == digit4 && digit2 == digit3) {
            System.out.println(1);
        } else {
            System.out.println(42);
        }
    }
}