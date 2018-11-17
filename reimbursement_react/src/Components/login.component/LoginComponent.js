import React from 'react';
import { Button } from 'reactstrap';
import { Alert } from 'reactstrap';
export class LoginComponent extends React.Component {
  
    constructor(props) {
      
        super(props);
        this.action = false;
        this.state = {
          username: '',
          password: '',
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
   
    submit = (e) =>{
        e.preventDefault()
        let cred = this.state
        // making a request with this url
        fetch("http://localhost:8088/Project1/login",{
          method: 'POST',
          body: JSON.stringify(cred),
          headers: {
            'Content-Type': 'application/json'
          },
          credentials: 'include'
        })
        .then(res =>(res.json()))
        .then(res =>{
          if(res.role === 'Employee'){
            //if the response was sent forward to home
                this.props.history.push('/employee-home');
            }
          else if(res.role ==='Manager'){
              this.props.history.push('/manager-home');
            }
          }).catch(reason => {})
    }

  render() {
    
    return (
      <>
      {this.action && <Alert color="primary">
        This is a primary alert — check it out!
      </Alert>  
      }
        <form className='' onSubmit={this.submit}>
        <h1 >Login Boi</h1>
        


        {/* username input and label will update state.username if input changes */}
        <label htmlFor="input-username">Username</label>
        <input type="text"
          id="input-username"
          placeholder="Username"
          required
          value={this.state.username}
          onChange={this.usernameChange}
        />

        <label htmlFor="inputPassword">Password</label>
        <input type="password"
          id="inputPassword"
          placeholder="Password"
          required
          value={this.state.password}
          onChange={this.passwordChange} />

        <Button
          type="submit" color = 'danger'>
          Sign in
        </Button>
      </form>
      </>
    )
  }
}