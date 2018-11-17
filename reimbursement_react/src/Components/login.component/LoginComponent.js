import React from 'react';
import { Button, Alert,Form,FormGroup, Input,Label} from 'reactstrap';

export class LoginComponent extends React.Component {
  
    constructor(props) {
      
        super(props);
        this.action = false;
        this.state = {
          username: '',
          password: '',
          alert : false
        }
      }
    
      passwordChange = (e) => {
        this.setState({
          ...this.state,
          password: e.target.value
        })
      }
    
      usernameChange = (e) => {
        this.setState({
          ...this.state,
          username: e.target.value
        })
      }

      displayAlert = ()=>{
        this.action = true;
        
      }
   
      invalidCred = (callback) =>{
        this.setState ({
          ...this.state,
          alert: true
        })
        setTimeout(this.removeAlert, 10000)
        
      }
      removeAlert = () =>{
        this.setState({
          ...this.state,
          alert : false
        })
      }
    submit = (e) =>{
        e.preventDefault()
        let cred = {
          username : this.state.username,
          password : this.state.password
        }
        // making a request with this url
        fetch("http://localhost:8088/Project1/login",{
          method: 'POST',
          body: JSON.stringify(cred),
          headers: {
            'Content-Type': 'application/json'
          },
          credentials: 'include'
        })

        .then(res =>(res.json())).catch(reason => { 
          this.invalidCred()
        })
        .then(res =>{
          if(res.role === 'Employee'){
            //if the response was sent forward to home
                this.props.history.push('/employee-home');
            }
          else if(res.role ==='Manager'){
              this.props.history.push('/manager-home');
            }
          }).catch(reason => {

          })
    }

  render() {
    
    return (
      <>
      {
        this.state.alert && <Alert color="danger" isOpen={this.state.alert} toggle={this.removeAlert}>
        Invalid username or passowrd. Please try again.
      </Alert>  
      }
      {/* {console.log(this.state.alert)} */}
        <Form id='loginForm' className= "col-3 border border-primary" onSubmit={this.submit}>
          <FormGroup id = "loginFormGroup">
            <h1 >Login Boi</h1>
            {/* username input and label will update state.username if input changes */}
            <Label htmlFor="input-username">Username</Label>
            <Input type="text"
              id="input-username"
              placeholder="Username"
              required
              value={this.state.username}
              onChange={this.usernameChange}
            />

            <Label htmlFor="inputPassword">Password</Label>
            <Input type="password"
              id="inputPassword"
              placeholder="Password"
              required
              value={this.state.password}
              onChange={this.passwordChange} />
            <Button type="submit" color = 'primary' id ='loginBtn'>
              Sign in
            </Button>

        </FormGroup>
      </Form>
      
      </>
    )
  }
}