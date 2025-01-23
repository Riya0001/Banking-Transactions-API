package com.dilpreet.bankingapi.constants;

public class TransactionApi {
    public static final String BASE_URL = "/api/v1";
    public static final String TRANSACTION_BASE_URL =  BASE_URL + "/transactions";
    public static final String TRANSFER_MONEY_URL = TRANSACTION_BASE_URL + "/transfer";
    public static final String TRANSACTION_HISTORY_URL = TRANSACTION_BASE_URL + "/history/{accountId}";
}
