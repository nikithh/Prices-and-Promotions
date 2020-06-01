import {
  InputLabel,
  Select,
  TextField,
  Typography,
  Button,
  FormControl,
  Snackbar,
} from "@material-ui/core"
import CheckIcon from "@material-ui/icons/Check"
import ClearIcon from "@material-ui/icons/Clear"
import MuiAlert from "@material-ui/lab/Alert"
import React, { Component } from "react"
import { connect } from "react-redux"
import PropTypes from "prop-types"
import {
  getClusters,
  getZones,
  postStore,
} from "../../redux/actions/RetailerActions"
import { createstore, storenamecheck } from "../utils/constants"

class StoreForm extends Component {
  constructor(props) {
    super(props)

    this.state = {
      zone: "",
      cluster: "",
      storeName: "",
      city: "",
      streetName: "",
      pin: "",
      status: 0,
    }

    this.handleChange = this.handleChange.bind(this)
    this.handleSubmit = this.handleSubmit.bind(this)
    // this.handleChangeStore = this.handleChangeStore.bind(this)
    this.handleChangeZone = this.handleChangeZone.bind(this)
  }

  // eslint-disable-next-line camelcase
  UNSAFE_componentWillMount() {
    const { history } = this.props
    history.push("/store")
  }

  componentDidMount() {
    const { getZones: getZonesAlt } = this.props
    getZonesAlt()
  }

  handleChange(e) {
    const { name, value } = e.target

    this.setState({ [name]: value })
  }

  handleChangeZone(e) {
    this.setState({ zone: e.target.value })
    const { getClusters: getClustersAlt } = this.props
    getClustersAlt(e.target.value)
  }

  handleSubmit(e) {
    e.preventDefault()
    const { postStore: postStoreAlt } = this.props
    const { storeName, zone, cluster, pin, city, streetName } = this.state
    const address = {
      streetName,
      city,
      pin,
    }
    const store = { storeName, address }
    postStoreAlt(store, zone, cluster)
    if (storeName.length > 6) {
      this.setState({ status: 1 })
    } else {
      this.setState({ status: -1 })
    }
  }

  render() {
    const {
      storeName,
      zone,
      cluster,
      pin,
      city,
      streetName,
      status,
    } = this.state
    const { clusters, zones } = this.props
    return (
      <div className="box-container">
        <div className="joint-form-assign flex-column">
          <div className="store-requirement">
            <h3 className="center-h3">Requirements</h3>
            {storeName.length <= 6 && (
              <div className="typo-div">
                <ClearIcon className="icon-style" />
                <Typography variant="subtitle2" gutterBottom>
                  {storenamecheck}
                </Typography>
              </div>
            )}
            {storeName.length > 6 && (
              <div className="approved-text">
                <CheckIcon className="icon-style" />
                <Typography variant="subtitle2" gutterBottom>
                  {storenamecheck}
                </Typography>
              </div>
            )}
          </div>
          <div id="store-form-container">
            <div className="help-block">
              <Typography
                color="primary"
                component="h1"
                variant="h4"
                className="special-store-help"
              >
                {createstore}
              </Typography>
            </div>
            <div className="advanced-form-container">
              <div className="form-first-half">
                <form className="{classes.form}" noValidate>
                  <FormControl margin="normal" variant="outlined" fullWidth>
                    <InputLabel htmlFor="outlined-age-native-simple">
                      Enter Zone
                    </InputLabel>
                    <Select
                      fullWidth
                      native
                      value={zone}
                      onChange={this.handleChangeZone}
                      label="Enter Zone"
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

                  <FormControl margin="normal" variant="outlined" fullWidth>
                    <InputLabel htmlFor="outlined-age-native-simple">
                      Enter Cluster
                    </InputLabel>
                    <Select
                      fullWidth
                      native
                      value={cluster}
                      onChange={this.handleChange}
                      label="Enter Cluster"
                      inputProps={{
                        name: "cluster",
                        id: "cluster",
                      }}
                    >
                      <option aria-label="None" value="" />
                      {clusters.map((clusterVal) => {
                        return <option value={clusterVal}>{clusterVal}</option>
                      })}
                    </Select>
                  </FormControl>

                  <TextField
                    variant="outlined"
                    margin="normal"
                    required
                    fullWidth
                    id="storeName"
                    label="Store Name"
                    name="storeName"
                    autoComplete="storeName"
                    onChange={this.handleChange}
                    value={storeName}
                  />
                </form>
              </div>
              <div className="form-second-half">
                <form className="{classes.form}" noValidate>
                  <TextField
                    variant="outlined"
                    margin="normal"
                    required
                    fullWidth
                    name="streetName"
                    label="Street name"
                    autoComplete="streetName"
                    id="streetName"
                    onChange={this.handleChange}
                    value={streetName}
                  />
                  <TextField
                    variant="outlined"
                    margin="normal"
                    required
                    fullWidth
                    name="city"
                    label="City"
                    id="city"
                    onChange={this.handleChange}
                    autoComplete="city"
                    value={city}
                  />
                  <TextField
                    variant="outlined"
                    margin="normal"
                    required
                    fullWidth
                    name="pin"
                    label="Pin-code"
                    type="number"
                    id="pin"
                    onChange={this.handleChange}
                    autoComplete="pin"
                    value={pin}
                  />
                </form>
              </div>
            </div>
            <div id="store-submit-btn">
              <Button
                type="button"
                fullWidth
                variant="contained"
                color="primary"
                className="{classes.submit}"
                onClick={this.handleSubmit}
                id="store-form-submit"
              >
                Save
              </Button>
            </div>
          </div>
        </div>
        <>
          {status === 1 ? (
            <Snackbar open="true" autoHideDuration={2000}>
              <MuiAlert elevation={6} variant="filled">
                Store created successfully!
              </MuiAlert>
            </Snackbar>
          ) : null}
        </>
        <>
          {status === -1 ? (
            <Snackbar open="true" autoHideDuration={2000}>
              <MuiAlert severity="error" elevation={6} variant="filled">
                Store creation failed. Please match the requirements
              </MuiAlert>
            </Snackbar>
          ) : null}
        </>
      </div>
    )
  }
}

StoreForm.propTypes = {
  getZones: PropTypes.func.isRequired,
  getClusters: PropTypes.func.isRequired,
  postStore: PropTypes.func.isRequired,
  zones: PropTypes.arrayOf.isRequired,
  clusters: PropTypes.arrayOf.isRequired,
  history: PropTypes.shape.isRequired,
}
const stateAsProps = (store) => ({
  zones: store.RetailerReducer.zones,
  clusters: store.RetailerReducer.clusters,
})
const actionAsProps = {
  getZones,
  getClusters,
  postStore,
}
export default connect(stateAsProps, actionAsProps)(StoreForm)
