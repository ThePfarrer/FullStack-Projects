import { makeStyles } from "@mui/styles";
import { Card, CardContent, CardMedia, Typography } from "@mui/material";
import logo from "./../logo.svg";

const useStyles = makeStyles((theme) => ({
  card: {
    maxWidth: 600,
    margin: "auto",
    marginTop: theme.spacing(5),
  },
  title: {
    padding: `${theme.spacing(3)}px ${theme.spacing(2.5)}px ${theme.spacing(
      2
    )}px`,
    color: theme.palette.openTitle,
  },
  media: {
    minHeight: 400,
  },
}));

const Home = () => {
    const classes = useStyles()
    return(
        <Card className={classes.card}>
            <Typography variant="h6" className={classes.title}>
                Home Page
            </Typography>
            <CardMedia className={classes.media} image={logo} title=""/>
            <CardContent>
                <Typography variant="body2" component="p">
                    Welcome to the Scala React Postgres Skeleton home page.
                </Typography>
            </CardContent>
        </Card>
    )
}

export default Home