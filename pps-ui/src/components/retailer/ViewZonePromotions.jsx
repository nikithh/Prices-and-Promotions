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
import { getPromotionsInzone } from "../../redux/actions/RetailerActions"
import ProductDetailsTable from "../utils/ProductDetailsTable"
import { viewZonePromotions, zonePromotionsConst } from "../utils/constants"
import Message from "../utils/Message"
import convertCurrency from "../utils/ConvertCurrency"

class ViewZonePromotions extends Component {
  constructor(props) {
    super(props)

    this.state = {}
  }

  // eslint-disable-next-line camelcase
  UNSAFE_componentWillMount() {
    const {
      getPromotionsInzone: getPromotionsInzoneAlt,
      productName,
      zone,
    } = this.props
    getPromotionsInzoneAlt(productName, zone)
  }

  render() {
    const { zonePromotions } = this.props
    return (
      <div className="box-container">
        <div className="joint-form-large-table">
          <div className="form-center">
            <div className="flex-grid">
              <ProductDetailsTable />
              <Typography className="card-header" variant="h6">
                {zonePromotionsConst}
              </Typography>

              <TableContainer component={Paper}>
                <Table aria-label="a dense table">
                  <TableHead>
                    <TableRow>
                      {viewZonePromotions.map((tcell) => (
                        <TableCell>{tcell}</TableCell>
                      ))}
                    </TableRow>
                  </TableHead>
                  <tbody>
                    {zonePromotions.map((promotion) => (
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
                              ? `$ ${promotion.promotionSellingPrice}`
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
                            Zone
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

ViewZonePromotions.propTypes = {
  productName: PropTypes.string.isRequired,
  zone: PropTypes.string.isRequired,
  zonePromotions: PropTypes.shape.isRequired,
  getPromotionsInzone: PropTypes.func.isRequired,
}

const stateAsProps = (store) => ({
  productName: store.RetailerReducer.productName,
  zone: store.RetailerReducer.zone,
  zonePromotions: store.RetailerReducer.zonePromotions,
})

const actionAsProps = {
  getPromotionsInzone,
}
export default connect(stateAsProps, actionAsProps)(ViewZonePromotions)
