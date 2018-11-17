import React from 'react';

export class CreateReimbComponent extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      amount: '',
      description: '',
      receipt: '',
      typeId: 1,
      dateSubmitted :new Date().toLocaleDateString()
    }
  }

amountChange = (amount) =>{
  this.setState({
    ...this.state,
    amount: amount.target.value
  })



}
descriptionChange = (des) =>{
  this.setState({
      ...this.state,
      description : des.target.value
  })
}

typeChange = (select) =>{
  this.setState({
      ...this.state,
      typeId : select.target.value
  })
}
submit = (e) =>{
  e.preventDefault()
  let reimb = {
    amount: this.state.amount,
      description: this.state.description,
      receipt: this.state.receipt,
      typeId: this.state.typeId
  }
  // making a request with this url
  fetch("http://localhost:8088/Project1/employee/submit_reimbursement",{
    method: 'POST',
    body: JSON.stringify(reimb),
    headers: {
      'Content-Type': 'application/json'
    },
    credentials: 'include'
  })
  .then(res =>{
      //if the response was sent forward to home
      if(res.status === 200){
          this.props.history.push('/employee-home');
      }
  })
}
  render() {
    return (
      
      <div>
        <div className = "form-group">
          <form onSubmit = {this.submit}>
            <label htmlFor ="amount">Amount</label>
            <input name ="amount" placeholder ="Enter amount" type ="text" value ={this.state.amount} onChange = {this.amountChange} required />

            <label htmlFor ="date">Date</label>
            <input name ="date"type ="text" value ={this.state.dateSubmitted} readOnly className="form-control-plaintext" id="staticEmail"/>

            <label htmlFor ="description">Description</label>
            <input name ="description"type ="text" value ={this.state.description} onChange = {this.descriptionChange} />
            <label>Type</label>
            <select value = {this.state.type} onChange = {this.typeChange}>
              <option value = "1">Lodging</option>
              <option value = "2">Travel</option>
              <option value = "3">Food</option>
              <option value = "4">Other</option>
            </select>


            <button type="submit">Submit Reimbursement</button>

          </form>
        </div>
      </div>
    )
  }

}