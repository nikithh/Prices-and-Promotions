import {
  InputLabel,
  Select,
  TextField,
  Typography,
  Button,
  FormControl,
  Paper,
  TableCell,
  TableContainer,
  TableHead,
  Table,
  TableRow,
  FormControlLabel,
  Checkbox,
} from "@material-ui/core"
import Tooltip from "@material-ui/core/Tooltip"
import ErrorOutlineIcon from "@material-ui/icons/ErrorOutline"
import React, { Component } from "react"
import { connect } from "react-redux"
import PropTypes from "prop-types"
import {
  getZones,
  assignToZone,
  getClusters,
  getClustersForProduct,
  saveUnassignedCluster,
  clearProductClusterList,
  clearClusterList,
  postZoneCluster,
} from "../../redux/actions/RetailerActions"
import Message from "../utils/Message"
import ProductDetailsTable from "../utils/ProductDetailsTable"
import { addMultipleClusters } from "../utils/constants"

class AssignToZone extends Component {
  constructor(props) {
    super(props)

    this.state = {
      zoneName: "",
      zoneDetails: {},
      clusterList: [],
    }

    this.handleChangeZoneName = this.handleChangeZoneName.bind(this)
    this.handleChangeProfitPecentage = this.handleChangeProfitPecentage.bind(
      this
    )
    this.handleChangeQuantity = this.handleChangeQuantity.bind(this)
    this.handleSubmit = this.handleSubmit.bind(this)
  }

  componentDidMount() {
    const {
      getZones: getZonesAlt,
      clearProductClusterList: clearProductClusterListAlt,
      clearClusterList: clearClusterListAlt,
    } = this.props
    getZonesAlt()
    clearProductClusterListAlt()
    clearClusterListAlt()
  }

  handleSubmit = (e) => {
    e.preventDefault()
    const { postZoneCluster: postZoneClusterAlt, productName } = this.props
    const { zoneDetails, clusterList, zoneName } = this.state
    const finalDetails = {
      quantityAssigned: zoneDetails.quantityAssigned,
      profitPercentage: zoneDetails.profitPercentage,
      zoneName,
      cluster: clusterList,
    }
    postZoneClusterAlt(finalDetails, productName)
  }

  handleCheckBoxChange = (e) => {
    if (
      !e.target.closest("tr").querySelectorAll("input")[1].value ||
      !e.target.closest("tr").querySelectorAll("input")[2].value
    ) {
      // eslint-disable-next-line no-alert
      alert("Please enter both the values before selecting")
      e.target.checked = false
      return
    }

    const clus = {
      clusterName: e.target.value,
      quantityAssigned: e.target.closest("tr").querySelectorAll("input")[1]
        .value,
      profitPercentage: e.target.closest("tr").querySelectorAll("input")[2]
        .value,
    }
    const { clusterList } = this.state
    const keyArr = clusterList.map((ele) => ele.clusterName)

    if (keyArr.indexOf(clus.clusterName) === -1) {
      this.setState({ clusterList: [...clusterList, clus] })
      e.target.closest("tr").querySelectorAll("input")[1].readOnly = true
      e.target.closest("tr").querySelectorAll("input")[1].style.color =
        "#999999"
      e.target.closest("tr").querySelectorAll("input")[2].readOnly = true
      e.target.closest("tr").querySelectorAll("input")[2].style.color =
        "#999999"
    } else {
      clusterList.splice(keyArr.indexOf(clus.clusterName))
      e.target.closest("tr").querySelectorAll("input")[1].readOnly = false
      e.target.closest("tr").querySelectorAll("input")[1].style.color = "#000"
      e.target.closest("tr").querySelectorAll("input")[2].readOnly = false
      e.target.closest("tr").querySelectorAll("input")[2].style.color = "#000"
    }
  }

  handleChangeProfitPecentage(e) {
    const dpercentage = e.target.value
    const { zoneDetails } = this.state
    zoneDetails.profitPercentage = dpercentage
    this.setState({ zoneDetails })
  }

  handleChangeQuantity(e) {
    const dquantity = e.target.value
    const { zoneDetails } = this.state
    zoneDetails.quantityAssigned = dquantity
    this.setState({ zoneDetails })
  }

  handleChangeZoneName(e) {
    const {
      getClusters: getClustersAlt,
      getClustersForProduct: getClustersForProductAlt,
      productName,
    } = this.props
    this.setState({ zoneName: e.target.value })
    this.setState({ clusterList: [] })
    getClustersAlt(e.target.value)
    getClustersForProductAlt(productName, e.target.value)

    const { productClusterList, clusters } = this.props
    const c = clusters.filter((n) => !productClusterList.includes(n))
    const { saveUnassignedCluster: saveUnassignedClusterAlt } = this.props
    saveUnassignedClusterAlt(c)
  }

  render() {
    const {
      statusCode,
      history,
      zones,
      clusters,
      productClusterList,
    } = this.props
    const { zoneDetails, zoneName } = this.state
    const customColumnStyle = { paddingLeft: "26px" }
    return (
      <>
        {statusCode === 200 ? (
          history.push("/view/assigned/zones")
        ) : (
          <div className="box-container">
            <div className="joint-form-large">
              {/* <ProductDetails /> */}
              <div className="product-form-body-padding">
                <Typography className="card-header" variant="h4">
                  Assign to Zone
                </Typography>
                <ProductDetailsTable />
                <form className="{classes.form}" noValidate>
                  <FormControl variant="outlined" fullWidth>
                    <InputLabel htmlFor="outlined-age-native-simple">
                      Enter Zone
                    </InputLabel>
                    <Select
                      fullWidth
                      native
                      autoFocus
                      onChange={this.handleChangeZoneName}
                      label="Enter zone"
                      inputProps={{
                        name: "zone",
                        id: "zone",
                      }}
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
                    id="zoneQuantity"
                    label="zoneQuantity"
                    name="zoneQuantity"
                    type="number"
                    onChange={this.handleChangeQuantity}
                    value={zoneDetails.quantityAssigned}
                  />
                  <TextField
                    variant="outlined"
                    margin="normal"
                    required
                    fullWidth
                    id="zoneProfitPercentage"
                    label="zoneProfitPercentage"
                    name="zoneProfitPercentage"
                    type="number"
                    onChange={this.handleChangeProfitPecentage}
                    value={zoneDetails.profitPercentage}
                  />
                  <TableContainer component={Paper}>
                    <Table size="small" aria-label="a dense table">
                      <TableHead>
                        <TableRow>
                          {addMultipleClusters.map((tcell) => (
                            <TableCell>{tcell}</TableCell>
                          ))}
                        </TableRow>
                      </TableHead>

                      <tbody>
                        {clusters.map((cluster) => {
                          return (
                            <TableRow>
                              <TableCell>
                                {!productClusterList.includes(cluster) ? (
                                  <div style={customColumnStyle}>
                                    <FormControlLabel
                                      control={
                                        <Checkbox
                                          name="checkedB"
                                          color="primary"
                                          onClick={this.handleCheckBoxChange}
                                          value={cluster}
                                        />
                                      }
                                    />
                                  </div>
                                ) : (
                                  <Tooltip
                                    title="The cluster is already assigned"
                                    placement="right"
                                  >
                                    <ErrorOutlineIcon />
                                  </Tooltip>
                                )}
                              </TableCell>
                              <TableCell>
                                <Typography variant="subtitle1" gutterBottom>
                                  {cluster}
                                </Typography>
                              </TableCell>

                              <TableCell>
                                {!productClusterList.includes(cluster) ? (
                                  <TextField
                                    fullWidth
                                    type="number"
                                    pattern="[0-9]*"
                                    min="0"
                                    max="100"
                                    step="1"
                                    onKeyDown={(evt) =>
                                      evt.key === "." && evt.preventDefault()
                                    }
                                    onChange={this.updateInputValue}
                                    label="Quantity"
                                    variant="outlined"
                                  />
                                ) : (
                                  <Tooltip
                                    title="The cluster is already assigned"
                                    placement="right"
                                  >
                                    <TextField
                                      fullWidth
                                      type="number"
                                      pattern="[0-9]*"
                                      min="0"
                                      max="100"
                                      step="1"
                                      onKeyDown={(evt) =>
                                        evt.key === "." && evt.preventDefault()
                                      }
                                      onChange={this.updateInputValue}
                                      label="Quantity"
                                      variant="outlined"
                                      disabled
                                    />
                                  </Tooltip>
                                )}
                              </TableCell>

                              <TableCell>
                                {!productClusterList.includes(cluster) ? (
                                  <TextField
                                    fullWidth
                                    type="number"
                                    pattern="[0-9]*"
                                    min="0"
                                    max="100"
                                    step="1"
                                    onKeyDown={(evt) =>
                                      evt.key === "." && evt.preventDefault()
                                    }
                                    onChange={this.updateInputValue}
                                    label="Cluster Profit Percent"
                                    variant="outlined"
                                  />
                                ) : (
                                  <Tooltip
                                    title="The cluster is already assigned"
                                    placement="right"
                                  >
                                    <TextField
                                      fullWidth
                                      type="number"
                                      pattern="[0-9]*"
                                      min="0"
                                      max="100"
                                      step="1"
                                      onKeyDown={(evt) =>
                                        evt.key === "." && evt.preventDefault()
                                      }
                                      onChange={this.updateInputValue}
                                      label="Cluster Profit Percent"
                                      variant="outlined"
                                      disabled
                                    />
                                  </Tooltip>
                                )}
                              </TableCell>
                            </TableRow>
                          )
                        })}
                      </tbody>
                    </Table>
                  </TableContainer>

                  {zoneDetails.quantityAssigned > 0 &&
                    zoneDetails.profitPercentage &&
                    zoneName !== "" && (
                      <Button
                        fullWidth
                        type="button"
                        variant="contained"
                        color="primary"
                        className="form-button {classes.submit} submit-pad"
                        onClick={this.handleSubmit}
                        id="assign-zone-submit"
                      >
                        Save
                      </Button>
                    )}
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

AssignToZone.propTypes = {
  productName: PropTypes.string.isRequired,
  statusCode: PropTypes.number.isRequired,
  zones: PropTypes.arrayOf.isRequired,
  getZones: PropTypes.func.isRequired,
  history: PropTypes.shape.isRequired,
  getClusters: PropTypes.func.isRequired,
  getClustersForProduct: PropTypes.func.isRequired,
  saveUnassignedCluster: PropTypes.func.isRequired,
  clearProductClusterList: PropTypes.func.isRequired,
  clearClusterList: PropTypes.func.isRequired,
  postZoneCluster: PropTypes.func.isRequired,
  productClusterList: PropTypes.arrayOf.isRequired,
  clusters: PropTypes.arrayOf.isRequired,
}

const stateAsProps = (store) => ({
  zones: store.RetailerReducer.zones,
  productName: store.RetailerReducer.productName,
  statusCode: store.RetailerReducer.statusCode,
  productClusterList: store.RetailerReducer.productClusterList,
  clusters: store.RetailerReducer.clusters,
  unassignedCluster: store.RetailerReducer.unassignedCluster,
})

const actionAsProps = {
  getZones,
  assignToZone,
  getClusters,
  getClustersForProduct,
  saveUnassignedCluster,
  clearProductClusterList,
  clearClusterList,
  postZoneCluster,
}

export default connect(stateAsProps, actionAsProps)(AssignToZone)
