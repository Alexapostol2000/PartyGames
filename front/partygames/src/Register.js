import React, { useState } from 'react';
import { Navigate } from 'react-router-dom';

async function register(credentials) {
  return fetch('http://localhost:8080/game/register', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(credentials)
  })
    .then(data => data.json())
 }

const Register = () => {
    const [name, setName] = useState();
    const [password, setPassword] = useState();
    const userRole = "ROLE_USER"
    
  const handleSubmit = async e => {
    e.preventDefault();
    console.log(name);
    const retBody = await register({
      
      name,
      password,
      userRole
    });


  }
  
  return (
    
      <div className="card shadow mb-4 mx-auto text-center" style={{ width: '22rem', maxHeight: '40rem', marginTop: '5%', backgroundColor: '#85BAA1' }}>
        <div className="card-body">
            <h4 className="card-title mb-0 border-bottom font-weight-bold"> Register</h4>
        </div>
                
        <div className="card-body text-center">
        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label>Name</label>
            <input type="text" className="form-control" placeholder="Enter username" onChange={e => setName(e.target.value)}/>
            
          </div>
        
          <div className="form-group">
            <label>Password</label>
            <input type="password" className="form-control" id="exampleInputPassword1" placeholder="Password" onChange={e => setPassword(e.target.value)}/>
          </div>

        

          
          <button type="submit" className="btn btn-dark">Submit</button>
        </form>

          
        </div>
        <div className="card-footer">
          <small className="text">
              Already have an account?
              <a className="ml-2" href="/login">
                  Sign In
              </a>
          </small>    
        </div>
      </div>
  
  

);



}
export default Register;