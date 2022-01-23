import React, {useState, useEffect} from 'react'
import { Button } from "bootstrap";
import { Navigate } from "react-router";
import {format} from 'react-string-format'
import { Link } from 'react-router-dom';
import { useParams } from 'react-router-dom';


const Room = ({ setToken, token }) => {
  const [room_name, setRoomName] = useState();
  const [roomType, setRoomType] = useState();
  const [maxPlayers, setMaxPlayers] = useState();
  const [roomPlayers, setRoomPlayers] = useState([]);
  
  // info camera
  // to do: lista players
  // buton start game
  // buton exit room
    // to do: change into room name     

    
    return (
        <>
        <h1 style ={{margin:'10%'}}> Welcome to partyyyy! </h1>
        </>

    );}

export default Room;