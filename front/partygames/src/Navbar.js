const Navbar = ({ token }) => {
    return ( 
        <>
        {
            (token) ? (
                <nav className="navbar" >
                        <h1 style = {{color: "#85BAA1", fontFamily: "fantasy"}}> PartyGames </h1>
                        <div className="links">
                            <a href="/" style={{
                                color: "white",
                                backgroundColor: '#85BAA1',
                                borderRadius: '8px'
                            }} > Home </a>
                             <a href="/account" style={{
                                color: "white",
                                backgroundColor: '#85BAA1',
                                borderRadius: '8px'
                            }} > My account </a>
                           
                            <a href="/login" style={{
                                color: "white",
                                backgroundColor: '#85BAA1',
                                borderRadius: '8px'
                            }} > Login </a>
                             
                             <a href="/logout" style={{
                                color: "white",
                                backgroundColor: '#85BAA1',
                                borderRadius: '8px'
                            }} > Logout </a>
                                            
                            
                            
                        </div>
                    </nav>
            ) : (
                <nav className="navbar" >
                        <h1 style = {{color: "#85BAA1", fontFamily: "fantasy"}}> PartyGames </h1>
                        <div className="links">
                            <a href="/" style={{
                                color: "white",
                                backgroundColor: '#85BAA1',
                                borderRadius: '8px'
                            }} > Home </a>
                            <a href="/login" style={{
                                color: "white",
                                backgroundColor: '#85BAA1',
                                borderRadius: '8px'
                            }} > Login </a>
                              <a href="/register" style={{
                                color: "white",
                                backgroundColor: '#85BAA1',
                                borderRadius: '8px'
                            }} > Register </a>
                           
                                            
                            
                            
                        </div>
                    </nav>
            )
        }
        </>

    )}
export default Navbar;