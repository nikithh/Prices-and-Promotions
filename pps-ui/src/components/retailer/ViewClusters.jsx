import {
  Table,
  Paper,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  TablePagination,
  TableBody,
  Typography,
} from "@material-ui/core"
import React, { Component } from "react"
import { connect } from "react-redux"
import PropTypes from "prop-types"
import { getClusterList } from "../../redux/actions/RetailerActions"
import { viewClusters, viewClustersConst } from "../utils/constants"

class ViewClusters extends Component {
  constructor(props) {
    super(props)
    const { getClusterList: getClusterListAlt } = this.props
    getClusterListAlt()

    this.state = {
      page: 0,
      rowsPerPage: 10,
    }
  }

  handleChangePage = (event, newPage) => {
    this.setState({ page: +newPage })
  }

  handleChangeRowsPerPage = (event) => {
    this.setState({ rowsPerPage: +event.target.value })
  }

  render() {
    const { clusterList } = this.props
    const { page, rowsPerPage } = this.state
    const classes = this.props
    const rows = clusterList
    return (
      <div className="box-container-table data-tables">
        <div className="flex-grid">
          <Typography className="card-header" variant="h4">
            {viewClustersConst}
          </Typography>

          <Paper className={classes.root}>
            <TableContainer className={classes.container}>
              <Table stickyHeader aria-label="sticky table">
                <TableHead>
                  <TableRow>
                    {/* <TableCell>Cluster Name</TableCell>
                  <TableCell>Number of Stores</TableCell> */}
                    {viewClusters.map((tcell) => (
                      <TableCell>{tcell}</TableCell>
                    ))}
                  </TableRow>
                </TableHead>
                <TableBody>
                  {Object.keys(rows)
                    .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
                    .map((row) => {
                      return (
                        <TableRow
                          hover
                          role="checkbox"
                          tabIndex={-1}
                          key={row.code}
                        >
                          <TableCell>{row}</TableCell>
                          <TableCell>{rows[row]}</TableCell>
                        </TableRow>
                      )
                    })}
                </TableBody>
              </Table>
            </TableContainer>
            <TablePagination
              rowsPerPageOptions={[10, 25, 100]}
              component="div"
              count={Object.keys(rows).length}
              rowsPerPage={rowsPerPage}
              page={page}
              onChangePage={this.handleChangePage}
              onChangeRowsPerPage={this.handleChangeRowsPerPage}
            />
          </Paper>
        </div>
      </div>
    )
  }
}

ViewClusters.propTypes = {
  getClusterList: PropTypes.func.isRequired,
  clusterList: PropTypes.shape.isRequired,
  root: PropTypes.shape.isRequired,
  container: PropTypes.shape.isRequired,
}

const stateAsProps = (store) => ({
  clusterList: store.RetailerReducer.clusterList,
})
const actionAsProps = {
  getClusterList,
}
export default connect(stateAsProps, actionAsProps)(ViewClusters)
