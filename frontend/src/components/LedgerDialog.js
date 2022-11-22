import * as React from 'react';
import Button from '@mui/material/Button';
import TextField from '@mui/material/TextField';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';
import {useState, useEffect} from 'react';
import {apis} from '../utils/apis';
import {getUserId} from '../utils/cookies';

export default function LedgerDialog(props) {
 
    const [open, setOpen] = React.useState(false);
    const [name, setName] = useState('');


    useEffect(()=>{
        setOpen(props.openNow)
    }, [props.openNow])


    const handleClose = () => {
        setOpen(false);
        props.setWindowStatus();
    };

    const handleSubmit = () => {

        switch(props.windowName) {
            case 'Create Ledger':
                console.log("creating ledger book...")
                apis.createLedger(name).then(
                    res => {
                        console.log(res.data)
                        setOpen(false);
                        props.setWindowStatus(false);
                    }
                ).catch(
                    err => {
                        console.log(err.response)
                    }
                )
                break;
            case 'Add Ledger':
                console.log("adding the user to the ledger book...")
                apis.addUserToLedger(getUserId(), name).then(
                    res => {
                        console.log(res.data);
                        props.handleRefresh();
                        setOpen(false);
                        props.setWindowStatus(false);
                    }
                ).catch(
                    err => {
                        console.log(err.response);
                    }
                )
                break;
            case 'Update Ledger':
                console.log("updating ledger book with a new name...")
                apis.updateLedger(props.ledgerId, name).then(
                    res => {
                        console.log(res.data);
                        props.handleRefresh();
                        setOpen(false);
                        props.setWindowStatus(false);
                    }
                ).catch (
                    err => console.log(err)
                )
                break;
            default:
                console.log("Something wrong with the ledger window name");
        }
    }

    return (
    <div>
        <Dialog open={open} onClose={handleClose}>
            <DialogTitle>{props.windowName}</DialogTitle>
        <DialogContent>
            <TextField
            autoFocus
            margin="dense"
            id="ledgerName"
            label="Ledger Name"
            type="text"
            fullWidth
            variant="standard"
            onChange={e => setName(e.target.value)}
            />
        </DialogContent>
        <DialogActions>
            <Button onClick={handleClose}>Cancel</Button>
            <Button onClick={handleSubmit}>Submit</Button>
        </DialogActions>
        </Dialog>
    </div>
    );
}