import React, { Component } from 'react';
import AppNav from './AppNav';
import DatePicker from 'react-datepicker';
import "../app/App.css";
import "react-datepicker/dist/react-datepicker.css";
import { Table, Container, Form, FormGroup, Button, Label, Input } from 'reactstrap';
import { Link } from 'react-router-dom';
import { getAllCategories, getAllExpenses, createExpense, removeExpense } from '../api/UtilsData';
import Moment from 'react-moment';
import "./Expense.css";
import Alert from 'react-s-alert';


class Expenses extends Component {


    emptyItem ={
        id:51,
        expenseDate: new Date(),
        description: '',
        amount: 0,
        category: {
            categoryId: 0,
            categoryName: ''
        }
    }

    constructor(props){
        super(props)
        this.state = {
            date: new Date(),
            isLoading: true,
            categories: [],
            expenses: [],
            item: this.emptyItem
          }

          this.handleSubmit = this.handleSubmit.bind(this);
          this.handleChange = this.handleChange.bind(this);
          this.handleDateChange = this.handleDateChange.bind(this);
    }

    async handleSubmit(event){
        event.preventDefault();
        const {item }= this.state;        
        console.log(item);
        const response = await createExpense(item);
        console.log(response);
        console.log(response.body);
        console.log(response.success);
        if(response.success){
            Alert.success("You are Successfully added the Expense !");
        }else{
            Alert.error((response.errorCode && response.errorMessage) || 'Oops ! something went wrong. ') ;
        }
        setTimeout(function(){
            window.location.reload(1);
         }, 3000);
    }

    async handleDateChange(date){
        let item={...this.state.item};
        item.expenseDate =date;
        this.setState({item});
    }


    async handleChange(event){
        const target= event.target;
        const value= target.value;
        const name = target.name;

        let item = {...this.state.item};
        console.log(item);
        if(name === 'category' ) {
            let category= item.category;
            console.log(category);
            category.categoryId=value;
        }else{
            item[name]=value;
        }
        this.setState({item});
    }

    async remove(id){
        const response =await removeExpense(id);
        console.log(response);
        if(response.ok){
            Alert.success("Successfully remove the Expense!");
        }else{
            Alert.error("UnSuccessfully remove the Expense!");
        }       
        setTimeout(function(){
            window.location.reload(1);
         }, 3000);
    }

      async componentDidMount(){
        const body= await getAllCategories();
        this.setState({categories : body, isLoading: false});

        const bodyExpenses= await getAllExpenses();
        this.setState({expenses : bodyExpenses, isLoading: false});
      }


    render() { 

        const title= <h3>Add Expense</h3>;
        const {categories }= this.state;
        const {expenses, isLoading }= this.state;

        if(isLoading) 
            return(<div>Loading ......</div>) 

        let categoryList = 
            categories.map(category =>
                <option key={category.categoryId} value={category.categoryId}>
                    { category.categoryName}    
                </option>
            )
        
        let rows=
            expenses.map(expense =>
                <tr key={expense.id}>
                    <td>{expense.description}</td>
                    <td>{expense.amount}</td>
                    <td><Moment format="DD/MM/YYYY">{expense.expenseDate}</Moment></td>
                    <td>{expense.category.categoryName}</td>
                    <td><Button size="sm" color="danger" onClick={()=> this.remove(expense.id)}>Delete</Button></td>
                </tr>
            
            )
        

        return ( 
            <div>
                <AppNav/> 
                 <div className="expense-container">
                    <div className="expense-content">
                        <Container>
                            {title}
                            <Form onSubmit={this.handleSubmit}>
                                <FormGroup>
                                    <Label for="description">Description</Label>
                                    <Input type="text" name="description" id="description" onChange={this.handleChange} autoComplete="name"/>
                                </FormGroup>

                                <FormGroup>
                                    <Label for="category" >Category</Label>
                                    <select name="category" onChange={this.handleChange} autoComplete="name">
                                        <option >
                                                Select Category
                                        </option>
                                        {categoryList}
                                    </select>
                                    
                                </FormGroup>

                                <FormGroup>
                                    <Label for="date"> Date</Label>
                                    <DatePicker selected={this.state.item.expenseDate}  onChange={this.handleDateChange}/>
                                </FormGroup>

                                <div className="row">
                                    <FormGroup className="col-md-4 mb-3" >
                                        <Label for="amount">Amount</Label>
                                        <Input type="text" name="amount" id="amount" onChange={this.handleChange} autoComplete="name"/>
                                    </FormGroup>
                                </div>
                                

                                <FormGroup>
                                    <Button color="primary" type="submit" >Save</Button>{' '}
                                    <Button color="secondary" tag={Link} to="/" >Cancel</Button>
                                </FormGroup>

                            </Form>
                        </Container>


                        <Container>
                            <h3> Expense List </h3>
                            <Table className="mt-4">
                                <thead>
                                    <tr>
                                        <th width="40%">Description</th>
                                        <th width="15%">Amount</th>
                                        <th width="20%">Date</th>
                                        <th>Category</th>
                                        <th width="15%">Action</th>
                                    </tr> 
                                </thead>
                                <tbody>
                                    {rows}
                                </tbody>

                            </Table>
                        </Container>
                    </div>
                </div>
            </div>     
        );
    }
}
 
export default Expenses;