import React, {useState, useEffect} from 'react'
import { Button } from "bootstrap";
import { Navigate } from "react-router";


async function room(credentials,token) {
  return fetch('http://localhost:8080/game/room', {
    method: 'POST',
    headers: {
      'Authorization':  `${token}`,
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(credentials)
  })
    .then(data => data.json())
 }





const Account = ({ setToken, token, name }) => {
  const [room_name, setRoomName] = useState();
  const [roomType, setRoomType] = useState();
  
  const [privateRooms, setPrivateRooms] = useState([]);
  const [publicRooms, setPublicRooms] = useState([]);

  const handleSubmit = async e => {
    e.preventDefault();
        const retBody = await room({
         name: room_name,
         roomType

        },token);
        

  }

  const getPublicRooms = (token) => {
    fetch('http://localhost:8080/game/room/public', {
      method: 'GET',
      headers: {
        'Authorization': token,
        'Content-Type': 'application/json'
      }
    })
    .then((response) => response.json())
    .then((data) => setPublicRooms(data));
  }

  const getPrivateRooms = (token) => {
    fetch('http://localhost:8080/game/room/private', {
      method: 'GET',
      headers: {
        'Authorization': token,
        'Content-Type': 'application/json'
      }
    })
    .then((response) => response.json())
    .then((data) => setPrivateRooms(data));
  }
  
  useEffect(() => {
    getPublicRooms(token);
    getPrivateRooms(token);
  }, []);
  

    if(!token) {
        return (
          <Navigate to="/login"/>
        );
    }
      
  console.log(publicRooms);
  console.log(privateRooms);
  
    return (
      <>
        <div class = "container"  style={{ width: '20rem', maxHeight: '40rem', margin: '5%',
        backgroundColor: '#85BAA1', alignContent:'center', borderRadius:'2rem',  borderColor:'#d3bcc0'}}  >
        <div className="card shadow mb-1 mx-auto text-center" />
                  <h5 class="card-title"> My account </h5>
                  <h6 class="card-subtitle mb-2 text-muted"> Nume player: {name} </h6>
                  <h6 class="card-subtitle mb-2 text-muted"> Total puncte: </h6>
                  <button type="submit" style={{ backgroundColor: '#85BAA1', alignContent:'center', borderColor:'#d3bcc0', borderRadius:"2rem", margin:'5%'}} > 
                  Create Room</button>
                  <p> lista camere (acordeon) + buton on click enter room</p>
                  
    </div>
    <div class = "container"  style={{ width: '20rem', maxHeight: '40rem', margin: '5%',
        backgroundColor: '#85BAA1', alignContent:'center', borderRadius:'2rem',  borderColor:'#d3bcc0'}}  >
       <div class="row" style ={{ alignContent: 'center', marginTop: '5%'}}> 
              
              <div className="card mx-auto" style ={{backgroundColor: '#85BAA1', padding: '15px', width: '35rem'}}>
              <h5 class="card-title" style ={{marginBottom:'20px'}}> Creare camera </h5>
                  <form class="row g-3" onSubmit={handleSubmit}>
                    <div class="col-md-12">
                      <input type="text" class="form-control" id="1" placeholder="Nume camera"  onChange={e => setRoomName(e.target.value)}/>
                    </div>
                 
                    <div class="col-12">
                    <select id="inputState" class="form-select" onChange={e => setRoomType(e.target.value)}>
                        <option selected>Tip Camera..</option>
                        <option>PUBLIC_ROOM</option>
                        <option>PRIVATE_ROOM</option>
                     
                      </select>
                    </div>
                    <div class="col-12">
                      <button type="submit" class="btn btn-primary" style ={{backgroundColor: '#85BAA1', alignContent:'center', borderColor:'#d3bcc0', borderRadius:"2rem", margin:'5%'}}>Create Room</button>
                    </div>
                  </form>
              </div>
            </div>
        </div>

        {publicRooms.map((room) => {
            console.log(room);

            return (
              <div class = "container" key={room.id} style={{ width: '20rem', maxHeight: '40rem', margin: '5%',
                    backgroundColor: '#85BAA1', alignContent:'center', borderRadius:'2rem',  borderColor:'#d3bcc0'}}  >
                    <div className="card shadow mb-1 mx-auto text-center" />
                <p>{room.name} {room.maxPlayerNum} {room.gameName}</p>
              </div>

            );
        })}

        {privateRooms.map((room) => {
            console.log(room);

            return (
              <div class = "container" key={room.id} style={{ width: '20rem', maxHeight: '40rem', margin: '5%',
                backgroundColor: '#85BAA1', alignContent: 'center', borderRadius: '2rem', borderColor: '#d3bcc0',
                top: '0rem', left: '0rem'}}  >
                    <div className="card shadow mb-1 mx-auto text-center" />
                    <p>{room.name} {room.maxPlayerNum} {room.gameName}</p>

              </div>

            );
        })} 
    </>
    );}
    export default Account;