package menu;

import java.util.Scanner;

public class InputValidator {
	
	private Scanner scanner = new Scanner(System.in);
	
	public int getChoice() {
        while (true) {
            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                scanner.nextLine(); // consume the newline
                return choice;
            } else {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine(); // consume the invalid input
            }
        }
    }
	
	public InputValidator(Scanner scanner) {
		super();
		this.scanner = scanner;
	}

	public String getValidName() {
        while (true) {
            System.out.print("Enter your name: ");
            String name = scanner.nextLine().trim();
            
            if (name.isEmpty()) {
                System.out.println("Name cannot be empty. Please enter your name.");
            } else if (name.length() < 2) {
                System.out.println("Name must be at least 2 characters long. Please try again.");
            } else {
                return name;
            }
        }
    }

	public String getValidAddress() {
        while (true) {
            System.out.print("Enter your address: ");
            String address = scanner.nextLine().trim();
            
            if (address.isEmpty()) {
                System.out.println("Address cannot be empty. Please enter your address.");
            } else if (address.length() < 5) {
                System.out.println("Address must be at least 5 characters long. Please try again.");
            } else {
                return address;
            }
        }
    }
    
	public String getValidPhone() {
        while (true) {
            System.out.print("Enter your phone: ");
            String phone = scanner.nextLine().trim();
            
            if (phone.isEmpty()) {
                System.out.println("Phone number cannot be empty. Please try again.");
                continue;
            }
            
            // Check if phone contains only digits and common phone characters
            if (phone.matches("[0-9+\\-\\s()]+")) {
                // Remove all non-digit characters for final validation
                String digitsOnly = phone.replaceAll("[^0-9]", "");
                
                if (digitsOnly.length() >= 8 && digitsOnly.length() <= 15) {
                    return phone; // Return original format with formatting
                } else {
                    System.out.println("Phone number must be 8-15 digits long. Please try again.");
                }
            } else {
                System.out.println("Phone number can only contain numbers, spaces, hyphens, parentheses, and +. Please try again.");
            }
        }
    }
    
	public boolean getYesNoChoice(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim().toLowerCase();
            
            if (input.equals("y") || input.equals("yes")) {
                return true;
            } else if (input.equals("n") || input.equals("no")) {
                return false;
            } else {
                System.out.println("Please enter 'y' for yes or 'n' for no.");
            }
        }
    }
}
