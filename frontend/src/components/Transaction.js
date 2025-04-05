import React, { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import {
    Container, TextField, Button, Typography, Paper, CircularProgress, Box, Alert
} from "@mui/material";

const Transaction = () => {
    const [formData, setFormData] = useState({
        senderEmail: "",
        receiverEmail: "",
        amount: "",
    });
    const [message, setMessage] = useState("");
    const [loading, setLoading] = useState(false);
    const navigate = useNavigate(); // ✅ For navigation after success

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData({ ...formData, [name]: value });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setMessage("");

        // ✅ 1. Validation: Sender & Receiver should not be the same
        if (formData.senderEmail === formData.receiverEmail) {
            setMessage("❌ Sender and receiver email cannot be the same.");
            return;
        }

        setLoading(true);

        try {
            const response = await axios.post("http://localhost:9902/api/transactions/transfer", formData, {
                headers: { "Content-Type": "application/json" },
                withCredentials: true,
            });

            console.log("✅ Transaction Response:", response.data);

            // ✅ If transaction is successful, navigate to success page
            if (response.status === 200) {
                navigate("/transaction-success", { state: { transaction: response.data } });
            }

        } catch (error) {
            console.error("🚨 Transaction Error:", error.response?.data);

            if (error.response) {
                const statusCode = error.response.status;
                const errorMessage = error.response.data.message;

                // ✅ 2. Priority 1: Check if sender account does not exist (404)
                if (statusCode === 404 && errorMessage.includes("Sender account does not exist")) {
                    setMessage("❌ Sender account does not exist. Please check the email address.");
                }
                // ✅ 3. Priority 2: Check if receiver account does not exist (404)
                else if (statusCode === 404 && errorMessage.includes("Receiver account does not exist")) {
                    setMessage("❌ Receiver account does not exist. Please check the email address.");
                }
                // ✅ 4. Priority 3: Check if sender and receiver are the same (400)
                else if (statusCode === 400 && errorMessage.includes("Sender and receiver email cannot be the same")) {
                    setMessage("❌ Sender and Receiver email cannot be the same.");
                }
                // ✅ 5. Priority 4: Check if balance is insufficient (400)
                else if (statusCode === 400 && errorMessage.includes("Insufficient funds")) {
                    setMessage("❌ Insufficient funds. Please check your account balance.");
                }
                // ✅ 6. Handle any other errors from the API
                else {
                    setMessage(errorMessage || "❌ Transaction Failed. Please try again.");
                }
            } else {
                setMessage("❌ Network error. Please try again.");
            }
        } finally {
            setLoading(false);
        }
    };


    return (
        <Container maxWidth="sm">
            <Paper elevation={5} sx={{ padding: 4, textAlign: "center", marginTop: 5 }}>
                <Typography variant="h4" fontWeight="bold" gutterBottom>
                    💸 Make a Transaction
                </Typography>

                {/* ✅ Show error messages */}
                {message && <Alert severity="error" sx={{ mb: 2 }}>{message}</Alert>}

                <form onSubmit={handleSubmit}>
                    <TextField
                        fullWidth
                        label="Sender's Email"
                        name="senderEmail"
                        type="email"
                        value={formData.senderEmail}
                        onChange={handleChange}
                        required
                        margin="normal"
                    />

                    <TextField
                        fullWidth
                        label="Receiver's Email"
                        name="receiverEmail"
                        type="email"
                        value={formData.receiverEmail}
                        onChange={handleChange}
                        required
                        margin="normal"
                    />

                    <TextField
                        fullWidth
                        label="Enter Amount"
                        name="amount"
                        type="number"
                        value={formData.amount}
                        onChange={handleChange}
                        required
                        margin="normal"
                        inputProps={{ min: 1 }}
                    />

                    <Box mt={3}>
                        <Button
                            type="submit"
                            fullWidth
                            variant="contained"
                            color="primary"
                            size="large"
                            disabled={loading}
                        >
                            {loading ? <CircularProgress size={24} /> : "💰 Send Money"}
                        </Button>
                    </Box>
                </form>
            </Paper>
        </Container>
    );
};

export default Transaction;
