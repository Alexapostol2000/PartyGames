import React, {useState, useEffect} from 'react'
import { Button } from "bootstrap";
import { Navigate } from "react-router";
import {format} from 'react-string-format'
import { Link } from 'react-router-dom';
import { useParams } from 'react-router-dom';
import { useLocation } from 'react-router-dom';
import Description from './Description';


const Game = ({ setToken,  token, adminToken, data }) => {

  const [word, setWord] = useState('')
  const [toggle, setToggle] = useState(false)
  const location = useLocation()
  const  { from } = location.state
  console.log(from)
  const roomName = String(from.name);
  
  
  // info camera
  // done: lista players
  // buton start game
  // buton exit room
    // to do: change into room name     

    async function startRound(credentials, token) {
    
    
      console.log(roomName)
      // if (adminToken === token) {
        // console.log("sunt admin")
        return fetch(format('http://localhost:8080/game/play/{0}', roomName), {
        method: 'POST',
        headers: {
          'Authorization': token,
          'Content-Type': 'application/json'
        },
          })
     
          .then(data => 
         data.text());
      //} 
      
      
      
    }
     
    const handleRound = async e => {
      
      const mesaj = await startRound({}, token)
      setWord(mesaj)
    }

   
    return (
        <>
        <div class = "container"  style={{ width: '20rem', maxHeight: 'rem', margin: '5%',
        backgroundColor: '#85BAA1', alignContent:'center', borderRadius:'2rem',  borderColor:'#d3bcc0'}}  >
        <div className="card shadow mb-1 mx-auto text-center" />
                  <h5 class="card-title"> <Description roomName={roomName} /> </h5>
                  
    </div>
    <div class = "container"  style={{ width: '20rem', maxHeight: '40rem', margin: '5%',
        backgroundColor: '#85BAA1', alignContent:'center', borderRadius:'2rem',  borderColor:'#d3bcc0'}}  >
       <div class="row" style ={{ alignContent: 'center', marginTop: '5%'}}> 
    </div>
    </div>
        <button type="button" className="btn btn-dark" style ={{backgroundColor:'#631D76'}} onClick={() => {
                  
                  handleRound()
                  console.log("start game")}}>Start Round</button>
       
        </>

    );}

export default Game;