Wallet Application
This Java application, named Wallet, allows users to manage their finances by tracking their income and expenses. It provides a simple command-line interface for
performing operations like checking the current balance, sending money, and receiving money. The application also requires authentication to ensure the user's financial data remains secure.


Table of Contents
Features
Usage
File Structure
How to Run


Features
1.Authentication: The application requires the user to enter a correct password before gaining access. The password is set in the PasswordManager class and can be 
customized to enhance security.
2.Financial Tracking: The user can perform the following financial operations:
-Check Balance: Displays the current balance.
-Send Money: Allows the user to send a specified amount of money. The transaction is recorded and subtracted from the balance.
-Receive Money: Allows the user to receive a specified amount of money. The transaction is recorded and added to the balance.
3.Data Storage: Financial data is stored in a CSV file named wallet.csv. The application reads this file to initialize the user's financial history and updates it
after each transaction.


File Structure
The application consists of several Java classes organized into a package. Here's an overview of the main classes and their responsibilities:
-Balance: Manages the user's financial data, including balance, received and spent amounts, and transaction dates. It also provides methods for reading and updating the CSV file containing financial data.
-PasswordManager: Handles user authentication by comparing the entered password with a predefined correct password.
-Start: The main entry point for the application. It manages user interaction, authenticates the user, and allows them to perform financial operations.
-Wallet: Contains the main method and creates an instance of the Start class to start the application.
