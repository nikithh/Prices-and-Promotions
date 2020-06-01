import {
  InputLabel,
  TextField,
  Typography,
  Button,
  FormControl,
  Select,
  Snackbar,
} from "@material-ui/core"
import CheckIcon from "@material-ui/icons/Check"
import ClearIcon from "@material-ui/icons/Clear"
import MuiAlert from "@material-ui/lab/Alert"
import React, { Component } from "react"
import { connect } from "react-redux"
import PropTypes from "prop-types"
import { getZones, postCluster } from "../../redux/actions/RetailerActions"
import Message from "../utils/Message"
import { createCluster, clusterCondition } from "../utils/constants"

class ClusterForm extends Component {
  constructor(props) {
    super(props)
    this.state = {
      zone: "",
      clusterName: "",
      taxRate: "",
      status: 0,
    }
    this.handleChange = this.handleChange.bind(this)
    this.handleSubmit = this.handleSubmit.bind(this)
  }

  // eslint-disable-next-line camelcase
  UNSAFE_componentWillMount() {
    const { history } = this.props
    history.push("/cluster")
  }

  componentDidMount() {
    const { getZones: getZonesAlt } = this.props
    getZonesAlt()
  }

  handleChange(e) {
    const { name, value } = e.target
    this.setState({ [name]: value })
  }

  handleSubmit(e) {
    e.preventDefault()
    const { clusterName, taxRate, zone } = this.state
    const { postCluster: postClusterAlt } = this.props
    const cluster = {
      clusterName,
      taxRate,
    }
    if (clusterName.length > 5) {
      postClusterAlt(cluster, zone)
      this.setState({ status: 1 })
    } else {
      this.setState({ status: -1 })
    }
  }

  render() {
    const { clusterName, zone, status, taxRate } = this.state
    const { zones } = this.props
    return (
      <div className="box-container">
        <div className="joint-form">
          <div className="validation-half">
            <div className="validations">
              <h3 className="center-h3">Requirements</h3>
              {clusterName.length <= 5 && (
                <div className="typo-div">
                  <ClearIcon className="icon-style" />
                  <Typography variant="subtitle2" gutterBottom>
                    {clusterCondition}
                  </Typography>
                </div>
              )}
              {clusterName.length > 5 && (
                <div className="approved-text">
                  <CheckIcon className="icon-style" />
                  <Typography variant="subtitle2" gutterBottom>
                    {clusterCondition}
                  </Typography>
                </div>
              )}
            </div>
          </div>
          <div className="form-half">
            <form className="{classes.form}" noValidate>
              <div className="help-block">
                <Typography
                  color="primary"
                  component="h1"
                  variant="h4"
                  className="help-block-h4"
                >
                  {createCluster}
                </Typography>
              </div>
              <FormControl variant="outlined" fullWidth>
                <InputLabel htmlFor="outlined-age-native-simple">
                  Zone
                </InputLabel>
                <Select
                  fullWidth
                  native
                  value={zone}
                  onChange={this.handleChange}
                  label="Zone"
                  inputProps={{
                    name: "zone",
                    id: "zone",
                  }}
                  autoFocus
                >
                  <option aria-label="None" value="" />
                  {zones.map((zoneVal) => {
                    return <option value={zoneVal}>{zoneVal}</option>
                  })}
                </Select>
              </FormControl>
              <TextField
                variant="outlined"
                margin="normal"
                required
                fullWidth
                id="clusterName"
                label="Cluster Name"
                name="clusterName"
                autoComplete="clusterName"
                onChange={this.handleChange}
                value={clusterName}
                style={{
                  marginTop: "24px",
                }}
              />
              <TextField
                variant="outlined"
                margin="normal"
                required
                fullWidth
                id="taxRate"
                label="Tax Rate"
                type="number"
                step="0.01"
                name="taxRate"
                autoComplete="taxRate"
                onChange={this.handleChange}
                value={taxRate}
              />
              <Button
                type="button"
                fullWidth
                variant="contained"
                color="primary"
                className="{classes.submit} submit-pad"
                onClick={this.handleSubmit}
                id="cluster-form-submit"
              >
                Save
              </Button>
            </form>
          </div>
        </div>
        <Message />

        <>
          {status === -1 ? (
            <Snackbar open="true" autoHideDuration={2000}>
              <MuiAlert severity="error" elevation={6} variant="filled">
                Cluster creation failed. Please match the requirements
              </MuiAlert>
            </Snackbar>
          ) : null}
        </>
      </div>
    )
  }
}

ClusterForm.propTypes = {
  zones: PropTypes.arrayOf.isRequired,
  getZones: PropTypes.func.isRequired,
  postCluster: PropTypes.func.isRequired,
  history: PropTypes.shape.isRequired,
}
const stateAsProps = (store) => ({
  zones: store.RetailerReducer.zones,
})
const actionAsProps = {
  getZones,
  postCluster,
}
export default connect(stateAsProps, actionAsProps)(ClusterForm)
