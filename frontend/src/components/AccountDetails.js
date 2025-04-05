import { useLocation } from "react-router-dom";
import { Card, CardContent, Typography, Container, Grid } from "@mui/material";

const AccountDetails = () => {
    const location = useLocation();
    const account = location.state?.account;  // Get account details from state

    if (!account) {
        return <Typography variant="h6" align="center" color="error">No account data found!</Typography>;
    }

    return (
        <Container maxWidth="sm">
            <Card sx={{ mt: 4, p: 2, boxShadow: 3, borderRadius: 3 }}>
                <CardContent>
                    <Typography variant="h5" align="center" gutterBottom>
                        Account Details
                    </Typography>
                    <Grid container spacing={2}>
                        <Grid item xs={6}><Typography variant="subtitle1"><strong>Account ID:</strong></Typography></Grid>
                        <Grid item xs={6}><Typography variant="body1">{account.id}</Typography></Grid>

                        <Grid item xs={6}><Typography variant="subtitle1"><strong>Account Holder Name:</strong></Typography></Grid>
                        <Grid item xs={6}><Typography variant="body1">{account.accountHolderName}</Typography></Grid>

                        <Grid item xs={6}><Typography variant="subtitle1"><strong>Date of Birth:</strong></Typography></Grid>
                        <Grid item xs={6}><Typography variant="body1">{account.dateOfBirth}</Typography></Grid>

                        <Grid item xs={6}><Typography variant="subtitle1"><strong>Email:</strong></Typography></Grid>
                        <Grid item xs={6}><Typography variant="body1">{account.email}</Typography></Grid>

                        <Grid item xs={6}><Typography variant="subtitle1"><strong>Phone Number:</strong></Typography></Grid>
                        <Grid item xs={6}><Typography variant="body1">{account.phoneNumber}</Typography></Grid>

                        <Grid item xs={6}><Typography variant="subtitle1"><strong>Account Type:</strong></Typography></Grid>
                        <Grid item xs={6}><Typography variant="body1">{account.accountType}</Typography></Grid>

                        <Grid item xs={6}><Typography variant="subtitle1"><strong>Initial Balance:</strong></Typography></Grid>
                        <Grid item xs={6}><Typography variant="body1">{account.initialBalance}</Typography></Grid>
                    </Grid>
                </CardContent>
            </Card>
        </Container>
    );
};

export default AccountDetails;
