import React from "react"
import { Route, Redirect } from "react-router-dom"

// eslint-disable-next-line react/prop-types
const PrivateRoute = ({ component: Component, ...rest }) => {
  const isLoggedIn =
    sessionStorage.getItem("token") &&
    sessionStorage.getItem("token").length > 10
  return (
    <Route
      // eslint-disable-next-line react/jsx-props-no-spreading
      {...rest}
      render={(props) =>
        // eslint-disable-next-line react/jsx-props-no-spreading
        isLoggedIn ? <Component {...props} /> : <Redirect to="/" />
      }
    />
  )
}

export default PrivateRoute
