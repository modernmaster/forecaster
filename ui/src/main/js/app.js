'use strict';

import { BrowserRouter as Router, Route, Link } from "react-router-dom";
import PushNotification from './component/push-notification';
import Stocks from './page/stocks/index.js';

const React = require('react');
const ReactDOM = require('react-dom');

class App extends React.Component {

  constructor(props) {
    super(props);
  }

  componentDidMount() {
  }

  render() {

    return (
      <Router>
      <div>
        <PushNotification/>
        <Stocks/>
        <footer>
        </footer>
        </div>
      </Router>
    )
  }
}

ReactDOM.render(
    <App/>,
    document.getElementById('react')
);
