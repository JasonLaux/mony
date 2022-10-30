import { ThemeProvider } from '@mui/material/styles';
import customTheme from '../themes/theme';
import { Outlet } from "react-router-dom"


export default function AppLayout() {

    return (
        <ThemeProvider theme={customTheme}>
            {/* <CssBaseline />
                <Navbar/> */}
            <main>
                <Outlet/>
            </main>
            <footer>
            
            </footer>
      </ThemeProvider>
    )
}