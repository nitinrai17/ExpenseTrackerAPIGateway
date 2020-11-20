import React, { Component } from 'react';
import {Route, BrowserRouter, Switch, Redirect} from 'react-router-dom';
import Category from '../components/Category';
import Home from '../components/Home';
import Expense from '../components/Expense';
import Login from '../user/Login';
import Signup from '../user/Signup';
import Alert from 'react-s-alert';
import 'react-s-alert/dist/s-alert-default.css';
import 'react-s-alert/dist/s-alert-css-effects/slide.css';
import './App.css';
import PrivateRoute from './PrivateRoute';
import Profile from '../user/Profile';
import NotFound from './NotFound';
import {ACCESS_TOKEN,CURRENTUSER } from '../constants';

class App extends Component {

  constructor(props){
    super(props);
    this.state={
        authenticated:  false,
        currentUser: null,
        loading: false
    }
    this.loadCurrentlyUser = this.loadCurrentlyUser.bind(this);

    
  }

  loadCurrentlyUser(){
    this.setState({
      loading: true
    });

    if(localStorage.getItem(ACCESS_TOKEN)) {
      this.setState({
        currentUser: localStorage.getItem(CURRENTUSER),
        authenticated: true,
        loading: false
      });
    }else {
      this.setState({
        loading: false
      });
      return <Redirect to='/login' />
    }
  }

  handleLogout(){
    localStorage.removeItem(ACCESS_TOKEN);
    localStorage.removeItem(CURRENTUSER);
    this.setState({
      authenticated: false,
      currentUser: null
    });
    Alert.success("You are safely logged out! ");
  }

  componentDidMount(){
    this.loadCurrentlyUser();
  }

  
  render() { 
    if(this.state.loading){
      return (<div>Loading ......</div>);
    }

    return (
      <div>
        <div>
            <BrowserRouter>
              <Switch>
                  <Route path="/" exact component={Home}/>
                  <PrivateRoute path="/profile" authenticated={this.state.authenticated} 
                    currentUser={this.state.currentUser} component={Profile}/>
                  <Route path="/category" component={Category}/>
                  <Route path="/expense"  component={Expense}/>
                  <Route path="/login"  component={Login}/>
                  <Route path="/signup"  component={Signup}/>
                  <Route component={NotFound} /> 
              </Switch>
            </BrowserRouter> 
        </div>
        <div>
          <Alert stack={{limit: 3}} 
          timeout = {3000}
          position='top-right' effect='slide' offset={65} />
        </div>
      </div>
    );
  }
}
 
export default App;