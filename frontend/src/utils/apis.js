import axios from 'axios';
import { getCookie } from './cookies';

export const apis = {
    signup,
    login,
    userUpdate,
    userRemove,
    getAllBankAccounts, 
    addBankAccount,
    updateBankAccount,
    deleteBankAccount,
    getAllLedgers,
    createLedger,
    addUserToLedger,
    updateLedger,
    deleteLedger,
    getAllBills,
    deleteBill,
    createBill,
    updateBill
}

const config = {
    headers: { Authorization: `Bearer ${getCookie('token')}` }
}

const instance = axios.create({
    baseURL: process.env.REACT_APP_API_BASE_URL
})

function signup(username, email, password){
    return instance.post('/auth/signup', {username, email, password})
}

function login(username, password) {
    return instance.post('/auth/login', {username, password})
}

function userUpdate(userId, username, email, password){
    return instance.put('/api/users/' + userId, {username, email, password}, config);
}

function userRemove(userId){
    return instance.delete('/api/users/' + userId, config);
}

function getAllBankAccounts(userId){
    console.log(config.headers)
    return instance.get('/api/users/'+ userId +'/bankAccounts', config)
}

function addBankAccount(userId, accountNumber, bankName, startBalance) {
    return instance.post('/api/users/'+ userId +'/bankAccounts', {
        accountNumber, bankName, startBalance
    }, config)
}

function updateBankAccount(userId, accountId, accountNumber, bankName, startBalance){
    return instance.put('/api/users/'+ userId +'/bankAccounts/'+ accountId, {
        accountNumber, bankName, startBalance
    }, config);
}

function deleteBankAccount(userId, accountId) {
    return instance.delete('/api/users/'+ userId +'/bankAccounts/'+ accountId, config);
}

function getAllLedgers(userId) {
    return instance.get('/api/users/'+ userId + '/ledgers', config);
}

function createLedger(name){
    return instance.post('/api/ledgers', {name}, config);
}

function addUserToLedger(userId, name) {
    return instance.post('/api/users/' + userId + '/ledgers/' + name, {}, config);
}

function updateLedger(ledgerId, name){
    return instance.put('/api/ledgers/' + ledgerId, {name}, config);
}

function deleteLedger(ledgerId) {
    return instance.delete('/api/ledgers/' + ledgerId, config);
}

function getAllBills(ledgerId) {
    return instance.get('/api/ledgers/' + ledgerId + '/bills', config);
}

function deleteBill(ledgerId, billId) {
    return instance.delete('/api/ledgers/' + ledgerId + '/bills/' + billId, config);
}

function createBill(ledgerId, amount, accountNumber, payeeName){
    return instance.post('/api/ledgers/' + ledgerId + '/bills/', {amount, accountNumber, payeeName}, config);
}

function updateBill(ledgerId, billId, amount, accountNumber, payeeName){
    return instance.put('/api/ledgers/' + ledgerId + '/bills/' + billId, {amount, accountNumber, payeeName}, config);
}




