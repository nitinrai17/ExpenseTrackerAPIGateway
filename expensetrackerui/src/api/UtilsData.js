//import axios from 'axios';
import { API_BASE_URL,ACCESS_TOKEN } from '../constants';

//const API_BASE_URL = process.env.REACT_APP_API_BASE_URL || '/api/1.0'; 

const request = (options) => {
    const headers = new Headers({
        'Content-Type': 'application/json',
    })
    
    if(localStorage.getItem(ACCESS_TOKEN)) {
        headers.append('Authorization', 'Bearer ' + localStorage.getItem(ACCESS_TOKEN))
    }

    const defaults = {headers: headers};
    options = Object.assign({}, defaults, options);

    
    return fetch(options.url, options)
    .then(response => 
            response.json().then(json => {
                if(!response.ok) {
                    return Promise.reject(json);
                }
                return json;
            })
    );
};


export function getAllCategories() {
    
    return request({
        url: '/categories',
        method: 'GET'
    });
}

export function createCategory(category) {
    
    return request({
        url: '/categories',
        method: 'POST',
        body: JSON.stringify(category)         
    });
}

export function removeCategory(id) {
    
    return fetch(
        `/categories/${id}`,{ 'method': 'DELETE'}
    );
}


export function getAllExpenses() {
    
    return request({
        url: '/expenses',
        method: 'GET'
    });
}

export function createExpense(expense) {
    
    return request({
        url: '/expenses',
        method: 'POST',
        body: JSON.stringify(expense)         
    });
}

export function removeExpense(id) {
    return fetch(
        `/expenses/${id}`,{ 'method': 'DELETE'}
    );
}

export async function  login(login) {
    
     return request({
         url: '/signin',
         method: 'POST',
         body: JSON.stringify(login)         
     });
    
}

export async function  signup(signup) {
    
    return request({
        url: '/signup',
        method: 'POST',
        body: JSON.stringify(signup)         
    });
}

