import { AppBar, Button, IconButton, Toolbar, Typography } from "@mui/material";
import { Link, withRouter } from "react-router-dom";
import HomeIcon from "@mui/icons-material/Home";
import auth from "../auth/auth-helper";

const isActive = (history, path) => {
  if (history.location.pathname === path) return { color: "#ff4081" };
  return { color: "#ffffff" };
};

const Menu = withRouter(({ history }) => (
  <AppBar position="static">
    <Toolbar>
      <Typography variant="h6" color="inherit">
        SPR Skeleton
      </Typography>
      <Link to="/">
        <IconButton aria-label="Home" style={isActive(history, "/")}>
          <HomeIcon />
        </IconButton>
      </Link>
      <Link to="/users">
        <Button style={isActive(history, "/users")}>Users</Button>
      </Link>
      {!auth.isAuthenticated() && (
        <span>
          <Link to="/signup">
            <Button style={isActive(history, "/signup")}> Sign Up</Button>
          </Link>
          <Link to="/signin">
            <Button style={isActive(history, "/signin")}> Sign In</Button>
          </Link>
        </span>
      )}
      {auth.isAuthenticated() && (
        <span>
          <Link to={"/user/" + auth.isAuthenticated().person.id}>
            <Button
              style={isActive(
                history,
                "/user/" + auth.isAuthenticated().person.id
              )}
            >
              {" "}
              My Profile
            </Button>
          </Link>
          <Button
            color="inherit"
            onClick={() => {
              auth.clearJWT(() => history.push("/"));
            }}
            style={isActive(history, "/signin")}
          >
            {" "}
            Sign Out
          </Button>
        </span>
      )}
    </Toolbar>
  </AppBar>
));

export default Menu;
