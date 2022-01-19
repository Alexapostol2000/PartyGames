import React from 'react'
import { Navigate } from 'react-router-dom';

const Logout = ({setToken, token}) => {
  console.log("Enter Logout");
  const handleSubmit = async e => {
    
    e.preventDefault();
    setToken(0);
    
  }

  if(!token) {
    return (
      <Navigate to="/login"/>
    )
  }

  return (
    <div className="card shadow mb-4 mx-auto" style={{ width: '22rem', maxHeight: '40rem', marginTop: '5%', backgroundColor: '#57abd1' }}>
    
      <div className="card-body text-center">
          <h6 className="card-title border-bottom mb-2 font-weight-bold">Are you sure you want to logout?</h6>   
          <button type="button" className="btn btn-dark" onClick={handleSubmit} >Yes!</button>

      </div>  
  </div>
  )
}
export default Logout
