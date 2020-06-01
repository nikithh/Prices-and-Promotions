import {
  Typography,
  Paper,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Table,
} from "@material-ui/core"
import React, { Component } from "react"
import { connect } from "react-redux"
import PropTypes from "prop-types"
import { getPromotionsIncluster } from "../../redux/actions/RetailerActions"
import ProductDetailsTable from "../utils/ProductDetailsTable"
import { viewClusterPromotion } from "../utils/constants"
import Message from "../utils/Message"
import convertCurrency from "../utils/ConvertCurrency"

class ViewClusterPromotions extends Component {
  constructor(props) {
    super(props)

    this.state = {}
  }

  // eslint-disable-next-line camelcase
  UNSAFE_componentWillMount() {
    const {
      getPromotionsIncluster: getPromotionsInclusterAlt,
      productName,
      zone,
      cluster,
    } = this.props
    getPromotionsInclusterAlt(productName, zone, cluster)
  }

  render() {
    const { clusterPromotions } = this.props
    return (
      <div className="box-container">
        <div className="joint-form-large-table">
          <div className="form-center">
            <div className="flex-grid">
              <ProductDetailsTable />
              <Typography className="card-header" variant="h6">
                Promotions in Cluster level
              </Typography>

              <TableContainer component={Paper}>
                <Table aria-label="a dense table">
                  <TableHead>
                    <TableRow>
                      {viewClusterPromotion.map((tcell) => (
                        <TableCell>{tcell}</TableCell>
                      ))}
                    </TableRow>
                  </TableHead>
                  <tbody>
                    {clusterPromotions.map((promotion) => (
                      <TableRow key={promotion.promotionId}>
                        <TableCell>
                          <Typography variant="subtitle1" gutterBottom>
                            {promotion.appliedDate.slice(0, 10)}
                          </Typography>
                        </TableCell>
                        <TableCell>
                          <Typography variant="subtitle1" gutterBottom>
                            {promotion.promotionPercentage}
                          </Typography>
                        </TableCell>
                        <TableCell>
                          <Typography variant="subtitle1" gutterBottom>
                            {sessionStorage.getItem("currency") === "USD"
                              ? promotion.promotionSellingPrice
                              : convertCurrency(
                                  "USD",
                                  sessionStorage.getItem("currency"),
                                  promotion.promotionSellingPrice
                                )}
                          </Typography>
                        </TableCell>
                        <TableCell>
                          <Typography variant="subtitle1" gutterBottom>
                            {promotion.startDate.slice(0, 10)}
                          </Typography>
                        </TableCell>
                        <TableCell>
                          <Typography variant="subtitle1" gutterBottom>
                            {promotion.endDate.slice(0, 10)}
                          </Typography>
                        </TableCell>
                        <TableCell>
                          <Typography variant="subtitle1" gutterBottom>
                            Cluster
                          </Typography>
                        </TableCell>
                        <TableCell>
                          {promotion.cancelledDate !== null && (
                            <Typography variant="subtitle1" gutterBottom>
                              {promotion.cancelledDate.slice(0, 10)}
                            </Typography>
                          )}
                        </TableCell>
                      </TableRow>
                    ))}
                  </tbody>
                </Table>
              </TableContainer>
            </div>
          </div>
        </div>
        <Message />
      </div>
    )
  }
}

ViewClusterPromotions.propTypes = {
  productName: PropTypes.string.isRequired,
  getPromotionsIncluster: PropTypes.func.isRequired,
  zone: PropTypes.string.isRequired,
  cluster: PropTypes.string.isRequired,
  clusterPromotions: PropTypes.shape.isRequired,
}

const stateAsProps = (store) => ({
  productName: store.RetailerReducer.productName,
  zone: store.RetailerReducer.zone,
  cluster: store.RetailerReducer.cluster,
  clusterPromotions: store.RetailerReducer.clusterPromotions,
})

const actionAsProps = {
  getPromotionsIncluster,
}
export default connect(stateAsProps, actionAsProps)(ViewClusterPromotions)
