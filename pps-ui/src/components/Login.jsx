import {
  Button,
  InputAdornment,
  TextField,
  Typography,
} from "@material-ui/core"
import Lock from "@material-ui/icons/Lock"
import LockOpenIcon from "@material-ui/icons/LockOpen"
import PersonIcon from "@material-ui/icons/Person"
import i18n from "i18next"
import md5 from "md5"
import PropTypes from "prop-types"
import React, { Component } from "react"
import { withTranslation } from "react-i18next"
import { connect } from "react-redux"
import { login } from "../redux/actions/RetailerActions"
import Message from "./utils/Message"

class Login extends Component {
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
    this.handleSubmit = this.handleSubmit.bind(this)
    sessionStorage.setItem("currency", "USD")
  }

  isValidUsername = () => {
    const { userCredentials, error } = this.state
    if (userCredentials.username === "") {
      error.usernameError = true
      error.usernameErrorMsg = i18n.t("please fill username")
      this.setState({ error })
      return false
    }
    error.usernameError = false
    error.usernameErrorMsg = ""
    this.setState({ error })
    return true
  }

  isValidPassword = () => {
    const { userCredentials, error } = this.state
    if (userCredentials.password === "") {
      error.passwordError = true
      error.passwordErrorMsg = i18n.t("please fill password")
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

  handleSubmit(e) {
    e.preventDefault()
    const u = this.isValidUsername()
    const p = this.isValidPassword()
    const { login: loginAlt } = this.props
    if (u && p) {
      const userObject = this.state
      userObject.userCredentials.password = md5(
        userObject.userCredentials.password
      )

      loginAlt({ ...userObject.userCredentials }) // thunk action
    }
  }

  isAuthenticated() {
    this.token = sessionStorage.getItem("token")
    return this.token && this.token.length > 10
  }

  render() {
    const { t, loginStatus, history, loggedInUser } = this.props
    const { userCredentials, error } = this.state
    return (
      <>
        {loginStatus.success || sessionStorage.getItem("token") ? (
          <>
            {loggedInUser.userType === "vendor"
              ? history.push("/vendor/addproduct")
              : history.push("/dashboard")}
          </>
        ) : (
          <div className="box-container-login">
            <div className="joint-form" id="login-joint-form">
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
                  {t("login.retailerLogin")}
                </Typography>
                <form className="{classes.form}" noValidate>
                  <TextField
                    variant="outlined"
                    margin="normal"
                    required
                    fullWidth
                    error={error.usernameError}
                    helperText={error.usernameErrorMsg}
                    id="username"
                    label={t("login.userName")}
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
                    label={t("login.password")}
                    type="password"
                    id="password"
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
                    id="retailer-login-btn"
                    type="button"
                    fullWidth
                    variant="contained"
                    color="primary"
                    className="{classes.submit} submit-pad"
                    onClick={this.handleSubmit}
                  >
                    {t("header.logIn")}
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
Login.propTypes = {
  loginStatus: PropTypes.shape.isRequired,
  login: PropTypes.func.isRequired,
  history: PropTypes.shape.isRequired,
  loggedInUser: PropTypes.shape.isRequired,
  t: PropTypes.shape.isRequired,
}
const stateAsProps = (store) => {
  if ("loginStatus" in store.RetailerReducer) {
    return {
      loginStatus: store.RetailerReducer.loginStatus,
      loggedInUser: store.RetailerReducer.loggedInUser,
    }
  }
  return { loginStatus: { errorMsg: "" } }
}

export default connect(stateAsProps, { login })(withTranslation()(Login))
