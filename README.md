# Assignment 5: Fix It, Pass It On, Harden It

Merge Robotics | Paired assignment | Java + Git

The pit crew's parts **Inventory Manager** was started by someone who left the team. It does not compile, gives wrong answers, and crashes on bad input. You and a partner will fix it and extend it, passing the work to each other through Git.

| | Part 1: The Fixer | Part 2: The Hardener |
|---|---|---|
| Goal | Make the program compile and behave as intended | Add a new feature and stop the program crashing |
| Skills | Debugging (syntax, logic, runtime errors) | try / catch / finally, `throw` |
| Starts when | Right away | After Part 1 is pushed to GitHub |

You and your partner decide who does which part. **Each of you must make your own commits**.

---

## Step 0: Setup (do this together)

1. **Partner 1** (the person doing Part 1) forks the instructor repo on GitHub (**Fork** button, top right).
2. Partner 1 opens the fork's **Settings > Collaborators** and adds Partner 2 using their GitHub username. Partner 2 accepts the invitation by email.
3. **Both partners** clone Partner 1's fork:
   ```
   git clone https://github.com/<partner1-username>/<repo-name>.git
   ```
4. **Both partners** tell Git who they are (so commits show your name):
   ```
   git config user.name "Your Name"
   git config user.email "you@example.com"
   ```
5. Open the folder in VS Code. Compile and run with:
   ```
   javac InventoryManager.java
   java InventoryManager
   ```

---

## What the program is supposed to do

| Menu | Behaviour |
|---|---|
| 1. View inventory | Lists every part (all 5) with quantity and price |
| 2. Total inventory value | Sum of quantity x price for every part, then back to the menu |
| 3. Use parts | Removes units from a part's stock. Refuses if there is not enough stock |
| 4. Restock a part | Adds units to a part's stock |
| 5. Update a price | Changes a part's price |
| 6. Average stock | Whole-number average units, across parts that have at least 1 unit |
| 7. Search for a part | Prints the part's details, or says it was not found |
| 8. Low-stock report | Lists parts with **fewer than 5** units |
| 9. Order quote | **Not built yet. Part 2 builds it** |
| 0. Exit | Quits |

---

## Part 1: The Fixer

The program has **syntax errors, logic errors and runtime errors** (see Lecture 4). Your job is to find and fix all of them so options 1 to 8 behave as the table above says.

**Tips**
- Fix compile errors first. The compiler may only show some of them at a time, so recompile after each fix.
- Logic errors compile and run but give wrong results. Work out what you **expected**, what **actually happened**, and why they differ (Trace, Analyze, Develop).
- Use the VS Code debugger: set breakpoints and watch the values in the Locals panel. Printing values also works.
- Test every menu option, including options that should say "not found" or "not enough stock".
- Do not rewrite the program. Make the smallest change that fixes each bug.

**Part 1 checklist**
- Program compiles with no errors
- Menu options 1 to 8 give correct results for valid input
- At least **three separate commits**, one each for syntax fixes, logic fixes and runtime fixes. Use clear messages, for example `Fix missing semicolon in printInventory`
- Everything pushed to GitHub (`git push`)

Some exceptions will still crash the program when a user types bad input. That is expected. Those are Part 2's job, listed in `EXCEPTION_CHECKLIST.md`. Do **not** add try/catch in Part 1. Do **not** build option 9.

---

## Part 2: The Hardener

Wait until your partner has pushed Part 1, then pull their work to your computer:
```
git pull
```
Open `EXCEPTION_CHECKLIST.md`. It lists every exception you need to handle, how to trigger each one, and where it is thrown. Run the program and reproduce each crash before you fix it. Tick the boxes in the checklist as you go and commit the file with your code.

### Task A: Catch the exceptions
Use `try` / `catch` so that **none of the exceptions in `EXCEPTION_CHECKLIST.md` can crash the program**. When one happens:
- Print a clear, friendly message (not a stack trace)
- Return to the menu

Requirements:
- Use a **specific `catch` block for each exception type**. A single `catch (Exception e)` for everything does not count.
- Use at least one `finally` block.
- Watch the order of your catch blocks. Some exception types are subclasses of others, and Java will not compile if a parent type is caught before its child.
- Hint: when `Scanner.nextInt()` throws, the bad text is still waiting in the input. If you do not discard it, the menu will loop forever.

### Task B: Build option 9, Order Quote
Fill in `orderQuote()`. It should:
1. Ask the user for a **target stock level** (a whole number).
2. For each part with fewer units than the target, calculate how many to order and what it costs (`units needed x price`).
3. Print one line per part to order, then a **quote total**.
4. **Throw** an `IllegalArgumentException` with a helpful message if the target is negative, and make sure your catch blocks from Task A handle it.

Example (target 10, with the starting stock):
```
--- Order Quote (target 10 units each) ---
Motor    order   2 x $24.99 = $49.98
Battery  order   7 x $89.50 = $626.50
Sensor   order   6 x $35.25 = $211.50
Bumper   order   4 x $18.75 = $75.00
Quote total: $962.98
```
Your formatting does not have to match exactly, but the numbers must.

**Part 2 checklist**
- You pulled Partner 1's commits before starting
- Every exception in `EXCEPTION_CHECKLIST.md` is caught with its own `catch` and a friendly message, and the checklist is ticked off
- At least one `finally` block
- Order Quote works and uses `throw` for a negative target
- At least **two commits** with clear messages (for example one for exception handling, one for the new feature)
- Pushed to GitHub (`git push`)

---

## Git commands you will need

```
git status                         # what has changed?
git add InventoryManager.java      # stage a file for the next commit
git commit -m "Fix missing semicolon in printInventory"
git push                           # upload your commits to GitHub
git pull                           # download your partner's commits
git log --oneline                  # list commits with author
```
In VS Code you can do the same from the **Source Control** tab (stage with `+`, type a message, click Commit, then Sync).

Commit after each fix, not once at the end. If `git push` is rejected, run `git pull` first, then push again.

---

## Sharing your work

When you are both done, share the **URL of your forked repo** with the instructor. Make sure everything is pushed first.
