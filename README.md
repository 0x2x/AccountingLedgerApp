# Ledger Application




## What each folder does
### Services
    While determining on how I should organize the project, I realized to make the project much easier to read/add on to would be by creating a services folder.
    Every file inside of the services folder runs holds all the main functionality in the application such as sorting, writing, reading and any basic utility item.
### Screens
    While looking at the project description, I saw the word "screens", I thought the word "screens" as a menu, So I made it in to the pathways which users may enter
    a specific Screen by typing one of the following commands on the home menu.
#### Screens Design
    This folder carries a set of designs used in the screens.
### Models
    Models is viewed as a blueprint, which explains what a model is and what can the model do. The models such as Debit and Transactions helps describe whats going on.
    Debit describes what a Debit Card is such as Card holder name, Card holder Address, Card Number, Card CVV, Card Expiration and how much money the card has.
    Transaction describes who you paid so far and who you owe.
    ``src/main/java/org.nigel/models/debit.java``
    ``src/main/java/org.nigel/models/transaction.java``
