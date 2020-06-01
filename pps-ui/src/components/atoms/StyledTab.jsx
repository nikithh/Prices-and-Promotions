import React from "react"
import { withStyles } from "@material-ui/core/styles"
import Tab from "@material-ui/core/Tab"

// The `withStyles()` higher-order component is injecting a `classes`
// prop that is used by the `Button` component.
const StyledTab = withStyles({
  root: {
    flexGrow: 1,
    fontFamily: "'Open Sans Condensed', sans-serif",
  },
})(Tab)

export default function ClassesShorthand() {
  return <StyledTab />
}
