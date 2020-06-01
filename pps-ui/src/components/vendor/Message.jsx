import React, { Component } from "react"
import connect from "react-redux/es/connect/connect"
import MuiAlert from "@material-ui/lab/Alert"
import Snackbar from "@material-ui/core/Snackbar"
import PropTypes from "prop-types"
import { messageSetNull } from "../../redux/actions/VendorActions"

class Message extends Component {
  constructor(props) {
    super(props)

    this.state = {}
    this.closeAlert = this.closeAlert.bind(this)
  }

  closeAlert = () => {
    const { messageSetNull: messageSetNullAlt } = this.props
    messageSetNullAlt()
  }

  render() {
    const { msg, msgSeverity } = this.props
    return (
      <>
        {msg !== "" ? (
          <Snackbar
            open="true"
            onClose={this.closeAlert}
            autoHideDuration={4000}
            anchorOrigin={{ vertical: "top", horizontal: "right" }}
          >
            <MuiAlert
              severity={msgSeverity}
              elevation={6}
              variant="filled"
              onClose={this.closeAlert}
            >
              {msg}
            </MuiAlert>
          </Snackbar>
        ) : null}
      </>
    )
  }
}

Message.propTypes = {
  msg: PropTypes.string.isRequired,
  msgSeverity: PropTypes.string.isRequired,

  messageSetNull: PropTypes.func.isRequired,
}

const stateAsProps = (store) => ({
  msg: store.VendorReducer.msg,
  msgSeverity: store.VendorReducer.msgSeverity,
})
const actionsAsProps = {
  messageSetNull,
}
export default connect(stateAsProps, actionsAsProps)(Message)
