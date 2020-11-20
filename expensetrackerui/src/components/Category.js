import React, { Component } from 'react';
import AppNav from './AppNav';
import { removeCategory, createCategory, getAllCategories } from '../api/UtilsData';
import { Table, Container, Form, FormGroup, Button, Label, Input } from 'reactstrap';
import { Link } from 'react-router-dom';
import './Category.css';
import Alert from 'react-s-alert';



class Category extends Component {

    emptyItem={
        categoryName: ''
    }

    state = { 
        isLoading: true,
        categories : [],
        item: this.emptyItem
     }

     constructor(props){
        super(props)
        this.state = {
            isLoading: true,
            categories: [],
            item: this.emptyItem
          }

          this.handleSubmit = this.handleSubmit.bind(this);
          this.handleChange = this.handleChange.bind(this);
    }

     async componentDidMount(){
        const body = await getAllCategories();
        console.log(body);
        this.setState({categories : body, isLoading : false});
     }

     async handleChange(event){
        const target= event.target;
        const value= target.value;
        const name = target.name;

        let item = {...this.state.item};
        item[name]=value;
        this.setState({item});
        console.log(this.state.item);

    }

    async handleSubmit(event){
        event.preventDefault();
        const {item }= this.state;
        console.log(this.state);
        const response = await createCategory(item);
        if(response.success){
            Alert.success("You are Successfully added the Category !");
        }else{
            Alert.error((response.errorCode && response.errorMessage) || 'Oops ! something went wrong. ') ;
        }   
        setTimeout(function(){
            window.location.reload(1);
         }, 3000);
    }

    async remove(id){
        const response =await removeCategory(id);
        console.log(response);
        if(response.ok){
            Alert.success("Successfully remove the Category!");
        }else{
            Alert.error("UnSuccessfully remove the Category!");
        }
        setTimeout(function(){
            window.location.reload(1);
         }, 3000);
    }

    render() { 
        const { categories, isLoading } = this.state;
        if(isLoading)
            return (<div>Loading ..... </div>);

        let rows=
        categories.map(category =>
            <tr key={category.categoryId}>
                <td>{category.categoryName}</td>
                <td><Button size="sm" color="danger" onClick={()=> this.remove(category.categoryId)}>Delete</Button></td>
            </tr>
        
        )    

        return (  
            
            <div>
             <AppNav/>
             <div className="category-container">
                    <div className="category-content">               
                        <Container>
                            <h2>Categories</h2>
                            <Form onSubmit={this.handleSubmit}>
                                <FormGroup>
                                    <Label for="category">Category</Label>
                                    <Input type="text" name="categoryName" id="category" onChange={this.handleChange} autoComplete="name"/>
                                </FormGroup>

                                <FormGroup>
                                    <Button color="primary" type="submit" >Save</Button>{' '}
                                    <Button color="secondary" tag={Link} to="/" >Cancel</Button>
                                </FormGroup>

                            </Form>
                        </Container>

                        <Container>
                            <h3> Category List </h3>
                            <Table className="mt-4">
                                <thead>
                                    <tr>
                                        <th width="40%">Category Name</th>
                                        <th width="10%">Action</th>
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
 
export default Category;
