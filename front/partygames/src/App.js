import logo from './logo.svg';
import './App.css';
import Login from './Login';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Navbar from './Navbar';
import Home from './Home';

function App() {
  return (
    <div className="App">
      <Navbar/>
      <div className="content">
      <Routes>
        <Route exact path="/" element = {<Home />}/>
        <Route path="/login" element = {<Login/>}/>
      </Routes>
      </div>
    </div>
  );
}

export default App;
