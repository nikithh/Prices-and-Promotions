import { createStore, applyMiddleware } from "redux"
import { composeWithDevTools } from "redux-devtools-extension"
import thunk from "redux-thunk"
import rootReducer from "./reducers/rootReducer"

let state = window.sessionStorage.reduxstate
if (state) {
  state = JSON.parse(state)
}

// eslint-disable-next-line import/no-mutable-exports
let store = null
if (state) {
  store = createStore
  store = createStore(
    rootReducer,
    state,
    composeWithDevTools(applyMiddleware(thunk))
  )
} else {
  store = createStore(rootReducer, composeWithDevTools(applyMiddleware(thunk)))
}

// the callback to subscribe is executed everytime the state changes
// in the store
// this is there to make the store persistent
store.subscribe(() => {
  window.sessionStorage.reduxstate = JSON.stringify(store.getState())
})

export default store
