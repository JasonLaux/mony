import { Grid } from '@mui/material';
import * as React from 'react';
import BasicTable from '../components/BasicTable';
import Button from '@mui/material/Button';
import Stack from '@mui/material/Stack';
import {useState, useEffect} from 'react';
import { apis } from '../utils/apis';
import BillDialog from '../components/BillDialog';
import { useParams } from 'react-router-dom';

export default function Bill(){

    const [rows, setRows] = useState([]);
    const [createBillOpen, setCreateBillOpen] = useState(false);
    const [updateBillOpen, setUpdateBillOpen] = useState(false);
    
    //Bill id
    const [billId, setBillId] = useState(1);
    let params = useParams();
    let ledgerId = params.id.match(/\d+/)[0];

    useEffect(() => {
        fetchList()
    }, [])


    const tableHeader = ["Select", "Created Time", "Amount", "Payee", "Ledger Name", "Bank Account Number"]

    const fetchList = () => {
        apis.getAllBills(ledgerId).then(
            res => {
                console.log("ledgerId:", ledgerId)
                const rows = res.data;
                setRows(
                    rows.map(row => Object.keys(row).map(key => row[key]))
                )
            }
        ).catch(err => {
            console.log(err)
        })
    }

    const closeWindow = () => {
        setCreateBillOpen(false)
        setUpdateBillOpen(false)
    }

    const handleClick = (id) => {
        setBillId(id)
        console.log(id)
    }

    const handleDelete = () => {
        apis.deleteBill(ledgerId, billId).then(
            res => {
                console.log(res.data);
                fetchList()
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
                        <Button variant="contained" onClick={() => setCreateBillOpen(true)}>Add Bill</Button>
                        <Button variant="contained" onClick={() => setUpdateBillOpen(true)}>Update Bill</Button>
                        <Button variant="contained" onClick={handleDelete}>Delete Bill</Button>
                    </Stack>
                </Grid>
                <Grid item>
                    <BillDialog billId ={billId} ledgerId = {ledgerId} openNow={createBillOpen || updateBillOpen} createOrUpdate={createBillOpen} setWindowStatus={closeWindow} handleRefresh={fetchList}/>
                </Grid>
            </Grid>
        </React.Fragment>
    )
}