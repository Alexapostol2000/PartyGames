import { useState } from 'react';

export default function useName() {
  const getName = () => {
    const tokenString = sessionStorage.getItem('name');
    const userToken = JSON.parse(tokenString);
    return userToken;
  };

  const [name, setName] = useState(getName());

  const saveName = userToken => {
    console.log('token ' + userToken);
    sessionStorage.setItem('name', JSON.stringify(userToken));
    setName(userToken);
  };

  return {
    setName: saveName,
    name
  }

}