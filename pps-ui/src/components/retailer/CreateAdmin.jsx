import { TextField, Typography, Button, Snackbar } from "@material-ui/core"
import CheckIcon from "@material-ui/icons/Check"
import ClearIcon from "@material-ui/icons/Clear"
import MuiAlert from "@material-ui/lab/Alert"
import React, { Component } from "react"
import { connect } from "react-redux"
import PropTypes from "prop-types"
import md5 from "md5"
import { createAdmin } from "../../redux/actions/RetailerActions"
import Message from "../utils/Message"

class CreateAdmin extends Component {
  constructor(props) {
    super(props)

    this.state = {
      admin: {
        userName: "",
        password: "",
      },
      status: 0,
    }
    this.handleChangeUserName = this.handleChangeUserName.bind(this)
    this.handleChangePassword = this.handleChangePassword.bind(this)
    this.handleSubmit = this.handleSubmit.bind(this)
  }

  // isValidAdminName = () => {
  //     const { admin } = this.state
  //     if (admin.username.length > 0) {
  //       this.setState({ status: 1 })
  //     } else {
  //       this.setState({ status: -1 })
  //       return false
  //     }
  //     return true
  //   }

  handleChangeUserName(e) {
    const { admin } = this.state
    admin.userName = e.target.value
    this.setState({ admin })
  }

  handleChangePassword(e) {
    const { admin } = this.state
    admin.password = e.target.value
    this.setState({ admin })
  }

  handleSubmit(e) {
    e.preventDefault()
    const { admin } = this.state
    const { createAdmin: createAdminAlt } = this.props
    if (admin.userName === "" || admin.password === "") {
      this.setState({ status: -1 })
    } else {
      admin.password = md5(admin.password)
      createAdminAlt({ ...admin }) // thunk action
    }
  }

  render() {
    const { admin, status } = this.state
    return (
      <div className="box-container">
        <div className="joint-form">
          <div className="validation-half">
            <div className="validations">
              <h3>Requirements</h3>
              {admin.userName.length <= 0 && (
                <div className="typo-div">
                  <ClearIcon className="icon-style" />
                  <Typography variant="subtitle2" gutterBottom>
                    Please enter the username
                  </Typography>
                </div>
              )}
              {admin.userName.length > 0 && (
                <div className="approved-text">
                  <CheckIcon className="icon-style" />
                  <Typography variant="subtitle2" gutterBottom>
                    Please enter the username
                  </Typography>
                </div>
              )}
              {admin.password.length <= 0 && (
                <div className="typo-div">
                  <ClearIcon className="icon-style" />
                  <Typography variant="subtitle2" gutterBottom>
                    Please enter password
                  </Typography>
                </div>
              )}
              {admin.password.length > 0 && (
                <div className="approved-text">
                  <CheckIcon className="icon-style" />
                  <Typography variant="subtitle2" gutterBottom>
                    Please enter password
                  </Typography>
                </div>
              )}
            </div>
          </div>
          <div className="form-half">
            <form className="{classes.form} expanded-form" noValidate>
              <div className="help-block">
                <Typography
                  color="primary"
                  component="h1"
                  variant="h4"
                  className="help-block-h4"
                >
                  Create admin
                </Typography>
              </div>
              <TextField
                variant="outlined"
                margin="normal"
                required
                fullWidth
                id="admin-input-username"
                label="userName"
                name="userName"
                onChange={this.handleChangeUserName}
                autoFocus
              />

              <TextField
                variant="outlined"
                margin="normal"
                type="password"
                required
                fullWidth
                id="admin-input-password"
                label="password"
                name="password"
                onChange={this.handleChangePassword}
              />

              <Button
                type="button"
                fullWidth
                variant="contained"
                color="primary"
                className="{classes.submit} submit-pad"
                onClick={this.handleSubmit}
                id="admin-submit"
              >
                Create
              </Button>
            </form>
          </div>
        </div>

        <>
          {status === -1 ? (
            <Snackbar open="true" autoHideDuration={2000}>
              <MuiAlert severity="error" elevation={6} variant="filled">
                Please fill required fields
              </MuiAlert>
            </Snackbar>
          ) : null}
        </>
        <Message />
      </div>
    )
  }
}

CreateAdmin.propTypes = {
  createAdmin: PropTypes.func.isRequired,
}

const actionAsProps = {
  createAdmin,
}
export default connect(null, actionAsProps)(CreateAdmin)
