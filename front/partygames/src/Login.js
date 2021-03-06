import React, { useState } from 'react';
import { Navigate } from "react-router-dom";

 
async function login(credentials) {
  return fetch('http://localhost:8080/game/login', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(credentials)
  })
    .then(data => data.json())
 }


const Login = ({setToken, token, setName}) => {
  const [name, setUserName] = useState("");
  const [password, setPassword] = useState("");

  console.log("mhm" + token);
  if(token) {
    return (
      <Navigate to="/account"/>
    );
  }
  
  const handleSubmit = async e => {
    e.preventDefault();
    const mesaj = await login({
      name,
      password
    })
    console.log("inainte de set" + mesaj.jwt);
    setToken(mesaj.jwt);
    setName(mesaj.name);
    console.log ("nume" + mesaj.name);


 
  }

  return(
      
  
    <div className="card shadow mb-4 mx-auto text-center" style={{ width: '22rem', maxHeight: '40rem', marginTop: '5%', backgroundColor: '#85BAA1' }}>
    <div className="card-body">
        <h4 className="card-title mb-0 border-bottom font-weight-bold"> Login</h4>
    </div>
            
    <div className="card-body text-center">
    <form onSubmit={handleSubmit}>
      <div className="form-group">
        <label for="exampleInputEmail1">Username</label>
        <input type="text" className="form-control" placeholder="Enter username" onChange={e => setUserName(e.target.value)}/>
        
      </div>
      <div className="form-group">
        <label for="exampleInputPassword1">Password</label>
        <input type="password" className="form-control" id="exampleInputPassword1" placeholder="Password" onChange={e => setPassword(e.target.value)}/>
      </div>
      
      <button type="submit" className="btn btn-dark" style ={{backgroundColor:'#631D76'}}>Submit</button>
    </form>

      
    </div>
    <div className="card-footer">
      <small className="text">
          Don't have an account? 
          <a className="ml-2" href="/register">
              Register
          </a>
      </small>    
    </div>
    <div className="card-footer">
      <small className="text">
          Or login as guest using guest/guest!
          
      </small>    
    </div>
  </div>   
  )
}
export default Login;