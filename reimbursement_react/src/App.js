import React, { Component } from 'react';
import './Include/bootstrap';
import './App.css';
import { BrowserRouter, Route, Switch } from 'react-router-dom';
import { LoginComponent } from './login.component/LoginComponent';
import { HomeComponent } from './home.component/HomeComponent';
import { ReimbursementComponent } from './reimbursement.component/RiembursementComponent';
import { CreateReimbComponent } from './createNewReimb.component/CreateReimbComponent';
import { ManagerHomeComponent } from './home.component/ManagerHomeComponent';
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
