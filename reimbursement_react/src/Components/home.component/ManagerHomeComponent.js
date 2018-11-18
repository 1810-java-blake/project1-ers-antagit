import React from 'react';
import { Button, Form, FormGroup,Label} from 'reactstrap';


export class ManagerHomeComponent extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            reimbursements: [],
            sort: "None"
        }
    }

componentDidMount() {
    fetch('http://localhost:8088/Project1/manager/reimbursement', {
        credentials: 'include'
    })
        .then(resp => resp.json())
        .then(data => {
        this.setState({               
            reimbursements: data

        })
        });
    }
rejectReimb = (e) =>{
    fetch('http://localhost:8088/Project1/manager/reject_reimbursement', {
        method: 'POST',
        body: JSON.stringify(e.target.id),
        headers: {
          'Content-Type': 'application/json'
        },
        credentials: 'include'
    })
    .then(resp => resp.json())
    .then(data => {     
        console.log(data); 
    this.setState({
        ...this.state,
        reimbursements : data
    })
})
}
acceptReimb = (e) =>{
    fetch('http://localhost:8088/Project1/manager/accept_reimbursement', {
        method: 'POST',
        body: JSON.stringify(e.target.id),
        headers: {
          'Content-Type': 'application/json'
        },
        credentials: 'include'
    })
    .then(resp => resp.json())
    .then(data => {     
    this.setState({
        ...this.state,
        reimbursements : data
    })
})
}
sortByChange = (e) =>{
    this.setState({
        ...this.state,
        sort : e.target.value
    })
    
}
    renderList = () => {
        let sort = this.state.sort;
        if(sort === "None")
            return this.state.reimbursements;
        else if(sort === "Pending") {
            return this.state.reimbursements.filter((index) =>index.status ==="Pending");
            // Filter
        }
        else if(sort === "Approved") {
            return this.state.reimbursements.filter((index) =>index.status ==="Approved");
        }
        else if(sort === "Denied") {
            return this.state.reimbursements.filter((index) =>index.status ==="Denied");
        }
    }
convertDate = (date)=>{
    let maxDate = new Date(date);
    let dateArray = [maxDate.getMonth() +1,maxDate.getDate(),maxDate.getFullYear()];
    let dateString = `${dateArray[0]} /${dateArray[1]}/${dateArray[2]} `
    return dateString;
}

  render() {
    let renderList = this.renderList();
    
    return (
        <>
      <div>
          <h1>Current Reimbursements</h1>
          <Form>
          <FormGroup>
            <Label for="exampleSelect">Sort By</Label>
            <select value = {this.state.sort} onChange = {this.sortByChange}>
                <option>None</option>
                <option>Pending</option>
                <option>Approved</option>
                <option>Denied</option>
                {/* {name} */}
            </select>
        </FormGroup>
          </Form>
          <table className="table table-hover">
              <thead>
                <tr>
                    <th>Reimbursement Id</th>
                    <th>First Name</th>
                    <th>Last Name</th>
                    <th>Amount</th>
                    <th>Date Submitted</th>
                    <th>Date Resolved</th>
                    <th>Description</th>
                    <th>Reciept</th>
                    <th>Status</th>
                    <th>Type</th>
                    <th></th>
                    <th></th>
                </tr>
                
              </thead>
              <tbody>
                      {
                      renderList.map( item =>
                            
                        <tr key = {item.id}>
                        <td>{item.id}</td>
                        <td>{item.firstName}</td>
                        <td>{item.lastName}</td>
                        <td>{item.amount}</td>
                        <td>{this.convertDate(item.submitted)}</td>
                        <td>{this.convertDate(item.resolved)}</td>
                        <td>{item.description}</td>
                        <td>{item.receipt}</td>
                        <td>{item.status}</td>
                        <td>{item.type}</td>
                        <td><Button color ="success" onClick = {this.acceptReimb} id= {item.id}>Approve</Button></td>
                        <td><Button color ="danger" onClick = {this.rejectReimb} id= {item.id}>Reject</Button></td>
                      </tr>
                      )
                      }
              </tbody>
          </table>
      </div>
      
      </>
    )
  }
}