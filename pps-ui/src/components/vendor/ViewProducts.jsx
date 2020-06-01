import {
  Table,
  Typography,
  TableCell,
  TableContainer,
  TablePagination,
  TableHead,
  TableRow,
} from "@material-ui/core"
import React, { Component } from "react"
import { connect } from "react-redux"
import PropTypes from "prop-types"
import {
  getAllProducts,
  getProductCount,
} from "../../redux/actions/VendorActions"
import { viewProducts } from "../utils/constants"
import convertCurrency from "../utils/ConvertCurrency"

class ViewProducts extends Component {
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
      getAllProducts: getAllProductsAlt,
      loggedInUser,
      getProductCount: getProductCountAlt,
    } = this.props
    const { page, rowsPerPage } = this.state
    getAllProductsAlt(loggedInUser.userName, page + 1, rowsPerPage)
    getProductCountAlt(loggedInUser.userName)
  }

  handleChangePage = (event, newPage) => {
    const { rowsPerPage } = this.state
    const { getAllProducts: getAllProductsAlt, loggedInUser } = this.props
    getAllProductsAlt(loggedInUser.userName, newPage + 1, rowsPerPage)
    this.setState({ page: newPage })
  }

  handleChangeRowsPerPage = (event) => {
    this.setState({ rowsPerPage: +event.target.value })
    const { getAllProducts: getAllProductsAlt, loggedInUser } = this.props
    getAllProductsAlt(loggedInUser.userName, 1, event.target.value)
    this.setState({ page: 0 })
  }

  render() {
    const { getProducts, countOfProducts } = this.props
    const { page, rowsPerPage } = this.state
    // const customColumnStyle = { maxWidth: "300px", backgroundColor: "white", paddingLeft: "10px", paddingRight: "10px" }
    const customColumnStyle2 = { maxWidth: "980px", backgroundColor: "white" }

    // console.log(getProducts)
    // console.log(getProducts.reverse())
    // this.setState({ getAllProductsRev: getProducts.reverse()})

    return (
      <div className="box-container">
        <div className="center-body">
          <div className="flex-grid">
            <Typography color="primary" component="h1" variant="h5">
              View All Products
            </Typography>
            <TableContainer style={customColumnStyle2}>
              <Table aria-label="a dense table">
                <TableHead>
                  <TableRow>
                    {viewProducts.map((tcell) => (
                      <TableCell>{tcell}</TableCell>
                    ))}
                  </TableRow>
                </TableHead>
                <tbody>
                  {getProducts.map((product) => {
                    return (
                      <TableRow>
                        <TableCell>
                          <Typography variant="subtitle1" gutterBottom>
                            {product.productName}
                          </Typography>
                          <div id="outer-image">
                            <div id="imagePreview-small">
                              {product.productImage.map((image) => (
                                <a
                                  target="_blank"
                                  rel="noopener noreferrer"
                                  href={image}
                                >
                                  <img
                                    className="thumbnail-small"
                                    src={image}
                                    alt="none"
                                  />
                                </a>
                              ))}
                            </div>
                          </div>
                        </TableCell>
                        <TableCell>
                          <Typography variant="subtitle1" gutterBottom>
                            Base Price:
                            {sessionStorage.getItem("currency") === "USD"
                              ? `$ ${product.productBasePrice}`
                              : convertCurrency(
                                  "USD",
                                  sessionStorage.getItem("currency"),
                                  product.productBasePrice
                                )}
                          </Typography>
                          <Typography variant="subtitle1" gutterBottom>
                            Remaining Quantity : {product.remainingQuantity}
                          </Typography>
                          <Typography variant="subtitle1" gutterBottom>
                            Product UOM : {product.productUOM}
                          </Typography>
                          <Typography variant="subtitle1" gutterBottom>
                            Product Category : {product.productCategory}
                          </Typography>
                        </TableCell>
                      </TableRow>
                    )
                  })}
                </tbody>
              </Table>
            </TableContainer>
            <div className=".pt-10">
              <TablePagination
                rowsPerPageOptions={[3, 5, 10]}
                style={customColumnStyle2}
                component="div"
                count={countOfProducts}
                rowsPerPage={rowsPerPage}
                page={page}
                onChangePage={this.handleChangePage}
                onChangeRowsPerPage={this.handleChangeRowsPerPage}
              />
            </div>
          </div>
        </div>
      </div>
    )
  }
}

ViewProducts.propTypes = {
  getAllProducts: PropTypes.func.isRequired,
  getProducts: PropTypes.shape.isRequired,
  loggedInUser: PropTypes.shape.isRequired,
  getProductCount: PropTypes.func.isRequired,
  countOfProducts: PropTypes.number.isRequired,
}

const stateAsProps = (store) => ({
  getProducts: store.VendorReducer.getProducts,
  levelOption: store.RetailerReducer.levelOption,
  startDate: store.RetailerReducer.startDate,
  endDate: store.RetailerReducer.endDate,
  loggedInUser: store.RetailerReducer.loggedInUser,
  countOfProducts: store.VendorReducer.countOfProducts,
})

const actionAsProps = {
  getAllProducts,
  getProductCount,
}

export default connect(stateAsProps, actionAsProps)(ViewProducts)
