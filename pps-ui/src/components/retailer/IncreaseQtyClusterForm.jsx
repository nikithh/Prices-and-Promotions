import { TextField, Typography, Button } from "@material-ui/core"
import React, { Component } from "react"
import CheckIcon from "@material-ui/icons/Check"
import ClearIcon from "@material-ui/icons/Clear"
import { connect } from "react-redux"
import PropTypes from "prop-types"
// import { spacing } from "@material-ui/system"
import {
  getZoneQuantity,
  getClusterQuantity,
  updateQuantity,
} from "../../redux/actions/RetailerActions"
import ProductDetailsTable from "../utils/ProductDetailsTable"
import { zonequantity } from "../utils/constants"
import convertCurrency from "../utils/ConvertCurrency"

class IncreaseQtyClusterForm extends Component {
  constructor(props) {
    super(props)

    this.state = {
      clusterQuantity: "",
      levelOption: "cluster",
      increaseQtyCluster: {
        zoneName: "",
        clusterName: "",
        quantityAssigned: "",
      },
    }

    this.handleChangeQuantity = this.handleChangeQuantity.bind(this)
    this.handleSubmit = this.handleSubmit.bind(this)
  }

  // eslint-disable-next-line camelcase
  UNSAFE_componentWillMount() {
    const {
      productName,
      zone,
      cluster,
      getZoneQuantity: getZoneQuantityAlt,
      getClusterQuantity: getClusterQuantityAlt,
    } = this.props
    getClusterQuantityAlt(productName, zone, cluster)
    getZoneQuantityAlt(productName, zone)
  }

  handleSubmit = (e) => {
    e.preventDefault()
    const {
      productName,
      zone,
      cluster,
      updateQuantity: updateQuantityAlt,
      history,
    } = this.props
    const { increaseQtyCluster, levelOption, clusterQuantity } = this.state
    increaseQtyCluster.zoneName = zone
    increaseQtyCluster.clusterName = cluster
    increaseQtyCluster.quantityAssigned = clusterQuantity
    updateQuantityAlt(increaseQtyCluster, productName, levelOption)
    history.push("/view/assigned/clusters")
  }

  handleChangeQuantity(e) {
    this.setState({
      clusterQuantity: e.target.value,
    })
  }

  render() {
    const { clusterQuantity } = this.state
    const {
      cluster,
      assignedPrice,
      quantityAssignedAtCluster,
      productDetails,
    } = this.props
    return (
      <div className="box-container">
        <div className="joint-form-large-table">
          <div className="store-requirement">
            <h3 className="center-h3">Requirements</h3>
            {(clusterQuantity >= productDetails.remainingQuantity ||
              clusterQuantity <= 0) && (
              <div className="approved-text">
                <ClearIcon className="icon-style" />
                <Typography variant="subtitle2" gutterBottom>
                  {zonequantity}
                </Typography>
              </div>
            )}
            {clusterQuantity > 0 &&
              clusterQuantity < productDetails.remainingQuantity && (
                <div className="unapproved-text">
                  <CheckIcon className="icon-style" />
                  <Typography variant="subtitle2" gutterBottom>
                    {zonequantity}
                  </Typography>
                </div>
              )}
          </div>

          <div className="form-center">
            <div className="flex-grid">
              <Typography className="card-header" variant="h4">
                Increase Quantity in Cluster
              </Typography>
              <ProductDetailsTable />
              <Typography className="card-header" variant="h6">
                Selected Cluster : {cluster}
              </Typography>
              <Typography className="card-header" variant="h6">
                Actual Price :{" "}
                {sessionStorage.getItem("currency") === "USD"
                  ? `$ ${assignedPrice}`
                  : convertCurrency(
                      "USD",
                      sessionStorage.getItem("currency"),
                      assignedPrice
                    )}
              </Typography>
              {/* <Typography className="card-header" variant="h6">
                Remaining Quantity At Zone : {quantityAssignedAtZone}
              </Typography> */}
              <Typography className="card-header" variant="h6">
                Quantity Assigned : {quantityAssignedAtCluster}
              </Typography>
              <TextField
                variant="outlined"
                margin="normal"
                defaultValue=""
                required
                fullWidth="false"
                type="number"
                step="0.01"
                id="clusterquantity"
                label="Add Quantity"
                name="clusterquantity"
                autoComplete="clusterquantity"
                onChange={this.handleChangeQuantity}
                InputProps={{
                  inputProps: {
                    max: productDetails.remainingQuantity,
                    min: 1,
                  },
                }}
                autoFocus
              />
              {clusterQuantity < productDetails.remainingQuantity &&
                clusterQuantity > 0 && (
                  <Button
                    halfWidth
                    type="button"
                    variant="contained"
                    color="primary"
                    className="{classes.submit}"
                    onClick={this.handleSubmit}
                    style={{ marginTop: "10px" }}
                    id="add-cluster-quantity"
                  >
                    Add Quantity to Cluster
                  </Button>
                )}
            </div>
          </div>
        </div>
      </div>
    )
  }
}

IncreaseQtyClusterForm.propTypes = {
  productName: PropTypes.string.isRequired,
  zone: PropTypes.string.isRequired,
  cluster: PropTypes.string.isRequired,
  history: PropTypes.shape.isRequired,
  assignedPrice: PropTypes.string.isRequired,
  getZoneQuantity: PropTypes.func.isRequired,
  getClusterQuantity: PropTypes.func.isRequired,
  quantityAssignedAtCluster: PropTypes.shape.isRequired,
  productDetails: PropTypes.shape.isRequired,
  updateQuantity: PropTypes.func.isRequired,
}
const stateAsProps = (store) => ({
  productDetails: store.RetailerReducer.productDetails,
  productName: store.RetailerReducer.productName,
  zone: store.RetailerReducer.zone,
  cluster: store.RetailerReducer.cluster,
  assignedPrice: store.RetailerReducer.assignedPrice,
  loggedInUser: store.RetailerReducer.loggedInUser,
  promotionAlert: store.AdminReducer.promotionAlert,
  quantityAssignedAtZone: store.RetailerReducer.quantityAssignedAtZone,
  quantityAssignedAtCluster: store.RetailerReducer.quantityAssignedAtCluster,
})

const actionAsProps = {
  getZoneQuantity,
  getClusterQuantity,
  updateQuantity,
}
export default connect(stateAsProps, actionAsProps)(IncreaseQtyClusterForm)
