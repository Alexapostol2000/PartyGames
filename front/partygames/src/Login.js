import React, { useState } from 'react';

 



const Login = () => {
  const [name, setUserName] = useState("");
  const [password, setPassword] = useState("");

  const handleSubmit = async e => {
    e.preventDefault();
    const mesaj = {name, password};
    fetch("http://localhost:8080/game/login", {
   method: "POST",
   headers: {
     "Content-Type": "application/json"
   },
   body: JSON.stringify(mesaj)
 })
   .then()

 
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
      
      <button type="submit" className="btn btn-dark">Submit</button>
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
  </div>   
  )
}
export default Login;