import {
  Select,
  TextField,
  Typography,
  Button,
  FormControl,
} from "@material-ui/core"
import React, { Component } from "react"
import { connect } from "react-redux"
import PropTypes from "prop-types"
import {
  assignToCluster,
  getZoneClusterNames,
} from "../../redux/actions/RetailerActions"
import ProductDetails from "../utils/ProductDetails"
import Message from "../utils/Message"

class AssignToCluster extends Component {
  constructor(props) {
    super(props)

    this.state = {
      zoneclustername: "",
      zonecluster: "",
      clusterName: "",
      zoneName: "",
      clusterDetails: {},
    }

    this.handleChangeCluster = this.handleChangeCluster.bind(this)
    this.handleChangeClusterName = this.handleChangeClusterName.bind(this)
    this.handleChangeProfitPecentage = this.handleChangeProfitPecentage.bind(
      this
    )
    this.handleChangeQuantity = this.handleChangeQuantity.bind(this)
    this.handleSubmit = this.handleSubmit.bind(this)
  }

  // eslint-disable-next-line camelcase
  UNSAFE_componentWillMount() {
    const { getZoneClusterNames: getZoneClusterNamesAlt } = this.props
    getZoneClusterNamesAlt()
  }

  handleSubmit = (e) => {
    e.preventDefault()
    const { assignToCluster: assignToClusterAlt, productName } = this.props
    const { zoneName, clusterName, clusterDetails } = this.state
    assignToClusterAlt(clusterDetails, zoneName, clusterName, productName)
  }

  handleChangeCluster(e) {
    this.setState({ zonecluster: e.target.value })
    const { getZoneClusterNames: getZoneClusterNamesAlt } = this.props
    getZoneClusterNamesAlt(e.target.value)
  }

  handleChangeClusterName(e) {
    // this.state.zoneclustername=e.target.value
    // this.setState({ zoneclustername: e.target.value })

    this.setState({ zoneclustername: e.target.value })
    const names = String(e.target.value).split("/")
    this.setState({ zoneName: names[0] })
    this.setState({ clusterName: names[1] })
  }

  handleChangeQuantity(e) {
    const dquantity = e.target.value
    const { clusterDetails } = this.state
    clusterDetails.quantityAssigned = dquantity
  }

  handleChangeProfitPecentage(e) {
    const dpercentage = e.target.value
    const { clusterDetails } = this.state
    clusterDetails.profitPercentage = dpercentage
  }

  render() {
    const { statusCode, history, zoneclusternames } = this.props
    const { zonecluster, zoneclustername, clusterDetails } = this.state
    return (
      <>
        {statusCode === 200 ? (
          history.push("/view/assigned/clusters")
        ) : (
          <div className="box-container">
            <div className="joint-form-large">
              <ProductDetails />
              <div className="product-form-body-padding">
                <Typography className="card-header" variant="h4">
                  Assign to Cluster
                </Typography>
                <form className="{classes.form}" noValidate>
                  {/* <FormControl variant="outlined" fullWidth> */}

                  <TextField
                    variant="outlined"
                    margin="normal"
                    required
                    fullWidth
                    id="zonecluster"
                    label="ClusterName"
                    name="zonecluster"
                    onChange={this.handleChangeCluster}
                    value={zonecluster}
                    autoFocus
                  />
                  <FormControl variant="outlined" fullWidth>
                    <Select
                      fullWidth
                      native
                      value={zoneclustername}
                      onChange={this.handleChangeClusterName}
                      label="Enter Cluster"
                      name="zoneclustername"
                      id="zoneclustername"
                    >
                      <option aria-label="None" />
                      {zoneclusternames.map((zoneclusternameVal) => {
                        return (
                          <option value={zoneclusternameVal}>
                            {zoneclusternameVal}
                          </option>
                        )
                      })}
                    </Select>
                  </FormControl>

                  <TextField
                    variant="outlined"
                    margin="normal"
                    required
                    fullWidth
                    id="clusterQuantity"
                    label="ClusterQuantity"
                    name="clusterQuantity"
                    type="number"
                    onChange={this.handleChangeQuantity}
                    value={clusterDetails.quantityAssigned}
                  />

                  <TextField
                    variant="outlined"
                    margin="normal"
                    required
                    fullWidth
                    id="clusterProfitPercentage"
                    label="ClusterProfitPercentage"
                    name="clusterProfitPercentage"
                    type="number"
                    onChange={this.handleChangeProfitPecentage}
                    value={clusterDetails.profitPercentage}
                  />

                  <Button
                    fullWidth
                    type="button"
                    variant="contained"
                    color="primary"
                    className="form-button {classes.submit} submit-pad"
                    onClick={this.handleSubmit}
                    id="assign-cluster-submit"
                  >
                    Save
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

AssignToCluster.propTypes = {
  productName: PropTypes.string.isRequired,
  statusCode: PropTypes.number.isRequired,
  getZoneClusterNames: PropTypes.func.isRequired,
  assignToCluster: PropTypes.func.isRequired,
  zoneclusternames: PropTypes.arrayOf.isRequired,
  history: PropTypes.shape.isRequired,
}

const stateAsProps = (store) => ({
  zoneclusternames: store.RetailerReducer.zoneclusternames,
  productName: store.RetailerReducer.productName,
  statusCode: store.RetailerReducer.statusCode,
})

const actionAsProps = {
  getZoneClusterNames,
  assignToCluster,
}

export default connect(stateAsProps, actionAsProps)(AssignToCluster)
