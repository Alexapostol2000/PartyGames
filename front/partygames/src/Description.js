import React, {useState, useEffect} from 'react';
import {format} from 'react-string-format'

const Description = ({roomName}) => {

    const [word, setWord] = useState()
    const [toggle, setToggle] = useState(false)
    console.log(roomName)
    async function getWord() {
        return fetch(format('http://localhost:8080/game/descriptionword/{0}', roomName), {
          method: 'GET',
          headers: {
            // 'Content-Type': 'application/json',
           // 'Accept': 'text/plain;charset=UTF-8'
          },
        })
       .then(function(response){  return response.text()}) 
      }
    const getGuessWord = async e => {
        const mesaj = await getWord()
        setWord(mesaj)
    }
    useEffect(() => {
      //  setTimeout(() => setToggle((prevToggle) => !prevToggle), 3000);
        getGuessWord()}, [])
    console.log(word)
    return (
        <div>
        {word}
        </div>
    
    );}
    export default Description;