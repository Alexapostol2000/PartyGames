import logo from './logo.svg';
import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import Login from './Login';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Navbar from './Navbar';
import Home from './Home';
import Register from './Register';
import Account from './Account';
import useToken from './hooks/useToken';
import useName from './hooks/useName';
import Logout from './Logout';
function App() {
  const { token, setToken } = useToken();
  const { name, setName} = useName();

  return (
    <div className="App">
      <Navbar token={token}/>
      <div className="content">
      <Routes>
        <Route exact path="/" element = {<Home />}/>
        <Route path="/login" element = {<Login setToken={setToken} token={token}  setName={setName}/>}/>
        <Route path="/register" element = {<Register/>}/>
        <Route path="/account" element = {<Account setToken={setToken} token={token}  name={name}/>}/>
        <Route path="/logout" element = {<Logout setToken={setToken} token={token}/>}/>

      </Routes>
      </div>
    </div>
  );
}

export default App;
