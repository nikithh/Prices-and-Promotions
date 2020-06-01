// vendor login
import {
  Button,
  InputAdornment,
  TextField,
  Typography,
} from "@material-ui/core"
import Lock from "@material-ui/icons/Lock"
import PersonIcon from "@material-ui/icons/Person"
import PropTypes from "prop-types"
import React, { Component } from "react"
import LockOpenIcon from "@material-ui/icons/LockOpen"
import { connect } from "react-redux"
import { vendorLogin } from "../../redux/actions/VendorActions"
import Message from "./Message"

class VendorLogin extends Component {
  constructor(props) {
    super(props)
    this.state = {
      userCredentials: {
        username: "",
        password: "",
      },
      error: {
        usernameError: false,
        usernameErrorMsg: "",
        passwordError: false,
        passwordErrorMsg: "",
      },
    }
    this.handleChange = this.handleChange.bind(this)
    this.handleSubmitLogin = this.handleSubmitLogin.bind(this)
    this.handleSubmitSignUp = this.handleSubmitSignUp.bind(this)
  }

  isValidusername = () => {
    // console.log('entered valid username')
    const { userCredentials } = this.state
    const { error } = this.state
    if (userCredentials.username === "") {
      error.usernameError = true
      error.usernameErrorMsg = "please fill username"
      this.setState({ error })
      return false
    }
    error.usernameError = false
    error.usernameErrorMsg = ""
    this.setState({ error })
    return true
  }

  isValidPassword = () => {
    const { userCredentials } = this.state
    const { error } = this.state
    if (userCredentials.password === "") {
      error.passwordError = true
      error.passwordErrorMsg = "please fill password"
      this.setState({ error })
      return false
    }
    error.passwordError = false
    error.passwordErrorMsg = ""
    this.setState({ error })
    return true
  }

  handleChange(e) {
    const { name, value } = e.target
    const { userCredentials } = this.state
    userCredentials[name] = value
    this.setState({ userCredentials })
  }

  handleSubmitLogin(e) {
    e.preventDefault()
    const { userCredentials } = this.state
    const { vendorLogin: vendorLoginAlt } = this.props
    if (this.isValidusername() && this.isValidPassword()) {
      vendorLoginAlt({ ...userCredentials }) // thunk action
    }
  }

  handleSubmitSignUp() {
    const { history } = this.props
    history.push("/vendor/reg")
  }

  isAuthenticated() {
    this.token = sessionStorage.getItem("token")
    return this.token && this.token.length > 10
  }

  render() {
    const { loginStatus, history } = this.props
    const { error, userCredentials } = this.state
    return (
      <>
        {sessionStorage.getItem("userType") === "vendor" &&
        loginStatus.success ? (
          history.push("/vendor/addproduct")
        ) : (
          <div className="box-container-login">
            <div className="joint-form" id="login-joint-form-vendor">
              <div className="login-full">
                {userCredentials.password.length <= 0 ? (
                  <div className="login-help-block">
                    <Lock className="login-icon" />
                  </div>
                ) : (
                  <div className="login-help-block">
                    <LockOpenIcon className="login-icon" />
                  </div>
                )}
                <Typography className="card-header" variant="h4">
                  Vendor Login
                </Typography>
                <form className="{classes.form}" noValidate>
                  <TextField
                    variant="outlined"
                    margin="normal"
                    required
                    fullWidth
                    error={error.usernameError}
                    helperText={error.usernameErrorMsg}
                    id="username-vendor"
                    label="User Name"
                    name="username"
                    autoComplete="username"
                    onChange={this.handleChange}
                    autoFocus
                    InputProps={{
                      startAdornment: (
                        <InputAdornment position="start">
                          <PersonIcon
                            color="primary"
                            borderColor="primary.main"
                            borderRight={1}
                          />
                        </InputAdornment>
                      ),
                    }}
                  />
                  <TextField
                    variant="outlined"
                    margin="normal"
                    required
                    fullWidth
                    error={error.passwordError}
                    helperText={error.passwordErrorMsg}
                    name="password"
                    label="Password"
                    type="password"
                    id="password-vendor"
                    onChange={this.handleChange}
                    autoComplete="current-password"
                    InputProps={{
                      startAdornment: (
                        <InputAdornment position="start">
                          <Lock
                            color="primary"
                            borderColor="primary.main"
                            borderRight={1}
                          />
                        </InputAdornment>
                      ),
                    }}
                  />
                  <Button
                    type="button"
                    fullWidth
                    variant="contained"
                    color="primary"
                    className="{classes.submit} submit-pad"
                    id="vendor-login-btn"
                    onClick={this.handleSubmitLogin}
                  >
                    {/* {t("header.logIn")} */}
                    Login
                  </Button>
                  <Button
                    type="button"
                    fullWidth
                    variant="contained"
                    color="primary"
                    className="{classes.submit} submit-pad"
                    id="vendor-signup-btn"
                    onClick={this.handleSubmitSignUp}
                  >
                    Not a Vendor? Sign Up
                  </Button>
                </form>
              </div>
            </div>
            <Message />
          </div>
        )}
      </>
    )
  }
}

VendorLogin.propTypes = {
  loginStatus: PropTypes.shape.isRequired,
  vendorLogin: PropTypes.func.isRequired,
  history: PropTypes.shape.isRequired,
}

const stateAsProps = (store) => {
  if ("loginStatus" in store.VendorReducer) {
    return {
      loginStatus: store.VendorReducer.loginStatus,
    }
  }
  return { loginStatus: { errorMsg: "" } }
}

export default connect(stateAsProps, { vendorLogin })(VendorLogin)
