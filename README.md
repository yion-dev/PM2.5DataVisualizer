# PM2.5 CLI Visualizer

## Overview

This is a simple Java CLI application that fetches, displays, and stores PM2.5 air quality data.
The project is designed for educational purposes and demonstrates core Object-Oriented Programming (OOP) concepts.

---

## Features

* Fetch PM2.5 data from an external API
* Display data in the terminal
* Simple text-based visualization (e.g., bar chart)
* Save data to CSV file
* Load data from CSV file

---

## Technologies

* Java (pure Java, no frameworks)
* CLI (Command Line Interface)
* CSV file handling

---

## Project Structure

```
src/com/pm25app/
 ├── Main.java
 ├── display/           # CLI Display Logics
 ├── services/          # Functions
 ├── exceptions/        # Errors
 └── data/              # Input / Output Operations
```

---

## OOP Concepts Used

* **Encapsulation** → Data stored in model classes (e.g., PM25Record)
* **Inheritance** → Display implementations share common behavior
* **Polymorphism** → Different display types used through a common interface
* **Interfaces** → ApiClient, Storage, Display
* **Generics** → Storage<T> for flexible data handling

---

## How to Run

1. Compile the project:

```
javac com/pm25app/Main.java
```

2. Run the program:

```
java com.pm25app.Main
```

---

## Example Commands

* `fetch` → Get data from API
* `view` → Display current data
* `save` → Save data to CSV
* `load` → Load data from CSV
* `exit` → Exit program

---

## Data Storage

* Data is stored in:

```
/data/data.csv
```

---
