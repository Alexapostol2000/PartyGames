import { useEffect, useState } from "react";
import {format} from 'react-string-format'

const EndDashboard = ({room, token}) => {

    const [usersScore, setUsersScore] = useState([]);
    console.log(room);
    

    async function getScore() {
        return fetch(format('http://localhost:8080/game/score/{0}', room), {
            method: 'GET',
            headers: {
              'Authorization': token,
              'Content-Type': 'application/json'
            },
          })
         
          .then(data => 
              data.json()
          );
      }
    const handleGetScore = async e => {
        const mesaj = await getScore()
        console.log(mesaj)
        setUsersScore(mesaj)
    }
   
    useEffect(() => {
        handleGetScore()
    }, [])
    
    return (
        <>
        <div class = "container"  style={{ width: '20rem', maxHeight: 'rem', marginLeft: '40%', marginTop:'7%', marginBottom:'2%',
                backgroundColor: '#85BAA1', alignContent: 'center', borderRadius: '2rem', borderColor: '#d3bcc0', justifyContent: 'center',
                padding:'2rem'}}  >
        <div className="card shadow mb-1 mx-auto text-center" style ={{justifyContent:'center'}} />
                  <h5 class="card-title"> Camera: {room} </h5>
                  <h6 class="card-subtitle mb-2 text-muted"> Numar utilizatori: {usersScore.length} </h6>
                {usersScore.map((user) => {
                    return (
                        <div class="row">
                            <div class="col md-6">
                                <div class="fw-bold" style={{ color: '#598392' }}> Playeri </div>
                                <ol class="list-group ">
                                    <li class="list-group-item d-flex justify-content-between align-items-start" key={user.id} style={{ backgroundColor: '#85BAA1' }}>
                                        
                                        <div class="fw-bold" style={{ color: '#598392' }}> {user.userName}</div>
                                    </li>
                                </ol>
                            </div>
                            <div class="col md-6">
                                <div class="fw-bold" style={{ color: '#598392' }}> Score </div>
                                <ol class="list-group ">
                                    <li class="list-group-item d-flex justify-content-between align-items-start" key={user.id} style={{ backgroundColor: '#85BAA1' }}>
                                    
                                        <div class="fw-bold" style={{ color: '#598392' }}> {user.score}</div>
                                    </li>
                                </ol>
                            </div>
                        </div>
                    )
                })}
      
            </div>
        </>
    
                    );}
export default EndDashboard;
    
