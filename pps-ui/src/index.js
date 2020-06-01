import {
  createMuiTheme,
  MuiThemeProvider,
  responsiveFontSizes,
} from "@material-ui/core/styles"
import React, { Suspense } from "react"
import ReactDOM from "react-dom"

import indigo from "@material-ui/core/colors/indigo"
import amber from "@material-ui/core/colors/amber"
import App from "./App"
import "./i18n"
import "./css/index.css"

let theme = createMuiTheme({
  palette: {
    primary: indigo,
    secondary: amber,
  },
  // typography: {
  //     fontFamily: "'Open Sans Condensed', sans-serif, 'Open Sans', sans-serif",
  // },
})

theme = responsiveFontSizes(theme)
ReactDOM.render(
  <Suspense fallback={<div>Loading</div>}>
    <MuiThemeProvider theme={theme}>
      <App />
    </MuiThemeProvider>
  </Suspense>,

  document.getElementById("root")
)
