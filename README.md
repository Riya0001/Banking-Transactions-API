# Banking Transaction API
# Simple Banking

## How to build and run the application

Clone the Repository

```bash
git clone https://github.com/Riya0001/Banking-Transactions-API.git
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
    "email": "ron@gmail.com",
    "dateOfBirth": "2002-01-01",
    "phoneNumber": "4369806567",
    "initialBalance": 100.00,
    "accountType": "SAVINGS",
    "accountHolderName": "ron"
    
- /transactions/transfer_funds 
  - Transfer funds from one account to another
  - Input (POST): all mandatory
    "senderEmail":"ron6@gmail.com",
    "receiverEmail":"ron7@gmail.com",
    "amount":16

- /transactions/history/{accountID}
  - View transactions history
  - Input (POST):
      - (Mandatory) accountId

**Assumptions for APIs**
- The age of the user should be more than 18 to create an account.
- initial balance should be greater than 1
- two users cant have same email id
- Transfer funds can't be done from with same source and destination
- Account balance must be greater than amount transfering
- money transfer should be > 1.
