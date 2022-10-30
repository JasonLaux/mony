import { ThemeProvider } from '@mui/material/styles';
import customTheme from '../themes/theme';
import { Outlet } from "react-router-dom"
import Navigator from '../components/Navigator';
import { CssBaseline } from '@mui/material';


export default function PortalLayout() {

    return (
        <ThemeProvider theme={customTheme}>
            <CssBaseline />
                <Navigator/>
            <main>
                <Outlet/>
            </main>
            <footer>
            
            </footer>
      </ThemeProvider>
    )
}