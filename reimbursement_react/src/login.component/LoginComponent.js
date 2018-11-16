import React from 'react';

export class LoginComponent extends React.Component {
    constructor(props) {
        super(props);
    
        this.state = {
          username: '',
          password: ''
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
          console.log(res);
            //if the response was sent forward to home
                this.props.history.push('/home');
          })
    }
  render() {
    return (
        //When button with type = "submit" is clicked call this.submit 
        <form className="form-signin" onSubmit={this.submit}>
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

        <button
          type="submit">
          Sign in
        </button>
      </form>
    )
  }
}