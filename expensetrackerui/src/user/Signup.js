import React, { Component } from 'react';
import { Redirect } from 'react-router-dom';
import { Button, Label,Input } from 'reactstrap';
import AppNav from '../components/AppNav';
import { Link } from 'react-router-dom';
import './Signup.css';
import {signup} from '../api/UtilsData';
import Alert from 'react-s-alert';

class Signup extends Component {
    state = {  }

    render() { 
        if(this.props.authenticated){
            return <Redirect to={{ pathname: "/" , state : {from:  this.props.location} }}/>
        }

        return (  
            <div>
                <AppNav/> 
                <div className="signup-container">
                    <div className="signup-content">
                        <h1 className="signup-title">Sign Up </h1> 
                        <SignupForm {...this.props} />
                        <span className=""> Already have an account ? <Link to="/login">Login</Link></span> 
                    </div>
                </div>
            </div>
        );
    }
}
 
class SignupForm extends Component{
    constructor(props){
        super(props);
        this.state={
            name: '', 
            email: '',
            password: '' 
        }
        this.handleInputChange= this.handleInputChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    };

    handleInputChange(event){
        const target= event.target;
        const inputName=target.name;
        const inputValue=target.value;

        let state = {...this.state};
        console.log(state);
        state[inputName]=inputValue;
        this.setState(state);
    }

    handleSubmit(event){
        event.preventDefault();

        const signupRequest= Object.assign({}, this.state); 

        console.log(signupRequest);
        const response = signup(signupRequest);
        console.log(response);
        console.log(response.ok);
        console.log(response.status);
        console.log(response.location);
        response.then(result => {
            console.log(result);
            Alert.success("You are Successfully logged in !");
            //this.props.history.push("/");
        });

        response.catch(error=>{
            console.log("error error = "+error.error);
            Alert.error((error && error.message) || 'Oops ! something went wrong. ') ;
            this.emptyMessage.message= error.error || 'Oops ! something went wrong. ';
            this.props.history.push('/signup');
        });
    }

    render(){
        return (
            <form onSubmit={this.handleSubmit}>
                 <Label for="email">Name</Label>
                <div className="form-item">
                    <Input type="text" name="name" className="form-control" id="name"
                        onChange={this.handleInputChange} autoComplete="name" required />  
                </div>
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
                    <Button color="primary" type="submit" >Sign Up</Button>
                    <Button color="secondary" tag={Link} to="/" >Cancel</Button>
                </div>

            </form>
        )

    }
}

export default Signup;