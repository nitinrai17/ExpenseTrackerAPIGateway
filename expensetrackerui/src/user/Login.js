import React, { Component } from 'react';
import { Link, Redirect } from 'react-router-dom';
import { Button, Label,Input } from 'reactstrap';
import Alert from 'react-s-alert';
import {login} from '../api/UtilsData';
import './Login.css';
import {ACCESS_TOKEN,CURRENTUSER} from '../constants';
import AppNav from '../components/AppNav';



class Login extends Component {
    state = {  }
    
    

    componentDidMount(){
        
    }

    render() { 
        return ( 
            <div>
                <AppNav/> 
                <div className="login-container">
                    <div className="login-content">
                        <h1 className="login-title"> Login </h1>
                        <LoginForm />
                        <span className="signup-link">New user ? <Link to="/signup">Sign up!</Link></span>
                    </div>
                </div>
            </div>
         );
    }
}

class LoginForm extends Component{

    
    constructor(props){
        super(props);
        this.state= {
            email: '',
            password: ''
        };
        this.handleInputChange = this.handleInputChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    async handleInputChange(event) {
        console.log(event);
        const target= event.target;
        const inputName= target.name;
        const inputValue= target.value;
        console.log(inputName + " , "+ inputValue);

        let state = {...this.state};
        console.log(state);
        state[inputName]=inputValue;
        this.setState(state);

    }

    async handleSubmit(event){
        event.preventDefault();

        const loginRequest= Object.assign({}, this.state); 

        console.log(loginRequest);
        const response = login(loginRequest);
        response.then(token=> {
            console.log(token); 
            console.log(token.accessToken);
            localStorage.setItem(ACCESS_TOKEN, token.accessToken);
            localStorage.setItem(CURRENTUSER, loginRequest.email);
            Alert.success("You are Successfully logged in !");
            
            return <Redirect to='/expense' />
        });
        response.catch(error=>{
            console.log("error= "+error);
            console.log("error error = "+error.error);
            Alert.error((error && error.message) || 'Oops ! something went wrong. ') ;
            // this.props.history.push('/login');
        });
    }


    render(){
      
        return (
            <form onSubmit={this.handleSubmit}>
                
                <Label for="email">Email</Label>
                <div className="form-item">
                    <Input type="email" name="email" className="form-control" id="email"
                     onChange={this.handleInputChange} autoComplete="name" required /> 
                </div>
                <Label for="password">Password</Label>
                <div className="form-item">
                    <Input type="password" name="password" className="form-control" id="password"
                    value={this.state.password} onChange={this.handleInputChange} required />
                </div>
                <div className="form-item">
                    <Button color="primary" type="submit" >Login</Button>
                    <Button color="secondary" tag={Link} to="/" >Cancel</Button>
                </div>
            </form>
        );
    }
}
 
export default Login;