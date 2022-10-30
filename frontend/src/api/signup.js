import axios from 'axios';
import {getCookie, setCookie, decodedCookie} from '../utils/cookies.js';
import { parseJwt } from '../utils/parseJwt.js';


const instance = axios.create({
    baseURL: process.env.REACT_APP_API_BASE_URL
})

export function signup(username, email, password){
    return instance.post('/auth/signup', {username, email, password},
        {headers: {'Content-type': 'application/json'}}
    )
}
