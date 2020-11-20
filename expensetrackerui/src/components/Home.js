import React, { Component } from 'react';
import AppNav from './AppNav';



class Home extends Component {
        
    render() { 
        return ( 
            <div> 
                <AppNav/>
                <h2 style={{display:'flex', justifyContent:'center', alignItems:'center', height:'75vh'}}> 
                       Welcome to Easy Expense Tracker App :) </h2>
    
            </div> 
             );
    }
}
 
export default Home;