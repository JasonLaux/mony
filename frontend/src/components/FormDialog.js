import * as React from 'react';
import Button from '@mui/material/Button';
import TextField from '@mui/material/TextField';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';
import {useState, useEffect} from 'react';
import {apis} from '../utils/apis';
import {getUserId} from '../utils/cookies';

export default function FormDialog(props) {
 
    const [open, setOpen] = React.useState(false);
    const [accountNumber, setAccountNumber] = useState('');
    const [bankName, setBankName] = useState('');
    const [startBalance , setStartBalance] = useState('');

    useEffect(()=>{
        setOpen(props.openNow)
    }, [props.openNow])


    const handleClose = () => {
        setOpen(false);
        props.setWindowStatus(false);
    };

    const handleSubmit = () => {

        if(props.createOrUpdate) {
            console.log("create bank account...")
            apis.addBankAccount(getUserId(), accountNumber, bankName, startBalance).then(res => {
                console.log(res.data)
                setOpen(false);
                props.setWindowStatus(false);
            }).catch(error => {
                console.log(error.response.message)
            })
        }
        else {
            console.log("update info...")
            apis.updateBankAccount(getUserId(), props.accountId, accountNumber, bankName, startBalance).then(res => {
                console.log(res.data)
                setOpen(false);
                props.setWindowStatus(false);
            }).catch(error => {
                console.log(error.response)
            })
        }
    }

    return (
    <div>
        <Dialog open={open} onClose={handleClose}>
            {/* <DialogTitle>Create</DialogTitle> */}
            {props.createOrUpdate ? <DialogTitle>Create</DialogTitle> : <DialogTitle>Update</DialogTitle>}
        <DialogContent>
            {
                props.createOrUpdate
                ?
                <DialogContentText>
                Please create your bank account.
                </DialogContentText>
                :
                <DialogContentText>
                Please update your bank account information.
                </DialogContentText>
            }
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
            id="bankName"
            label="Bank Name"
            type="text"
            fullWidth
            variant="standard"
            onChange={e => setBankName(e.target.value)}
            />
            <TextField
            autoFocus
            margin="dense"
            id="startBalance"
            label="Start Balance"
            type="text"
            fullWidth
            variant="standard"
            onChange={e => setStartBalance(e.target.value)}
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