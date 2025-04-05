import React, { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import {
    Container, TextField, Button, Typography, Paper, MenuItem, CircularProgress, Box, Alert
} from "@mui/material";

const CreateAccount = () => {
    const [formData, setFormData] = useState({
        accountHolderName: "",
        dateOfBirth: "",
        email: "",
        phoneNumber: "",
        accountType: "SAVINGS",
        initialBalance: "",
    });

    const [errors, setErrors] = useState([]);  // ✅ Holds multiple validation errors
    const [loading, setLoading] = useState(false);
    const navigate = useNavigate();  // ✅ Navigation hook

    // Handle input changes
    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData({ ...formData, [name]: value });
    };

    // Handle form submission
    const handleSubmit = async (e) => {
        e.preventDefault();
        setErrors([]);  // Clear previous errors
        setLoading(true);

        const headers = { "Content-Type": "application/json" };

        try {
            const response = await axios.post("http://localhost:9902/api/account/create", formData, { headers });

            console.log("✅ API Response:", response.data);

            // ✅ Collect validation errors if present
            let validationErrors = [];
            if (response.data.emailError) validationErrors.push(`📧 ${response.data.emailError}`);
            if (response.data.adult) validationErrors.push(`🔞 ${response.data.adult}`);
            if (response.data.phoneNumberError) validationErrors.push(`📞 ${response.data.phoneNumberError}`);
            if (response.data.initialBalanceError) validationErrors.push(`💰 ${response.data.initialBalanceError}`);

            if (validationErrors.length > 0) {
                setErrors(validationErrors);  // ✅ Show errors on the Create Account page
                console.warn(" Validation Errors Detected:", validationErrors);
                return;  // ✅ Prevents navigation if there are errors
            }

            // ✅ Only navigate if validation is successful
            console.log("✅ No validation errors. Navigating to Account Details...");
            navigate("/account-details", { state: { account: response.data } });

        } catch (error) {
            console.error("🚨 Error:", error.response?.data);

            let errorMessages = [];

            // ✅ Extract errors correctly from the backend response
            const errorData = error.response?.data;
            if (errorData) {
                Object.keys(errorData).forEach((key) => {
                    errorMessages.push(` ${errorData[key]}`);
                });
            }

            if (errorMessages.length === 0) errorMessages.push(" Error creating account. Please try again.");
            setErrors(errorMessages);  // ✅ Display backend errors
        } finally {
            setLoading(false);
        }
    };



    return (
        <Container maxWidth="sm">
            <Paper elevation={5} sx={{ padding: 4, textAlign: "center", marginTop: 5 }}>
                <Typography variant="h4" fontWeight="bold" gutterBottom>
                    🚀 Open a New Account
                </Typography>

                {/* ✅ Display all validation errors */}
                {errors.length > 0 && (
                    <Box sx={{ mb: 2 }}>
                        {errors.map((error, index) => (
                            <Alert key={index} severity="error" sx={{ mb: 1 }}>
                                 {error}
                            </Alert>
                        ))}
                    </Box>
                )}

                <form onSubmit={handleSubmit}>
                    <TextField
                        fullWidth
                        label="Full Name"
                        name="accountHolderName"
                        value={formData.accountHolderName}
                        onChange={handleChange}
                        required
                        margin="normal"
                    />

                    <TextField
                        fullWidth
                        label="Date of Birth"
                        name="dateOfBirth"
                        type="date"
                        value={formData.dateOfBirth}
                        onChange={handleChange}
                        required
                        margin="normal"
                        InputLabelProps={{ shrink: true }}
                    />

                    <TextField
                        fullWidth
                        label="Email Address"
                        name="email"
                        type="email"
                        value={formData.email}
                        onChange={handleChange}
                        required
                        margin="normal"
                    />

                    <TextField
                        fullWidth
                        label="Phone Number"
                        name="phoneNumber"
                        type="tel"
                        value={formData.phoneNumber}
                        onChange={handleChange}
                        required
                        margin="normal"
                        inputProps={{ pattern: "[0-9]{10}" }}
                        helperText="Enter a 10-digit phone number"
                    />

                    <TextField
                        fullWidth
                        select
                        label="Account Type"
                        name="accountType"
                        value={formData.accountType}
                        onChange={handleChange}
                        margin="normal"
                    >
                        <MenuItem value="SAVINGS">Savings</MenuItem>
                        <MenuItem value="CURRENT">Current</MenuItem>
                    </TextField>

                    <TextField
                        fullWidth
                        label="Initial Deposit"
                        name="initialBalance"
                        type="number"
                        value={formData.initialBalance}
                        onChange={handleChange}
                        required
                        margin="normal"
                        inputProps={{ min: 0 }}
                        helperText="Minimum balance must be greater than 0"
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
                            {loading ? <CircularProgress size={24} /> : "✨ Create Account"}
                        </Button>
                    </Box>
                </form>
            </Paper>
        </Container>
    );
};

export default CreateAccount;
