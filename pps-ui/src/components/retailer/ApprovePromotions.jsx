import {
  Table,
  Typography,
  Paper,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  IconButton,
  Tooltip,
} from "@material-ui/core"
import CheckCircleOutlinedIcon from "@material-ui/icons/CheckCircleOutlined"
import CancelOutlinedIcon from "@material-ui/icons/CancelOutlined"
import React, { Component } from "react"
import { connect } from "react-redux"
import PropTypes from "prop-types"
import ProductDetailsTable from "../utils/ProductDetailsTable"
import {
  getPendingPromotions,
  approvePromotions,
} from "../../redux/actions/RetailerActions"
import {
  approvePromotion,
  approvePromotionZoneCluster,
  approvePromotionTable,
} from "../utils/constants"
import Message from "../utils/Message"

class ApprovePromotions extends Component {
  constructor(props) {
    super(props)
    this.state = {}
  }

  // eslint-disable-next-line camelcase
  UNSAFE_componentWillMount() {
    const {
      productName,
      getPendingPromotions: getPendingPromotionsAlt,
    } = this.props
    getPendingPromotionsAlt(productName)
  }

  handleSubmit = (e, promoId, result) => {
    const { productName, approvePromotions: approvePromotionsAlt } = this.props
    approvePromotionsAlt(promoId, productName, result)
    document
      .getElementById("withdraw-tbody")
      .removeChild(document.getElementById(`row${promoId}`))
  }

  render() {
    const { pendingPromotions } = this.props
    return (
      <div className="box-container">
        <div className="joint-form-large-table">
          <div className="form-center">
            <div className="flex-grid">
              <Typography className="card-header" variant="h4">
                {approvePromotion}
              </Typography>
              <ProductDetailsTable />
              <Typography className="card-header" variant="h5">
                {approvePromotionZoneCluster}
              </Typography>
              <TableContainer component={Paper}>
                <Table aria-label="a dense table">
                  <TableHead>
                    <TableRow>
                      {approvePromotionTable.map((tcell) => (
                        <TableCell>{tcell}</TableCell>
                      ))}
                    </TableRow>
                  </TableHead>
                  <tbody id="withdraw-tbody">
                    {pendingPromotions.map((promotion) => (
                      <TableRow id={`row${promotion.promotionId}`}>
                        <TableCell>
                          <Typography variant="subtitle1" gutterBottom>
                            {promotion.clusterPrice}
                          </Typography>
                        </TableCell>
                        <TableCell>
                          <Typography variant="subtitle1" gutterBottom>
                            {promotion.appliedDate.slice(0, 10)}
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
                            {promotion.zoneCluster}
                          </Typography>
                        </TableCell>
                        <TableCell>
                          <Typography variant="subtitle1" gutterBottom>
                            <div>
                              <Tooltip
                                title="Accept Promotion"
                                placement="left"
                              >
                                <IconButton>
                                  <CheckCircleOutlinedIcon
                                    className="accept-icon"
                                    fontSize="large"
                                    onClick={(e) => {
                                      if (
                                        // eslint-disable-next-line no-alert
                                        window.confirm(
                                          "Are you sure you wish to accept the promotion?"
                                        )
                                      )
                                        this.handleSubmit(
                                          e,
                                          promotion.promotionId,
                                          "APPROVED"
                                        )
                                    }}
                                  />
                                </IconButton>
                              </Tooltip>
                              <Tooltip
                                title="Reject Promotion"
                                placement="left"
                              >
                                <IconButton>
                                  <CancelOutlinedIcon
                                    fontSize="large"
                                    className="reject-icon"
                                    onClick={(e) => {
                                      if (
                                        // eslint-disable-next-line no-alert
                                        window.confirm(
                                          "Are you sure you wish to reject the promotion?"
                                        )
                                      )
                                        this.handleSubmit(
                                          e,
                                          promotion.promotionId,
                                          "REJECTED"
                                        )
                                    }}
                                  />
                                </IconButton>
                              </Tooltip>
                            </div>
                          </Typography>
                        </TableCell>
                      </TableRow>
                    ))}
                  </tbody>
                </Table>
              </TableContainer>
            </div>
            <Message />
          </div>
        </div>
      </div>
    )
  }
}

ApprovePromotions.propTypes = {
  productName: PropTypes.string.isRequired,
  getPendingPromotions: PropTypes.func.isRequired,
  pendingPromotions: PropTypes.shape.isRequired,
  approvePromotions: PropTypes.func.isRequired,
}

const stateAsProps = (store) => ({
  productName: store.RetailerReducer.productName,
  pendingPromotions: store.RetailerReducer.pendingPromotions,
})
const actionAsProps = {
  getPendingPromotions,
  approvePromotions,
}
export default connect(stateAsProps, actionAsProps)(ApprovePromotions)
