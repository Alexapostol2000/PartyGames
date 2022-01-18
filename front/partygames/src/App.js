import logo from './logo.svg';
import './App.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import Login from './Login';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Navbar from './Navbar';
import Home from './Home';
import Register from './Register';
function App() {
  return (
    <div className="App">
      <Navbar/>
      <div className="content">
      <Routes>
        <Route exact path="/" element = {<Home />}/>
        <Route path="/login" element = {<Login/>}/>
        <Route path="/register" element = {<Register/>}/>
      </Routes>
      </div>
    </div>
  );
}

export default App;
