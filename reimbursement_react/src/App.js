import React, { Component } from 'react';
import './Include/bootstrap';
import './App.css';
// import { AppNav } from './Components/Nav.component';
import { BrowserRouter, Route, Switch } from 'react-router-dom';
import { LoginComponent } from './Components/login.component/LoginComponent';
import { HomeComponent } from './Components/home.component/HomeComponent';
import { ReimbursementComponent } from './Components/reimbursement.component/RiembursementComponent';
import { CreateReimbComponent } from './Components/createNewReimb.component/CreateReimbComponent';
import { ManagerHomeComponent } from './Components/home.component/ManagerHomeComponent';
class App extends Component {
  render() {
    return (
      <BrowserRouter>
      <>
        
        <div id="main-content-container">
          <Switch>
          <Route path ="/employee-home" component={HomeComponent} />
          <Route path ="/manager-home" component={ManagerHomeComponent} />
          <Route path ="/reimbursement" component = {ReimbursementComponent}/>
          <Route path = "/createReimb" component = {CreateReimbComponent}/>
          
            {/* default */}
            <Route component={LoginComponent} />
          </Switch>
        </div>
      </>
    </BrowserRouter>
    );
  }
}

export default App;
