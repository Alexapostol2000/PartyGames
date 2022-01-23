import React, {useState, useEffect} from 'react'
import { Button } from "bootstrap";
import { Navigate } from "react-router";
import {format} from 'react-string-format'
import { Link } from 'react-router-dom';
import { useParams } from 'react-router-dom';



const Room = ({ setToken, token, adminToken, data }) => {
  const [dataRoom, setDataRoom] = useState(data);
  const [dataGame, setDataGame] = useState(data);
  
  // info camera
  // done: lista players
  // buton start game
  // buton exit room
    // to do: change into room name     

    console.log(data)
    console.log(dataRoom.players)
    const roomPlayers = dataRoom.players;
    const games = dataRoom.game;
    console.log(games)
    

    async function deletePlayer(credentials, token) {
      const roomName = String(data.name);
      if (adminToken === token) {
        console.log("sunt admin")
        return fetch(format('http://localhost:8080/game/room/admin/leave/{0}', roomName), {
        method: 'DELETE',
        headers: {
          'Authorization': token,
          'Content-Type': 'application/json'
        },
          })
     
          .then(data => 
          data.json());
      } else {
        return fetch(format('http://localhost:8080/game/room/user/leave/{0}', roomName), {
          method: 'DELETE',
          headers: {
            'Authorization': token,
            'Content-Type': 'application/json'
          },
        })
       
        .then(data => 
          data.json());
      }
      
      
      
    }
     
    const handleLeave = async e => {
      
      const mesaj = await deletePlayer({}, token)
      setDataRoom(mesaj)  
    }

    async function startGame(credentials, token) {
    
      const roomName = String(data.name);
      if (adminToken === token) {
        console.log("sunt admin")
        return fetch(format('http://localhost:8080/game/start/{0}', roomName), {
        method: 'POST',
        headers: {
          'Authorization': token,
          'Content-Type': 'application/json'
        },
          })
     
          .then(data => 
          data.json());
      } 
      
      
      
    }
     
    const handleStart = async e => {
      
      const mesaj = await startGame({}, token)
      setDataGame(mesaj)  
    }


    

    return (
        <>
        <div class="row">
        <div class="col-md-6">
        <div class = "container"  style={{ width: '20rem', maxHeight: 'rem', margin: '5%',
          backgroundColor: '#85BAA1', alignContent:'center', borderRadius:'2rem',  borderColor:'#d3bcc0'}}  >
        <div className="card shadow mb-1 mx-auto text-center" />
                  <h5 class="card-title"> Nume camera: {data.name} </h5>
                  <h6 class="card-subtitle mb-2 text-muted"> Lista utilizatori: {roomPlayers.length} </h6>
                   {roomPlayers.map((player) =>{
                     return(
                        <ol class="list-group ">
                        <li class="list-group-item d-flex justify-content-between align-items-start" key = {player.id} style ={{ backgroundColor: '#85BAA1'}}>
                        
                          <div class="fw-bold" style ={{color: '#598392'}}> {player.name}</div>
                          
                        </li>
                        </ol>
                     )
                     })
                    }
           <button type="button" className="btn btn-dark" style ={{backgroundColor:'#631D76'}} onClick={() => {
                  
                  handleLeave()
                  console.log("leave room")}}>Leave Room</button>

        </div>
        <button type="button" className="btn btn-dark" style ={{backgroundColor:'#631D76'}} onClick={() => {
                  
                  handleStart()
                  console.log(dataGame)}}>Create Game</button>
        <Link to="/game" state={{ from: dataGame }} className="btn btn-dark" type="button" >Go to game!</Link>
        </div>                             
        </div>
        </>

    );}

export default Room;