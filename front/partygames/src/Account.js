import React, {useState, useEffect} from 'react'
import { Button } from "bootstrap";
import { Navigate } from "react-router";
import {format} from 'react-string-format'
import { Link } from 'react-router-dom';
import Room from './Room'

async function room(credentials, token) {

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

//  function handleAddPublic(room) {
//   const newPublicRoomList = publicRooms.concat({ room });

//   setPublicRooms(newPublicRoomList);
// }

// function handleAddPrivate(room) {
//   const newPrivateRoomList = privateRooms.concat({ room });

//   setPrivateRooms(newPrivateRoomList);
// }

const Account = ({ setToken, setAdminToken, token, name }) => {
  const [room_name, setRoomName] = useState();
  const [roomType, setRoomType] = useState();
  const [maxPlayers, setMaxPlayers] = useState();
  const [roomPassword, setRoomPassword] = useState("");
  const [dataRoom, setDataRoom] = useState(null);
  const [dataRoom1, setDataRoom1] = useState(null);

 const [privateRooms, setPrivateRooms] = useState([]);
 const [publicRooms, setPublicRooms] = useState([]);
 const [adminToken, setAdminTok] = useState();
 

 const [roomPass, setRoomPass] = useState("");

  //const [created, setCreated ] = useState(0);

  
  
  const handleSubmit = async e => {
    e.preventDefault();
        const retBody = await room({
         name: room_name,
         roomType,
         maxPlayerNum: maxPlayers,
         password: roomPassword

        }, token);
        
        getPublicRooms(token);
        getPrivateRooms(token);
        setAdminTok(token);
        setAdminToken(token);
        e.target.reset();
        console.log("aaaaaaaaa");
        console.log("room.id" + retBody.id);
        
        
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
      .then((data) => {
        setPublicRooms(data);
        console.log(data);
      });
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
      .then((data) => {
        setPrivateRooms(data);
        console.log(data);
      });
  }
  
  useEffect(() => {
    getPublicRooms(token);
    getPrivateRooms(token);
  }, [2000]);

  

  // const handleEnterPublic = async e => {
  //   e.preventDefault();
  //   const requestOptions = {
  //     method: "POST",
  //     headers: {
  //       'Authorization':  `${token}`,
  //       'Content-Type': 'application/json'
  //     },
  //   };
  //   fetch('http://localhost:8080/game/room/public/enter/${room_name}', requestOptions)
  //     .then(response => response.json())
  //     .then(res => console.log(res));
  // };
  

    
  // if (created) {
  //   return (
  //     <Navigate to={format('/room/{0}', created)} state={{ "room_id":created, name }}/>
  //   )
  // }
      
  console.log(publicRooms);
  console.log(privateRooms);
  
  if(dataRoom != null){
    return <Room data={dataRoom} token={token} adminToken={adminToken}/>
  } 
  if(dataRoom1 != null){
    console.log ("aici" + dataRoom1)
    return <Room data={dataRoom1} token={token} adminToken={adminToken}/>
  }

 

    return (
      <>
      <div class="row">
      <div class="col-md-6">
        <div class = "container"  style={{ width: '20rem', maxHeight: 'rem', margin: '5%',
        backgroundColor: '#85BAA1', alignContent:'center', borderRadius:'2rem',  borderColor:'#d3bcc0'}}  >
        <div className="card shadow mb-1 mx-auto text-center" />
                  <h5 class="card-title"> My account </h5>
                  <h6 class="card-subtitle mb-2 text-muted"> Nume player: {name} </h6>
                  <h6 class="card-subtitle mb-2 text-muted"> Total puncte: </h6>
                   
              
                  
    </div>
    <div class = "container"  style={{ width: '20rem', maxHeight: '40rem', margin: '5%',
        backgroundColor: '#85BAA1', alignContent:'center', borderRadius:'2rem',  borderColor:'#d3bcc0'}}  >
       <div class="row" style ={{ alignContent: 'center', marginTop: '5%'}}> 
              
              <h5 class="card-title" style ={{marginTop:'7%'}}> Creare camera </h5>
                  <form class="row g-3" onSubmit={handleSubmit}>
                    <div class="col-md-12">
                      <input type="text" class="form-control" id="1" placeholder="Nume camera"  onChange={e => setRoomName(e.target.value)}/>
                    </div>
                    <div class="col-md-12">
                      <input type="text" class="form-control" id="1" placeholder="Numar maxim jucatori"  onChange={e => setMaxPlayers(e.target.value)}/>
                    </div>
                 
                    <div class="col-12">
                    <select id="inputState" class="form-select" onChange={e => setRoomType(e.target.value)}>
                        <option selected>Tip Camera..</option>
                        <option>PUBLIC_ROOM</option>
                        <option>PRIVATE_ROOM</option>
                     
                      </select>
                      {
                        roomType === "PRIVATE_ROOM" ? (
                          <div class="col-md-12" style ={{marginTop:'5%'}}>
                          <input type="password" class="form-control" id="1" placeholder="Parola"  onChange={e => setRoomPassword(e.target.value)}/>
                        </div>
                        ) :
                        (
                          <> </>
                        )
                      }
                    </div>
                    <div class="col-12">
                      <button type="submit" style={{ backgroundColor: '#85BAA1', alignContent:'center', borderColor:'#d3bcc0', borderRadius:"2rem", margin:'5%'}} > Create Room</button>
                    </div>
                  </form>
              </div>
            
        </div>
        </div>
        <div class="col-md-6">

        <div className="card mx-auto" style ={{backgroundColor: '#598392', padding: '15px', width: '35rem', marginTop: '3rem'}}>
        <h5 class="card-title" style ={{marginBottom:'20px'}}> Public Rooms </h5>
     
        {publicRooms.map((room) => {
            console.log(room);

            async function enterPublic(credentials, token) {
              const roomName = String(room.name);
              return fetch(format('http://localhost:8080/game/room/public/enter/{0}', roomName), {
                method: 'POST',
                headers: {
                  'Authorization': token,
                  'Content-Type': 'application/json'
                },
              })
             
              .then(data => 
                data.json());
              
              
            }
             
            const handleEnterPublic = async e => {
              
              const mesaj = await enterPublic({}, token)
              setDataRoom(mesaj)
              
              
            }
    
            return (
              <ol class="list-group ">
              <li class="list-group-item d-flex justify-content-between align-items-start" key = {room.id} style ={{ backgroundColor: '#85BAA1'}}>
                <div class="ms-2 me-auto">
                <div class="fw-bold" style ={{color: '#598392'}}> {room.name}</div>
                <b>Players Now:</b> {room.currentPlayerNum} <br></br>
                  <b>Max Players:</b> {room.maxPlayerNum}<br></br>
                  <b>Admin:</b> {room.adminName}<br></br>
                  <b>Game:</b> GuessMe<br></br>
                  <b>Status:</b> 
                </div>
                <button type="button" className="btn btn-dark" style ={{backgroundColor:'#631D76'}} onClick={() => {
                  
                  handleEnterPublic()
                  console.log("plm din piblc")}}>Enter</button>
              </li>
              </ol>
              

         

            );
        })}
        </div>
        </div>
        <div class="row">
        <div class="col-md-6">
          </div>
          <div class="col-md-6">
        <div className="card mx-auto" style ={{backgroundColor: '#598392', padding: '15px', width: '35rem', marginTop: '3rem'}}>
        <h5 class="card-title" style ={{marginBottom:'20px'}}> Private Rooms </h5>
        {privateRooms.map((room) => {
            console.log(room);
            

            async function enterPrivate(credentials, token) {
              const roomName = String(room.name);
              const roomPassAux = String(room.password);
              console.log(roomPassAux);
              return fetch(format('http://localhost:8080/game/room/private/enter/{0}/pass/{1}', roomName, roomPassAux), {
                method: 'POST',
                headers: {
                  'Authorization': token,
                  'Content-Type': 'application/json'
                },
                
              })
                .then(data => data.json())
             }
          
             const handleEnterPrivate = async e => {
              const mesaj1 = await enterPrivate({
             
              }, token)
              setDataRoom1(mesaj1)
              
            }

            return (
              <>
              <ol class="list-group ">
              <li class="list-group-item d-flex justify-content-between align-items-start" key = {room.id} style ={{ backgroundColor: '#85BAA1'}}>
                <div class="ms-2 me-auto">
                  <div class="fw-bold" style ={{color: '#598392'}}> {room.name}</div>
                  <b>Players Now:</b> {room.currentPlayerNum}<br></br>
                  <b>Max Players:</b> {room.maxPlayerNum}<br></br>
                  <b>Admin:</b> {room.adminName}<br></br>
                  <b>Game:</b> GuessMe<br></br>
                  <b>Status:</b> 
                </div>

                <div>
                <form >
                <label>
                Room Pass:
                <input type="text" onChange={e => setRoomPass(e.target.value)}/>
                </label>
                <button type="button" className="btn btn-dark" style ={{backgroundColor:'#631D76'}} onClick={() => {
                  console.log("plm din butonb priv")
                  handleEnterPrivate()}}>Enter</button>
                </form>
                </div>
              </li>
              </ol>
              </>
            );
        })} 
        </div>
        </div>
        </div>
        </div>
        
    </>
    );}
    export default Account;