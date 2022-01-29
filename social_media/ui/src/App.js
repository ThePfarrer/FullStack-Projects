import { useState, useEffect } from "react";
import MainRouter from "./MainRouter";
import { BrowserRouter } from "react-router-dom";
// import logo from "./logo.svg";
import "./App.css";
import { ThemeProvider } from "@mui/material/styles";
import theme from "./theme";

const App = () => {
  const [data, setData] = useState(null);

  useEffect(() => {
    fetch("/api/persons/13")
      .then((res) => res.json())
      .then((data) => setData(data.name));
  }, []);

  return (
    <BrowserRouter>
      <ThemeProvider theme={theme}>
        <MainRouter />
      </ThemeProvider>
    </BrowserRouter>
  );
  // <div className="App">
  //   <header className="App-header">
  //     <img src={logo} className="App-logo" alt="logo" />
  //     <p>{!data ? "Loading..." : data}</p>
  //   </header>
  // </div>
};

export default App;
