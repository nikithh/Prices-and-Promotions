import {
  InputLabel,
  TextField,
  Typography,
  Button,
  FormControl,
  Select,
} from "@material-ui/core"
import CheckIcon from "@material-ui/icons/Check"
import ClearIcon from "@material-ui/icons/Clear"
import Autocomplete from "@material-ui/lab/Autocomplete"
import React, { Component } from "react"
import { connect } from "react-redux"
import PropTypes from "prop-types"
import {
  getProductDetails,
  getProductList,
  saveClusterValue,
  saveProductValue,
  saveZoneValue,
  clearAssignedPrice,
  checkAssignedCluster,
  getZonesForProduct,
  getClustersForProduct,
} from "../../redux/actions/RetailerActions"
import Message from "../utils/Message"

class IncreaseQtyCluster extends Component {
  constructor(props) {
    super(props)

    this.state = {
      productName: "",
      zone: "",
      cluster: "",
      assignedPriceState: "",
      // status: 0
    }
    this.handleChangeProduct = this.handleChangeProduct.bind(this)
    this.handleChangeZone = this.handleChangeZone.bind(this)
    this.handleChangeCluster = this.handleChangeCluster.bind(this)
    this.handleSubmit = this.handleSubmit.bind(this)
  }

  // eslint-disable-next-line camelcase
  UNSAFE_componentWillMount() {
    const {
      getProductList: getProductListAlt,
      clearAssignedPrice: clearAssignedPriceAlt,
    } = this.props
    const { assignedPriceState } = this.state
    getProductListAlt()
    clearAssignedPriceAlt(assignedPriceState)
  }

  handleChangeProduct = (e, value) => {
    const {
      getZonesForProduct: getZonesForProductAlt,
      saveProductValue: saveProductValueAlt,
      getProductDetails: getProductDetailsAlt,
    } = this.props
    const productName = value
    this.setState({ productName })
    saveProductValueAlt(productName)
    getZonesForProductAlt(productName)
    getProductDetailsAlt(productName)
  }

  handleChangeZone(e) {
    const { value } = e.target
    this.setState({ zone: value })
    const {
      saveZoneValue: saveZoneValueAlt,
      getClustersForProduct: getClustersForProductAlt,
    } = this.props
    const { productName } = this.state
    getClustersForProductAlt(productName, value)
    saveZoneValueAlt(value)
  }

  handleChangeCluster(e) {
    const { value } = e.target
    this.setState({ cluster: value })
    const { saveClusterValue: saveClusterValueAlt } = this.props
    saveClusterValueAlt(value)
  }

  handleSubmit() {
    const {
      zone,
      cluster,
      productName,
      checkAssignedCluster: checkAssignedClusterAlt,
    } = this.props
    checkAssignedClusterAlt(productName, zone, cluster)
  }

  render() {
    const { productName, zone, cluster } = this.state
    const {
      productZoneList,
      productClusterList,
      products,
      assignedPrice,
      history,
    } = this.props

    return (
      <>
        {assignedPrice !== "" ? (
          history.push("/updateqty/clusterform")
        ) : (
          <div className="box-container">
            <div className="joint-form">
              <div className="validation-half">
                <div className="validations">
                  <h3 className="center-h3">Requirements</h3>

                  {productName === "" && (
                    <div className="unapproved-text">
                      <ClearIcon className="icon-style" />
                      <Typography variant="subtitle2" gutterBottom>
                        Select a product name
                      </Typography>
                    </div>
                  )}
                  {productName !== "" && (
                    <div className="approved-text">
                      <CheckIcon className="icon-style" />
                      <Typography variant="subtitle2" gutterBottom>
                        Select a product name
                      </Typography>
                    </div>
                  )}
                  {zone === "" && (
                    <div className="unapproved-text">
                      <ClearIcon className="icon-style" />
                      <Typography variant="subtitle2" gutterBottom>
                        Select a zone name
                      </Typography>
                    </div>
                  )}
                  {zone !== "" && (
                    <div className="approved-text">
                      <CheckIcon className="icon-style" />
                      <Typography variant="subtitle2" gutterBottom>
                        Select a zone name
                      </Typography>
                    </div>
                  )}
                  {cluster === "" && (
                    <div className="unapproved-text">
                      <ClearIcon className="icon-style" />
                      <Typography variant="subtitle2" gutterBottom>
                        Select a cluster name
                      </Typography>
                    </div>
                  )}
                  {cluster !== "" && (
                    <div className="approved-text">
                      <CheckIcon />
                      <Typography variant="subtitle2" gutterBottom>
                        Select a cluster name
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
                      Select product name and cluster
                    </Typography>
                  </div>
                  <FormControl variant="outlined" fullWidth>
                    <Autocomplete
                      id="product-list"
                      fullWidth
                      options={products}
                      getOptionLabel={(option) => option}
                      renderInput={(params) => (
                        <TextField
                          // eslint-disable-next-line react/jsx-props-no-spreading
                          {...params}
                          label="Product Name"
                          variant="outlined"
                          autoFocus
                        />
                      )}
                      onChange={this.handleChangeProduct}
                      name="productName"
                    />
                  </FormControl>
                  <FormControl margin="normal" variant="outlined" fullWidth>
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
                    >
                      <option aria-label="None" value="" />
                      {productZoneList.map((zoneVal) => {
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
                      onChange={this.handleChangeCluster}
                      label="Enter Cluster"
                      inputProps={{
                        name: "cluster",
                        id: "cluster",
                      }}
                    >
                      <option aria-label="None" value="" />
                      {productClusterList.map((clusterVal) => {
                        return <option value={clusterVal}>{clusterVal}</option>
                      })}
                    </Select>
                  </FormControl>

                  {productName !== "" && zone !== "" && cluster !== "" && (
                    <Button
                      type="button"
                      fullWidth
                      variant="contained"
                      color="primary"
                      className="{classes.submit} submit-pad"
                      onClick={this.handleSubmit}
                      id="increase-cluster-qty"
                    >
                      Go
                    </Button>
                  )}
                </form>
              </div>
              <Message />
            </div>
          </div>
        )}
      </>
    )
  }
}

IncreaseQtyCluster.propTypes = {
  products: PropTypes.arrayOf.isRequired,
  getProductDetails: PropTypes.func.isRequired,
  saveProductValue: PropTypes.func.isRequired,
  getProductList: PropTypes.func.isRequired,
  saveZoneValue: PropTypes.func.isRequired,
  saveClusterValue: PropTypes.func.isRequired,
  zone: PropTypes.string.isRequired,
  cluster: PropTypes.string.isRequired,
  productName: PropTypes.string.isRequired,
  history: PropTypes.shape.isRequired,
  assignedPrice: PropTypes.string.isRequired,
  clearAssignedPrice: PropTypes.func.isRequired,
  checkAssignedCluster: PropTypes.func.isRequired,
  productZoneList: PropTypes.arrayOf.isRequired,
  getZonesForProduct: PropTypes.func.isRequired,
  productClusterList: PropTypes.arrayOf.isRequired,
  getClustersForProduct: PropTypes.func.isRequired,
}

const stateAsProps = (store) => ({
  products: store.RetailerReducer.productList,
  zone: store.RetailerReducer.zone,
  productName: store.RetailerReducer.productName,
  cluster: store.RetailerReducer.cluster,
  assignedPrice: store.RetailerReducer.assignedPrice,
  productZoneList: store.RetailerReducer.productZoneList,
  productClusterList: store.RetailerReducer.productClusterList,
})
const actionAsProps = {
  getProductList,
  saveProductValue,
  getProductDetails,
  saveZoneValue,
  saveClusterValue,
  clearAssignedPrice,
  checkAssignedCluster,
  getZonesForProduct,
  getClustersForProduct,
}
export default connect(stateAsProps, actionAsProps)(IncreaseQtyCluster)
