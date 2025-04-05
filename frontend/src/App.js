import { BrowserRouter as Router, Route, Routes, Link } from "react-router-dom";
import { Button, Box, Container } from "@mui/material";
import CreateAccount from "./components/CreateAccount";
import Transaction from "./components/Transaction";
import TransactionHistory from "./components/TransactionHistory";
import TransactionSuccess from "./components/TransactionSuccess";
import AccountDetails from "./components/AccountDetails"; // ✅ Import Account Details Page

function App() {
    return (
        <Router>
            <Box sx={{ display: "flex", justifyContent: "center", gap: 2, p: 2, bgcolor: "primary.main" }}>
                <Button variant="contained" component={Link} to="/create-account" sx={{ backgroundColor: "white", color: "black", "&:hover": { backgroundColor: "#f0f0f0" } }}>
                    🏦 Create Account
                </Button>
                <Button variant="contained" component={Link} to="/transaction" sx={{ backgroundColor: "white", color: "black", "&:hover": { backgroundColor: "#f0f0f0" } }}>
                    💰 Make a Transaction
                </Button>
                <Button variant="contained" component={Link} to="/transaction-history" sx={{ backgroundColor: "white", color: "black", "&:hover": { backgroundColor: "#f0f0f0" } }}>
                    📜 Transaction History
                </Button>
            </Box>

            <Container>
                <Routes>
                    <Route path="/create-account" element={<CreateAccount />} />
                    <Route path="/transaction" element={<Transaction />} />
                    <Route path="/transaction-history" element={<TransactionHistory />} />
                    <Route path="/transaction-success" element={<TransactionSuccess />} />
                    <Route path="/account-details" element={<AccountDetails />} /> {/* ✅ Add this line */}
                </Routes>
            </Container>
        </Router>
    );
}

export default App;
