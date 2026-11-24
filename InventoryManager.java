import java.sql.Array;
import java.util.Scanner;

// =============================================================================
// Merge Robotics Parts Inventory Manager
//
// A console program the pit crew uses to track robot parts.
// Menu choices and what each one is SUPPOSED to do:
//
//   1. View inventory        - list every part with its quantity and price
//   2. Total inventory value - sum of (quantity x price) for every part
//   3. Use parts             - remove a number of units from a part's stock
//   4. Restock a part        - add a number of units to a part's stock
//   5. Update a price        - change the price of a part
//   6. Average stock         - whole-number average units per part that is in stock
//   7. Search for a part     - look up a part by name, print its details or "not found"
//   8. Low-stock report      - list every part with fewer than LOW_STOCK_LIMIT units
//   9. Order quote           - (NOT BUILT YET - this is the Part 2 feature)
//   0. Exit
// =============================================================================
public class InventoryManager {

    static final int LOW_STOCK_LIMIT = 5;

    static String[] partNames = {"Motor", "Battery", "Wheel", "Sensor", "Bumper"};
    static int[] quantities   = {8, 3, 12, 4, 6};
    static double[] prices    = {24.99, 89.50, 12.00, 35.25, 18.75};

    static String[] reorderList;

    static Scanner scanner = new Scanner(System.in);

    // ---------------------------------------------------------------- Option 1
    public static void printInventory() {
        System.out.println("\n--- Current Inventory ---");
        for (int i = 0; i < partNames.length; i++) {
            System.out.println((i + 1) + ". " + partNames[i] + " | qty: " + quantities[i] + " | $" + prices[i]);
        }
    }

    // ---------------------------------------------------------------- Option 2
    public static double getTotalValue() {
        double totalValue = 0;
        for (int i = 0; i < partNames.length; i++) {
            totalValue += quantities[i] * prices[i];
        }
        return totalValue;
    }

    // ---------------------------------------------------------------- Option 3
    public static void usePart() {
        printInventory();
        boolean invalidInput = true;
        int partNumber = 0, amount = 0;
        while (invalidInput) {
            try {
                System.out.print("Part number to use: ");
                partNumber = scanner.nextInt();
                System.out.print("How many units? ");
                amount = scanner.nextInt();
                invalidInput = false;

            } catch (Exception e) {
                System.out.println("Invalid input. Please enter numeric values within the allowed range.");
                scanner.nextLine();
            }
        }

        int index = partNumber - 1;

        try {
            if (amount < 0) {
                throw new IllegalArgumentException("Cannot use a negative amount of parts!");
            } else if (amount > quantities[index]) {
                throw new IllegalArgumentException("Not enough " + partNames[index] + " in stock!");
            } else if (amount == 0) {
                System.out.println("No parts were used.");
                return;
            }

            quantities[index] -= amount;
            System.out.println("Used " + amount + " " + partNames[index]
                    + ". Remaining: " + quantities[index]);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid part number!");
        }
        }

    // ---------------------------------------------------------------- Option 4
    public static void restockPart() {
        printInventory();
        boolean invalidInput = true;
        int partNumber = 0, amount = 0;
        while (invalidInput) {
            try {
                System.out.print("Part number to restock: ");
                partNumber = scanner.nextInt();
                System.out.print("How many units? ");
                amount = scanner.nextInt();
                invalidInput = false;

            } catch (Exception e) {
                System.out.println("Invalid input. Please enter numeric values.");
                scanner.nextLine();
            }
        }

        try {
            if (amount < 0) {
                throw new IllegalArgumentException("Cannot restock a negative amount of parts!");
            } else if (amount == 0) {
                System.out.println("No parts were restocked.");
                return;
            } else if (partNumber < 1 || partNumber > partNames.length) {
                throw new IllegalArgumentException("Invalid part number!");
            }
            int index = partNumber - 1;
            quantities[index] += amount;
            System.out.println("Restocked " + partNames[index] + ". New quantity: " + quantities[index]);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid number entered!");
        }
    }

    // ---------------------------------------------------------------- Option 5
    public static void updatePrice() {
        printInventory();
        boolean invalidInput = true;
        int partNumber = 0;
        double newPrice = 0;
        while (invalidInput) {
            try {
                System.out.print("Part number to reprice: ");
                partNumber = scanner.nextInt();
                System.out.print("New price: ");
                newPrice = Double.parseDouble(scanner.next());
                invalidInput = false;
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter numeric values.");
                scanner.nextLine();
            }
        }
        try {
            if (newPrice < 0) {
                throw new IllegalArgumentException("Price cannot be negative!");
            } else if (partNumber < 1 || partNumber > partNames.length) {
                throw new IllegalArgumentException("Invalid part number!");
            }
            int index = partNumber - 1;
            prices[index] = newPrice;
        System.out.println(partNames[index] + " now costs $" + prices[index]);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid input. Please enter numeric values.");
        }
        
    }

    // ---------------------------------------------------------------- Option 6
    public static int averageStockPerStockedPart() {
        int totalUnits = 0;
        int stockedParts = 0;
        for (int i = 0; i < partNames.length; i++) {
            if (quantities[i] > 0) {
                totalUnits += quantities[i];
                stockedParts++;
            }
        }
        if (stockedParts == 0) {
            return 0;
        }
        return totalUnits / stockedParts;
    }

    // ---------------------------------------------------------------- Option 7
    // Returns the index of the part with this name, or -1 if there is no match.
    public static int findPart(String name) {
        for (int i = 0; i < partNames.length; i++) {
            if (partNames[i].equalsIgnoreCase(name)) {
                return i;
            }
        }
        return -1;
    }

    public static void searchPart() {
        System.out.print("Part name to search for: ");
        String name = scanner.next();
        int index = findPart(name);
        if (index == -1) {
            System.out.println("No part named \"" + name + "\" was found.");
        } else {
            System.out.println(partNames[index] + " | qty: " + quantities[index] + " | $" + prices[index]);
        }
    }

    // ---------------------------------------------------------------- Option 8
    public static void lowStockReport() {
        int count = 0;
        reorderList = new String[partNames.length];
        for (int i = 0; i < partNames.length; i++) {
            if (quantities[i] < LOW_STOCK_LIMIT) {
                reorderList[count] = partNames[i];
                count++;
            }
        }

        System.out.println("\n--- Low Stock (fewer than " + LOW_STOCK_LIMIT + ") ---");
        if (count == 0) {
            System.out.println("Everything is well stocked.");
        }
        for (int i = 0; i < count; i++) {
            System.out.println("- " + reorderList[i]);
        }
    }

    // ---------------------------------------------------------------- Option 9
    // PART 2 FEATURE - not written yet.
    public static void orderQuote() {
        int targetStockLevel = 0;
        String[] partsToOrder = new String[partNames.length];
        int[] partsToOrderQuantities = new int[partNames.length];
        double[] partsToOrderPrices = new double[partNames.length];
        double quoteTotal = 0;

        try {
            System.out.println("Enter the desired stock level for all parts:");
            targetStockLevel = scanner.nextInt();
        } catch (Exception e) {
            System.out.println("Invalid input. Please enter a positive integer value.");
            return;
        } finally {
            scanner.nextLine();
        }

        if (targetStockLevel < 0) {
            System.out.println("Target stock level cannot be negative. Please enter a positive integer.");
            return;
        }

        for (int i = 0; i < partNames.length; i++) {
            if (quantities[i] < targetStockLevel) {
                partsToOrder[i] = partNames[i];
                partsToOrderQuantities[i] = targetStockLevel - quantities[i];
            }
        }

        for (int i = 0; i < partsToOrder.length; i++) {
            if (partsToOrder[i] != null) {
                int index = findPart(partsToOrder[i]);
                partsToOrderPrices[i] = prices[index];
            }
        }

        System.out.println("\n--- Order Quote ---");
        for (int i = 0; i < partsToOrder.length; i++) {
            if (partsToOrder[i] != null) {
                System.out.println(partsToOrder[i] + " order | " + partsToOrderQuantities[i] + " x $" + partsToOrderPrices[i] + " = $" + (partsToOrderQuantities[i] * partsToOrderPrices[i]));
                quoteTotal += partsToOrderQuantities[i] * partsToOrderPrices[i];
            }
        }
        System.out.printf("Quote total = $%.2f", quoteTotal);

        return;
    }

    // ------------------------------------------------------------------- Menu
    public static void printMenu() {
        System.out.println("\n===== Merge Robotics Inventory =====");
        System.out.println("1. View inventory");
        System.out.println("2. Total inventory value");
        System.out.println("3. Use parts");
        System.out.println("4. Restock a part");
        System.out.println("5. Update a price");
        System.out.println("6. Average stock per stocked part");
        System.out.println("7. Search for a part");
        System.out.println("8. Low-stock report");
        System.out.println("9. Order quote");
        System.out.println("0. Exit");
        System.out.print("Choose an option: ");
    }

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            printMenu();

            boolean invalidInput = true;
            int choice = 0;
            while (invalidInput) {
                try {
                    choice = scanner.nextInt();
                    invalidInput = false;

                } catch (Exception e) {
                    System.out.println("Invalid input. Please enter numeric values.");
                    scanner.nextLine();
               }
            }

            switch (choice) {
                case 1:
                    printInventory();
                    break;
                case 2:
                    System.out.printf("Total inventory value: $%.2f%n", getTotalValue());
                    break;
                case 3:
                    usePart();
                    break;
                case 4:
                    restockPart();
                    break;
                case 5:
                    updatePrice();
                    break;
                case 6:
                    System.out.println("Average stock per stocked part: " + averageStockPerStockedPart());
                    break;
                case 7:
                    searchPart();
                    break;
                case 8:
                    lowStockReport();
                    break;
                case 9:
                    orderQuote();
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Please choose a number from 0 to 9.");
            }
        }
        System.out.println("Goodbye!");
    }
}
