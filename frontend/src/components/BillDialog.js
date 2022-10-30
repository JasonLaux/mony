import * as React from 'react';
import Button from '@mui/material/Button';
import TextField from '@mui/material/TextField';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';
import {useState, useEffect} from 'react';
import {apis} from '../utils/apis';

export default function BillDialog(props) {
 
    const [open, setOpen] = useState(false);
    const [amount, setAmount] = useState(0);
    const [accountNumber, setAccountNumber] = useState(0);
    const [payeeName, setPayeeName] = useState('Musk');

    useEffect(()=>{
        setOpen(props.openNow)
    }, [props.openNow])


    const handleClose = () => {
        setOpen(false);
        props.setWindowStatus();
    };

    const handleSubmit = () => {

        if(props.createOrUpdate) {
            console.log("creating a new bill...")
                apis.createBill(props.ledgerId, amount, accountNumber, payeeName).then(
                    res => {
                        console.log(res.data)
                        setOpen(false);
                        props.setWindowStatus(false);
                    }
                ).catch(
                    err => console.log(err.response)
                    
                )
        }
        else {
            console.log("updating a bill...")
            apis.updateBill(props.ledgerId, props.billId, amount, accountNumber, payeeName).then(
                res => {
                    console.log(res.data);
                    setOpen(false);
                    props.setWindowStatus(false);
                }
            ).catch (
                err => console.log(err)
            )
        }

    }
    

    return (
    <div>
        <Dialog open={open} onClose={handleClose}>
            {props.createOrUpdate ? <DialogTitle>Add a bill</DialogTitle> : <DialogTitle>Update a bill</DialogTitle>}
        <DialogContent>
            <TextField
            autoFocus
            margin="dense"
            id="amount"
            label="Amount"
            type="text"
            fullWidth
            variant="standard"
            onChange={e => setAmount(e.target.value)}
            />
            <TextField
            autoFocus
            margin="dense"
            id="accountNumber"
            label="Account Number"
            type="text"
            fullWidth
            variant="standard"
            onChange={e => setAccountNumber(e.target.value)}
            />
            <TextField
            autoFocus
            margin="dense"
            id="payeeName"
            label="Payee Name"
            type="text"
            fullWidth
            variant="standard"
            onChange={e => setPayeeName(e.target.value)}
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