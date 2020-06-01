import { TextField, Typography, Button, Snackbar } from "@material-ui/core"
import CheckIcon from "@material-ui/icons/Check"
import ClearIcon from "@material-ui/icons/Clear"
import MuiAlert from "@material-ui/lab/Alert"
import React, { Component } from "react"
import { connect } from "react-redux"
import PropTypes from "prop-types"
import { postGroup } from "../../redux/actions/RetailerActions"
import Message from "../utils/Message"
import { groupName, addGroup, addGroupFail } from "../utils/constants"

class AddGroup extends Component {
  constructor(props) {
    super(props)

    this.state = {
      group: {
        groupName: "",
      },
      status: 0,
    }
    this.handleChange = this.handleChange.bind(this)
    this.handleSubmit = this.handleSubmit.bind(this)
  }

  isValidGroupName = () => {
    const { group } = this.state
    if (group.groupName.length > 0) {
      this.setState({ status: 1 })
    } else {
      this.setState({ status: -1 })
      return false
    }
    return true
  }

  handleChange(e) {
    const { name, value } = e.target
    const { group } = this.state
    group[name] = value
    this.setState({ group })
  }

  handleSubmit(e) {
    e.preventDefault()
    if (this.isValidGroupName()) {
      const { group } = this.state
      const { postGroup: postGroupAlt } = this.props
      postGroupAlt(group) // thunk action
    }
  }

  render() {
    const { group, status } = this.state
    return (
      <div className="box-container">
        <div className="joint-form">
          <div className="validation-half">
            <div className="validations">
              <h3>Requirements</h3>
              {group.groupName.length <= 0 && (
                <div className="typo-div">
                  <ClearIcon className="icon-style" />
                  <Typography variant="subtitle2" gutterBottom>
                    {groupName}
                  </Typography>
                </div>
              )}
              {group.groupName.length > 0 && (
                <div className="approved-div">
                  <CheckIcon className="icon-style" />
                  <Typography variant="subtitle2" gutterBottom>
                    {groupName}
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
                  {addGroup}
                </Typography>
              </div>
              <TextField
                variant="outlined"
                margin="normal"
                required
                fullWidth
                id="group-input"
                label="Group Name"
                name="groupName"
                onChange={this.handleChange}
                autoComplete="zoneName"
                autoFocus
              />

              <Button
                type="button"
                fullWidth
                variant="contained"
                color="primary"
                className="{classes.submit} submit-pad"
                onClick={this.handleSubmit}
                id="group-submit"
              >
                Save
              </Button>
            </form>
          </div>
        </div>

        <>
          {status === -1 ? (
            <>
              <Snackbar open="true" autoHideDuration={2000}>
                <MuiAlert severity="error" elevation={6} variant="filled">
                  {addGroupFail}
                </MuiAlert>
              </Snackbar>
            </>
          ) : null}
        </>
        <Message />
      </div>
    )
  }
}

AddGroup.propTypes = {
  postGroup: PropTypes.func.isRequired,
}

const actionAsProps = {
  postGroup,
}
export default connect(null, actionAsProps)(AddGroup)
