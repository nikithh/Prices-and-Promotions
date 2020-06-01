import React from "react"
import { BrowserRouter as Router, Switch, Redirect } from "react-router-dom"
import PrivateRoute from "../utils/privateRoute"
import SelectProduct from "./SelectProduct"
import AssignToZone from "./AssignToZone"
import ViewAssignedZones from "./ViewAssignedZones"

const AssignToZoneRouter = () => {
  return (
    <Router>
      <Redirect to="/selectproduct" />
      <Switch>
        <PrivateRoute exact path="/selectproduct" component={SelectProduct} />
        <PrivateRoute exact path="/assigntozone" component={AssignToZone} />

        <PrivateRoute
          exact
          path="/viewassignedzones"
          component={ViewAssignedZones}
        />
      </Switch>
    </Router>
  )
}
export default AssignToZoneRouter
