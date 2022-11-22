import { Grid } from '@mui/material';
import * as React from 'react';
import BasicTable from '../components/BasicTable';
import Button from '@mui/material/Button';
import Stack from '@mui/material/Stack';
import { useState, useEffect } from 'react';
import { apis } from '../utils/apis';
import { getUserId } from '../utils/cookies';
import LedgerDialog from '../components/LedgerDialog';
import { useNavigate } from 'react-router-dom';

export default function Ledger(){

    // createLedger, addLedger, updateLedger, addBill
    const [ledgerId, setLedgerId] = useState(1);
    const [ledgers, setLedgers] = useState([]);
    const [ledgerName, setLedgerName] = useState('');
    const [createLedgerOpen, setCreateLedgerOpen] = useState(false);
    const [addLedgerOpen, setAddLedgerOpen] = useState(false);
    const [updateLedgerOpen, setUpdateLedgerOpen] = useState(false);
    const [windowName, setWindowName] = useState('createLedger');
    let navigate = useNavigate();

    useEffect(()=>{
        fetchList()
    }, [])

    const fetchList = () => {
        apis.getAllLedgers(getUserId()).then(
            res => {
                console.log(res.data)
                // convert the ledger object to the value list format
                setLedgers(res.data.map(ledger => Object.keys(ledger).map(key => ledger[key])))
                
            }
        )
    }

    const handleClick = (id) => {
        setLedgerId(id);
        console.log(id);
    }

    const handleSetLedgerName = (name) => {
        setLedgerName(name)
    }

    const handleDeleteClick = () => {
        apis.deleteLedger(ledgerId).then(
            
            res => {
                console.log(res);
                fetchList();
            }
        ).catch(
            err => console.log(err)
        )
    }

    const closeWindow = () => {
        setAddLedgerOpen(false)
        setCreateLedgerOpen(false)
        setUpdateLedgerOpen(false)
    }
    
    // get the selected ID

    const tableHeader = ["Selected", "Name"]


    return (
        <React.Fragment>
            <Grid container>
                <Grid item xs={12} sx={{mt:8}}>
                    <BasicTable tableHeader = {tableHeader} rows={ledgers} handleClick={handleClick}/>
                </Grid>    
                <Grid item sx={{mt:3}}>
                    <Stack spacing={2} direction="row" justifyContent="center" alignItems="center">
                        <Button variant="contained" onClick={()=>{setCreateLedgerOpen(true); setWindowName('Create Ledger')}}>Create New Ledger</Button>
                        <Button variant="contained" onClick={()=>{setAddLedgerOpen(true); setWindowName('Add Ledger')}}>Add Existing Ledger</Button>
                        <Button variant="contained" onClick={()=>{setUpdateLedgerOpen(true); setWindowName('Update Ledger')}}>Update Ledger</Button>
                        <Button variant="contained" onClick={handleDeleteClick}>Delete Ledger</Button>
                        <Button variant="contained" onClick={() =>{navigate(`/portal/ledgers/${ledgerId}/bills`)}}>Bill</Button>
                    </Stack>
                </Grid>
                <Grid item>
                    <LedgerDialog ledgerId={ledgerId} setLedgerName={handleSetLedgerName} openNow={createLedgerOpen || addLedgerOpen || updateLedgerOpen} windowName={windowName} setWindowStatus={closeWindow} handleRefresh={fetchList}></LedgerDialog>
                </Grid>
            </Grid>
        </React.Fragment>
    )
}