import * as React from 'react';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import Paper from '@mui/material/Paper';
import { createTheme } from '@mui/system';
import { ClassNames } from '@emotion/react';
import Radio from '@mui/material/Radio';
import {useState} from 'react';
import RadioGroup from '@mui/material/RadioGroup';
import { ConstructionOutlined } from '@mui/icons-material';

export default function BasicTable(props) {

  // const [tableHeader, setTableHeader] = useState([]);

  const { tableHeader, rows, handleClick} = props
  const [selectedValue, setSelectedValue] = useState('');

  const handleChange = (event) => {
    setSelectedValue(event.target.value);
    console.log(event.target.value);
  };
  // const handleClick = (row) => {
  //   setId(row[0])
  // }

  return (
    <TableContainer component={Paper}>
      <Table sx={{ minWidth: 650 }} aria-label="simple table">
        <TableHead>
          <TableRow>
            {/* <TableCell align="center">{tableHeader[0]}</TableCell>
            <TableCell align="center">{tableHeader[1]}</TableCell>
            <TableCell align="center">{tableHeader[2]}</TableCell> */}
            {tableHeader.map((header)=>
              <TableCell align="center" key={header}>{header}</TableCell>
            )}
          </TableRow>
        </TableHead>
        <TableBody>
          {rows.map((row) => (
              <TableRow
                onClick={() => handleClick(row[0])}
                key={row[0]}
                sx={{ 
                      // "&:selected, &:selected:hover": {backgroundColor: "purple"},
                      // "&&:hover": {backgroundColor: "purple"}
                    }}
              >
                <TableCell align="center">
                  <Radio
                  checked={row[0] == selectedValue}
                  onChange={handleChange}
                  value={row[0]}
                  name="radio-buttons"
                  inputProps={{ 'aria-label': 'A' }}
                  />
                </TableCell>
                {row.slice(1).map(value => (<TableCell align="center" key={value}>{value}</TableCell>))}
              </TableRow>
            ))} 
        </TableBody>
      </Table>
    </TableContainer>
  );
}