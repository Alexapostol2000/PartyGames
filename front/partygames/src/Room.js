import React, {useState, useEffect} from 'react'
import { Button } from "bootstrap";
import { Navigate } from "react-router";
import {format} from 'react-string-format'
import { Link } from 'react-router-dom';
import { useParams } from 'react-router-dom';


const Room = ({ setToken, token, data }) => {
  const [room_name, setRoomName] = useState();
  const [roomType, setRoomType] = useState();
  const [maxPlayers, setMaxPlayers] = useState();
  const [roomPlayers, setRoomPlayers] = useState([]);
  
  // info camera
  // to do: lista players
  // buton start game
  // buton exit room
    // to do: change into room name     

    console.log(data)
    return (
        <>
        <div class="row">
      <div class="col-md-6">
        <div class = "container"  style={{ width: '20rem', maxHeight: 'rem', margin: '5%',
        backgroundColor: '#85BAA1', alignContent:'center', borderRadius:'2rem',  borderColor:'#d3bcc0'}}  >
        <div className="card shadow mb-1 mx-auto text-center" />
                  <h5 class="card-title"> Nume camera </h5>
                  <h6 class="card-subtitle mb-2 text-muted"> Lista utilizatori  </h6>
                 
                   
              
                  
            </div>
      </div>
      </div>
        </>

    );}

export default Room;