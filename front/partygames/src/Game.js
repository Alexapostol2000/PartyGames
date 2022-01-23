import React, {useState, useEffect} from 'react'
import { Button } from "bootstrap";
import { Navigate } from "react-router";
import {format} from 'react-string-format'
import { Link } from 'react-router-dom';
import { useParams } from 'react-router-dom';
import { useLocation } from 'react-router-dom';


const Game = ({ setToken, setAdminToken, token, adminToken, data }) => {

  const location = useLocation()
  const  { from } = location.state
  console.log(from)
  
  
  // info camera
  // done: lista players
  // buton start game
  // buton exit room
    // to do: change into room name     

    async function startRound(credentials, token) {
    
      const roomName = String(from.name);
      console.log(roomName)
      if (adminToken === token) {
        console.log("sunt admin")
        return fetch(format('http://localhost:8080/game/play/{0}', roomName), {
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
     
    const handleRound = async e => {
      
      const mesaj = await startRound({}, token)
       
    }

    

    return (
        <>
        
        <button type="button" className="btn btn-dark" style ={{backgroundColor:'#631D76'}} onClick={() => {
                  
                  handleRound()
                  console.log("start game")}}>Start Round</button>
       
        </>

    );}

export default Game;