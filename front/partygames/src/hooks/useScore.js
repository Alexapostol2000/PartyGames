import { useState } from 'react';

export default function useScore() {
  const getScore = () => {
    const tokenString = sessionStorage.getItem('totalScore');
    const userScore = JSON.parse(tokenString);
    return userScore;
  };

  const [totalScore, setScore] = useState(getScore());

  const saveScore = userToken => {
   // console.log('token ' + userToken);
    sessionStorage.setItem('totalScore', JSON.stringify(userScore));
    setName(userScore);
  };

  return {
    setScore: saveScore,
    totalScore
  }

}