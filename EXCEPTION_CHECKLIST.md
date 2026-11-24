# Part 2 Exception Checklist

Part 2 author: ________

Every exception below can crash the program once Part 1 is fixed. Your job is to make sure **none of them can**. For each one:

1. **Reproduce** it first. Run the program, follow the "How to trigger it" column, and read the stack trace.
2. **Handle** it with its own `catch` block. Print a friendly message (no stack trace) and return to the menu.
3. **Test** it again and confirm the program prints your message and shows the menu.
4. Tick the boxes and commit this file along with your code.

## Exceptions to handle

| # | Exception | What it means | How to trigger it | Where it is thrown | Reproduced | Caught | Tested |
|---|-----------|---------------|-------------------|--------------------|:---:|:---:|:---:|
| 1 | `InputMismatchException` | Text was typed where a whole number was expected | Type `abc` at the menu, or as a part number, amount or target (options 3, 4, 5, 9) | any `scanner.nextInt()` | [Y ] | [Y ] | [Y ] |
| 2 | `ArrayIndexOutOfBoundsException` | A part number that is not in the inventory | Options 3, 4 or 5: enter part number `0` or `6` | `usePart`, `restockPart`, `updatePrice` | [Y ] | [Y ] | [Y ] |
| 3 | `NumberFormatException` | A String cannot be converted to a number | Option 5: enter `abc` as the new price | `updatePrice` (`Double.parseDouble`) | [Y ] | [Y ] | [Y ] |
| 4 | `ArithmeticException` | Whole-number division by zero | Use up **all** units of **all five** parts with option 3, then choose option 6 | `averageStockPerStockedPart` | [Y ] | [Y ] | [Y ] | COMMENT: This was already fixed before because an if-else statement is used to take care of this very case of division by zero.
| 5 | `IllegalArgumentException` (not enough stock) | The program deliberately throws this when you use more than you have | Option 3: use `99` units of any part | `usePart` | [Y ] | [Y ] | [Y ] |
| 6 | `IllegalArgumentException` (negative target) | **You** throw this in your Order Quote code | Option 9: enter `-1` as the target | `orderQuote` (you write this) | [Y ] | [Y ] | [Y ] |

## Rules for how you handle them

- [Y ] Each exception type above has its **own** `catch` block (do not use a single `catch (Exception e)` for everything)
- [Y ] Every message is friendly and tells the user what to do (for example "Please type a whole number"), with no stack trace
- [Y ] After any exception the program **returns to the menu** and keeps running
- [Y ] At least one `finally` block is used
- [Y ] After `InputMismatchException`, the bad input is cleared so the menu does not loop forever
- [Y ] Catch blocks are ordered so the program compiles (see the hint below)

**Hint:** Some exception types are subclasses of others. Java will not compile if a parent type is caught before its child. Check the Java documentation to see which of the six above are related to each other.

## Order Quote feature (option 9)

- [Y ] Asks for a target stock level
- [Y ] Prints one line per part below the target (units to order x price)
- [Y ] Prints a quote total
- [Y ] Uses `throw new IllegalArgumentException(...)` for a negative target (row 6 above)

## Final check before you push

- [Y ] I pulled my partner's Part 1 commits before I started
- [Y ] I ran every row in the table above one last time and nothing crashes
- [Y ] I have at least two commits with clear messages
- [Y ] I pushed everything (`git push`)
