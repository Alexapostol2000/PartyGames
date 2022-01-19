import { Button } from "bootstrap";
import { Navigate } from "react-router";

const Account = ({ token, name }) => {

    if(!token) {
        return (
          <Navigate to="/login"/>
        );
    }
       
    return (
        <div class = "container"  style={{ width: '20rem', maxHeight: '40rem', margin: '5%',
        backgroundColor: '#85BAA1', alignContent:'center', borderRadius:'2rem',  borderColor:'#d3bcc0'}}  >
        <div className="card shadow mb-1 mx-auto text-center" />
                  <h5 class="card-title"> My account </h5>
                  <h6 class="card-subtitle mb-2 text-muted"> Nume player: {name} </h6>
                  <h6 class="card-subtitle mb-2 text-muted"> Total puncte: </h6>
                  <button style={{ backgroundColor: '#85BAA1', alignContent:'center', borderColor:'#d3bcc0', borderRadius:"2rem", margin:'5%'}} > 
                  Create Room</button>
                  <p> lista camere (acordeon) + buton on click enter room</p>
    </div>
    
    );}
    export default Account;