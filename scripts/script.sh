#!/bin/bash
cd "src"
javac --module-path "C:\Java\javafx-sdk-22.0.1\lib" --add-modules javafx.controls,javafx.fxml HomePage.java Sudoku.java SudokuApp.java SudokuController.java SudokuViewer.java 
java --module-path "C:\Java\javafx-sdk-22.0.1\lib" --add-modules javafx.controls,javafx.fxml SudokuApp
javadoc --module-path "C:\Java\javafx-sdk-22.0.1\lib" --add-modules javafx.controls,javafx.fxml -d "../docs" HomePage.java Sudoku.java SudokuApp.java SudokuController.java SudokuViewer.java 
