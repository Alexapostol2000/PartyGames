import { useState } from 'react';

export default function useScore() {
  const getScore = () => {
    const tokenString = sessionStorage.getItem('totalScore');
    const userToken = JSON.parse(tokenString);
    return userToken;
  };

  const [score, setScore] = useState(getScore());

  const saveScore = userToken => {
   // console.log('token ' + userToken);
    sessionStorage.setItem('totalScore', JSON.stringify(userToken));
    setScore(userToken);
  };

  return {
    setScore: saveScore,
    score
  }

}