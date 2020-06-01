import React, { Component } from "react"
import connect from "react-redux/es/connect/connect"
import PropTypes from "prop-types"
import {
  Paper,
  Table,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  TablePagination,
  Button,
} from "@material-ui/core"
import { priceChangeProductDetailsTable } from "./constants"
import { cancelProductEffectivePriceChange } from "../../redux/actions/RetailerActions"
import Message from "./Message"

class PriceChangeProductDetailsTable extends Component {
  constructor(props) {
    super(props)

    this.state = {
      page: 0,
      rowsPerPage: 10,
    }
    this.handleSubmit = this.handleSubmit.bind(this)
  }

  handleSubmit = (productName) => {
    const {
      cancelProductEffectivePriceChange: cancelProductEffectivePriceChangeAlt,
    } = this.props
    cancelProductEffectivePriceChangeAlt(productName)
    document
      .getElementById("cancelprice-tbody")
      .removeChild(document.getElementById(`row-${productName}`))
  }

  handleChangePage = (event, newPage) => {
    this.setState({ page: +newPage })
  }

  handleChangeRowsPerPage = (event) => {
    this.setState({ rowsPerPage: +event.target.value })
  }

  render() {
    const { priceChangeProductsList } = this.props
    const { page, rowsPerPage } = this.state

    return (
      <>
        <TableContainer component={Paper}>
          <Table>
            <TableHead>
              <TableRow>
                {priceChangeProductDetailsTable.map((tcell) => (
                  <TableCell>{tcell}</TableCell>
                ))}
                <TableCell />
              </TableRow>
            </TableHead>
            <tbody id="cancelprice-tbody">
              {priceChangeProductsList
                .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
                .map((product) => (
                  <TableRow id={`row-${product.productName}`}>
                    <TableCell>{product.productName}</TableCell>
                    <TableCell>{product.startDate.substr(0, 10)}</TableCell>
                    <TableCell>{product.endDate.substr(0, 10)}</TableCell>
                    <TableCell>
                      <Button
                        fullWidth
                        type="button"
                        variant="contained"
                        color="primary"
                        className="{classes.submit}"
                        onClick={() => {
                          if (
                            // eslint-disable-next-line no-alert
                            window.confirm(
                              "Are you sure you wish to price change?"
                            )
                          )
                            this.handleSubmit(product.productName)
                        }}
                        id="assign-cluster-submit"
                      >
                        Cancel Price Change
                      </Button>
                    </TableCell>
                  </TableRow>
                ))}
            </tbody>
          </Table>
        </TableContainer>
        <TablePagination
          rowsPerPageOptions={[10, 15, 20]}
          component="div"
          count={priceChangeProductsList.length}
          rowsPerPage={rowsPerPage}
          page={page}
          onChangePage={this.handleChangePage}
          onChangeRowsPerPage={this.handleChangeRowsPerPage}
        />
        <Message />
      </>
    )
  }
}

PriceChangeProductDetailsTable.propTypes = {
  priceChangeProductsList: PropTypes.arrayOf.isRequired,
  cancelProductEffectivePriceChange: PropTypes.func.isRequired,
}

const stateAsProps = (store) => ({
  priceChangeProductsList: store.RetailerReducer.priceChangeProductsList,
})
const actionsAsProps = {
  cancelProductEffectivePriceChange,
}
export default connect(
  stateAsProps,
  actionsAsProps
)(PriceChangeProductDetailsTable)
