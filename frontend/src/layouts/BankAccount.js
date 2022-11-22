import { Grid } from '@mui/material';
import * as React from 'react';
import BasicTable from '../components/BasicTable';
import Button from '@mui/material/Button';
import Stack from '@mui/material/Stack';
import { getUserId } from '../utils/cookies';
import {useState, useEffect} from 'react';
import { apis } from '../utils/apis';
import FormDialog from '../components/FormDialog';

export default function BankAccount(){

    // id, accountNumber, bankName, balance

    const [rows, setRows] = useState([]);
    const [createAccountOpen, setCreateAccountOpen] = useState(false);
    const [updateAccountOpen, setUpdateAccountOpen] = useState(false);

    // Account ID
    const [id, setId] = useState(1);

    useEffect(() => {
        fetchList()
    }, [])

    const tableHeader = ["Select", "Account Number", "Bank Name", "Balance"]

    const closeWindow = (status) => {
        setCreateAccountOpen(status)
        setUpdateAccountOpen(status)
    }

    const fetchList = () => {
        apis.getAllBankAccounts(getUserId()).then(
            res => {
                const rows = res.data;
                setRows(
                    rows.map(row => Object.keys(row).map(key => row[key]))
                )
            }
        ).catch(err => {
            console.log(getUserId())
            console.log(err)
        })
    }

    const handleSubmit = () => {
        fetchList()
    }

    const handleClick = (id) => {
        setId(id)
        console.log(id)
    }

    const handleDelete = () => {
        apis.deleteBankAccount(getUserId(), id).then(
            res => {
                fetchList()
                console.log(res.data)
            }
        ).catch(
            err => {
                console.log(err)
            }
        )
    }


    return (
        <React.Fragment>
            <Grid container>
                <Grid item xs={12} sx={{mt:8}}>
                    <BasicTable tableHeader={tableHeader} rows={rows} handleClick={handleClick}/>
                </Grid>    
                <Grid item sx={{mt:3}}>
                    <Stack spacing={2} direction="row" justifyContent="center" alignItems="center">
                        <Button variant="contained" onClick={() => setCreateAccountOpen(true)}>Add Bank Account</Button>
                        <Button variant="contained" onClick={() => setUpdateAccountOpen(true)}>Update Bank Account</Button>
                        <Button variant="contained" onClick={handleDelete}>Delete Bank Account</Button>
                    </Stack>
                </Grid>
                <Grid item>
                    <FormDialog accountId={id} openNow={createAccountOpen || updateAccountOpen} createOrUpdate={createAccountOpen} setWindowStatus={closeWindow} handleRefresh={handleSubmit}/>
                </Grid>
            </Grid>
        </React.Fragment>
    )
}