import { useState } from 'react';

export default function useAdminToken() {
  const getToken = () => {
    const tokenString = sessionStorage.getItem('token');
    const userToken = JSON.parse(tokenString);
    return userToken;
  };

  const [token, setAdminToken] = useState(getToken());

  const saveToken = userToken => {
    sessionStorage.setItem('token', JSON.stringify(userToken));
    setAdminToken(userToken);
  };
  console.log("use token:" + token)
  return {
    setAdminToken: saveToken,
    token
  }

}