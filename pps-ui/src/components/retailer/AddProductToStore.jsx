import {
  InputLabel,
  Select,
  Typography,
  Button,
  FormControl,
  Snackbar,
} from "@material-ui/core"
import MuiAlert from "@material-ui/lab/Alert"
import React, { Component } from "react"
import PropTypes from "prop-types"
import { connect } from "react-redux"
import { Link } from "react-router-dom"
import CheckIcon from "@material-ui/icons/Check"
import ClearIcon from "@material-ui/icons/Clear"
import {
  getClusters,
  getStores,
  getZones,
  saveClusterValue,
  saveStoreValue,
  saveZoneValue,
} from "../../redux/actions/RetailerActions"
import {
  zoneCreated,
  zoneCreationFailed,
  addProductToStore,
} from "../utils/constants"

class AddProductToStore extends Component {
  constructor(props) {
    super(props)

    this.state = {
      zone: "",
      cluster: "",
      store: "",
      status: 0,
    }

    this.handleChange = this.handleChange.bind(this)
    this.handleChangeZone = this.handleChangeZone.bind(this)
    this.handleChangeCluster = this.handleChangeCluster.bind(this)
    this.handleChangeStore = this.handleChangeStore.bind(this)
  }

  componentDidMount() {
    const { getAllZones: getAllZonesAlt } = this.props
    getAllZonesAlt()
  }

  handleSubmitHistory = () => {
    const { history } = this.props
    history.push("/store")
  }

  handleChangeStore(e) {
    this.setState({ store: e.target.value })
    const { saveStoreValue: saveStoreValueAlt } = this.props
    saveStoreValueAlt(e.target.value)
  }

  handleChangeCluster(e) {
    this.setState({ cluster: e.target.value })
    const {
      getAllStores: getAllStoresAlt,
      saveClusterValue: saveClusterValueAlt,
      zone,
    } = this.props
    getAllStoresAlt(zone, e.target.value)
    saveClusterValueAlt(e.target.value)
  }

  handleChangeZone(e) {
    this.setState({ zone: e.target.value })
    const {
      getAllClusters: getAllClustersAlt,
      saveZoneValue: saveZoneValueAlt,
    } = this.props
    getAllClustersAlt(e.target.value)
    saveZoneValueAlt(e.target.value)
  }

  handleChange(e) {
    const { name, value } = e.target
    this.setState({ [name]: value })
  }

  render() {
    const { zone, cluster, store, status } = this.state
    const { zones, clusters, stores } = this.props
    return (
      <div className="box-container">
        <div className="joint-form">
          <div className="validation-half">
            <div className="validations">
              <h3>Requirements</h3>
              {zone === "" && (
                <div className="unapproved-text">
                  <ClearIcon className="icon-style" />
                  <Typography variant="subtitle2" gutterBottom>
                    Select Zone
                  </Typography>
                </div>
              )}
              {zone !== "" && (
                <div className="approved-text">
                  <CheckIcon className="icon-style" />
                  <Typography variant="subtitle2" gutterBottom>
                    Select Zone
                  </Typography>
                </div>
              )}
              {cluster === "" && (
                <div className="unapproved-text">
                  <ClearIcon className="icon-style" />
                  <Typography variant="subtitle2" gutterBottom>
                    Select Cluster
                  </Typography>
                </div>
              )}
              {cluster !== "" && (
                <div className="approved-text">
                  <CheckIcon className="icon-style" />
                  <Typography variant="subtitle2" gutterBottom>
                    Select Cluster
                  </Typography>
                </div>
              )}
              {store === "" && (
                <div className="unapproved-text">
                  <ClearIcon className="icon-style" />
                  <Typography variant="subtitle2" gutterBottom>
                    Select Store
                  </Typography>
                </div>
              )}
              {store !== "" && (
                <div className="approved-text">
                  <CheckIcon className="icon-style" />
                  <Typography variant="subtitle2" gutterBottom>
                    Select Store
                  </Typography>
                </div>
              )}
            </div>
          </div>
          <div className="form-half">
            <form className="{classes.form}" noValidate>
              <>
                <div className="help-block">
                  <Typography
                    color="primary"
                    component="h1"
                    variant="h4"
                    className="help-block-h4"
                    id="special-add-prods-help"
                  >
                    {addProductToStore}
                  </Typography>
                </div>
              </>
              <FormControl
                variant="outlined"
                fullWidth
                className="space-margin-top"
              >
                <InputLabel htmlFor="outlined-age-native-simple">
                  Zone
                </InputLabel>
                <Select
                  fullWidth
                  native
                  value={zone}
                  onChange={this.handleChangeZone}
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
              <FormControl
                variant="outlined"
                fullWidth
                className="space-margin-top"
              >
                <InputLabel htmlFor="outlined-age-native-simple">
                  Cluster
                </InputLabel>
                <Select
                  fullWidth
                  native
                  value={cluster}
                  onChange={this.handleChangeCluster}
                  label="Cluster"
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
              <FormControl
                variant="outlined"
                fullWidth
                className="space-margin-top"
              >
                <InputLabel htmlFor="outlined-age-native-simple">
                  Store
                </InputLabel>
                <Select
                  fullWidth
                  native
                  value={store}
                  onChange={this.handleChangeStore}
                  label="Store"
                  inputProps={{
                    name: "store",
                    id: "store",
                  }}
                >
                  <option aria-label="None" value="" />
                  {stores.map((storeVal) => {
                    return <option value={storeVal}>{storeVal}</option>
                  })}
                </Select>
              </FormControl>
              {cluster !== "" && stores.length <= 0 && (
                <Button
                  type="button"
                  fullWidth
                  variant="contained"
                  color="primary"
                  className="{classes.submit} submit-pad"
                  onClick={this.handleSubmitHistory}
                >
                  Add Store
                </Button>
              )}
              {store !== "" && zone !== "" && cluster !== "" ? (
                <Link to="/addproducts" className="special-href">
                  <Button
                    type="button"
                    fullWidth
                    variant="contained"
                    color="primary"
                    className="{classes.submit} submit-pad"
                    id="add-prods-store-submit"
                  >
                    Add Products
                  </Button>
                </Link>
              ) : (
                <Link to="/addproducts" className="special-href">
                  <Button
                    type="button"
                    fullWidth
                    variant="contained"
                    color="primary"
                    className="{classes.submit} submit-pad empty-submit"
                    id="add-prods-store-submit"
                    // onClick={this.handleSubmit}
                  >
                    Add Products
                  </Button>
                </Link>
              )}
            </form>
          </div>
        </div>
        <>
          {status === 1 ? (
            <>
              <Snackbar open="true" autoHideDuration={2000}>
                <MuiAlert elevation={6} variant="filled">
                  {zoneCreated}
                </MuiAlert>
              </Snackbar>
            </>
          ) : null}
        </>
        <>
          {status === -1 ? (
            <>
              <Snackbar open="true" autoHideDuration={2000}>
                <MuiAlert severity="error" elevation={6} variant="filled">
                  {zoneCreationFailed}
                </MuiAlert>
              </Snackbar>
            </>
          ) : null}
        </>
      </div>
    )
  }
}

AddProductToStore.propTypes = {
  zones: PropTypes.shape.isRequired,
  clusters: PropTypes.shape.isRequired,
  zone: PropTypes.string.isRequired,
  stores: PropTypes.shape.isRequired,
  getAllStores: PropTypes.func.isRequired,
  getAllClusters: PropTypes.func.isRequired,
  getAllZones: PropTypes.func.isRequired,
  saveZoneValue: PropTypes.func.isRequired,
  saveClusterValue: PropTypes.func.isRequired,
  saveStoreValue: PropTypes.func.isRequired,
  history: PropTypes.shape.isRequired,
}

const stateAsProps = (store) => ({
  zones: store.RetailerReducer.zones,
  clusters: store.RetailerReducer.clusters,
  zone: store.RetailerReducer.zone,
  cluster: store.RetailerReducer.cluster,
  store: store.RetailerReducer.store,
  stores: store.RetailerReducer.stores,
})
const actionAsProps = {
  getAllZones: getZones,
  getAllClusters: getClusters,
  getAllStores: getStores,
  saveZoneValue,
  saveClusterValue,
  saveStoreValue,
}
export default connect(stateAsProps, actionAsProps)(AddProductToStore)
