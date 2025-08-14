# Connect 4 (JavaFX)  

A polished, playable Connect 4 game built with **Java** and **JavaFX**. It supports **1–2 players**, features a **Minimax AI with alpha–beta pruning** (three difficulty levels), and tracks your **best completion times** per difficulty.

> Main class: `project.GameApp`  
> UI: FXML (`project/Game.fxml`)  

---

## Features

- **Play modes:** 1 Player (vs AI) or 2 Players (local).  
- **AI difficulties:** Easy, Medium, Hard — implemented with a Minimax search + alpha–beta pruning.  
  - Easy and Medium: **You start**.  
  - Hard: **AI starts**.  
- **Classic board:** 7 columns × 6 rows.  
---

## Tech Stack

- **Language:** Java (11+ recommended)  
- **UI:** JavaFX (controls + FXML)  
- **Architecture:** MVC-ish split across `Board`, `Game`, `GameController`, and `MiniMaxAI`

---

## Project Structure

```
src/
  main/
    java/
      project/
        Board.java
        Cell.java
        Colors.java
        FileHandler.java
        FileManager.java
        Game.java
        GameApp.java        // entry point
        GameController.java // JavaFX controller
    resources/
      project/
        Game.fxml
        EasyHighScores      // created/updated by the app
        MediumHighScores    // created/updated by the app
        HardHighScores      // created/updated by the app
```

> The controller loads the FXML with `FXMLLoader.load(getClass().getResource("Game.fxml"))`, so the file should be on the classpath at `project/Game.fxml`.

---

## How to Run

You can run this project with **Maven** or **manually** with a JavaFX SDK.

### Option A: Maven

1. Add JavaFX dependencies and the JavaFX Maven plugin to your `pom.xml`:
   ```xml
   <dependencies>
     <dependency>
       <groupId>org.openjfx</groupId>
       <artifactId>javafx-controls</artifactId>
       <version>17.0.8</version>
     </dependency>
     <dependency>
       <groupId>org.openjfx</groupId>
       <artifactId>javafx-fxml</artifactId>
       <version>17.0.8</version>
     </dependency>
   </dependencies>

   <build>
     <plugins>
       <plugin>
         <groupId>org.openjfx</groupId>
         <artifactId>javafx-maven-plugin</artifactId>
         <version>0.0.8</version>
         <configuration>
           <mainClass>project.GameApp</mainClass>
         </configuration>
       </plugin>
     </plugins>
   </build>
   ```

2. Run:
   ```bash
   mvn clean javafx:run
   ```


### Option B: Manual (JavaFX SDK)

1. Download a JavaFX SDK (matching your JDK).  
2. Set `PATH_TO_FX` to the SDK's `lib` folder.
   - macOS/Linux:
     ```bash
     export PATH_TO_FX=/path/to/javafx-sdk/lib
     ```
   - Windows PowerShell:
     ```powershell
     $env:PATH_TO_FX = "C:\path	o\javafx-sdk\lib"
     ```
3. Compile and run:
   - macOS/Linux:
     ```bash
     javac --module-path $PATH_TO_FX --add-modules javafx.controls,javafx.fxml            -d out $(find src/main/java -name "*.java")

     java --module-path $PATH_TO_FX --add-modules javafx.controls,javafx.fxml           -cp out:src/main/resources project.GameApp
     ```
   - Windows PowerShell:
     ```powershell
     javac --module-path $env:PATH_TO_FX --add-modules javafx.controls,javafx.fxml `
           -d out (Get-ChildItem -Recurse src/main/java/*.java | % FullName)

     java --module-path $env:PATH_TO_FX --add-modules javafx.controls,javafx.fxml `
          -cp "out;src/main/resources" project.GameApp
     ```

> Ensure `src/main/resources/project/Game.fxml` exists so the loader can find it.

---


## Implementation Notes

- **Board model:** `Board` tracks a `Cell` grid, validates moves, and detects four-in-a-row in all directions.  
- **AI:** `MiniMaxAI` returns the best column index using Minimax with alpha–beta pruning.  
  - Depth is tuned per difficulty (e.g., Easy uses a shallower depth than Medium/Hard).  
- **High scores:** `FileHandler` reads/writes simple newline-separated times under `src/main/resources/` with file names:
  - `EasyHighScores`, `MediumHighScores`, `HardHighScores`  
- In **1P mode**, Easy/Medium let you move first; **Hard** lets the AI move first.

