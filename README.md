# BankingApi
# Simple Banking

## How to build and run the application

Clone the Repository

```bash
git clone https://github.com/dilpreet701/BankingApi
```
 

Build the project

```bash
mvn clean install
```

Run the BankingapiApplication.java file. The application will run on http://localhost:8080

**API List**

- /accounts/create
  - Create a new account
  - Input (POST): all mandatory
    "email": "dilpreetgill374@gmail.com",
    "accountHolderName": "DILPREET",
    "balance":180, 
    "currencyCode": "INR"
    
- /transactions/transfer_funds 
  - Transfer funds from one account to another
  - Input (POST): all mandatory
    - 
    "accountId": 2,
    "counterAccountId": 1,
    "amount": 25

- /transactions/history
  - View transactions history
  - Input (POST):
      - (Mandatory) accountId

**Assumptions for APIs**
- A person can have multiple accounts with the same email id
- account currency field can be one of these only (INR|USD|CAD|CNY|EUR|GBP|AUD|JPY|CHF|NZD)
- Transfer funds can't be done from with same source and destination
- Account balance must be greater than amount transfering
- money transfer should be > 1.
