import * as React from 'react';
import { useNavigate} from "react-router-dom";
import { AppBar, Toolbar, Grid, Button} from '@mui/material';

export default function Navigator(props) {
    let navigate = useNavigate();
    return (
        <AppBar>
            <Toolbar>
                <Grid container spacing={0} alignItems="center" justifyContent="space-evenly">
                    <Grid item>
                        <Button onClick={() => navigate('/portal')} variant="text" sx={{color: '#FFFFFF'}}> Account </Button>
                    </Grid>
                    <Grid item>
                        <Button onClick={() => navigate('/portal/ledgers')} variant="text" sx={{color: '#FFFFFF'}}> Ledger </Button>
                    </Grid>
                    <Grid item>
                        <Button onClick={() => navigate('/portal/settings')} variant="text" sx={{color: '#FFFFFF'}}> Setting </Button>
                    </Grid>
                </Grid>
            </Toolbar>
        </AppBar>
    )
}