import {
  Table,
  Typography,
  Paper,
  TableCell,
  TableContainer,
  TablePagination,
  TableHead,
  TableRow,
} from "@material-ui/core"
import React, { Component } from "react"
import { connect } from "react-redux"
import PropTypes from "prop-types"
import { getPromotionsInRange } from "../../redux/actions/RetailerActions"
import {
  viewPromotions,
  promotionDetails,
  queryPromotionsForProducts,
} from "../utils/constants"
import convertCurrency from "../utils/ConvertCurrency"

class ViewPromotions extends Component {
  constructor(props) {
    super(props)
    this.state = {
      page: 0,
      rowsPerPage: 3,
    }
  }

  // eslint-disable-next-line camelcase
  UNSAFE_componentWillMount() {
    const {
      getPromotionsInRange: getPromotionsInRangeAlt,
      startDate,
      endDate,
      levelOption,
    } = this.props
    getPromotionsInRangeAlt(startDate, endDate, levelOption)
  }

  handleChangePage = (event, newPage) => {
    this.setState({ page: +newPage })
  }

  handleChangeRowsPerPage = (event) => {
    this.setState({ rowsPerPage: +event.target.value })
  }

  render() {
    const { promotions, levelOption } = this.props
    const { page, rowsPerPage } = this.state
    return (
      <div className="box-container">
        <div className="center-body">
          <div className="flex-grid">
            <Typography color="primary" component="h1" variant="h4">
              {queryPromotionsForProducts}
            </Typography>
            <TableContainer component={Paper}>
              <Table aria-label="a dense table">
                <TableHead>
                  <TableRow>
                    {viewPromotions.map((tcell) => (
                      <TableCell>{tcell}</TableCell>
                    ))}
                  </TableRow>
                </TableHead>
                <tbody>
                  {promotions
                    .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
                    .map((product) => {
                      return (
                        <TableRow>
                          <TableCell>
                            <Typography variant="subtitle1" gutterBottom>
                              {product.productName}
                            </Typography>
                            <div className="imagePreview" id="imagePreview">
                              {product.image.map((image) => (
                                <a
                                  target="_blank"
                                  rel="noopener noreferrer"
                                  href={image}
                                >
                                  <img
                                    className="thumbnail"
                                    src={image}
                                    alt="none"
                                  />
                                </a>
                              ))}
                            </div>
                          </TableCell>
                          <TableCell>
                            <Typography variant="subtitle1" gutterBottom>
                              Vendor Name : {product.vendorName}
                            </Typography>
                            <Typography variant="subtitle1" gutterBottom>
                              {sessionStorage.getItem("currency") === "USD"
                                ? `$ ${product.vendorPrice}`
                                : convertCurrency(
                                    "USD",
                                    sessionStorage.getItem("currency"),
                                    product.vendorPrice
                                  )}
                            </Typography>
                            <Typography variant="subtitle1" gutterBottom>
                              Initial Quantity : {product.initialQty}
                            </Typography>
                            <Typography variant="subtitle1" gutterBottom>
                              Remaining Quantity : {product.remainingQty}
                            </Typography>
                            <Typography variant="subtitle1" gutterBottom>
                              Product Category : {product.category}
                            </Typography>
                          </TableCell>
                          <TableCell>
                            <Typography variant="subtitle1" gutterBottom>
                              <Table size="small" aria-label="a dense table">
                                <TableHead>
                                  <TableRow>
                                    {promotionDetails.map((tcell) => (
                                      <TableCell>{tcell}</TableCell>
                                    ))}
                                  </TableRow>
                                </TableHead>
                                <tbody>
                                  {product.list.map((promotion) => {
                                    return (
                                      <TableRow key={promotion.promotionId}>
                                        <TableCell>
                                          <Typography
                                            variant="subtitle1"
                                            gutterBottom
                                          >
                                            {promotion.promotionPercentage}
                                          </Typography>
                                        </TableCell>
                                        <TableCell>
                                          <Typography
                                            variant="subtitle1"
                                            gutterBottom
                                          >
                                            {sessionStorage.getItem(
                                              "currency"
                                            ) === "USD"
                                              ? `$ ${promotion.promotionSellingPrice}`
                                              : convertCurrency(
                                                  "USD",
                                                  sessionStorage.getItem(
                                                    "currency"
                                                  ),
                                                  promotion.promotionSellingPrice
                                                )}
                                          </Typography>
                                        </TableCell>
                                        <TableCell>
                                          <Typography
                                            variant="subtitle1"
                                            gutterBottom
                                          >
                                            {promotion.startDate.slice(0, 10)}
                                          </Typography>
                                        </TableCell>
                                        <TableCell>
                                          <Typography
                                            variant="subtitle1"
                                            gutterBottom
                                          >
                                            {promotion.endDate.slice(0, 10)}
                                          </Typography>
                                        </TableCell>
                                        <TableCell>
                                          <Typography
                                            variant="subtitle1"
                                            gutterBottom
                                          >
                                            {levelOption}
                                          </Typography>
                                          <Typography
                                            variant="subtitle1"
                                            gutterBottom
                                          >
                                            {promotion.zoneCluster}
                                          </Typography>
                                        </TableCell>
                                      </TableRow>
                                    )
                                  })}
                                </tbody>
                              </Table>
                            </Typography>
                          </TableCell>
                        </TableRow>
                      )
                    })}
                </tbody>
              </Table>
            </TableContainer>
            <TablePagination
              rowsPerPageOptions={[3, 5, 10]}
              component="div"
              count={promotions.length}
              rowsPerPage={rowsPerPage}
              page={page}
              onChangePage={this.handleChangePage}
              onChangeRowsPerPage={this.handleChangeRowsPerPage}
            />
          </div>
        </div>
      </div>
    )
  }
}

ViewPromotions.propTypes = {
  getPromotionsInRange: PropTypes.func.isRequired,
  promotions: PropTypes.shape.isRequired,
  levelOption: PropTypes.string.isRequired,
  startDate: PropTypes.instanceOf(Date).isRequired,
  endDate: PropTypes.instanceOf(Date).isRequired,
}

const stateAsProps = (store) => ({
  promotions: store.RetailerReducer.promotions,
  levelOption: store.RetailerReducer.levelOption,
  startDate: store.RetailerReducer.startDate,
  endDate: store.RetailerReducer.endDate,
})

const actionAsProps = {
  getPromotionsInRange,
}

export default connect(stateAsProps, actionAsProps)(ViewPromotions)
