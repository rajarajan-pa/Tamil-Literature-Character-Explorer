# Tamil Literature Character Explorer

## Requirements
- Java JDK 19 or newer
- Visual Studio Code (optional)

## Run
1. Keep `Main.java`, `index.html`, and `characters.json` in the same folder.
2. Open a terminal in this folder.
3. Compile:
   `javac --add-modules jdk.httpserver Main.java`
4. Start:
   `java --add-modules jdk.httpserver Main`
5. Open `http://localhost:8080` in your browser.
6. Stop the server with Ctrl+C in the terminal.

## Features
- Search by name, category, author, or description
- Filter by literature category
- Character details
- Java HTTP server backend and JSON API at `/api/characters`

## Content note
This starter dataset includes every name supplied in the project brief, including names repeated under multiple literary categories. The descriptions are introductory placeholders and should be fact-checked and expanded before final academic submission.
