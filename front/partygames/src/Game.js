import React, {useState, useEffect} from 'react'
import { Button } from "bootstrap";
import { Navigate } from "react-router";
import {format} from 'react-string-format'
import { Link } from 'react-router-dom';
import { useParams } from 'react-router-dom';
import { useLocation } from 'react-router-dom';
import Description from './Description';
import img from './emoji.png'

const Game = ({ setToken,  token, adminToken, data }) => {

  const [word, setWord] = useState('')
  const [solution, setSolution] = useState("")
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
     
          .then(data => {
            window.location.reload(false);
            data.text();
          }
         );
      //} 
      
      
      
    }
     
    const handleRound = async e => {
      
      const mesaj = await startRound({}, token)
      setWord(mesaj)
    }

    async function solutionWord() {

        return fetch(format('http://localhost:8080/game/solve/{0}/word/{1}', roomName, solution), {
          method: 'POST',
          headers: {
            'Authorization': token,
            'Content-Type': 'text/plain'
          },
          body: solution
        })
          .then(data => data.json())
       }
       const handleSolution = async e => {
        e.preventDefault();
        const mesaj = await solutionWord({
          solution
        })
        e.target.reset();

     
      }

    return (
        <>
        {/* <div class = "container"  style={{ width: '20rem', maxHeight: 'rem', margin: '5%',
        backgroundColor: '#85BAA1', alignContent:'center', borderRadius:'2rem',  borderColor:'#d3bcc0'}}  >
        <div className="card shadow mb-1 mx-auto text-center" />
                  <h5 class="card-title"> <Description roomName={roomName} /> </h5>
                  
    </div>
    <div class = "container"  style={{ width: '20rem', maxHeight: '40rem', margin: '5%',
        backgroundColor: '#85BAA1', alignContent:'center', borderRadius:'2rem',  borderColor:'#d3bcc0'}}  >
       <div class="row" style ={{ alignContent: 'center', marginTop: '5%'}}> 
    </div>
    </div>
    <div className="card-body text-center">
    <form onSubmit={handleSolution}>
      <div className="form-group">
        <label for="exampleInputEmail1">Solution</label>
        <input type="text" className="form-control" placeholder="Enter solution" onChange={e => setSolution(e.target.value)}/>
        
      </div>
      <button type="submit" className="btn btn-dark" style ={{backgroundColor:'#631D76'}}>Submit</button>
      </form>
      </div>

        <button type="button" className="btn btn-dark" style ={{backgroundColor:'#631D76'}} onClick={() => {
                  
                  handleRound()
                  console.log("start game")}}>Start Round</button> */}

                  <div class="row">
                    <div class="col md-4">

                    </div>
                    <div class="col md-4">
                    <div class="card" style={{display: 'flex',  justifyContent:'center', alignItems:'center', marginTop:'5rem', backgroundColor: '#85BAA1'}}>
                            <img className="img" src={img} style ={{maxWidth:'20rem', marginTop:'3rem'}} />
                      <div class="card-body" > 
                        <h5 class="card-title"> Guess me! </h5>
                        <p class="card-text" style ={{ backgroundColor: '#598392', padding:'2 rem',border: '2px solid black',borderRadius:"1rem", padding:"2%"}} > <Description roomName={roomName} /></p>
                      </div>
                      <ul class="list-group list-group-flush"  style ={{border: '2px solid black', marginBottom:"3rem", borderRadius:"1rem"}}> 
                        <li class="list-group-item" style ={{ backgroundColor: '#598392'}}>
                           <form onSubmit={handleSolution}>
                          <div className="form-group" >
                       
                            <input type="text" className="form-control" placeholder="Enter solution" onChange={e => setSolution(e.target.value)}/>
                            
                          </div>
                          <button type="submit" className="btn btn-dark" style ={{backgroundColor:'#631D76', marginTop:'1rem'}}>Submit</button>
                          </form>
                        </li>
                        <li class="list-group-item" style ={{ backgroundColor: '#598392'}}> 
                         <button type="button" className="btn btn-dark" style ={{backgroundColor:'#631D76'}} onClick={() => {
                              handleRound()
                              console.log("start game")}}>New Round</button></li>
              
                       </ul>
  

</div> 
                      </div>
                      <div class="col md-4">
                      
                      </div>
                  </div>
        
        </>

    );}

export default Game;