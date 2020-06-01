import React, { Component } from "react"

import connect from "react-redux/es/connect/connect"
import { Button, AppBar, Toolbar } from "@material-ui/core"
import MenuIcon from "@material-ui/icons/Menu"
import AccountCircle from "@material-ui/icons/AccountCircle"
import { Link } from "react-router-dom"
import { vendorlogout } from "../../redux/actions/VendorActions"
import Message from "./Message"

/* eslint class-methods-use-this: ["error", { "exceptMethods": ["handleSubmit","handleLogout"] }] */
class Home extends Component {
  constructor(props) {
    super(props)
    this.handleLogout = this.handleLogout.bind(this)
  }

  handleLogout() {
    sessionStorage.removeItem("token")
    window.location.href = "./"
  }

  render() {
    return (
      <div>
        <AppBar position="static" elevation={0}>
          <Toolbar>
            <Link className="button-link" to="./addproduct">
              <Button
                color="secondary"
                className="{classes.submit}"
                onClick={this.handleSubmit}
                id="add-prod-vendor"
                startIcon={<MenuIcon />}
              >
                addproduct
              </Button>
            </Link>
            <Link className="button-link" to="./updateprice">
              <Button
                color="secondary"
                className="{classes.submit}"
                id="update-prod-vendor"
                startIcon={<MenuIcon />}
              >
                Update Price/quantity
              </Button>
            </Link>
            <div>
              <Button
                color="inherit"
                className="{classes.submit}"
                onClick={this.handleLogout}
                id="logout-vendor"
                startIcon={<AccountCircle />}
              >
                logout
              </Button>
            </div>
          </Toolbar>
        </AppBar>

        <Message />
      </div>
    )
  }
}
const stateAsProps = (store) => ({
  msg: store.VendorReducer.msg,
})
const actionsAsProps = {
  vendorlogout,
}
export default connect(stateAsProps, actionsAsProps)(Home)
