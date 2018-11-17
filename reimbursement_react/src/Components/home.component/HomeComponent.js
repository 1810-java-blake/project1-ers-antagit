import React from 'react';

export class HomeComponent extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            reimbursements: []
        }
    }

    componentWillMount() {
        fetch('http://localhost:8088/Project1/employee/reimbursement', {
          credentials: 'include'
        })
          .then(resp => resp.json())
          .then(data => {
              console.log(data);
            this.setState({               
                reimbursements: data
            })
          });
      }

      routeCreateReimb = () =>{
          this.props.history.push('/createReimb')
      }
  render() {
    return (
        <>
      <div>
          <h1>Current Reimbursements</h1>

          <button onClick = {this.routeCreateReimb}>Add Reimbursement</button>
          <table className="table table-hover">
              <thead>
                <tr>
                    <th>Reimbursement Id</th>
                    <th>Amount</th>
                    <th>Date Submitted</th>
                    <th>Date Resolved</th>
                    <th>Description</th>
                    <th>Reciept</th>
                    <th>Status</th>
                    <th>Type</th>
                </tr>
                
              </thead>
              <tbody>
                      {
                      this.state.reimbursements.map( item =>
                        <tr key = {item.id}>
                        <td>{item.id}</td>
                        <td>{item.amount}</td>
                        <td>{item.submitted}</td>
                        <td>{item.resolved}</td>
                        <td>{item.description}</td>
                        <td>{item.receipt}</td>
                        <td>{item.status}</td>
                        <td>{item.type}</td>
                        
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