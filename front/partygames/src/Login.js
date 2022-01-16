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
      
    <div className="login-wrapper">
      <h1>Please Log In</h1>
      <form onSubmit={handleSubmit}>
        <label>
          <p>Username</p>
          <input type="text"  required value = {name} onChange={e => setUserName(e.target.value)} />
        </label>
        <label>
          <p>Password</p>
          <input type="password" required value = {password} onChange={e => setPassword(e.target.value)} />
        </label>
        
        <div>
           
          <button type="submit">Submit</button>
        </div>
      </form>
    </div>
  )
}
export default Login;