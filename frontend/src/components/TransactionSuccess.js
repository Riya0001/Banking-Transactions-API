import React from "react";
import { useLocation } from "react-router-dom";
import { Container, Typography, Paper } from "@mui/material";

const TransactionSuccess = () => {
    const location = useLocation();
    const transaction = location.state?.transaction;

    console.log("Transaction Data:", transaction); // Debugging log

    if (!transaction) {
        return <Typography variant="h6" color="error">Transaction details not found.</Typography>;
    }

    return (
        <Container maxWidth="sm">
            <Paper elevation={5} sx={{ padding: 4, textAlign: "center", marginTop: 5 }}>
                <Typography variant="h4" fontWeight="bold" gutterBottom color="green">
                    ✅ Transaction Successful!
                </Typography>

                <Typography variant="h6" fontWeight="bold">Transaction ID: {transaction.transactionId}</Typography>
                <Typography variant="body1"><strong>Sender:</strong> {transaction.senderEmail}</Typography>
                <Typography variant="body1"><strong>Receiver:</strong> {transaction.receiverEmail}</Typography>
                <Typography variant="h6" fontWeight="bold" color="primary">Amount: ${transaction.amount}</Typography>

                {/* ✅ Display Updated Balance Directly */}
                <Paper elevation={3} sx={{ padding: 2, backgroundColor: "#f0f0f0", marginTop: 2 }}>
                    <Typography variant="h6" fontWeight="bold" color="secondary">
                        💰 Updated Balance: ${transaction.updatedBalance}
                    </Typography>
                </Paper>
            </Paper>
        </Container>
    );
};

export default TransactionSuccess;
