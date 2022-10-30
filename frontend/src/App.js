import { Routes, Route } from 'react-router-dom';
import './App.css';
import AppLayout from './layouts/AppLayout';
import Home from './layouts/Home';
import SignUp from './layouts/SignUp';
import BankAccount from './layouts/BankAccount';
import PortalLayout from './layouts/PortalLayout';
import Ledger from './layouts/Ledger';
import Setting from './layouts/Setting';
import Bill from './layouts/Bill';


function App() {
  return (
    <Routes>
      <Route path="/" element={<AppLayout/>}>
        <Route index element={<Home/>}/>
        <Route path="sign-up" element={<SignUp/>}/>
      </Route>
      <Route path="/portal" element={<PortalLayout/>}>
        <Route index element={<BankAccount/>}/>
        <Route path="ledgers" element={<Ledger/>}/>
        <Route path="ledgers/:id/bills" element={<Bill/>}/>
        <Route path="settings" element={<Setting/>}/>
      </Route>
    </Routes>
  );
}

export default App;
