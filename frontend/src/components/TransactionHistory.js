import React, { useState } from "react";
import axios from "axios";
import {
    Container, Typography, Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper,
    CircularProgress, Button, Box, Alert, TextField
} from "@mui/material";

const TransactionHistory = () => {
    const [accountId, setAccountId] = useState(""); // User input for Account ID
    const [transactions, setTransactions] = useState([]); // Transactions for entered account
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState("");
    const [fetched, setFetched] = useState(false); // Tracks if "Fetch Transactions" was clicked

    // Fetch transaction history for a given account ID with priority error handling
    const fetchTransactions = () => {
        if (!accountId || isNaN(accountId)) {
            setError("Please enter a valid Account ID.");
            return;
        }

        setLoading(true);
        setFetched(true); // Mark that the fetch was triggered
        setTransactions([]);  // Clear previous transactions
        setError("");  // Clear previous error

        axios.get(`http://localhost:9902/api/transactions/history/${accountId}`)
            .then((response) => {
                if (response.data.length === 0) {
                    setError("Account does not exist."); // First priority check
                    setTransactions([]);
                } else {
                    setTransactions(response.data);
                }
                setLoading(false);
            })
            .catch((error) => {
                setLoading(false);

                if (error.response) {
                    const errorMessage = error.response.data.message;

                    // ✅ Priority 1: Account does not exist
                    if (errorMessage.includes("Account does not exist")) {
                        setError("Account does not exist.");
                    }
                    // ✅ Priority 2: Handle general API errors
                    else {
                        setError("Failed to load transaction history. Please check the Account ID.");
                    }
                } else {
                    setError("Network error. Please try again.");
                }
            });
    };

    return (
        <Container maxWidth="md">
            <Paper elevation={5} sx={{ padding: 4, textAlign: "center", marginTop: 5 }}>
                <Typography variant="h4" fontWeight="bold" gutterBottom>
                    📜 Transaction History
                </Typography>

                {/* Display error messages */}
                {error && <Alert severity="error" sx={{ mb: 2 }}>{error}</Alert>}

                {/* Account ID Input Field */}
                <Box sx={{ display: "flex", justifyContent: "center", gap: 2, mb: 3 }}>
                    <TextField
                        label="Enter Account ID"
                        type="number"
                        variant="outlined"
                        value={accountId}
                        onChange={(e) => setAccountId(e.target.value)}
                        required
                    />
                    <Button variant="contained" color="primary" onClick={fetchTransactions}>
                        Fetch Transactions
                    </Button>
                </Box>

                {/* Transaction History Table */}
                <Typography variant="h6" sx={{ mb: 2 }}>
                    {fetched ? `Transactions for Account ID: ${accountId}` : "Enter an Account ID and press Fetch"}
                </Typography>

                {loading ? (
                    <CircularProgress />
                ) : transactions.length > 0 ? (
                    <TableContainer component={Paper}>
                        <Table>
                            <TableHead>
                                <TableRow sx={{ backgroundColor: "#1976d2" }}>
                                    <TableCell sx={{ color: "white" }}>Transaction ID</TableCell>
                                    <TableCell sx={{ color: "white" }}>Sender</TableCell>
                                    <TableCell sx={{ color: "white" }}>Receiver</TableCell>
                                    <TableCell sx={{ color: "white" }}>Amount</TableCell>
                                    <TableCell sx={{ color: "white" }}>Updated Balance</TableCell>
                                    <TableCell sx={{ color: "white" }}>Transaction Type</TableCell>
                                    <TableCell sx={{ color: "white" }}>Transaction Time</TableCell>
                                    <TableCell sx={{ color: "white" }}>Status</TableCell>
                                    <TableCell sx={{ color: "white" }}>Remarks</TableCell>
                                </TableRow>
                            </TableHead>
                            <TableBody>
                                {transactions.map((tx, index) => (
                                    <TableRow key={index} hover>
                                        <TableCell>{tx.transactionId}</TableCell>
                                        <TableCell>{tx.senderEmail}</TableCell>
                                        <TableCell>{tx.receiverEmail}</TableCell>
                                        <TableCell>${tx.amount}</TableCell>
                                        <TableCell>${tx.updatedBalance}</TableCell>
                                        <TableCell>{tx.transactionType}</TableCell>
                                        <TableCell>{new Date(tx.transactionTime).toLocaleString()}</TableCell>
                                        <TableCell>{tx.status}</TableCell>
                                        <TableCell>{tx.remarks}</TableCell>
                                    </TableRow>
                                ))}
                            </TableBody>
                        </Table>
                    </TableContainer>
                ) : fetched ? (
                    <Typography color="textSecondary">No transactions found for this account.</Typography>
                ) : null}
            </Paper>
        </Container>
    );
};

export default TransactionHistory;
